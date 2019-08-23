package cn.com.platform.platformz.controller.platformz99;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.platform.model.common.PlatformJobRepeat;
import cn.com.platform.platform.model.common01.PlatformJobRepeat01Model;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platformz.form.platformz99.Platformz9906Form;
import cn.com.platform.platformz.service.platformz99.Platformz9906DeleteService;
import cn.com.platform.platformz.service.platformz99.Platformz9906InitService;
import cn.com.platform.platformz.service.platformz99.Platformz9906PauseService;
import cn.com.platform.platformz.service.platformz99.Platformz9906ResumeService;
import cn.com.platform.platformz.service.platformz99.Platformz9906RunService;
import cn.com.platform.platformz.service.platformz99.Platformz9906SearchService;
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
@RequestMapping("/" + CommonConstantsIF.URI_BASE_MEMBER + "/Platformz9906")
public class Platformz9906Controller extends BaseController {

  @Autowired
  Platformz9906InitService Platformz9906InitService;
  @Autowired
  Platformz9906SearchService Platformz9906SearchService;
  @Autowired
  Platformz9906DeleteService Platformz9906DeleteService;
  @Autowired
  Platformz9906ResumeService Platformz9906ResumeService;
  @Autowired
  Platformz9906PauseService Platformz9906PauseService;
  @Autowired
  Platformz9906RunService Platformz9906RunService;

  @ModelAttribute
  public Platformz9906Form setPlatformz9906Form(@ModelAttribute Platformz9906Form inForm) {
    return inForm;
  }

  @RequestMapping("/")
  public String doInit(Platformz9906Form inForm) {
    Platformz9906InitService.doInit(inForm);
    
    return viewThymeleaf("/platform/platformz/Platformz99/Platformz9906");
  }

  /**
   * 检索
   * 
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doSearch")
  public EasyJson<PlatformJobRepeat01Model> doSearch(Platformz9906Form inForm) throws SystemException {
    return Platformz9906SearchService.doSearch(inForm);
  }

  /**
   * 删除任务
   * 
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doDelete")
  public EasyJson<PlatformJobRepeat> doDelete(Platformz9906Form inForm) throws SystemException {
    return Platformz9906DeleteService.doDelete(inForm);
  }
  
  /**
   * 恢复任务
   * 
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doResume")
  public EasyJson<PlatformJobRepeat> doResume(Platformz9906Form inForm) throws SystemException {
    return Platformz9906ResumeService.doResume(inForm);
  }

  /**
   * 暂停任务
   * 
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doPause")
  public EasyJson<PlatformJobRepeat> doPause(Platformz9906Form inForm) throws SystemException {
    return Platformz9906PauseService.doPause(inForm);
  }
  
  /**
   * 执行一次任务
   * 
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doRun")
  public EasyJson<PlatformJobRepeat> doRun(Platformz9906Form inForm) throws SystemException {
    return Platformz9906RunService.doRun(inForm);
  }

}
