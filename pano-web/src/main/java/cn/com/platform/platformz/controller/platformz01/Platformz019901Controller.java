package cn.com.platform.platformz.controller.platformz01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.platform.model.common.PlatformCompany;
import cn.com.platform.platform.model.common.PlatformDepartment;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platformz.form.platformz01.Platformz019901Form;
import cn.com.platform.platformz.form.platformz02.Platformz029901Form;
import cn.com.platform.platformz.service.platformz01.Platformz019901EntryService;
import cn.com.platform.platformz.service.platformz01.Platformz019901InitService;
import cn.com.platform.platformz.service.platformz02.Platformz029901EntryService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;


/**
 * 
 * 社区新增
 * 
 * @author 代仁宗
 * @date 2019-06-17
 *
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_MEMBER + "/platformz019901")
public class Platformz019901Controller extends BaseController {


  @Autowired
  Platformz019901InitService platformz019901InitService;

  @Autowired
  Platformz019901EntryService platformz019901EntryService;

  @ModelAttribute
  public Platformz019901Form setPlatformz019901Form(@ModelAttribute Platformz019901Form inForm) {
    return inForm;
  }

  @RequestMapping(method = RequestMethod.GET, value = "/")
  public String index(Platformz019901Form inForm) throws SystemException {

    platformz019901InitService.doInit(inForm);
    return viewJsp("/platform/platformz/platformz01/platformz019901");
  }

  /**
   * 保存
   * 
   */
  @ResponseBody
  @PostMapping(path = "/doEntry")
  public EasyJson<PlatformDepartment> doEntry(Platformz019901Form inForm) throws SystemException {
    return platformz019901EntryService.doEntry(inForm);
  }


}
