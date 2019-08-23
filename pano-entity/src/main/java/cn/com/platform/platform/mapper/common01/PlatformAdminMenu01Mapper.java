package cn.com.platform.platform.mapper.common01;

import java.util.List;
import java.util.Map;
import cn.com.platform.platform.mapper.common.PlatformAdminMenuMapper;
import cn.com.platform.platform.model.common.PlatformAdminMenuQuery;
import cn.com.platform.platform.model.common01.PlatformAdminMenu01Model;

/**
 * 管理员菜单Mapper。
 * 
 * @author 唐友成
 * @date 2018-08-10
 *
 */
public interface PlatformAdminMenu01Mapper extends PlatformAdminMenuMapper {

  /**
   * 通过基础model删除数据。
   * @param conditions 检索条件
   *
   */
  int deleteByBaseModel(PlatformAdminMenuQuery conditions);

  /**
   * 通过基础model检索数据。
   * @param conditions 检索条件
   *
   */
  List<PlatformAdminMenu01Model> selectByBaseModel(PlatformAdminMenuQuery conditions);

  /**
   * 检索左侧竖直菜单信息。
   * 
   * @param conditions 检索条件
   * @return 返回结果
   */
  public List<PlatformAdminMenu01Model> selectSubMenu(Map<?, ?> conditions);
}
