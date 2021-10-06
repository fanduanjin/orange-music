package cn.fan.model.music;

import cn.fan.model.common.Property;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class Song implements Serializable {

    private Integer id;

    private Integer singerId;

    private Integer singerPlatId;

    private Integer type;


    private String mid;


    private Integer platId;


    private String name;


    private String title;


    private String subTitle;


    private Integer albumPlatId;


    private String mvid;

    private Integer language;


    private Integer genre;


    private Date publicTime;


    private Boolean pricePlay;


    private String mediaMid;


    private Long mediaResourceId;

    @TableField(exist = false)
    private String mediaPath;

    private String lrc;

    @TableField(exist = false)
    private List<Property> properties;

    private static final long serialVersionUID = 1L;

}