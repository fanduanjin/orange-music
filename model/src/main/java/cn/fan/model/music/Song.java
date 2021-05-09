package cn.fan.model.music;

import lombok.Data;

/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-05-08 16:33
 */
@Data
public class Song {
    private int id;
    private String mid;
    private String title;
    private String subTitle;
    private String publicTime;
    private int albumId;
}
