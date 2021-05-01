package cn.fan.debugger;

import cn.fan.constant.ConfigConstant;
import cn.fan.model.constanst.DebuggerConstant;
import cn.fan.model.debugger.SingerInfo;
import cn.fan.util.QqEncrypt;
import cn.fan.util.ResponseHandler;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.curator.utils.DebugUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.ListIterator;


/**
 * @program: orange-music
 * @description: 爬取QQ音乐平台数据
 * @author: fanduanjin
 * @create: 2021-04-15 23:21
 */
@Component
public class DebuggerSingerList {
    private static Logger logger= LoggerFactory.getLogger(DebuggerSingerList.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;
    /**
     * page 页数
     */
    static final String module="Music.SingerListServer";
    static final String method="get_singer_list";
    static final String group="singerList";
    static final String PARAM_TEMPLETE ="{\"area\":-100,\"sex\":-100,\"genre\":-100,\"index\":-100,\"sin\":$sin,\"cur_page\":$cur_page}";
    static final String param_sin="$sin";
    static final int value_sin=80;
    static final String param_cur_page="$cur_page";

    @Scheduled(fixedRate = 50000)
    public void testM(){
        try {
            String param="{\"singer_mids\": [\"002FTSfR2lcnnw\"],\"ex_singer\": 1,\"wiki_singer\": 1,\"group_singer\": 1,\"pic\": 1,\"photos\": 0}";
            RequestTemplate requestTemplate=new RequestTemplate();
            requestTemplate.setMethod("GetSingerDetail");
            requestTemplate.setModule("musichall.singer_info_server");
            requestTemplate.setGroup("getSingerDetail");
            requestTemplate.setParam(param);
            String data=requestTemplate.toString();
            String sign=QqEncrypt.getSign(data);
            String url=ConfigConstant.baseUrl+"sign="+sign+"&data="+data;
            Connection.Response response=Jsoup.connect(url).execute();
            logger.info(response.body());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    //@Scheduled(cron = "30 * * * * *")
    public void debugger() throws IOException {
        logger.info("开始爬取歌手列表");
        Connection.Response response=null;
        RequestTemplate requestTemplate=new RequestTemplate();
        requestTemplate.setGroup(group);
        requestTemplate.setModule(module);
        requestTemplate.setMethod(method);
        String url=null;
        String param=null;
        String sign=null;
        String data=null;
        for(int i=1,len=1;i<=len;i++){
            param=buildParam(i);
            requestTemplate.setParam(param);
            data=requestTemplate.toString();
            sign=QqEncrypt.getSign(data);
            url=ConfigConstant.baseUrl+"sign="+sign+"&data="+data;
            response=Jsoup.connect(url).execute();
            JSONObject root= ResponseHandler.getData(response,group);
            JSONObject jso_data=root.getJSONObject("data");
            if(len==1){
                //len等于1 计算下 一共有多少页
                int total=jso_data.getIntValue("total");
                len=ResponseHandler.computePageTotal(total,value_sin);
            }
            if(i>2){
                //测试用 大于2 不用再爬取
                break;
            }
            JSONArray jsa_singerList=jso_data.getJSONArray("singerlist");
            handlerSingerList(jsa_singerList);
        }
    }

    public void handlerSingerList(JSONArray singerList){
        JSONObject jsonObject=null;
        SingerInfo singerInfo=null;
        for(ListIterator<Object> iterator= singerList.listIterator();iterator.hasNext();){
            jsonObject=(JSONObject) iterator.next();
            singerInfo=new SingerInfo();
            singerInfo.setSinger_id(jsonObject.getString("singer_id"));
            singerInfo.setSinger_name(jsonObject.getString("singer_name"));
            singerInfo.setSinger_mid(jsonObject.getString("singer_mid"));
            singerInfo.setSinger_pic(jsonObject.getString("singer_pic"));
            //爬取玩基础 信息，再爬取 歌手详细信息 提交到mq 通过mq监听在进行爬取
            rabbitTemplate.convertAndSend(DebuggerConstant.queue_singer_detail,singerInfo);
            //爬取 所有歌曲列表
            rabbitTemplate.convertAndSend(DebuggerConstant.queue_song_list,singerInfo);
        }
    }


    /**
     * 根据pageindex 构建 请求参数
     * @param pageIndex
     * @return
     */
    public String buildParam(int pageIndex){
        String param=new String(PARAM_TEMPLETE);
        param=param.replace(param_sin,String.valueOf((pageIndex-1)*value_sin));
        param=param.replace(param_cur_page,String.valueOf(pageIndex));
        return param;
    }

}
