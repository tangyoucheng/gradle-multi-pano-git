package cn.com.platform.common.datasource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("spring.datasource0001.datasource")
public class DataSource0001Properties extends AbstractDatasourceProperties{
}
