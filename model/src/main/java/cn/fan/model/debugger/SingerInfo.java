package cn.fan.model.debugger;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-04-16 20:01
 */

@Data
public class SingerInfo implements  Serializable {
    private  String singer_mid;
    private String singer_id;
    private String singer_name;
    private String singer_pic;
    private String singer_desc;

}
