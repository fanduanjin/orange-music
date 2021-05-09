package cn.fan.service.impl;

import cn.fan.api.music.ISingerService;
import cn.fan.dao.SingerMapper;
import cn.fan.model.music.Singer;
import cn.fan.model.music.SingerExample;
import com.github.pagehelper.PageHelper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-05-08 18:44
 */

@DubboService
public class SingerService implements ISingerService {
    @Autowired
    SingerMapper singerMapper;
    @Override
    public int insert(Singer singer) {
        return singerMapper.insert(singer);
    }

    @Override
    public int remove(int id) {
        return singerMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int modify(Singer singer) {
        return singerMapper.updateByPrimaryKeyWithBLOBs(singer);
    }

    @Override
    public Singer get(int id) {
        return singerMapper.selectByPrimaryKey(id);
    }

    @Override
    public Singer getByPlatId(int id) {
        SingerExample singerExample=new SingerExample();
        singerExample.createCriteria().andPlatIdEqualTo(id);
        PageHelper.offsetPage(0,1);
        List<Singer>  singers= singerMapper.selectByExampleWithBLOBs(singerExample);
        return singers.isEmpty()?null:singers.get(0);
    }
}
