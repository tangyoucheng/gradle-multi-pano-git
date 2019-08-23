package cn.com.platform.platform.controller.platform03;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platform.form.platform03.Platform0399Form;
import cn.com.platform.platform.model.common.PlatformCompany;
import cn.com.platform.platform.service.platform03.Platform0399DeleteService;
import cn.com.platform.platform.service.platform03.Platform0399InitService;
import cn.com.platform.platform.service.platform03.Platform0399SearchService;
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
@RequestMapping("/" + CommonConstantsIF.URI_BASE_ADMIN + "/platform0399")
public class Platform0399Controller extends BaseController {

  @Autowired
  Platform0399InitService platform0399InitService;
  
  @Autowired
  Platform0399SearchService platform0399SearchService;
  
  @Autowired
  Platform0399DeleteService platform0399DeleteService;

  @ModelAttribute
  public Platform0399Form setPlatform0399Form(@ModelAttribute Platform0399Form inForm) {
    return inForm;
  }

  @RequestMapping("/")
  public String doInit(Platform0399Form inForm) {
    platform0399InitService.doInit(inForm);
    return viewJsp("/platform/platform/platform03/platform0399");
  }

  /**
   * 检索（页面初始化检索数据）
   * 
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doSearch")
  public EasyJson<PlatformCompany> doSearch(Platform0399Form inForm) throws SystemException {
    return platform0399SearchService.doSearch(inForm);
  }

  /**
   * 删除
   * 
   * @throws SystemException
   */
  @ResponseBody
  @PostMapping(path = "/doDelete")
  public EasyJson<PlatformCompany> doDelete(HttpServletResponse response, Platform0399Form inForm)
      throws SystemException {
    return platform0399DeleteService.doDelete(inForm);
  }
}
