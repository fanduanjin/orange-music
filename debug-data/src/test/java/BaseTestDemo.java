
import cn.fan.debugger.DebuggerSingerList;
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
        try {
            Connection.Response response=Jsoup.connect("http://y.qq.com/music/photo_new/T001R300x300M000001MXQUi1tlLon.jpg?max_age=2592000").execute();
            byte[] bytes= response.bodyAsBytes();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }
}
