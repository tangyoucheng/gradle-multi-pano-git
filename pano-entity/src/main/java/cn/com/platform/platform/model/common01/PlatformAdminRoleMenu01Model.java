package cn.com.platform.platform.model.common01;

import cn.com.pano.pano.model.common01.PanoExposition01Model;
import cn.com.platform.platform.model.common.PlatformAdminRoleMenu;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 管理员角色菜单01Mode。
 * 
 * @author 唐友成
 * @date 2019-07-06
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class PlatformAdminRoleMenu01Model extends PlatformAdminRoleMenu {

  /** 身份id。 */
  public String roleCd;
  /** 身份名。 */
  public String roleName;

  public String getRoleCd() {
    return roleCd;
  }

  public void setRoleCd(String roleCd) {
    this.roleCd = roleCd;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

}
