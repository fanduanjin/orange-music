
import cn.fan.debugger.DebuggerSingerList;
import cn.fan.model.music.Singer;
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
    public void TestSingerList(){
        System.out.println(Singer.class.hashCode());
    }


}
