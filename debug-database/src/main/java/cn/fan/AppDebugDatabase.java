package cn.fan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-04-16 23:24
 */
@SpringBootApplication
public class AppDebugDatabase {
    public static void main(String[] args) {
        SpringApplication.run(AppDebugDatabase.class,args);
        try {
            System.in.read();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
