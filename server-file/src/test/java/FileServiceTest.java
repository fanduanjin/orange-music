import cn.fan.AppFile;
import cn.fan.api.file.IFileService;
import cn.fan.api.file.IResourceService;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import io.minio.MinioClient;
import io.minio.errors.*;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.time.LocalDateTime;

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

    @DubboReference
    IResourceService resourceService;

    @Autowired
    MinioClient minioClient;

    @Test
    public void upload() {
        long startTime=System.currentTimeMillis();
        String resPath = "https://m801.music.126.net/20211016193359/8163658ea106ca4bfa7e7b2ba0c975f5/jdyyaac/obj/w5rDlsOJwrLDjj7CmsOj/9244508655/a4d8/caa9/71d9/999a37ebbe128fb6b5c422f1ec1670f0.m4a";
        byte[] bytes = HttpUtil.downloadBytes(resPath);
        String fileSuffix = FileUtil.getSuffix(resPath);
        String path = fileService.uploadFile(bytes, fileSuffix);
        System.out.println(path);
        System.out.println("总用时"+(System.currentTimeMillis()-startTime));
    }

    @Test
    public void stringParseDate() throws ParseException {
        String path = "group1/M00/00/00/wKgAZmFa-VqAaE2LAAAABeUKP4c681.tx1t";
        fileService.existFile(path);
    }

    @Test
    public void deleteFile() {
        String path = "2031/35/c3dcf5d1-8ab6-45ac-8e11-ea4c694978a4.m4a";
        fileService.deleteFile(path);
    }

    @Test
    public void existFile() {
        String path = "1202110/1619/e3791bc0-dbb9-4a32-aedf-677389be996a.m4a";
        System.out.println(fileService.existFile(path));
    }

    @Test
    public void testUploadMinio() throws IOException, InvalidBucketNameException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        System.out.println(LocalDateTime.now());
        System.out.println("年 " + LocalDateTime.now().getYear());
        System.out.println("月 " + LocalDateTime.now().getMonthValue());
        System.out.println("日 " + LocalDateTime.now().getDayOfMonth());
        System.out.println("时 " + LocalDateTime.now().getHour());
        String sourcePath="http://dl.stream.qqmusic.qq.com/C400001BaZtr3jrkpe.m4a?guid=5198084873&vkey=9038FB36C4478362B38111416278ACDA4C6435244DF9EC057ECCDA7BA3ED2FCCA66FEB81DB584660EF8BB95B8357EA0BAF48BB66A74D8301&uin=&fromtag=999";
        String oldPath = StrUtil.subBefore(sourcePath, '?', false);
        System.out.println(oldPath);
    }

}
