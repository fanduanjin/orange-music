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
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest(classes = AppCommon.class)
public class CommonMapperTest {


    @Autowired
    PropertyMapper propertyMapper;

    private final String entityType = Song.class.getName();

    private final int entityId = 999999999;

    @Test
    public void insert() {
        Property property = new Property();
        property.setKey("test");
        property.setValue("value");
        property.setEntityType(entityType);
        property.setEntityId(entityId);
        int row = propertyMapper.insert(property);
        Assert.isTrue(row == 1, "插入失败");
    }

    @Test
    public void update() {
        List<Property> properties = propertyMapper.selectListByEntityTypeWithEntityId(entityType, entityId, entityType.hashCode());
        Assert.notNull(properties, "未查找到任何数据");
        Property property = properties.get(0);
        property.setValue("test update");
        int row= propertyMapper.update(property);
        Assert.isTrue(row>0,"没有修改任何数据");
    }


}
