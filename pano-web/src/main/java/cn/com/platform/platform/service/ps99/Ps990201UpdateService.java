package cn.com.platform.platform.service.ps99;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.platform.form.ps99.Ps990201Form;
import cn.com.platform.platform.mapper.common01.PlatformMember01Mapper;
import cn.com.platform.platform.model.common.PlatformMember;
import cn.com.platform.platform.model.common.PlatformMemberQuery;
import cn.com.platform.platform.model.common.PlatformMemberQuery.Criteria;
import cn.com.platform.web.BaseService;

/**
 * 会员密码变更页面初期化Service。
 * 
 * @author 唐友成
 * @since 2017年10月31日
 */
@Service
public class Ps990201UpdateService extends BaseService {


  @Autowired
  private PlatformMember01Mapper platformMember01Mapper;

  /**
   * 变更处理。
   * 
   * @param inForm 密码变更页面form
   * @throws Exception 异常的场合
   */
  public boolean doUpdate(Ps990201Form inForm) throws Exception {
    // 加密的密码
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    String encodeNewPassword = passwordEncoder.encode(inForm.newPassword);

    String userId = UserSessionUtils.getUserName();
    PlatformMemberQuery adminQuery = new PlatformMemberQuery();
    Criteria criteria = adminQuery.createCriteria();
    criteria.andMemberLoginIdEqualTo(userId);
    List<PlatformMember> accountsInfo = platformMember01Mapper.selectByBaseModel(adminQuery);
    // 帐号信息获取失败
    if (accountsInfo == null || accountsInfo.isEmpty()) {
      inForm.message = "帐号信息获取失败";
      return false;
    }
    PlatformMember accountInfo = accountsInfo.get(0);
    // 当前密码错误
    if (!passwordEncoder.matches(inForm.currentPassword, accountInfo.getMemberPassword())) {
      inForm.message = "当前密码错误";
      return false;
    }

    // 新密码与确认密码不相同
    if (!inForm.newPasswordConfirm.equals(inForm.newPassword)) {
      inForm.message = "新密码与确认密码不相同";
      return false;
    }

    // 密码变更成功
    accountInfo.setMemberPassword(encodeNewPassword);
    updateAudit(accountInfo);
    platformMember01Mapper.updateByPrimaryKey(accountInfo);
    inForm.message = "密码变更成功";
    return true;
  }

}
