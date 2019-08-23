package cn.com.platform.common.datasource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

@Data
@Component
@ConfigurationProperties("spring.abstract.datasource")
public class DatasourceBaseProperties {
  private String driverClassName;
  private String sqlScriptEncoding;
  private Integer maxActive;
  private Integer maxIdle;
  private Integer minIdle;
  private Integer initialSize;
  private String validationQuery;
  private Boolean testOnBorrow;
  private Boolean testWhileIdle;
  private Integer timeBetweenEvictionRunsMillis;
  private Integer minEvictableIdleTimeMillis;
}
