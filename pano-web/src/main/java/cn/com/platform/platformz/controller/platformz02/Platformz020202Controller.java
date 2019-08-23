package cn.com.platform.platformz.controller.platformz02;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.platform.model.common.PlatformRole;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platformz.form.platformz02.Platformz020202Form;
import cn.com.platform.platformz.service.platformz02.Platformz020202InitService;
import cn.com.platform.platformz.service.platformz02.Platformz020202UpdateService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;

/**
 * 
 * 公司角色编辑
 * 
 * @author 代仁宗
 * @date 2019-06-18
 *
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_MEMBER + "/platformz020202")
public class Platformz020202Controller extends BaseController {

  @Autowired
  Platformz020202InitService platformz020202InitService;
  @Autowired
  Platformz020202UpdateService platformz020202UpdateService;

  @ModelAttribute
  public Platformz020202Form setPlatformz020202Form(@ModelAttribute Platformz020202Form inForm) {
    return inForm;
  }

  /**
   * 编辑页面初始化
   * 
   * @throws SystemException
   */
  @RequestMapping(method = RequestMethod.GET, value = "/")
  public String index(Platformz020202Form inForm) throws SystemException {
    platformz020202InitService.doInit(inForm);
    return viewJsp("/platform/platformz/platformz02/platformz020202");
  }

  /**
   * 更新
   * 
   * @throws SystemException
   */
  @ResponseBody
  @PostMapping(path = "/doUpdate")
  public EasyJson<PlatformRole> doUpdate(Platformz020202Form inForm) throws SystemException {
    return platformz020202UpdateService.doUpdate(inForm);
  }

}
