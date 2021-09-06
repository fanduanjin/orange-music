package cn.fan.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LrcResponseHandler {
    private final  static Logger LOGGER= LoggerFactory.getLogger(LrcResponseHandler.class);
    public static String getLrc(Connection.Response response) {
        JSONObject jo_result = JSON.parseObject(response.body());
        int resultCode=jo_result.getInteger("code");
        if(resultCode==-1901){
            //1901状态码 暂无歌词， 此状态码待确定
            LOGGER.warn("this song not lrc");
            return "";
        }
        if (resultCode!= 0) {
            LOGGER.error("get lrc faild !" + response.body());
            return "";
        }
        LOGGER.info(response.body());
        return jo_result.getString("lyric");
    }
}
