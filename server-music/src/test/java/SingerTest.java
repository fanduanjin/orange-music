import cn.fan.AppMusic;
import cn.fan.api.music.ISingerService;
import cn.fan.dao.SingerMapper;
import cn.fan.model.music.Singer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.List;

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

    @DubboReference
    ISingerService singerService;
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

    private String teamJson = "{\n" +
            "        \"mid\": \"002pUZT93gF4Cu\",\n" +
            "        \"id\": 9999999,\n" +
            "        \"name\": \"BEYOND\",\n" +
            "        \"type\": 2,\n" +
            "        \"desc\": \"d854e5e30991db47994df0f419e94236\",\n" +
            "        \"area\": 0,\n" +
            "        \"genre\": 2,\n" +
            "        \"foreignName\": \"\",\n" +
            "        \"birthday\": \"\",\n" +
            "        \"wiki\": \"113f924d931d7f33fcce41bb67faebd6\",\n" +
            "        \"team\": [\n" +
            "            {\n" +
            "                \"id\": 6657,\n" +
            "                \"name\": \"黄家强\",\n" +
            "                \"mid\": \"003Mou2l4HUaFM\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 13319,\n" +
            "                \"name\": \"黄家驹\",\n" +
            "                \"mid\": \"003bD7bY1MBaBg\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 249,\n" +
            "                \"name\": \"黄贯中\",\n" +
            "                \"mid\": \"0003GUsP2IfFAX\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 6641,\n" +
            "                \"name\": \"叶世荣\",\n" +
            "                \"mid\": \"0019vAKm07LaFx\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"pic\": \"http://y.gtimg.cn/music/photo_new/T001R300x300M000002pUZT93gF4Cu.jpg\"\n" +
            "    }";

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

    @Test
    public void insertTeam() throws JsonProcessingException {
        Singer team = objectMapper.readValue(teamJson, Singer.class);
        Assert.isTrue(singerService.insertTeam(team),"添加乐队失败");
    }

    @Test
    public void selectTeam() throws JsonProcessingException {
        Singer singer=singerService.getById(9999999);
        System.out.println(objectMapper.writeValueAsString(singer));
    }

    @Test
    public void modifyTeam() throws JsonProcessingException {
        Singer team = objectMapper.readValue(teamJson, Singer.class);
        Assert.isTrue(singerService.modifyTeam(team),"添加乐队失败");
    }

    @Test
    public void test(){
           List<Singer> singers= singerMapper.selectTeamSingers(9999999);
        System.out.println();
    }

}
