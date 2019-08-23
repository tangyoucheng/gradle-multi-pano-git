/*
 * Copyright(c) 2016
 */

package cn.com.platform.platform.form.platform01;

import cn.com.platform.framework.form.AbstractForm;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 后台管理员登陆画面form。
 * 
 * @author 唐友成
 * @date 2018-09-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Platform010101Form extends AbstractForm {

  /** 用户ID。 */
  public String loginId = null;
  /** 密码。 */
  public String password = null;


}
