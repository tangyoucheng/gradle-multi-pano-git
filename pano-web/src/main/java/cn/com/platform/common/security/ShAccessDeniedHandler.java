package cn.com.platform.common.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import cn.com.platform.common.CommonConstantsIF;

/**
 * 无权访问控制处理
 * 
 * @author 唐友成
 * @date 2018-09-03
 *
 */
public class ShAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException, ServletException {
    if (isAjaxRequest(request)) {
      // ajax失败的场合吗，返回statusCode
      response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());
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

