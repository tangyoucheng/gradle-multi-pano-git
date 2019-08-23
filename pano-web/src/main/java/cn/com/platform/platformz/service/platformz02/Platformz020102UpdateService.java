package cn.com.platform.platformz.service.platformz02;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platform.mapper.common01.PlatformDepartmentUser01Mapper;
import cn.com.platform.platform.mapper.common01.PlatformMember01Mapper;
import cn.com.platform.platform.mapper.common01.PlatformRoleUser01Mapper;
import cn.com.platform.platform.model.common.PlatformDepartmentUser;
import cn.com.platform.platform.model.common.PlatformDepartmentUserQuery;
import cn.com.platform.platform.model.common.PlatformMember;
import cn.com.platform.platform.model.common.PlatformMemberQuery;
import cn.com.platform.platform.model.common.PlatformMemberQuery.Criteria;
import cn.com.platform.platform.model.common.PlatformRoleUser;
import cn.com.platform.platform.model.common.PlatformRoleUserQuery;
import cn.com.platform.platformz.form.platformz02.Platformz020102Form;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 公司用户编辑service
 * 
 * @author 代仁宗
 * @date 2019-06-19
 *
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Platformz020102UpdateService extends BaseService {
  // 公司用户
  @Autowired
  PlatformMember01Mapper platformMember01Mapper;
  // 公司角色和用户关联表
  @Autowired
  private PlatformRoleUser01Mapper platformRoleUser01Mapper;
  // 公司社区和用户关联表
  @Autowired
  private PlatformDepartmentUser01Mapper platformDepartmentUser01Mapper;

  public EasyJson<PlatformMember> doUpdate(Platformz020102Form inForm) throws SystemException {

    PlatformMemberQuery platformzMemberQuery = new PlatformMemberQuery();
    Criteria criteria = platformzMemberQuery.createCriteria();
    criteria.andMemberIdEqualTo(inForm.getMemberId());
    criteria.andDeleteFlagEqualTo(false);
    List<PlatformMember> members = platformMember01Mapper.selectByBaseModel(platformzMemberQuery);

    if (members.isEmpty() || members.size() == 0) {
      EasyJson<PlatformMember> easyJson = new EasyJson<PlatformMember>();
      easyJson.setSuccess(false);
      easyJson.setMsg("当前用户已被删除");
      return easyJson;
    } else {
      PlatformMember platformzMember = members.get(0);
      // 更新新数据
      platformzMember.setMemberName(inForm.getMemberName());
      platformzMember.setMemberEmail(inForm.getMemberEmail());
      updateAudit(platformzMember);
      // 更新公司用户信息
      platformMember01Mapper.updateByMemberId(platformzMember);

      // 删除公司角色的数据--角色和用户关联表
      PlatformRoleUserQuery roleUserQuery = new PlatformRoleUserQuery();
      roleUserQuery.createCriteria().andMemberIdEqualTo(platformzMember.getMemberId());
      platformRoleUser01Mapper.deleteByBaseModel(roleUserQuery);
      // 登录公司角色
      if (inForm.getRolesId() != null && inForm.getRolesId().length > 0) {
        for (String roleId : inForm.getRolesId()) {
          PlatformRoleUser RoleUser = new PlatformRoleUser();
          RoleUser.setMemberId(platformzMember.getMemberId());
          RoleUser.setRoleId(roleId);
          createAudit(RoleUser);
          platformRoleUser01Mapper.insert(RoleUser);
        }
      }

      // 删除公司社区的数据-社区和用户关联表
      PlatformDepartmentUserQuery departmentUserQuery = new PlatformDepartmentUserQuery();
      departmentUserQuery.createCriteria().andMemberIdEqualTo(platformzMember.getMemberId());
      platformDepartmentUser01Mapper.deleteByBaseModel(departmentUserQuery);
      // 登录公司社区
      if (inForm.getDepartmentsId() != null && inForm.getDepartmentsId().length > 0) {
        for (String departmentId : inForm.getDepartmentsId()) {
          PlatformDepartmentUser platformzDepartmentUser = new PlatformDepartmentUser();
          platformzDepartmentUser.setMemberId(platformzMember.getMemberId());
          platformzDepartmentUser.setDepartmentId(departmentId);
          createAudit(platformzDepartmentUser);
          platformDepartmentUser01Mapper.insert(platformzDepartmentUser);
        }
      }
    }
    EasyJson<PlatformMember> easyJson = new EasyJson<PlatformMember>();
    easyJson.setSuccess(true);
    easyJson.setMsg("更新成功");
    return easyJson;
  }
}
