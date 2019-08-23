package cn.com.platform.platform.mapper.platform02;

import java.util.List;
import java.util.Map;
import cn.com.platform.platform.mapper.common.PlatformAdminUserMapper;
import cn.com.platform.platform.model.common01.PlatformAdminMenu01Model;

/**
 * 菜单权限管理SMapper。
 * 
 * @author 唐友成
 * @date 2018-08-10
 *
 */
public interface Platform0203Mapper extends PlatformAdminUserMapper {

  /**
   * 检索角色对应的菜单。
   * 
   * @param conditions 条件
   * @return 实体
   */
  public List<PlatformAdminMenu01Model> selectRoleMenus(Map<?, ?> conditions);

}
