package cn.com.platform.platformz.controller.platformz01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.platform.model.common.PlatformRole;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platformz.form.platformz01.Platformz010203Form;
import cn.com.platform.platformz.service.platformz01.Platformz010203SearchService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;


/**
 * 
 * 角色管理
 * 
 * @author 唐友成
 * @date 2018-08-12
 *
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_MEMBER + "/platformz010203")
public class Platformz010203Controller extends BaseController {

  @Autowired
  Platformz010203SearchService platformz010203SearchService;

  @ModelAttribute
  public Platformz010203Form setPlatformz010203Form(@ModelAttribute Platformz010203Form inForm) {
    return inForm;
  }

  @RequestMapping("/{returnTargetIframe}")
  public String doInit(@PathVariable String returnTargetIframe, Platformz010203Form inForm) {
    inForm.returnTargetIframe = returnTargetIframe;
    return viewJsp("/platform/platformz/platformz01/platformz010203");
  }

  @ResponseBody
  @RequestMapping(path = "/doSearch")
  public EasyJson<PlatformRole> doSearch(Platformz010203Form inForm) throws SystemException {
    return platformz010203SearchService.doSearch(inForm);
  }
}
