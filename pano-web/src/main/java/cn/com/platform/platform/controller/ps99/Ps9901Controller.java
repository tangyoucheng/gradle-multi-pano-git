package cn.com.platform.platform.controller.ps99;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.platform.form.ps99.Ps9901Form;
import cn.com.platform.platform.service.ps99.Ps9901InitService;
import cn.com.platform.platform.service.ps99.Ps9901SearchService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;

/**
 * 
 * 管理员后台管理
 * 
 * @author 唐友成
 * @date 2018-08-19
 *
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_ADMIN + "/ps99")
public class Ps9901Controller extends BaseController {

  @Autowired
  private Ps9901InitService ps9901InitService;
  @Autowired
  private Ps9901SearchService ps9901SearchService;

  @ModelAttribute
  public Ps9901Form setPs9901Form(@ModelAttribute Ps9901Form inForm) {
    return inForm;
  }

  /**
   * 初期化处理
   * 
   * @param registry
   * @return
   */
  @RequestMapping("/ps9901")
  public String doInit(Ps9901Form inForm) {
    ps9901InitService.doInit(inForm);
    return viewJsp("/platform/platform/ps99/ps9901");
  }

  /**
   * 检索处理
   * 
   * @return
   */
  @ResponseBody
  @PostMapping("/ps9901/doSearch")
  public EasyJson<Object> doSearch(Ps9901Form inForm) {
    ps9901SearchService.doSearch(inForm);
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setSuccess(true);
    inForm.selectTopMenuName = "菜单";
    easyJson.setObj(inForm);
    return easyJson;
  }
}
