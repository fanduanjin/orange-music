package cn.fan;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-04-17 21:57
 */
@SpringBootApplication
@EnableDubbo

public class AppFile {
    public static void main(String[] args) {
        SpringApplication.run(AppFile.class,args);
        try {
            System.in.read();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
