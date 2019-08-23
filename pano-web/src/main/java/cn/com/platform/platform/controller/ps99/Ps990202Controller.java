package cn.com.platform.platform.controller.ps99;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.platform.form.ps99.Ps990202Form;
import cn.com.platform.platform.service.ps99.Ps990202InitService;
import cn.com.platform.web.BaseController;

/**
 * 会员首页管理处理。
 * 
 * @author 唐友成
 * @since 2017年10月31日
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_MEMBER + "/ps99")
public class Ps990202Controller extends BaseController {

  public HttpServletRequest request;

  @Autowired
  private Ps990202InitService ps990202InitService;

  @ModelAttribute
  public Ps990202Form setPs990202Form(@ModelAttribute Ps990202Form inForm) {
    return inForm;
  }
  /**
   * 初期显示处理。
   * 
   * @return 显示页面
   * @throws Exception 异常的场合
   */
  @RequestMapping("/ps990202")
  public String index(Ps990202Form inForm) throws Exception {
    ps990202InitService.doInit(inForm);
    return viewJsp("/platform/platform/ps99/ps990202");
  }
}
