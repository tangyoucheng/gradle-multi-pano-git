package cn.com.platform.platformz.controller.platformz99;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.platformz.form.platformz99.Platformz99050101Form;
import cn.com.platform.platformz.service.platformz99.Platformz99050101InitService;
import cn.com.platform.web.BaseController;


/**
 * 
 * 触发器设定 定时任务
 * 
 * @author 王笃鹏
 * @date 2019-01-17
 *
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_MEMBER + "/Platformz99050101")
public class Platformz99050101Controller extends BaseController {

  @Autowired
  Platformz99050101InitService Platformz99050101InitService;
  
  @ModelAttribute
  public Platformz99050101Form setPlatformz99050101Form(@ModelAttribute Platformz99050101Form inForm) {
    return inForm;
  }

  @RequestMapping("/")
  public String doInit(Platformz99050101Form inForm) {
    Platformz99050101InitService.doInit(inForm);
    
    return viewThymeleaf("/platform/platformz/Platformz99/Platformz99050101");
  }

}
