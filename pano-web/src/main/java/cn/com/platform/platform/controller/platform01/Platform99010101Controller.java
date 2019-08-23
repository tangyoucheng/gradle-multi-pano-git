package cn.com.platform.platform.controller.platform01;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.platform.form.platform01.Platform99010101Form;
import cn.com.platform.web.BaseController;

/**
 * 普通用户登录等待页。
 * 
 * @author 王笃鹏
 * @date 2019-07-18
 *
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_MEMBER + "/platform99010101")
public class Platform99010101Controller extends BaseController {
  @ModelAttribute
  public Platform99010101Form setPlatform99010101Form(@ModelAttribute Platform99010101Form inForm) {
    return inForm;
  }

  /**
   * 功能一览初期化。
   * 
   * @param inForm inForm
   */
  @RequestMapping(value = "/")
  public String index(Platform99010101Form inForm) {
    return viewThymeleaf("/platform/platform/platform01/platform99010101");
  }

  /**
   * 功能一览跳转。
   * 
   * @param inForm inForm
   */
  @RequestMapping(path = "/doRredirect")
  public String doRredirect(Platform99010101Form inForm) {
    return redirect(
        "/" + CommonConstantsIF.URI_BASE_MEMBER + "/ps99/ps9902/" + inForm.getDispFlag());
  }
}
