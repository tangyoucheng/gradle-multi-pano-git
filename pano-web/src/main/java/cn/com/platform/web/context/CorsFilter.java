package cn.com.platform.web.context;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.*;
import org.springframework.util.ObjectUtils;


/**
 * 跨域访问拦截 当前未开启
 * 
 * @author xiongcheng
 *
 */
public class CorsFilter implements Filter {

  private String allowOrigin;
  private String allowMethods;
  private String allowCredentials;
  private String allowHeaders;
  private String exposeHeaders;

  public void init(FilterConfig filterConfig) throws ServletException {
    allowOrigin = filterConfig.getInitParameter("allowOrigin");
    allowMethods = filterConfig.getInitParameter("allowMethods");
    allowCredentials = filterConfig.getInitParameter("allowCredentials");
    allowHeaders = filterConfig.getInitParameter("allowHeaders");
    exposeHeaders = filterConfig.getInitParameter("exposeHeaders");
  }


  /**
   * @description 通过CORS技术实现AJAX跨域访问,只要将CORS响应头写入response对象中即可
   * @author rico
   * @created 2017年7月4日 下午5:02:38
   * @param req
   * @param res
   * @param chain
   * @throws IOException
   * @throws ServletException
   * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse,
   *      javax.servlet.FilterChain)
   */
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;
    String currentOrigin = request.getHeader("Origin");
    if (!ObjectUtils.isEmpty(allowOrigin)) {
      List<String> allowOriginList = Arrays.asList(allowOrigin.split(","));
      if (!ObjectUtils.isEmpty(allowOriginList)) {
        if (allowOriginList.contains(currentOrigin)) {
          response.setHeader("Access-Control-Allow-Origin", currentOrigin);
        }
      }
    }
    if (!ObjectUtils.isEmpty(allowMethods)) {
      response.setHeader("Access-Control-Allow-Methods", allowMethods);
    }
    if (!ObjectUtils.isEmpty(allowCredentials)) {
      response.setHeader("Access-Control-Allow-Credentials", allowCredentials);
    }
    if (!ObjectUtils.isEmpty(allowHeaders)) {
      response.setHeader("Access-Control-Allow-Headers", allowHeaders);
    }
    if (!ObjectUtils.isEmpty(exposeHeaders)) {
      response.setHeader("Access-Control-Expose-Headers", exposeHeaders);
    }
    chain.doFilter(req, res);
  }

  public void destroy() {}

}
