package cn.com.platform.platformz.controller.platformz99;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.platform.model.common.PlatformJobLog;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platformz.form.platformz99.Platformz9907Form;
import cn.com.platform.platformz.service.platformz99.Platformz9907CleanService;
import cn.com.platform.platformz.service.platformz99.Platformz9907DeleteService;
import cn.com.platform.platformz.service.platformz99.Platformz9907SearchService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;


/**
 * 
 * 任务日志
 * 
 * @author 王笃鹏
 * @date 2019-01-17
 *
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_MEMBER + "/Platformz9907")
public class Platformz9907Controller extends BaseController {

  @Autowired
  Platformz9907SearchService Platformz9907SearchService;
  @Autowired
  Platformz9907DeleteService Platformz9907DeleteService;
  @Autowired
  Platformz9907CleanService Platformz9907CleanService;

  @ModelAttribute
  public Platformz9907Form setPlatformz9907Form(@ModelAttribute Platformz9907Form inForm) {
    return inForm;
  }

  @RequestMapping("/")
  public String doInit(Platformz9907Form inForm) {
    return viewThymeleaf("/platform/platformz/Platformz99/Platformz9907");
  }

  /**
   * 检索
   * 
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doSearch")
  public EasyJson<PlatformJobLog> doSearch(Platformz9907Form inForm) throws SystemException {
    return Platformz9907SearchService.doSearch(inForm);
  }
  
  /**
   * 删除日志
   * 
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doDelete")
  public EasyJson<PlatformJobLog> doDelete(Platformz9907Form inForm) throws SystemException {
    return Platformz9907DeleteService.doDelete(inForm);
  }

  /**
   * 清空日志
   * 
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doClean")
  public EasyJson<PlatformJobLog> doClean(Platformz9907Form inForm) throws SystemException {
    return Platformz9907CleanService.doClean(inForm);
  }

}
