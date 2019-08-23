package cn.com.platform.platformz.service.platformz01;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import cn.com.platform.platform.mapper.common01.PlatformDepartment01Mapper;
import cn.com.platform.platform.model.common.PlatformDepartment;
import cn.com.platform.platform.model.common.PlatformDepartmentQuery;
import cn.com.platform.platform.model.common.PlatformDepartmentQuery.Criteria;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platformz.form.platformz01.Platformz019901Form;
import cn.com.platform.web.BaseService;

/**
 * 社区初期化service
 * 
 * @author 代仁宗
 * @date 2019-06-24
 *
 */
@Service
public class Platformz019901InitService extends BaseService {

  @Autowired
  PlatformDepartment01Mapper platformzDepartment01Mapper;

  public void doInit(Platformz019901Form inForm) throws SystemException {
//社区信息
    if (!ObjectUtils.isEmpty(inForm.parentDepartmentId)) {//判断ID是否为空
      PlatformDepartmentQuery platformzDepartmentQuery = new PlatformDepartmentQuery();
      Criteria criteria = platformzDepartmentQuery.createCriteria();
      criteria.andDepartmentIdEqualTo(inForm.getParentDepartmentId());
      criteria.andDeleteFlagEqualTo(false);
      List<PlatformDepartment> departments = platformzDepartment01Mapper.selectByBaseModel(platformzDepartmentQuery);

      if (!departments.isEmpty() && departments.size() == 1) {
        PlatformDepartment platformzDepartment = departments.get(0);
        //设置父节点name为当前点击节点的name
          inForm.parentDepartmentName = platformzDepartment.getDepartmentName();
      }
    }
      

  }
}
