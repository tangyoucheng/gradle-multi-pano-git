package cn.com.platform.platform.dto.platform02;

import java.io.Serializable;
import lombok.Data;

/**
 * 菜单权限管理-菜单节点。
 * 
 * @author 唐友成
 * @date 2019-07-06
 * 
 */
@Data
public class Platform0203F01Dto implements Serializable {

  private static final long serialVersionUID = 1L;
  /** 节点id。 */
  public String id;
  /** 父节点id。 */
  public String pId;
  /** 菜单名。 */
  public String name;
  /** 菜单id。 */
  public String menuId;
  /** 是否勾选。 */
  public boolean checked;
  /** 是否展开。 */
  public boolean open;
}
