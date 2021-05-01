package cn.fan.util;

import cn.fan.exception.ResponseHandlerException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;

/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-04-16 20:09
 */
public class ResponseHandler {
    public static JSONObject getData(Connection.Response response,String group){
        String body=response.body();
        if(body==null||body.isEmpty()){
            throw  new ResponseHandlerException("debugger result is emplty");
        }
        JSONObject root= JSON.parseObject(body);
        //获取接口状态码
        int code=root.getIntValue("code");
        if(code!=0){
            //状态码不等于0 调用接口失败
            throw  new ResponseHandlerException("code faild : "+body);
        }
        //开始解析数据 返回JSONObject 类型对象
        return root.getJSONObject(group);
    }

    public static int computePageTotal(int total,int size){
        int pageTotal=total/size;
        if(total%size!=0){
            pageTotal++;
        }
        return pageTotal;
    }
}
