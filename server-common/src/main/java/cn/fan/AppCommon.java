package cn.fan;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-05-09 19:29
 */
@SpringBootApplication
@MapperScan("cn.fan.dao")
@EnableDubbo
public class AppCommon {
    public static void main(String[] args) {
        SpringApplication.run(AppCommon.class);
        try {
            System.in.read();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
