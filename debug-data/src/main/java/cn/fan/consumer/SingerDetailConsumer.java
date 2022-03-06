package cn.fan.consumer;

import cn.fan.model.music.Singer;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author fanduanjin
 * @program orange-music
 * @Classname SingerDetailCounsumer
 * @Description
 * @Date 2022/3/6 19:49
 * @Created by fanduanjin
 */

@RabbitListener
public class SingerDetailConsumer {

    @Value("${apiHost}")
    String apiHost;

    @RabbitHandler
    public void debuggerSingerDetail(Singer singer) {
    }
}
