package cn.com.platform.platform.controller.platform99;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.platform.form.platform99.Platform99050101Form;
import cn.com.platform.platform.service.platform99.Platform99050101InitService;
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
@RequestMapping("/" + CommonConstantsIF.URI_BASE_ADMIN + "/platform99050101")
public class Platform99050101Controller extends BaseController {

  @Autowired
  Platform99050101InitService platform99050101InitService;
  
  @ModelAttribute
  public Platform99050101Form setPlatform99050101Form(@ModelAttribute Platform99050101Form inForm) {
    return inForm;
  }

  @RequestMapping("/")
  public String doInit(Platform99050101Form inForm) {
    platform99050101InitService.doInit(inForm);
    
    return viewThymeleaf("/platform/platform/platform99/platform99050101");
  }

}
