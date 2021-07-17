package cn.fan.service.impl;

import cn.fan.api.common.IPropertyService;
import cn.fan.dao.PropertyMapper;
import cn.fan.model.common.Property;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-05-09 19:17
 */
@DubboService
public class PropetyService extends ServiceImpl<PropertyMapper,Property> implements IPropertyService {

    @Autowired
    private PropertyMapper propertyMapper;


    @Override
    public int insert(Property property) {
        return propertyMapper.insert(property);
    }


    @Override
    public int remove(int id) {
        return propertyMapper.deleteById(id);
    }

    @Override
    public int removeByEntityId(Class clazz, int entityId) {
        LambdaQueryWrapper<Property> lambdaQueryWrapper=new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(Property::getEntityType,clazz.getName());
        lambdaQueryWrapper.eq(Property::getEntityId,entityId);
        return propertyMapper.delete(lambdaQueryWrapper);
    }

    @Override
    public List<Property> list(Class clazz, int entityId) {
        LambdaQueryWrapper<Property> lambdaQueryWrapper=new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(Property::getEntityType,clazz.getName());
        lambdaQueryWrapper.eq(Property::getEntityId,entityId);
        return propertyMapper.selectList(lambdaQueryWrapper);
    }

    @Override
    public int modify(Property property) {
        return propertyMapper.updateById(property);
    }

    @Override
    public boolean modifyBatch(List<Property> properties) {
        return this.updateBatchById(properties);
    }

    @Override
    public boolean insertBatch(List<Property> properties) {
        return this.saveBatch(properties);
    }


}
