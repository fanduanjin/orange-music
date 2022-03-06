package cn.fan.dao;

import cn.fan.model.music.Song;

import java.util.List;

/**
 * @author fanduanjin
 * @program orange-music
 * @Classname SongMapper
 * @Description
 * @Date 2021/12/7 21:04
 * @Created by fanduanjin
 */

public interface SongMapper {

    /**
     * 添加一条记录
     *
     * @param entity
     * @return
     */
    int insert(Song entity);

    /**
     * 根据主键删除记录
     *
     * @param id
     * @return
     */
    int delete(int pid);

    /**
     * 根据主键更新记录
     *
     * @param entity
     * @return
     */
    int update(Song entity);

    /**
     * 根据主键查询一条数据
     *
     * @param pid
     * @return
     */
    Song selectOne(int pid);

    /**
     * 查询集合
     *
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<Song> selectList(int startIndex, int pageSize);

    /**
     * 根据platId 查询 单条记录
     * @param id
     * @return
     */
    Song selectOneById(int id);
}
