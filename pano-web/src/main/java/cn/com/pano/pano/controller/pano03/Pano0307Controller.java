package cn.com.pano.pano.controller.pano03;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.form.pano03.Pano0307Form;
import cn.com.pano.pano.service.pano03.Pano0307EntryService;
import cn.com.pano.pano.service.pano03.Pano0307InitService;
import cn.com.pano.pano.service.pano03.Pano0307SearchService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;

/**
 * 场景地图一览。
 * 
 * @author ouyangzidou
 * 
 */
@Controller
@RequestMapping("/" + PanoConstantsIF.URI_BASE_MEMBER + "/pano0307")
public class Pano0307Controller extends BaseController {
  @Autowired
  public Pano0307EntryService pano0307EntryService;
  @Autowired
  public Pano0307InitService pano0307InitService;
  @Autowired
  public Pano0307SearchService pano0307SearchService;

  @ModelAttribute
  public Pano0307Form setPano0307Form(@ModelAttribute Pano0307Form inForm) {
    return inForm;
  }

  /**
   * 初期表示。
   * 
   * @return 返回结果
   */
  @RequestMapping("/")
  public String index(Pano0307Form inForm) throws Exception {
    pano0307InitService.doInit(inForm);
    return viewJsp("/pano/pano/pano03/pano0307");
  }

  /**
   * 登录处理。
   * 
   * @return 返回结果
   */
  @ResponseBody
  @RequestMapping("/doEntry")
  public EasyJson<Object> doEntry(Pano0307Form inForm) throws Exception {
    return pano0307EntryService.doEntry(inForm);
  }

  /**
   * 分页处理
   * 
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/doSearch")
  public EasyJson<Object> doSearch(Pano0307Form inForm) throws Exception {
    return pano0307SearchService.doSearchExpositionMap(inForm);
  }

}
