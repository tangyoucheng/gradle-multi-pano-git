package cn.com.platform.platformz.mapper.platformz02;

import java.util.HashMap;
import java.util.List;
import cn.com.platform.platform.mapper.common.PlatformMemberMapper;
import cn.com.platform.platformz.model.platformz02.Platformz0201Model;

/**
 * 公司用户一览页面Mapper。
 * 
 * @author 代仁宗
 * @date 2019-06-19
 *
 */
public interface Platformz0201Mapper extends PlatformMemberMapper {

  /** 多表查询 */

  /**
   * 检索未被删除的所有公司用户-角色关联数据。
   * 
   * @param parameter 检索条件
   * @return
   */
  List<Platformz0201Model> selectMemberRoleInfo(HashMap<?, ?> parameter);

  /**
   * 检索未被删除的所有公司用户-部门关联数据。
   * 
   * @param parameter 检索条件
   * @return
   */
  List<Platformz0201Model> selectMemberDepartmentInfo(HashMap<?, ?> parameter);

  /**
   * 检索会员信息（session用）。
   * 
   * @param parameter 检索条件
   * @return
   */
  List<Platformz0201Model> selectMemberSessionInfo(HashMap<?, ?> parameter);
}
