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
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.platform.model.common.PlatformAdminUser;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.platform.form.platform01.Platform010101Form;
import cn.com.platform.platform.service.platform01.Platform010101Service;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;

/**
 * 后台管理员登录处理。
 * 
 * @author 唐友成
 *
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_ADMIN)
public class Platform010101Controller extends BaseController {

  @Autowired
  Platform010101Service platform010101Service;

  @ModelAttribute
  public Platform010101Form setPlatform010101Form(@ModelAttribute Platform010101Form inForm) {
    return inForm;
  }

  /**
   * 管理员登陆初期化。
   * 
   * @param inForm Platform010101Form
   * @param request HttpServletRequest
   * @param response HttpServletResponse
   * @return 显示页面
   * @throws Exception 异常的场合
   */
  // @RequestMapping(method = RequestMethod.GET, value = "/")
  @RequestMapping(value = "/")
  public String index(Platform010101Form inForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null) {
      new SecurityContextLogoutHandler().logout(request, response, auth);
    }
    return viewThymeleaf("/platform/platform/platform01/platform010101");
  }

  /**
   * 管理员登陆验证处理。
   * 
   * @param response 响应内容
   * @param session 会话内容
   * @throws SystemException 异常的场合
   */
  @ResponseBody
  @PostMapping("/checkGeneralLogin")
  public EasyJson<Object> generalLogin(Platform010101Form inForm, HttpServletResponse response,
      HttpSession session) throws SystemException {
    PlatformAdminUser platformzAdmin = new PlatformAdminUser();
    platformzAdmin.setAdminLoginId(inForm.getLoginId());
    platformzAdmin.setAdminPassword(inForm.getPassword());
    EasyJson<Object> easyJson = platform010101Service.doCheckGeneralLogin(platformzAdmin);

    // 验证通过的场合，在会话中保存允许登录的变量
    if (easyJson.getSuccess()) {
      UserSessionUtils.putSession(CommonConstantsIF.ADMIN_LOGIN_PERMIT, true);
    }
    return easyJson;
  }

  /**
   * 退出处理。
   * 
   * @param request HttpServletRequest
   * @param response HttpServletResponse
   * @return 显示页面
   */
  @RequestMapping("/logout")
  public String doLogout(HttpServletRequest request, HttpServletResponse response) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null) {
      // HttpSession httpSession = ServletUtils.getRequest().getSession(false);
      // // 会话ID
      // String sessionId = httpSession.getId();
      // // 登陆ID
      // String loginId = auth.getName();
      // 清除会话信息
      new SecurityContextLogoutHandler().logout(request, response, auth);
    }
    return viewThymeleaf("/platform/platform/platform01/platform010101");
  }
}
