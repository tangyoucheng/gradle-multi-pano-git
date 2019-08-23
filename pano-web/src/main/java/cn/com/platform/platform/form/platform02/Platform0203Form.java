package cn.com.platform.platform.form.platform02;

import java.util.List;
import cn.com.platform.framework.form.AbstractForm;
import cn.com.platform.platform.dto.platform02.Platform0203F01Dto;
import cn.com.platform.platform.model.common.PlatformAdminRole;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜单管理form。
 * 
 * @author 唐友成
 * @date 2018-08-27
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Platform0203Form extends AbstractForm {

  /** 身份编号。 */
  public String roleCode;

  /** 角色结果集。 */
  public List<PlatformAdminRole> roleList;
  /** 菜单结果集。 */
  public List<Platform0203F01Dto> menuList;
  /** 菜单一览Json。 */
  public String menuListJson;
}
