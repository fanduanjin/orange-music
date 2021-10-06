package cn.fan.model.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class Property implements Serializable{

    private Integer id;

    private String entityType;

    private Integer entityId;

    private String key;

    private String value;

    private static final long serialVersionUID = 1L;


}