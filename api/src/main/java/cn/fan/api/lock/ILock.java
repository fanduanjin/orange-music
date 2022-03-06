package cn.fan.api.lock;
/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-04-16 15:36
 */
public interface ILock {

    /**
     * 加锁
     * @param str
     * @return
     */
    boolean lock(String str);

    /**
     * 解锁
     * @param str
     * @return
     */
    boolean unlock(String str);
}
