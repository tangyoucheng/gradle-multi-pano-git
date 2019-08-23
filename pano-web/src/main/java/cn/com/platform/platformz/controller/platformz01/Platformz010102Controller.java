package cn.com.platform.platformz.controller.platformz01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.platform.model.common.PlatformMember;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platformz.form.platformz01.Platformz010102Form;
import cn.com.platform.platformz.service.platformz01.Platformz010102InitService;
import cn.com.platform.platformz.service.platformz01.Platformz010102UpdateService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;

/**
 * 用户管理编辑。
 * 
 * @author 唐友成
 * @date 2018-08-10
 *
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_MEMBER + "/platformz010102")
public class Platformz010102Controller extends BaseController {

  @Autowired
  Platformz010102InitService platformz010102InitService;
  @Autowired
  Platformz010102UpdateService platformz010102UpdateService;

  @ModelAttribute
  public Platformz010102Form setPlatformz010102Form(@ModelAttribute Platformz010102Form inForm) {
    return inForm;
  }

  /**
   * 编辑页面初始化。
   * 
   * @throws SystemException 异常的场合
   */
  @RequestMapping(method = RequestMethod.GET, value = "/")
  public String index(Platformz010102Form inForm) throws SystemException {
    platformz010102InitService.doInit(inForm);
    return viewJsp("/platform/platformz/platformz01/platformz010102");
  }

  /**
   * 更新。
   * 
   * @throws SystemException 异常的场合
   */
  @ResponseBody
  @PostMapping(path = "/doUpdate")
  public EasyJson<PlatformMember> doUpdate(Platformz010102Form inForm) throws SystemException {
    return platformz010102UpdateService.doUpdate(inForm);
  }

}
