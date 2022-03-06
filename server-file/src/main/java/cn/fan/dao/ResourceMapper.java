package cn.fan.dao;

import cn.fan.model.file.Resource;

import java.util.List;

/**
 * @author fanduanjin
 * @program orange-music
 * @Classname ResourceMapper
 * @Description
 * @Date 2021/12/7 20:20
 * @Created by fanduanjin
 */

public interface ResourceMapper {

    /**
     * 添加一条记录
     *
     * @param entity
     * @return
     */
    int insert(Resource entity);

    /**
     * 根据主键删除记录
     *
     * @param id
     * @return
     */
    int delete(int id);

    /**
     * 根据主键更新记录
     *
     * @param entity
     * @return
     */
    int update(Resource entity);

    /**
     * 根据主键查询一条数据
     *
     * @param id
     * @return
     */
    Resource selectOne(int id);

    /**
     * 查询集合
     *
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<Resource> selectList(int startIndex, int pageSize);

    /**
     * 根据雪花id查询
     * @param resourceId
     * @return
     */
    Resource selectOneByResourceId(long resourceId);

    /**
     * 根据 resourceId删除
     * @param resourceId
     * @return
     */
    int deleteByResourceId(long resourceId);
}
