package cn.fan.api.singer;

import cn.fan.model.Singer.Singer;

/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-04-17 16:35
 */
public interface ISingerService {

    /**
     * 插入
     * @param singer
     * @return
     */
    int insert(Singer singer);

    /**
     *
     * @param id
     * @return
     */
    int remove(int id);

    /**
     *
     * @param singer
     * @return
     */
    int modify(Singer singer);

    /**
     *
     * @param id
     * @return
     */
    Singer get(int id);

}
