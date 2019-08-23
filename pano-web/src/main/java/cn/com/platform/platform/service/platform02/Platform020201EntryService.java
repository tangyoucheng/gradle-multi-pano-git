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
import cn.com.platform.framework.util.FwStringUtils;
import cn.com.platform.platform.form.platform02.Platform020201Form;
import cn.com.platform.platform.mapper.common01.PlatformAdminRole01Mapper;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 管理员管理新增service
 * 
 * @author 唐友成
 * @date 2018-08-10
 */
@Service
@Transactional(rollbackFor=Throwable.class)
public class Platform020201EntryService extends BaseService {

  @Autowired
  PlatformAdminRole01Mapper platformzAdminRole01Mapper;

  public EasyJson<PlatformAdminRole> doEntry(Platform020201Form inForm) throws SystemException {

    PlatformAdminRoleQuery platformzAdminRoleQuery = new PlatformAdminRoleQuery();
    Criteria criteria = platformzAdminRoleQuery.createCriteria();
    criteria.andRoleNameEqualTo(inForm.getRoleName());
    criteria.andDeleteFlagEqualTo(false);
    List<PlatformAdminRole> roles = platformzAdminRole01Mapper.selectByBaseModel(platformzAdminRoleQuery);

    if (!roles.isEmpty() && roles.size() > 0) {
      EasyJson<PlatformAdminRole> easyJson = new EasyJson<PlatformAdminRole>();
      easyJson.setSuccess(false);
      easyJson.setMsg("角色名已存在");
      return easyJson;
    } else {
      PlatformAdminRole platformzAdminRole = new PlatformAdminRole();
      platformzAdminRole.setRoleId(FwStringUtils.getUniqueId());
      platformzAdminRole.setRoleName(inForm.getRoleName());

      createAudit(platformzAdminRole);
      // 新建
      platformzAdminRole01Mapper.insert(platformzAdminRole);
    }

    EasyJson<PlatformAdminRole> easyJson = new EasyJson<PlatformAdminRole>();
    easyJson.setSuccess(true);
    easyJson.setMsg("登录成功");
    return easyJson;
  }
}
