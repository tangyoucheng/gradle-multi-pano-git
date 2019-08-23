package cn.com.platform.platformz.controller.platformz01;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.platform.model.common.PlatformMember;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platformz.form.platformz01.Platformz010101Form;
import cn.com.platform.platformz.service.platformz01.Platformz010101EntryService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;

/**
 * 用户管理新增。
 * 
 * @author 唐友成
 * @date 2018-08-10
 *
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_MEMBER + "/platformz010101")
public class Platformz010101Controller extends BaseController {

  @Autowired
  Platformz010101EntryService platformz010101EntryService;

  @ModelAttribute
  public Platformz010101Form setPlatformz010101Form(@ModelAttribute Platformz010101Form inForm) {
    return inForm;
  }

  /**
   * 新建页面初始化。
   */
  @RequestMapping(method = RequestMethod.GET, value = "/")
  public String index(Platformz010101Form inForm) {
    return viewJsp("/platform/platformz/platformz01/platformz010101");
  }

  /**
   * 登录。
   * 
   * @throws SystemException 异常的场合
   */
  @ResponseBody
  @PostMapping(path = "/doEntry")
  public EasyJson<PlatformMember> doEntry(HttpServletResponse response, Platformz010101Form inForm)
      throws SystemException {
    return platformz010101EntryService.doEntry(inForm);
  }

}
