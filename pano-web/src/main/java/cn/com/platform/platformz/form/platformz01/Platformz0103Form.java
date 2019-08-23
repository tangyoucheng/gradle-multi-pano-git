package cn.com.platform.platformz.form.platformz01;

import java.util.List;
import cn.com.platform.framework.form.AbstractForm;
import cn.com.platform.platform.model.common.PlatformRole;
import cn.com.platform.platformz.dto.platformz01.Platformz0103F01Dto;
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
public class Platformz0103Form extends AbstractForm {

  /** 身份编号。 */
  public String roleCode;

  /** 角色结果集。 */
  public List<PlatformRole> roleList;
  /** 菜单结果集。 */
  public List<Platformz0103F01Dto> menuList;
  /** 菜单一览Json。 */
  public String menuListJson;
}
