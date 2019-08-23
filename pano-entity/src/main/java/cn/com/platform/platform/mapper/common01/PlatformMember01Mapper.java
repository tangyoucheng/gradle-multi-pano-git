package cn.com.platform.platform.mapper.common01;

import java.util.HashMap;
import java.util.List;
import cn.com.platform.platform.mapper.common.PlatformMemberMapper;
import cn.com.platform.platform.model.common.PlatformMember;
import cn.com.platform.platform.model.common.PlatformMemberQuery;

/**
 * 会员用户Mapper
 * 
 * @author 唐友成
 * @date 2018-08-10
 *
 */
public interface PlatformMember01Mapper extends PlatformMemberMapper {

  /**
   * 通过基础model删除数据
   *
   * @mbg.generated
   */
  int deleteByBaseModel(PlatformMemberQuery example);

  /**
   * 通过基础model检索数据
   *
   * @mbg.generated
   */
  List<PlatformMember> selectByBaseModel(PlatformMemberQuery example);


  /**
   * 检索未被删除的所有会员用户的数量
   * 
   * @param parameter
   * @return
   */
  long selectMembersCount(HashMap<?, ?> parameter);


  /**
   * 检索未被删除的所有会员用户
   * 
   * @param parameter
   * @return
   */
  List<PlatformMember> selectMembersInfo(HashMap<?, ?> parameter);

  /**
   * 伦理删除会员用户
   * 
   * @param memberId 会员用户ID
   */
  void deleteById(String memberId);

  /**
   * 更新会员用户
   * 
   * @param PlatformMember
   */
  void updateByMemberId(PlatformMember PlatformMember);

  /**
   * 检索拥有某一角色的所有用户
   *
   * @mbg.generated
   */
  List<PlatformMember> selectMemberByRoleId(HashMap<?, ?> parameter);
}
