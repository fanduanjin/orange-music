package cn.fan.consumer;

import cn.fan.api.music.ISingerService;
import cn.fan.model.constanst.DebuggerQueueName;
import cn.fan.model.music.Singer;
import cn.fan.model.music.SingerType;
import cn.fan.model.web.Promise;
import cn.fan.model.web.ResultModel;
import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author fanduanjin
 * @program orange-music
 * @Classname SingerDetailCounsumer
 * @Description
 * @Date 2022/3/6 19:49
 * @Created by fanduanjin
 */
@Component
@RabbitListener(queuesToDeclare = @Queue(DebuggerQueueName.SINGER_DETAIL_QUEUE))
public class SingerDetailConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SingerDetailConsumer.class);
    @Value(value = "${qm-api-host}")
    String apiHost;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @DubboReference
    ISingerService singerService;

    final String path = "/getSingerDetail?singerMid=";

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @RabbitHandler
    public void debuggerSingerDetail(Singer singer) throws JsonProcessingException {
        String url = apiHost + path + singer.getMid();
        String responseBody = HttpUtil.get(url);
        ResultModel<Singer> resultModel = objectMapper.readValue(responseBody,
                new TypeReference<ResultModel<Singer>>() {
                });
        Promise<Singer> promise = new Promise<>();
        promise.success(singer1 -> {
            handlerDebuggerSinger(singer1);
        }).fail(msg -> {
            LOGGER.warn("???????????????????????? " + msg);
        }).end(resultModel);
    }

    void handlerDebuggerSinger(Singer singer) {
        if (singer == null) {
            LOGGER.warn("debugger singer detail is failed");
            return;
        }
        Singer dbSinger = singerService.getById(singer.getId());
        if (dbSinger == null) {
            //??????
            if (singer.getType() != SingerType.Team.getCode()) {
                singerService.insert(singer);
                LOGGER.info("???????????? ????????????????????? ????????????");
            } else if (singer.getType() == SingerType.Team.getCode()) {
                singerService.insertTeam(singer);
                LOGGER.info("???????????? ????????????????????? ????????????");
                List<Singer> teamSingers = singer.getTeam();
                if (teamSingers != null && !teamSingers.isEmpty()) {
                    threadPoolTaskExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            teamSingers.forEach(singer1 -> {
                                rabbitTemplate.convertAndSend(DebuggerQueueName.SINGER_DETAIL_QUEUE, singer1);
                            });
                            LOGGER.info("???????????? ??????????????????");
                        }
                    });
                }

            }

        } else if (dbSinger.getType() != SingerType.Team.getCode() && !dbSinger.equals(singer)) {
            //??????????????? ??? ?????????????????? ?????????????????? ???????????????
            singer.setPid(dbSinger.getPid());
            singerService.modify(singer);
            LOGGER.info("???????????? ????????????????????? ????????????????????????");
        } else if (dbSinger.getType() == SingerType.Team.getCode()) {
            //???????????????
            singer.setPid(dbSinger.getPid());
            if (dbSinger.equals(singer)) {
                LOGGER.info("???????????????DB?????? ????????????");
            } else {
                //????????? ?????? ????????????
                singerService.modifyTeam(singer);
                LOGGER.info("???????????? ????????????????????? ????????????????????????");
            }

        } else {
            LOGGER.info("???????????????DB?????? ????????????");
        }
    }
}
