package cn.fan.service.impl;

import cn.fan.api.common.IPropertyService;
import cn.fan.dao.PropertyMapper;
import cn.fan.model.common.PropertyExample;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-05-09 19:17
 */
@DubboService
public class PropetyService implements IPropertyService {

    @Autowired
    private PropertyMapper propertyMapper;
    @Override
    public int insert(Property property) {
        return propertyMapper.insert(property);
    }

    @Override
    public int remove(int id) {
        return propertyMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int removeByEntityId(int entityId) {
        PropertyExample propertyExample=new PropertyExample();
        propertyExample.createCriteria().andEntityIdEqualTo(entityId);
        return propertyMapper.deleteByExample(propertyExample);
    }

    @Override
    public int modify(Property property) {
        return propertyMapper.updateByPrimaryKey(property);
    }
}
