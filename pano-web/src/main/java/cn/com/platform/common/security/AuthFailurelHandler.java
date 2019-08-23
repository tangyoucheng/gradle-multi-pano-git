package cn.com.platform.common.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import cn.com.platform.util.SpringUtils;

/**
 * ajax失败的处理
 * 
 * @author admin
 *
 */
public class AuthFailurelHandler extends SimpleUrlAuthenticationFailureHandler {
  
  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException, ServletException {
    // response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed");

    if (isAjaxRequest(request)) {

      Map<String, Object> returnObj = new HashMap<String, Object>();
      returnObj.put("status", HttpServletResponse.SC_UNAUTHORIZED);
      returnObj.put("data", "");
      if (exception instanceof BadCredentialsException) {
        returnObj.put("msg", "用户名或密码错误");
      }


      ObjectMapper mapper = SpringUtils.getBean("objectMapper");
      response.setContentType("text/html;charset=" + StandardCharsets.UTF_8.name());
      response.getWriter().print(mapper.writeValueAsString(returnObj));
      response.getWriter().flush();
    } else {
      super.onAuthenticationFailure(request, response, exception);
    }
  }

  public static boolean isAjaxRequest(HttpServletRequest request) {
    String ajaxFlag = request.getHeader("X-Requested-With");
    return ajaxFlag != null && "XMLHttpRequest".equals(ajaxFlag);
  }
}
