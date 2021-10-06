package cn.fan.api.file;

import java.io.InputStream;

/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-04-17 22:22
 */
public interface IFileService {

    public String uploadFile(byte[] bytes,String fileSuffix);

    void deleteFile(String path);
}
