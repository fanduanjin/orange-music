package cn.fan.service.impl;

import cn.fan.api.music.ISingerService;
import cn.fan.dao.SingerMapper;
import cn.fan.model.music.Singer;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class SingerService extends ServiceImpl<SingerMapper, Singer> implements ISingerService {
    @Autowired
    SingerMapper singerMapper;

    @Override
    public int insert(Singer singer) {
        return singerMapper.insert(singer);
    }

    @Override
    public int remove(int id) {
        return singerMapper.deleteById(id);
    }

    @Override
    public int modify(Singer singer) {
        return singerMapper.updateById(singer);
    }

    @Override
    public Singer get(int id) {
        return singerMapper.selectById(id);
    }

    @Override
    public Singer getByPlatId(int id) {
        LambdaQueryWrapper<Singer> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(Singer::getPlatId, id);
        Singer singer = singerMapper.selectOne(lambdaQueryWrapper);
        return singer;
    }

    public boolean existsByPlayId(int platId) {
        LambdaQueryWrapper<Singer> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Singer::getPlatId, platId);
        int count = singerMapper.selectCount(lambdaQueryWrapper);
        return count > 0;
    }
}
