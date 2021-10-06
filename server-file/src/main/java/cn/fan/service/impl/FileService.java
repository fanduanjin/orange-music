package cn.fan.service.impl;

import ch.qos.logback.classic.LoggerContext;
import cn.fan.api.file.IFileService;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.upload.FastFile;
import com.github.tobato.fastdfs.service.DefaultFastFileStorageClient;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-04-17 22:27
 */

@DubboService
public class FileService implements IFileService {
    private static Logger logger =LoggerFactory.getLogger(FileService.class);

    @Autowired
    private FastFileStorageClient storageClient;

    @Override
    public String uploadFile(byte[] bytes,String fileSuffix){
        ByteArrayInputStream byteArrayOutputStream=new ByteArrayInputStream(bytes);
        StorePath storePath= storageClient.uploadFile(byteArrayOutputStream,Long.valueOf(bytes.length),fileSuffix,null);
        logger.info("上传成功:"+storePath.getFullPath());
        return storePath.getFullPath();
    }

    @Override
    public void deleteFile(String path) {
        storageClient.deleteFile(path);
    }
}
