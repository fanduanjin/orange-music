package cn.fan.model.music;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Singer implements Serializable {

    private Integer id;


    private Integer platId;

    private String mid;


    private String name;


    private Integer type;

    private Integer area;


    private Integer genre;


    private String foreignName;


    private Date birthday;


    private String pic;


    private Long picResourceId;


    private String desc;


    private String wiki;


    private static final long serialVersionUID = 1L;


}