package cn.fan.config.minio;

import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author fanduanjin
 * @program orange-music
 * @Classname MinioConfiguration
 * @Description
 * @Date 2021/10/16 16:33
 * @Created by fanduanjin
 */

@Configuration
public class MinioConfiguration {

    @Autowired
    MinioConfigurationProperties minioConfigurationProperties;

    @Bean
    MinioClient minioClient() throws InvalidPortException, InvalidEndpointException {
        MinioClient minioClient=new MinioClient(minioConfigurationProperties.getEndpoint(),
                minioConfigurationProperties.getAccessKey(),
                minioConfigurationProperties.getSecretKey());
        return minioClient;
    }
}
