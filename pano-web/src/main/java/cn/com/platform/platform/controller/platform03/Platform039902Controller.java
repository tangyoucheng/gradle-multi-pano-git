package cn.com.platform.platform.controller.platform03;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.common.annotation.LogOperate;
import cn.com.platform.framework.code.BusinessType;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platform.form.platform03.Platform039902Form;
import cn.com.platform.platform.model.common.PlatformCompany;
import cn.com.platform.platform.service.platform03.Platform039902InitService;
import cn.com.platform.platform.service.platform03.Platform039902UpdateService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;

/**
 * 公司编辑。
 * 
 * @author 代仁宗
 * @date 2019-06-17
 *
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_ADMIN + "/platform039902")
public class Platform039902Controller extends BaseController {

  @Autowired
  Platform039902InitService platform039902InitService;
  @Autowired
  Platform039902UpdateService platform039902UpdateService;

  @ModelAttribute
  public Platform039902Form setPlatform039902Form(@ModelAttribute Platform039902Form inForm) {
    return inForm;
  }

  /**
   * 编辑页面初始化。
   * 
   * @throws SystemException 异常的场合
   */
  @RequestMapping(method = RequestMethod.GET, value = "/")
  public String index(Platform039902Form inForm) throws SystemException {
    platform039902InitService.doInit(inForm);
    return viewJsp("/platform/platform/platform03/platform039902");
  }

  /**
   * 更新。
   * 
   * @throws SystemException 异常的场合
   */
  @ResponseBody
  @PostMapping(path = "/doUpdate")
  @LogOperate(moduleId = "cn.com.platform.platform.controller.platform03.platform0399", moduleTitle = "公司管理",
      businessType = BusinessType.UPDATE)
  public EasyJson<PlatformCompany> doUpdate(Platform039902Form inForm) throws SystemException {
    // 输出日志用的业务数据主键
    inForm.logRecordId = inForm.getCompanyId();
    return platform039902UpdateService.doUpdate(inForm);
  }

}
