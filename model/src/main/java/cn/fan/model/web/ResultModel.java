package cn.fan.model.web;

import lombok.Data;

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * @author fanduanjin
 * @program orange-music
 * @Classname ResultModel
 * @Description
 * @Date 2022/3/4 12:26
 * @Created by fanduanjin
 */
@Data
public class ResultModel<T> {
    private int code;
    private String msg;
    private T data;
    private int total;


}
