package cn.com.platform.platform.controller.platform99;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.platform.model.common.PlatformJobCron;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platform.form.platform99.Platform990501Form;
import cn.com.platform.platform.service.platform99.Platform990501EntryService;
import cn.com.platform.platform.service.platform99.Platform990501InitService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;


/**
 * 
 * 新建定时任务
 * 
 * @author 王笃鹏
 * @date 2019-01-17
 *
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_ADMIN + "/platform990501")
public class Platform990501Controller extends BaseController {

  @Autowired
  Platform990501InitService platform990501InitService;
  @Autowired
  Platform990501EntryService platform990501EntryService;
  
  @ModelAttribute
  public Platform990501Form setPlatform990501Form(@ModelAttribute Platform990501Form inForm) {
    return inForm;
  }

  @RequestMapping("/")
  public String doInit(Platform990501Form inForm) {
    platform990501InitService.doInit(inForm);
    return viewThymeleaf("/platform/platform/platform99/platform990501");
  }

  /**
   * 新建保存
   * 
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doEntry")
  public EasyJson<PlatformJobCron> doEntry(Platform990501Form inForm) throws SystemException {
    return platform990501EntryService.doEntry(inForm);
  }
}
