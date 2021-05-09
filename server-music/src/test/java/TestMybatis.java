import cn.fan.AppMusic;
import cn.fan.api.music.ISingerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-05-08 19:59
 */
@SpringBootTest(classes = AppMusic.class)
public class TestMybatis {

    @Autowired
    ISingerService singerService;

    @Test
    public void test(){
        singerService.get(0);
        System.out.println("success");
    }
}
