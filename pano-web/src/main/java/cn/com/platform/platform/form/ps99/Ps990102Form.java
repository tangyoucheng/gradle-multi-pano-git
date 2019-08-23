package cn.com.platform.platform.form.ps99;

import cn.com.platform.framework.form.AbstractForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 管理员首页管理form。
 * 
 * @author 唐友成
 * @since 2017年10月31日
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Ps990102Form extends AbstractForm {

  /** 公司名。 */
  public String companyName;
  /** 部门名。 */
  public String departmentName;
  /** 身份名。 */
  public String roleName;
}
