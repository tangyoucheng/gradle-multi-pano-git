package cn.com.platform.platformz.service.platformz02;

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
import cn.com.platform.platformz.form.platformz02.Platformz020102Form;
import cn.com.platform.platformz.mapper.platformz02.Platformz0201Mapper;
import cn.com.platform.platformz.model.platformz02.Platformz0201Model;
import cn.com.platform.web.BaseService;

/**
 * 公司用户编辑初始化service
 * 
 * @author 代仁宗
 * @date 2019-06-19
 *
 */
@Service
public class Platformz020102InitService extends BaseService {

  @Autowired
  private PlatformMember01Mapper platformMember01Mapper;
  @Autowired
  private Platformz0201Mapper platformz0201Mapper;

  public void doInit(Platformz020102Form inForm) throws SystemException {

    PlatformMemberQuery platformzMemberQuery = new PlatformMemberQuery();
    Criteria criteria = platformzMemberQuery.createCriteria();
    criteria.andMemberIdEqualTo(inForm.getMemberId());
    criteria.andDeleteFlagEqualTo(false);
    List<PlatformMember> members = platformMember01Mapper.selectByBaseModel(platformzMemberQuery);

    if (!members.isEmpty() && members.size() == 1) {
      PlatformMember platformzMember = members.get(0);
      inForm.setMemberId(platformzMember.getMemberId());
      inForm.setMemberLoginId(platformzMember.getMemberLoginId());
      inForm.setMemberName(platformzMember.getMemberName());
      inForm.setMemberEmail(platformzMember.getMemberEmail());
    }


    // SQL文的参数
    HashMap<String, Object> parameter = new HashMap<String, Object>();
    // 删除标识
    parameter.put("deleteFlag", FlagStatus.Disable.toString());
    // 公司用户编码
    parameter.put("memberId", inForm.getMemberId());
    
    //公司角色
    List<Platformz0201Model> memberRoles = platformz0201Mapper.selectMemberRoleInfo(parameter);
    if (memberRoles != null) {
      List<CodeValueRecord> rolesInfo = new ArrayList<CodeValueRecord>();
      for (Platformz0201Model platformz0201Model : memberRoles) {
        rolesInfo.add(new CodeValueRecord(platformz0201Model.getRoleId(), platformz0201Model.getRoleName()));
      }
      inForm.setRolesInfo(rolesInfo);
    }
    //公司社区
    List<Platformz0201Model> memberDepartmentIds = platformz0201Mapper.selectMemberDepartmentInfo(parameter);
    if (memberDepartmentIds != null) {
      List<CodeValueRecord> departmentsInfo = new ArrayList<CodeValueRecord>();
      for (Platformz0201Model platformz0201Model : memberDepartmentIds) {
        departmentsInfo.add(new CodeValueRecord(platformz0201Model.getDepartmentId(), platformz0201Model.getDepartmentName()));
      }
      inForm.setDepartmentsInfo(departmentsInfo);
    }
  }
}
