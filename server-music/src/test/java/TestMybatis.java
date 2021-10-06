import cn.fan.AppMusic;
import cn.fan.api.common.IPropertyService;
import cn.fan.api.music.ISingerService;
import cn.fan.model.common.Property;
import com.alibaba.fastjson.JSON;
import org.apache.dubbo.common.serialize.support.SerializationOptimizer;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.text.StyledEditorKit;

/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-05-08 19:59
 */
@SpringBootTest(classes = AppMusic.class)
public class TestMybatis {

    @DubboReference
    ISingerService singerService;

    @DubboReference
    IPropertyService propertyService;
    @Test
    public void test(){
        long time=System.currentTimeMillis();
        System.out.println("开始时间"+time);
        boolean bl= singerService.existsByPlayId(4607);
        System.out.println("结束时间"+System.currentTimeMillis());
        System.out.println("使用时间2 : "+bl+(System.currentTimeMillis()-time));
        time=System.currentTimeMillis();
        Object ob= singerService.getByPlatId(4607);
        System.out.println("使用时间 : "+(System.currentTimeMillis()-time));
        System.out.println(JSON.toJSONString(ob));
        Property property=propertyService.getById(1);
        System.out.println(JSON.toJSONString(property));
    }
}
