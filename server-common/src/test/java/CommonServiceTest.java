import cn.fan.AppCommon;
import cn.fan.api.common.IPropertyService;
import cn.fan.dao.PropertyMapper;
import cn.fan.model.common.Property;
import cn.fan.model.music.Song;
import com.alibaba.fastjson.JSON;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author fanduanjin
 * @program orange-music
 * @Classname CommonServiceTest
 * @Description
 * @Date 2021/12/6 21:16
 * @Created by fanduanjin
 */

@SpringBootTest(classes = AppCommon.class)
public class CommonServiceTest {

    @Autowired
    PropertyMapper propertyService;


}
