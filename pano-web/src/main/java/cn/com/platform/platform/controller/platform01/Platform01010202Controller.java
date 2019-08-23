package cn.com.platform.platform.controller.platform01;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.platform.model.common.PlatformMember;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.framework.common.session.UserSessionInfo;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.platform.form.platform01.Platform01010202Form;
import cn.com.platform.platform.service.platform01.Platform010102Service;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;

/**
 * 
 * 普通用户登录处理
 * 
 * @author 唐友成
 *
 */
@Controller
public class Platform01010202Controller extends BaseController {

  @Autowired
  Platform010102Service platform010102Service;

  @ModelAttribute
  public Platform01010202Form setPlatform01010202Form(@ModelAttribute Platform01010202Form inForm) {
    return inForm;
  }

  /**
   * 会员登陆初始化
   * 
   * @param response
   * @param session
   * @param user
   * @throws SystemException
   */
  @RequestMapping(value = "/")
  public String index(Platform01010202Form inForm) {
    return viewThymeleaf("/platform/platform/platform01/platform01010202");
  }

  /**
   * 会员登陆验证处理
   * 
   * @param response 响应内容
   * @param session 会话内容
   * @return
   * @throws SystemException 异常的场合
   */
  @ResponseBody
  @PostMapping("/checkGeneralLogin")
  public EasyJson<Object> generalLogin(Platform01010202Form inForm, HttpServletResponse response,
      HttpSession session) throws SystemException {
    PlatformMember platformzMember = new PlatformMember();
    platformzMember.setMemberLoginId(inForm.getLoginId());
    platformzMember.setMemberPassword(inForm.getPassword());
    EasyJson<Object> easyJson = platform010102Service.doCheckGeneralLogin(platformzMember);

    // 验证通过的场合，在会话中保存允许登录的变量
    if (easyJson.getSuccess()) {
      UserSessionUtils.putSession(CommonConstantsIF.MEMBER_LOGIN_PERMIT, true);
    }
    return easyJson;
  }

  /**
   * 会员登陆初始化
   * 
   * @param response
   * @param session
   * @param user
   * @throws SystemException
   */
  @RequestMapping("/" + CommonConstantsIF.URI_BASE_MEMBER + "/")
  public String doLogiAfter(Platform01010202Form inForm) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserSessionInfo userSessionInfo = (UserSessionInfo) auth.getPrincipal();
    return redirect("/" + CommonConstantsIF.URI_BASE_MEMBER + "/ps99/ps9902");
//    return viewThymeleaf("/platform/platform/platform01/platform99010101");
  }


  /**
   * 会员退出系统处理
   * 
   * @param response
   * @param session
   * @param user
   * @throws SystemException
   */
  @RequestMapping("/" + CommonConstantsIF.URI_BASE_MEMBER + "/logout")
  public String doLogout(HttpServletRequest request, HttpServletResponse response) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null) {
      // // 会话ID
      // HttpSession httpSession = ServletUtils.getRequest().getSession(false);
      // String sessionId = httpSession.getId();
      // // 登陆ID
      // String loginId = auth.getName();
      // 清除会话信息
      new SecurityContextLogoutHandler().logout(request, response, auth);
    }
    return redirect("/");
  }
}
