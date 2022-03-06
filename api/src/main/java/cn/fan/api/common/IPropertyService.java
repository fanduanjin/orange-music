package cn.fan.api.common;

import cn.fan.model.common.Property;


import java.util.List;
import java.util.Map;

/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-05-09 19:10
 */
public interface IPropertyService {
    /**
     * 插入
     *
     * @param property
     * @return
     */
    boolean insert(Property property);

    /**
     * 根据主键pid删除
     *
     * @param pid
     * @return
     */
    boolean delete(int pid);

    /**
     * 根据实体 类型and主键 获取列表
     *
     * @param clazz
     * @param entityId
     * @return
     */
    List<Property> list(Class clazz, int entityId);

    /**
     * 修改
     *
     * @param property
     * @return
     */
    boolean update(Property property);

    /**
     * 批量插入
     *
     * @param properties
     * @return
     */
    boolean insertBatch(List<Property> properties);
}
