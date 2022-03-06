package cn.fan.api.file;

import cn.fan.model.file.Resource;

import java.util.List;

/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-05-09 14:20
 */
public interface IResourceService {
    /**
     * 插入
     * @param resource
     * @return
     */
    int insertResource(Resource resource);

    /**
     * 分页查询数据
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<Resource> pageList(int pageNum,int pageSize);

    /**
     * 根据雪花id查询  对等于 数据库 resource_id 列
     * @return
     */
    Resource getResourceBySnowId(long resourceId);

    /**
     * 更新
     * @param resource
     * @return
     */
    boolean update(Resource resource);

    /**
     *
     */
    int deleteBySnowId(long resourceId);
}
