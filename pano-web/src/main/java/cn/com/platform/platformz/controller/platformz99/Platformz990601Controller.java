package cn.com.platform.platformz.controller.platformz99;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.platform.model.common.PlatformJobRepeat;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platformz.form.platformz99.Platformz990601Form;
import cn.com.platform.platformz.service.platformz99.Platformz990601EntryService;
import cn.com.platform.platformz.service.platformz99.Platformz990601InitService;
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
@RequestMapping("/" + CommonConstantsIF.URI_BASE_MEMBER + "/Platformz990601")
public class Platformz990601Controller extends BaseController {

  @Autowired
  Platformz990601InitService Platformz990601InitService;
  @Autowired
  Platformz990601EntryService Platformz990601EntryService;
  
  @ModelAttribute
  public Platformz990601Form setPlatformz990601Form(@ModelAttribute Platformz990601Form inForm) {
    return inForm;
  }

  @RequestMapping("/")
  public String doInit(Platformz990601Form inForm) {
    Platformz990601InitService.doInit(inForm);
    return viewThymeleaf("/platform/platformz/Platformz99/Platformz990601");
  }

  /**
   * 新建保存
   * 
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doEntry")
  public EasyJson<PlatformJobRepeat> doEntry(Platformz990601Form inForm) throws SystemException {
    return Platformz990601EntryService.doEntry(inForm);
  }
}
