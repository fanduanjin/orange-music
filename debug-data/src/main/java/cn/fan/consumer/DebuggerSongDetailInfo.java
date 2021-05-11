package cn.fan.consumer;

import cn.fan.constant.ConfigConstant;
import cn.fan.debugger.RequestTemplate;
import cn.fan.model.constanst.DebuggerConstant;
import cn.fan.util.QqEncrypt;
import cn.fan.util.ResponseHandler;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-04-27 18:33
 */
@Component
@RabbitListener(queuesToDeclare = @Queue(DebuggerConstant.queue_song_detail))
public class DebuggerSongDetailInfo {
    /**
     * {"comm":{"cv":4747474,"ct":24,"format":"json","inCharset":"utf-8","outCharset":"utf-8","notice":0,"platform":"yqq.json","needNewCode":1,"uin":0,"g_tk_new_20200303":5381,"g_tk":5381},"req_1":{"method":"get_song_detail_yqq","module":"music.pf_song_detail_svr","param":{"song_mid":"003sYHuC3aBd4r"}}}:
     */
    private static final String param_template = "{\"song_mid\":\"$song_mid\"}";
    private static final String param_song_mid = "$song_mid";
    private static final String group="SongDetail";
    private static final String method="get_song_detail_yqq";
    private static final String module="music.pf_song_detail_svr";

    private static final Logger LOGGER = LoggerFactory.getLogger(DebuggerSongDetailInfo.class);

    @RabbitHandler
    void receiver(String song_mid) throws IOException {
        RequestTemplate requestTemplate=new RequestTemplate();
        String param=param_template.replace(param_song_mid,song_mid);
        requestTemplate.setGroup(group);
        requestTemplate.setModule(module);
        requestTemplate.setMethod(method);
        requestTemplate.setParam(param);
        String data=requestTemplate.toString();
        String sing= QqEncrypt.getSign(data);
        String url= ConfigConstant.baseUrl+"data="+data+"&sign="+sing;
        Connection.Response response=Jsoup.connect(url).execute();
        JSONObject jo_root = ResponseHandler.getData(response,group);
        JSONObject jo_data=jo_root.getJSONObject("data");

    }


}

