package cn.fan.consumer;

import cn.fan.api.music.ISingerService;
import cn.fan.constant.ConfigConstant;
import cn.fan.debugger.RequestTemplate;
import cn.fan.model.constanst.DebuggerConstant;
import cn.fan.model.file.Resource;
import cn.fan.model.music.Singer;
import cn.fan.util.QqEncrypt;
import cn.fan.util.ResponseHandler;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.dubbo.config.annotation.DubboReference;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @program: orange-music
 * @description: 爬取歌手详情信息
 * @author: fanduanjin
 * @create: 2021-05-07 22:42
 */
@Component
@RabbitListener(queuesToDeclare = @Queue(DebuggerConstant.queue_singer_detail))
public class DebuggerSingerDetailInfo {
    private static final Logger LOGGER = LoggerFactory.getLogger(DebuggerSingerDetailInfo.class);

    @Value("${spring.application.workerId}")
    private int workerId;
    @Value("${spring.application.datacenterId}")
    private int datacenterId;

    @DubboReference
    private ISingerService singerService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final String param_template = "{\"singer_mids\": [\"$singer_mid\"],\"ex_singer\": 1,\"wiki_singer\": 1,\"group_singer\": 1,\"pic\": 1,\"photos\": 1}";
    private static final String method = "GetSingerDetail";
    private static final String module = "musichall.singer_info_server";
    private static final String group = "getSingerDetail";
    private static final String param_singer_mid = "$singer_mid";

    @RabbitHandler
    public void receiver(String singer_mid) throws IOException {
        LOGGER.info("handler " + DebuggerConstant.queue_singer_detail + ":" + singer_mid);
        String param = param_template.replace(param_singer_mid, singer_mid);
        RequestTemplate requestTemplate = new RequestTemplate();
        requestTemplate.setParam(param);
        requestTemplate.setMethod(method);
        requestTemplate.setModule(module);
        requestTemplate.setGroup(group);
        String data = requestTemplate.toString();
        String sign = QqEncrypt.getSign(data);
        String url = ConfigConstant.baseUrl + "sign=" + sign + "&data=" + data;
        Connection.Response response = null;
        response = Jsoup.connect(url).execute();
        //处理响应数据
        JSONObject jo_root = ResponseHandler.getData(response, group);
        JSONObject jo_data = jo_root.getJSONObject("data");
        JSONArray ja_singerList = jo_data.getJSONArray("singer_list");
        //由于一次性只获取一个 歌手详细 直接获取 数组坐标0就可以
        JSONObject jo_singerDetail = ja_singerList.getJSONObject(0);
        handlerSingerDetail(jo_singerDetail);
    }

    void handlerSingerDetail(JSONObject jo_singerDetail) {
        Singer singerInfo = new Singer();
        //获取基础信息
        JSONObject jo_basic_info = jo_singerDetail.getJSONObject("basic_info");
        singerInfo.setMid(jo_basic_info.getString("singer_mid"));
        singerInfo.setPlatId(jo_basic_info.getIntValue("singer_id"));
        singerInfo.setName(jo_basic_info.getString("name"));
        singerInfo.setType(jo_basic_info.getIntValue("type"));
        // ex_info
        JSONObject jo_ex_info = jo_singerDetail.getJSONObject("ex_info");
        singerInfo.setArea(jo_ex_info.getIntValue("area"));
        singerInfo.setDesc(jo_ex_info.getString("desc"));
        singerInfo.setGenre(jo_ex_info.getIntValue("genre"));
        singerInfo.setForeignName(jo_ex_info.getString("foreign_name"));
        singerInfo.setBirthday(jo_ex_info.getDate("birthday"));
        //wiki 处理 wiki
        singerInfo.setWiki(jo_singerDetail.getString("wiki"));
        //头像处理pic
        JSONObject jo_pic = jo_singerDetail.getJSONObject("pic");
        //获取头像 url
        String pic_url = jo_pic.getString("pic");
        pic_url = pic_url == null ? "" : pic_url;
        singerInfo.setPic(pic_url);
        Singer singer = singerService.getByPlatId(singerInfo.getPlatId());
        if (singer == null) {
            //生成全局唯一id
            long resource_id = IdUtil.getSnowflake(workerId, datacenterId).nextId();
            singerInfo.setPicResourceId(resource_id);
            singerService.insert(singerInfo);
            LOGGER.info("持久化到数据库:" + singerInfo.getMid());

            if (!pic_url.isEmpty()) {
                Resource resource = new Resource();
                resource.setResourceId(resource_id);
                //http://y.gtimg.cn/music/photo_new/T001R300x300M000001tYW3b0S3Rd9.jpg
                //获取 800x800的尺寸
                String source_path = pic_url.replace("300x300", "800x800");
                resource.setSourcePath(source_path);
                rabbitTemplate.convertAndSend(DebuggerConstant.queue_dowload_resource, resource);
            }

        } else {
            boolean picIsUpdate = !singer.getPic().equals(pic_url);
            boolean isModify = singerInfoIsModify(singer, singerInfo);
            long pic_resource_id = singer.getPicResourceId();
            if (isModify || picIsUpdate) {
                if (picIsUpdate) {
                    //图片头像修改过了 url为空 id 为0
                    pic_resource_id = pic_url.isEmpty() ? 0 : IdUtil.getSnowflake(workerId, datacenterId).nextId();
                }
                singerInfo.setId(singer.getId());
                singerInfo.setPicResourceId(pic_resource_id);
                singerService.modify(singerInfo);
                LOGGER.info("更新了 SingerInfo" + JSON.toJSONString(singerInfo));
                if (picIsUpdate && !pic_url.isEmpty()) {
                    Resource resource = new Resource();
                    resource.setResourceId(pic_resource_id);
                    //http://y.gtimg.cn/music/photo_new/T001R300x300M000001tYW3b0S3Rd9.jpg
                    //获取 800x800的尺寸
                    String source_path = pic_url.replace("300x300", "800x800");
                    resource.setSourcePath(source_path);
                    rabbitTemplate.convertAndSend(DebuggerConstant.queue_dowload_resource, resource);
                }
            }
        }


    }

    boolean singerInfoIsModify(Singer db_singer, Singer y_singer) {
        boolean isModify;
        Integer id= db_singer.getId();
        long picResourceId= db_singer.getPicResourceId();
        //将 两个 singer 都转化为字符串 再去比较，解决其他列 为空时的异常
        db_singer.setId(0);
        db_singer.setPicResourceId(0L);
        y_singer.setId(0);
        y_singer.setPicResourceId(0L);
        String json_db_singer=JSON.toJSONString(db_singer);
        String json_singer_info=JSON.toJSONString(y_singer);
        isModify=!json_db_singer.equals(json_singer_info);
        db_singer.setId(id);
        db_singer.setPicResourceId(picResourceId);
        return isModify;
    }
}
