package cn.com.platform.platformz.form.platformz02;

import java.util.List;
import cn.com.platform.framework.form.AbstractForm;
import cn.com.platform.platform.model.common.PlatformMember;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 社区服务类型-字典表form
 * 
 * @author 代仁宗
 * @date 2019-06-19
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Platformz0201Form extends AbstractForm {

  /** 公司用户登录ID */
  private String memberLoginId;

  /** 公司用户姓名 */
  private String memberName;
  
  /** 结果集。 */
  public List<PlatformMember> memberRoleList;
}
