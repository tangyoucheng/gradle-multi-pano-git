package cn.com.platform.common.datasource;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * springboot集成mybatis的基本入口。<br>
 * 1）创建数据源(如果采用的是默认的tomcat-jdbc数据源，则不需要)。<br>
 * 2）创建SqlSessionFactory。。<br>
 * 3）配置事务管理器，除非需要使用事务，否则不用配置。<br>
 */
// @Component
@Configuration // 该注解类似于spring配置文件
// @MapperScan(basePackages = "com.xxx.firstboot.mapper")
@MapperScan({"cn.com.pano.*.mapper", "cn.com.platform.*.mapper"})
@EnableTransactionManagement // 加上这个注解，使得支持事务
public class MyBatisConfig {
  @Autowired
  private DataSource0000Properties dataSource0000Properties;
  @Autowired
  private DataSource0001Properties dataSource0001Properties;
  @Autowired
  private DataSource0002Properties dataSource0002Properties;

  /**
   * 创建数据源(数据源的名称：方法名可以取为XXXDataSource(),XXX为数据库名称,该名称也就是数据源的名称)。
   */
  @Bean(name = "dataSource0000")
  public DataSource dataSource0000() throws Exception {
    // tomcat数据源
    // return dataSource0000Properties.createDataSourceBean();
    // 阿里数据源
    return dataSource0000Properties.createDruidDataSourceBean();
  }

  /**
   * DataSource做成。
   * 
   * @return DataSource
   * @throws Exception 异常的场合
   */
  @Bean(name = "dataSource0001")
  public DataSource dataSource0001() throws Exception {
    // tomcat数据源
    // return dataSource0001Properties.createDataSourceBean();
    // 阿里数据源
    return dataSource0001Properties.createDruidDataSourceBean();
  }

  /**
   * DataSource做成。
   * 
   * @return DataSource
   * @throws Exception 异常的场合
   */
  @Bean(name = "dataSource0002")
  public DataSource dataSource0002() throws Exception {
    // tomcat数据源
    // return dataSource0002Properties.createDataSourceBean();
    // 阿里数据源
    return dataSource0002Properties.createDruidDataSourceBean();
  }

  /**
   * DynamicDataSource设定。
   * 
   * @Primary 该注解表示在同一个接口有多个实现类可以注入的时候，默认选择哪一个，而不是让@autowire注解报错
   * @Qualifier 根据名称进行注入，通常是在具有相同的多个类型的实例的一个注入（例如有多个DataSource类型的实例）
   */
  @Bean
  @Primary
  public DynamicDataSource dataSource(@Qualifier("dataSource0000") DataSource dataSource0000,
      @Qualifier("dataSource0001") DataSource dataSource0001,
      @Qualifier("dataSource0002") DataSource dataSource0002) {
    Map<Object, Object> targetDataSources = new HashMap<>();
    targetDataSources.put(DatabaseType.datasource0000, dataSource0000);
    // targetDataSources.put(DatabaseType.datasource0001, dataSource0001);
    // targetDataSources.put(DatabaseType.datasource0002, dataSource0002);

    DynamicDataSource dataSource = new DynamicDataSource();
    dataSource.setTargetDataSources(targetDataSources);// 该方法是AbstractRoutingDataSource的方法
    dataSource.setDefaultTargetDataSource(dataSource0000);// 默认的datasource设置为dataSource0000

    return dataSource;
  }

  /**
   * 根据数据源创建SqlSessionFactory。
   */
  @Bean
  public SqlSessionFactory sqlSessionFactory(DynamicDataSource ds) throws Exception {
    SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
    sessionFactory.setDataSource(ds);// 指定数据源(这个必须有，否则报错)
    sessionFactory
        .setMapperLocations(((ResourcePatternResolver) new PathMatchingResourcePatternResolver())
            .getResources("classpath:cn/com/**/**/*.xml"));
    // 下边两句仅仅用于*.xml文件，如果整个持久层操作不需要使用到xml文件的话（只用注解就可以搞定），则不加
    // fb.setTypeAliasesPackage(env.getProperty("mybatis.typeAliasesPackage"));// 指定基包
    // fb.setMapperLocations(new PathMatchingResourcePatternResolver()
    // .getResources(env.getProperty("mybatis.mapperLocations")));

    return sessionFactory.getObject();
  }

  /**
   * 配置事务管理器。
   */
  @Bean
  public PlatformTransactionManager transactionManager(DynamicDataSource dataSource)
      throws Exception {
    return new DataSourceTransactionManager(dataSource);
  }

  // @Bean
  // public HandlerInterceptor datasourceInterceptor() {
  // return new DatasourceInterceptor();
  // }
  //
  // @Bean
  // public MappedInterceptor interceptorMapping() {
  // return new MappedInterceptor(new String[] {"/**"}, datasourceInterceptor());
  // }

}
