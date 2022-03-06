package cn.fan.config.minio;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program orange-music
 * @Classname MinioConfigurationProperties
 * @Description
 * @Date 2021/10/16 16:19
 * @Created by fanduanjin
 * @author fanduanjin
 */

@Component
@Data
@ConfigurationProperties(prefix = "minio")
public class MinioConfigurationProperties {
    private String endpoint;
    private String accessKey;
    private String secretKey;
}
