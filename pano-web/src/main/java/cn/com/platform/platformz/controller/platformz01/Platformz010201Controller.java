package cn.com.platform.platformz.controller.platformz01;

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
import cn.com.platform.platformz.form.platformz01.Platformz010201Form;
import cn.com.platform.platformz.service.platformz01.Platformz010201EntryService;
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
@RequestMapping("/" + CommonConstantsIF.URI_BASE_MEMBER + "/platformz010201")
public class Platformz010201Controller extends BaseController {

  @Autowired
  Platformz010201EntryService platformz010201EntryService;

  @ModelAttribute
  public Platformz010201Form setPlatformz010201Form(@ModelAttribute Platformz010201Form inForm) {
    return inForm;
  }

  @RequestMapping(method = RequestMethod.GET, value = "/")
  public String index(Platformz010201Form inForm) {
    return viewJsp("/platform/platformz/platformz01/platformz010201");
  }

  /**
   * 保存
   * 
   */
  @ResponseBody
  @PostMapping(path = "/doEntry")
  public EasyJson<PlatformRole> doEntry(Platformz010201Form inForm) throws SystemException {
    return platformz010201EntryService.doEntry(inForm);
  }


}
