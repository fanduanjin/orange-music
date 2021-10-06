package cn.fan.consumer;


import cn.fan.api.file.IFileService;
import cn.fan.api.file.IResourceService;
import cn.fan.api.lock.ILock;
import cn.fan.api.music.ISingerService;
import cn.fan.api.music.ISongService;
import cn.fan.debugger.DebuggerSingerDetail;
import cn.fan.debugger.DebuggerSingerList;
import cn.fan.debugger.DebuggerSongDetail;
import cn.fan.debugger.DebuggerSongList;
import cn.fan.model.constanst.DebuggerConstant;
import cn.fan.model.file.Resource;
import cn.fan.model.music.Singer;
import cn.fan.model.music.Song;
import cn.hutool.core.io.FileUtil;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;

import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.xml.bind.ValidationEvent;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
public class DebuggerConsumer {

    static final Logger LOGGER = LoggerFactory.getLogger(DebuggerConsumer.class);

    @Value("${spring.application.workerId}")
    int workerId;
    @Value("${spring.application.workerId}")
    int datacenterId;
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    DebuggerSingerList debuggerSingerList;

    @Autowired
    DebuggerSingerDetail debuggerSingerDetail;

    @Autowired
    DebuggerSongList debuggerSongList;

    @Autowired
    DebuggerSongDetail debuggerSongDetail;

    @DubboReference
    ILock lock;

    @DubboReference
    IFileService fileService;

    @DubboReference
    IResourceService resourceService;

    @DubboReference
    ISingerService singerService;

    @DubboReference
    ISongService songService;

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;


    //@Scheduled(fixedDelay = 5000)
    public void testDebugger() {
    }


    @Scheduled(cron = "*/50 * * * * *")
    public void startDebuggerSingers() {
        LOGGER.info("开始爬取");
        try {
            if (lock.lock("/start debugger"))
                debuggerSingerList.debugger(new DebuggerSingerList.ResultHandler() {
                    @Override
                    public void handler(List<Singer> singers) {
                        for (Singer singer : singers) {
                            rabbitTemplate.convertAndSend(DebuggerConstant.queue_singer_list, singer);
                        }
                    }
                });
        } catch (IOException e) {
            LOGGER.error(e.toString());
        } finally {
            //lock.unlock("start debugger");
        }
    }

    @RabbitListener(queuesToDeclare = @Queue(DebuggerConstant.queue_singer_list))
    void consumerSingerList(Singer singer) {
        try {
            //singer.setMid("000WvhDw0ciqdg");
            singer = debuggerSingerDetail.debugger(singer.getMid());
            Singer finalSinger = singer;
            //singer 为空 爬取失败
            if (singer == null)
                return;
            Singer db_singer = singerService.getByPlatId(singer.getPlatId());
            if (db_singer != null) {
                //不为空 验证 歌手信息是否更新
                singer.setId(db_singer.getId());
                singer.setPicResourceId(db_singer.getPicResourceId());
                if (!singer.equals(db_singer)) {
                    //歌手信息更改了
                    CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
                        singerService.modify(finalSinger);
                        return 0;
                    }, threadPoolTaskExecutor);
                    CompletableFuture<Void> completableFutureCheckResource = CompletableFuture.supplyAsync(() -> {
                        String msg = StrUtil.join(DebuggerConstant.mq_message_join_charter, String.valueOf(db_singer.getPicResourceId()), finalSinger.getPic());
                        rabbitTemplate.convertAndSend(DebuggerConstant.queue_check_resource_modify, msg);
                        return null;
                    }, threadPoolTaskExecutor);
                    completableFuture.thenAccept((result) -> {
                        if (result != 0)
                            LOGGER.warn("歌手信息修改失败 失败代码:" + result);
                        LOGGER.info("歌手信息更改了:" + finalSinger.getName());
                    });
                    completableFuture.exceptionally((e) -> {
                        LOGGER.error(e.toString());
                        return 1;
                    });
                    completableFutureCheckResource.exceptionally((e) -> {
                        LOGGER.warn(e.toString());
                        return null;
                    });
                    //CompletableFuture.allOf(completableFutureCheckResource);
                }
            } else {
                //歌手不存在 保存到数据库
                long picResourceId = IdUtil.getSnowflake(workerId, datacenterId).nextId();
                finalSinger.setPicResourceId(picResourceId);
                CompletableFuture<Integer> completableFutureInsert = CompletableFuture.supplyAsync(() -> {
                    singerService.insert(finalSinger);
                    return 0;
                }, threadPoolTaskExecutor);
                CompletableFuture<Void> completableFutureRecource = CompletableFuture.supplyAsync(() -> {
                    String msg = StrUtil.join(DebuggerConstant.mq_message_join_charter, String.valueOf(picResourceId), finalSinger.getPic());
                    rabbitTemplate.convertAndSend(DebuggerConstant.queue_download_resource, msg);
                    LOGGER.info("发送文件下载队列 进行等待下载!");
                    return null;
                }, threadPoolTaskExecutor);
                completableFutureInsert.thenAccept((result) -> {
                    if (result != 0)
                        LOGGER.warn("插入失败 失败代码:" + result);
                    LOGGER.info("持久化歌手成功:" + finalSinger.getName());
                });
                completableFutureInsert.exceptionally((e) -> {
                    LOGGER.error(e.toString());
                    return 1;
                });
                completableFutureRecource.exceptionally((e) -> {
                    LOGGER.warn(e.toString());
                    return null;
                });

            }
            rabbitTemplate.convertAndSend(DebuggerConstant.queue_song_list, singer);
        } catch (Exception e) {
            LOGGER.error(e.toString());
        }
    }

    /**
     * 处理歌手列表
     */
    @RabbitListener(queuesToDeclare = @Queue(DebuggerConstant.queue_song_list))
    void consumerDebuggerSongList(Singer singer) {
        try {
            debuggerSongList.debugger(singer, new DebuggerSongList.ResultHandler() {
                @Override
                public void handler(List<Song> songs) {
                    for (Song song : songs) {
                        song.setSingerId(singer.getId());
                        song.setSingerPlatId(singer.getPlatId());
                        rabbitTemplate.convertAndSend(DebuggerConstant.queue_song_detail, song);
                    }
                }
            });
        } catch (IOException e) {
            LOGGER.error(e.toString());
        }
    }

    /**
     * 爬取歌曲详情
     *
     * @param song
     */
    @RabbitListener(queuesToDeclare = @Queue(DebuggerConstant.queue_song_detail))
    void consumerDebuggerSongDetail(Song song) {
        debuggerSongDetail.debugger(song, new DebuggerSongDetail.ResultHandler() {
            @Override
            public void handler(Song result) {
                //爬取完毕 含歌词 audio文件路径
                Song db_song = songService.getByPlatId(song.getPlatId());
                if (db_song != null) {
                    //验证歌曲信息是否修改
                } else {
                    //插入到数据库
                    CompletableFuture<Void> completableFutureInsert = CompletableFuture.runAsync(new Runnable() {
                        @Override
                        public void run() {
                            songService.insert(result);
                            LOGGER.info("歌曲以持久化数据库" + song.getSubTitle());
                        }
                    }, threadPoolTaskExecutor);
                    //下载audio任务
                    CompletableFuture<Void> completableFutureDownload = CompletableFuture.runAsync(new Runnable() {
                        @Override
                        public void run() {
                            long resourceId = IdUtil.getSnowflake(workerId, datacenterId).nextId();
                            if (song.getMediaPath() == null)
                                song.setMediaPath("no/resource.err");
                            rabbitTemplate.convertAndSend(DebuggerConstant.queue_download_resource, StrUtil.join(DebuggerConstant.mq_message_join_charter, String.valueOf(resourceId), song.getMediaPath()));
                            LOGGER.info("准备下载 audio" +StrUtil.join(DebuggerConstant.mq_message_join_charter, String.valueOf(resourceId), song.getMediaPath()));
                        }
                    }, threadPoolTaskExecutor);
                    try {
                        completableFutureDownload.exceptionally((e)->{
                            LOGGER.warn(e.toString());
                            return null;
                        });
                        completableFutureInsert.exceptionally((e)->{
                            LOGGER.warn(e.toString());
                            return null;
                        });
                        completableFutureDownload.get();
                        completableFutureInsert.get();
                        LOGGER.info("歌曲详情爬取完毕:" + song.getSubTitle());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    /**
     * 验证网络资源是否 修改
     */

    @RabbitListener(queuesToDeclare = @Queue(DebuggerConstant.queue_check_resource_modify))
    void consumerCheckResourceIsModify(String msg) {
        String[] strArr = StrUtil.split(msg, DebuggerConstant.mq_message_join_charter);
        long resourceId = Long.valueOf(strArr[0]);
        String resourcePath = strArr[1];
        Resource resource = resourceService.getById(resourceId);
        if (resource == null) {
            LOGGER.warn("check resource is not find :" + resourceId + resourcePath + " try download resource");
            rabbitTemplate.convertAndSend(DebuggerConstant.queue_download_resource, msg);
            return;
        }
        if (!resource.getSourcePath().equals(resourcePath)) {
            //资源文件更改了
            byte[] bytes = HttpUtil.downloadBytes(resourcePath);
            String fileType = FileUtil.getSuffix(resourcePath);
            String dfsPath = fileService.uploadFile(bytes, fileType);
            resource.setSourcePath(resourcePath);
            resource.setDfsPath(dfsPath);
            resourceService.updateById(resource);
            LOGGER.info("resource modifyed");
        }
    }

    @RabbitListener(queuesToDeclare = @Queue(DebuggerConstant.queue_download_resource))
    void consumerDownloadResource(String msg) {
        String[] strArr = StrUtil.split(msg, DebuggerConstant.mq_message_join_charter);
        long resourceId = Long.valueOf(strArr[0]);
        String resourcePath = strArr[1];
        byte[] bytes = null;
        try {
            bytes = HttpUtil.downloadBytes(resourcePath);
        } catch (Exception e) {
            LOGGER.warn("文件下载失败:" + e.getClass());
            bytes = new byte[0];
        }
        String fileType = FileUtil.getSuffix(resourcePath);
        String dfsPath = fileService.uploadFile(bytes, fileType);
        Resource resource = new Resource();
        resource.setResourceId(resourceId);
        resource.setSourcePath(resourcePath);
        resource.setDfsPath(dfsPath);
        try {
            resourceService.insertResource(resource);
        } catch (Exception e) {
            LOGGER.warn("download resource consumer falid" + e.toString());
            fileService.deleteFile(dfsPath);
        }

        LOGGER.info("文件下载成功");
    }
}
