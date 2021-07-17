package cn.fan.debugger;

import cn.fan.constant.ConfigConstant;
import cn.fan.exception.ResponseHandlerException;
import cn.fan.model.constanst.DebuggerConstant;
import cn.fan.util.QqEncrypt;
import cn.fan.util.ResponseHandler;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ListIterator;


/**
 * @program: orange-music
 * @description: 爬取QQ音乐平台数据
 * @author: fanduanjin
 * @create: 2021-04-15 23:21
 */
@Component
public class DebuggerSingerList {
    private static Logger logger = LoggerFactory.getLogger(DebuggerSingerList.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;
    /**
     * page 页数
     */
    static final String module = "Music.SingerListServer";
    static final String method = "get_singer_list";
    static final String group = "singerList";
    static final String PARAM_TEMPLETE = "{\"area\":-100,\"sex\":-100,\"genre\":-100,\"index\":-100,\"sin\":$sin,\"cur_page\":$cur_page}";
    static final String param_sin = "$sin";
    static final int value_sin = 80;
    static final String param_cur_page = "$cur_page";


    @Scheduled(cron = "10 * * * * *")
    public void debugger() {
        try {
            logger.info("开始爬取歌手列表");
            Connection.Response response = null;
            RequestTemplate requestTemplate = new RequestTemplate();
            requestTemplate.setGroup(group);
            requestTemplate.setModule(module);
            requestTemplate.setMethod(method);
            String url = null;
            String param = null;
            String sign = null;
            String data = null;
            for (int i = 1, len = 1; i <= len; i++) {
                param = buildParam(i);
                requestTemplate.setParam(param);
                data = requestTemplate.toString();
                sign = QqEncrypt.getSign(data);
                url = ConfigConstant.baseUrl + "sign=" + sign + "&data=" + data;
                response = Jsoup.connect(url).execute();
                JSONObject root = ResponseHandler.getData(response, group);
                JSONObject jso_data = root.getJSONObject("data");
                if (len == 1) {
                    //len等于1 计算下 一共有多少页
                    int total = jso_data.getIntValue("total");
                    len = ResponseHandler.computePageTotal(total, value_sin);
                }
                if (i > 2) {
                    //测试用 大于2 不用再爬取
                    break;
                }
                JSONArray jsa_singerList = jso_data.getJSONArray("singerlist");
                handlerSingerList(jsa_singerList);
            }
        } catch (Exception exception) {
            logger.error(exception.toString());
        }
    }

    public void handlerSingerList(JSONArray singerList) {
        JSONObject jsonObject = null;
        for (ListIterator<Object> iterator = singerList.listIterator(); iterator.hasNext(); ) {
            jsonObject = (JSONObject) iterator.next();
            String singer_mid = jsonObject.getString("singer_mid");
            //取除mid 爬取歌手详细信息
            logger.info("send "+DebuggerConstant.queue_singer_detail+":"+singer_mid);
            rabbitTemplate.convertAndSend(DebuggerConstant.queue_singer_detail, singer_mid);
        }
    }


    /**
     * 根据pageindex 构建 请求参数
     *
     * @param pageIndex
     * @return
     */
    public String buildParam(int pageIndex) {
        String param = new String(PARAM_TEMPLETE);
        param = param.replace(param_sin, String.valueOf((pageIndex - 1) * value_sin));
        param = param.replace(param_cur_page, String.valueOf(pageIndex));
        return param;
    }

}
