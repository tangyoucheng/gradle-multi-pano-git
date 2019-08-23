package cn.com;

// import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import cn.com.platform.common.datasource.DataSource0000Properties;
import cn.com.platform.common.datasource.DataSource0001Properties;
import cn.com.platform.common.datasource.DataSource0002Properties;

// @MapperScan("cn.com.cis.*.mapper")
// @SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
// SecurityAutoConfiguration.class})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableConfigurationProperties({DataSourceProperties.class, DataSource0000Properties.class,
    DataSource0001Properties.class, DataSource0002Properties.class})
// @SpringBootApplication
@ServletComponentScan
// public class ShWebJspApplication extends WebMvcAutoConfiguration {
//@ComponentScan(basePackages = {"cn.com.pano", "cn.com.platform"})
public class PanoWebJspApplication extends SpringBootServletInitializer {
  public static void main(String[] args) {
    SpringApplication.run(PanoWebJspApplication.class, args);
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    return builder.sources(PanoWebJspApplication.class);
  }
}
