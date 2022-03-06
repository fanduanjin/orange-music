package cn.fan.dao;

import cn.fan.model.music.Singer;

import java.util.List;

/**
 * @author fanduanjin
 * @program orange-music
 * @Classname SingerMapper
 * @Description
 * @Date 2021/12/7 20:50
 * @Created by fanduanjin
 */

public interface SingerMapper {

    /**
     * 添加一条记录
     *
     * @param entity
     * @return
     */
    int insert(Singer entity);

    /**
     * 根据主键删除记录
     *
     * @param pid
     * @return
     */
    int delete(int pid);

    /**
     * 根据主键更新记录
     *
     * @param entity
     * @return
     */
    int update(Singer entity);

    /**
     * 根据主键查询一条数据
     *
     * @param pid
     * @return
     */
    Singer selectOne(int pid);

    /**
     * 查询集合
     *
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<Singer> selectList(int startIndex, int pageSize);

    /**
     * 根据 id 获取数量
     * @param id
     * @return
     */
    int getCountById(int id);


    /**
     * 根据platId查询单条记录
     * @param id
     * @return
     */
    Singer selectOneById(int id);
}
