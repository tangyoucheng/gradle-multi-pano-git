package cn.com.pano.panovr.controller.panovr02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.panovr.form.panovr02.PanoVr0204Form;
import cn.com.pano.panovr.service.panovr02.PanoVr0204EntryService;
import cn.com.pano.panovr.service.panovr02.PanoVr0204InitService;
import cn.com.pano.panovr.service.panovr02.PanoVr0204SearchService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;

/**
 * 为导航热点添加全景图
 * 
 * @author ouyangzidou
 * 
 */
@Controller
@RequestMapping("/" + PanoConstantsIF.URI_BASE_MEMBER + "/panoVr0204")
public class PanoVr0204Controller extends BaseController {

  @Autowired
  public PanoVr0204EntryService vr0204EntryService;
  @Autowired
  public PanoVr0204InitService vr0204InitService;
  @Autowired
  public PanoVr0204SearchService vr0204SearchService;

  @ModelAttribute
  public PanoVr0204Form setPanoVr0204Form(@ModelAttribute PanoVr0204Form inForm) {
    return inForm;
  }

  /**
   * 初期表示。
   * 
   * @return 返回结果
   */
  @RequestMapping("/")
  public String index(PanoVr0204Form inForm) throws Exception {
    vr0204InitService.doInit(inForm);
    return viewJsp("/pano/panovr/panovr02/panovr0204");
  }

  /**
   * 分页处理
   * 
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/doPage")
  public EasyJson<Object> doPage(PanoVr0204Form inForm) throws Exception {
    String result = vr0204SearchService.doSearchPanorama(inForm);
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setObj(result);
    return easyJson;
  }

  /**
   * 全景图录入。
   * 
   * @return 返回结果
   */
  @RequestMapping("/doEntry")
  public String doEntry(PanoVr0204Form inForm) throws Exception {
    vr0204EntryService.doEntry(inForm);
    return viewJsp("/pano/panovr/panovr02/panovr0204");
  }

}
