package cn.com.platform.common.datasource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("spring.datasource0000.datasource")
public class DataSource0000Properties extends AbstractDatasourceProperties{

}
