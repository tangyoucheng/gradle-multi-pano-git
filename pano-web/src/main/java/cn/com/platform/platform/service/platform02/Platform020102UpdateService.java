package cn.com.platform.platform.service.platform02;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.platform.platform.model.common.PlatformAdminRoleUser;
import cn.com.platform.platform.model.common.PlatformAdminRoleUserQuery;
import cn.com.platform.platform.model.common.PlatformAdminUser;
import cn.com.platform.platform.model.common.PlatformAdminUserQuery;
import cn.com.platform.platform.model.common.PlatformAdminUserQuery.Criteria;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.framework.util.FwDateUtils;
import cn.com.platform.platform.form.platform02.Platform020102Form;
import cn.com.platform.platform.mapper.common01.PlatformAdminRoleUser01Mapper;
import cn.com.platform.platform.mapper.common01.PlatformAdminUser01Mapper;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 管理员用户更新service
 * 
 * @author 唐友成
 * @date 2018-08-10
 *
 */
@Service
@Transactional(rollbackFor=Throwable.class)
public class Platform020102UpdateService extends BaseService {

  @Autowired
  PlatformAdminUser01Mapper platformzAdmin01Mapper;
  @Autowired
  private PlatformAdminRoleUser01Mapper platformzAdminRoleUser01Mapper;

  public EasyJson<PlatformAdminUser> doUpdate(Platform020102Form inForm) throws SystemException {

    PlatformAdminUserQuery platformzAdminQuery = new PlatformAdminUserQuery();
    Criteria criteria = platformzAdminQuery.createCriteria();
    criteria.andAdminIdEqualTo(inForm.getAdminId());
    criteria.andDeleteFlagEqualTo(false);
    List<PlatformAdminUser> adminUsers = platformzAdmin01Mapper.selectByBaseModel(platformzAdminQuery);

    if (adminUsers.isEmpty() || adminUsers.size() == 0) {
      EasyJson<PlatformAdminUser> easyJson = new EasyJson<PlatformAdminUser>();
      easyJson.setSuccess(false);
      easyJson.setMsg("当前用户已被删除");
      return easyJson;
    } else {
      PlatformAdminUser platformzAdmin = adminUsers.get(0);
      platformzAdmin.setAdminName(inForm.getAdminName());
      platformzAdmin.setAdminEmail(inForm.getAdminEmail());
      updateAudit(platformzAdmin);
      // 更新管理员信息
      platformzAdmin01Mapper.updateByPrimaryKey(platformzAdmin);

      // 删除用户角色的数据
      PlatformAdminRoleUserQuery AdminRoleUserQuery = new PlatformAdminRoleUserQuery();
      cn.com.platform.platform.model.common.PlatformAdminRoleUserQuery.Criteria AdminRoleUserCriteria =
          AdminRoleUserQuery.createCriteria();
      AdminRoleUserCriteria.andAdminIdEqualTo(platformzAdmin.getAdminId());
      platformzAdminRoleUser01Mapper.deleteByBaseModel(AdminRoleUserQuery);

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
    easyJson.setMsg("更新成功");
    return easyJson;
  }
}
