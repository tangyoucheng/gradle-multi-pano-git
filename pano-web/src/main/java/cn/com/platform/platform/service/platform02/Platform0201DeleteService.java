package cn.com.platform.platform.service.platform02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platform.form.platform02.Platform0201Form;
import cn.com.platform.platform.mapper.common01.PlatformAdminRoleUser01Mapper;
import cn.com.platform.platform.mapper.common01.PlatformAdminUser01Mapper;
import cn.com.platform.platform.model.common.PlatformAdminRoleUserQuery;
import cn.com.platform.platform.model.common.PlatformAdminUser;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 管理员用户删除service
 * 
 * @author 唐友成
 * @date 2018-08-10
 *
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Platform0201DeleteService extends BaseService {

  @Autowired
  PlatformAdminUser01Mapper platformzAdmin01Mapper;
  @Autowired
  private PlatformAdminRoleUser01Mapper platformzAdminRoleUser01Mapper;

  /**
   * 删除
   * 
   * @param inForm
   * @throws SystemException
   */
  public EasyJson<PlatformAdminUser> doDelete(Platform0201Form inForm) throws SystemException {
    if (!ObjectUtils.isEmpty(inForm.uniqueKeyList)) {
      for (Object adminId : inForm.uniqueKeyList) {
        // 检索用户信息
        PlatformAdminUser platformzAdmin =
            platformzAdmin01Mapper.selectByPrimaryKey(ObjectUtils.getDisplayString(adminId));
        if (platformzAdmin != null) {
          // 删除用户信息
          platformzAdmin01Mapper.deleteByPrimaryKey(platformzAdmin.adminId);

          // 删除用户角色的数据
          PlatformAdminRoleUserQuery AdminRoleUserQuery = new PlatformAdminRoleUserQuery();
          cn.com.platform.platform.model.common.PlatformAdminRoleUserQuery.Criteria AdminRoleUserCriteria =
              AdminRoleUserQuery.createCriteria();
          AdminRoleUserCriteria.andAdminIdEqualTo(platformzAdmin.getAdminId());
          platformzAdminRoleUser01Mapper.deleteByBaseModel(AdminRoleUserQuery);
        }
      }
    }
    EasyJson<PlatformAdminUser> easyJson = new EasyJson<PlatformAdminUser>();
    easyJson.setSuccess(true);
    easyJson.setMsg("删除成功");
    return easyJson;
  }

}
