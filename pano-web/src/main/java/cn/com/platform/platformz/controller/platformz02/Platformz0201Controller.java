package cn.com.platform.platformz.controller.platformz02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.platformz.model.platformz02.Platformz0201Model;
import cn.com.platform.platform.model.common.PlatformMember;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platformz.form.platformz02.Platformz0201Form;
import cn.com.platform.platformz.service.platformz02.Platformz0201DeleteService;
import cn.com.platform.platformz.service.platformz02.Platformz0201InitService;
import cn.com.platform.platformz.service.platformz02.Platformz0201SearchService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;


/**
 * 
 * 公司用户管理
 * 
 * @author 代仁宗
 * @date 2019-06-18
 *
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_MEMBER + "/platformz0201")
public class Platformz0201Controller extends BaseController {

  @Autowired
  Platformz0201InitService platformz0201InitService;
  @Autowired
  Platformz0201SearchService platformz0201SearchService;
  @Autowired
  Platformz0201DeleteService platformz0201DeleteService;

  @ModelAttribute
  public Platformz0201Form setPlatformz0201Form(@ModelAttribute Platformz0201Form inForm) {
    return inForm;
  }

  @RequestMapping("/")
  public String doInit(Platformz0201Form inForm) {
    platformz0201InitService.doInit(inForm);
    return viewJsp("/platform/platformz/platformz02/platformz0201");
  }

  /**
   * 检索
   * 
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doSearch")
  public EasyJson<PlatformMember> doSearch(Platformz0201Form inForm) throws SystemException {
    return platformz0201SearchService.doSearch(inForm);
  }

  /**
   * 删除
   * 
   * @throws SystemException
   */
  @ResponseBody
  @PostMapping(path = "/doDelete")
  public EasyJson<Object> doDelete(Platformz0201Form inForm)
      throws SystemException {
    return platformz0201DeleteService.doDelete(inForm);
  }
}
