package cn.com.platform.platformz.service.platformz01;

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
import cn.com.platform.platformz.form.platformz01.Platformz010201Form;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 用户管理新增service
 * 
 * @author 唐友成
 * @date 2018-08-10
 */
@Service
@Transactional(rollbackFor=Throwable.class)
public class Platformz010201EntryService extends BaseService {

  @Autowired
  PlatformRole01Mapper platformzMemberRole01Mapper;

  public EasyJson<PlatformRole> doEntry(Platformz010201Form inForm) throws SystemException {

    PlatformRoleQuery platformzMemberRoleQuery = new PlatformRoleQuery();
    Criteria criteria = platformzMemberRoleQuery.createCriteria();
    criteria.andRoleNameEqualTo(inForm.getRoleName());
    criteria.andDeleteFlagEqualTo(false);
    List<PlatformRole> roles = platformzMemberRole01Mapper.selectByBaseModel(platformzMemberRoleQuery);

    if (!roles.isEmpty() && roles.size() > 0) {
      EasyJson<PlatformRole> easyJson = new EasyJson<PlatformRole>();
      easyJson.setSuccess(false);
      easyJson.setMsg("角色名已存在");
      return easyJson;
    } else {
      PlatformRole platformzMemberRole = new PlatformRole();
      platformzMemberRole.setRoleId(FwStringUtils.getUniqueId());
      platformzMemberRole.setRoleName(inForm.getRoleName());

      createAudit(platformzMemberRole);
      // 新建
      platformzMemberRole01Mapper.insert(platformzMemberRole);
    }

    EasyJson<PlatformRole> easyJson = new EasyJson<PlatformRole>();
    easyJson.setSuccess(true);
    easyJson.setMsg("登录成功");
    return easyJson;
  }
}
