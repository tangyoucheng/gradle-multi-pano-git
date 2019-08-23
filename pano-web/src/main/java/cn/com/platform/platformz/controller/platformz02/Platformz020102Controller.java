package cn.com.platform.platformz.controller.platformz02;

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
import cn.com.platform.platformz.form.platformz02.Platformz020102Form;
import cn.com.platform.platformz.service.platformz02.Platformz020102InitService;
import cn.com.platform.platformz.service.platformz02.Platformz020102UpdateService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;

/**
 * 
 * 公司用户编辑
 * 
 * @author 代仁宗
 * @date 2019-06-19
 *
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_MEMBER + "/platformz020102")
public class Platformz020102Controller extends BaseController {

  @Autowired
  Platformz020102InitService platformz020102InitService;
  @Autowired
  Platformz020102UpdateService platformz020102UpdateService;

  @ModelAttribute
  public Platformz020102Form setPlatformz020102Form(@ModelAttribute Platformz020102Form inForm) {
    return inForm;
  }

  /**
   * 编辑页面初始化
   * 
   * @throws SystemException
   */
  @RequestMapping(method = RequestMethod.GET, value = "/")
  public String index(Platformz020102Form inForm) throws SystemException {
    platformz020102InitService.doInit(inForm);
    return viewJsp("/platform/platformz/platformz02/platformz020102");
  }

  /**
   * 更新
   * 
   * @throws SystemException
   */
  @ResponseBody
  @PostMapping(path = "/doUpdate")
  public EasyJson<PlatformMember> doUpdate(Platformz020102Form inForm) throws SystemException {
    return platformz020102UpdateService.doUpdate(inForm);
  }

}
