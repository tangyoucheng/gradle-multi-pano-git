/*
 * Copyright(c) 2011
 */
package cn.com.platform.web.context;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * ContextListener.
 * 
 * @author 唐友成
 * @date 2018-12-07
 */
@WebListener
public class JodConverterContextListener implements ServletContextListener {

  private static final Logger logger = LoggerFactory.getLogger(JodConverterContextListener.class);

  /**
   * 当Servlet 容器启动Web 应用时调用该方法。 <br>
   * 在调用完该方法之后，容器再对Filter 初始化， <br>
   * 并且对那些在Web 应用启动时就需要被初始化的Servlet 进行初始化。
   * 
   * @param _event event
   */
  @Override
  public void contextInitialized(ServletContextEvent _event) {

    try {
      JodWebappContext.init(_event.getServletContext());
    } catch (Exception e1) {
      logger.error(e1.getMessage(), e1);
    } finally {
      logger.debug("contextInitialized: ".concat(_event.getServletContext().getRealPath(""))
          .concat(" - ").concat(_event.getServletContext().getServerInfo()));
    }

  }

  /**
   * 当Servlet 容器终止Web 应用时调用该方法。 <br>
   * 在调用该方法之前，容器会先销毁所有的Servlet 和Filter 过滤器。
   * 
   * @param _event event
   */
  @Override
  public void contextDestroyed(ServletContextEvent _event) {
    try {
      JodWebappContext.destroy(_event.getServletContext());
    } catch (Exception e1) {
      logger.error(e1.getMessage(), e1);
    } finally {
      logger.debug("contextDestroyed: ".concat(_event.getServletContext().getRealPath("")));
    }

  }


}
