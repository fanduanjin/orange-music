package cn.fan.service.impl;

import cn.fan.api.common.IPropertyService;
import cn.fan.api.music.ISongService;
import cn.fan.dao.SongMapper;
import cn.fan.model.common.Property;
import cn.fan.model.music.Song;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DubboService
public class SongService implements ISongService {
    @Autowired
    SongMapper songMapper;

    @DubboReference
    IPropertyService propertyService;

    @Transactional
    @Override
    public int insert(Song song) {
        int result = songMapper.insert(song);
        List<Property> properties = song.getProperties();
        if (properties != null && !properties.isEmpty()) {
            for (Property property : properties) {
                property.setEntityId(song.getId());
                property.setEntityType(Song.class.getName());
            }
            propertyService.insertBatch(properties);
        }
        return result;
    }

    @Transactional
    @Override
    public int remove(int id) {
        propertyService.removeByEntityId(Song.class, id);
        return songMapper.deleteById(id);
    }

    @Transactional
    @Override
    public int update(Song song) {
        if (song.getProperties() != null && !song.getProperties().isEmpty()) {
            //debugger 时 判断 属性是否修改 有点麻烦，这里如果 song信息修改过，就当作 properties也修改过
            //删除以前的 properties
            propertyService.removeByEntityId(Song.class, song.getId());
            //直接插入 现在的
            for (Property property : song.getProperties()) {
                property.setEntityId(song.getId());
                property.setEntityType(Song.class.getName());
            }
            propertyService.insertBatch(song.getProperties());
            //propertyService.modifyBatch(song.getProperties());
        }
        return songMapper.updateById(song);
    }

    @Override
    public Song get(int id) {
        Song song = songMapper.selectById(id);
        song.setProperties(propertyService.list(Song.class, song.getId()));
        return song;
    }

    @Override
    public Song getByPlatId(int platId) {
        LambdaQueryWrapper<Song> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Song::getPlatId, platId);
        Song song = songMapper.selectOne(lambdaQueryWrapper);
        if (song != null)
            song.setProperties(propertyService.list(Song.class, song.getId()));
        return song;
    }
}
