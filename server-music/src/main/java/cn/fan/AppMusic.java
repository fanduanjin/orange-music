package cn.fan;

import org.apache.dubbo.common.serialize.support.SerializationOptimizer;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.ObjectMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

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
