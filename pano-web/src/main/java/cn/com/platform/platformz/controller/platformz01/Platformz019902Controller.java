package cn.com.platform.platformz.controller.platformz01;

import javax.servlet.http.HttpServletResponse;
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
import cn.com.platform.platformz.form.platformz01.Platformz019902Form;
import cn.com.platform.platformz.form.platformz02.Platformz029902Form;
import cn.com.platform.platformz.service.platformz01.Platformz019902InitService;
import cn.com.platform.platformz.service.platformz01.Platformz019902UpdateService;
import cn.com.platform.platformz.service.platformz02.Platformz029902InitService;
import cn.com.platform.platformz.service.platformz02.Platformz029902UpdateService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;

/**
 * 
 * 社区编辑
 * 
 * @author 代仁宗
 * @date 2019-06-17
 *
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_MEMBER + "/platformz019902")
public class Platformz019902Controller extends BaseController {

  /** 初始化页面 */
  @Autowired
  Platformz019902InitService platformz019902InitService;
  @Autowired
  Platformz019902UpdateService platformz019902UpdateService;

  @ModelAttribute
  public Platformz029902Form setPlatformz029902Form(@ModelAttribute Platformz029902Form inForm) {
    return inForm;
  }

  /**
   * 编辑页面初始化
   * 
   * @throws SystemException
   */
  @RequestMapping(method = RequestMethod.GET, value = "/")
  public String index(Platformz019902Form inForm) throws SystemException {
    platformz019902InitService.doInit(inForm);
    return viewJsp("/platform/platformz/platformz01/platformz019902");
  }

  /**
   * 更新
   * 
   * @throws SystemException
   */
  @ResponseBody
  @PostMapping(path = "/doUpdate")
  public EasyJson<PlatformDepartment> doUpdate(Platformz019902Form inForm) throws SystemException {
    return platformz019902UpdateService.doUpdate(inForm);
  }

}
