package cn.com.platform.platformz.controller.platformz02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.platform.model.common.PlatformRole;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platformz.form.platformz02.Platformz020203Form;
import cn.com.platform.platformz.service.platformz02.Platformz020203SearchService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;


/**
 * 
 * 公司角色管理-弹出框
 * 
 * @author 代仁宗
 * @date 2019-06-18
 *
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_MEMBER + "/platformz020203")
public class Platformz020203Controller extends BaseController {

  @Autowired
  Platformz020203SearchService platformz020203SearchService;

  @ModelAttribute
  public Platformz020203Form setPlatformz020203Form(@ModelAttribute Platformz020203Form inForm) {
    return inForm;
  }
/** Iframe传ID */
  @RequestMapping("/{returnTargetIframe}")
  public String doInit(@PathVariable String returnTargetIframe, Platformz020203Form inForm) {
    inForm.returnTargetIframe = returnTargetIframe;
    return viewJsp("/platform/platformz/platformz02/platformz020203");
  }
/**检索*/
  @ResponseBody
  @RequestMapping(path = "/doSearch")
  public EasyJson<PlatformRole> doSearch(Platformz020203Form inForm) throws SystemException {
    return platformz020203SearchService.doSearch(inForm);
  }
}
