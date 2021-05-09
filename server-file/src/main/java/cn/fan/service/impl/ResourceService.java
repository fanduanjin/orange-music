package cn.fan.service.impl;

import cn.fan.api.file.IResourceService;
import cn.fan.dao.ResourceMapper;
import cn.fan.model.file.Resource;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-05-09 14:39
 */
@DubboService
public class ResourceService implements IResourceService {

    @Autowired
    ResourceMapper resourceMapper;

    @Override
    public int insertResource(Resource resource) {
        return resourceMapper.insert(resource);
    }
}
