package cn.fan.distributelock.lock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author fanduanjin
 * @program orange-music
 * @Classname ZookeeperDistrubuteLock
 * @Description
 * @Date 2022/3/9 20:56
 * @Created by fanduanjin
 */


public class ZookeeperDistributedLock implements ILock {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperDistributedLock.class);

    CuratorFramework curatorFramework;


    public ZookeeperDistributedLock(CuratorFramework curatorFramework) {
        this.curatorFramework = curatorFramework;
    }

    @Override
    public boolean lock(String lockStr) {
        try {
            //创建一个临时节点
            curatorFramework.create().
                    creatingParentsIfNeeded().
                    withMode(CreateMode.EPHEMERAL).
                    forPath(splicingLockString(lockStr));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean unLock(String lockStr) {
        try {
            //删除节点
            curatorFramework.delete().
                    forPath(splicingLockString(lockStr));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return true;
    }

    String splicingLockString(String lockString) {
        return "/" + lockString;
    }

}
