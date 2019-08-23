package cn.com.platform.platform.controller.platform02;

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
import cn.com.platform.platform.form.platform02.Platform020102Form;
import cn.com.platform.platform.service.platform02.Platform020102InitService;
import cn.com.platform.platform.service.platform02.Platform020102UpdateService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;

/**
 * 
 * 管理员管理编辑
 * 
 * @author 唐友成
 * @date 2018-08-10
 *
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_ADMIN + "/platform020102")
public class Platform020102Controller extends BaseController {

  @Autowired
  Platform020102InitService Platform020102InitService;
  @Autowired
  Platform020102UpdateService platform020102UpdateService;

  @ModelAttribute
  public Platform020102Form setPlatform020102Form(@ModelAttribute Platform020102Form inForm) {
    return inForm;
  }

  /**
   * 编辑页面初始化
   * 
   * @throws SystemException
   */
  @RequestMapping(method = RequestMethod.GET, value = "/")
  public String index(Platform020102Form inForm) throws SystemException {
    Platform020102InitService.doInit(inForm);
    return viewJsp("/platform/platform/platform02/platform020102");
  }

  /**
   * 更新
   * 
   * @throws SystemException
   */
  @ResponseBody
  @PostMapping(path = "/doUpdate")
  public EasyJson<PlatformAdminUser> doUpdate(Platform020102Form inForm) throws SystemException {
    return platform020102UpdateService.doUpdate(inForm);
  }

}
