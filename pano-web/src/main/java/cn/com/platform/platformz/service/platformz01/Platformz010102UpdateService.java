package cn.com.platform.platformz.service.platformz01;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platform.mapper.common01.PlatformMember01Mapper;
import cn.com.platform.platform.mapper.common01.PlatformRoleUser01Mapper;
import cn.com.platform.platform.model.common.PlatformMember;
import cn.com.platform.platform.model.common.PlatformMemberQuery;
import cn.com.platform.platform.model.common.PlatformMemberQuery.Criteria;
import cn.com.platform.platform.model.common.PlatformRoleUser;
import cn.com.platform.platform.model.common.PlatformRoleUserQuery;
import cn.com.platform.platformz.form.platformz01.Platformz010102Form;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 用户更新service
 * 
 * @author 唐友成
 * @date 2018-08-10
 *
 */
@Service
@Transactional(rollbackFor=Throwable.class)
public class Platformz010102UpdateService extends BaseService {

  @Autowired
  PlatformMember01Mapper platformMember01Mapper;
  @Autowired
  private PlatformRoleUser01Mapper platformMemberRoleUser01Mapper;

  public EasyJson<PlatformMember> doUpdate(Platformz010102Form inForm) throws SystemException {

    PlatformMemberQuery platformzMemberQuery = new PlatformMemberQuery();
    Criteria criteria = platformzMemberQuery.createCriteria();
    criteria.andMemberIdEqualTo(inForm.getMemberId());
    criteria.andDeleteFlagEqualTo(false);
    List<PlatformMember> memberUsers = platformMember01Mapper.selectByBaseModel(platformzMemberQuery);

    if (memberUsers.isEmpty() || memberUsers.size() == 0) {
      EasyJson<PlatformMember> easyJson = new EasyJson<PlatformMember>();
      easyJson.setSuccess(false);
      easyJson.setMsg("当前用户已被删除");
      return easyJson;
    } else {
      PlatformMember platformzMember = memberUsers.get(0);
      platformzMember.setMemberName(inForm.getMemberName());
      platformzMember.setMemberEmail(inForm.getMemberEmail());
      updateAudit(platformzMember);
      // 更新管理员信息
      platformMember01Mapper.updateByMemberId(platformzMember);

      // 删除用户角色的数据
      PlatformRoleUserQuery MemberRoleUserQuery = new PlatformRoleUserQuery();
      MemberRoleUserQuery.createCriteria().andMemberIdEqualTo(platformzMember.getMemberId());
      platformMemberRoleUser01Mapper.deleteByBaseModel(MemberRoleUserQuery);

      if (inForm.getRolesId() != null && inForm.getRolesId().length > 0) {
        for (String roleId : inForm.getRolesId()) {
          // 登录用户角色
          PlatformRoleUser MemberRoleUser = new PlatformRoleUser();
          MemberRoleUser.setMemberId(platformzMember.getMemberId());
          MemberRoleUser.setRoleId(roleId);
          createAudit(MemberRoleUser);
          platformMemberRoleUser01Mapper.insert(MemberRoleUser);
        }
      }
    }
    EasyJson<PlatformMember> easyJson = new EasyJson<PlatformMember>();
    easyJson.setSuccess(true);
    easyJson.setMsg("更新成功");
    return easyJson;
  }
}
