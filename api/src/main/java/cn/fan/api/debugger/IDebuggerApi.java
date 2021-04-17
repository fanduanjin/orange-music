package cn.fan.api.debugger;

import javax.print.DocFlavor;

/**
 * @author Administrator
 */
public interface IDebuggerApi {
    /**
     * 获取 com参数
     * @param clasname
     * @return
     */
    String getData(String clasname);

    /**
     * 获取sign签名
     * @param className
     * @return
     */
    String getSign(String className);
}
