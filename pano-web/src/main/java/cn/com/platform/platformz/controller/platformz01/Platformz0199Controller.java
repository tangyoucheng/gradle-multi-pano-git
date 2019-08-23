package cn.com.platform.platformz.controller.platformz01;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.platform.model.common.PlatformDepartment;
import cn.com.platform.platform.model.common01.PlatformDepartment01Model;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platformz.form.platformz01.Platformz0199Form;
import cn.com.platform.platformz.service.platformz01.Platformz0199DeleteService;
import cn.com.platform.platformz.service.platformz01.Platformz0199InitService;
import cn.com.platform.platformz.service.platformz01.Platformz0199SearchService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;


/**
 * 
 * 社区管理管理
 * 
 * @author 代仁宗
 * @date 2019-06-21
 *
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_MEMBER + "/platformz0199")
public class Platformz0199Controller extends BaseController {

  @Autowired
  Platformz0199InitService platformz0199InitService;

  @Autowired
  Platformz0199SearchService platformz0199SearchService;

  @Autowired
  Platformz0199DeleteService platformz0199DeleteService;

  @ModelAttribute
  public Platformz0199Form setPlatformz0199Form(@ModelAttribute Platformz0199Form inForm) {
    return inForm;
  }

  /** 跳转页面 */
  @RequestMapping("/")
  public String doInit(Platformz0199Form inForm) {
    platformz0199InitService.doInit(inForm);
    return viewJsp("/platform/platformz/platformz01/platformz0199");
  }

  /**
   * 检索（页面初始化检索数据）
   * 
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doSearch")
  public EasyJson<PlatformDepartment01Model> doSearch(Platformz0199Form inForm) throws SystemException {
    return platformz0199SearchService.doSearch(inForm);
  }

  /**
   * 删除
   * 
   * @throws SystemException
   */
  @ResponseBody
  @PostMapping(path = "/doDelete")
  public EasyJson<PlatformDepartment> doDelete(HttpServletResponse response, Platformz0199Form inForm)
      throws SystemException {
    return platformz0199DeleteService.doDelete(inForm);
  }
}
