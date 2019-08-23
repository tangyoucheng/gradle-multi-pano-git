package cn.com.platform.platformz.service.platformz01;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platform.mapper.common01.PlatformDepartment01Mapper;
import cn.com.platform.platform.model.common.PlatformDepartment;
import cn.com.platform.platform.model.common.PlatformDepartmentQuery;
import cn.com.platform.platform.model.common.PlatformDepartmentQuery.Criteria;
import cn.com.platform.platformz.form.platformz01.Platformz019902Form;
import cn.com.platform.web.BaseService;

/**
 * 社区管理编辑初始化service
 * 
 * @author 代仁宗
 * @date 2019-06-24
 *
 */
@Service
public class Platformz019902InitService extends BaseService {

  @Autowired
  PlatformDepartment01Mapper platformzDepartment01Mapper;

  public void doInit(Platformz019902Form inForm) throws SystemException {
    // 社区信息
    // 通过前台传入的departmentId检索数据
    PlatformDepartmentQuery platformzDepartmentQuery = new PlatformDepartmentQuery();
    Criteria criteria = platformzDepartmentQuery.createCriteria();
    criteria.andDepartmentIdEqualTo(inForm.getDepartmentId());
    criteria.andDeleteFlagEqualTo(false);
    List<PlatformDepartment> departments = platformzDepartment01Mapper.selectByBaseModel(platformzDepartmentQuery);

    if (!departments.isEmpty() && departments.size() == 1) {
      PlatformDepartment platformzDepartment = departments.get(0);
      inForm.setDepartmentId(platformzDepartment.getDepartmentId());
      inForm.setDepartmentName(platformzDepartment.getDepartmentName());
      inForm.setDepartmentHierarchy(platformzDepartment.getDepartmentHierarchy());
      // 判断父节ID是否为空
      if (!ObjectUtils.isEmpty(platformzDepartment.getParentDepartmentId())) {
        //通过父ID查询出数据
        PlatformDepartment platformzParentDepartment = platformzDepartment01Mapper.ByParentId(platformzDepartment.getParentDepartmentId());
      //设置父节点name为当前点击节点的name
        inForm.parentDepartmentName = platformzParentDepartment.getDepartmentName();
      }
    }

  }
}
