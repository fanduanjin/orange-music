import cn.fan.AppDebugData;
import cn.fan.consumer.SingerListConsumer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * @author fanduanjin
 * @program orange-music
 * @Classname TestStartDebugger
 * @Description
 * @Date 2022/3/6 21:28
 * @Created by fanduanjin
 */

@SpringBootTest(classes = AppDebugData.class)
public class TestStartDebugger {
    @Autowired
    SingerListConsumer singerListConsumer;

    @Test
    void start() throws IOException {
        singerListConsumer.startDebuggerSingerList();
    }
}
