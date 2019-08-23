package cn.com.platform.platformz.service.platformz01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.framework.common.CodeValueRecord;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platform.mapper.common01.PlatformMember01Mapper;
import cn.com.platform.platform.model.common.PlatformMember;
import cn.com.platform.platform.model.common.PlatformMemberQuery;
import cn.com.platform.platform.model.common.PlatformMemberQuery.Criteria;
import cn.com.platform.platformz.form.platformz01.Platformz010102Form;
import cn.com.platform.platformz.mapper.platformz01.Platformz0101Mapper;
import cn.com.platform.platformz.model.platformz01.Platformz0101Model;
import cn.com.platform.web.BaseService;

/**
 * 用户更新service
 * 
 * @author 唐友成
 * @date 2018-08-10
 *
 */
@Service
public class Platformz010102InitService extends BaseService {

  @Autowired
  private PlatformMember01Mapper platformMember01Mapper;
  @Autowired
  private Platformz0101Mapper platformz0101Mapper;

  public void doInit(Platformz010102Form inForm) throws SystemException {

    PlatformMemberQuery platformzMemberQuery = new PlatformMemberQuery();
    Criteria criteria = platformzMemberQuery.createCriteria();
    criteria.andMemberIdEqualTo(inForm.getMemberId());
    criteria.andDeleteFlagEqualTo(false);
    List<PlatformMember> memberUsers = platformMember01Mapper.selectByBaseModel(platformzMemberQuery);

    if (!memberUsers.isEmpty() && memberUsers.size() == 1) {
      PlatformMember platformzMember = memberUsers.get(0);
      inForm.setMemberId(platformzMember.getMemberId());
      inForm.setMemberLoginId(platformzMember.getMemberLoginId());
      inForm.setMemberName(platformzMember.getMemberName());
      inForm.setMemberEmail(platformzMember.getMemberEmail());
    }


    // SQL文的参数
    HashMap<String, Object> parameter = new HashMap<String, Object>();
    // 删除标识
    parameter.put("deleteFlag", FlagStatus.Disable.toString());
    // 用户编码
    parameter.put("memberId", inForm.getMemberId());

    List<Platformz0101Model> memberRoles = platformz0101Mapper.selectMemberRoleInfo(parameter);
    if (memberRoles != null) {
      List<CodeValueRecord> rolesInfo = new ArrayList<CodeValueRecord>();
      for (Platformz0101Model platformz0101Model : memberRoles) {
        rolesInfo.add(new CodeValueRecord(platformz0101Model.getRoleId(), platformz0101Model.getRoleName()));
      }
      inForm.setRolesInfo(rolesInfo);
    }
  }
}
