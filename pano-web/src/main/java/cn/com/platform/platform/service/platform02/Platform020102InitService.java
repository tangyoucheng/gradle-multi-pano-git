package cn.com.platform.platform.service.platform02;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.com.platform.platform.mapper.common01.PlatformAdminUser01Mapper;
import cn.com.platform.platform.mapper.platform02.Platform0201Mapper;
import cn.com.platform.platform.model.platform02.Platform0201Model;
import cn.com.platform.platform.model.common.PlatformAdminUser;
import cn.com.platform.platform.model.common.PlatformAdminUserQuery;
import cn.com.platform.platform.model.common.PlatformAdminUserQuery.Criteria;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.framework.common.CodeValueRecord;
import cn.com.platform.platform.form.platform02.Platform020102Form;
import cn.com.platform.web.BaseService;

/**
 * 管理员用户更新service。
 * 
 * @author 唐友成
 * @date 2018-08-10
 *
 */
@Service
public class Platform020102InitService extends BaseService {

  @Autowired
  private PlatformAdminUser01Mapper platformzAdmin01Mapper;
  @Autowired
  private Platform0201Mapper platform0201Mapper;

  /**
   * 初始化角色数据。
   * 
   * @param inForm Platform020102Form
   */
  public void doInit(Platform020102Form inForm) {

    PlatformAdminUserQuery platformzAdminQuery = new PlatformAdminUserQuery();
    Criteria criteria = platformzAdminQuery.createCriteria();
    criteria.andAdminIdEqualTo(inForm.getAdminId());
    criteria.andDeleteFlagEqualTo(false);
    List<PlatformAdminUser> adminUsers = platformzAdmin01Mapper.selectByBaseModel(platformzAdminQuery);

    if (!adminUsers.isEmpty() && adminUsers.size() == 1) {
      PlatformAdminUser platformzAdmin = adminUsers.get(0);
      inForm.setAdminId(platformzAdmin.getAdminId());
      inForm.setAdminLoginId(platformzAdmin.getAdminLoginId());
      inForm.setAdminName(platformzAdmin.getAdminName());
      inForm.setAdminEmail(platformzAdmin.getAdminEmail());
    }


    // SQL文的参数
    HashMap<String, Object> parameter = new HashMap<String, Object>();
    // 删除标识
    parameter.put("deleteFlag", FlagStatus.Disable.toString());
    // 用户编码
    parameter.put("adminId", inForm.getAdminId());

    List<Platform0201Model> adminRoles = platform0201Mapper.selectAdminRoleInfo(parameter);
    if (adminRoles != null) {
      List<CodeValueRecord> rolesInfo = new ArrayList<CodeValueRecord>();
      for (Platform0201Model platform0201Model : adminRoles) {
        rolesInfo.add(new CodeValueRecord(platform0201Model.getRoleId(), platform0201Model.getRoleName()));
      }
      inForm.setRolesInfo(rolesInfo);
    }
  }
}
