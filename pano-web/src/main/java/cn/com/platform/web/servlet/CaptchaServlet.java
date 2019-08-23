package cn.com.platform.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.com.platform.util.CaptchaUtil;


/**
 * 验证码servlet
 * 
 * @author 唐友成
 * @date 2018-11-01
 */
@WebServlet(urlPatterns = "/images/captcha")
public class CaptchaServlet extends HttpServlet {
  private static final long serialVersionUID = -90304944339413093L;

  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // GIF
    // CaptchaUtil.out(request, response);
    // Png
    CaptchaUtil.outPng(request, response);
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doGet(request, response);
  }

}
