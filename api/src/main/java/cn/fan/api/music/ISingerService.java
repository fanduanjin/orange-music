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
     * @param singer
     * @return
     */
    int insert(Singer singer);

    /**
     * 删除
     * @param id
     * @return
     */
    int remove(int id);

    /**
     * 修改
     * @param singer
     * @return
     */
    int modify(Singer singer);

    /**
     * 根据主键id获取数据
     * @param id
     * @return
     */
    Singer get(int id);

    /**
     * 根据平台 singer_id获取
     * @param platId
     * @return
     */
    Singer getByPlatId(int platId);

    /**
     * 根据 plat_id 判断数据是否存在
     * @param platId
     * @return
     */
    boolean existsByPlayId(int platId);

}
