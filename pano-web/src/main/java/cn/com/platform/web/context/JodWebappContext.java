package cn.com.platform.web.context;

import java.io.File;
import javax.servlet.ServletContext;
import org.apache.commons.lang3.math.NumberUtils;
import org.jodconverter.DocumentConverter;
import org.jodconverter.LocalConverter;
import org.jodconverter.office.LocalOfficeManager;
import org.jodconverter.office.OfficeException;
import org.jodconverter.office.OfficeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import cn.com.platform.common.config.ConfigProperties;

public class JodWebappContext {

  private static final Logger LOGGER = LoggerFactory.getLogger(JodWebappContext.class);
  private static final String KEY = JodWebappContext.class.getName();

  private final OfficeManager officeManager;
  private final DocumentConverter documentConverter;

  /**
   * Creates a new WebappContext using the specified servlet context.
   *
   * @param servletContext the servlet context that contains properties used to create a JOD
   *        document converter.
   */
  public JodWebappContext(final ServletContext servletContext) {

    // office安装路径
    final String officeHomeParam = ConfigProperties.init().getJodconverterOfficeHome();
    File officeHomeDir = new File(officeHomeParam);
    if (!officeHomeDir.exists() || !officeHomeDir.isDirectory()) {
      officeManager = null;
      documentConverter = null;
      return;
    }

    final LocalOfficeManager.Builder builder = LocalOfficeManager.builder();
    // office安装路径
    builder.officeHome(officeHomeParam);
    // 端口
    int[] portNumbers = null;
    final String officePortParam = ConfigProperties.init().getJodconverterPortNumbers();
    String[] portNumberArray = officePortParam.split(",");
    portNumbers = new int[portNumberArray.length];
    for (int i = 0; i < portNumberArray.length; i++) {
      portNumbers[i] = NumberUtils.toInt(portNumberArray[i]);
    }
    if (officePortParam != null) {
      builder.portNumbers(portNumbers);
    }
    // 临时文件目录
    final String workingDir = ConfigProperties.init().getJodconverterWorkingDir();
    builder.workingDir(workingDir);

    officeManager = builder.build();
    documentConverter = LocalConverter.make(officeManager);
  }

  protected static void init(final ServletContext servletContext) throws OfficeException {
    final JodWebappContext instance = new JodWebappContext(servletContext);
    servletContext.setAttribute(KEY, instance);
    if (instance.officeManager != null) {
      instance.officeManager.start();
    }
  }

  protected static void destroy(final ServletContext servletContext) throws OfficeException {
    final JodWebappContext instance = (JodWebappContext) servletContext.getAttribute(KEY);
    if (instance.officeManager != null) {
      instance.officeManager.stop();
    }
  }

  /**
   * Gets the WebappContext form the specified servlet context.
   *
   * @param servletContext the servlet context that contains the WebappContext.
   * @return the WebappContext.
   */
  public static JodWebappContext get() {
    // ServletContext servletContext =
    // ContextLoader.getCurrentWebApplicationContext().getServletContext();
    ServletContext servletContext =
        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
            .getServletContext();
    return (JodWebappContext) servletContext.getAttribute(KEY);
  }


  /**
   * Gets the document converter of the context.
   *
   * @return the context's document converter.
   */
  public OfficeManager getOfficeManager() {
    return officeManager;
  }

  /**
   * Gets the document converter of the context.
   *
   * @return the context's document converter.
   */
  public DocumentConverter getDocumentConverter() {
    return documentConverter;
  }
}
