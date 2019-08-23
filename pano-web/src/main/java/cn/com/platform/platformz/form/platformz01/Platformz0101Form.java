package cn.com.platform.platformz.form.platformz01;

import java.util.List;
import cn.com.platform.framework.form.AbstractForm;
import cn.com.platform.platform.model.common.PlatformMember;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户管理form
 * @author
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Platformz0101Form extends AbstractForm{

  /** 结果集。 */
  public List<PlatformMember> memberList;
}
