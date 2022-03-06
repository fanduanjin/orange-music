package cn.fan.model.file;

import lombok.Data;

import java.io.Serializable;
/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-04-16 15:36
 */
@Data
public class Resource implements Serializable {

    private Integer id;


    private Long resourceId;


    private String sourcePath;


    private String dfsPath;


}