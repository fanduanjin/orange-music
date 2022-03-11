package cn.fan.api.music;

import cn.fan.model.music.Singer;

/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-04-17 16:35
 */
public interface ISingerService {

    /**
     * 插入
     *
     * @param singer
     * @return
     */
    boolean insert(Singer singer);

    /**
     * 添加一个乐队
     *
     * @param team
     * @return
     */
    boolean insertTeam(Singer team);

    /**
     * 删除
     *
     * @param pid
     * @return
     */
    boolean remove(int pid);

    /**
     * 修改
     *
     * @param singer
     * @return
     */
    boolean modify(Singer singer);

    /**
     * 修改某个团队信息
     * @param team
     * @return
     */
    boolean modifyTeam(Singer team);
    /**
     * 根据主键pid获取数据
     *
     * @param pid
     * @return
     */
    Singer get(int pid);

    /**
     * 根据平台 singer_id获取
     *
     * @param id
     * @return
     */
    Singer getById(int id);


}
