package cn.com.platform.platformz.service.platformz01;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platform.mapper.common01.PlatformDepartment01Mapper;
import cn.com.platform.platform.model.common.PlatformDepartment;
import cn.com.platform.platform.model.common.PlatformDepartmentQuery;
import cn.com.platform.platform.model.common.PlatformDepartmentQuery.Criteria;
import cn.com.platform.platformz.form.platformz01.Platformz019902Form;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 社区管理更新service
 * 
 * @author 代仁宗
 * @date 2019-06-24
 *
 */
@Service
@Transactional(rollbackFor=Throwable.class)
public class Platformz019902UpdateService extends BaseService {

  @Autowired
  PlatformDepartment01Mapper platformzDepartment01Mapper;

  public EasyJson<PlatformDepartment> doUpdate(Platformz019902Form inForm) throws SystemException {

    PlatformDepartmentQuery platformzDepartmentQuery = new PlatformDepartmentQuery();
    Criteria criteria = platformzDepartmentQuery.createCriteria();
    criteria.andDepartmentIdEqualTo(inForm.getDepartmentId());
    criteria.andDeleteFlagEqualTo(false);
    List<PlatformDepartment> departments = platformzDepartment01Mapper.selectByBaseModel(platformzDepartmentQuery);
    //通过ID查询数据判断社区是否删除
    if (departments.isEmpty() || departments.size() == 0) {
      EasyJson<PlatformDepartment> easyJson = new EasyJson<PlatformDepartment>();
      easyJson.setSuccess(false);
      easyJson.setMsg("社区已被删除");
      return easyJson;
    } else {
      PlatformDepartment platformzDepartment = departments.get(0);
      platformzDepartment.setDepartmentName(inForm.getDepartmentName());
      
      updateAudit(platformzDepartment);
      // 更新
      platformzDepartment01Mapper.updateByPrimaryKey(platformzDepartment);
    }

    EasyJson<PlatformDepartment> easyJson = new EasyJson<PlatformDepartment>();
    easyJson.setSuccess(true);
    easyJson.setMsg("更新成功");
    return easyJson;
  }
}
