package cn.fan.consumer;

import cn.fan.model.constanst.DebuggerConstant;
import cn.fan.model.debugger.SongInfo;
import com.alibaba.fastjson.JSON;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Iterator;

/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-04-27 18:33
 */
@Component
@RabbitListener(queuesToDeclare = @Queue(DebuggerConstant.queue_song_detail))
public class DebuggerSongDetailInfo {
    /**
     * {"comm":{"cv":4747474,"ct":24,"format":"json","inCharset":"utf-8","outCharset":"utf-8","notice":0,"platform":"yqq.json","needNewCode":1,"uin":0,"g_tk_new_20200303":5381,"g_tk":5381},"req_1":{"method":"get_song_detail_yqq","module":"music.pf_song_detail_svr","param":{"song_mid":"003sYHuC3aBd4r"}}}:
     */
    private final String url_template = "https://y.qq.com/n/yqq/song/$song_mid.html";
    private final String param_song_mid = "$song_mid";

    private static final Logger LOGGER = LoggerFactory.getLogger(DebuggerSongDetailInfo.class);

    @RabbitHandler
    void reactive(SongInfo msg) {
        String url = url_template.replace(param_song_mid, msg.getMid());
        try {
            Document document = Jsoup.connect(url).get();
            Elements data_info_ul= document.getElementsByClass("data__info");
            //解析ul标签
            //handlerProperty(data_info_ul.iterator());
            LOGGER.info(document.html());
        } catch (IOException exception) {
            LOGGER.error(exception.getMessage());
        }
    }

    void handlerProperty(Iterator iterator){
        for(;iterator.hasNext();){
            Element element=(Element) iterator.next();
            LOGGER.info(element.text());
        }
    }
}

