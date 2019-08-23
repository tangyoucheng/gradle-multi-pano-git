package cn.com.platform.platform.service.platform01;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.framework.code.UserState;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.framework.util.FwStringUtils;
import cn.com.platform.platform.form.platform01.Platform01010201Form;
import cn.com.platform.platform.mapper.common.PlatformMemberExtendMapper;
import cn.com.platform.platform.mapper.common01.PlatformMember01Mapper;
import cn.com.platform.platform.model.common.PlatformMember;
import cn.com.platform.platform.model.common.PlatformMemberExtend;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 会员注册登录service
 * 
 * @author 唐友成
 * @date 2018-08-25
 *
 */
@Service
public class Platform01010201RegisterService extends BaseService {

  @Autowired
  private PlatformMember01Mapper platformzMember01Mapper;
  @Autowired
  private PlatformMemberExtendMapper platformzMemberExtendMapper;

  public EasyJson<Object> doRegister(Platform01010201Form inForm) throws SystemException {
    // 会员信息
    PlatformMember platformzMember = new PlatformMember();
    platformzMember.setMemberId(FwStringUtils.getUniqueId());
    platformzMember.setMemberLoginId(inForm.getMemberLoginId());
    platformzMember.setMemberName(inForm.getMemberName());

    // 加密的密码
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    String encodeNewPassword = passwordEncoder.encode(inForm.getMemberPassword());
    platformzMember.setMemberPassword(encodeNewPassword);

    platformzMember.setMemberStatus(UserState.Active.toString());
    platformzMember.setMemberIdentificationState(FlagStatus.Disable.toString());

    // 自主注册的场合，只能单独设定证记信息，不能调用共通
    platformzMember.setDeleteFlag(false);
    platformzMember.setCreateUserId(platformzMember.getMemberId());
    platformzMember.setCreateDate(LocalDateTime.now());
    platformzMember.setLastUpdateUserId(platformzMember.getMemberId());
    platformzMember.setLastUpdateDate(LocalDateTime.now());
    // createAudit(platformzMember);
    // 登录会员信息
    platformzMember01Mapper.insert(platformzMember);

    // 会员扩展信息
    PlatformMemberExtend platformzMemberExtend =
        new PlatformMemberExtend();
    platformzMemberExtend.setMemberId(platformzMember.getMemberId());
    platformzMemberExtend.setMemberRegisterTime(LocalDateTime.now());

    // 自主注册的场合，只能单独设定证记信息，不能调用共通
    platformzMemberExtend.setDeleteFlag(false);
    platformzMemberExtend.setCreateUserId(platformzMember.getMemberId());
    platformzMemberExtend.setCreateDate(LocalDateTime.now());
    platformzMemberExtend.setLastUpdateUserId(platformzMember.getMemberId());
    platformzMemberExtend.setLastUpdateDate(LocalDateTime.now());
    // createAudit(platformzMemberExtend);

    // 登录会员扩展信息
    platformzMemberExtendMapper.insert(platformzMemberExtend);

    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setSuccess(true);
    easyJson.setMsg("注册成功");
    return easyJson;
  }
}
