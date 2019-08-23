package cn.com.platform.platform.controller.ps99;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.platform.form.ps99.Ps9902Form;
import cn.com.platform.platform.service.ps99.Ps9902InitService;
import cn.com.platform.platform.service.ps99.Ps9902SearchService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;

/**
 * 
 * 会员后台管理
 * 
 * @author 唐友成
 * @date 2018-08-19
 *
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_MEMBER + "/ps99")
public class Ps9902Controller extends BaseController {
  @Autowired
  private Ps9902InitService ps9902InitService;
  @Autowired
  private Ps9902SearchService ps9902SearchService;

  @ModelAttribute
  public Ps9902Form setPs9902Form(@ModelAttribute Ps9902Form inForm) {
    return inForm;
  }

  /**
   * 初期化处理
   * 
   * @param registry
   * @return
   */
  // @RequestMapping("/ps9902/{dispFlag}")
  // public String doInit(Ps9902Form inForm, @PathVariable String dispFlag) {
  @RequestMapping("/ps9902")
  public String doInit(Ps9902Form inForm) {
    ps9902InitService.doInit(inForm);
    return viewJsp("/platform/platform/ps99/ps9902");
  }

  /**
   * 检索处理
   * 
   * @return
   */
  @ResponseBody
  @PostMapping("/ps9902/doSearch")
  public EasyJson<Object> doSearch(Ps9902Form inForm) {
    ps9902SearchService.doSearch(inForm);
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setSuccess(true);
    easyJson.setObj(inForm);
    return easyJson;
  }
}
