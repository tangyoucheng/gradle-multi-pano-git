package cn.com.platform.platform.controller.platform02;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.platform.model.common.PlatformAdminUser;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platform.form.platform02.Platform0201Form;
import cn.com.platform.platform.service.platform02.Platform0201DeleteService;
import cn.com.platform.platform.service.platform02.Platform0201InitService;
import cn.com.platform.platform.service.platform02.Platform0201SearchService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;


/**
 * 
 * 管理员用户管理
 * 
 * @author 唐友成
 * @date 2018-08-10
 *
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_ADMIN + "/platform0201")
public class Platform0201Controller extends BaseController {

  @Autowired
  Platform0201InitService platform0201InitService;
  @Autowired
  Platform0201SearchService platform0201SearchService;
  @Autowired
  Platform0201DeleteService platform0201DeleteService;

  @ModelAttribute
  public Platform0201Form setPlatform0201Form(@ModelAttribute Platform0201Form inForm) {
    return inForm;
  }

  @RequestMapping("/")
  public String doInit(Platform0201Form inForm) {
    platform0201InitService.doInit(inForm);
    return viewJsp("/platform/platform/platform02/platform0201");
  }

  /**
   * 检索
   * 
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doSearch")
  public EasyJson<PlatformAdminUser> doSearch(Platform0201Form inForm) throws SystemException {
    return platform0201SearchService.doSearch(inForm);
  }

  /**
   * 删除
   * 
   * @throws SystemException
   */
  @ResponseBody
  @PostMapping(path = "/doDelete")
  public EasyJson<PlatformAdminUser> doDelete(HttpServletResponse response, Platform0201Form inForm)
      throws SystemException {
    return platform0201DeleteService.doDelete(inForm);
  }
}
