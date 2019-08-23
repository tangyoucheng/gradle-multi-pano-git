package cn.com.platform.platformz.controller.platformz01;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.platform.model.common.PlatformMember;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platformz.form.platformz01.Platformz0101Form;
import cn.com.platform.platformz.service.platformz01.Platformz0101DeleteService;
import cn.com.platform.platformz.service.platformz01.Platformz0101InitService;
import cn.com.platform.platformz.service.platformz01.Platformz0101SearchService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;


/**
 * 用户管理。.
 * 
 * @author 唐友成
 * @date 2018-08-10
 *
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_MEMBER + "/platformz0101")
public class Platformz0101Controller extends BaseController {

  @Autowired
  Platformz0101InitService platformz0101InitService;
  @Autowired
  Platformz0101SearchService platformz0101SearchService;
  @Autowired
  Platformz0101DeleteService platformz0101DeleteService;

  @ModelAttribute
  public Platformz0101Form setPlatformz0101Form(@ModelAttribute Platformz0101Form inForm) {
    return inForm;
  }

  @RequestMapping("/")
  public String doInit(Platformz0101Form inForm) {
    platformz0101InitService.doInit(inForm);
    return viewJsp("/platform/platformz/platformz01/platformz0101");
  }

  /**
   * 检索。.
   * 
   * @throws SystemException 异常的场合
   */
  @ResponseBody
  @RequestMapping(path = "/doSearch")
  public EasyJson<PlatformMember> doSearch(Platformz0101Form inForm) throws SystemException {
    return platformz0101SearchService.doSearch(inForm);
  }

  /**
   * 删除。
   * 
   * @throws SystemException 异常的场合
   */
  @ResponseBody
  @PostMapping(path = "/doDelete")
  public EasyJson<PlatformMember> doDelete(HttpServletResponse response, Platformz0101Form inForm)
      throws SystemException {
    return platformz0101DeleteService.doDelete(inForm);
  }
}
