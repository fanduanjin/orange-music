
import cn.fan.AppDebugData;
import cn.fan.api.music.ISingerService;
import cn.fan.debugger.*;
import cn.fan.model.music.Song;
import cn.fan.util.RequestTemplate;
import cn.fan.model.constanst.DebuggerConstant;
import cn.fan.model.music.Singer;
import cn.fan.util.QqEncrypt;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;
import org.apache.dubbo.config.annotation.DubboReference;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootTest(classes = AppDebugData.class)
public class BaseTestDemo {
    @Autowired
    DebuggerSingerList debuggerSingerList;

    @Autowired
    DebuggerSingerDetail debuggerSingerDetail;

    @Autowired
    DebuggerSongList debuggerSongList;

    @Autowired
    DebuggerSongDetail debuggerSongDetail;

    @Autowired
    DebuggerMedialUrl debuggerMedialUrl;

    @Autowired
    DebuggerLrc debuggerLrc;

    @DubboReference
    ISingerService singerService;
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void TestSongLrc() throws IOException {
        StringBuilder path = new StringBuilder("https://c.y.qq.com/lyric/fcgi-bin/fcg_query_lyric_new.fcg");
        path.append("?songmid=004Fs2FP1EvZYc&format=json");

        String result = Jsoup.connect(path.toString()).header("referer", "https://y.qq.com/").execute().body();
        System.out.println(result);
    }

    @Test
    public void upLoadAudio() throws IOException {
        RequestTemplate requestTemplate = new RequestTemplate();
        requestTemplate.setGroup("req1");
        requestTemplate.setMethod("CgiGetVkey");
        requestTemplate.setModule("vkey.GetVkeyServer");
        requestTemplate.setParam("{ \"guid\": \"" + RandomUtil.randomNumbers(10) + "\",\"songmid\": [\"003dANGA3aX7c4\"]}\n");
        String data = requestTemplate.toString();
        String sign = QqEncrypt.getSign(data);
        String url = "https://u.y.qq.com/cgi-bin/musics.fcg?sign=" + sign;
        String result = Jsoup.connect(url).method(Connection.Method.POST).requestBody(data).execute().body();
        System.out.println(result);
    }

    ConcurrentHashMap<String,String> concurrentHashMap=new ConcurrentHashMap<>();
    @Test
    public void debuggerSingerList() throws IOException {
        debuggerSingerList.debugger(new DebuggerSingerList.ResultHandler() {
            @Override
            public void handler(List<Singer> singers) {
                for(Singer singer:singers){
                    concurrentHashMap.put(String.valueOf(singer.getPlatId()),"");
                }
            }
        });
        System.out.println("dfsdfs  " +concurrentHashMap.size());
    }

    @Test
    public void debuggerSingerListByPage() throws IOException {
        debuggerSingerList.debugger(1);
        debuggerSingerList.debugger(2);
    }

    @Test
    public void debuggerSingerDetail() throws IOException, ParseException {
        String singer_mid = "000OG0wK0vlC0D";
        Singer singer= debuggerSingerDetail.debugger(singer_mid);
        Singer singer_db=singerService.getByPlatId(singer.getPlatId());
        singer.setId(singer_db.getId());
        System.out.println(singer_db.equals(singer));
    }

    @Test
    public void debuggerSingerWithSingerDetail() throws IOException {
        debuggerSingerList.debugger(new DebuggerSingerList.ResultHandler() {
            @SneakyThrows
            @Override
            public void handler(List<Singer> singers) {
                for (Singer singer : singers) {
                    debuggerSingerDetail.debugger(singer.getMid());
                }
            }
        });
    }

    @Test
    public void debuggerSongList() throws IOException {
        String singer_mid="000aw4WC2EQYTv";
        Singer singer=new Singer();
        singer.setMid(singer_mid);
        singer.setName("张靓颖");
        debuggerSongList.debugger(singer, new DebuggerSongList.ResultHandler() {
            @Override
            public void handler(List<Song> songs) {
                System.out.println(JSON.toJSONString(songs));
            }
        });
    }

    @Test
    public void debuggerSongDetail() throws IOException {
        String song_mid="002FnVLT088mc8";
        Song song=new Song();
        song.setMid(song_mid);
        Song song1= debuggerSongDetail.debugger(song, new DebuggerSongDetail.ResultHandler() {
            @Override
            public void handler(Song result) {
            }
        });
        System.out.println(JSON.toJSONString(song1));
    }

    @Test
    public  void debuggerLrc() throws IOException {
        String song_mid="000EmeJq28cbh5";
        Song song=new Song();
        song.setMid(song_mid);
        String lrc= debuggerLrc.debugger(song);
    }

    @Test
    public  void debuggerMediaUlr() throws IOException {
        String song_mid="002sjtbA1K4ASF";
        debuggerMedialUrl.debugger(song_mid);
    }



}
