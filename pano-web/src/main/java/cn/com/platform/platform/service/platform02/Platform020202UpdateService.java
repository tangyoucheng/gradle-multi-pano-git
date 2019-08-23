package cn.com.platform.platform.service.platform02;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.platform.platform.model.common.PlatformAdminRole;
import cn.com.platform.platform.model.common.PlatformAdminRoleQuery;
import cn.com.platform.platform.model.common.PlatformAdminRoleQuery.Criteria;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.framework.util.FwDateUtils;
import cn.com.platform.platform.form.platform02.Platform020202Form;
import cn.com.platform.platform.mapper.common01.PlatformAdminRole01Mapper;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 角色更新service
 * 
 * @author 唐友成
 * @date 2018-08-10
 *
 */
@Service
@Transactional(rollbackFor=Throwable.class)
public class Platform020202UpdateService extends BaseService {

  @Autowired
  PlatformAdminRole01Mapper platformzAdminRole01Mapper;

  public EasyJson<PlatformAdminRole> doUpdate(Platform020202Form inForm) throws SystemException {

    PlatformAdminRoleQuery platformzAdminRoleQuery = new PlatformAdminRoleQuery();
    Criteria criteria = platformzAdminRoleQuery.createCriteria();
    criteria.andRoleIdEqualTo(inForm.getRoleId());
    criteria.andDeleteFlagEqualTo(false);
    List<PlatformAdminRole> roles = platformzAdminRole01Mapper.selectByBaseModel(platformzAdminRoleQuery);

    if (roles.isEmpty() || roles.size() == 0) {
      EasyJson<PlatformAdminRole> easyJson = new EasyJson<PlatformAdminRole>();
      easyJson.setSuccess(false);
      easyJson.setMsg("当前角色已被删除");
      return easyJson;
    } else {
      PlatformAdminRole platformzAdminRole = roles.get(0);
      platformzAdminRole.setRoleName(inForm.getRoleName());
      updateAudit(platformzAdminRole);
      // 更新
      platformzAdminRole01Mapper.updateByPrimaryKey(platformzAdminRole);
    }

    EasyJson<PlatformAdminRole> easyJson = new EasyJson<PlatformAdminRole>();
    easyJson.setSuccess(true);
    easyJson.setMsg("更新成功");
    return easyJson;
  }
}
