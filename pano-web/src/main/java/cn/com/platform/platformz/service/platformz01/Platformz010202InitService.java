package cn.com.platform.platformz.service.platformz01;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platform.mapper.common01.PlatformRole01Mapper;
import cn.com.platform.platform.model.common.PlatformRole;
import cn.com.platform.platform.model.common.PlatformRoleQuery;
import cn.com.platform.platform.model.common.PlatformRoleQuery.Criteria;
import cn.com.platform.platformz.form.platformz01.Platformz010202Form;
import cn.com.platform.web.BaseService;

/**
 * 用户更新service
 * 
 * @author 唐友成
 * @date 2018-08-10
 *
 */
@Service
public class Platformz010202InitService extends BaseService {

  @Autowired
  PlatformRole01Mapper platformzMemberRole01Mapper;

  public void doInit(Platformz010202Form inForm) throws SystemException {

    PlatformRoleQuery platformzMemberRoleQuery = new PlatformRoleQuery();
    Criteria criteria = platformzMemberRoleQuery.createCriteria();
    criteria.andRoleIdEqualTo(inForm.getRoleId());
    criteria.andDeleteFlagEqualTo(false);
    List<PlatformRole> memberUsers = platformzMemberRole01Mapper.selectByBaseModel(platformzMemberRoleQuery);

    if (!memberUsers.isEmpty() && memberUsers.size() == 1) {
      PlatformRole platformzMemberRole = memberUsers.get(0);
      inForm.setRoleId(platformzMemberRole.getRoleId());
      inForm.setRoleName(platformzMemberRole.getRoleName());
    }

  }
}
