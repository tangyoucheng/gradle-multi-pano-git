package cn.com.platform.framework.common.log.layout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.ThrowableProxy;
import ch.qos.logback.core.status.ErrorStatus;
import ch.qos.logback.core.status.StatusManager;
import cn.com.platform.framework.common.unique_id.UniqueIdGenerator;

/**
 * 异常发生时，保存异常的内容（java的Trace）到其他文件的扩展类<br/>
 * 使用这个类的场合，下记的内容会被保存到文件<br/>
 * <ol>
 * <li>日志ID</li>
 * <li>发生日期、时间</li>
 * <li>线程名</li>
 * <li>线程组</li>
 * <li>Java的Trace</li>
 * </ol>
 */
public class OutputStackTracePatternLayout extends PatternLayout {

  private static final String KEY_4_GENERATING_TIME = "log.generating.time";

  private static final String KEY_4_LOG_ID = "log.id";

  private static final String KEY_4_LOG_LEVEL = "log.level";

  private static final String KEY_4_LOGGER_NAME = "log.logger.name";

  private static final String KEY_4_THREAD_GROUP = "log.thread.group";

  private static final String KEY_4_THREAD_ID = "log.thread.id";

  private static final String KEY_4_LOG_MASSEGE = "log.message";

  private boolean enableOutputStackTrace = true;

  private String stackTraceDir = null;

  private String stackTraceFilename = null;

  /*
   * (non-Javadoc)
   * 
   * @see ch.qos.logback.classic.PatternLayout#doLayout(ch.qos.logback.classic.spi.ILoggingEvent)
   */
  @Override
  public String doLayout(final ILoggingEvent event) {

    String result = super.doLayout(event);

    if (enableOutputStackTrace == false) {
      return result;
    }

    final IThrowableProxy proxy = event.getThrowableProxy();
    if (!(proxy instanceof ThrowableProxy)) {
      return result;
    }

    final ThrowableProxy tProxy = (ThrowableProxy) proxy;
    if (tProxy != null) {
      final Throwable throwable = tProxy.getThrowable();

      PrintWriter printWriter = null;
      try {
        final String dir = this.stackTraceDir + File.separator;

        // 用取得的日志ID生成文件名
        String logId = org.slf4j.MDC.get(KEY_4_LOG_ID);
        if (logId == null) {
          logId = UUID.randomUUID().toString();
        }
        final String fileName = createStackTraceLogFileName(logId);

        // 日志输入的文件
        File logFile = new File(dir + fileName);

        if (logFile.exists()) {
          final String logId2nd = UniqueIdGenerator.getUniqueId();

          final String fileName2nd = createStackTraceLogFileName(logId2nd);
          logFile = new File(dir + fileName2nd);

          // 替换旧日志的ID(！！！严格遵守顺番！！！)
          result = result.replace(logId, logId2nd);
          logId = logId2nd;
        }

        final File parent = logFile.getParentFile();
        if (parent.exists() == false && !parent.mkdirs()) {
          throw new IOException("Failed to create directory : " + parent.getAbsolutePath());
        }
        if (!logFile.createNewFile()) {
          throw new IOException("Failed to create file : " + logFile.getAbsolutePath());
        }

        // Exception的日志输出
        printWriter = new PrintWriter(logFile);

        // 输出日期
        final Date date = new Date(event.getTimeStamp());
        printWriter.println(KEY_4_GENERATING_TIME + "=" + date.toString());

        // 日志级别
        printWriter.println(KEY_4_LOG_LEVEL + "=" + event.getLevel());

        // 日志名
        printWriter.println(KEY_4_LOGGER_NAME + "=" + event.getLoggerName());

        // 日志ID
        printWriter.println(KEY_4_LOG_ID + "=" + logId);

        // 线程名
        final Thread currentThread = Thread.currentThread();
        printWriter.println(KEY_4_THREAD_ID + "=" + currentThread.getName());

        // 线程组
        final String thredgroup = currentThread.getThreadGroup().getName();
        printWriter.println(KEY_4_THREAD_GROUP + "=" + thredgroup);

        // 消息
        printWriter.println(KEY_4_LOG_MASSEGE + "=" + event.getMessage());

        // StackTrace
        printWriter.println();
        throwable.printStackTrace(printWriter);
        printWriter.println();
      } catch (final FileNotFoundException e) {
        e.printStackTrace();
      } catch (final IOException e) {
        e.printStackTrace();
      } catch (final Exception e) {
        final StatusManager sm = getContext().getStatusManager();
        sm.add(new ErrorStatus(e.getMessage(), this, e));
        e.printStackTrace();
      } finally {
        if (printWriter != null) {
          printWriter.close();
        }
      }
    }
    return result;
  }

  private String createStackTraceLogFileName(final String logId) {
    final String name = this.stackTraceFilename.replaceAll("%logId", logId);
    final SimpleDateFormat sdf = new SimpleDateFormat(name);
    final String file = sdf.format(new Date());
    return file;
  }

  /**
   * 返回异常日志文件的保存目录。
   * 
   * @return 异常日志文件的保存目录
   */
  public String getStackTraceDir() {
    return stackTraceDir;
  }

  /**
   * 设定异常日志文件的保存目录。
   * 
   * @param stackTraceDir 异常日志文件的保存目录
   */
  public void setStackTraceDir(final String stackTraceDir) {
    this.stackTraceDir = stackTraceDir;
  }

  /**
   * 返回异常日志（java的trace）文件的保存目录。 返回异常日志文件名。
   * 
   * @return 异常日志文件名
   */
  public String getStackTraceFilename() {
    return stackTraceFilename;
  }

  /**
   * 设定异常日志（java的trace）文件的保存目录。
   * 
   * @param stackTraceFilename 异常日志文件名
   */
  public void setStackTraceFilename(final String stackTraceFilename) {
    this.stackTraceFilename = stackTraceFilename;
  }

  /**
   * 返回异常日志（java的trace）文件是否单独保存一个文件。
   * 
   * @return 单独保存到另一个文件的场合为 true, 以外的场合为 false。
   */
  public boolean isEnableOutputStackTrace() {
    return enableOutputStackTrace;
  }

  /**
   * 设定异常日志（java的trace）文件是否单独保存一个文件。
   * 
   * @param enableOutputStackTrace 异常日志（java的trace）文件是否单独保存
   */
  public void setEnableOutputStackTrace(final boolean enableOutputStackTrace) {
    this.enableOutputStackTrace = enableOutputStackTrace;
  }

}
