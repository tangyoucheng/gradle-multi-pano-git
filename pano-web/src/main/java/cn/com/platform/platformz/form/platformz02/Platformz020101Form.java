package cn.com.platform.platformz.form.platformz02;

import cn.com.platform.framework.form.AbstractForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 公司用户信息新增form
 * 
 * @author 代仁宗
 * @date 2019-06-19
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Platformz020101Form extends AbstractForm {
  /** 公司用户编号。 */
  public String memberId;
  /** 公司用户登录编号。 */
  public String memberLoginId;
  /** 公司用户名。 */
  public String memberName;
  /** 公司用户密码。 */
  public String memberPassword;
  /** 公司用户邮箱。 */
  public String memberEmail;
  /** 公司用户手机号。 */
  public String memberPhone;
  /** 公司用户头像。 */
  public String memberProfilePicture;
  /** 公司角色ID *//** 公司角色和公司用户关联 */
  private String[] rolesId = null;
  /** 公司社区ID *//** 公司社区和公司用户关联 */
  private String[] departmentsId = null;
}
