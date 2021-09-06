package cn.fan.exception;

import lombok.Data;

@Data
public class ResponseExceptionJsonObject {

    private String url;
    private Object data;
    private String responseBody;
    private String msg;
}
