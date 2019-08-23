package cn.com.platform.web.context;

import java.io.File;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileSystemUtils;
import cn.com.platform.framework.common.StandardConstantsIF;
import cn.com.platform.util.FwFileUtils;

/**
 * 会话临时目录监听
 * 
 * @author 唐友成
 * @date 2018-10-29
 */
@WebListener
public class SessionScopeStorageListener implements HttpSessionListener {

  private static final Logger logger = LoggerFactory.getLogger(SessionScopeStorageListener.class);

  @Override
  public void sessionCreated(final HttpSessionEvent se) {}

  @Override
  public void sessionDestroyed(final HttpSessionEvent se) {

    // if (RequestContextHolder.getRequestAttributes() == null) {
    // return;
    // }
    HttpSession session = se.getSession();
    ServletContext servletContext = session.getServletContext();

    // 删除AP服务器上当前会话的临时文件
    File selfAppSessionFolder = new File(FwFileUtils.getAbsolutePath(
        servletContext.getRealPath(System.getProperty("file.separator")),
        StandardConstantsIF.APP_SERVER_TEMP_SESSION_FOLDER, session.getId()));
    if (selfAppSessionFolder.exists() && selfAppSessionFolder.isDirectory()) {
      FileSystemUtils.deleteRecursively(selfAppSessionFolder);
    }

    // 删除AP服务器上会话的临时文件
    File appServerTempFolder = new File(FwFileUtils.getAbsolutePath(
        servletContext.getRealPath(System.getProperty("file.separator")),
        StandardConstantsIF.APP_SERVER_TEMP_SESSION_FOLDER));
    if (appServerTempFolder.exists() && appServerTempFolder.isDirectory()) {
      File[] appServerTempFiles = appServerTempFolder.listFiles();
      if (appServerTempFiles != null) {
        for (File file : appServerTempFiles) {
          // 最后更新时间
          LocalDateTime lastModified = LocalDateTime
              .ofInstant(Instant.ofEpochMilli(file.lastModified()), ZoneId.systemDefault());
          // [最后一次修改时间+(session-timeout)]在当前时间之前的话就直接删除
          if (lastModified.plusSeconds(se.getSession().getMaxInactiveInterval())
              .isBefore(LocalDateTime.now())) {
            FileSystemUtils.deleteRecursively(file);
          }
        }
      }
    }
  }

}
