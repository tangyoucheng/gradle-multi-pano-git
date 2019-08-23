package cn.com.platform.common.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import cn.com.platform.common.CommonConstantsIF;

/**
 * 异常处理
 * 
 * @author admin
 *
 */
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException, ServletException {
    if (isAjaxRequest(request)) {
      // ajax失败的场合吗，返回statusCode
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    } else {
      if (request.getRequestURI().contains(CommonConstantsIF.URI_BASE_ADMIN)) {// 后台管理员的场合
        response
            .sendRedirect(request.getContextPath() + "/" + CommonConstantsIF.URI_BASE_ADMIN + "/");
      } else {// 普通用户的场合
        response.sendRedirect(request.getContextPath() + "/");
      }
    }

  }

  public static boolean isAjaxRequest(HttpServletRequest request) {
    String ajaxFlag = request.getHeader("X-Requested-With");
    return ajaxFlag != null && "XMLHttpRequest".equals(ajaxFlag);
  }
}
