package cn.fan.api.music;

import cn.fan.model.music.Song;
/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-04-16 15:36
 */
public interface ISongService {

    /**
     * 插入
     * @param song
     * @return
     */
    int insert(Song song);

    /**
     * 删除
     * @param id
     * @return
     */
    int remove(int id);

    /**
     * 修改
     * @param song
     * @return
     */
    int update(Song song);

    /**
     * 根据主键id 获取
     * @param id
     * @return
     */
    Song get(int id);


    /**
     * 根据plat_id获取
     * @param platId
     * @return
     */
    Song getByPlatId(int platId);
}
