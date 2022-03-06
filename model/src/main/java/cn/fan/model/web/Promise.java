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


    private Success<T> success;
    private Fail fail;

    public Promise success(Success<T> success) {
        this.success = success;
        return this;
    }

    public Promise fail(Fail fail) {
        this.fail = fail;
        return this;
    }

    public void end(ResultModel<T> resultModel) {
        if (resultModel.getCode() != ResultStatus.SUCCESS.getCode()) {
            this.success.success(resultModel.getData());
        } else {
            String msg = "发生错误 错误代码:" + resultModel.getCode() + " msg:" + resultModel.getMsg();
            this.fail.fail(msg);
        }
    }

    @FunctionalInterface
    public interface Success<T> {

        /**
         * 成功时回调
         *
         * @param t
         */
        void success(T t);
    }

    @FunctionalInterface
    public interface Fail {
        /**
         * 失败时回调
         *
         * @param msg
         */
        void fail(String msg);
    }

}
