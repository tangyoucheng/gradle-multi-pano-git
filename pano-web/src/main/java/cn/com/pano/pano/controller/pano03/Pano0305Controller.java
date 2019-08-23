package cn.com.pano.pano.controller.pano03;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.form.pano03.Pano0305Form;
import cn.com.pano.pano.form.pano03.Pano0306Form;
import cn.com.pano.pano.service.pano03.Pano0305EntryService;
import cn.com.pano.pano.service.pano03.Pano0305InitService;
import cn.com.pano.pano.service.pano03.Pano0305SearchService;
import cn.com.pano.pano.service.pano03.Pano0305UpdateService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;

/**
 * 场景地图一览。
 * 
 * @author ouyangzidou
 * 
 */
@Controller
@RequestMapping("/" + PanoConstantsIF.URI_BASE_MEMBER + "/pano0305")
public class Pano0305Controller extends BaseController {
  @Autowired
  public Pano0305EntryService pano0305EntryService;
  @Autowired
  public Pano0305InitService pano0305InitService;
  @Autowired
  public Pano0305SearchService pano0305SearchService;
  @Autowired
  public Pano0305UpdateService pano0305UpdateService;

  @ModelAttribute
  public Pano0305Form setPano0305Form(@ModelAttribute Pano0305Form inForm) {
    return inForm;
  }

  /**
   * 初期表示。
   * 
   * @return 返回结果
   */
  @RequestMapping("/")
  public String index(Pano0305Form inForm) throws Exception {
    pano0305InitService.doInit(inForm);
    return viewJsp("/pano/pano/pano03/pano0305");
  }

  /**
   * 登录处理。
   * 
   * @return 返回结果
   */
  @ResponseBody
  @RequestMapping("/doEntry")
  public EasyJson<Object> doEntry(Pano0305Form inForm) throws Exception {
    return  pano0305EntryService.doEntry(inForm);
  }

  /**
   * 检索处理。
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/doSearch")
  public EasyJson<Object> doSearch(Pano0305Form inForm) throws Exception {
    return pano0305SearchService.doSearchExpositionMap(inForm);
  }

  /**
   * 删除处理。
   * 
   * @param inForm Pano0306Form
   */
  @ResponseBody
  @RequestMapping("/doDelete")
  public EasyJson<Object> doDelete(Pano0306Form inForm) throws Exception {
    return pano0305UpdateService.doDeleteExpositionMap(inForm);
  }

}
