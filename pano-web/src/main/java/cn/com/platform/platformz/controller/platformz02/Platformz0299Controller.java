package cn.com.platform.platformz.controller.platformz02;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.platform.model.common.PlatformCompany;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platformz.form.platformz02.Platformz0299Form;
import cn.com.platform.platformz.service.platformz02.Platformz0299DeleteService;
import cn.com.platform.platformz.service.platformz02.Platformz0299InitService;
import cn.com.platform.platformz.service.platformz02.Platformz0299SearchService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;


/**
 * 
 * 公司管理
 * 
 * @author 代仁宗
 * @date 2018-08-10
 *
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_MEMBER + "/platformz0299")
public class Platformz0299Controller extends BaseController {

  @Autowired
  Platformz0299InitService platformz0299InitService;
  
  @Autowired
  Platformz0299SearchService platformz0299SearchService;
  
  @Autowired
  Platformz0299DeleteService platformz0299DeleteService;

  @ModelAttribute
  public Platformz0299Form setPlatformz0299Form(@ModelAttribute Platformz0299Form inForm) {
    return inForm;
  }

  @RequestMapping("/")
  public String doInit(Platformz0299Form inForm) {
    platformz0299InitService.doInit(inForm);
    return viewJsp("/platform/platformz/platformz02/platformz0299");
  }

  /**
   * 检索（页面初始化检索数据）
   * 
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doSearch")
  public EasyJson<PlatformCompany> doSearch(Platformz0299Form inForm) throws SystemException {
    return platformz0299SearchService.doSearch(inForm);
  }

  /**
   * 删除
   * 
   * @throws SystemException
   */
  @ResponseBody
  @PostMapping(path = "/doDelete")
  public EasyJson<PlatformCompany> doDelete(HttpServletResponse response, Platformz0299Form inForm)
      throws SystemException {
    return platformz0299DeleteService.doDelete(inForm);
  }
}
