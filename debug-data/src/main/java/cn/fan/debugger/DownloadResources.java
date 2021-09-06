package cn.fan.debugger;

import cn.fan.api.file.IFileService;
import cn.fan.api.file.IResourceService;
import cn.fan.model.constanst.DebuggerConstant;
import cn.fan.model.file.Resource;
import cn.hutool.core.io.file.FileNameUtil;
import com.alibaba.fastjson.JSON;
import org.apache.dubbo.config.annotation.DubboReference;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-05-08 23:39
 */
@Component
@RabbitListener(queuesToDeclare = @Queue(DebuggerConstant.queue_dowload_resource))
public class DownloadResources {
    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadResources.class);

    @DubboReference
    private IFileService fileService;

    @DubboReference
    private IResourceService resourceService;

    @RabbitHandler
    void receiver(Resource resource) throws IOException {
        LOGGER.info("开始下载资源文:" + resource.getSourcePath());
        Connection.Response response = Jsoup.connect(resource.getSourcePath()).ignoreContentType(true).
                execute();
        String path = fileService.uploadFile(response.bodyAsBytes(), FileNameUtil.getSuffix(resource.getSourcePath()));
        resource.setDfsPath(path);
        resourceService.insertResource(resource);
        LOGGER.info("资源下载成功:" + JSON.toJSONString(resource));
    }
}
