package cn.com.platform.platformz.form.platformz02;

import java.util.List;
import cn.com.platform.framework.form.AbstractForm;
import cn.com.platform.platform.model.common.PlatformRole;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 公司角色form
 * @author
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Platformz0202Form extends AbstractForm{
  /** 公司角色名 */
  private String roleName;
  /** 结果集。 */
  public List<PlatformRole> roleList;
}
