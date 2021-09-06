package cn.fan.debugger;

import cn.fan.api.lock.ILock;
import cn.fan.constant.ConfigConstant;
import cn.fan.model.music.Singer;
import cn.fan.util.QqEncrypt;
import cn.fan.util.RequestTemplate;
import cn.fan.util.ResponseHandler;
import cn.hutool.core.util.PageUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.dubbo.config.annotation.DubboReference;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * @program: orange-music
 * @description: 爬取QQ音乐平台数据
 * @author: fanduanjin
 * @create: 2021-04-15 23:21
 */
@Component
public class DebuggerSingerList {
    private static Logger LOGGER = LoggerFactory.getLogger(DebuggerSingerList.class);

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;
    /**
     * page 页数
     */
    static final String module = "Music.SingerListServer";
    static final String method = "get_singer_list";
    static final String group = "singerList";
    static final String PARAM_TEMPLETE = "{\"area\":-100,\"sex\":-100,\"genre\":-100,\"index\":-100,\"sin\":$sin,\"cur_page\":$cur_page}";
    //static final String PARAM_TEMPLETE = "{\"area\":-100,\"sex\":-100,\"genre\":-100,\"index\":-100,\"cur_page\":$cur_page}";
    static final String param_sin = "$sin";
    //固定值不要动
    static final int value_sin = 80;
    static final String param_cur_page = "$cur_page";
    int total = 0;
    @DubboReference
    ILock lock;

    public void debugger(ResultHandler resultHandler) throws IOException {
        LOGGER.info("开始爬取歌手列表");
        total = 0;
        JSONObject root = debugger(1);
        if (root == null)
            LOGGER.error("爬取歌手列表失败 未获取歌手总数");
        //获取歌手总数量
        JSONObject jso_data = root.getJSONObject("data");
        int singerTotal = jso_data.getIntValue("total");
        int pageTotal = PageUtil.totalPage(singerTotal, value_sin);
        JSONArray jsa_singerList = jso_data.getJSONArray("singerlist");
        List<Singer> singers = handlerSingerList(jsa_singerList);
        calculationTotal(singers.size());
        resultHandler.handler(singers);
        List<CompletableFuture<Integer>> futures = new ArrayList<>(pageTotal);
        for (int i = 2; i <= pageTotal; i++) {
            //开启多个线程执行
            int finalI = i;
            CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {

                JSONObject jso_obj = null;
                try {
                    jso_obj = debugger(finalI);
                } catch (IOException e) {
                    LOGGER.error(e.toString());
                    return 0;
                }
                if (jso_obj == null)
                    return 0;
                JSONObject jso_data2 = jso_obj.getJSONObject("data");
                JSONArray jsa_singerList2 = jso_data2.getJSONArray("singerlist");
                List<Singer> singers2 = handlerSingerList(jsa_singerList2);
                resultHandler.handler(singers2);
                return singers2.size();
            }, threadPoolTaskExecutor);
            futures.add(future);
        }
        for (CompletableFuture<Integer> future : futures) {
            try {
                calculationTotal(future.get());
            } catch (InterruptedException e) {
                LOGGER.error(e.toString());
            } catch (ExecutionException e) {
                LOGGER.error(e.toString());
            }
        }
        LOGGER.info("总爬取数量 :" + total + "总页数 :" + pageTotal);
    }

    public JSONObject debugger(int pageIndex) throws IOException {
        RequestTemplate requestTemplate = new RequestTemplate();
        requestTemplate.setGroup(group);
        requestTemplate.setModule(module);
        requestTemplate.setMethod(method);
        String param = buildParam(pageIndex);
        requestTemplate.setParam(param);
        String data = requestTemplate.toString();
        String sign = QqEncrypt.getSign(data);
        String url = ConfigConstant.baseUrl + "sign=" + sign + "&data=" + data;
        Connection connection = Jsoup.connect(url);
        Connection.Request request = connection.request();
        Connection.Response response = connection.execute();
        JSONObject root = ResponseHandler.getData(request, response, group);
        return root;
    }

    public List<Singer> handlerSingerList(JSONArray singerList) {
        JSONObject jsonObject = null;
        Singer singer = null;
        List<Singer> singers = new ArrayList<>(value_sin);
        for (ListIterator<Object> iterator = singerList.listIterator(); iterator.hasNext(); ) {
            jsonObject = (JSONObject) iterator.next();
            singer = new Singer();
            int singer_id = jsonObject.getIntValue("singer_id");
            singer.setPlatId(singer_id);
            String singer_name = jsonObject.getString("singer_name");
            singer.setName(singer_name);
            String singer_pic = jsonObject.getString("singer_pic");
            singer.setPic(singer_pic);
            String singer_mid = jsonObject.getString("singer_mid");
            singer.setMid(singer_mid);
            singers.add(singer);
        }
        return singers;
    }


    /**
     * 根据pageindex 构建 请求参数
     *
     * @param pageIndex
     * @return
     */
    public String buildParam(int pageIndex) {
        String param = new String(PARAM_TEMPLETE);
        if (pageIndex > 1) {
            param = param.replace(param_sin, String.valueOf((pageIndex - 1) * value_sin));
        } else {
            param = param.replace(param_sin, String.valueOf(0));
        }
        param = param.replace(param_cur_page, String.valueOf(pageIndex));
        return param;
    }

    public interface ResultHandler {
        void handler(List<Singer> singers);
    }

    synchronized void calculationTotal(int total) {
        this.total += total;
    }


}
