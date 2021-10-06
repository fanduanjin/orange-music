package cn.fan.debugger;

import cn.fan.constant.ConfigConstant;
import cn.fan.model.music.Singer;
import cn.fan.util.QqEncrypt;
import cn.fan.util.RequestTemplate;
import cn.fan.util.ResponseHandler;
import cn.hutool.core.date.format.FastDateFormat;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: orange-music
 * @description: 爬取歌手详情信息
 * @author: fanduanjin
 * @create: 2021-05-07 22:42
 */
@Component
public class DebuggerSingerDetail {
    private static final Logger LOGGER = LoggerFactory.getLogger(DebuggerSingerDetail.class);


    private static final String param_template = "{\"singer_mids\": [\"$singer_mid\"],\"ex_singer\": 1,\"wiki_singer\": 1,\"group_singer\": 1,\"pic\": 1,\"photos\": 1}";
    private static final String method = "GetSingerDetail";
    private static final String module = "musichall.singer_info_server";
    private static final String group = "getSingerDetail";
    private static final String param_singer_mid = "$singer_mid";

    public Singer debugger(String singer_mid) throws IOException, ParseException {
        String param = param_template.replace(param_singer_mid, singer_mid);
        RequestTemplate requestTemplate = new RequestTemplate();
        requestTemplate.setParam(param);
        requestTemplate.setMethod(method);
        requestTemplate.setModule(module);
        requestTemplate.setGroup(group);
        String data = requestTemplate.toString();
        String sign = QqEncrypt.getSign(data);
        String url = ConfigConstant.baseUrl + "sign=" + sign + "&data=" + data;
        Connection connection = Jsoup.connect(url);
        Connection.Request request = connection.request();
        Connection.Response response = connection.execute();
        //处理响应数据
        JSONObject jo_root = ResponseHandler.getData(request, response, group);
        if (jo_root == null)
            return null;
        JSONObject jo_data = jo_root.getJSONObject("data");
        JSONArray ja_singerList = jo_data.getJSONArray("singer_list");
        //由于一次性只获取一个 歌手详细 直接获取 数组坐标0就可以 有可能返回空数据
        if (ja_singerList.isEmpty())
            return null;
        JSONObject jo_singerDetail = ja_singerList.getJSONObject(0);
        Singer singer = handlerSingerDetail(jo_singerDetail);
        return singer;
    }

    Singer handlerSingerDetail(JSONObject jo_singerDetail) throws ParseException {
        Singer singer = new Singer();
        //获取基础信息
        JSONObject jo_basic_info = jo_singerDetail.getJSONObject("basic_info");
        singer.setMid(jo_basic_info.getString("singer_mid"));
        singer.setPlatId(jo_basic_info.getIntValue("singer_id"));
        singer.setName(jo_basic_info.getString("name"));
        singer.setType(jo_basic_info.getIntValue("type"));
        // ex_info
        JSONObject jo_ex_info = jo_singerDetail.getJSONObject("ex_info");
        singer.setArea(jo_ex_info.getIntValue("area"));
        singer.setDesc(jo_ex_info.getString("desc"));
        singer.setGenre(jo_ex_info.getIntValue("genre"));
        singer.setForeignName(jo_ex_info.getString("foreign_name"));
        try {
            singer.setBirthday(jo_ex_info.getDate("birthday"));
        } catch (Exception e) {
            //时间转换 可能有bug 201/01/01
            String date=jo_ex_info.getString("birthday").replaceAll("/","-");
            Date birthday= FastDateFormat.getInstance("YY-MM-DD").parse(date);
            singer.setBirthday(birthday);
        }
        //wiki 处理 wiki
        singer.setWiki(jo_singerDetail.getString("wiki"));
        //头像处理pic
        JSONObject jo_pic = jo_singerDetail.getJSONObject("pic");
        //获取头像 url
        String pic_url = jo_pic.getString("pic");
        pic_url = pic_url == null ? "" : pic_url;
        //将300x300图片 替换为800x800高清图
        pic_url = pic_url.replace("300x300", "800x800");
        singer.setPic(pic_url);
        return singer;
    }


}
