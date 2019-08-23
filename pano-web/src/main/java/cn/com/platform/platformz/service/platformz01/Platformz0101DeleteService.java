package cn.com.platform.platformz.service.platformz01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platform.mapper.common01.PlatformMember01Mapper;
import cn.com.platform.platform.mapper.common01.PlatformRoleUser01Mapper;
import cn.com.platform.platform.model.common.PlatformMember;
import cn.com.platform.platform.model.common.PlatformRoleUserQuery;
import cn.com.platform.platformz.form.platformz01.Platformz0101Form;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 用户删除service
 * 
 * @author 唐友成
 * @date 2018-08-10
 *
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Platformz0101DeleteService extends BaseService {

  @Autowired
  PlatformMember01Mapper platformzMember01Mapper;
  @Autowired
  private PlatformRoleUser01Mapper platformzMemberRoleUser01Mapper;

  /**
   * 删除
   * 
   * @param inForm
   * @throws SystemException
   */
  public EasyJson<PlatformMember> doDelete(Platformz0101Form inForm) throws SystemException {
    if (!ObjectUtils.isEmpty(inForm.uniqueKeyList)) {
      for (Object memberId : inForm.uniqueKeyList) {
        // 检索用户信息
        PlatformMember platformzMember =
            platformzMember01Mapper.selectByPrimaryKey(ObjectUtils.getDisplayString(memberId));
        if (platformzMember != null) {
          // 删除用户信息
          platformzMember01Mapper.deleteByPrimaryKey(platformzMember.memberId);

          // 删除用户角色的数据
          PlatformRoleUserQuery MemberRoleUserQuery = new PlatformRoleUserQuery();
          MemberRoleUserQuery.createCriteria().andMemberIdEqualTo(platformzMember.getMemberId());
          platformzMemberRoleUser01Mapper.deleteByBaseModel(MemberRoleUserQuery);
        }
      }
    }
    EasyJson<PlatformMember> easyJson = new EasyJson<PlatformMember>();
    easyJson.setSuccess(true);
    easyJson.setMsg("删除成功");
    return easyJson;
  }

}
