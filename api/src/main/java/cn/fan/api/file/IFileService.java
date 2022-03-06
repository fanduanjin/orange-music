package cn.fan.api.file;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-04-17 22:22
 */
public interface IFileService {

    /**
     * 上传文件
     *
     * @param bytes
     * @param fileSuffix
     * @return 返回空 就是上传失败
     */
    String uploadFile(byte[] bytes, String fileSuffix) ;

    /**
     * 删除文件
     * @param path
     */
    boolean deleteFile(String path) ;

    /**
     * 判断文件是否存在
     * @param path
     * @return
     */
    boolean existFile(String path);
}
