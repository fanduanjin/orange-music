
import cn.fan.debugger.DebuggerSingerList;
import cn.fan.debugger.RequestTemplate;
import cn.fan.model.music.Singer;
import cn.fan.util.QqEncrypt;
import cn.hutool.core.util.RandomUtil;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class BaseTestDemo {
    /**
     *   https://y.qq.com/portal/singer_list.html#page=2&index=1&
     *   头像大小 800 300 150
     */



    @Test
    public void TestSongLrc() throws IOException {
        StringBuilder path=new StringBuilder("https://c.y.qq.com/lyric/fcgi-bin/fcg_query_lyric_new.fcg");
        path.append("?songmid=004Fs2FP1EvZYc&format=json");

        String result=Jsoup.connect(path.toString()).header("referer","https://y.qq.com/").execute().body();
        System.out.println(result);
    }

    @Test
    public void upLoadAudio() throws IOException {
        RequestTemplate requestTemplate=new RequestTemplate();
        requestTemplate.setGroup("req1");
        requestTemplate.setMethod("CgiGetVkey");
        requestTemplate.setModule("vkey.GetVkeyServer");
        requestTemplate.setParam("{ \"guid\": \""+ RandomUtil.randomNumbers(10) +"\",\"songmid\": [\"003dANGA3aX7c4\"]}\n");
        String data=requestTemplate.toString();
        String sign= QqEncrypt.getSign(data);
        String url="https://u.y.qq.com/cgi-bin/musics.fcg?sign="+sign;
        String result= Jsoup.connect(url).method(Connection.Method.POST).requestBody(data).execute().body();
        System.out.println(result);
    }

}
