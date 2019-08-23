package cn.com.platform.platformz.controller.platformz02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.platform.model.common.PlatformCompany;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platformz.form.platformz02.Platformz029902Form;
import cn.com.platform.platformz.service.platformz02.Platformz029902InitService;
import cn.com.platform.platformz.service.platformz02.Platformz029902UpdateService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;

/**
 * 公司编辑。
 * 
 * @author 代仁宗
 * @date 2019-06-17
 *
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_MEMBER + "/platformz029902")
public class Platformz029902Controller extends BaseController {

  @Autowired
  Platformz029902InitService platformz029902InitService;
  @Autowired
  Platformz029902UpdateService platformz029902UpdateService;

  @ModelAttribute
  public Platformz029902Form setPlatformz029902Form(@ModelAttribute Platformz029902Form inForm) {
    return inForm;
  }

  /**
   * 编辑页面初始化。
   * 
   * @throws SystemException 异常的场合
   */
  @RequestMapping(method = RequestMethod.GET, value = "/")
  public String index(Platformz029902Form inForm) throws SystemException {
    platformz029902InitService.doInit(inForm);
    return viewJsp("/platform/platformz/platformz02/platformz029902");
  }

  /**
   * 更新。
   * 
   * @throws SystemException 异常的场合
   */
  @ResponseBody
  @PostMapping(path = "/doUpdate")
  public EasyJson<PlatformCompany> doUpdate(Platformz029902Form inForm) throws SystemException {
    return platformz029902UpdateService.doUpdate(inForm);
  }

}
