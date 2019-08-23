package cn.com.platform.common.datasource;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import lombok.Data;

@Data
public class AbstractDatasourceProperties {

  @Autowired
  DatasourceBaseProperties dataSourceProperties;

  protected String url;
  protected String username;
  protected String password;

  /**
   * tomcat数据源信息。
   * 
   * @return
   */
  @Bean
  public DataSource createDataSourceBean() {
    DataSource ds = new org.apache.tomcat.jdbc.pool.DataSource();
    ds.setDriverClassName(this.dataSourceProperties.getDriverClassName());

    ds.setMaxActive(this.dataSourceProperties.getMaxActive());
    ds.setMaxIdle(this.dataSourceProperties.getMaxIdle());
    ds.setMinIdle(this.dataSourceProperties.getMinIdle());
    ds.setInitialSize(this.dataSourceProperties.getInitialSize());

    ds.setValidationQuery(this.dataSourceProperties.getValidationQuery());
    ds.setTestOnBorrow(this.dataSourceProperties.getTestOnBorrow());
    ds.setTestWhileIdle(this.dataSourceProperties.getTestWhileIdle());
    ds.setTimeBetweenEvictionRunsMillis(
        this.dataSourceProperties.getTimeBetweenEvictionRunsMillis());
    ds.setMinEvictableIdleTimeMillis(this.dataSourceProperties.getMinEvictableIdleTimeMillis());

    ds.setUrl(this.getUrl());
    ds.setUsername(this.getUsername());
    ds.setPassword(this.getPassword());
    return ds;
  }

  /**
   * 阿里数据源信息。
   * 
   * @return
   */
  @Bean(destroyMethod = "close", initMethod = "init") 
  public DruidDataSource createDruidDataSourceBean() {
    DruidDataSource ds = DruidDataSourceBuilder.create().build();
    ds.setUrl(this.getUrl());
    ds.setUsername(this.getUsername());
    ds.setPassword(this.getPassword());
    return ds;
  }
}
