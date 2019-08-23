package cn.com.platform.platform.controller.platform02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.platform.model.common.PlatformAdminRole;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platform.form.platform02.Platform020203Form;
import cn.com.platform.platform.service.platform02.Platform020203SearchService;
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
@RequestMapping("/" + CommonConstantsIF.URI_BASE_ADMIN + "/platform020203")
public class Platform020203Controller extends BaseController {

  @Autowired
  Platform020203SearchService platform020203SearchService;

  @ModelAttribute
  public Platform020203Form setPlatform020203Form(@ModelAttribute Platform020203Form inForm) {
    return inForm;
  }

  @RequestMapping("/{returnTargetIframe}")
  public String doInit(@PathVariable String returnTargetIframe, Platform020203Form inForm) {
    inForm.returnTargetIframe = returnTargetIframe;
    return viewJsp("/platform/platform/platform02/platform020203");
  }

  @ResponseBody
  @RequestMapping(path = "/doSearch")
  public EasyJson<PlatformAdminRole> doSearch(Platform020203Form inForm) throws SystemException {
    return platform020203SearchService.doSearch(inForm);
  }
}
