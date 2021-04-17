package cn.fan.exception;

/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-04-16 20:11
 */
public class ResponseHandlerException extends RuntimeException {
    public ResponseHandlerException(String msg) {
        super(msg);
    }
}
