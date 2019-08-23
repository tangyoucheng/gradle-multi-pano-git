package cn.com.platform.platform.form.platform02;

import java.util.List;
import cn.com.platform.framework.form.AbstractForm;
import cn.com.platform.platform.model.common.PlatformAdminRole;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色管理form
 * 
 * @author
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Platform0202Form extends AbstractForm {

  /** 结果集。 */
  public List<PlatformAdminRole> roleList;
}
