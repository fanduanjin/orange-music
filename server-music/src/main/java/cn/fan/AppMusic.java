package cn.fan;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;

/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-05-08 18:52
 */
@SpringBootApplication
@EnableDubbo
@MapperScan("cn.fan.dao")
@EnableTransactionManagement
public class AppMusic {
    public static void main(String[] args) {
        SpringApplication.run(AppMusic.class,args);
        try {
            System.in.read();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
