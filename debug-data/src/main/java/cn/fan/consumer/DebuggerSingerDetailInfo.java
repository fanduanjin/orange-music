package cn.fan.consumer;

import ch.qos.logback.core.util.FileUtil;
import cn.fan.api.file.IFileService;
import cn.fan.model.constanst.DebuggerConstant;
import cn.fan.model.debugger.SingerInfo;
import com.alibaba.fastjson.JSON;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Reference;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;

import java.io.*;

/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-04-17 14:08
 */

@Component
@RabbitListener(queuesToDeclare = @Queue(DebuggerConstant.queue_singer_detail))
public class DebuggerSingerDetailInfo {
    private static final Logger LOGGER = LoggerFactory.getLogger(DebuggerSingerDetailInfo.class);

    public static final String baseUrl = "https://y.qq.com/n/ryqq/singer/";

    @DubboReference
    private IFileService fileService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    @RabbitHandler
    public void receivel(SingerInfo msg) {
        try {
            Document document = Jsoup.connect(baseUrl + msg.getSinger_mid()).get();
            Elements elements = document.getElementsByClass("popup_data_detail__cont");
            //歌手简介div
            msg.setSinger_desc(elements.html());
            //保存到mongodb
            Query query = new Query();
            query.addCriteria(Criteria.where("singer_id").is(msg.getSinger_id()));
            SingerInfo singerInfo = mongoTemplate.findOne(query, SingerInfo.class, DebuggerConstant.coll_singer);
            if (singerInfo == null) {
                //不存在 新增
                LOGGER.info("mongodb 新增" + msg.getSinger_name());
                //保存mongodb
                mongoTemplate.save(msg, DebuggerConstant.coll_singer);
            } else {
                //存在 修改
                boolean picModify = !msg.getSinger_pic().equals(singerInfo.getSinger_pic());
                boolean isModify = !msg.getSinger_mid().equals(singerInfo.getSinger_mid()) ||
                        !msg.getSinger_desc().equals(singerInfo.getSinger_desc()) ||
                        !msg.getSinger_name().equals(singerInfo.getSinger_name())
                        || picModify;
                if (isModify) {
                    if (picModify) {
                        //只是修改头像 则更新头像 修改其他信息 则修改其他信息
                        //提交到 mq 修改完 并更新 mongodb 图片信息
                        LOGGER.info("头像修改了" + msg.getSinger_name());
                    } else {
                        LOGGER.info("歌手其他信息修改了"+singerInfo.getSinger_name());
                        Update update = new Update();
                        update.set("singer_id", msg.getSinger_id());
                        update.set("singer_mid", msg.getSinger_mid());
                        update.set("singer_desc", msg.getSinger_desc());
                        update.set("singer_name", msg.getSinger_name());
                        mongoTemplate.updateFirst(query, update, DebuggerConstant.coll_singer);
                    }
                }
            }
            //保存 mongodb 爬取 歌曲列表
            rabbitTemplate.convertAndSend(DebuggerConstant.queue_song_list,msg);
        } catch (IOException exception) {
            LOGGER.error(exception.toString());
        }

    }

}
