package cn.fan.distributed.lock;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.io.IOException;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDubbo
public class AppDistributedLock {
    public static void main(String[] args) {
        SpringApplication.run(AppDistributedLock.class, args);
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
