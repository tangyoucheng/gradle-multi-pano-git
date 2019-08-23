package cn.com.platform.platform.controller.platform02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.platform.model.common.PlatformAdminRole;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platform.form.platform02.Platform020201Form;
import cn.com.platform.platform.service.platform02.Platform020201EntryService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;


/**
 * 
 * 角色管理新增
 * 
 * @author 唐友成
 * @date 2018-08-12
 *
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_ADMIN + "/platform020201")
public class Platform020201Controller extends BaseController {

  @Autowired
  Platform020201EntryService platform020201EntryService;

  @ModelAttribute
  public Platform020201Form setPlatform020201Form(@ModelAttribute Platform020201Form inForm) {
    return inForm;
  }

  @RequestMapping(method = RequestMethod.GET, value = "/")
  public String index(Platform020201Form inForm) {
    return viewJsp("/platform/platform/platform02/platform020201");
  }

  /**
   * 保存
   * 
   */
  @ResponseBody
  @PostMapping(path = "/doEntry")
  public EasyJson<PlatformAdminRole> doEntry(Platform020201Form inForm) throws SystemException {
    return platform020201EntryService.doEntry(inForm);
  }


}
