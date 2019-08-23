package cn.com.platform.platform.mapper.common01;

import java.util.HashMap;
import java.util.List;
import cn.com.platform.platform.mapper.common.PlatformLoginInfoMapper;
import cn.com.platform.platform.model.common.PlatformLoginInfo;

/**
 * 登陆用户Mapper
 * 
 * @author 唐友成
 * @date 2018-08-10
 *
 */
public interface PlatformLoginInfo01Mapper extends PlatformLoginInfoMapper {


  /**
   * 检索用户登陆日志的数量
   * 
   * @param parameter
   * @return
   */
  long selectLoginInfoCount(HashMap<?, ?> parameter);


  /**
   * 检索用户登陆日志
   * 
   * @param parameter
   * @return
   */
  List<PlatformLoginInfo> selectLoginInfo(HashMap<?, ?> parameter);

  /**
   * 清空用户登陆日志
   */
  public void clearLoginInfo();

}
