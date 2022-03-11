package cn.fan.distributelock.config;

import cn.fan.distributelock.lock.ILock;
import cn.fan.distributelock.lock.LockTemplate;
import cn.fan.distributelock.lock.ZookeeperDistributedLock;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryForever;
import org.apache.curator.retry.RetryOneTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author fanduanjin
 * @program orange-music
 * @Classname DistributeLockAutoConfiguation
 * @Description
 * @Date 2022/3/9 20:02
 * @Created by fanduanjin
 */
@EnableConfigurationProperties(DistributeLockConfig.class)
@ComponentScan
@Configuration
public class DistributeLockAutoConfiguration {

    @Autowired
    DistributeLockConfig distributeLockConfig;


    @Bean
    CuratorFramework curatorFramework() {
        RetryPolicy retryPolicy = new RetryForever(distributeLockConfig.getRetryMillis());
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(distributeLockConfig.getZookeeperUrl())
                .namespace(distributeLockConfig.getLockNameSpace())
                .retryPolicy(retryPolicy)
                .build();
        client.start();
        return client;
    }

    @Bean
    ILock lock(@Autowired CuratorFramework curatorFramework) {
        ILock lock = new ZookeeperDistributedLock(curatorFramework);
        return lock;
    }

    @Bean
    @ConditionalOnClass(ZookeeperDistributedLock.class)
    LockTemplate lockTemplate(@Autowired ZookeeperDistributedLock lock) {
        return new LockTemplate(lock);
    }

}
