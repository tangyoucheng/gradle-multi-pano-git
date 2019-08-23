package cn.com.platform.platform.form.ps99;

import cn.com.platform.framework.form.AbstractForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 管理员密码变更页面form。
 * 
 * @author 唐友成
 * @since 2017年10月31日
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Ps990101Form extends AbstractForm {

  /** 当前密码 。 */
  public String currentPassword = "";
  /** 新密码。 */
  public String newPassword = "";
  /** 新密码(确认用)。 */
  public String newPasswordConfirm = "";
  /** 信息。 */
  public String message;

}
