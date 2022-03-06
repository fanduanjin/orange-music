import cn.fan.AppMusic;
import cn.fan.dao.SongMapper;
import cn.fan.model.music.Song;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.handler.codec.http.HttpUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.io.IOException;

/**
 * @author fanduanjin
 * @program orange-music
 * @Classname SongTest
 * @Description
 * @Date 2022/3/5 19:49
 * @Created by fanduanjin
 */

@SpringBootTest(classes = AppMusic.class)
public class SongTest {

    private static final String SONG_JSON = "{\n" +
            "        \"properties\": [\n" +
            "            {\n" +
            "                \"key\": \"唱片公司\",\n" +
            "                \"value\": \"杰威尔音乐有限公司\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"key\": \"歌曲流派\",\n" +
            "                \"value\": \"Pop\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"key\": \"简介\",\n" +
            "                \"value\": \"「情歌被打败，爱已不存在」谁说住冲绳才可以唱出这种鼻腔共鸣的海岛唱腔？这首充满岛国风情的「花海」，周杰伦就以这种海岛唱腔演唱，有种抚慰人心的神奇力量，这首「花海」，歌词由两位杰威尔的同事合力完成，也是周杰伦给公司同事的福利，开放一首歌让公司有创作兴趣的的同事比稿，由古小力和黄凌嘉两位的歌词脱颖而出，将两人的歌词精华结合成一首唯美的情歌。谁说俄罗斯人才可以飙高音？这次周杰伦在这首歌里也挑战了超高音；这首英式摇滚抒情，结合了民族风，散发出一丝悠悠的花香，媲美经典的七里香，也势必造成潮流经典K歌！\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"key\": \"歌曲语种\",\n" +
            "                \"value\": \"国语\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"key\": \"发行时间\",\n" +
            "                \"value\": \"2008-10-15\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"extraName\": \"\",\n" +
            "        \"id\": 449198,\n" +
            "        \"mid\": \"003cI52o4daJJL\",\n" +
            "        \"title\": \"花海\",\n" +
            "        \"subTitle\": \"\",\n" +
            "        \"singers\": [\n" +
            "            {\n" +
            "                \"id\": 4558,\n" +
            "                \"mid\": \"0025NhlN2yWrP4\",\n" +
            "                \"name\": \"周杰伦\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"albumId\": 36062,\n" +
            "        \"albumMid\": \"002Neh8l0uciQZ\",\n" +
            "        \"mvId\": 199394,\n" +
            "        \"mvMid\": \"r00127x0yzd\",\n" +
            "        \"publicTime\": \"2008-10-15\",\n" +
            "        \"lrc\": \"82282517f0e5e0ee5cb94f402d23909a\",\n" +
            "        \"mediaUrl\": \"\"\n" +
            "    }";

    private final int songId = 449198;


    @Autowired
    private SongMapper songMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void insert() throws JsonProcessingException {
        Song song = objectMapper.readValue(SONG_JSON, Song.class);
        int row = songMapper.insert(song);
        Assert.isTrue(row == 1, "插入失败");
    }

    @Test
    public void selectOneById() {
        Song song = songMapper.selectOneById(songId);
        Assert.notNull(song, "未查询到Song");
    }

    @Test
    public void selectOne() {
        Song song = songMapper.selectOneById(songId);
        song = songMapper.selectOneById(song.getId());
        Assert.notNull(song, "未查询到数据");
    }

    @Test
    public void update() {
        Song song = songMapper.selectOneById(songId);
        song.setMid("test");
        int row = songMapper.update(song);
        Assert.isTrue(row > 0, "修改失败");
    }

    @Test
    public void delete() {
        Song song = songMapper.selectOneById(songId);
        int row = songMapper.delete(song.getPid());
        Assert.isTrue(row > 0, "删除失败");
    }

}
