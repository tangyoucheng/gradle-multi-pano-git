package cn.com.pano.panovr.controller.panovr03;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.panovr.form.panovr03.PanoVr0307Form;
import cn.com.pano.panovr.service.panovr03.PanoVr0307EntryService;
import cn.com.pano.panovr.service.panovr03.PanoVr0307InitService;
import cn.com.pano.panovr.service.panovr03.PanoVr0307SearchService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;

/**
 * 场景地图一览
 * 
 * @author ouyangzidou
 * 
 */
@Controller
@RequestMapping("/" + PanoConstantsIF.URI_BASE_MEMBER + "/panoVr0307")
public class PanoVr0307Controller extends BaseController {

  @Autowired
  public PanoVr0307EntryService vr0307EntryService;
  @Autowired
  public PanoVr0307InitService vr0307InitService;
  @Autowired
  public PanoVr0307SearchService vr0307SearchService;

  @ModelAttribute
  public PanoVr0307Form setPanoVr0307Form(@ModelAttribute PanoVr0307Form inForm) {
    return inForm;
  }

  /**
   * 初期表示。
   * 
   * @return 返回结果
   */
  @RequestMapping("/")
  public String index(PanoVr0307Form inForm) throws Exception {
    inForm.pageStartRowNo = 0;
    vr0307InitService.doInit(inForm);
    return viewJsp("/pano/panovr/panovr03/panovr0307");
  }

  /**
   * 登录处理
   * 
   * @return 返回结果
   */
  @RequestMapping(path = "/doEntry")
  public String doEntry(PanoVr0307Form inForm) throws Exception {
    vr0307EntryService.doEntry(inForm);
    return viewJsp("/pano/panovr/panovr03/panovr0307");
  }

  /**
   * 分页处理
   * 
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doPage")
  public EasyJson<Object> doPage(PanoVr0307Form inForm) throws Exception {
    String result = vr0307SearchService.doSearchExpositionMap(inForm);
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setObj(result);
    return easyJson;
  }

  /**
   * 读取场景地图预览图
   * 
   * @return 返回结果
   */
  @ResponseBody
  @RequestMapping(path = "/doGetPreviewImage")
  public EasyJson<Object> doGetPreviewImage(PanoVr0307Form inForm) throws Exception {
    String result = vr0307SearchService.doGetPreviewImage(inForm);
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setObj(result);
    return easyJson;
  }
}
