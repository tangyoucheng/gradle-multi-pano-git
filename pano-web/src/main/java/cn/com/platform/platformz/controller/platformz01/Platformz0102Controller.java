package cn.com.platform.platformz.controller.platformz01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.platform.model.common.PlatformRole;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platformz.form.platformz01.Platformz0102Form;
import cn.com.platform.platformz.service.platformz01.Platformz0102DeleteService;
import cn.com.platform.platformz.service.platformz01.Platformz0102InitService;
import cn.com.platform.platformz.service.platformz01.Platformz0102SearchService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;


/**
 * 
 * 角色管理
 * 
 * @author 唐友成
 * @date 2018-08-12
 *
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_MEMBER + "/platformz0102")
public class Platformz0102Controller extends BaseController {

  @Autowired
  Platformz0102InitService platformz0102InitService;
  @Autowired
  Platformz0102SearchService platformz0102SearchService;
  @Autowired
  Platformz0102DeleteService platformz0102DeleteService;

  @ModelAttribute
  public Platformz0102Form setPlatformz0102Form(@ModelAttribute Platformz0102Form inForm) {
    return inForm;
  }

  @RequestMapping("/")
  public String doInit(Platformz0102Form inForm) {
    platformz0102InitService.doInit(inForm);
    return viewJsp("/platform/platformz/platformz01/platformz0102");
  }

  @ResponseBody
  @RequestMapping(path = "/doSearch")
  public EasyJson<PlatformRole> doSearch(Platformz0102Form inForm) throws SystemException {
    return platformz0102SearchService.doSearch(inForm);
  }

  /**
   * 删除
   * 
   * @throws SystemException
   * 
   */
  @ResponseBody
  @PostMapping(path = "/doDelete")
  public EasyJson<PlatformRole> doDelete(Platformz0102Form inForm) throws SystemException {
    return platformz0102DeleteService.doDelete(inForm);
  }
}
