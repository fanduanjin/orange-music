package cn.fan.model.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-04-16 15:36
 */
@Data
public class Property implements Serializable {

    private Integer pid;

    private String entityType;

    private int entityTypeCrc;

    public int getEntityTypeCrc() {
        if (entityType != null) {
            return entityType.hashCode();
        }
        return -1;
    }

    private Integer entityId;

    private String key;

    private String value;


}