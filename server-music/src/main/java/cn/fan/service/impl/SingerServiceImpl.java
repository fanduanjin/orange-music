package cn.fan.service.impl;

import cn.fan.api.music.ISingerService;
import cn.fan.dao.SingerMapper;
import cn.fan.model.music.Singer;
import cn.fan.model.music.SingerType;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    public boolean insert(Singer singer) {
        Assert.isTrue(singer.getType() != SingerType.Team.getCode(), "添加失败，当前歌手是个乐队");
        return singerMapper.insert(singer) == 1;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean insertTeam(Singer team) {
        Assert.isTrue(team.getType() == SingerType.Team.getCode(), "当前歌手不是一个乐队");
        Assert.isTrue(singerMapper.insert(team) == 1, "添加乐队失败");
        if (team.getTeam() != null && !team.getTeam().isEmpty()) {
            for (Singer singer : team.getTeam()) {
                Assert.isTrue(singerMapper.insertTeamSinger(team.getId(), singer.getId()) == 1, "添加乐队成员失败");
            }
        }
        return true;
    }

    @Override
    public boolean remove(int pid) {
        return singerMapper.delete(pid) == 1;
    }

    @Override
    public boolean modify(Singer singer) {
        Assert.isTrue(singer.getType() != SingerType.Team.getCode(), "修改失败，当前歌手是个乐队");
        return singerMapper.update(singer) == 1;

    }

    @Override
    public Singer get(int pid) {
        Singer singer = singerMapper.selectOne(pid);
        if (singer != null && singer.getType() == SingerType.Team.getCode()) {
            singer.setTeam(singerMapper.selectTeamSingers(singer.getId()));
        }
        return singer;
    }

    @Override
    public Singer getById(int id) {
        Singer singer = singerMapper.selectOneById(id);
        if (singer != null && singer.getType() == SingerType.Team.getCode()) {
            singer.setTeam(singerMapper.selectTeamSingers(singer.getId()));
        }
        return singer;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean modifyTeam(Singer team) {
        Assert.isTrue(team.getType() == SingerType.Team.getCode(), "当前歌手不是一个乐队");
        singerMapper.update(team);
        if (team.getTeam() == null || team.getTeam().isEmpty()) {
            singerMapper.deleteTeamSingers(team.getId());
        } else {
            List<Singer> dbTeams = singerMapper.selectTeamSingers(team.getId());
            //不为空 判断是 哪个映射信息不对
            if (dbTeams == null || dbTeams.isEmpty()) {
                team.getTeam().forEach(singer -> {
                    singerMapper.insertTeamSinger(team.getId(), singer.getId());
                });
            } else {
                //这里还有优化空间***
                for (Singer singer : team.getTeam()) {
                    //拿爬取的 比 数据库中的 不存在 新增 存在 跳过
                    for (Singer singer1 : dbTeams) {
                        if (singer1 != null && singer1.getId() != singer.getId()) {
                            //不存在 就新增
                            singerMapper.insertTeamSinger(team.getId(), singer.getId());
                        }
                    }
                }

                for (Singer singer : dbTeams) {
                    //数据库中的 比 爬取的 不存在 就删除
                    for (Singer singer1 : team.getTeam()) {
                        if (singer != null && singer1.getId() != singer.getId()) {
                            //不存在 就删除
                            singerMapper.deleteTeamSinger(team.getId(), singer.getId());
                        }
                    }
                }
            }
        }
        return true;
    }

}
