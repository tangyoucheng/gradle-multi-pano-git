package cn.com.platform.platformz.service.platformz02;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platform.mapper.common01.PlatformRole01Mapper;
import cn.com.platform.platform.model.common.PlatformRole;
import cn.com.platform.platform.model.common.PlatformRoleQuery;
import cn.com.platform.platform.model.common.PlatformRoleQuery.Criteria;
import cn.com.platform.platformz.form.platformz02.Platformz020202Form;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 公司角色更新service
 * 
 * @author 代仁宗
 * @date 2019-06-18
 *
 */
@Service
@Transactional(rollbackFor=Throwable.class)
public class Platformz020202UpdateService extends BaseService {

  @Autowired
  PlatformRole01Mapper platformzRole01Mapper;

  public EasyJson<PlatformRole> doUpdate(Platformz020202Form inForm) throws SystemException {

    PlatformRoleQuery platformzRoleQuery = new PlatformRoleQuery();
    Criteria criteria = platformzRoleQuery.createCriteria();
    criteria.andRoleIdEqualTo(inForm.getRoleId());
    criteria.andDeleteFlagEqualTo(false);
    List<PlatformRole> roles = platformzRole01Mapper.selectByBaseModel(platformzRoleQuery);

    if (roles.isEmpty() || roles.size() == 0) {
      EasyJson<PlatformRole> easyJson = new EasyJson<PlatformRole>();
      easyJson.setSuccess(false);
      easyJson.setMsg("当前公司角色已被删除");
      return easyJson;
    } else {
      PlatformRole platformzRole = roles.get(0);
      platformzRole.setRoleName(inForm.getRoleName());
      updateAudit(platformzRole);
      // 更新
      platformzRole01Mapper.updateByPrimaryKey(platformzRole);
    }

    EasyJson<PlatformRole> easyJson = new EasyJson<PlatformRole>();
    easyJson.setSuccess(true);
    easyJson.setMsg("更新成功");
    return easyJson;
  }
}
