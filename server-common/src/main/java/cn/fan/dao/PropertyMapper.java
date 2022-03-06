package cn.fan.dao;

import cn.fan.model.common.Property;

import java.util.List;

/**
 * @author fanduanjin
 * @program orange-music
 * @Classname PropertyMapper
 * @Description
 * @Date 2021/12/6 20:45
 * @Created by fanduanjin
 */

public interface PropertyMapper {

    /**
     * 添加一条记录
     *
     * @param entity
     * @return
     */
    int insert(Property entity);

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
    int update(Property entity);

    /**
     * 根据主键查询一条数据
     *
     * @param pid
     * @return
     */
    Property selectOne(int pid);


    /**
     * 根据 实体类型和id 查询列表
     *
     * @param entityType
     * @param entityId
     * @param entityTypeCrc entityType hashcode
     * @return
     */
    List<Property> selectListByEntityTypeWithEntityId(String entityType, int entityId, int entityTypeCrc);

    /**
     * 根据 实体类型和id 删除记录
     *
     * @param entityType
     * @param entityId
     * @param entityTypeCrc entityType hashcode
     * @return
     */
    int deleteByEntityTypeWithEntityId(String entityType, int entityId, int entityTypeCrc);




}
