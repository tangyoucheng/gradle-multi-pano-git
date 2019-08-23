package cn.com.platform.platform.model.common01;

import cn.com.pano.pano.model.common01.PanoExposition01Model;
import cn.com.platform.platform.model.common.PlatformMenu;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class PlatformMenu01Model extends PlatformMenu {

  /** 身份下的菜单ID。 */
  public String roleMenuId;

  public String getRoleMenuId() {
    return roleMenuId;
  }

  public void setRoleMenuId(String roleMenuId) {
    this.roleMenuId = roleMenuId;
  }
}
