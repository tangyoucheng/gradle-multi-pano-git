package cn.com.platform.platformz.mapper.platformz01;

import java.util.List;
import java.util.Map;
import cn.com.platform.platform.mapper.common.PlatformMemberMapper;
import cn.com.platform.platform.model.common01.PlatformMenu01Model;

/**
 * 菜单权限管理Mapper。
 * 
 * @author 唐友成
 * @date 2018-08-10
 *
 */
public interface Platformz0103Mapper extends PlatformMemberMapper {

  /**
   * 检索角色对应的菜单。
   * 
   * @param conditions 条件
   * @return 实体
   */
  public List<PlatformMenu01Model> selectRoleMenus(Map<?, ?> conditions);

}
