package cn.com.pano.panovr.controller.panovr02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.panovr.form.panovr02.PanoVr0203Form;
import cn.com.pano.panovr.service.panovr02.PanoVr0203InitService;
import cn.com.pano.panovr.service.panovr02.PanoVr0203UpdateService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;

/**
 * 全景图上热点保存
 * 
 * @author ouyangzidou
 * 
 */
@Controller
@RequestMapping("/" + PanoConstantsIF.URI_BASE_MEMBER + "/panoVr0203")
public class PanoVr0203Controller extends BaseController {
  @Autowired
  public PanoVr0203InitService vr0203InitService;
  @Autowired
  public PanoVr0203UpdateService vr0203UpdateService;

  @ModelAttribute
  public PanoVr0203Form setPanoVr0203Form(@ModelAttribute PanoVr0203Form inForm) {
    return inForm;
  }

  /**
   * 初期显示
   * 
   * @return
   */
  @RequestMapping("/")
  public String index(PanoVr0203Form inForm) throws Exception {
    vr0203InitService.doInit(inForm);
    return viewJsp("/pano/panovr/panovr02/panovr0203");
  }

  /**
   * 保存热点信息
   * 
   * @return
   * @throws Exception
   */
  @RequestMapping("/doSave")
  public String doSave(PanoVr0203Form inForm) throws Exception {
    vr0203UpdateService.doSave(inForm);
    return viewJsp("/pano/panovr/panovr02/panovr0203");
  }

  /**
   * 多边形返回
   * 
   * @return
   * @throws Exception
   */
  @RequestMapping("/doSearchFromPanoVr0108")
  public String doSearchFromPanoVr0108(PanoVr0203Form inForm) throws Exception {
    vr0203InitService.doInit(inForm);
    return viewJsp("/pano/panovr/panovr02/panovr0203");
  }

  /**
   * 得到第二个素材Id和path等(单点热点种类为音频)
   * 
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/doGetSelectedMaterialInfo")
  public EasyJson<Object> doGetSelectedMaterialInfo(PanoVr0203Form inForm) throws Exception {
    String result = vr0203InitService.doGetSelectedMaterialInfo(inForm);
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setObj(result);
    return easyJson;
  }
}
