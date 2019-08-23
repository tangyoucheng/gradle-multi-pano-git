package cn.com.platform.platformz.controller.platformz99;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.platform.model.common.PlatformJobCron;
import cn.com.platform.platform.model.common01.PlatformJobCron01Model;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platformz.form.platformz99.Platformz9905Form;
import cn.com.platform.platformz.service.platformz99.Platformz9905DeleteService;
import cn.com.platform.platformz.service.platformz99.Platformz9905InitService;
import cn.com.platform.platformz.service.platformz99.Platformz9905PauseService;
import cn.com.platform.platformz.service.platformz99.Platformz9905ResumeService;
import cn.com.platform.platformz.service.platformz99.Platformz9905RunService;
import cn.com.platform.platformz.service.platformz99.Platformz9905SearchService;
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
@RequestMapping("/" + CommonConstantsIF.URI_BASE_MEMBER + "/Platformz9905")
public class Platformz9905Controller extends BaseController {

  @Autowired
  Platformz9905InitService Platformz9905InitService;
  @Autowired
  Platformz9905SearchService Platformz9905SearchService;
  @Autowired
  Platformz9905DeleteService Platformz9905DeleteService;
  @Autowired
  Platformz9905ResumeService Platformz9905ResumeService;
  @Autowired
  Platformz9905PauseService Platformz9905PauseService;
  @Autowired
  Platformz9905RunService Platformz9905RunService;

  @ModelAttribute
  public Platformz9905Form setPlatformz9905Form(@ModelAttribute Platformz9905Form inForm) {
    return inForm;
  }

  @RequestMapping("/")
  public String doInit(Platformz9905Form inForm) {
    Platformz9905InitService.doInit(inForm);
    
    return viewThymeleaf("/platform/platformz/Platformz99/Platformz9905");
  }

  /**
   * 检索
   * 
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doSearch")
  public EasyJson<PlatformJobCron01Model> doSearch(Platformz9905Form inForm) throws SystemException {
    return Platformz9905SearchService.doSearch(inForm);
  }

  /**
   * 删除任务
   * 
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doDelete")
  public EasyJson<PlatformJobCron> doDelete(Platformz9905Form inForm) throws SystemException {
    return Platformz9905DeleteService.doDelete(inForm);
  }
  
  /**
   * 恢复任务
   * 
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doResume")
  public EasyJson<PlatformJobCron> doResume(Platformz9905Form inForm) throws SystemException {
    return Platformz9905ResumeService.doResume(inForm);
  }

  /**
   * 暂停任务
   * 
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doPause")
  public EasyJson<PlatformJobCron> doPause(Platformz9905Form inForm) throws SystemException {
    return Platformz9905PauseService.doPause(inForm);
  }
  
  /**
   * 执行一次任务
   * 
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doRun")
  public EasyJson<PlatformJobCron> doRun(Platformz9905Form inForm) throws SystemException {
    return Platformz9905RunService.doRun(inForm);
  }

}
