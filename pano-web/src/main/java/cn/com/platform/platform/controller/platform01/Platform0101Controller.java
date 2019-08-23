package cn.com.platform.platform.controller.platform01;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.framework.common.session.UserSessionInfo;
import cn.com.platform.platform.form.platform01.Platform0101Form;
import cn.com.platform.web.BaseController;

/**
 * 
 * 首页 /**
 * 
 * @author 唐友成
 *
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_HOME)
public class Platform0101Controller extends BaseController {

  @ModelAttribute
  public Platform0101Form setPlatform0101Form(@ModelAttribute Platform0101Form inForm) {
    return inForm;
  }

  @RequestMapping("/")
  public String doInit() {
    return viewJsp("/platform/platform/platform01/platform0101");
  }

//  @RequestMapping("/" + CommonConstantsIF.URI_BASE_MEMBER + "/")
  public String doLogiAfter(Platform0101Form inForm) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserSessionInfo userSessionInfo = (UserSessionInfo) auth.getPrincipal();
    inForm.setLoginId(userSessionInfo.getUsername());
    inForm.setUserDisplayName(userSessionInfo.getUserDisplayName());
    // return "forward:WEB-INF/view/platform/platform01/platform0101.jsp";
    return viewJsp("/platform/platform/platform01/platform0101");
  }

//  @RequestMapping("/" + CommonConstantsIF.URI_BASE_MEMBER + "/logout")
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
