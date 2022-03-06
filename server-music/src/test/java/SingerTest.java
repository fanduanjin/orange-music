import cn.fan.AppMusic;
import cn.fan.dao.SingerMapper;
import cn.fan.model.music.Singer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.util.Assert;

import java.io.IOException;

/**
 * @author fanduanjin
 * @program orange-music
 * @Classname SongTest
 * @Description
 * @Date 2022/3/2 19:18
 * @Created by fanduanjin
 */
@SpringBootTest(classes = AppMusic.class)
public class SingerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SingerMapper singerMapper;

    private int id = 999999999;
    private String singerJson = "{\n" +
            "        \"mid\": \"0025NhlN2yWrP4\",\n" +
            "        \"id\": 999999999,\n" +
            "        \"name\": \"周杰伦\",\n" +
            "        \"type\": 0,\n" +
            "        \"desc\": \"b4976654188f50a0ffbfc50a09ba7580\",\n" +
            "        \"area\": 0,\n" +
            "        \"genre\": 1,\n" +
            "        \"foreignName\": \"Jay Chou\",\n" +
            "        \"birthday\": \"1979-01-18\",\n" +
            "        \"wiki\": \"cc6d6daa93ab77b70d2ffdf0a60c97a8\",\n" +
            "        \"pic\": \"http://y.gtimg.cn/music/photo_new/T001R300x300M0000025NhlN2yWrP4.jpg\"\n" +
            "    }";
    ;


    @Test
    public void insert() throws JsonProcessingException {
        Singer singer = objectMapper.readValue(singerJson, Singer.class);
        int row = singerMapper.insert(singer);
        Assert.isTrue(row == 1, "插入失败");
    }

    @Test
    public void selectOneById() {
        Singer singer = singerMapper.selectOneById(id);
        Assert.notNull(singer, "没有查询到数据");
    }

    @Test
    public void selectOne() {
        Singer singer = singerMapper.selectOneById(id);
        int pid = singer.getPid();
        singer = singerMapper.selectOne(pid);
        Assert.notNull(singer, "没有查询到数据");
    }

    @Test
    public void update() {
        Singer singer = singerMapper.selectOneById(id);
        singer.setPic("test");
        int row = singerMapper.update(singer);
        Assert.isTrue(row > 0, "没有找到更新的数据");
    }

    @Test
    public void delete() {
        Singer singer = singerMapper.selectOneById(id);
        int row = singerMapper.delete(singer.getPid());
        Assert.isTrue(row > 0, "没有找到删除的数据");
    }


}
