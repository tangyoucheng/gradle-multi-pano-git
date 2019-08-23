package cn.com.platform.platformz.service.platformz01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.com.platform.platform.mapper.common01.PlatformRole01Mapper;
import cn.com.platform.platform.model.common.PlatformRoleQuery;
import cn.com.platform.framework.common.StandardConstantsIF;
import cn.com.platform.platformz.form.platformz01.Platformz0103Form;
import cn.com.platform.web.BaseService;

/**
 * 菜单管理service
 * 
 * @author 唐友成
 * @date 2018-08-27
 *
 */
@Service
public class Platformz0103InitService extends BaseService {

  @Autowired
  PlatformRole01Mapper platformzMemberRole01Mapper;

  /**
   * 初期化处理
   * 
   * @param inForm
   */
  public void doInit(Platformz0103Form inForm) {
    PlatformRoleQuery query = new PlatformRoleQuery();
    query.createCriteria().andRoleIdNotEqualTo(StandardConstantsIF.ROLE_TENANT_MANAGER);
    inForm.roleList = platformzMemberRole01Mapper.selectByBaseModel(query);
  }
}
