package cn.fan.service.impl;

import cn.fan.api.common.IPropertyService;
import cn.fan.api.music.ISongService;
import cn.fan.dao.SongMapper;
import cn.fan.model.common.Property;
import cn.fan.model.music.Song;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-04-16 15:36
 */
@DubboService
public class SongServiceImpl implements ISongService {
    private static final  Logger LOGGER= LoggerFactory.getLogger(SongServiceImpl.class);

    @Autowired
    SongMapper songMapper;

    @DubboReference
    IPropertyService propertyService;

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;


    @Override
    public int insert(Song song) {
        return 0;
    }

    @Override
    public int remove(int id) {
        return 0;
    }

    @Override
    public int update(Song song) {
        return 0;
    }

    @Override
    public Song get(int id) {
        return null;
    }

    @Override
    public Song getByPlatId(int platId) {
        return null;
    }
}
