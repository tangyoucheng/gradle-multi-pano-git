package cn.com.platform.platformz.model.platformz01;

import cn.com.platform.platform.model.common.PlatformMember;

public class Platformz0101Model extends PlatformMember {
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
