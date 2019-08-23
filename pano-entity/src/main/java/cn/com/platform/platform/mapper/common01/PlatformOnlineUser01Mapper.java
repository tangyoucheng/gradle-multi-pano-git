package cn.com.platform.platform.mapper.common01;

import java.util.HashMap;
import java.util.List;
import cn.com.platform.platform.mapper.common.PlatformOnlineUserMapper;
import cn.com.platform.platform.model.common.PlatformOnlineUser;

/**
 * 
 * 在线用户Mapper
 * 
 * @author 唐友成
 * @date 2018-12-24
 *
 */
public interface PlatformOnlineUser01Mapper extends PlatformOnlineUserMapper {


  /**
   * 检索在线用户的数量
   * 
   * @param parameter
   * @return
   */
  long selectUsersCount(HashMap<?, ?> parameter);


  /**
   * 检索在线用户
   * 
   * @param parameter
   * @return
   */
  List<PlatformOnlineUser> selectUsersInfo(HashMap<?, ?> parameter);

  /**
   * 删除会话已经超时，未及时清除的的在线用户数据
   * 
   * @param adminId 管理员用户ID
   */
  void deleteTimeOutOnlineUser();

}
