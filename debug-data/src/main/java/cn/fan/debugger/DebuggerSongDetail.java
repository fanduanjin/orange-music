package cn.fan.debugger;

import cn.fan.constant.ConfigConstant;
import cn.fan.util.RequestTemplate;
import cn.fan.model.common.Property;
import cn.fan.model.music.Song;
import cn.fan.util.LrcResponseHandler;
import cn.fan.util.QqEncrypt;
import cn.fan.util.ResponseHandler;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.dubbo.common.threadpool.ThreadPool;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Handler;

/**
 * @program: orange-music
 * @description: 爬取歌曲详情信息 每次爬取要发送多个请求，考虑forkjoin  excutor 解决？
 * @author: fanduanjin
 * @create: 2021-04-27 18:33
 * @
 */
@Component
public class DebuggerSongDetail {
    /**
     * {"comm":{"cv":4747474,"ct":24,"format":"json","inCharset":"utf-8","outCharset":"utf-8","notice":0,"platform":"yqq.json","needNewCode":1,"uin":0,"g_tk_new_20200303":5381,"g_tk":5381},"req_1":{"method":"get_song_detail_yqq","module":"music.pf_song_detail_svr","param":{"song_mid":"003sYHuC3aBd4r"}}}:
     */
    private static final String param_template = "{\"song_mid\":\"$song_mid\"}";
    private static final String param_song_mid = "$song_mid";
    private static final String group = "SongDetail";
    private static final String method = "get_song_detail_yqq";
    private static final String module = "music.pf_song_detail_svr";
    private static final Logger LOGGER = LoggerFactory.getLogger(DebuggerSongDetail.class);

    @Autowired
    DebuggerLrc debuggerLrc;

    @Autowired
    DebuggerMedialUrl debuggerMedialUrl;

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public Song debugger(Song song, ResultHandler resultHandler) {
        CompletableFuture<Song> songCompletableFuture = CompletableFuture.supplyAsync(() -> {
            Song result = null;
            try {
                result = debugger(song);
            } catch (IOException e) {
                LOGGER.info(e.toString());
                return null;
            }
            return result;
        }, threadPoolTaskExecutor);

        CompletableFuture<String> lrcCompletableFuture = CompletableFuture.supplyAsync(() -> {
            String result = null;
            try {
                result = debuggerLrc.debugger(song);
            } catch (IOException e) {
                LOGGER.error(e.toString());
                return "";
            }
            return result;
        }, threadPoolTaskExecutor);

        CompletableFuture<String> mediaUrlCompletableFuture = CompletableFuture.supplyAsync(() -> {
            String result = null;
            try {
                result = debuggerMedialUrl.debugger(song.getMid());
            } catch (IOException e) {
                LOGGER.error(e.toString());
                return "";
            }
            return result;
        }, threadPoolTaskExecutor);
        Song result = null;
        try {
            result = songCompletableFuture.get();
            result.setLrc(lrcCompletableFuture.get());
            String medialFilePaht = mediaUrlCompletableFuture.get();
        } catch (InterruptedException e) {
            LOGGER.error(e.toString());
            return null;
        } catch (ExecutionException e) {
            LOGGER.error(e.toString());
            return null;
        }
        resultHandler.handler();
        return result;
    }

    public Song debugger(Song song) throws IOException {
        RequestTemplate requestTemplate = new RequestTemplate();
        String param = param_template.replace(param_song_mid, song.getMid());
        requestTemplate.setGroup(group);
        requestTemplate.setModule(module);
        requestTemplate.setMethod(method);
        requestTemplate.setParam(param);
        String data = requestTemplate.toString();
        String sing = QqEncrypt.getSign(data);
        String url = ConfigConstant.baseUrl + "data=" + data + "&sign=" + sing;
        Connection connection = Jsoup.connect(url);
        Connection.Request request = connection.request();
        Connection.Response response = connection.execute();
        JSONObject jo_root = ResponseHandler.getData(request, response, group);
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
        songInfo.setMediaMid(song.getMediaMid());
        return songInfo;
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

    public interface ResultHandler {
        void handler();
    }

}

