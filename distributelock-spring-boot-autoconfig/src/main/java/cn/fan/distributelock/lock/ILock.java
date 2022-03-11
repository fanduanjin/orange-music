package cn.fan.distributelock.lock;

/**
 * @author fanduanjin
 * @program orange-music
 * @Classname ILock
 * @Description
 * @Date 2022/3/9 20:56
 * @Created by fanduanjin
 */
public interface ILock {

    /**
     * 上锁
     * @param lockStr
     * @return
     */
    boolean lock(String lockStr);

    /**
     * 解锁
     * @param lockStr
     * @return
     */
    boolean unLock(String lockStr);
}
