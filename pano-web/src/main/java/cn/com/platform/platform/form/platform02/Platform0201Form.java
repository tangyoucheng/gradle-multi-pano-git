package cn.com.platform.platform.form.platform02;

import java.util.List;
import cn.com.platform.framework.form.AbstractForm;
import cn.com.platform.platform.model.common.PlatformAdminUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 管理员管理form
 * @author
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Platform0201Form extends AbstractForm{

  /** 结果集。 */
  public List<PlatformAdminUser> adminList;
}
