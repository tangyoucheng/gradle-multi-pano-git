package cn.com.platform.platformz.service.platformz02;

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
import cn.com.platform.platformz.form.platformz02.Platformz0202Form;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 公司角色管理删除service
 * 
 * @author
 *
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Platformz0202DeleteService extends BaseService {

  @Autowired
  PlatformRole01Mapper platformzRole01Mapper;
  @Autowired
  PlatformRoleMenu01Mapper platformzRoleMenu01Mapper;

  public EasyJson<PlatformRole> doDelete(Platformz0202Form inForm) throws SystemException {
    if (!ObjectUtils.isEmpty(inForm.uniqueKeyList)) {
      for (Object roleId : inForm.uniqueKeyList) {
        // 删除角色的菜单权限
        PlatformRoleMenuQuery menuRole = new PlatformRoleMenuQuery();
        Criteria criteria = menuRole.createCriteria();
        criteria.andRoleIdEqualTo(ObjectUtils.getDisplayString(roleId));
        platformzRoleMenu01Mapper.deleteByBaseModel(menuRole);
        // 检索 公司角色信息
        PlatformRole platformzRole =
            platformzRole01Mapper.selectByPrimaryKey(ObjectUtils.getDisplayString(roleId));
        if (platformzRole != null) {
          // 物理删除 公司角色信息
          // platformzRole01Mapper.deleteByPrimaryKey(platformzRole);
          // 逻辑删除 公司角色信息
          platformzRole01Mapper.deleteById(roleId.toString());
        }
      }
    }
    EasyJson<PlatformRole> easyJson = new EasyJson<PlatformRole>();
    easyJson.setSuccess(true);
    easyJson.setMsg("删除成功");
    return easyJson;
  }
}
