package cn.fan.debugger;

import cn.fan.util.QqEncrypt;
import cn.fan.util.RequestTemplate;
import cn.fan.util.ResponseHandler;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DebuggerMedialUrl {

    private static final String mediaFile_group = "MediaFile";
    private static final String mediaFile_method = "CgiGetVkey";
    private static final String mediaFile_module = "vkey.GetVkeyServer";
    private static final String mediaFile_param_template = "{ \"guid\": \"$guid\",\"songmid\": [\"$songmid\"]}";
    private static final String mediaFile_param_songmid = "$songmid";
    private static final String mediaFile_param_guid = "$guid";
    private static final String mediaFile_host = "http://dl.stream.qqmusic.qq.com/";

    public String debugger(String songMid) throws IOException {
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
        Connection connection=Jsoup.connect(url);
        Connection.Request request=connection.request();
        Connection.Response response = connection.method(Connection.Method.POST).requestBody(data).execute();
        JSONObject result = ResponseHandler.getData(request,response, mediaFile_group);
        JSONObject jo_data = result.getJSONObject("data");
        JSONArray ja_midurlinfo = jo_data.getJSONArray("midurlinfo");
        if (ja_midurlinfo != null && !ja_midurlinfo.isEmpty()) {
            JSONObject jo_midurlinfo = ja_midurlinfo.getJSONObject(0);
            String mediaUrl = jo_midurlinfo == null ? "" : jo_midurlinfo.getString("purl");
            //如果url为空 或者没有直接返回 ，前端 以 未有音频文件处理
            if (mediaUrl == null || mediaUrl.isEmpty())
                return "";
            return mediaFile_host + mediaUrl;
        } else {
            return "";
        }
    }
}
