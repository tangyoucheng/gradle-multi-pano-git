package cn.com.platform.platform.mapper.common01;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import cn.com.platform.platform.mapper.common.PlatformRoleMapper;
import cn.com.platform.platform.model.common.PlatformRole;
import cn.com.platform.platform.model.common.PlatformRoleQuery;

/**
 * 公司角色Mapper
 * 
 * @author 唐友成
 * @date 2018-08-10
 *
 */
public interface PlatformRole01Mapper extends PlatformRoleMapper {

  /**
   * 通过基础model删除数据
   *
   * @mbg.generated
   */
  int deleteByBaseModel(PlatformRoleQuery example);

  /**
   * 通过基础model检索数据
   *
   * @mbg.generated
   */
  List<PlatformRole> selectByBaseModel(PlatformRoleQuery example);

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
  List<PlatformRole> selectRolesInfo(HashMap<?, ?> parameter);

  /**
   * 伦理删除角色
   * 
   * @param id
   */
  void deleteById(String id);
}
