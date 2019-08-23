package cn.com.platform.platformz.service.platformz02;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.com.platform.platform.mapper.common01.PlatformRole01Mapper;
import cn.com.platform.platform.model.common.PlatformRole;
import cn.com.platform.platform.model.common.PlatformRoleQuery;
import cn.com.platform.platform.model.common.PlatformRoleQuery.Criteria;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platformz.form.platformz02.Platformz020202Form;
import cn.com.platform.web.BaseService;

/**
 * 公司角色更新service
 * 
 * @author 代仁宗
 * @date 2019-06-18
 *
 */
@Service
public class Platformz020202InitService extends BaseService {

  @Autowired
  PlatformRole01Mapper platformzRole01Mapper;

  public void doInit(Platformz020202Form inForm) throws SystemException {

    PlatformRoleQuery platformzRoleQuery = new PlatformRoleQuery();
    Criteria criteria = platformzRoleQuery.createCriteria();
    criteria.andRoleIdEqualTo(inForm.getRoleId());
    criteria.andDeleteFlagEqualTo(false);
    List<PlatformRole> roles = platformzRole01Mapper.selectByBaseModel(platformzRoleQuery);

    if (!roles.isEmpty() && roles.size() == 1) {
      PlatformRole platformzRole = roles.get(0);
      inForm.setRoleId(platformzRole.getRoleId());
      inForm.setRoleName(platformzRole.getRoleName());
    }

  }
}
