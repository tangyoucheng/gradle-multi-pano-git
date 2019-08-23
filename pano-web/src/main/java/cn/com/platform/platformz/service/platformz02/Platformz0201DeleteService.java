package cn.com.platform.platformz.service.platformz02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import cn.com.platform.platform.mapper.common01.PlatformDepartmentUser01Mapper;
import cn.com.platform.platform.mapper.common01.PlatformMember01Mapper;
import cn.com.platform.platform.mapper.common01.PlatformRoleUser01Mapper;
import cn.com.platform.platform.model.common.PlatformMember;
import cn.com.platform.platformz.form.platformz02.Platformz0201Form;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 公司用户管理service
 * 
 * @author 代仁宗
 * @date 2019-06-18
 *
 */
@Service
public class Platformz0201DeleteService extends BaseService {

  @Autowired
  PlatformMember01Mapper platformzMember01Mapper;

  @Autowired
  PlatformRoleUser01Mapper platformzRoleUser01Mapper;

  @Autowired
  PlatformDepartmentUser01Mapper platformzDepartmentUser01Mapper;

  public EasyJson<Object> doDelete(Platformz0201Form inForm) {

    if (!ObjectUtils.isEmpty(inForm.uniqueKeyList)) {
      for (Object memberId : inForm.uniqueKeyList) {
        // 检索公司用户信息
        PlatformMember platformzMember =
            platformzMember01Mapper.selectByPrimaryKey(ObjectUtils.getDisplayString(memberId));
        if (platformzMember != null) {
          // 物理删除 公司用户信息
          // platformzMember01Mapper.deleteByPrimaryKey(platformzMember);
          // 逻辑删除 公司用户信息
          platformzMember01Mapper.deleteById(memberId.toString());
          // 逻辑删除 公司用户-角色关系信息
          platformzRoleUser01Mapper.deleteByRoleUserId(memberId.toString());
          // 逻辑删除 公司用户-部门关系信
          platformzDepartmentUser01Mapper.deleteByDepartmentUserId(memberId.toString());
        }
      }
    }

    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setSuccess(true);
    easyJson.setMsg("删除成功");
    return easyJson;
  }
}
