package cn.com.platform.platformz.service.platformz01;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.framework.code.UserState;
import cn.com.platform.framework.util.FwStringUtils;
import cn.com.platform.platform.mapper.common01.PlatformMember01Mapper;
import cn.com.platform.platform.mapper.common01.PlatformRoleUser01Mapper;
import cn.com.platform.platform.model.common.PlatformMember;
import cn.com.platform.platform.model.common.PlatformMemberQuery;
import cn.com.platform.platform.model.common.PlatformMemberQuery.Criteria;
import cn.com.platform.platform.model.common.PlatformRoleUser;
import cn.com.platform.platformz.form.platformz01.Platformz010101Form;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 用户新增service。
 * 
 * @author 唐友成
 * @date 2018-08-10
 *
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Platformz010101EntryService extends BaseService {

  @Autowired
  private PlatformMember01Mapper platformMember01Mapper;
  @Autowired
  private PlatformRoleUser01Mapper platformRoleUser01Mapper;

  /**
   * 登录用户。
   * 
   * @param inForm Platformz010101Form
   * @return
   */
  public EasyJson<PlatformMember> doEntry(Platformz010101Form inForm) {

    PlatformMemberQuery platformzMemberQuery = new PlatformMemberQuery();
    Criteria criteria = platformzMemberQuery.createCriteria();
    criteria.andMemberLoginIdEqualTo(inForm.getMemberLoginId());
    criteria.andDeleteFlagEqualTo(false);
    List<PlatformMember> memberUsers = platformMember01Mapper.selectByBaseModel(platformzMemberQuery);

    if (!memberUsers.isEmpty() && memberUsers.size() > 0) {
      EasyJson<PlatformMember> easyJson = new EasyJson<PlatformMember>();
      easyJson.setSuccess(false);
      easyJson.setMsg("用户登陆ID已存在");
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
      platformzMember.setMemberIdentificationState(FlagStatus.Disable.toString());
      createAudit(platformzMember);
      // 登录用户
      platformMember01Mapper.insert(platformzMember);

      if (inForm.getRolesId() != null && inForm.getRolesId().length > 0) {
        for (String roleId : inForm.getRolesId()) {
          // 登录用户角色
          PlatformRoleUser roleUser = new PlatformRoleUser();
          roleUser.setMemberId(platformzMember.getMemberId());
          roleUser.setRoleId(roleId);
          createAudit(platformzMember);
          platformRoleUser01Mapper.insert(roleUser);
        }
      }
    }

    EasyJson<PlatformMember> easyJson = new EasyJson<PlatformMember>();
    easyJson.setSuccess(true);
    easyJson.setMsg("登录成功");
    return easyJson;
  }
}
