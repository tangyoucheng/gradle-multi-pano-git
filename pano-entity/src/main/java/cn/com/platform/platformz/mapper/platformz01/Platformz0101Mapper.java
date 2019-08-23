package cn.com.platform.platformz.mapper.platformz01;

import java.util.HashMap;
import java.util.List;
import cn.com.platform.platform.mapper.common.PlatformMemberMapper;
import cn.com.platform.platformz.model.platformz01.Platformz0101Model;

/**
 * 用户一览页面Mapper。
 * 
 * @author 唐友成
 * @date 2018-08-10
 *
 */
public interface Platformz0101Mapper extends PlatformMemberMapper {

  /**
   * 检索未被删除的所有管理员用户。
   * 
   * @param parameter 检索条件
   * @return
   */
  List<Platformz0101Model> selectMemberRoleInfo(HashMap<?, ?> parameter);
  
  /**
   * 根据roleId检索管理员用户。
   * 
   * @param parameter 检索条件
   * @return
   */
  List<Platformz0101Model> selectUsersInfoByRoleId(HashMap<?, ?> parameter);

}
