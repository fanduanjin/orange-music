package cn.fan.consumer;

import cn.fan.model.constanst.DebuggerConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-04-16 23:28
 */
@Component
@RabbitListener(queuesToDeclare = @Queue(DebuggerConstant.queue_singer_list))
public class SingerListConsumer  {
    private Logger logger= LoggerFactory.getLogger(SingerListConsumer.class);
    @RabbitHandler
    public void receivel(String message){
        logger.info(message);
    }
}
