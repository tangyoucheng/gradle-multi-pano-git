package cn.com.platform.platform.controller.platform99;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.platform.form.platform99.Platform99060101Form;
import cn.com.platform.platform.service.platform99.Platform99060101InitService;
import cn.com.platform.web.BaseController;


/**
 * 
 * 触发器设定 重复任务
 * 
 * @author 王笃鹏
 * @date 2019-01-17
 *
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_ADMIN + "/platform99060101")
public class Platform99060101Controller extends BaseController {

  @Autowired
  Platform99060101InitService platform99060101InitService;
  
  @ModelAttribute
  public Platform99060101Form setPlatform99060101Form(@ModelAttribute Platform99060101Form inForm) {
    return inForm;
  }

  @RequestMapping("/")
  public String doInit(Platform99060101Form inForm) {
    platform99060101InitService.doInit(inForm);
    
    return viewThymeleaf("/platform/platform/platform99/platform99060101");
  }

}
