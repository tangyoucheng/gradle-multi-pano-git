package cn.com.platform.platform.controller.ps99;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.platform.form.ps99.Ps990101Form;
import cn.com.platform.platform.service.ps99.Ps990101InitService;
import cn.com.platform.platform.service.ps99.Ps990101UpdateService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;

/**
 * 管理员密码变更页面处理。
 * 
 * @author 唐友成
 * @since 2017年10月27日
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_ADMIN + "/ps99")
public class Ps990101Controller extends BaseController {

  public HttpServletRequest request;

  @Autowired
  private Ps990101InitService ps990101InitService;
  @Autowired
  private Ps990101UpdateService ps990101UpdateService;

  @ModelAttribute
  public Ps990101Form setPs990101Form(@ModelAttribute Ps990101Form inForm) {
    return inForm;
  }

  /**
   * 初期显示处理。
   * 
   * @return 显示页面
   * @throws Exception 异常的场合
   */
  @RequestMapping("/ps990101")
  public String index(Ps990101Form inForm) throws Exception {
    ps990101InitService.doInit(inForm);
    return viewJsp("/platform/platform/ps99/ps990101");
  }

  /**
   * 变更处理。
   * 
   * @return 显示页面
   * @throws Exception 异常的场合
   */
  @ResponseBody
  @PostMapping("/ps990101/doUpdate")
  public EasyJson<Object> doUpdate(Ps990101Form inForm) throws Exception {
    boolean flag = ps990101UpdateService.doUpdate(inForm);
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
