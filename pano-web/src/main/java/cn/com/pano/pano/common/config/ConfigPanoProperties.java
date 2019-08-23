package cn.com.pano.pano.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;
import lombok.Data;

/**
 * 外部配置文件。
 * 
 * @author 唐友成
 * @date 2018-08-22
 *
 */
@Data
@Component
// @PropertySources({
// @PropertySource(value = "classpath:config/sys_config.properties",
// ignoreResourceNotFound = true),
// @PropertySource(value = "classpath:config/pano_config.properties",
// ignoreResourceNotFound = true)})
@PropertySources({@PropertySource(value = "classpath:config/pano_config.properties",
    ignoreResourceNotFound = true)})
public class ConfigPanoProperties {

  /** 场景图片缩略图的宽。 */
  @Value("${pano.image.thumbnail.width}")
  private String panoImageThumbnailWidth;
  /** 场景图片缩略图的高。 */
  @Value("${pano.image.thumbnail.height}")
  private String panoImageThumbnailHeight;

  /** 在线用户同步到数据库时间间隔 。 */
  @Value("${online.user.dbSyncPeriod}")
  private String dbSyncPeriod;


  private static final ThreadLocal<ConfigPanoProperties> ConfigPropertiesHolder =
      new ThreadLocal<>();

  /**
   * 做成ConfigProperties实例。
   * 
   * @return ConfigProperties实例
   */
  public static ConfigPanoProperties init() {
    if (ConfigPropertiesHolder.get() == null) {
      ConfigPropertiesHolder
          .set((new AnnotationConfigApplicationContext(ConfigPanoProperties.class))
              .getBean(ConfigPanoProperties.class));
    }
    return ConfigPropertiesHolder.get();
  }
}
