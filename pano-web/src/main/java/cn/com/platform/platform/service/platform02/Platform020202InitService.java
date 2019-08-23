package cn.com.platform.platform.service.platform02;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.com.platform.platform.model.common.PlatformAdminRole;
import cn.com.platform.platform.model.common.PlatformAdminRoleQuery;
import cn.com.platform.platform.model.common.PlatformAdminRoleQuery.Criteria;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platform.form.platform02.Platform020202Form;
import cn.com.platform.platform.mapper.common01.PlatformAdminRole01Mapper;
import cn.com.platform.web.BaseService;

/**
 * 管理员用户更新service
 * 
 * @author 唐友成
 * @date 2018-08-10
 *
 */
@Service
public class Platform020202InitService extends BaseService {

  @Autowired
  PlatformAdminRole01Mapper platformzAdminRole01Mapper;

  public void doInit(Platform020202Form inForm) throws SystemException {

    PlatformAdminRoleQuery platformzAdminRoleQuery = new PlatformAdminRoleQuery();
    Criteria criteria = platformzAdminRoleQuery.createCriteria();
    criteria.andRoleIdEqualTo(inForm.getRoleId());
    criteria.andDeleteFlagEqualTo(false);
    List<PlatformAdminRole> adminUsers = platformzAdminRole01Mapper.selectByBaseModel(platformzAdminRoleQuery);

    if (!adminUsers.isEmpty() && adminUsers.size() == 1) {
      PlatformAdminRole platformzAdminRole = adminUsers.get(0);
      inForm.setRoleId(platformzAdminRole.getRoleId());
      inForm.setRoleName(platformzAdminRole.getRoleName());
    }

  }
}
