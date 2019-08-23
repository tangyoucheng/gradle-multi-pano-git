package cn.com.platform.platform.mapper.common01;

import java.util.HashMap;
import java.util.List;
import cn.com.platform.platform.mapper.common.PlatformDepartmentMapper;
import cn.com.platform.platform.model.common.PlatformDepartment;
import cn.com.platform.platform.model.common.PlatformDepartmentQuery;
import cn.com.platform.platform.model.common01.PlatformDepartment01Model;

public interface PlatformDepartment01Mapper extends PlatformDepartmentMapper {

  /**
   * 通过基础model删除数据
   *
   * @mbg.generated
   */
  int deleteByBaseModel(PlatformDepartmentQuery example);

  /**
   * 通过基础model检索数据
   *
   * @mbg.generated
   */
  List<PlatformDepartment> selectByBaseModel(PlatformDepartmentQuery example);

  /**
   * 检索未被删除的所有社区的数量
   * 
   * @param parameter
   * @return
   */
  long selectDepartmentCount(HashMap<?, ?> parameter);


  /**
   * 检索未被删除的所有社区
   * 
   * @param parameter
   * @return
   */
  List<PlatformDepartment01Model> selectDepartmentInfo(HashMap<?, ?> parameter);

  /**
   * 父ID查询社区信息
   * 
   * @param parentDepartmentId 社区ID
   * @return
   */
  PlatformDepartment ByParentId(String Id);

}
