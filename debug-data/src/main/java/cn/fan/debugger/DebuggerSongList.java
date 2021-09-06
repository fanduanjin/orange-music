package cn.fan.debugger;

import cn.fan.constant.ConfigConstant;
import cn.fan.model.constanst.DebuggerConstant;
import cn.fan.model.music.Singer;
import cn.fan.model.music.Song;
import cn.fan.util.QqEncrypt;
import cn.fan.util.RequestTemplate;
import cn.fan.util.ResponseHandler;
import cn.hutool.core.util.PageUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.dubbo.common.threadpool.ThreadPool;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @program: orange-music
 * @description: 爬取歌手列表
 * @author: fanduanjin
 * @create: 2021-04-18 15:01
 */

@Component
public class DebuggerSongList {
    private static final ThreadLocal<Integer> songNum = new ThreadLocal<>();

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    private static final String group = "singerSongList";
    private static final String method = "GetSingerSongList";
    private static final String module = "musichall.song_list_server";
    private static final String param_template = "{\"order\":1,\"singerMid\":\"$singerMid\",\"begin\":$begin,\"num\":$num}";
    private static final String param_num = "$num";
    private static final String param_begin = "$begin";
    private static final String param_singerMid = "$singerMid";
    private static final int pageNum = 10;

    private static final Logger LOGGER = LoggerFactory.getLogger(DebuggerSongList.class);

    public void debugger(Singer singer, ResultHandler resultHandler) throws IOException {
        RequestTemplate requestTemplate = new RequestTemplate();
        requestTemplate.setGroup(group);
        requestTemplate.setModule(module);
        requestTemplate.setMethod(method);
        requestTemplate.setParam(buildParam(singer.getMid(), 1));
        String data = requestTemplate.toString();
        String sign = QqEncrypt.getSign(data);
        String url = ConfigConstant.baseUrl + "sign=" + sign + "&data=" + data;
        Connection connection = Jsoup.connect(url);
        Connection.Request request = connection.request();
        Connection.Response response = connection.execute();
        JSONObject root = ResponseHandler.getData(request, response, group);
        if (root == null)
            return;
        JSONObject jso_data = root.getJSONObject("data");
        //获取总页数
        int totalNum = jso_data.getIntValue("totalNum");
        int totalPage = PageUtil.totalPage(totalNum, pageNum);
        //处理爬取的 歌曲
        JSONArray jsa_songList = jso_data.getJSONArray("songList");
        List<Song> songs = handlerSongList(jsa_songList);
        resultHandler.handler(songs);
        songNum.set(songs.size());
        List<CompletableFuture<Integer>> futures=new ArrayList<>(totalPage);
        for (int i = 2; i <= totalPage; i++) {
            int finalI = i;
            CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
                List<Song> songs1 = null;
                try {
                    songs1 = debugger(singer.getMid(), finalI);
                } catch (IOException e) {
                    LOGGER.error(e.toString());
                    return 0;
                }
                resultHandler.handler(songs1);
                return songs1.size();
            }, threadPoolTaskExecutor);
            futures.add(future);
        }
        for(CompletableFuture<Integer> future:futures){
            try {
                songNum.set(future.get()+songNum.get());
                System.out.println(songNum.get());
            } catch (InterruptedException e) {
                LOGGER.error(e.toString());
            } catch (ExecutionException e) {
                LOGGER.error(e.toString());
            }
        }
        LOGGER.info(singer.getMid() + ":" + singer.getName() + "  歌曲总数:" + totalNum + "  爬取总数" + songNum.get());

    }

    List<Song> debugger(String singer_mid, int pageIndex) throws IOException {
        RequestTemplate requestTemplate = new RequestTemplate();
        requestTemplate.setGroup(group);
        requestTemplate.setModule(module);
        requestTemplate.setMethod(method);
        requestTemplate.setParam(buildParam(singer_mid, pageIndex));
        String data = requestTemplate.toString();
        String sign = QqEncrypt.getSign(data);
        String url = ConfigConstant.baseUrl + "sign=" + sign + "&data=" + data;
        Connection connection = Jsoup.connect(url);
        Connection.Request request = connection.request();
        Connection.Response response = connection.execute();
        JSONObject root = ResponseHandler.getData(request, response, group);
        JSONObject jso_data = root.getJSONObject("data");
        JSONArray jsa_songList = jso_data.getJSONArray("songList");
        List<Song> songs = handlerSongList(jsa_songList);
        return songs;
    }

    String buildParam(String singerMid, int pageIndex) {
        String param = param_template.replace(param_singerMid, singerMid);
        param = param.replace(param_begin, String.valueOf((pageIndex - 1) * pageNum));
        param = param.replace(param_num, String.valueOf(pageNum));
        return param;
    }

    /**
     * "songInfo": {
     * "id": 105044366,
     * "type": 0,
     * "mid": "003FIqDQ0xyaGF",
     * "name": "我的梦",
     * "title": "我的梦",
     * "subtitle": "华为手机主题曲|《为了你我愿意热爱整个世界》影视剧主题曲",
     * "singer": [
     * {
     * "id": 4607,
     * "mid": "000aw4WC2EQYTv",
     * "name": "张靓颖",
     * "title": "张靓颖",
     * "type": 1,
     * "uin": 0,
     * "pmid": ""
     * }
     * ],
     * "album": {
     * "id": 1213329,
     * "mid": "002JsU5q10jUBt",
     * "name": "我的梦",
     * "title": "我的梦",
     * "subtitle": "华为手机主题曲|《为了你我愿意热爱整个世界》影视剧主题曲",
     * "time_public": "2015-11-26",
     * "pmid": "002JsU5q10jUBt_1"
     * },
     * "mv": {
     * "id": 1058494,
     * "vid": "a00229izdld",
     * "name": "",
     * "title": "",
     * "vt": 0
     * },
     * "interval": 219,
     * "isonly": 0,
     * "language": 0,
     * "genre": 1,
     * "index_cd": 0,
     * "index_album": 1,
     * "time_public": "2015-11-26",
     * "status": 0,
     * "fnote": 4009,
     * "file": {
     * "media_mid": "002ASqZy0YXb6I",
     * "size_24aac": 0,
     * "size_48aac": 1344072,
     * "size_96aac": 2703950,
     * "size_192ogg": 4724943,
     * "size_192aac": 5320486,
     * "size_128mp3": 3513976,
     * "size_320mp3": 8784651,
     * "size_ape": 0,
     * "size_flac": 23069313,
     * "size_dts": 0,
     * "size_try": 0,
     * "try_begin": 42897,
     * "try_end": 70226,
     * "url": "",
     * "size_hires": 0,
     * "hires_sample": 0,
     * "hires_bitdepth": 0,
     * "b_30s": 0,
     * "e_30s": 0,
     * "size_96ogg": 2427240
     * },
     * "pay": {
     * "pay_month": 1,
     * "price_track": 200,
     * "price_album": 0,
     * "pay_play": 0,
     * "pay_down": 1,
     * "pay_status": 0,
     * "time_free": 0
     * },
     * "action": {
     * "switch": 16889603,
     * "msgid": 14,
     * "alert": 2,
     * "icons": 8535932,
     * "msgshare": 0,
     * "msgfav": 0,
     * "msgdown": 0,
     * "msgpay": 6
     * },
     * "ksong": {
     * "id": 186818,
     * "mid": "000PUkhD2EI94s"
     * },
     * "volume": {
     * "gain": -8.381,
     * "peak": 0.996,
     * "lra": 9.62
     * },
     * "label": "0",
     * "url": "",
     * "bpm": 60,
     * "version": 0,
     * "trace": "",
     * "data_type": 0,
     * "modify_stamp": 0,
     * "pingpong": "",
     * "aid": 0,
     * "ppurl": "",
     * "tid": 0,
     * "ov": 0,
     * "sa": 0,
     * "es": "",
     * "vs": [
     * "",
     * ""
     * ]
     * }
     *
     * @param jsonArray
     */
    List<Song> handlerSongList(JSONArray jsonArray) {
        JSONObject jso_song = null;
        JSONObject jso_songInfo = null;
        List<Song> songs = new ArrayList<>(pageNum);
        Song song;
        for (Iterator iterator = jsonArray.iterator(); iterator.hasNext(); ) {
            song = new Song();
            jso_song = (JSONObject) iterator.next();
            jso_songInfo = jso_song.getJSONObject("songInfo");
            String song_mid = jso_songInfo.getString("mid");
            song.setMid(song_mid);
            int song_id = jso_songInfo.getIntValue("id");
            song.setPlatId(song_id);
            String name = jso_songInfo.getString("name");
            song.setName(name);
            String title = jso_songInfo.getString("title");
            song.setTitle(title);
            String subtitle = jso_songInfo.getString("subtitle");
            song.setSubTitle(subtitle);
            //JSONObject jso_file=jso_songInfo.getJSONObject("file");
            //song.setMediaMid(jso_file.getString("media_mid"));
            songs.add(song);
        }
        return songs;
    }

    public interface ResultHandler {
        void handler(List<Song> songs);
    }

}
