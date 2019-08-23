package cn.com.platform.platform.controller.ps99;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.platform.form.ps99.Ps990212Form;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;

/**
 * 会员密码变更页面处理。
 * 
 * @author 唐友成
 * @since 2017年10月27日
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_MEMBER + "/ps99")
public class Ps990212Controller extends BaseController {

  public HttpServletRequest request;


  @ModelAttribute
  public Ps990212Form setPs990212Form(@ModelAttribute Ps990212Form inForm) {
    return inForm;
  }

  /**
   * 初期显示处理。
   * 
   * @return 显示页面
   * @throws Exception 异常的场合
   */
  @RequestMapping("/ps990212")
  public String index(Ps990212Form inForm) throws Exception {
    return viewThymeleaf("/platform/platform/ps99/ps990212");
  }


}
