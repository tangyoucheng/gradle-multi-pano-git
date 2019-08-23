package cn.com.platform.platformz.controller.platformz99;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.platform.model.common.PlatformLoginInfo;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platformz.form.platformz99.Platformz9904Form;
import cn.com.platform.platformz.service.platformz99.Platformz9904SearchService;
import cn.com.platform.platformz.service.platformz99.Platformz9904UpdateService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;


/**
 * 
 * 用户登陆日志管理
 * 
 * @author 唐友成
 * @date 2018-12-21
 *
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_MEMBER + "/Platformz9904")
public class Platformz9904Controller extends BaseController {

  @Autowired
  Platformz9904SearchService Platformz9904SearchService;
  @Autowired
  Platformz9904UpdateService Platformz9904UpdateService;

  @ModelAttribute
  public Platformz9904Form setPlatformz9904Form(@ModelAttribute Platformz9904Form inForm) {
    return inForm;
  }

  @RequestMapping("/")
  public String doInit(Platformz9904Form inForm) {
    return viewThymeleaf("/platform/platformz/Platformz99/Platformz9904");
  }

  /**
   * 检索登陆日志
   * 
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doSearch")
  public EasyJson<PlatformLoginInfo> doSearch(Platformz9904Form inForm) throws SystemException {
    return Platformz9904SearchService.doSearch(inForm);
  }

  /**
   * 删除登陆日志
   * 
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doDelete")
  public EasyJson<Object> doDelete(Platformz9904Form inForm) throws SystemException {
    return Platformz9904UpdateService.doDelete(inForm);
  }

  /**
   * 清空登陆日志
   * 
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doClear")
  public EasyJson<Object> doClear(Platformz9904Form inForm) throws SystemException {
    return Platformz9904UpdateService.doClear(inForm);
  }

}
