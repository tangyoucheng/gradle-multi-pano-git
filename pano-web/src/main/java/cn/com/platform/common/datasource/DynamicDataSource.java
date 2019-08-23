package cn.com.platform.common.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源（需要继承AbstractRoutingDataSource）。
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

  @Override
  protected Object determineCurrentLookupKey() {
    if (DatabaseContextHolder.getDatabaseType() == null) {
      return DatabaseType.datasource0000;
    }
    return DatabaseContextHolder.getDatabaseType();
  }
}
