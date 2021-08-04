import cn.fan.api.lock.ILock;
import cn.fan.distributed.lock.AppDistributedLock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.config.annotation.DubboReference;

import org.apache.dubbo.config.spring.ConfigCenterBean;
import org.apache.dubbo.remoting.zookeeper.ZookeeperClient;

import org.apache.dubbo.remoting.zookeeper.ZookeeperTransporter;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
@SpringBootTest(classes = AppDistributedLock.class)
public class TestDistributedLock {

    @DubboReference
    ILock lock;


}
