package cn.com.platform.platformz.form.platformz01;

import java.util.List;
import cn.com.platform.framework.form.AbstractForm;
import cn.com.platform.platform.model.common.PlatformRole;
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
public class Platformz010203Form extends AbstractForm {

  /** 结果集。 */
  public List<PlatformRole> roleList;
}
