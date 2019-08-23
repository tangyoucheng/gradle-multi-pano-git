package cn.com.platform.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.JstlView;

/**
 * 视图层全体配置。
 * 
 * @author 唐友成
 * @date 2019-07-01
 *
 */
@Configuration
// @EnableWebMvc 会把control修改成单例模式
// 导入ThymeleafConfig。
@Import({ThymeleafConfig.class})
public class WebMvcConfig implements WebMvcConfigurer {

  /**
   * ViewResolverRegistry信息更新。
   */
  public void configureViewResolvers(ViewResolverRegistry registry) {
    registry.jsp().prefix("/WEB-INF/views").suffix(".jsp").viewNames(new String[] {"/jsp/*"})
        .viewClass(JstlView.class);
    registry.order(3);
  }

  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    configurer.enable();
  }

  /**
   * 配置静态访问资源。<br>
   * 通过addResourceHandler添加映射路径，然后通过addResourceLocations来指定路径
   */
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/res-act/plugin/**", "/res-act/static/**")
        .addResourceLocations("classpath:/res-act/plugin/", "classpath:/res-act/static/");
    registry.addResourceHandler("/res-act/ftl/**").addResourceLocations("classpath:/res-act/ftl/");
  }
}
