package cn.com.platform.web.context;

import java.io.IOException;
import java.util.Date;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.common.manager.AsyncManager;
import cn.com.platform.common.manager.factory.AsyncFactory;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.framework.util.FwDateUtils;
import cn.com.platform.util.ServletUtils;

/**
 * 同步Session数据到Db
 * 
 * @author 唐友成
 * @date 2018-12-19
 */
public class SyncOnlineSessionFilter implements Filter {

  /**
   * 同步会话数据到DB 一次请求最多同步一次 防止过多处理 需要放到过滤器之前
   */
  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain chain) throws IOException, ServletException {

    // HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

    if (!StringUtils.isEmpty(UserSessionUtils.getUserName())) {
      // 最近一次同步时间
      Date lastSyncDate = UserSessionUtils.getUserSessionInfo().getLastSyncDate();
      if (lastSyncDate != null) {
        long deltaTime = ServletUtils.getSession().getLastAccessedTime() - lastSyncDate.getTime();
        int dbSyncPeriod =
            NumberUtils.parseNumber(CommonConstantsIF.ONLINE_USER_DB_SYN_CPERIOD, Integer.class);
        // 时间差不足 无需同步
        if (deltaTime > dbSyncPeriod * 60 * 1000) {
          AsyncManager.me().execute(AsyncFactory.syncSessionToDb(FlagStatus.Enable.toString()));
          UserSessionUtils.getUserSessionInfo().setLastSyncDate(FwDateUtils.getSysDate());
        }
      } else {
        AsyncManager.me().execute(AsyncFactory.syncSessionToDb(FlagStatus.Enable.toString()));
        UserSessionUtils.getUserSessionInfo().setLastSyncDate(FwDateUtils.getSysDate());
      }
    }
    chain.doFilter(servletRequest, servletResponse);
  }
}
