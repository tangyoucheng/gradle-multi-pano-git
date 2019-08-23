package cn.com.platform.platform.mapper.common01;

import java.util.List;
import cn.com.platform.platform.mapper.common.PlatformRoleMenuMapper;
import cn.com.platform.platform.model.common.PlatformRoleMenu;
import cn.com.platform.platform.model.common.PlatformRoleMenuQuery;

public interface PlatformRoleMenu01Mapper extends PlatformRoleMenuMapper {

  /**
   * 通过基础model删除数据
   *
   * @mbg.generated
   */
  int deleteByBaseModel(PlatformRoleMenuQuery example);

  /**
   * 通过基础model检索数据
   *
   * @mbg.generated
   */
  List<PlatformRoleMenu> selectByBaseModel(PlatformRoleMenuQuery example);
}
