package cn.com.platform.platformz.controller.platformz01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.platform.model.common.PlatformRole;
import cn.com.platform.platform.model.common01.PlatformDepartment01Model;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platformz.form.platformz01.Platformz019903Form;
import cn.com.platform.platformz.form.platformz02.Platformz020203Form;
import cn.com.platform.platformz.service.platformz01.Platformz019903SearchService;
import cn.com.platform.platformz.service.platformz02.Platformz020203SearchService;
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
@RequestMapping("/" + CommonConstantsIF.URI_BASE_MEMBER + "/platformz019903")
public class Platformz019903Controller extends BaseController {

  @Autowired
  Platformz019903SearchService platformz019903SearchService;

  @ModelAttribute
  public Platformz019903Form setPlatformz019903Form(@ModelAttribute Platformz019903Form inForm) {
    return inForm;
  }
/** Iframe传ID */
  @RequestMapping("/{returnTargetIframe}")
  public String doInit(@PathVariable String returnTargetIframe, Platformz019903Form inForm) {
    inForm.returnTargetIframe = returnTargetIframe;
    return viewJsp("/platform/platformz/platformz01/platformz019903");
  }
/**检索*/
  @ResponseBody
  @RequestMapping(path = "/doSearch")
  public EasyJson<PlatformDepartment01Model> doSearch(Platformz019903Form inForm) throws SystemException {
    return platformz019903SearchService.doSearch(inForm);
  }
}
