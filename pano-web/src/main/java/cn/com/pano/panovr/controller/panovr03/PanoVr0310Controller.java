package cn.com.pano.panovr.controller.panovr03;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.panovr.form.panovr03.PanoVr0310Form;
import cn.com.pano.panovr.service.panovr03.PanoVr0310InitService;
import cn.com.platform.web.BaseController;

/**
 * 场景上音频热点预览
 * 
 * @author yangyuzhen
 * 
 */
@Controller
@RequestMapping("/" + PanoConstantsIF.URI_BASE_MEMBER + "/panoVr0310")
public class PanoVr0310Controller extends BaseController {
  @Autowired
  public PanoVr0310InitService vr0310InitService;

  @ModelAttribute
  public PanoVr0310Form setPPanoVr0310Form(@ModelAttribute PanoVr0310Form inForm) {
    return inForm;
  }

  /**
   * 初期显示处理
   * 
   * @return
   * @throws Exception
   */
  @RequestMapping("/")
  public String index(PanoVr0310Form inForm) throws Exception {
    vr0310InitService.doInit(inForm);
    return viewJsp("/pano/panovr/panovr03/panovr0310");
  }

}
