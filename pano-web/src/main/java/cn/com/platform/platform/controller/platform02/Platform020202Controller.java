package cn.com.platform.platform.controller.platform02;

import javax.servlet.http.HttpServletResponse;
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
import cn.com.platform.platform.form.platform02.Platform020202Form;
import cn.com.platform.platform.service.platform02.Platform020202UpdateService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;

/**
 * 
 * 角色编辑
 * 
 * @author 唐友成
 * @date 2018-08-10
 *
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_ADMIN + "/platform020202")
public class Platform020202Controller extends BaseController {

  @Autowired
  cn.com.platform.platform.service.platform02.Platform020202InitService Platform020202InitService;
  @Autowired
  Platform020202UpdateService platform020202UpdateService;

  @ModelAttribute
  public Platform020202Form setPlatform020202Form(@ModelAttribute Platform020202Form inForm) {
    return inForm;
  }

  /**
   * 编辑页面初始化
   * 
   * @throws SystemException
   */
  @RequestMapping(method = RequestMethod.GET, value = "/")
  public String index(Platform020202Form inForm) throws SystemException {
    Platform020202InitService.doInit(inForm);
    return viewJsp("/platform/platform/platform02/platform020202");
  }

  /**
   * 更新
   * 
   * @throws SystemException
   */
  @ResponseBody
  @PostMapping(path = "/doUpdate")
  public EasyJson<PlatformAdminRole> doUpdate(Platform020202Form inForm) throws SystemException {
    return platform020202UpdateService.doUpdate(inForm);
  }

}
