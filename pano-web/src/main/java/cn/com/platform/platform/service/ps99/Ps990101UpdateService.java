package cn.com.platform.platform.service.ps99;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.platform.form.ps99.Ps990101Form;
import cn.com.platform.platform.mapper.common01.PlatformAdminUser01Mapper;
import cn.com.platform.platform.model.common.PlatformAdminUser;
import cn.com.platform.web.BaseService;

/**
 * 管理员密码变更页面初期化Service。
 * 
 * @author 唐友成
 * @since 2017年10月31日
 */
@Service
public class Ps990101UpdateService extends BaseService {

  @Autowired
  private PlatformAdminUser01Mapper platformAdminUser01Mapper;

  /**
   * 变更处理。
   * 
   * @param inForm 密码变更页面form
   * @throws Exception 异常的场合
   */
  public boolean doUpdate(Ps990101Form inForm) throws Exception {

    String userId = UserSessionUtils.getUserName();
    PlatformAdminUser accountInfo = platformAdminUser01Mapper.selectByPrimaryKey(userId);
    // 帐号信息获取失败
    if (accountInfo == null) {
      inForm.message = "帐号信息获取失败";
      return false;
    }
    // 加密的密码
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    String encodeNewPassword = passwordEncoder.encode(inForm.newPassword);
    // 当前密码错误
    if (!passwordEncoder.matches(inForm.currentPassword, accountInfo.getAdminPassword())) {
      inForm.message = "当前密码错误";
      return false;
    }

    // 新密码与确认密码不相同
    if (!inForm.newPasswordConfirm.equals(inForm.newPassword)) {
      inForm.message = "新密码与确认密码不相同";
      return false;
    }

    // 密码变更成功
    accountInfo.setAdminPassword(encodeNewPassword);
    updateAudit(accountInfo);
    platformAdminUser01Mapper.updateByPrimaryKey(accountInfo);
    inForm.message = "密码变更成功";
    return true;
  }

}
