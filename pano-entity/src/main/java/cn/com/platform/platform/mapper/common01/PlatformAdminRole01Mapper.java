package cn.com.platform.platform.mapper.common01;

import java.util.HashMap;
import java.util.List;
import cn.com.platform.platform.mapper.common.PlatformAdminRoleMapper;
import cn.com.platform.platform.model.common.PlatformAdminRole;
import cn.com.platform.platform.model.common.PlatformAdminRoleQuery;

/**
 * 角色Mapper
 * 
 * @author 唐友成
 * @date 2018-08-10
 *
 */
public interface PlatformAdminRole01Mapper extends PlatformAdminRoleMapper {

  /**
   * 通过基础model检索数据
   *
   * @mbg.generated
   */
  List<PlatformAdminRole> selectByBaseModel(PlatformAdminRoleQuery example);

  /**
   * 检索未被删除的所有角色总件数
   */
  /**
   * @param parameter
   * @return
   */
  long selectRolesCount(HashMap<?, ?> parameter);

  /**
   * 检索未被删除的所有角色
   * 
   * @param parameter
   * @return
   */
  List<PlatformAdminRole> selectRolesInfo(HashMap<?, ?> parameter);
}
