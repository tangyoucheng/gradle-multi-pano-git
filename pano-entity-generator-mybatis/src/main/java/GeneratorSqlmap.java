import java.io.File;
import java.util.List;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import com.google.common.collect.Lists;

public class GeneratorSqlmap {
  
  /**
   * 执行main方法以生成代码。
   * 
   * @param args 参数
   */
  public static void main(String[] args) {
    try {
      GeneratorSqlmap generatorSqlmap = new GeneratorSqlmap();
      generatorSqlmap.generatorPano("/common-mybatis-generator-config-template-pano.xml");
      generatorSqlmap.generatorPano("/common-mybatis-generator-config-template-pano-example.xml");
      generatorSqlmap.generatorPano("/common-mybatis-generator-config-template-platform.xml");
      generatorSqlmap
          .generatorPano("/common-mybatis-generator-config-template-platform-example.xml");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 自动生成持久层代码。
   * 
   * @param fileName 文件名
   * @throws Exception 异常的场合
   */
  public void generatorPano(String fileName) throws Exception {
    List<String> warnings = Lists.newArrayList();
    boolean overwrite = true;
    // 指定配置文件
    String path = this.getClass().getResource(fileName).getPath();
    File configFile = new File(path);

    ConfigurationParser cp = new ConfigurationParser(warnings);
    Configuration config = cp.parseConfiguration(configFile);
    DefaultShellCallback callback = new DefaultShellCallback(overwrite);
    MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
    myBatisGenerator.generate(null);
  }

}
