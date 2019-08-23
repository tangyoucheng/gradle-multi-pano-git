package cn.com.platform.util;

import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import cn.com.platform.framework.common.session.UserSessionUtils;

/**
 * 获取i18n资源文件
 * 
 * @author 唐友成
 * @date 2018-12-19
 */
public class MessageUtils {

  /**
   * 根据消息键和参数 获取消息 委托给spring messageSource
   * 
   * @param code 代码
   * @return Message
   */
  public static String getMessage(String code) {
    return getMessageSource().getMessage(code, null, UserSessionUtils.getUserCurrentLocale());
  }

  /**
   * 取得Message
   * 
   * @param code 代码
   * @param args 参数
   * @return Message
   */
  public static String getMessage(String code, Object[] args) {
    return getMessageSource().getMessage(code, args, UserSessionUtils.getUserCurrentLocale());
  }

  public static String getMessage(String code, Object[] args, String defaultMessage,
      Locale locale) {
    return getMessageSource().getMessage(code, args, defaultMessage, locale);
  }

  public static String getMessage(String code, Object[] args, Locale locale)
      throws NoSuchMessageException {
    return getMessageSource().getMessage(code, args, locale);
  }

  public static String getMessage(MessageSourceResolvable resolvable, Locale locale)
      throws NoSuchMessageException {
    return getMessageSource().getMessage(resolvable, locale);
  }

  /**
   * 获取MessageSource对象
   *
   * @return
   */
  private static MessageSource getMessageSource() {
    MessageSource messageSource = SpringUtils.getBean(MessageSource.class);
    return messageSource;
  }
}
