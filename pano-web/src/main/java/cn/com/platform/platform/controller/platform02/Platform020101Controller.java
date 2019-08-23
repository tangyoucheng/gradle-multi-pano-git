package cn.com.platform.platform.controller.platform02;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.platform.model.common.PlatformAdminUser;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platform.form.platform02.Platform020101Form;
import cn.com.platform.platform.service.platform02.Platform020101EntryService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;

/**
 * 
 * 管理员管理新增
 * 
 * @author 唐友成
 * @date 2018-08-10
 *
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_ADMIN + "/platform020101")
public class Platform020101Controller extends BaseController {

  @Autowired
  Platform020101EntryService platform020101EntryService;

  @ModelAttribute
  public Platform020101Form setPlatform020101Form(@ModelAttribute Platform020101Form inForm) {
    return inForm;
  }

  /**
   * 新建页面初始化
   */
  @RequestMapping(method = RequestMethod.GET, value = "/")
  public String index(Platform020101Form inForm) {
    return viewJsp("/platform/platform/platform02/platform020101");
  }

  /**
   * 登录
   * 
   * @throws SystemException
   */
  @ResponseBody
  @PostMapping(path = "/doEntry")
  public EasyJson<PlatformAdminUser> doEntry(HttpServletResponse response, Platform020101Form inForm)
      throws SystemException {
    return platform020101EntryService.doEntry(inForm);
  }

}
