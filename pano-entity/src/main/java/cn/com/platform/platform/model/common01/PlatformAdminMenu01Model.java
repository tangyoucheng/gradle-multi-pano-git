package cn.com.platform.platform.model.common01;

import cn.com.platform.platform.model.common.PlatformAdminMenu;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class PlatformAdminMenu01Model extends PlatformAdminMenu {

  /** 身份下的菜单ID。 */
  public String roleMenuId;

  public String getRoleMenuId() {
    return roleMenuId;
  }

  public void setRoleMenuId(String roleMenuId) {
    this.roleMenuId = roleMenuId;
  }
}
