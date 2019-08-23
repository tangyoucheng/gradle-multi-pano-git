/*
 * Copyright(c) 2016
 */

package cn.com.platform.platform.form.platform01;

import lombok.Data;


/**
 * 普通用户注册画面form。
 * 
 * @author 唐友成
 * @date 2018-09-26
 */
@Data
public class Platform01010201Form {

  /** 会员登录编号。 */
  public String memberLoginId;
  /** 会员名。 */
  public String memberName;
  /** 会员密码。 */
  public String memberPassword;
  /** 会员密码确认。 */
  public String memberPasswordConfirm;
  /** 验证码。 */
  public String captchaText;
  /** 登录跳转链接 */
  private String targetUrl = null;

}
