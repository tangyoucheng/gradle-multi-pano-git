package cn.com.platform.common.datasource.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import cn.com.platform.common.datasource.DatabaseContextHolder;
import cn.com.platform.common.datasource.DatabaseType;

/**
 * 选择数据源
 * 
 * @author 唐友成
 * @date 2018-08-17
 *
 */
public class DatasourceInterceptor extends HandlerInterceptorAdapter {

  /**
   * 事前処理
   */
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    // 切换到会话中保持的datasource
    if (!StringUtils.isEmpty(request.getSession().getAttribute("datasource"))) {
      DatabaseContextHolder
          .setDatabaseType((DatabaseType) request.getSession().getAttribute("datasource"));
    }
    return true;
  }


}
