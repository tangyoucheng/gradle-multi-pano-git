package cn.com.platform.platformz.controller.platformz02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.platform.model.common.PlatformRole;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platformz.form.platformz02.Platformz0202Form;
import cn.com.platform.platformz.service.platformz02.Platformz0202DeleteService;
import cn.com.platform.platformz.service.platformz02.Platformz0202InitService;
import cn.com.platform.platformz.service.platformz02.Platformz0202SearchService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;


/**
 * 
 * 公司角色管理
 * 
 * @author 代仁宗
 * @date 2019-06-18
 *
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_MEMBER + "/platformz0202")
public class Platformz0202Controller extends BaseController {

  @Autowired
  Platformz0202InitService platformz0202InitService;
  @Autowired
  Platformz0202SearchService platformz0202SearchService;
  @Autowired
  Platformz0202DeleteService platformz0202DeleteService;

  @ModelAttribute
  public Platformz0202Form setPlatformz0202Form(@ModelAttribute Platformz0202Form inForm) {
    return inForm;
  }

  @RequestMapping("/")
  public String doInit(Platformz0202Form inForm) {
    platformz0202InitService.doInit(inForm);
    return viewJsp("/platform/platformz/platformz02/platformz0202");
  }

  @ResponseBody
  @RequestMapping(path = "/doSearch")
  public EasyJson<PlatformRole> doSearch(Platformz0202Form inForm) throws SystemException {
    return platformz0202SearchService.doSearch(inForm);
  }

  /**
   * 删除
   * 
   * @throws SystemException
   * 
   */
  @ResponseBody
  @PostMapping(path = "/doDelete")
  public EasyJson<PlatformRole> doDelete(Platformz0202Form inForm) throws SystemException {
    return platformz0202DeleteService.doDelete(inForm);
  }
}
