import cn.fan.AppFile;
import cn.fan.api.file.IFileService;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.security.RunAs;
import java.nio.charset.StandardCharsets;

/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-04-18 00:39
 */

@SpringBootTest(classes = AppFile.class)
public class FileServiceTest {

    @DubboReference
    IFileService fileService;

    @Test
    public void upload(){
        String path= fileService.uploadFile("fdsfs".getBytes(StandardCharsets.UTF_8),"txt");
    }
}
