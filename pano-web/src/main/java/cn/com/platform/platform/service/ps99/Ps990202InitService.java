package cn.com.platform.platform.service.ps99;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.platform.form.ps99.Ps990202Form;
import cn.com.platform.platform.mapper.common.PlatformMemberExtendMapper;
import cn.com.platform.platform.model.common.PlatformMemberExtend;
import cn.com.platform.web.BaseService;

/**
 * 会员首页初期化Service。
 * 
 * @author 唐友成
 * @since 2017年10月31日
 */
@Service
public class Ps990202InitService extends BaseService {

  @Autowired
  PlatformMemberExtendMapper platformMemberExtendMapper;
  /**
   * 初期化处理。
   * 
   * @param inForm 密码变更页面form
   * @throws Exception 异常的场合
   */
  public void doInit(Ps990202Form inForm) throws Exception {
    // 登录用户信息
    String loginUserkey = UserSessionUtils.getLoginUserKey();
    if (!ObjectUtils.isEmpty(loginUserkey)) {
      // 会员扩展信息
      PlatformMemberExtend platformMemberExtend = platformMemberExtendMapper.selectByPrimaryKey(loginUserkey);
      if (platformMemberExtend != null) {
        inForm.memberProfilePicture = platformMemberExtend.getMemberProfilePicture();
      }
    }
  }
}
