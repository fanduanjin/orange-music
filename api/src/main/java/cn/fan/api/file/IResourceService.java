package cn.fan.api.file;

import cn.fan.model.file.Resource;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-05-09 14:20
 */
public interface IResourceService extends IService<Resource> {
    /**
     * 插入
     * @param resource
     * @return
     */
    int insertResource(Resource resource);



}
