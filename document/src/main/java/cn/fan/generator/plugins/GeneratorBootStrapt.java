package cn.fan.generator.plugins;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-05-16 20:28
 */
public class GeneratorBootStrapt
{
    public static void main(String[] args) throws Exception {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        InputStream configInputStream = GeneratorBootStrapt.class.getResourceAsStream("/generatorConfig.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configInputStream);
        configInputStream.close();
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(new ProgressCallback() {
            @Override
            public void introspectionStarted(int i) {

            }

            @Override
            public void generationStarted(int i) {

            }

            @Override
            public void saveStarted(int i) {

            }

            @Override
            public void startTask(String s) {
                System.out.println(s);
            }

            @Override
            public void done() {
                for(String warnging:warnings){
                    System.out.println(warnging);
                }
            }

            @Override
            public void checkCancel() throws InterruptedException {

            }
        });
    }
}
