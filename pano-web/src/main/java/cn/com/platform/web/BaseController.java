package cn.com.platform.web;

import java.text.MessageFormat;

/**
 * web层通用数据处理。
 * 
 * @author 唐友成
 * @date 2019-07-04
 */
public class BaseController {

  /**
   * 页面跳转。
   */
  public String redirect(String url) {
    return MessageFormat.format("redirect:{0}", url);
  }

  /**
   * jsp页面跳转。
   */
  public String viewJsp(String url) {
    return MessageFormat.format("/jsp{0}", url);
  }

  /**
   * thymeleaf页面跳转。
   */
  public String viewThymeleaf(String url) {
    return MessageFormat.format("/thymeleaf{0}", url);
  }
}
