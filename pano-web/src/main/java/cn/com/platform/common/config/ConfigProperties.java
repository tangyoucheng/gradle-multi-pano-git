package cn.com.platform.common.config;

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
@PropertySources({
    @PropertySource(value = "classpath:config/sys_config.properties",
        ignoreResourceNotFound = true),
    @PropertySource(value = "classpath:config/pano_config.properties",
        ignoreResourceNotFound = true)})
public class ConfigProperties {

  /** 文件存储根路径。 */
  @Value("${storages.path}")
  private String storagesPath;
  /** publc.storage路径。 */
  @Value("${public.storage.path}")
  private String storagePublcPath;
  /** publc.storage路径。 */
  @Value("${webapp.storage.path}")
  private String storageWebappPath;

  /** 房间图片缩略图的宽。 */
  @Value("${room.image.thumbnail.width}")
  private String roomImageThumbnailWidth;
  /** 房间图片缩略图的高。 */
  @Value("${room.image.thumbnail.height}")
  private String roomImageThumbnailHeight;
  /** 房间图片的宽。 */
  @Value("${room.image.width}")
  private String roomImageWidth;
  /** 房间图片的高。 */
  @Value("${room.image.height}")
  private String roomImageHeight;

  /** 评论图片缩略图的尺寸。 */
  @Value("${comment.image.thumbnail.size}")
  private String commentImageThumbnailSize;
  /** 评论图片的尺寸。 */
  @Value("${comment.image.size}")
  private String commentImageSize;

  /** jodconverter.officeHome路径。 */
  @Value("${jodconverter.officeHome}")
  private String jodconverterOfficeHome;
  /** jodconverter.portNumbers。 */
  @Value("${jodconverter.portNumbers}")
  private String jodconverterPortNumbers;
  /** jodconverter.workingDir路径。 */
  @Value("${jodconverter.workingDir}")
  private String jodconverterWorkingDir;

  /** 在线用户同步到数据库时间间隔 。 */
  @Value("${online.user.dbSyncPeriod}")
  private String dbSyncPeriod;


  private static final ThreadLocal<ConfigProperties> ConfigPropertiesHolder = new ThreadLocal<>();

  /**
   * 做成ConfigProperties实例。
   * @return ConfigProperties实例
   */
  public static ConfigProperties init() {
    if (ConfigPropertiesHolder.get() == null) {
      ConfigPropertiesHolder.set((new AnnotationConfigApplicationContext(ConfigProperties.class))
          .getBean(ConfigProperties.class));
    }
    return ConfigPropertiesHolder.get();
  }
}
