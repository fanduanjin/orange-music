package cn.fan.model.debugger;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-04-27 18:22
 */
@Data
public class SongInfo implements Serializable {
    private String id;
    private String mid;
    private String title;
    private String subTitle;
    private String time_public;
    private String album_id;
}
