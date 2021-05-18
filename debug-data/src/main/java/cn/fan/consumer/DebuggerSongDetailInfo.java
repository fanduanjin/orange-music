package cn.fan.consumer;

import cn.fan.constant.ConfigConstant;
import cn.fan.debugger.RequestTemplate;
import cn.fan.model.common.Property;
import cn.fan.model.constanst.DebuggerConstant;
import cn.fan.model.music.Song;
import cn.fan.util.QqEncrypt;
import cn.fan.util.ResponseHandler;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        JSONObject jo_info=jo_data.getJSONObject("info");
        Song song=new Song();
        List<Property> properties= parseProperties(jo_info);
        JSONObject jo_track_info=jo_data.getJSONObject("track_info");
        song.setTitle(jo_track_info.getString("title"));
        song.setName(jo_track_info.getString("name"));
        song.setSubTitle(jo_track_info.getString("subtitle"));
        JSONObject jo_album=jo_track_info.getJSONObject("album");
        if(jo_album!=null) {
            song.setAlbumPlatId(jo_album.getInteger("id"));
        }
        JSONObject jo_mv=jo_track_info.getJSONObject("mv");
        if(jo_mv!=null){
            song.setMvid(jo_mv.getString("vid"));
        }
        JSONObject jo_pay=jo_track_info.getJSONObject("pay");
        if(jo_pay!=null){
            Boolean pay_down=jo_pay.getBoolean("pay_down");
            Boolean pay_play=jo_pay.getBoolean("pay_play");
            song.setPricePlay(pay_down||pay_play);
        }
        song.setLanguage(jo_track_info.getInteger("language"));
        song.setGenre(jo_track_info.getIntValue("genre"));
        song.setPublicTime(jo_track_info.getDate("time_public"));
        song.setType(jo_track_info.getIntValue("type"));
        song.setPlatId(jo_track_info.getInteger("id"));
        song.setMid(jo_track_info.getString("mid"));
        JSONObject jo_file=jo_track_info.getJSONObject("file");
        song.setMediaMid(jo_file.getString("media_mid"));
        LOGGER.info("test:"+ JSON.toJSONString(song));
    }

    List<Property> parseProperties(JSONObject jo_info){
        //便利每个jsonobject 取出 title value
        Property property;
        List<Property> properties=new ArrayList<>();
        for(Map.Entry<String, Object> entry : jo_info.entrySet()){
            JSONObject jo_property=(JSONObject) entry.getValue();
            property=new Property();
            property.setKey(jo_property.getString("title"));
            JSONArray ja_content=jo_property.getJSONArray("content");
            JSONObject jo_content=ja_content.getJSONObject(0);
            property.setValue(jo_content.getString("value"));
            properties.add(property);
        }
        return properties;
    }


}

