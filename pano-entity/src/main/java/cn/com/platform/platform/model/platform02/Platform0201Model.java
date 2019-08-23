package cn.com.platform.platform.model.platform02;

import cn.com.platform.platform.model.common.PlatformAdminUser;

/**
 * 管理员用户一览页面Model。
 * 
 * @author 唐友成
 * @date 2019-07-06
 *
 */
public class Platform0201Model extends PlatformAdminUser {
  private String roleId;
  private String roleName;

  public String getRoleId() {
    return roleId;
  }

  public void setRoleId(String roleId) {
    this.roleId = roleId == null ? null : roleId.trim();
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName == null ? null : roleName.trim();
  }
}
