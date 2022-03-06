import cn.fan.model.music.Singer;
import cn.fan.model.music.Song;
import cn.fan.model.web.ResultModel;
import cn.hutool.core.util.PageUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.log.StaticLog;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.List;

/**
 * @author fanduanjin
 * @program orange-music
 * @Classname TestQmApi
 * @Description 测试 aa音乐api
 * @Date 2022/3/4 12:28
 * @Created by fanduanjin
 */
public class TestQmApi {
    private static final String API_HOST = "786793.top";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void getSingerList() throws IOException {
        int total, debugCount = 0;
        String baseUrl = API_HOST + "/getSingerList?pageIndex=";
        String resBody = HttpUtil.get(baseUrl + 1);
        ResultModel<List<Singer>> result = OBJECT_MAPPER.readValue(resBody, ResultModel.class);
        total = result.getTotal();
        debugCount += result.getData().size();
        int pageCount = PageUtil.totalPage(total, 80);
        for (int i = 2; i <= pageCount; i++) {
            resBody = HttpUtil.get(baseUrl + i);
            result = OBJECT_MAPPER.readValue(resBody, ResultModel.class);
            debugCount += result.getData().size();
            StaticLog.info(resBody);
        }
        Assert.isTrue(total == debugCount, "爬取数量 与 实际数量不等");
        StaticLog.info("应该爬取总数 {}，实际爬取总数{}", total, debugCount);
    }

    @Test
    public void getSingerDetail() throws IOException {
        String url = API_HOST + "/getSingerDetail?singerMid=0025NhlN2yWrP4";
        String resBody = HttpUtil.get(url);
        ResultModel<Singer> result = OBJECT_MAPPER.readValue(resBody, ResultModel.class);
        Assert.notNull(result.getData(), "爬取失败 当前没有爬取到数据");
        StaticLog.info(resBody);
    }

    @Test
    public void getSongList() throws IOException {
        int total, debugCount = 0;
        String baseUrl = API_HOST + "/getSongList?singerMid=003Nz2So3XXYek&pageIndex=";
        String resBody = HttpUtil.get(baseUrl + 1);
        ResultModel<List<Song>> result = OBJECT_MAPPER.readValue(resBody, ResultModel.class);
        total = result.getTotal();
        debugCount += result.getData().size();
        int pageCount = PageUtil.totalPage(total, 10);
        for (int i = 2; i <= pageCount; i++) {
            resBody = HttpUtil.get(baseUrl + i);
            result = OBJECT_MAPPER.readValue(resBody, ResultModel.class);
            debugCount += result.getData().size();
            StaticLog.info(resBody);
        }
        Assert.isTrue(total == debugCount, "爬取数量 与 实际数量不等");
        StaticLog.info("应该爬取总数 {}，实际爬取总数{}", total, debugCount);
    }

    @Test
    public void getSongDetail() throws IOException {
        String url = API_HOST + "/getSongDetail?songMid=003cI52o4daJJL";
        String resBody = HttpUtil.get(url);
        ResultModel<Song> result = OBJECT_MAPPER.readValue(resBody, ResultModel.class);
        Assert.notNull(result.getData(), "爬取失败 当前没有爬取到数据");
        StaticLog.info(resBody);
    }

    @Test
    public void getAlbumList(){

    }

}
