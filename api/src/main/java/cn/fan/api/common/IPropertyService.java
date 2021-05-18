package cn.fan.api.common;

import cn.fan.model.common.Property;

/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-05-09 19:10
 */
public interface IPropertyService {
    /**
     * 插入
     * @param property
     * @return
     */
    int insert(Property property);

    /**
     * 根据 实体.ID 删除
     * @param entityId
     * @return
     */
    int remove(int id);

    int removeByEntityId(int entityId);

    int modify(Property property);

}
