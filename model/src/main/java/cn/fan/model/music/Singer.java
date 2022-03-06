package cn.fan.model.music;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-04-16 15:36
 */
@Data
public class Singer implements Serializable {

    /**
     * 主键自增列
     */
    private int pid;

    /**
     * q音平台id
     */
    private int id;

    /**
     * 歌手姓名
     */
    private String name;

    /**
     * q音平台mid
     */
    private String mid;

    /**
     * 歌手类型
     */
    private int type;

    /**
     * 歌手简介
     */
    private String desc;

    /**
     * 外文名
     */
    private String foreignName;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 个人经历
     */
    private String wiki;

    /**
     * 头像地址
     */
    private String pic;


}