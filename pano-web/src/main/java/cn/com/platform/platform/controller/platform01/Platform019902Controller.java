package cn.com.platform.platform.controller.platform01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platform.form.platform01.Platform019902Form;
import cn.com.platform.platform.model.common.PlatformDepartment;
import cn.com.platform.platform.service.platform01.Platform019902InitService;
import cn.com.platform.platform.service.platform01.Platform019902UpdateService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;

/**
 * 
 * 社区编辑
 * 
 * @author 代仁宗
 * @date 2019-06-17
 *
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_ADMIN + "/platform019902")
public class Platform019902Controller extends BaseController {

  /** 初始化页面 */
  @Autowired
  Platform019902InitService platform019902InitService;
  @Autowired
  Platform019902UpdateService platform019902UpdateService;

  @ModelAttribute
  public Platform019902Form setPlatform019902Form(@ModelAttribute Platform019902Form inForm) {
    return inForm;
  }

  /**
   * 编辑页面初始化
   * 
   * @throws SystemException
   */
  @RequestMapping(method = RequestMethod.GET, value = "/")
  public String index(Platform019902Form inForm) throws SystemException {
    platform019902InitService.doInit(inForm);
    return viewJsp("/platform/platform/platform01/platform019902");
  }

  /**
   * 更新
   * 
   * @throws SystemException
   */
  @ResponseBody
  @PostMapping(path = "/doUpdate")
  public EasyJson<PlatformDepartment> doUpdate(Platform019902Form inForm) throws SystemException {
    return platform019902UpdateService.doUpdate(inForm);
  }

}
