package cn.fan.distributelock.lock;

/**
 * @author fanduanjin
 * @program orange-music
 * @Classname LockTemplate
 * @Description
 * @Date 2022/3/9 21:58
 * @Created by fanduanjin
 */
public class LockTemplate {
    ILock lock;

    public LockTemplate(ILock lock){
        this.lock=lock;
    }

    public boolean lock(String lockName){
        return lock.lock(lockName);
    }

    public boolean unLock(String lockName){
        return lock.unLock(lockName);
    }
}

