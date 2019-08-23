package cn.com.pano.panovr.controller.panovr01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.panovr.form.panovr01.PanoVr0105Form;
import cn.com.pano.panovr.service.panovr01.PanoVr0105InitService;
import cn.com.pano.panovr.service.panovr01.PanoVr0105SearchService;
import cn.com.pano.panovr.service.panovr01.PanoVr0105UpdateService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;

/**
 * 场景导航图操作
 * 
 * @author ouyangzidou
 * 
 */
@Controller
@RequestMapping("/" + PanoConstantsIF.URI_BASE_MEMBER + "/panoVr0105")
public class PanoVr0105Controller extends BaseController {
  @Autowired
  public PanoVr0105InitService vr0105InitService;
  @Autowired
  public PanoVr0105SearchService vr0105SearchService;
  @Autowired
  public PanoVr0105UpdateService vr0105UpdateService;

  @ModelAttribute
  public PanoVr0105Form setPanoVr0105Form(@ModelAttribute PanoVr0105Form inForm) {
    return inForm;
  }

  /**
   * 初期显示
   * 
   * @return
   */
  @RequestMapping("/")
  public String index(PanoVr0105Form inForm) throws Exception {
    vr0105InitService.doInit(inForm);
    return viewJsp("/pano/panovr/panovr01/panovr0105");
  }

  /**
   * 检索导航图下热点的信息
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/doCheckHostspotInfo")
  public EasyJson<Object> doCheckHostspotInfo(PanoVr0105Form inForm) throws Exception {
    String result = vr0105SearchService.doCheckHostspotInfo(inForm);
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setObj(result);
    return easyJson;
  }

  /**
   * 从导航图编辑画面返回的处理
   * 
   * @return
   */
  @RequestMapping("/doSearchFromPanoVr0106")
  public String doSearchFromPanoVr0106(PanoVr0105Form inForm) throws Exception {
    vr0105InitService.doInit(inForm);
    return viewJsp("/pano/panovr/panovr01/panovr0105");
  }

  /**
   * 从导航图上热点编辑画面返回的处理
   * 
   * @return
   */
  @RequestMapping("/doSearchFromPanoVr0107")
  public String doSearchFromPanoVr0107(PanoVr0105Form inForm) throws Exception {
    vr0105InitService.doInit(inForm);
    return viewJsp("/pano/panovr/panovr01/panovr0105");
  }

  /**
   * 从导航图上热点添加全景图画面返回的处理
   * 
   * @return
   */
  @RequestMapping("/doSearchFromPanoVr0307")
  public String doSearchFromPanoVr0307(PanoVr0105Form inForm) throws Exception {
    vr0105InitService.doInit(inForm);
    return viewJsp("/pano/panovr/panovr01/panovr0105");
  }

  /**
   * 保存导航图上热点的雷达角度
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/doSaveRadarHeading")
  public EasyJson<Object> doSaveRadarHeading(PanoVr0105Form inForm) throws Exception {
    String result = vr0105UpdateService.doSaveRadarHeading(inForm);
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setObj(result);
    return easyJson;
  }

  /**
   * 保存展览浮动信息层的位置
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/doSaveFlowInfoOffset")
  public EasyJson<Object> doSaveFlowInfoOffset(PanoVr0105Form inForm) throws Exception {
    String result = vr0105UpdateService.doSaveFlowInfoOffset(inForm);
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setObj(result);
    return easyJson;
  }

  /**
   * 保存展览工具条的状态
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/doSaveButtonsBar")
  public EasyJson<Object> doSaveButtonsBar(PanoVr0105Form inForm) throws Exception {
    String result = vr0105UpdateService.doSaveButtonsBar(inForm);
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setObj(result);
    return easyJson;
  }

  /**
   * 检查浮动层是否被保存
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/doCheckFlowInfoLayer")
  public EasyJson<Object> doCheckFlowInfoLayer(PanoVr0105Form inForm) throws Exception {
    String result = vr0105SearchService.doCheckFlowInfoLayer(inForm);
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setObj(result);
    return easyJson;
  }

  /**
   * 检查浮动层是否被保存
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/doDeleteFlowInfo")
  public EasyJson<Object> doDeleteFlowInfo(PanoVr0105Form inForm) throws Exception {
    String result = vr0105UpdateService.doDeleteFlowInfo(inForm);
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setObj(result);
    return easyJson;
  }
}
