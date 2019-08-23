package cn.com.platform.platform.dto.ps99;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * 会员纵向菜单。
 * 
 * @author 唐友成
 * @since 2017年11月24日
 * 
 */
@Data
public class Ps9902F02Dto {

  /** 菜单ID。 */
  public String menuId;
  /** 菜单名。 */
  public String menuName;
  /** ＵＲＬ。 */
  public String url;
  /** 网页字体。 */
  public String webFont;
  /** 菜单类型。 */
  public String menuType;
  /** 下级菜单。 */
  public List<Ps9902F02Dto> menuLinkList = new ArrayList<Ps9902F02Dto>();
}
