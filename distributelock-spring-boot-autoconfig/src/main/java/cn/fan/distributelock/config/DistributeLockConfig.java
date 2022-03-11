package cn.fan.distributelock.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author fanduanjin
 * @program orange-music
 * @Classname DistributeLockConfig
 * @Description
 * @Date 2022/3/9 19:56
 * @Created by fanduanjin
 */
@Data
@ConfigurationProperties(prefix = "lock")
public class DistributeLockConfig {

    private String zookeeperUrl="zookeeper://localhost:2181";

    private int retryMillis=3000;

    private String lockNameSpace="distribute-lock";

}
