package cn.com.pano.pano.controller.pano02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.form.pano02.Pano0203Form;
import cn.com.pano.pano.service.pano02.Pano0203InitService;
import cn.com.pano.pano.service.pano02.Pano0203UpdateService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;

/**
 * 全景图上热点保存。
 * 
 * @author ouyangzidou
 * 
 */
@Controller
@RequestMapping("/" + PanoConstantsIF.URI_BASE_MEMBER + "/pano0203")
public class Pano0203Controller extends BaseController {
  @Autowired
  public Pano0203InitService pano0203InitService;
  @Autowired
  public Pano0203UpdateService pano0203UpdateService;

  @ModelAttribute
  public Pano0203Form setPano0203Form(@ModelAttribute Pano0203Form inForm) {
    return inForm;
  }

  /**
   * 初期显示。
   * 
   * @return
   */
  @RequestMapping("/")
  public String index(Pano0203Form inForm) throws Exception {
    pano0203InitService.doInit(inForm);
    return viewJsp("/pano/pano/pano02/pano0203");
  }

  /**
   * 保存热点信息。
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/doSave")
  public EasyJson<Object> doSave(Pano0203Form inForm) throws Exception {
    return pano0203UpdateService.doSave(inForm);
  }

  /**
   * 多边形返回。
   * 
   * @return
   */
  @RequestMapping("/doSearchFromPano0108")
  public String doSearchFromPano0108(Pano0203Form inForm) throws Exception {
    pano0203InitService.doInit(inForm);
    return viewJsp("/pano/pano/pano02/pano0203");
  }

  /**
   * 得到第二个素材Id和path等(单点热点种类为音频)。
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/doGetSelectedMaterialInfo")
  public EasyJson<Object> doGetSelectedMaterialInfo(Pano0203Form inForm) throws Exception {
    String result = pano0203InitService.doGetSelectedMaterialInfo(inForm);
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setObj(result);
    return easyJson;
  }
}
