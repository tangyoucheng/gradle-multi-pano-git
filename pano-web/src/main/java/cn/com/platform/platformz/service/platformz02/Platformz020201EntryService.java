package cn.com.platform.platformz.service.platformz02;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.framework.util.FwStringUtils;
import cn.com.platform.platform.mapper.common01.PlatformRole01Mapper;
import cn.com.platform.platform.model.common.PlatformRole;
import cn.com.platform.platform.model.common.PlatformRoleQuery;
import cn.com.platform.platform.model.common.PlatformRoleQuery.Criteria;
import cn.com.platform.platformz.form.platformz02.Platformz020201Form;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 公司角色管理新增service
 * 
 * @author 代仁宗
 * @date 2019-06-18
 */
@Service
@Transactional(rollbackFor=Throwable.class)
public class Platformz020201EntryService extends BaseService {

  @Autowired
  PlatformRole01Mapper platformzRole01Mapper;

  public EasyJson<PlatformRole> doEntry(Platformz020201Form inForm) throws SystemException {

    PlatformRoleQuery platformzRoleQuery = new PlatformRoleQuery();
    Criteria criteria = platformzRoleQuery.createCriteria();
    criteria.andRoleNameEqualTo(inForm.getRoleName());
    criteria.andDeleteFlagEqualTo(false);
    List<PlatformRole> roles = platformzRole01Mapper.selectByBaseModel(platformzRoleQuery);

    if (!roles.isEmpty() && roles.size() > 0) {
      EasyJson<PlatformRole> easyJson = new EasyJson<PlatformRole>();
      easyJson.setSuccess(false);
      easyJson.setMsg("公司角色名已存在");
      return easyJson;
    } else {
      PlatformRole platformzRole = new PlatformRole();
      platformzRole.setRoleId(FwStringUtils.getUniqueId());
      platformzRole.setRoleName(inForm.getRoleName());

      createAudit(platformzRole);
      // 新建
      platformzRole01Mapper.insert(platformzRole);
    }

    EasyJson<PlatformRole> easyJson = new EasyJson<PlatformRole>();
    easyJson.setSuccess(true);
    easyJson.setMsg("登录成功");
    return easyJson;
  }
}
