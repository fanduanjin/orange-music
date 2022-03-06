package cn.fan;

import cn.fan.model.music.Singer;
import cn.fan.model.web.ResultModel;
import cn.hutool.http.HttpUtil;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.IOException;
import java.util.List;

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
        SpringApplication.run(AppDebugData.class, args);

        try {
            System.in.read();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
