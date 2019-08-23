package cn.com.platform.platformz.controller.platformz02;

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
import cn.com.platform.platformz.form.platformz02.Platformz020201Form;
import cn.com.platform.platformz.service.platformz02.Platformz020201EntryService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;


/**
 * 
 * 公司角色管理新增
 * 
 * @author 代仁宗
 * @date 2019-06-18
 *
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_MEMBER + "/platformz020201")
public class Platformz020201Controller extends BaseController {

  @Autowired
  Platformz020201EntryService platformz020201EntryService;

  @ModelAttribute
  public Platformz020201Form setPlatformz020201Form(@ModelAttribute Platformz020201Form inForm) {
    return inForm;
  }

  @RequestMapping(method = RequestMethod.GET, value = "/")
  public String index(Platformz020201Form inForm) {
    return viewJsp("/platform/platformz/platformz02/platformz020201");
  }

  /**
   * 保存
   * 
   */
  @ResponseBody
  @PostMapping(path = "/doEntry")
  public EasyJson<PlatformRole> doEntry(Platformz020201Form inForm) throws SystemException {
    return platformz020201EntryService.doEntry(inForm);
  }


}
