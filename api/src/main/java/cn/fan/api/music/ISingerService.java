package cn.fan.api.music;

import cn.fan.model.music.Singer;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-04-17 16:35
 */
public interface ISingerService extends IService<Singer> {

    /**
     * 插入
     * @param singer
     * @return
     */
    int insert(Singer singer);

    /**
     *
     * @param id
     * @return
     */
    int remove(int id);

    /**
     *
     * @param singer
     * @return
     */
    int modify(Singer singer);

    /**
     *
     * @param id
     * @return
     */
    Singer get(int id);

    /**
     * 根据平台 singer_id获取
     * @param id
     * @return
     */
    Singer getByPlatId(int id);

    boolean existsByPlayId(int platId);

}
