package cn.com.platform.platformz.service.platformz01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platform.mapper.common01.PlatformRole01Mapper;
import cn.com.platform.platform.mapper.common01.PlatformRoleMenu01Mapper;
import cn.com.platform.platform.model.common.PlatformRole;
import cn.com.platform.platform.model.common.PlatformRoleMenuQuery;
import cn.com.platform.platform.model.common.PlatformRoleMenuQuery.Criteria;
import cn.com.platform.platformz.form.platformz01.Platformz0102Form;
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
public class Platformz0102DeleteService extends BaseService {

  @Autowired
  PlatformRole01Mapper platformzMemberRole01Mapper;
  @Autowired
  PlatformRoleMenu01Mapper platformzMemberRoleMenu01Mapper;

  public EasyJson<PlatformRole> doDelete(Platformz0102Form inForm) throws SystemException {
    if (!ObjectUtils.isEmpty(inForm.uniqueKeyList)) {
      for (Object roleId : inForm.uniqueKeyList) {
        // 删除角色的菜单权限
        PlatformRoleMenuQuery menuRole = new PlatformRoleMenuQuery();
        Criteria criteria = menuRole.createCriteria();
        criteria.andRoleIdEqualTo(ObjectUtils.getDisplayString(roleId));
        platformzMemberRoleMenu01Mapper.deleteByBaseModel(menuRole);
        // 检索 角色信息
        PlatformRole platformzMemberRole = platformzMemberRole01Mapper.selectByPrimaryKey(ObjectUtils.getDisplayString(roleId));
        if (platformzMemberRole != null) {
          // 删除 角色信息
          platformzMemberRole01Mapper.deleteByPrimaryKey(platformzMemberRole.roleId);
        }
      }
    }
    EasyJson<PlatformRole> easyJson = new EasyJson<PlatformRole>();
    easyJson.setSuccess(true);
    easyJson.setMsg("删除成功");
    return easyJson;
  }
}
