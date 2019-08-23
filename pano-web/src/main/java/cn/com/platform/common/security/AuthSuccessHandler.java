package cn.com.platform.common.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import cn.com.platform.util.SpringUtils;
import net.minidev.json.JSONObject;

/**
 * ajax成功的处理
 * 
 * @author admin
 *
 */
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
  
  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    // response.setStatus(HttpServletResponse.SC_OK);

    if (isAjaxRequest(request)) {
      Map<String, Object> returnObj = new HashMap<String, Object>();
      returnObj.put("status", HttpServletResponse.SC_OK);
      returnObj.put("data", "");
      ObjectMapper mapper = SpringUtils.getBean("objectMapper");;
      response.getWriter().print(mapper.writeValueAsString(returnObj));
      response.getWriter().flush();
    } else {
      super.onAuthenticationSuccess(request, response, authentication);
    }
  }

  public static boolean isAjaxRequest(HttpServletRequest request) {
    String ajaxFlag = request.getHeader("X-Requested-With");
    return ajaxFlag != null && "XMLHttpRequest".equals(ajaxFlag);
  }
}
