package cn.com.platform.platformz.controller.platformz99;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.platform.model.common.PlatformOnlineUser;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.platformz.form.platformz99.Platformz9901Form;
import cn.com.platform.platformz.service.platformz99.Platformz9901SearchService;
import cn.com.platform.platformz.service.platformz99.Platformz9901UpdateService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;


/**
 * 
 * 在线用户管理
 * 
 * @author 唐友成
 * @date 2018-12-21
 *
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_MEMBER + "/Platformz9901")
public class Platformz9901Controller extends BaseController {

  @Autowired
  Platformz9901SearchService Platformz9901SearchService;
  @Autowired
  Platformz9901UpdateService Platformz9901UpdateService;

  @ModelAttribute
  public Platformz9901Form setPlatformz9901Form(@ModelAttribute Platformz9901Form inForm) {
    return inForm;
  }

  @RequestMapping("/")
  public String doInit(Platformz9901Form inForm) {
    return viewThymeleaf("/platform/platformz/Platformz99/Platformz9901");
  }

  /**
   * 检索
   * 
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doSearch")
  public EasyJson<PlatformOnlineUser> doSearch(Platformz9901Form inForm) throws SystemException {
    return Platformz9901SearchService.doSearch(inForm);
  }

  /**
   * 强退
   * 
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doForceLogout")
  public EasyJson<PlatformOnlineUser> doForceLogout(Platformz9901Form inForm) throws SystemException {
    return Platformz9901UpdateService.doDelete(inForm);
  }

}
