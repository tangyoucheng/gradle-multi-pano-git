package cn.com.platform.platform.controller.platform02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.platform.model.common.PlatformAdminRole;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platform.form.platform02.Platform0202Form;
import cn.com.platform.platform.service.platform02.Platform0202DeleteService;
import cn.com.platform.platform.service.platform02.Platform0202InitService;
import cn.com.platform.platform.service.platform02.Platform0202SearchService;
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
@RequestMapping("/" + CommonConstantsIF.URI_BASE_ADMIN + "/platform0202")
public class Platform0202Controller extends BaseController {

  @Autowired
  Platform0202InitService platform0202InitService;
  @Autowired
  Platform0202SearchService platform0202SearchService;
  @Autowired
  Platform0202DeleteService platform0202DeleteService;

  @ModelAttribute
  public Platform0202Form setPlatform0202Form(@ModelAttribute Platform0202Form inForm) {
    return inForm;
  }

  @RequestMapping("/")
  public String doInit(Platform0202Form inForm) {
    platform0202InitService.doInit(inForm);
    return viewJsp("/platform/platform/platform02/platform0202");
  }

  @ResponseBody
  @RequestMapping(path = "/doSearch")
  public EasyJson<PlatformAdminRole> doSearch(Platform0202Form inForm) throws SystemException {
    return platform0202SearchService.doSearch(inForm);
  }

  /**
   * 删除
   * 
   * @throws SystemException
   * 
   */
  @ResponseBody
  @PostMapping(path = "/doDelete")
  public EasyJson<PlatformAdminRole> doDelete(Platform0202Form inForm) throws SystemException {
    return platform0202DeleteService.doDelete(inForm);
  }
}
