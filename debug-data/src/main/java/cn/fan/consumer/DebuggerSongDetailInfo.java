package cn.fan.consumer;

import cn.fan.api.music.ISongService;
import cn.fan.constant.ConfigConstant;
import cn.fan.debugger.RequestTemplate;
import cn.fan.exception.ResponseHandlerException;
import cn.fan.model.common.Property;
import cn.fan.model.constanst.DebuggerConstant;
import cn.fan.model.music.Song;
import cn.fan.util.LrcResponseHandler;
import cn.fan.util.QqEncrypt;
import cn.fan.util.ResponseHandler;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.dubbo.config.annotation.DubboReference;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/**
 * @program: orange-music
 * @description: 爬取歌曲详情信息 每次爬取要发送多个请求，考虑forkjoin  excutor 解决？
 * @author: fanduanjin
 * @create: 2021-04-27 18:33
 * @
 */
@Component
@RabbitListener(queuesToDeclare = @Queue(DebuggerConstant.queue_song_detail))
public class DebuggerSongDetailInfo {
    /**
     * {"comm":{"cv":4747474,"ct":24,"format":"json","inCharset":"utf-8","outCharset":"utf-8","notice":0,"platform":"yqq.json","needNewCode":1,"uin":0,"g_tk_new_20200303":5381,"g_tk":5381},"req_1":{"method":"get_song_detail_yqq","module":"music.pf_song_detail_svr","param":{"song_mid":"003sYHuC3aBd4r"}}}:
     */
    private static final String param_template = "{\"song_mid\":\"$song_mid\"}";
    private static final String param_song_mid = "$song_mid";
    private static final String group = "SongDetail";
    private static final String method = "get_song_detail_yqq";
    private static final String module = "music.pf_song_detail_svr";
    private static final String lrcPath = "https://c.y.qq.com/lyric/fcgi-bin/fcg_query_lyric_new.fcg?format=json";
    private static final Logger LOGGER = LoggerFactory.getLogger(DebuggerSongDetailInfo.class);


    @Value("${spring.application.workerId}")
    private int workerId;
    @Value("${spring.application.datacenterId}")
    private int datacenterId;
    @DubboReference
    private ISongService songService;

    @RabbitHandler
    void receiver(String song_mid) throws IOException {
        RequestTemplate requestTemplate = new RequestTemplate();
        String param = param_template.replace(param_song_mid, song_mid);
        requestTemplate.setGroup(group);
        requestTemplate.setModule(module);
        requestTemplate.setMethod(method);
        requestTemplate.setParam(param);
        String data = requestTemplate.toString();
        String sing = QqEncrypt.getSign(data);
        String url = ConfigConstant.baseUrl + "data=" + data + "&sign=" + sing;
        Connection.Response response = Jsoup.connect(url).execute();
        JSONObject jo_root = ResponseHandler.getData(response, group);
        JSONObject jo_data = jo_root.getJSONObject("data");
        JSONObject jo_info = jo_data.getJSONObject("info");
        Song songInfo = new Song();
        List<Property> properties = parseProperties(jo_info);
        songInfo.setProperties(properties);
        JSONObject jo_track_info = jo_data.getJSONObject("track_info");
        songInfo.setTitle(jo_track_info.getString("title"));
        songInfo.setName(jo_track_info.getString("name"));
        songInfo.setSubTitle(jo_track_info.getString("subtitle"));
        JSONObject jo_album = jo_track_info.getJSONObject("album");
        if (jo_album != null) {
            songInfo.setAlbumPlatId(jo_album.getInteger("id"));
        }
        JSONObject jo_mv = jo_track_info.getJSONObject("mv");
        if (jo_mv != null) {
            songInfo.setMvid(jo_mv.getString("vid"));
        }
        JSONObject jo_pay = jo_track_info.getJSONObject("pay");
        if (jo_pay != null) {
            Boolean pay_down = jo_pay.getBoolean("pay_down");
            Boolean pay_play = jo_pay.getBoolean("pay_play");
            songInfo.setPricePlay(pay_down || pay_play);
        }
        songInfo.setLanguage(jo_track_info.getInteger("language"));
        songInfo.setGenre(jo_track_info.getIntValue("genre"));
        songInfo.setPublicTime(jo_track_info.getDate("time_public"));
        songInfo.setType(jo_track_info.getIntValue("type"));
        songInfo.setPlatId(jo_track_info.getInteger("id"));
        songInfo.setMid(jo_track_info.getString("mid"));
        JSONObject jo_file = jo_track_info.getJSONObject("file");
        songInfo.setMediaMid(jo_file.getString("media_mid"));
        //开始爬取歌词 base64编码 不用解码
        url = lrcPath + "&songmid=" + song_mid;
        String lrc = LrcResponseHandler.getLrc(Jsoup.connect(url).
                header("referer", "https://y.qq.com/").execute());
        songInfo.setLrc(lrc);
        Song song = songService.getByPlatId(songInfo.getPlatId());
        if (song == null) {
            //没有插入过
            long resourceId = IdUtil.getSnowflake(workerId, datacenterId).nextId();
            songInfo.setMediaResourceId(resourceId);
            songService.insert(songInfo);
            //爬取音频文件
            getMediaUrl(song_mid);
        } else {
            //插入过  判断是否修改，修改则 更新
            if (songInfoIsModify(song, songInfo)) {
                //修改过
                songService.update(songInfo);
            }
        }
    }

    List<Property> parseProperties(JSONObject jo_info) {
        //便利每个jsonobject 取出 title value
        Property property;
        List<Property> properties = new ArrayList<>();
        for (Map.Entry<String, Object> entry : jo_info.entrySet()) {
            JSONObject jo_property = (JSONObject) entry.getValue();
            property = new Property();
            property.setKey(jo_property.getString("title"));
            JSONArray ja_content = jo_property.getJSONArray("content");
            JSONObject jo_content = ja_content.getJSONObject(0);
            property.setValue(jo_content.getString("value"));
            properties.add(property);
        }
        return properties;
    }

    boolean songInfoIsModify(Song db_song, Song song) {
        long resourceId = db_song.getMediaResourceId();
        int id = db_song.getId();
        db_song.setMediaResourceId(0L);
        db_song.setId(0);
        song.setMediaResourceId(0L);
        song.setId(0);
        List<Property> source_properties = db_song.getProperties();
        db_song.setProperties(new ArrayList<Property>());
        for (Property property : source_properties) {
            Property property1 = new Property();
            property1.setKey(property.getKey());
            property1.setValue(property.getValue());
            db_song.getProperties().add(property1);
        }
        String json_db_song = JSON.toJSONString(db_song);
        String json_song = JSON.toJSONString(song);
        //改回 原来的数据
        db_song.setId(id);
        db_song.setMediaResourceId(resourceId);
        song.setId(id);
        song.setMediaResourceId(resourceId);
        return !json_db_song.equals(json_song);
    }


    private static final String mediaFile_group = "MediaFile";
    private static final String mediaFile_method = "CgiGetVkey";
    private static final String mediaFile_module = "vkey.GetVkeyServer";
    private static final String mediaFile_param_template = "{ \"guid\": \"$guid\",\"songmid\": [\"$songmid\"]}";
    private static final String mediaFile_param_songmid = "$songmid";
    private static final String mediaFile_param_guid = "$guid";
    private static final String mediaFile_host = "http://dl.stream.qqmusic.qq.com/";

    String getMediaUrl(String songMid) throws IOException {
        RequestTemplate requestTemplate = new RequestTemplate();
        requestTemplate.setGroup(mediaFile_group);
        requestTemplate.setMethod(mediaFile_method);
        requestTemplate.setModule(mediaFile_module);
        String param = mediaFile_param_template.replace(mediaFile_param_guid, RandomUtil.randomNumbers(10))
                .replace(mediaFile_param_songmid, songMid);

        requestTemplate.setParam(param);
        String data = requestTemplate.toString();
        String sign = QqEncrypt.getSign(data);
        String url = "https://u.y.qq.com/cgi-bin/musics.fcg?sign=" + sign;
        Connection.Response response = Jsoup.connect(url).method(Connection.Method.POST).requestBody(data).execute();
        JSONObject result = ResponseHandler.getData(response, mediaFile_group);
        JSONObject jo_data = result.getJSONObject("data");
        JSONArray ja_midurlinfo = jo_data.getJSONArray("midurlinfo");
        if (ja_midurlinfo != null && !ja_midurlinfo.isEmpty()) {
            JSONObject jo_midurlinfo = ja_midurlinfo.getJSONObject(0);
            String mediaUrl = jo_midurlinfo == null ? "" : jo_midurlinfo.getString("purl");
            //如果url为空 或者没有直接返回 ，前端 以 未有音频文件处理
            if (mediaUrl == null || mediaUrl.isEmpty())
                return "";
            System.out.println(mediaFile_host + mediaUrl);
            return mediaFile_host + mediaUrl;
        } else {
            return "";
        }
    }
}

