package cn.com.platform.platform.mapper.platform02;

import java.util.HashMap;
import java.util.List;
import cn.com.platform.platform.mapper.common.PlatformAdminUserMapper;
import cn.com.platform.platform.model.platform02.Platform0201Model;

/**
 * 管理员用户一览页面Mapper。
 * 
 * @author 唐友成
 * @date 2018-08-10
 *
 */
public interface Platform0201Mapper extends PlatformAdminUserMapper {

  /**
   * 检索未被删除的所有管理员用户。.
   * 
   * @param parameter 检索条件
   * @return 返回结果
   */
  List<Platform0201Model> selectAdminRoleInfo(HashMap<?, ?> parameter);

  /**
   * 根据roleId检索管理员用户。
   * 
   * @param parameter 检索条件
   * @return 返回结果
   */
  List<Platform0201Model> selectUsersInfoByRoleId(HashMap<?, ?> parameter);

}
