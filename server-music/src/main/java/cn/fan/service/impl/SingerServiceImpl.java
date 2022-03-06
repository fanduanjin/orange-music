package cn.fan.service.impl;

import cn.fan.api.music.ISingerService;
import cn.fan.dao.SingerMapper;
import cn.fan.model.music.Singer;
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
public class SingerServiceImpl implements ISingerService {
    @Autowired
    SingerMapper singerMapper;

    @Override
    public int insert(Singer singer) {
        return singerMapper.insert(singer);
    }

    @Override
    public int remove(int id) {
        return singerMapper.delete(id);
    }

    @Override
    public int modify(Singer singer) {
        return singerMapper.update(singer);
    }

    @Override
    public Singer get(int id) {
        return singerMapper.selectOne(id);
    }

    @Override
    public Singer getByPlatId(int platId) {
        return null;
    }

    @Override
    public boolean existsByPlayId(int platId) {
        return false;
    }
}
