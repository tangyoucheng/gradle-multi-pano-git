package cn.com.platform.platformz.controller.platformz02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.platform.model.common.PlatformCompany;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platformz.form.platformz02.Platformz029901Form;
import cn.com.platform.platformz.service.platformz02.Platformz029901EntryService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;


/**
 * 
 * 公司新增
 * 
 * @author 代仁宗
 * @date 2019-06-17
 *
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_MEMBER + "/platformz029901")
public class Platformz029901Controller extends BaseController {

  @Autowired
  Platformz029901EntryService platformz029901EntryService;

  @ModelAttribute
  public Platformz029901Form setPlatformz029901Form(@ModelAttribute Platformz029901Form inForm) {
    return inForm;
  }

  @RequestMapping(method = RequestMethod.GET, value = "/")
  public String index(Platformz029901Form inForm) {
    return viewJsp("/platform/platformz/platformz02/platformz029901");
  }

  /**
   * 保存
   * 
   */
  @ResponseBody
  @PostMapping(path = "/doEntry")
  public EasyJson<PlatformCompany> doEntry(Platformz029901Form inForm) throws SystemException {
    return platformz029901EntryService.doEntry(inForm);
  }


}
