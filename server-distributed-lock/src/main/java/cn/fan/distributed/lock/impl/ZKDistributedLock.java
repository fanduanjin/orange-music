package cn.fan.distributed.lock.impl;

import cn.fan.api.lock.ILock;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@DubboService
@Configuration
public class ZKDistributedLock implements ILock {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZKDistributedLock.class);

    @Autowired
    private CuratorFramework curatorFramework;


    @Override
    public boolean lock(String path) {
        try {
            curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);
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
                .connectString("jlht.icu")
                .sessionTimeoutMs(3000)
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .namespace("zk_lock")
                .build();
        client.start();
        return client;
    }


    //private CuratorZookeeperTransporter curatorZookeeperTransporter;
    //private Map<String, Integer> point;
    //private Map<String, ZookeeperClient> zookeeperClientMap;

   /* @Autowired
    ConfigCenterBean configCenterBean;

    ZookeeperClient getZookeeperClient() {
        if (curatorZookeeperTransporter == null) {
            ExtensionLoader<ZookeeperTransporter> extensionLoader = ExtensionLoader.getExtensionLoader(ZookeeperTransporter.class);
            curatorZookeeperTransporter = (CuratorZookeeperTransporter) extensionLoader.getDefaultExtension();
          *//*  try {
                Field field = curatorZookeeperTransporter.getClass().getDeclaredField("zookeeperClientMap");
                field.setAccessible(true);
                zookeeperClientMap = (Map<String, ZookeeperClient>) field.get(curatorZookeeperTransporter);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }*//*
        }
        //获取一个zk url
        //String url= zookeeperClientMap.keySet().stream().findFirst().get();
        //ZookeeperClient zookeeperClient=zookeeperClientMap.get(url);


        ZookeeperClient zookeeperClient= curatorZookeeperTransporter.connect(URL.valueOf(configCenterBean.getAddress()));
        return zookeeperClient;
    }*/

}
