package cn.com.platform.platformz.form.platformz02;

import java.util.List;
import cn.com.platform.framework.form.AbstractForm;
import cn.com.platform.platform.model.common.PlatformRole;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 公司角色管理-弹出框form
 * 
 * @author
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Platformz020203Form extends AbstractForm {

  /** 结果集。 */
  public List<PlatformRole> roleList;
}
