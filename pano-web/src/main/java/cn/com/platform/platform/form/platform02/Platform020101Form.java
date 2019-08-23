package cn.com.platform.platform.form.platform02;

import lombok.Data;

/**
 * 管理员管理新增form
 * 
 * @author 唐友成
 * @date 2018-08-10
 */
@Data
public class Platform020101Form {

  /** 用户名 */
  private String adminName = null;
  /** 密码 */
  private String adminPassword = null;
  /** 邮箱 */
  private String adminEmail = null;
  /** 移动电话 */
  private String mobileNum = null;
  /** 用户ID */
  private String adminId = null;
  /** 用户登陆ID */
  private String adminLoginId = null;
  /** 角色编码 */
  private String[] rolesId = null;
}
