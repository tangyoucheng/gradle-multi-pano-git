package cn.com.platform.platform.controller.platform99;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.platform.model.common.PlatformJobLog;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platform.form.platform99.Platform9907Form;
import cn.com.platform.platform.service.platform99.Platform9907CleanService;
import cn.com.platform.platform.service.platform99.Platform9907DeleteService;
import cn.com.platform.platform.service.platform99.Platform9907SearchService;
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
@RequestMapping("/" + CommonConstantsIF.URI_BASE_ADMIN + "/platform9907")
public class Platform9907Controller extends BaseController {

  @Autowired
  Platform9907SearchService platform9907SearchService;
  @Autowired
  Platform9907DeleteService platform9907DeleteService;
  @Autowired
  Platform9907CleanService platform9907CleanService;

  @ModelAttribute
  public Platform9907Form setPlatform9907Form(@ModelAttribute Platform9907Form inForm) {
    return inForm;
  }

  @RequestMapping("/")
  public String doInit(Platform9907Form inForm) {
    return viewThymeleaf("/platform/platform/platform99/platform9907");
  }

  /**
   * 检索
   * 
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doSearch")
  public EasyJson<PlatformJobLog> doSearch(Platform9907Form inForm) throws SystemException {
    return platform9907SearchService.doSearch(inForm);
  }
  
  /**
   * 删除日志
   * 
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doDelete")
  public EasyJson<PlatformJobLog> doDelete(Platform9907Form inForm) throws SystemException {
    return platform9907DeleteService.doDelete(inForm);
  }

  /**
   * 清空日志
   * 
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doClean")
  public EasyJson<PlatformJobLog> doClean(Platform9907Form inForm) throws SystemException {
    return platform9907CleanService.doClean(inForm);
  }

}
