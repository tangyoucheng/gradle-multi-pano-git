package cn.com.platform.common.config;

import java.nio.charset.StandardCharsets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * Thymeleaf配置信息。
 * 
 * @author 唐友成
 * @date 2019-07-01
 *
 */
@Configuration
public class ThymeleafConfig {

  /**
   * 做成SpringResourceTemplateResolver实例。
   * 
   * @return SpringResourceTemplateResolver实例
   */
  @Bean
  public SpringResourceTemplateResolver templateResolver() {
    SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
    // 模板文件所在的文件夹【/WEB-INF/views】
    templateResolver.setPrefix("/WEB-INF/views");
    // 文件名后缀
    templateResolver.setSuffix(".html");
    // 模板类型
    templateResolver.setTemplateMode(TemplateMode.HTML);
    // 开发时关闭缓存,不然没法看到实时页面
    templateResolver.setCacheable(false);
    templateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
    return templateResolver;
  }

  /**
   * 做成SpringTemplateEngine实例。
   * 
   * @return SpringTemplateEngine实例
   */
  @Bean
  public SpringTemplateEngine templateEngine() {
    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    templateEngine.addDialect(new Java8TimeDialect());
    templateEngine.setTemplateResolver(templateResolver());
    templateEngine.setEnableSpringELCompiler(true);
    return templateEngine;
  }

  /**
   * 做成ThymeleafViewResolver实例。
   * 
   * @return ThymeleafViewResolver实例
   */
  @Bean
  public ThymeleafViewResolver viewResolver() {
    ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
    viewResolver.setTemplateEngine(templateEngine());
    viewResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
    // 从Controller返回的View名，匹配下列规则的场合，使用ThymeleafViewResolver
    viewResolver.setViewNames(new String[] {"/thymeleaf/*"});
    // 使用ViewResolver的顺序，比JSP优先。
    viewResolver.setOrder(2);
    return viewResolver;
  }
}
