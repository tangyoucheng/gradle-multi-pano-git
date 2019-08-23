package cn.com.platform.platform.mapper.common01;

import java.util.List;
import cn.com.platform.platform.mapper.common.PlatformAdminRoleMenuMapper;
import cn.com.platform.platform.model.common.PlatformAdminRoleMenu;
import cn.com.platform.platform.model.common.PlatformAdminRoleMenuQuery;

/**
 * 管理员角色菜单权限01Mapper。
 * 
 * @author 唐友成
 * @date 2019-07-06
 *
 */
public interface PlatformAdminRoleMenu01Mapper extends PlatformAdminRoleMenuMapper {

  /**
   * 通过基础model删除数据。
   *
   */
  int deleteByBaseModel(PlatformAdminRoleMenuQuery conditions);

  /**
   * 通过基础model检索数据。
   *
   */
  List<PlatformAdminRoleMenu> selectByBaseModel(PlatformAdminRoleMenuQuery conditions);
}
