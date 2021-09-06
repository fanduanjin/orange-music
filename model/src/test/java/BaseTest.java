import cn.fan.model.music.Singer;
import org.junit.jupiter.api.Test;

public class BaseTest {

    @Test
    public void testEqualsObject(){
        Singer singer=new Singer();
        singer.setName("singer");
        Singer singer1=new Singer();
        singer1.setName("singer");
        System.out.println(singer==singer1);
    }
}
