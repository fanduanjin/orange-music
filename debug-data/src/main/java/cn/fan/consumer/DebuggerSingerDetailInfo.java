package cn.fan.consumer;

import cn.fan.model.constanst.DebuggerConstant;
import cn.fan.model.debugger.SingerInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-04-17 14:08
 */

@Component
@RabbitListener(queuesToDeclare = @Queue(DebuggerConstant.queue_singer_detail))
public class DebuggerSingerDetailInfo {
    private static final Logger LOGGER= LoggerFactory.getLogger(DebuggerSingerDetailInfo.class);


    public static final String baseUrl="https://y.qq.com/n/ryqq/singer/";

    @RabbitHandler
    public void receivel(SingerInfo msg){
        try {
            Document document=Jsoup.connect(baseUrl+msg.getSinger_mid()).get();
            Elements elements= document.getElementsByClass("popup_data_detail__cont");
            LOGGER.info(elements.html());
        } catch (IOException exception) {
            LOGGER.error(exception.toString());
        }
    }

}
