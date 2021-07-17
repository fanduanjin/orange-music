package cn.fan.model.file;

import lombok.Data;

import java.io.Serializable;

@Data
public class Resource implements Serializable {

    private Integer id;


    private Long resourceId;


    private String sourcePath;


    private String dfsPath;


    private static final long serialVersionUID = 1L;


}