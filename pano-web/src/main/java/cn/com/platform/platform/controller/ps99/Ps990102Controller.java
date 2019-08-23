package cn.com.platform.platform.controller.ps99;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.platform.form.ps99.Ps990102Form;
import cn.com.platform.platform.service.ps99.Ps990102InitService;
import cn.com.platform.web.BaseController;

/**
 * 管理员首页管理处理。
 * 
 * @author 唐友成
 * @since 2017年10月31日
 */
@Controller
public class Ps990102Controller extends BaseController {

  public HttpServletRequest request;

  @Autowired
  private Ps990102InitService ps990102InitService;

  @ModelAttribute
  public Ps990102Form setPs990102Form(@ModelAttribute Ps990102Form inForm) {
    return inForm;
  }

  /**
   * 初期显示处理。
   * 
   * @return 显示页面
   * @throws Exception 异常的场合
   */
  @RequestMapping("/" + CommonConstantsIF.URI_BASE_ADMIN + "/ps99/ps990102")
  public String indexAdmin(Ps990102Form inForm) throws Exception {
    ps990102InitService.doInit(inForm);
    return viewJsp("/platform/platform/ps99/ps990102");
  }

  /**
   * 初期显示处理。
   * 
   * @return 显示页面
   * @throws Exception 异常的场合
   */
  @RequestMapping("/" + CommonConstantsIF.URI_BASE_MEMBER + "/ps99/ps990102")
  public String indexMember(Ps990102Form inForm) throws Exception {
    ps990102InitService.doInit(inForm);
    return viewJsp("/platform/platform/ps99/ps990102");
  }
}
