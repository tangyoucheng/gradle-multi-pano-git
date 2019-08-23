package cn.com.platform.platformz.form.platformz01;

import java.util.List;
import lombok.Data;

/**
 * 用户管理编辑form
 * 
 * @author 唐友成
 * @date 2018-08-10
 */
@Data
public class Platformz010102Form {

  /** 用户名 */
  private String memberName = null;
  /** 密码 */
  private String memberPassword = null;
  /** 邮箱 */
  private String memberEmail = null;
  /** 移动电话 */
  private String mobileNum = null;
  /** 用户ID */
  private String memberId = null;
  /** 用户登陆ID */
  private String memberLoginId = null;
  /** 角色编码 */
  private List<?> rolesInfo = null;
  /** 角色编码 */
  private String[] rolesId = null;
}
