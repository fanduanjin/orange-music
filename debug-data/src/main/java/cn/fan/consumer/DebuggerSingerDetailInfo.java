package cn.fan.consumer;

import cn.fan.api.file.IFileService;
import cn.fan.model.constanst.DebuggerConstant;
import cn.fan.model.debugger.SingerInfo;
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
import org.springframework.stereotype.Component;

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
    private static final Logger LOGGER= LoggerFactory.getLogger(DebuggerSingerDetailInfo.class);

    public static final String baseUrl="https://y.qq.com/n/ryqq/singer/";

    @DubboReference
    private IFileService fileService;

    @RabbitHandler
    public void receivel(SingerInfo msg){
        try {
            Document document=Jsoup.connect(baseUrl+msg.getSinger_mid()).get();
            Elements elements= document.getElementsByClass("popup_data_detail__cont");
            //歌手简介div
            msg.setSinger_desc(elements.html());
            Connection.Response response=Jsoup.connect(msg.getSinger_pic()).ignoreContentType(true).execute();
            String path=fileService.uploadFile(response.bodyAsBytes());
            LOGGER.info("上传文件成功:"+path);
        } catch (IOException exception) {
            LOGGER.error(exception.toString());
        }
    }

}
