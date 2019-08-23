package cn.com.platform.platform.controller.platform03;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platform.form.platform03.Platform039901Form;
import cn.com.platform.platform.model.common.PlatformCompany;
import cn.com.platform.platform.service.platform03.Platform039901EntryService;
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
@RequestMapping("/" + CommonConstantsIF.URI_BASE_ADMIN + "/platform039901")
public class Platform039901Controller extends BaseController {

  @Autowired
  Platform039901EntryService platform039901EntryService;

  @ModelAttribute
  public Platform039901Form setPlatform039901Form(@ModelAttribute Platform039901Form inForm) {
    return inForm;
  }

  @RequestMapping(method = RequestMethod.GET, value = "/")
  public String index(Platform039901Form inForm) {
    return viewJsp("/platform/platform/platform03/platform039901");
  }

  /**
   * 保存
   * 
   */
  @ResponseBody
  @PostMapping(path = "/doEntry")
  public EasyJson<PlatformCompany> doEntry(Platform039901Form inForm) throws SystemException {
    return platform039901EntryService.doEntry(inForm);
  }


}
