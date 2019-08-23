package cn.com.pano.pano.controller.pano01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.utils.PanoCommonUtil;
import cn.com.pano.pano.form.pano01.Pano0102Form;
import cn.com.pano.pano.service.pano01.Pano0102AdminSearchService;
import cn.com.pano.pano.service.pano01.Pano0102InitService;
import cn.com.pano.pano.service.pano01.Pano0102SearchService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;

/**
 * 展览检索。
 * 
 * @author tangzhenzong
 * 
 */
@Controller
@RequestMapping("/" + PanoConstantsIF.URI_BASE_MEMBER + "/pano0102")
public class Pano0102Controller extends BaseController {
  @Autowired
  public Pano0102SearchService pano0102SearchService;
  @Autowired
  public Pano0102AdminSearchService pano0102AdminSearchService;
  @Autowired
  public Pano0102InitService pano0102InitService;

  @ModelAttribute
  public Pano0102Form setPano0102Form(@ModelAttribute Pano0102Form inForm) {
    return inForm;
  }

  /**
   * 初期显示处理。
   * 
   * @return
   */
  @RequestMapping("/")
  public String index(Pano0102Form inForm) throws Exception {
    pano0102InitService.doInit(inForm);
    // inForm.pageStartRowNo = 0;
    // boolean isTenant = PanoCommonUtil.isLoginUserArchiveUser("tenant_manager");
    // if (isTenant) {
    // pano0102AdminSearchService.doSearchExpositionInfo(inForm);
    // } else {
    // pano0102SearchService.doSearchExpositionInfo(inForm);
    // }
    return viewJsp("/pano/pano/pano01/pano0102");
  }

  /**
   * 检索处理。
   * 
   * @throws Exception 异常的场合
   * @return
   */
  @ResponseBody
  @RequestMapping("/doSearch")
  public EasyJson<Object> doSearch(Pano0102Form inForm) throws Exception {
    // pano0102InitService.doInit(inForm);
    boolean isTenant = PanoCommonUtil.isLoginUserArchiveUser("tenant_manager");
    EasyJson<Object> returnData = new EasyJson<Object>();
    if (isTenant) {
      returnData = pano0102AdminSearchService.doSearchExpositionInfo(inForm);
    } else {
      returnData = pano0102SearchService.doSearchExpositionInfo(inForm);
    }
    return returnData;
  }

}
