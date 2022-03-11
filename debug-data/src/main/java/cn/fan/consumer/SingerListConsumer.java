package cn.fan.consumer;

import cn.fan.model.constanst.DebuggerQueueName;
import cn.fan.model.music.Singer;
import cn.fan.model.web.Promise;
import cn.fan.model.web.ResultModel;

import cn.hutool.core.util.PageUtil;
import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author fanduanjin
 * @program orange-music
 * @Classname SingerListConsumer
 * @Description
 * @Date 2022/3/6 20:00
 * @Created by fanduanjin
 */

@Component
public class SingerListConsumer {

    private static Logger LOGGER = LoggerFactory.getLogger(SingerListConsumer.class);

    @Value(value = "${qm-api-host}")
    private String apiHost;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    private static final String PATH = "/getSingerList";

    private final int pageSize = 80;

    public void startDebuggerSingerList() throws IOException {
        String baseUrl = apiHost + PATH + "?pageIndex=";
        //先爬取一页，顺便获得 total 总数
        String responseBody = HttpUtil.get(baseUrl + 1);
        ResultModel<List<Singer>> result = objectMapper.readValue(responseBody,
                new TypeReference<ResultModel<List<Singer>>>() {
                });
        Promise<List<Singer>> promise = new Promise();
        boolean success = promise.success(singers -> {
            sendToSingerDetailQueue(singers);
        }).fail(msg -> {
            LOGGER.warn("获取歌手列表失败 " + msg);
        }).end(result);
        if (success) {
            int pageTotal = PageUtil.totalPage(result.getTotal(), pageSize);
            for (int i = 1; i <= pageTotal; i++) {
                responseBody = HttpUtil.get(baseUrl + i);
                result = objectMapper.readValue(responseBody, new TypeReference<ResultModel<List<Singer>>>() {
                });
                promise.end(result);
            }
        }

    }

    void sendToSingerDetailQueue(List<Singer> singers) {
        if (singers == null || singers.isEmpty()) {
            throw new IllegalArgumentException("参数 [singers] 不能为空");
        }
        threadPoolTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                singers.forEach(singer -> {
                    rabbitTemplate.convertAndSend(DebuggerQueueName.SINGER_DETAIL_QUEUE, singer);
                    LOGGER.info(DebuggerQueueName.SINGER_DETAIL_QUEUE + singer.getName());
                });
            }
        });


    }

}



