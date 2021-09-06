package cn.fan.consumer;


import cn.fan.api.music.ISingerService;
import cn.fan.debugger.DebuggerSingerDetail;
import cn.fan.debugger.DebuggerSingerList;
import cn.fan.model.constanst.DebuggerConstant;
import cn.fan.model.music.Singer;
import cn.fan.model.music.Song;
import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.xml.bind.ValidationEvent;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Component
public class DebuggerConsumer {

    static final Logger LOGGER=LoggerFactory.getLogger(DebuggerConsumer.class);

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    DebuggerSingerList debuggerSingerList;

    @Autowired
    DebuggerSingerDetail debuggerSingerDetail;

    @DubboReference
    ISingerService singerService;

    //@Scheduled(fixedDelay=30000*1000 )
    public void startDebuggerSingers(){
        try {
            debuggerSingerList.debugger(new DebuggerSingerList.ResultHandler() {
                @Override
                public void handler(List<Singer> singers) {
                    for(Singer singer: singers){
                        rabbitTemplate.convertAndSend(DebuggerConstant.queue_singer_list,singer);
                    }
                }
            });
        } catch (IOException e) {
            LOGGER.error(e.toString());
        }
    }

    @RabbitListener(queuesToDeclare = @Queue(DebuggerConstant.queue_singer_list))
    void consumerSongList(Singer singer){
        try {
            singer=debuggerSingerDetail.debugger(singer.getMid());
            //singer 为空 爬取失败
            if(singer==null)
                return;
            Singer db_singer=singerService.getByPlatId(singer.getPlatId());
            if(db_singer!=null)
                return;
            singerService.insert(singer);
            LOGGER.info("未存在歌手 持久化数据库"+singer.getName());
        } catch (IOException | ParseException e) {
            LOGGER.error(e.toString());
        }
    }

}
