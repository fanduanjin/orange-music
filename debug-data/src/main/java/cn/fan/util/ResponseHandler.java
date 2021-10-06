package cn.fan.util;

import cn.fan.exception.ResponseExceptionJsonObject;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-04-16 20:09
 */
public class ResponseHandler {
    public static final Logger LOGGER = LoggerFactory.getLogger(ResponseHandler.class);


    /**
     * 处理返回数据 有异常返回null
     * @param request
     * @param response
     * @param group
     * @return
     */
    public static JSONObject getData(Connection.Request request, Connection.Response response, String group) {
        String body = response.body();
        ResponseExceptionJsonObject responseExceptionJsonObject = new ResponseExceptionJsonObject();
        responseExceptionJsonObject.setUrl(request.url().toString());
        responseExceptionJsonObject.setData(request.postDataCharset());
        responseExceptionJsonObject.setResponseBody(body);
        if (body == null || body.isEmpty()) {
            responseExceptionJsonObject.setMsg("debugger result is empty");
            responseExceptionJsonObject.setResponseBody(response.body());
            LOGGER.warn(JSON.toJSONString(responseExceptionJsonObject));
            return null;
        }
        JSONObject root = JSON.parseObject(body);
        //获取接口状态码
        int code = root.getIntValue("code");
        if (code != 0) {
            //状态码不等于0 调用接口失败
            responseExceptionJsonObject.setMsg("result code error " + code);
            LOGGER.warn(JSON.toJSONString(responseExceptionJsonObject));
            return null;
        }
        //开始解析数据 返回JSONObject 类型对象
        //LOGGER.info(JSON.toJSONString(responseExceptionJsonObject));
        return root.getJSONObject(group);
    }
}
