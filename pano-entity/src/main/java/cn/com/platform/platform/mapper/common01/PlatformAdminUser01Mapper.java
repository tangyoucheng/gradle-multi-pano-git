package cn.com.platform.platform.mapper.common01;

import java.util.HashMap;
import java.util.List;
import cn.com.platform.platform.mapper.common.PlatformAdminUserMapper;
import cn.com.platform.platform.model.common.PlatformAdminUser;
import cn.com.platform.platform.model.common.PlatformAdminUserQuery;

/**
 * 管理员用户Mapper
 * 
 * @author 唐友成
 * @date 2018-08-10
 *
 */
public interface PlatformAdminUser01Mapper extends PlatformAdminUserMapper {

  /**
   * 通过基础model检索数据
   *
   * @mbg.generated
   */
  List<PlatformAdminUser> selectByBaseModel(PlatformAdminUserQuery example);


  /**
   * 检索未被删除的所有管理员用户的数量
   * 
   * @param parameter
   * @return
   */
  long selectUsersCount(HashMap<?, ?> parameter);


  /**
   * 检索未被删除的所有管理员用户
   * 
   * @param parameter
   * @return
   */
  List<PlatformAdminUser> selectUsersInfo(HashMap<?, ?> parameter);
}
