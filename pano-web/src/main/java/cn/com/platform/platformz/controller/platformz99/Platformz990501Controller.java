package cn.com.platform.platformz.controller.platformz99;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.platform.model.common.PlatformJobCron;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platformz.form.platformz99.Platformz990501Form;
import cn.com.platform.platformz.service.platformz99.Platformz990501EntryService;
import cn.com.platform.platformz.service.platformz99.Platformz990501InitService;
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
@RequestMapping("/" + CommonConstantsIF.URI_BASE_MEMBER + "/Platformz990501")
public class Platformz990501Controller extends BaseController {

  @Autowired
  Platformz990501InitService Platformz990501InitService;
  @Autowired
  Platformz990501EntryService Platformz990501EntryService;
  
  @ModelAttribute
  public Platformz990501Form setPlatformz990501Form(@ModelAttribute Platformz990501Form inForm) {
    return inForm;
  }

  @RequestMapping("/")
  public String doInit(Platformz990501Form inForm) {
    Platformz990501InitService.doInit(inForm);
    return viewThymeleaf("/platform/platformz/Platformz99/Platformz990501");
  }

  /**
   * 新建保存
   * 
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doEntry")
  public EasyJson<PlatformJobCron> doEntry(Platformz990501Form inForm) throws SystemException {
    return Platformz990501EntryService.doEntry(inForm);
  }
}
