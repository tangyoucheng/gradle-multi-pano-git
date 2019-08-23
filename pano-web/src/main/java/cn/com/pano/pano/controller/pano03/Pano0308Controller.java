package cn.com.pano.pano.controller.pano03;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.form.pano03.Pano0308Form;
import cn.com.pano.pano.service.pano03.Pano0308EntryService;
import cn.com.pano.pano.service.pano03.Pano0308InitService;
import cn.com.pano.pano.service.pano03.Pano0308SearchService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;

/**
 * 展览目录编辑。
 * 
 * @author shiwei
 * 
 */
@Controller
@RequestMapping("/" + PanoConstantsIF.URI_BASE_MEMBER + "/pano0308")
public class Pano0308Controller extends BaseController {
  @Autowired
  public Pano0308InitService pano0308InitService;
  @Autowired
  public Pano0308SearchService pano0308SearchService;
  @Autowired
  public Pano0308EntryService pano0308EntryService;

  @ModelAttribute
  public Pano0308Form setPano0308Form(@ModelAttribute Pano0308Form inForm) {
    return inForm;
  }

  /**
   * 初始化。
   * 
   * @return
   */
  @RequestMapping("/")
  public String index(Pano0308Form inForm) throws Exception {
    pano0308InitService.doInit(inForm);
    return viewJsp("/pano/pano/pano03/pano0308");
  }

  /**
   * 初始化。
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/doSearch")
  public EasyJson<Object> doSearch(Pano0308Form inForm) throws Exception {
    return pano0308SearchService.doSearch(inForm);
  }

  /**
   * 保存缩略图显示状况。
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/doSave")
  public EasyJson<Object> doSave(Pano0308Form inForm) throws Exception {
    return pano0308EntryService.doSave(inForm);
  }

}
