package cn.com.pano.pano.controller.pano02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.form.pano02.Pano0204Form;
import cn.com.pano.pano.service.pano02.Pano0204EntryService;
import cn.com.pano.pano.service.pano02.Pano0204InitService;
import cn.com.pano.pano.service.pano02.Pano0204SearchService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;

/**
 * 为导航热点添加全景图。
 * 
 * @author ouyangzidou
 * 
 */
@Controller
@RequestMapping("/" + PanoConstantsIF.URI_BASE_MEMBER + "/pano0204")
public class Pano0204Controller extends BaseController {
  @Autowired
  public Pano0204EntryService pano0204EntryService;
  @Autowired
  public Pano0204InitService pano0204InitService;
  @Autowired
  public Pano0204SearchService pano0204SearchService;

  @ModelAttribute
  public Pano0204Form setPano0204Form(@ModelAttribute Pano0204Form inForm) {
    return inForm;
  }

  /**
   * 初期表示。
   * 
   * @return 返回结果
   */
  @RequestMapping("/")
  public String index(Pano0204Form inForm) throws Exception {
    pano0204InitService.doInit(inForm);
    return viewJsp("/pano/pano/pano02/pano0204");
  }

  /**
   * 检索处理。
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/doSearch")
  public EasyJson<Object> doSearchPanorama(Pano0204Form inForm) throws Exception {
    return pano0204SearchService.doSearchPanorama(inForm);
  }

  /**
   * 全景图录入。
   * 
   * @return 返回结果
   */
  @ResponseBody
  @RequestMapping("/doEntry")
  public EasyJson<Object> doEntry(Pano0204Form inForm) throws Exception {
    return pano0204EntryService.doEntry(inForm);
  }

}
