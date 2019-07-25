package tools.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MyBatisGeneratorApp {
    public void generator() throws Exception {

        String path = MyBatisGeneratorApp.class.getResource("/generatorConfig.xml").getPath();
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        // 指定 逆向工程配置文件
		File configFile = new File(path);
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(configFile);
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		myBatisGenerator.generate(null);

	}

	public static void main(String[] args) throws Exception {
		try {
			MyBatisGeneratorApp generatorSqlmap = new MyBatisGeneratorApp();
			generatorSqlmap.generator();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}