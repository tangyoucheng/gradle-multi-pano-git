package cn.com.platform.platform.controller.platform01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platform.form.platform01.Platform019903Form;
import cn.com.platform.platform.model.common01.PlatformDepartment01Model;
import cn.com.platform.platform.service.platform01.Platform019903SearchService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;


/**
 * 
 * 公司部门管理-弹出框
 * 
 * @author 代仁宗
 * @date 2019-06-18
 *
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_ADMIN + "/platform019903")
public class Platform019903Controller extends BaseController {

  @Autowired
  Platform019903SearchService platform019903SearchService;

  @ModelAttribute
  public Platform019903Form setPlatform019903Form(@ModelAttribute Platform019903Form inForm) {
    return inForm;
  }
/** Iframe传ID */
  @RequestMapping("/{returnTargetIframe}")
  public String doInit(@PathVariable String returnTargetIframe, Platform019903Form inForm) {
    inForm.returnTargetIframe = returnTargetIframe;
    return viewJsp("/platform/platform/platform01/platform019903");
  }
/**检索*/
  @ResponseBody
  @RequestMapping(path = "/doSearch")
  public EasyJson<PlatformDepartment01Model> doSearch(Platform019903Form inForm) throws SystemException {
    return platform019903SearchService.doSearch(inForm);
  }
}
