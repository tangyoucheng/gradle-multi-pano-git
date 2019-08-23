package cn.com.pano.panovr.controller.panovr02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.panovr.form.panovr02.PanoVr0209Form;
import cn.com.pano.panovr.service.panovr02.PanoVr0209EntryService;
import cn.com.pano.panovr.service.panovr02.PanoVr0209InitService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;

/**
 * 编辑热点的大小
 * 
 * @author tangzhenzong
 * 
 */
@Controller
@RequestMapping("/" + PanoConstantsIF.URI_BASE_MEMBER + "/panoVr0209")
public class PanoVr0209Controller extends BaseController {
  @Autowired
  public PanoVr0209InitService vr0209InitService;
  @Autowired
  public PanoVr0209EntryService vr0209EntryService;

  @ModelAttribute
  public PanoVr0209Form setPanoVr0209Form(@ModelAttribute PanoVr0209Form inForm) {
    return inForm;
  }

  /**
   * 初始化
   * 
   * @return
   * @throws Exception
   */
  @RequestMapping("/")
  public String index(PanoVr0209Form inForm) throws Exception {
    vr0209InitService.doInit(inForm);
    return viewJsp("/pano/panovr/panovr02/panovr0209");
  }

  /**
   * 保存热点大小
   * 
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/doSaveHotspotScale")
  public EasyJson<Object> doSaveHotspotScale(PanoVr0209Form inForm) throws Exception {
    String result = vr0209EntryService.doSave(inForm);
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setObj(result);
    return easyJson;
  }

}
