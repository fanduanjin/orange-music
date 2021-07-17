import cn.fan.AppCommon;
import cn.fan.api.common.IPropertyService;
import cn.fan.model.common.Property;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest( classes = AppCommon.class)
public class CommonServiceTest {

    @DubboReference
    IPropertyService propertyService;

    @Test
    public void testKeyGenerator(){
        Property property=new Property();
        property.setValue("1");
        property.setKey("ddd");
        property.setEntityType(Property.class.getName());
        propertyService.insert(property);
        System.out.println(property.getId());
    }
}
