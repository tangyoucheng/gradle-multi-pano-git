package cn.com.platform.platformz.service.platformz02;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.platform.framework.code.UserState;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.framework.util.FwStringUtils;
import cn.com.platform.platform.mapper.common01.PlatformDepartmentUser01Mapper;
import cn.com.platform.platform.mapper.common01.PlatformMember01Mapper;
import cn.com.platform.platform.mapper.common01.PlatformRoleUser01Mapper;
import cn.com.platform.platform.model.common.PlatformDepartmentUser;
import cn.com.platform.platform.model.common.PlatformMember;
import cn.com.platform.platform.model.common.PlatformMemberQuery;
import cn.com.platform.platform.model.common.PlatformMemberQuery.Criteria;
import cn.com.platform.platform.model.common.PlatformRoleUser;
import cn.com.platform.platformz.form.platformz02.Platformz020101Form;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 公司用户新增service
 * 
 * @author 代仁宗
 * @date 2019-06-18
 *
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Platformz020101EntryService extends BaseService {

  /** 公司用户 */
  @Autowired
  private PlatformMember01Mapper platformMember01Mapper;

  /** 公司角色用户关联 */
  @Autowired
  private PlatformRoleUser01Mapper platformRoleUser01Mapper;

  /** 社区用户关联 */
  @Autowired
  private PlatformDepartmentUser01Mapper platformzDepartmentUser01Mapper;

  public EasyJson<PlatformMember> doEntry(Platformz020101Form inForm) throws SystemException {

    PlatformMemberQuery platformzMemberQuery = new PlatformMemberQuery();
    Criteria criteria = platformzMemberQuery.createCriteria();
    criteria.andMemberLoginIdEqualTo(inForm.getMemberLoginId());
    criteria.andDeleteFlagEqualTo(false);
    List<PlatformMember> members = platformMember01Mapper.selectByBaseModel(platformzMemberQuery);

    if (!members.isEmpty() && members.size() > 0) {
      EasyJson<PlatformMember> easyJson = new EasyJson<PlatformMember>();
      easyJson.setSuccess(false);
      easyJson.setMsg("公司用户登陆ID已存在");
      return easyJson;
    } else {
      PlatformMember platformzMember = new PlatformMember();
      platformzMember.setMemberId(FwStringUtils.getUniqueId());
      platformzMember.setMemberLoginId(inForm.getMemberLoginId());
      platformzMember.setMemberName(inForm.getMemberName());

      // 加密的密码
      PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      String encodeNewPassword = passwordEncoder.encode(inForm.getMemberPassword());
      platformzMember.setMemberPassword(encodeNewPassword);

      platformzMember.setMemberEmail(inForm.getMemberEmail());
      platformzMember.setMemberStatus(UserState.Active.toString());
      platformzMember.setMemberIdentificationState(UserState.Active.toString());
      createAudit(platformzMember);
      // 登录公司用户
      platformMember01Mapper.insert(platformzMember);
      
      // 登录公司角色
      if (inForm.getRolesId() != null && inForm.getRolesId().length > 0) {
        for (String roleId : inForm.getRolesId()) {
          // 登录公司用户角色
          PlatformRoleUser platformzRoleUser = new PlatformRoleUser();
          platformzRoleUser.setMemberId(platformzMember.getMemberId());
          platformzRoleUser.setRoleId(roleId);
          createAudit(platformzRoleUser);
          platformRoleUser01Mapper.insert(platformzRoleUser);
        }
      }
      // 登录公司社区
      if (inForm.getDepartmentsId() != null && inForm.getDepartmentsId().length > 0) {
        for (String departmentId : inForm.getDepartmentsId()) {
          PlatformDepartmentUser platformzDepartmentUser = new PlatformDepartmentUser();
          platformzDepartmentUser.setMemberId(platformzMember.getMemberId());
          platformzDepartmentUser.setDepartmentId(departmentId);
          createAudit(platformzDepartmentUser);
          platformzDepartmentUser01Mapper.insert(platformzDepartmentUser);
        }
      }
    }
    // 公司用户
    EasyJson<PlatformMember> easyJson = new EasyJson<PlatformMember>();
    easyJson.setSuccess(true);
    easyJson.setMsg("登录成功");
    return easyJson;
  }
}
