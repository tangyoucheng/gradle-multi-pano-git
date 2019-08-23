package cn.com.platform.platformz.form.platformz02;

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
@EqualsAndHashCode(callSuper = false)
public class Platformz029902Form extends AbstractForm {

  /** 公司id。 */
  private String companyId = null;
  /** 公司名。 */
  private String companyName = null;
}
