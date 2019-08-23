package cn.com.platform.platform.mapper.common01;

import java.util.List;
import java.util.Map;
import cn.com.platform.platform.mapper.common.PlatformMenuMapper;
import cn.com.platform.platform.model.common.PlatformMenuQuery;
import cn.com.platform.platform.model.common01.PlatformMenu01Model;

/**
 * 用户菜单01Mapper。
 * 
 * @author 唐友成
 * @date 2018-08-10
 *
 */
public interface PlatformMenu01Mapper extends PlatformMenuMapper {

  /**
   * 通过基础model删除数据。
   * 
   * @param conditions 检索条件
   *
   */
  int deleteByBaseModel(PlatformMenuQuery conditions);

  /**
   * 通过基础model检索数据。
   * 
   * @param conditions 检索条件
   *
   */
  List<PlatformMenu01Model> selectByBaseModel(PlatformMenuQuery conditions);

  /**
   * 检索左侧竖直菜单信息。
   * 
   * @param conditions 检索条件
   * @return 返回结果
   */
  public List<PlatformMenu01Model> selectTopMenu(Map<?, ?> conditions);


  /**
   * 检索左侧竖直菜单信息。
   * 
   * @param conditions 检索条件
   * @return 返回结果
   */
  public List<PlatformMenu01Model> selectSubMenu(Map<?, ?> conditions);

}
