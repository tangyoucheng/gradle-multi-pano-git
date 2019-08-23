package cn.com.platform.platform.controller.platform99;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.platform.model.common.PlatformJobRepeat;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platform.form.platform99.Platform990601Form;
import cn.com.platform.platform.service.platform99.Platform990601EntryService;
import cn.com.platform.platform.service.platform99.Platform990601InitService;
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
@RequestMapping("/" + CommonConstantsIF.URI_BASE_ADMIN + "/platform990601")
public class Platform990601Controller extends BaseController {

  @Autowired
  Platform990601InitService platform990601InitService;
  @Autowired
  Platform990601EntryService platform990601EntryService;
  
  @ModelAttribute
  public Platform990601Form setPlatform990601Form(@ModelAttribute Platform990601Form inForm) {
    return inForm;
  }

  @RequestMapping("/")
  public String doInit(Platform990601Form inForm) {
    platform990601InitService.doInit(inForm);
    return viewThymeleaf("/platform/platform/platform99/platform990601");
  }

  /**
   * 新建保存
   * 
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doEntry")
  public EasyJson<PlatformJobRepeat> doEntry(Platform990601Form inForm) throws SystemException {
    return platform990601EntryService.doEntry(inForm);
  }
}
