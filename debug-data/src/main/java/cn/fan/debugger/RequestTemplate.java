package cn.fan.debugger;


import cn.fan.constant.ConfigConstant;

/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-04-16 18:24
 */

public class RequestTemplate {

    private String module;
    private String method;
    private String param;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    private String group;


    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    @Override
    public String toString(){
        return "{\"comm\":"+ ConfigConstant.comm +
                "," +"\""+group+"\":{"+
                "\"module\":\""+module+
                "\",\"method\":\""+method+
                "\",\"param\":"+param+
                "}}";
    }
}
