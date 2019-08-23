package cn.com.platform.platform.service.platform01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.platform.form.platform01.Platform0199Form;
import cn.com.platform.platform.mapper.common01.PlatformDepartment01Mapper;
import cn.com.platform.platform.model.common.PlatformDepartment;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 社区删除service。
 * 
 * @author 代仁宗
 * @date 2019-06-17
 *
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Platform0199DeleteService extends BaseService {

  @Autowired
  PlatformDepartment01Mapper platformzDepartment01Mapper;

  /**
   * 删除。
   * 
   * @param inForm Platform0199Form
   * @throws SystemException 异常的场合
   */
  public EasyJson<PlatformDepartment> doDelete(Platform0199Form inForm) throws SystemException {
    if (!ObjectUtils.isEmpty(inForm.uniqueKeyList)) {
      for (Object departmentId : inForm.uniqueKeyList) {
        // 检索社区信息
        PlatformDepartment platformzDepartment = platformzDepartment01Mapper.selectByPrimaryKey(
            UserSessionUtils.getCompanyId(), ObjectUtils.getDisplayString(departmentId));
        if (platformzDepartment != null) {
          // 删除用户信息
          platformzDepartment01Mapper.deleteByPrimaryKey(platformzDepartment.companyId,
              platformzDepartment.departmentId);
        }
      }
    }
    EasyJson<PlatformDepartment> easyJson = new EasyJson<PlatformDepartment>();
    easyJson.setSuccess(true);
    easyJson.setMsg("删除成功");
    return easyJson;
  }

}
