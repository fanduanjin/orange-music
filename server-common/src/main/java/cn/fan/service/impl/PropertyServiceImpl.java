package cn.fan.service.impl;

import cn.fan.api.common.IPropertyService;
import cn.fan.dao.PropertyMapper;
import cn.fan.model.common.Property;
import com.alibaba.druid.pool.DruidDataSource;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-05-09 19:17
 */
@DubboService
public class PropertyServiceImpl implements IPropertyService {

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @Autowired
    private PropertyMapper propertyMapper;


    @Override
    public boolean insert(Property property) {
        return propertyMapper.insert(property) == 1;
    }

    @Override
    public boolean delete(int pid) {
        return propertyMapper.delete(pid) == 1;
    }

    @Override
    public List<Property> list(Class clazz, int entityId) {
        return propertyMapper.selectListByEntityTypeWithEntityId(clazz.getName(), entityId, clazz.getName().hashCode());
    }

    @Override
    public boolean update(Property property) {
        return propertyMapper.update(property) == 1;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean insertBatch(List<Property> properties) {
        if (properties == null || properties.isEmpty()) {
            throw new IllegalArgumentException("参数property不能为空");
        }
        PropertyMapper propertyMapper =
                sqlSessionFactory.openSession(ExecutorType.BATCH).getMapper(PropertyMapper.class);
        for (Property property : properties) {
            propertyMapper.insert(property);
        }
        return true;
    }
}
