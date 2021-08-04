package cn.fan.api.lock;

public interface ILock {

    boolean lock(String str);

    boolean unlock(String str);
}
