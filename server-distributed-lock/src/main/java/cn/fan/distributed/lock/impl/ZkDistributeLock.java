package cn.fan.distributed.lock.impl;

import cn.fan.api.lock.ILock;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-04-16 15:36
 */
@DubboService
@Configuration
public class ZkDistributeLock implements ILock {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZkDistributeLock.class);

    @Autowired
    private CuratorFramework curatorFramework;


    @Override
    public boolean lock(String path) {
        try {
            //curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);
            curatorFramework.create().creatingParentsIfNeeded().forPath(path);
        } catch (Exception e) {
            LOGGER.error(e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean unlock(String path) {
        try {
            curatorFramework.delete().forPath(path);
        } catch (Exception e) {
            LOGGER.error(e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    @Bean
    CuratorFramework curatorFramework() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("r730.host")
                .sessionTimeoutMs(3000)
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .namespace("zk_lock")
                .build();
        client.start();
        return client;
    }

}
