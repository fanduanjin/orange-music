package cn.fan.model.music;

import cn.fan.model.common.Property;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-04-16 15:36
 */
@Data
public class Song implements Serializable {
    /**
     * 主键
     */
    private int pid;

    /**
     * 平台id
     */
    private int id;

    /**
     * 扩展名称 ***主题曲
     */
    private String extraName;

    /**
     * 平台mid
     */
    private String mid;

    /**
     * 标题
     */
    private String title;

    /**
     * 长标题|扩展标题
     */
    private String subTitle;

    /**
     * 专辑id（平台）
     */
    private int albumId;

    /**
     * 专辑mid
     */
    private String albumMid;

    /**
     * MV id（平台）
     */
    private int mvId;

    /**
     * MV mid
     */
    private String mvMid;

    /**
     * 发布时间
     */
    private Date publicTime;

    /**
     * 歌词 MD5
     */
    private String lrc;

    /**
     * 歌曲文件路径 （云路径）
     */
    private String mediaUrl;

}