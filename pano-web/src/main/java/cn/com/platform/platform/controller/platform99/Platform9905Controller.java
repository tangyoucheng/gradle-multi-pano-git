package cn.com.platform.platform.controller.platform99;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.platform.model.common.PlatformJobCron;
import cn.com.platform.platform.model.common01.PlatformJobCron01Model;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platform.form.platform99.Platform9905Form;
import cn.com.platform.platform.service.platform99.Platform9905DeleteService;
import cn.com.platform.platform.service.platform99.Platform9905InitService;
import cn.com.platform.platform.service.platform99.Platform9905PauseService;
import cn.com.platform.platform.service.platform99.Platform9905ResumeService;
import cn.com.platform.platform.service.platform99.Platform9905RunService;
import cn.com.platform.platform.service.platform99.Platform9905SearchService;
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
@RequestMapping("/" + CommonConstantsIF.URI_BASE_ADMIN + "/platform9905")
public class Platform9905Controller extends BaseController {

  @Autowired
  Platform9905InitService platform9905InitService;
  @Autowired
  Platform9905SearchService platform9905SearchService;
  @Autowired
  Platform9905DeleteService platform9905DeleteService;
  @Autowired
  Platform9905ResumeService platform9905ResumeService;
  @Autowired
  Platform9905PauseService platform9905PauseService;
  @Autowired
  Platform9905RunService platform9905RunService;

  @ModelAttribute
  public Platform9905Form setPlatform9905Form(@ModelAttribute Platform9905Form inForm) {
    return inForm;
  }

  @RequestMapping("/")
  public String doInit(Platform9905Form inForm) {
    platform9905InitService.doInit(inForm);
    
    return viewThymeleaf("/platform/platform/platform99/platform9905");
  }

  /**
   * 检索
   * 
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doSearch")
  public EasyJson<PlatformJobCron01Model> doSearch(Platform9905Form inForm) throws SystemException {
    return platform9905SearchService.doSearch(inForm);
  }

  /**
   * 删除任务
   * 
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doDelete")
  public EasyJson<PlatformJobCron> doDelete(Platform9905Form inForm) throws SystemException {
    return platform9905DeleteService.doDelete(inForm);
  }
  
  /**
   * 恢复任务
   * 
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doResume")
  public EasyJson<PlatformJobCron> doResume(Platform9905Form inForm) throws SystemException {
    return platform9905ResumeService.doResume(inForm);
  }

  /**
   * 暂停任务
   * 
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doPause")
  public EasyJson<PlatformJobCron> doPause(Platform9905Form inForm) throws SystemException {
    return platform9905PauseService.doPause(inForm);
  }
  
  /**
   * 执行一次任务
   * 
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doRun")
  public EasyJson<PlatformJobCron> doRun(Platform9905Form inForm) throws SystemException {
    return platform9905RunService.doRun(inForm);
  }

}
