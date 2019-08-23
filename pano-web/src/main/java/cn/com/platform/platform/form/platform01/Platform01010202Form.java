/*
 * Copyright(c) 2016
 */

package cn.com.platform.platform.form.platform01;

import lombok.Data;


/**
 * 普通用户登录画面form。
 * 
 * @author 唐友成
 * @date 2018-09-26
 */
@Data
public class Platform01010202Form {

  /** 用户ID */
  private String loginId = null;
  /** 密码 */
  private String password = null;
  /** 登录跳转链接 */
  private String targetUrl = null;

}
