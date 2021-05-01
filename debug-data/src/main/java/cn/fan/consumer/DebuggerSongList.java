package cn.fan.consumer;

import cn.fan.constant.ConfigConstant;
import cn.fan.debugger.RequestTemplate;
import cn.fan.model.constanst.DebuggerConstant;
import cn.fan.model.debugger.SingerInfo;
import cn.fan.model.debugger.SongInfo;
import cn.fan.util.QqEncrypt;
import cn.fan.util.ResponseHandler;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;

/**
 * @program: orange-music
 * @description: 爬取歌手列表
 * @author: fanduanjin
 * @create: 2021-04-18 15:01
 */

@Component
@RabbitListener(queuesToDeclare = @Queue(DebuggerConstant.queue_song_list))
public class DebuggerSongList {
    private static final String group = "singerSongList";
    private static final String method = "GetSingerSongList";
    private static final String module = "musichall.song_list_server";
    private static final String param_template = "{\"order\":1,\"singerMid\":\"$singerMid\",\"begin\":$begin,\"num\":$num}";
    private static final String param_num = "$num";
    private static final String param_begin = "$begin";
    private static final String param_singerMid = "$singerMid";
    private static final int pageNum = 10;

    private static final Logger logger = LoggerFactory.getLogger(DebuggerSongList.class);
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @RabbitHandler
    void reactive(SingerInfo msg) {
        RequestTemplate requestTemplate = new RequestTemplate();
        requestTemplate.setGroup(group);
        requestTemplate.setModule(module);
        requestTemplate.setMethod(method);
        try {
            for (int i = 1, len = 1; i <= len; i++) {
                requestTemplate.setParam(buildParam(msg.getSinger_mid(), i));
                String data = requestTemplate.toString();
                String sign = QqEncrypt.getSign(data);
                String url = ConfigConstant.baseUrl + "sign=" + sign + "&data=" + data;
                Connection.Response response = Jsoup.connect(url).execute();
                JSONObject root = ResponseHandler.getData(response, group);
                JSONObject jso_data = root.getJSONObject("data");
                //获取总页数
                if(len==1) {
                    len = jso_data.getIntValue("totalNum");
                    len = ResponseHandler.computePageTotal(len, pageNum);
                }
                //处理爬取的 歌曲
                JSONArray jsa_songList = jso_data.getJSONArray("songList");
                handlerSongList(jsa_songList);
            }
        } catch (Exception exception) {
            logger.error(exception.getMessage());
        }
    }

    String buildParam(String singerMid, int pageIndex) {
        String param = param_template.replace(param_singerMid, singerMid);
        param = param.replace(param_begin, String.valueOf((pageIndex - 1) * pageNum));
        param = param.replace(param_num, String.valueOf(pageNum));
        return param;
    }

    void handlerSongList(JSONArray jsonArray) {
        JSONObject jso_song = null;
        JSONObject jso_songInfo = null;
        SongInfo songInfo=new SongInfo();
        for (Iterator iterator = jsonArray.iterator(); iterator.hasNext(); ) {
            jso_song = (JSONObject) iterator.next();
            jso_songInfo = jso_song.getJSONObject("songInfo");
            songInfo.setId(jso_songInfo.getString("id"));
            songInfo.setMid(jso_songInfo.getString("mid"));
            songInfo.setSubTitle(jso_songInfo.getString("subtitle"));
            songInfo.setTitle(jso_songInfo.getString("title"));
            songInfo.setTime_public(jso_songInfo.getString("time_public"));
            //获取专辑id
            JSONObject jso_album=jso_songInfo.getJSONObject("album");
            songInfo.setAlbum_id(jso_album.getString("id"));
            //获取完 提交到mq 爬取 歌曲详情信息
            rabbitTemplate.convertAndSend(DebuggerConstant.queue_song_detail,songInfo);
        }
    }

}
