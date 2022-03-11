package cn.fan.model.web;

import java.util.function.Consumer;

/**
 * @author fanduanjin
 * @program orange-music
 * @Classname Promise
 * @Description
 * @Date 2022/3/6 20:54
 * @Created by fanduanjin
 */
public class Promise<T> {


    private Consumer<T> success;
    private Consumer<String> fail;


    public Promise fail(Consumer<String> consumer) {
        this.fail = consumer;
        return this;
    }

    public Promise success(Consumer<T> consumer) {
        this.success = consumer;
        return this;
    }

    public boolean end(ResultModel<T> resultModel) {
        if (resultModel.getCode() == ResultStatus.SUCCESS.getCode()) {
            this.success.accept(resultModel.getData());
            return true;
        } else {
            String msg = "发生错误 错误代码:" + resultModel.getCode() + " msg:" + resultModel.getMsg();
            this.fail.accept(msg);
            return false;
        }
    }

}
