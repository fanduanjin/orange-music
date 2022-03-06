package cn.fan.service.impl;

import cn.fan.api.file.IResourceService;
import cn.fan.dao.ResourceMapper;
import cn.fan.model.file.Resource;
import cn.hutool.core.util.PageUtil;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-05-09 14:39
 */
@DubboService
public class ResourceServiceImpl implements IResourceService {

    @Autowired
    ResourceMapper resourceMapper;

    @Autowired
    ResourceServiceImpl resourceService;

    @Override
    public int insertResource(Resource resource) {
        return resourceMapper.insert(resource);
    }

    @Override
    public List<Resource> pageList(int pageNum, int pageSize) {
        List<Resource> resources = resourceMapper.selectList(PageUtil.getStart(pageNum, pageSize), pageSize);
        return resources;
    }

    @Override
    public Resource getResourceBySnowId(long resourceId) {
        return resourceMapper.selectOneByResourceId(resourceId);
    }

    @Override
    public boolean update(Resource resource) {
        return resourceMapper.update(resource) > 0;
    }

    @Override
    public int deleteBySnowId(long resourceId) {
        return resourceMapper.deleteByResourceId(resourceId);
    }
}
