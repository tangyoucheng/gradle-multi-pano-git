package cn.com.platform.platformz.controller.platformz01;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.platform.model.common.PlatformRole;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platformz.form.platformz01.Platformz010202Form;
import cn.com.platform.platformz.service.platformz01.Platformz010202UpdateService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;

/**
 * 
 * 角色编辑
 * 
 * @author 唐友成
 * @date 2018-08-10
 *
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_MEMBER + "/platformz010202")
public class Platformz010202Controller extends BaseController {

  @Autowired
  cn.com.platform.platformz.service.platformz01.Platformz010202InitService Platformz010202InitService;
  @Autowired
  Platformz010202UpdateService platformz010202UpdateService;

  @ModelAttribute
  public Platformz010202Form setPlatformz010202Form(@ModelAttribute Platformz010202Form inForm) {
    return inForm;
  }

  /**
   * 编辑页面初始化
   * 
   * @throws SystemException
   */
  @RequestMapping(method = RequestMethod.GET, value = "/")
  public String index(Platformz010202Form inForm) throws SystemException {
    Platformz010202InitService.doInit(inForm);
    return viewJsp("/platform/platformz/platformz01/platformz010202");
  }

  /**
   * 更新
   * 
   * @throws SystemException
   */
  @ResponseBody
  @PostMapping(path = "/doUpdate")
  public EasyJson<PlatformRole> doUpdate(Platformz010202Form inForm) throws SystemException {
    return platformz010202UpdateService.doUpdate(inForm);
  }

}
