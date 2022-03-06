import cn.fan.AppFile;
import cn.fan.dao.ResourceMapper;
import cn.fan.model.file.Resource;
import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author fanduanjin
 * @program orange-music
 * @Classname ResourceMapperTest
 * @Description
 * @Date 2021/12/7 20:26
 * @Created by fanduanjin
 */
@SpringBootTest(classes = AppFile.class)
public class ResourceMapperTest {
    @Autowired
    ResourceMapper resourceMapper;

    @Test
    void testInsert(){
        Resource resource=new Resource();
        resource.setResourceId(1231231232L);
        resource.setDfsPath("test");
        resource.setSourcePath("testsss");
        resourceMapper.insert(resource);
        System.out.println(resource.getId());
    }

    @Test
    void testSelectOne(){
        //12604
        Resource resource= resourceMapper.selectOne(12604);
        System.out.println(JSON.toJSONString(resource));
    }

    @Test
    void testSelectList(){
        List<Resource> resources=resourceMapper.selectList(0,10);
        System.out.println(JSON.toJSONString(resources));
    }

    @Test
    void testUpdate(){
        Resource resource= resourceMapper.selectOne(12604);
        resource.setSourcePath("sfadsfda");
        resourceMapper.update(resource);
    }

    @Test
    void testDelete(){
        resourceMapper.delete(12604);
    }

}
