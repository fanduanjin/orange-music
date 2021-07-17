package cn.fan.api.music;

import cn.fan.model.music.Song;

public interface ISongService {
    int insert(Song song);

    int remove(int id);

    int update(Song song);

    Song get(int id);

    Song getByPlatId(int platId);
}
