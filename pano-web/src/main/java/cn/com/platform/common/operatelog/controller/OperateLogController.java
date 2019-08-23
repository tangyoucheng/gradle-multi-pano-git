package cn.com.platform.common.operatelog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.common.operatelog.form.OperateLogForm;
import cn.com.platform.common.operatelog.service.OperateLogInitService;
import cn.com.platform.common.operatelog.service.OperateLogParameteService;
import cn.com.platform.common.operatelog.service.OperateLogSearchService;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.platform.model.common01.PlatformOperateLog01Model;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;

/**
 * 操作日志共通 。
 * 
 * @author 代仁宗
 * @date 2019-07-09
 *
 */

@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_MEMBER + "/operateLog")
public class OperateLogController extends BaseController {

  @Autowired
  OperateLogInitService operateLogInitService;
  @Autowired
  OperateLogSearchService operateLogSearchService;

  @Autowired
  OperateLogParameteService operateLogParameteService;

  @ModelAttribute
  public OperateLogForm setOperateLogForm(@ModelAttribute OperateLogForm inForm) {
    return inForm;
  }

  @RequestMapping("/")
  public String doInit(OperateLogForm inForm) {
    operateLogInitService.doInit(inForm);
    return viewThymeleaf("/cis/common/operateLog");
  }


  /**
   * 传参。
   * 
   * @throws SystemException Exception
   */
  @ResponseBody
  @PostMapping(path = "/doParameter")
  public EasyJson<PlatformOperateLog01Model> doParameter(OperateLogForm inForm) throws SystemException {
    return operateLogParameteService.doParameter(inForm);
  }

  /**
   * 检索。
   * 
   * @param inForm form
   */
  @ResponseBody
  @RequestMapping(path = "/doSearch")
  public EasyJson<PlatformOperateLog01Model> doSearch(OperateLogForm inForm) throws SystemException {
    return operateLogSearchService.doSearch(inForm);
  }
}
