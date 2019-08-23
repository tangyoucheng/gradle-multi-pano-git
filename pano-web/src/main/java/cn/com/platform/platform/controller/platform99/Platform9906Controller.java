package cn.com.platform.platform.controller.platform99;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.platform.model.common.PlatformJobRepeat;
import cn.com.platform.platform.model.common01.PlatformJobRepeat01Model;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platform.form.platform99.Platform9906Form;
import cn.com.platform.platform.service.platform99.Platform9906DeleteService;
import cn.com.platform.platform.service.platform99.Platform9906InitService;
import cn.com.platform.platform.service.platform99.Platform9906PauseService;
import cn.com.platform.platform.service.platform99.Platform9906ResumeService;
import cn.com.platform.platform.service.platform99.Platform9906RunService;
import cn.com.platform.platform.service.platform99.Platform9906SearchService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;


/**
 * 
 * 定时任务
 * 
 * @author 王笃鹏
 * @date 2019-01-17
 *
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_ADMIN + "/platform9906")
public class Platform9906Controller extends BaseController {

  @Autowired
  Platform9906InitService platform9906InitService;
  @Autowired
  Platform9906SearchService platform9906SearchService;
  @Autowired
  Platform9906DeleteService platform9906DeleteService;
  @Autowired
  Platform9906ResumeService platform9906ResumeService;
  @Autowired
  Platform9906PauseService platform9906PauseService;
  @Autowired
  Platform9906RunService platform9906RunService;

  @ModelAttribute
  public Platform9906Form setPlatform9906Form(@ModelAttribute Platform9906Form inForm) {
    return inForm;
  }

  @RequestMapping("/")
  public String doInit(Platform9906Form inForm) {
    platform9906InitService.doInit(inForm);
    
    return viewThymeleaf("/platform/platform/platform99/platform9906");
  }

  /**
   * 检索
   * 
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doSearch")
  public EasyJson<PlatformJobRepeat01Model> doSearch(Platform9906Form inForm) throws SystemException {
    return platform9906SearchService.doSearch(inForm);
  }

  /**
   * 删除任务
   * 
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doDelete")
  public EasyJson<PlatformJobRepeat> doDelete(Platform9906Form inForm) throws SystemException {
    return platform9906DeleteService.doDelete(inForm);
  }
  
  /**
   * 恢复任务
   * 
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doResume")
  public EasyJson<PlatformJobRepeat> doResume(Platform9906Form inForm) throws SystemException {
    return platform9906ResumeService.doResume(inForm);
  }

  /**
   * 暂停任务
   * 
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doPause")
  public EasyJson<PlatformJobRepeat> doPause(Platform9906Form inForm) throws SystemException {
    return platform9906PauseService.doPause(inForm);
  }
  
  /**
   * 执行一次任务
   * 
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doRun")
  public EasyJson<PlatformJobRepeat> doRun(Platform9906Form inForm) throws SystemException {
    return platform9906RunService.doRun(inForm);
  }

}
