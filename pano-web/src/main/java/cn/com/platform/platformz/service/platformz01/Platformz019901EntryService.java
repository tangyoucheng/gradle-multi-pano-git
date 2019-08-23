package cn.com.platform.platformz.service.platformz01;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.framework.util.FwStringUtils;
import cn.com.platform.platform.mapper.common01.PlatformDepartment01Mapper;
import cn.com.platform.platform.model.common.PlatformDepartment;
import cn.com.platform.platform.model.common.PlatformDepartmentQuery;
import cn.com.platform.platform.model.common.PlatformDepartmentQuery.Criteria;
import cn.com.platform.platformz.form.platformz01.Platformz019901Form;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 社区管理新增service。
 * 
 * @author 代仁宗
 * @date 2019-06-24
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Platformz019901EntryService extends BaseService {

  @Autowired
  PlatformDepartment01Mapper platformzDepartment01Mapper;

  /**
   * 新增。
   * 
   * @param inForm form
   * @return 提示信息
   */
  public EasyJson<PlatformDepartment> doEntry(Platformz019901Form inForm) throws SystemException {
    // 检索数据库未被删除数据
    PlatformDepartmentQuery platformzDepartmentQuery = new PlatformDepartmentQuery();
    Criteria criteria = platformzDepartmentQuery.createCriteria();
    criteria.andDepartmentNameEqualTo(inForm.getDepartmentName());
    criteria.andDeleteFlagEqualTo(false);
    List<PlatformDepartment> departments =
        platformzDepartment01Mapper.selectByBaseModel(platformzDepartmentQuery);

    if (!departments.isEmpty() && departments.size() > 0) {
      EasyJson<PlatformDepartment> easyJson = new EasyJson<PlatformDepartment>();
      easyJson.setSuccess(false);
      easyJson.setMsg("社区已存在");
      return easyJson;
    } else {
      PlatformDepartment platformzDepartment = new PlatformDepartment();
      //获取当前登陆用户信息
      platformzDepartment.setCompanyId(UserSessionUtils.getCompanyId());
      //新增ID
      platformzDepartment.setDepartmentId(FwStringUtils.getUniqueId());
      platformzDepartment.setDepartmentName(inForm.getDepartmentName());
      platformzDepartment.setParentDepartmentId(inForm.getParentDepartmentId());
      platformzDepartment.setDepartmentHierarchy(inForm.getDepartmentHierarchy());

      createAudit(platformzDepartment);
      // 新建
      platformzDepartment01Mapper.insert(platformzDepartment);
    }

    EasyJson<PlatformDepartment> easyJson = new EasyJson<PlatformDepartment>();
    easyJson.setSuccess(true);
    easyJson.setMsg("登录成功");
    return easyJson;
  }
}
