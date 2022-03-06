package cn.fan.service.impl;

import cn.fan.api.file.IFileService;
import cn.fan.util.MinioUtil;
import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import io.minio.errors.*;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-04-17 22:27
 */

@DubboService
public class FileServiceImpl implements IFileService {
    private static Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);


    @Autowired
    private IFileService fileService;

    @Autowired
    private MinioUtil minioUtil;

    @Override
    public String uploadFile(byte[] bytes, String fileSuffix) {
        String result = null;
        try {
            result = minioUtil.uploadFile(bytes, fileSuffix);
        } catch (Exception e) {
            LOGGER.error(e.toString());
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean deleteFile(String path) {
        try {
            minioUtil.deleteFile(path);
            return true;
        } catch (Exception e) {
            LOGGER.error(e.toString());
        }
        return false;
    }

    @Override
    public boolean existFile(String path) {
        try {
            return minioUtil.existFile(path);
        } catch (Exception e) {
            LOGGER.error(e.toString());
        }
        return false;
    }


}
