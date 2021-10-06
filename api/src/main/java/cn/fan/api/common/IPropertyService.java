package cn.fan.api.common;

import cn.fan.model.common.Property;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-05-09 19:10
 */
public interface IPropertyService extends IService<Property> {
    /**
     * 插入
     * @param property
     * @return
     */
    int insert(Property property);

    int remove(int id);

    int removeByEntityId(Class clazz, int entityId);

    List<Property> list(Class clazz,int entityId);

    int modify(Property property);

    boolean modifyBatch(List<Property> properties);

    boolean insertBatch(List<Property> properties);
}
