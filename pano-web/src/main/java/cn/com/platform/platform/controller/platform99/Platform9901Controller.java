package cn.com.platform.platform.controller.platform99;

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
import cn.com.platform.platform.form.platform99.Platform9901Form;
import cn.com.platform.platform.service.platform99.Platform9901SearchService;
import cn.com.platform.platform.service.platform99.Platform9901UpdateService;
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
@RequestMapping("/" + CommonConstantsIF.URI_BASE_ADMIN + "/platform9901")
public class Platform9901Controller extends BaseController {

  @Autowired
  Platform9901SearchService platform9901SearchService;
  @Autowired
  Platform9901UpdateService platform9901UpdateService;

  @ModelAttribute
  public Platform9901Form setPlatform9901Form(@ModelAttribute Platform9901Form inForm) {
    return inForm;
  }

  @RequestMapping("/")
  public String doInit(Platform9901Form inForm) {
    return viewThymeleaf("/platform/platform/platform99/platform9901");
  }

  /**
   * 检索
   * 
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doSearch")
  public EasyJson<PlatformOnlineUser> doSearch(Platform9901Form inForm) throws SystemException {
    return platform9901SearchService.doSearch(inForm);
  }

  /**
   * 强退
   * 
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doForceLogout")
  public EasyJson<PlatformOnlineUser> doForceLogout(Platform9901Form inForm) throws SystemException {
    return platform9901UpdateService.doDelete(inForm);
  }

}
