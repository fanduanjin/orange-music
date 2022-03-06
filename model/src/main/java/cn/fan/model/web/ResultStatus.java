package cn.fan.model.web;

import lombok.Data;

/**
 * @author fanduanjin
 * @program orange-music
 * @Classname ResultStatus
 * @Description
 * @Date 2022/3/6 20:14
 * @Created by fanduanjin
 */
public enum ResultStatus {
    SUCCESS(0),
    ERROR(-1);

    /**
     * 状态值
     */
    private int code;
    ResultStatus(int code){
        this.code=code;
    }

    public int getCode(){
        return this.code;
    }
}
