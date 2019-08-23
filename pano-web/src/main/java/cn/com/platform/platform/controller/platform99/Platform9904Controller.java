package cn.com.platform.platform.controller.platform99;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.platform.model.common.PlatformLoginInfo;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platform.form.platform99.Platform9904Form;
import cn.com.platform.platform.service.platform99.Platform9904SearchService;
import cn.com.platform.platform.service.platform99.Platform9904UpdateService;
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
@RequestMapping("/" + CommonConstantsIF.URI_BASE_ADMIN + "/platform9904")
public class Platform9904Controller extends BaseController {

  @Autowired
  Platform9904SearchService platform9904SearchService;
  @Autowired
  Platform9904UpdateService platform9904UpdateService;

  @ModelAttribute
  public Platform9904Form setPlatform9904Form(@ModelAttribute Platform9904Form inForm) {
    return inForm;
  }

  @RequestMapping("/")
  public String doInit(Platform9904Form inForm) {
    return viewThymeleaf("/platform/platform/platform99/platform9904");
  }

  /**
   * 检索登陆日志
   * 
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doSearch")
  public EasyJson<PlatformLoginInfo> doSearch(Platform9904Form inForm) throws SystemException {
    return platform9904SearchService.doSearch(inForm);
  }

  /**
   * 删除登陆日志
   * 
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doDelete")
  public EasyJson<Object> doDelete(Platform9904Form inForm) throws SystemException {
    return platform9904UpdateService.doDelete(inForm);
  }

  /**
   * 清空登陆日志
   * 
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doClear")
  public EasyJson<Object> doClear(Platform9904Form inForm) throws SystemException {
    return platform9904UpdateService.doClear(inForm);
  }

}
