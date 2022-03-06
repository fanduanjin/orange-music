package cn.fan.util;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import io.minio.MinioClient;
import io.minio.ObjectStat;
import io.minio.PutObjectOptions;
import io.minio.errors.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

/**
 * @author fanduanjin
 * @program orange-music
 * @Classname MinioUtil
 * @Description
 * @Date 2021/10/16 18:39
 * @Created by fanduanjin
 */

@Component
public class MinioUtil {

    private final Logger LOGGER = LoggerFactory.getLogger(MinioUtil.class);

    /**
     * 每小时清理一次
     */
    private TimedCache<String, Boolean> bucketNameCache = CacheUtil.newTimedCache(1000 * 60 * 60);

    @Autowired
    MinioClient minioClient;

    /**
     * 上传文件
     *
     * @param bytes
     * @param fileSuffix
     * @return
     * @throws InvalidBucketNameException
     * @throws InsufficientDataException
     * @throws ErrorResponseException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws InvalidResponseException
     * @throws XmlParserException
     * @throws InternalException
     * @throws RegionConflictException
     */
    public String uploadFile(byte[] bytes, String fileSuffix) throws InvalidBucketNameException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException, RegionConflictException {
        LocalDateTime localDateTime = LocalDateTime.now();
        //根据 年月 生成 桶 202101
        String bucketName = String.valueOf(localDateTime.getYear()) + localDateTime.getMonthValue();
        //根据 日 加 时 生成文件路径
        String path = String.valueOf(localDateTime.getDayOfMonth()) + localDateTime.getHour();
        //UUID作为文件名称
        String fileName = IdUtil.randomUUID();
        String objectName = path + "/" + fileName + "." + fileSuffix;
        ByteArrayInputStream byteArrayOutputStream = new ByteArrayInputStream(bytes);
        PutObjectOptions putObjectOptions = new PutObjectOptions(byteArrayOutputStream.available(), -1);
        createBucket(bucketName);
        minioClient.putObject(bucketName, objectName, byteArrayOutputStream, putObjectOptions);
        return bucketName + "/" + objectName;
    }

    public void deleteFile(String path) throws InvalidBucketNameException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String bucketName = StrUtil.subBefore(path, '/', false);
        String objectName = StrUtil.subAfter(path, '/', false);
        minioClient.removeObject(bucketName, objectName);
    }

    public boolean existFile(String path) throws InvalidBucketNameException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String bucketName = StrUtil.subBefore(path, '/', false);
        String objectName = StrUtil.subAfter(path, '/', false);
        ObjectStat objectStat = null;
        try {
            objectStat = minioClient.statObject(bucketName, objectName);
        } catch (ErrorResponseException e) {
            LOGGER.warn(e.toString());
        }

        return objectStat != null;
    }

    /**
     * 创建 bucket
     *
     * @param bucketName
     * @throws InvalidBucketNameException
     * @throws InsufficientDataException
     * @throws ErrorResponseException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws InvalidResponseException
     * @throws XmlParserException
     * @throws InternalException
     * @throws RegionConflictException
     */
    private void createBucket(String bucketName) throws InvalidBucketNameException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException, RegionConflictException {
        Boolean isCreate = bucketNameCache.get(bucketName);
        if (isCreate == null) {
            isCreate = minioClient.bucketExists(bucketName);
        }
        if (isCreate == false) {
            minioClient.makeBucket(bucketName);
            isCreate = true;
            bucketNameCache.put(bucketName, isCreate);
        }
    }

}
