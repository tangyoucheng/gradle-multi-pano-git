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
import cn.com.platform.platform.form.platform01.Platform019901Form;
import cn.com.platform.platform.model.common.PlatformDepartment;
import cn.com.platform.platform.service.platform01.Platform019901EntryService;
import cn.com.platform.platform.service.platform01.Platform019901InitService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;


/**
 * 
 * 社区新增
 * 
 * @author 代仁宗
 * @date 2019-06-17
 *
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_ADMIN + "/platform019901")
public class Platform019901Controller extends BaseController {


  @Autowired
  Platform019901InitService platform019901InitService;

  @Autowired
  Platform019901EntryService platform019901EntryService;

  @ModelAttribute
  public Platform019901Form setPlatform019901Form(@ModelAttribute Platform019901Form inForm) {
    return inForm;
  }

  @RequestMapping(method = RequestMethod.GET, value = "/")
  public String index(Platform019901Form inForm) throws SystemException {

    platform019901InitService.doInit(inForm);
    return viewJsp("/platform/platform/platform01/platform019901");
  }

  /**
   * 保存
   * 
   */
  @ResponseBody
  @PostMapping(path = "/doEntry")
  public EasyJson<PlatformDepartment> doEntry(Platform019901Form inForm) throws SystemException {
    return platform019901EntryService.doEntry(inForm);
  }


}
