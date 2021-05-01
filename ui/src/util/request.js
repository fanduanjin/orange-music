import axios from "axios";
import {Message} from "element-ui";
import fa from "element-ui/src/locale/lang/fa";
import da from "element-ui/src/locale/lang/da";
import ca from "element-ui/src/locale/lang/ca";

/**
 * 规定js接口
 * {
 *     code:0,
 *     msg:'',
 *     data:{}
 * }
 */

const handler = {
    handlerResponse(result, sucessCall, faildCall) {
        if (!result.code || result.code == 0) {
            //业务状态码异常 调用回调 直接返回
            Message.error('服务器异常:' + result.code + '  ' + result.msg);
            faildCall && typeof (faildCall) == "function" && faildCall(result);
        }
        //业务状态码正常
        sucessCall && typeof (sucessCall) == "function" && sucessCall(result);
    }

}

axios.interceptors.response.use(function (res) {
    return res;
}, function (error) {
    Message.error(error);
})

const request = axios.create({})

export default {
    put(url, param, success, faild) {
        request.put(url, param).then(res => {
                handler.handlerResponse(res, success, faild)
            }
        ).catch(error => {
            handler.handlerResponse({code: -1, msg: error}, null, faild)
        })
    },
    del(url, param, success, faild) {
        request.delete(url, {
            data:param
        }).then(res => {
                handler.handlerResponse(res, success, faild)
            }
        ).catch(error => {
            handler.handlerResponse({code: -1, msg: error}, null, faild)
        })
    },
    post(url, param, success, faild) {
        request.post(url, param).then(res => {
                handler.handlerResponse(res, success, faild)
            }
        ).catch(error => {
            handler.handlerResponse({code: -1, msg: error}, null, faild)
        })
    },
    get(url, param, success, faild) {
        request.get(url, {
            data:param
        }).then(res => {
                handler.handlerResponse(res, success, faild)
            }
        ).catch(error => {
            handler.handlerResponse({code: -1, msg: error}, null, faild)
        })
    }

}