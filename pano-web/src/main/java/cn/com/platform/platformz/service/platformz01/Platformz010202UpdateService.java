package cn.com.platform.platformz.service.platformz01;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platform.mapper.common01.PlatformRole01Mapper;
import cn.com.platform.platform.model.common.PlatformRole;
import cn.com.platform.platform.model.common.PlatformRoleQuery;
import cn.com.platform.platform.model.common.PlatformRoleQuery.Criteria;
import cn.com.platform.platformz.form.platformz01.Platformz010202Form;
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
public class Platformz010202UpdateService extends BaseService {

  @Autowired
  PlatformRole01Mapper platformzMemberRole01Mapper;

  public EasyJson<PlatformRole> doUpdate(Platformz010202Form inForm) throws SystemException {

    PlatformRoleQuery platformzMemberRoleQuery = new PlatformRoleQuery();
    Criteria criteria = platformzMemberRoleQuery.createCriteria();
    criteria.andRoleIdEqualTo(inForm.getRoleId());
    criteria.andDeleteFlagEqualTo(false);
    List<PlatformRole> roles = platformzMemberRole01Mapper.selectByBaseModel(platformzMemberRoleQuery);

    if (roles.isEmpty() || roles.size() == 0) {
      EasyJson<PlatformRole> easyJson = new EasyJson<PlatformRole>();
      easyJson.setSuccess(false);
      easyJson.setMsg("当前角色已被删除");
      return easyJson;
    } else {
      PlatformRole platformzMemberRole = roles.get(0);
      platformzMemberRole.setRoleName(inForm.getRoleName());
      updateAudit(platformzMemberRole);
      // 更新
      platformzMemberRole01Mapper.updateByPrimaryKey(platformzMemberRole);
    }

    EasyJson<PlatformRole> easyJson = new EasyJson<PlatformRole>();
    easyJson.setSuccess(true);
    easyJson.setMsg("更新成功");
    return easyJson;
  }
}
