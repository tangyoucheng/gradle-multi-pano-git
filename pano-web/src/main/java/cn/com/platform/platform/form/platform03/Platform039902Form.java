package cn.com.platform.platform.form.platform03;

import cn.com.platform.common.annotation.LogLabel;
import cn.com.platform.common.annotation.LogTargetClass;
import cn.com.platform.framework.form.AbstractForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色管理编辑form。
 * 
 * @author
 *
 */
@Data
@LogTargetClass()
@EqualsAndHashCode(callSuper = false)
public class Platform039902Form extends AbstractForm {

  /** 公司id。 */
  private String companyId = null;
  /** 公司名。 */
  @LogLabel(label = "公司名")
  private String companyName = null;
}
