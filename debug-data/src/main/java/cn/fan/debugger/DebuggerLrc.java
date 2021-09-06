package cn.fan.debugger;

import cn.fan.model.music.Song;
import cn.fan.util.LrcResponseHandler;
import cn.fan.util.QqEncrypt;
import cn.fan.util.RequestTemplate;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DebuggerLrc {

    private static final String param_template = "{\"song_mid\":\"$song_mid\"}";
    private static final String param_song_mid = "$song_mid";
    private static final String group = "SongDetail";
    private static final String method = "get_song_detail_yqq";
    private static final String module = "music.pf_song_detail_svr";
    private static final String lrcPath = "https://c.y.qq.com/lyric/fcgi-bin/fcg_query_lyric_new.fcg?format=json";

    public String debugger(Song song) throws IOException {
        RequestTemplate requestTemplate = new RequestTemplate();
        String param = param_template.replace(param_song_mid, song.getMid());
        requestTemplate.setGroup(group);
        requestTemplate.setModule(module);
        requestTemplate.setMethod(method);
        requestTemplate.setParam(param);
        String data = requestTemplate.toString();
        String sing = QqEncrypt.getSign(data);
        String url = lrcPath + "&songmid=" + song.getMid();
        String lrc = LrcResponseHandler.getLrc(Jsoup.connect(url).
                header("referer", "https://y.qq.com/").execute());
        return lrc;
    }
}
