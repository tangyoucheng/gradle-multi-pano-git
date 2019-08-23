package cn.com.platform.common.operatelog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.common.operatelog.form.OperateLogViewForm;
import cn.com.platform.common.operatelog.service.OperateLogViewInitService;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.web.BaseController;

/**
 * 操作日志共通 。
 * 
 * @author 代仁宗
 * @date 2019-07-09
 *
 */

@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_MEMBER + "/operateLogView")
public class OperateLogViewController extends BaseController {

  @Autowired
  OperateLogViewInitService operateLogViewInitService;

  @ModelAttribute
  public OperateLogViewForm setOperateLogViewForm(@ModelAttribute OperateLogViewForm inForm) {
    return inForm;
  }

  /**
   * 查看。
   * 
   * @param inForm form
   * @throws SystemException Exception
   */
  @RequestMapping(method = RequestMethod.GET, value = "/")
  public String doInit(OperateLogViewForm inForm) throws SystemException {
    operateLogViewInitService.doInit(inForm);
    return viewThymeleaf("/cis/common/operateLogView");
  }
}
