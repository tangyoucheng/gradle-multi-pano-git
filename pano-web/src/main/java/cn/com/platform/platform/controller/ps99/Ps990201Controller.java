package cn.com.platform.platform.controller.ps99;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.platform.form.ps99.Ps990201Form;
import cn.com.platform.platform.service.ps99.Ps990201InitService;
import cn.com.platform.platform.service.ps99.Ps990201UpdateService;
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
public class Ps990201Controller extends BaseController {

  public HttpServletRequest request;

  @Autowired
  private Ps990201InitService ps990201InitService;
  @Autowired
  private Ps990201UpdateService ps990201UpdateService;

  @ModelAttribute
  public Ps990201Form setPs990201Form(@ModelAttribute Ps990201Form inForm) {
    return inForm;
  }

  /**
   * 初期显示处理。
   * 
   * @return 显示页面
   * @throws Exception 异常的场合
   */
  @RequestMapping("/ps990201")
  public String index(Ps990201Form inForm) throws Exception {
    ps990201InitService.doInit(inForm);
    return viewJsp("/platform/platform/ps99/ps990201");
  }

  /**
   * 变更处理。
   * 
   * @return 显示页面
   * @throws Exception 异常的场合
   */
  @ResponseBody
  @PostMapping("/ps990201/doUpdate")
  public EasyJson<Object> doUpdate(Ps990201Form inForm) throws Exception {
    boolean flag = ps990201UpdateService.doUpdate(inForm);
    EasyJson<Object> easyJson = new EasyJson<Object>();
    if (!flag) {
      easyJson.setSuccess(false);
      easyJson.setMsg(inForm.message);
    } else {
      easyJson.setSuccess(true);
      easyJson.setMsg(inForm.message);
    }
    return easyJson;
  }

}
