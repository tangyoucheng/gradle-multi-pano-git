package cn.com.platform.platform.service.platform02;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.platform.framework.code.UserState;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.framework.util.FwStringUtils;
import cn.com.platform.platform.form.platform02.Platform020101Form;
import cn.com.platform.platform.mapper.common01.PlatformAdminRoleUser01Mapper;
import cn.com.platform.platform.mapper.common01.PlatformAdminUser01Mapper;
import cn.com.platform.platform.model.common.PlatformAdminRoleUser;
import cn.com.platform.platform.model.common.PlatformAdminUser;
import cn.com.platform.platform.model.common.PlatformAdminUserQuery;
import cn.com.platform.platform.model.common.PlatformAdminUserQuery.Criteria;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 管理员用户新增service
 * 
 * @author 唐友成
 * @date 2018-08-10
 *
 */
@Service
@Transactional(rollbackFor=Throwable.class)
public class Platform020101EntryService extends BaseService {

  @Autowired
  private PlatformAdminUser01Mapper platformzAdmin01Mapper;
  @Autowired
  private PlatformAdminRoleUser01Mapper platformzAdminRoleUser01Mapper;

  public EasyJson<PlatformAdminUser> doEntry(Platform020101Form inForm) throws SystemException {

    PlatformAdminUserQuery platformzAdminQuery = new PlatformAdminUserQuery();
    Criteria criteria = platformzAdminQuery.createCriteria();
    criteria.andAdminLoginIdEqualTo(inForm.getAdminLoginId());
    criteria.andDeleteFlagEqualTo(false);
    List<PlatformAdminUser> adminUsers = platformzAdmin01Mapper.selectByBaseModel(platformzAdminQuery);

    if (!adminUsers.isEmpty() && adminUsers.size() > 0) {
      EasyJson<PlatformAdminUser> easyJson = new EasyJson<PlatformAdminUser>();
      easyJson.setSuccess(false);
      easyJson.setMsg("用户登陆ID已存在");
      return easyJson;
    } else {
      PlatformAdminUser platformzAdmin = new PlatformAdminUser();
      platformzAdmin.setAdminId(FwStringUtils.getUniqueId());
      platformzAdmin.setAdminLoginId(inForm.getAdminLoginId());
      platformzAdmin.setAdminName(inForm.getAdminName());

      // 加密的密码
      PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      String encodeNewPassword = passwordEncoder.encode(inForm.getAdminPassword());
      platformzAdmin.setAdminPassword(encodeNewPassword);

      platformzAdmin.setAdminEmail(inForm.getAdminEmail());
      platformzAdmin.setAdminStatus(UserState.Active.toString());
      createAudit(platformzAdmin);
      // 登录用户
      platformzAdmin01Mapper.insert(platformzAdmin);

      if (inForm.getRolesId() != null && inForm.getRolesId().length > 0) {
        for (String roleId : inForm.getRolesId()) {
          // 登录用户角色
          PlatformAdminRoleUser AdminRoleUser = new PlatformAdminRoleUser();
          AdminRoleUser.setAdminId(platformzAdmin.getAdminId());
          AdminRoleUser.setRoleId(roleId);
          createAudit(AdminRoleUser);
          platformzAdminRoleUser01Mapper.insert(AdminRoleUser);
        }
      }
    }

    EasyJson<PlatformAdminUser> easyJson = new EasyJson<PlatformAdminUser>();
    easyJson.setSuccess(true);
    easyJson.setMsg("登录成功");
    return easyJson;
  }
}
