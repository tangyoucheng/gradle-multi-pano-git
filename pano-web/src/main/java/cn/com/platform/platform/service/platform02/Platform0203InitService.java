package cn.com.platform.platform.service.platform02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.com.platform.platform.model.common.PlatformAdminRoleQuery;
import cn.com.platform.framework.common.StandardConstantsIF;
import cn.com.platform.platform.form.platform02.Platform0203Form;
import cn.com.platform.platform.mapper.common01.PlatformAdminRole01Mapper;
import cn.com.platform.web.BaseService;

/**
 * 菜单管理service。
 * 
 * @author 唐友成
 * @date 2018-08-27
 *
 */
@Service
public class Platform0203InitService extends BaseService {

  @Autowired
  PlatformAdminRole01Mapper platformzAdminRole01Mapper;

  /**
   * 初期化处理。
   * 
   * @param inForm Platform0203Form
   */
  public void doInit(Platform0203Form inForm) {
    PlatformAdminRoleQuery query = new PlatformAdminRoleQuery();
    query.createCriteria().andRoleIdNotEqualTo(StandardConstantsIF.ROLE_SYSTEM_MANAGER);
    inForm.roleList = platformzAdminRole01Mapper.selectByBaseModel(query);
  }
}
