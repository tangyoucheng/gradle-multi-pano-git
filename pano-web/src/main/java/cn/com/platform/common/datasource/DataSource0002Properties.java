package cn.com.platform.common.datasource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("spring.datasource0002.datasource")
public class DataSource0002Properties extends AbstractDatasourceProperties{

}
