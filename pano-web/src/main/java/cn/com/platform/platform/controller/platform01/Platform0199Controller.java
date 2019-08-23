package cn.com.platform.platform.controller.platform01;

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
import cn.com.platform.platform.form.platform01.Platform0199Form;
import cn.com.platform.platform.service.platform01.Platform0199DeleteService;
import cn.com.platform.platform.service.platform01.Platform0199InitService;
import cn.com.platform.platform.service.platform01.Platform0199SearchService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;


/**
 * 
 *社区管理管理
 * 
 * @author 代仁宗
 * @date 2019-06-21
 *
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_ADMIN + "/platform0199")
public class Platform0199Controller extends BaseController {

  @Autowired
  Platform0199InitService platform0199InitService;
  
  @Autowired
  Platform0199SearchService platform0199SearchService;
  
  @Autowired
  Platform0199DeleteService platform0199DeleteService;

  @ModelAttribute
  public Platform0199Form setPlatform0199Form(@ModelAttribute Platform0199Form inForm) {
    return inForm;
  }
  /**跳转页面*/
  @RequestMapping("/")
  public String doInit(Platform0199Form inForm) {
    platform0199InitService.doInit(inForm);
    return viewJsp("/platform/platform/platform01/platform0199");
  }

  /**
   * 检索（页面初始化检索数据）
   * 
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doSearch")
  public EasyJson<PlatformDepartment01Model> doSearch(Platform0199Form inForm) throws SystemException {
    return platform0199SearchService.doSearch(inForm);
  }

  /**
   * 删除
   * 
   * @throws SystemException
   */
  @ResponseBody
  @PostMapping(path = "/doDelete")
  public EasyJson<PlatformDepartment> doDelete(HttpServletResponse response, Platform0199Form inForm)
      throws SystemException {
    return platform0199DeleteService.doDelete(inForm);
  }
}
