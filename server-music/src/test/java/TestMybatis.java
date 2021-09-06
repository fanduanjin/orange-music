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
        long time=System.currentTimeMillis();
        singerService.getByPlatId(4607);
        System.out.println("使用时间 : "+(System.currentTimeMillis()-time));
    }
}
