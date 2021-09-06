package cn.fan;

import cn.fan.model.constanst.DebuggerConstant;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;

/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-04-16 22:47
 */

@SpringBootApplication
@EnableScheduling
public class AppDebugData {

    public static void main(String[] args) {
        SpringApplication.run(AppDebugData.class,args);
        try {
            System.in.read();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }



}
