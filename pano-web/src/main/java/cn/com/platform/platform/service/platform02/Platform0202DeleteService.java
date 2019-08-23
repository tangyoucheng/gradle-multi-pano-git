package cn.com.platform.platform.service.platform02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platform.form.platform02.Platform0202Form;
import cn.com.platform.platform.mapper.common01.PlatformAdminRole01Mapper;
import cn.com.platform.platform.mapper.common01.PlatformAdminRoleMenu01Mapper;
import cn.com.platform.platform.model.common.PlatformAdminRole;
import cn.com.platform.platform.model.common.PlatformAdminRoleMenuQuery;
import cn.com.platform.platform.model.common.PlatformAdminRoleMenuQuery.Criteria;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 角色管理删除service
 * 
 * @author
 *
 */
@Service
@Transactional(rollbackFor=Throwable.class)
public class Platform0202DeleteService extends BaseService {

  @Autowired
  PlatformAdminRole01Mapper platformzAdminRole01Mapper;
  @Autowired
  PlatformAdminRoleMenu01Mapper platformzAdminRoleMenu01Mapper;

  public EasyJson<PlatformAdminRole> doDelete(Platform0202Form inForm) throws SystemException {
    if (!ObjectUtils.isEmpty(inForm.uniqueKeyList)) {
      for (Object roleId : inForm.uniqueKeyList) {
        // 删除角色的菜单权限
        PlatformAdminRoleMenuQuery menuRole = new PlatformAdminRoleMenuQuery();
        Criteria criteria = menuRole.createCriteria();
        criteria.andRoleIdEqualTo(ObjectUtils.getDisplayString(roleId));
        platformzAdminRoleMenu01Mapper.deleteByBaseModel(menuRole);
        // 检索 角色信息
        PlatformAdminRole platformzAdminRole = platformzAdminRole01Mapper.selectByPrimaryKey(ObjectUtils.getDisplayString(roleId));
        if (platformzAdminRole != null) {
          // 删除 角色信息
          platformzAdminRole01Mapper.deleteByPrimaryKey(platformzAdminRole.roleId);
        }
      }
    }
    EasyJson<PlatformAdminRole> easyJson = new EasyJson<PlatformAdminRole>();
    easyJson.setSuccess(true);
    easyJson.setMsg("删除成功");
    return easyJson;
  }
}
