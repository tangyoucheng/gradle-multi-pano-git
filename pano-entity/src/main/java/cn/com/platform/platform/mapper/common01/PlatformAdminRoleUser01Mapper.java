package cn.com.platform.platform.mapper.common01;

import java.util.List;
import cn.com.platform.platform.mapper.common.PlatformAdminRoleUserMapper;
import cn.com.platform.platform.model.common.PlatformAdminRoleUser;
import cn.com.platform.platform.model.common.PlatformAdminRoleUserQuery;

/**
 * 管理员角色用户Mapper
 * 
 * @author 唐友成
 * @date 2018-08-10
 *
 */
public interface PlatformAdminRoleUser01Mapper extends PlatformAdminRoleUserMapper {


  /**
   * 通过基础model检索数据
   *
   * @mbg.generated
   */
  List<PlatformAdminRoleUser> selectByBaseModel(PlatformAdminRoleUserQuery example);

  /**
   * 通过基础model删除数据
   *
   * @mbg.generated
   */
  int deleteByBaseModel(PlatformAdminRoleUserQuery example);
}