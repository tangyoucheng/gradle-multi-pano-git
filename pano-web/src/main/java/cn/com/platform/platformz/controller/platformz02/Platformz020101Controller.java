package cn.com.platform.platformz.controller.platformz02;

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
import cn.com.platform.platformz.form.platformz02.Platformz020101Form;
import cn.com.platform.platformz.service.platformz02.Platformz020101EntryService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;


/**
 * 
 * 公司用户新建
 * 
 * @author 代仁宗
 * @date 2019-06-18
 *
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_MEMBER + "/platformz020101")
public class Platformz020101Controller extends BaseController {

  @Autowired
  Platformz020101EntryService platformz020101EntryService;

  @ModelAttribute
  public Platformz020101Form setPlatformz020101Form(@ModelAttribute Platformz020101Form inForm) {
    return inForm;
  }

  /**
   * 新建页面初始化
   */
  @RequestMapping(method = RequestMethod.GET, value = "/")
  public String index(Platformz020101Form inForm) {
    return viewJsp("/platform/platformz/platformz02/platformz020101");
  }

  /**
   * 登录
   * 
   * @throws SystemException
   */
  @ResponseBody
  @PostMapping(path = "/doEntry")
  public EasyJson<PlatformMember> doEntry(HttpServletResponse response, Platformz020101Form inForm)
      throws SystemException {
    return platformz020101EntryService.doEntry(inForm);
  }
}
