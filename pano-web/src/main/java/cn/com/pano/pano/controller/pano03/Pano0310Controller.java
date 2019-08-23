package cn.com.pano.pano.controller.pano03;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.form.pano03.Pano0310Form;
import cn.com.pano.pano.service.pano03.Pano0310InitService;
import cn.com.platform.web.BaseController;


/**
 * 场景上音频热点预览
 * 
 * @author yangyuzhen
 * 
 */
@Controller
@RequestMapping("/" + PanoConstantsIF.URI_BASE_MEMBER + "/pano0310")
public class Pano0310Controller extends BaseController {
  @Autowired
  public Pano0310InitService pano0310InitService;

  @ModelAttribute
  public Pano0310Form setPano0310Form(@ModelAttribute Pano0310Form inForm) {
    return inForm;
  }

  /**
   * 初期显示处理
   * 
   * @return
   * @throws Exception
   */
  @RequestMapping("/")
  public String index(Pano0310Form inForm) throws Exception {
    pano0310InitService.doInit(inForm);
    return viewJsp("/pano/pano/pano03/pano0310");
  }

}
