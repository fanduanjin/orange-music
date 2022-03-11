package cn.fan;

import org.apache.dubbo.common.serialize.support.SerializationOptimizer;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-05-09 19:29
 */
@SpringBootApplication
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
