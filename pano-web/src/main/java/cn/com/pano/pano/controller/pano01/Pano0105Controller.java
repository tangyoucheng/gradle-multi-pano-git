package cn.com.pano.pano.controller.pano01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.form.pano01.Pano0105Form;
import cn.com.pano.pano.service.pano01.Pano0105InitService;
import cn.com.pano.pano.service.pano01.Pano0105SearchService;
import cn.com.pano.pano.service.pano01.Pano0105UpdateService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;

/**
 * 场景导航图操作。
 * 
 * @author ouyangzidou
 * 
 */
@Controller
@RequestMapping("/" + PanoConstantsIF.URI_BASE_MEMBER + "/pano0105")
public class Pano0105Controller extends BaseController {
  @Autowired
  public Pano0105InitService pano0105InitService;
  @Autowired
  public Pano0105SearchService pano0105SearchService;
  @Autowired
  public Pano0105UpdateService pano0105UpdateService;

  @ModelAttribute
  public Pano0105Form setPano0105Form(@ModelAttribute Pano0105Form inForm) {
    return inForm;
  }

  /**
   * 初期显示。
   * 
   * @return
   */
  @RequestMapping("/")
  public String index(Pano0105Form inForm) throws Exception {
    pano0105InitService.doInit(inForm);
    return viewJsp("/pano/pano/pano01/pano0105");
  }

  /**
   * 检索导航图下热点的信息。
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/doCheckHostspotInfo")
  public EasyJson<Object> doCheckHostspotInfo(Pano0105Form inForm) throws Exception {
    return pano0105SearchService.doCheckHostspotInfo(inForm);
  }

  /**
   * 从导航图编辑画面返回的处理。
   * 
   * @return
   */
  // @RequestMapping("/doSearchFromPano0106")
  // public String doSearchFromPano0106(Pano0105Form inForm) throws Exception {
  // pano0105InitService.doInit(inForm);
  // return viewJsp("/pano/pano/pano01/pano0105");
  // }

  /**
   * 从导航图上热点编辑画面返回的处理。
   * 
   * @return
   */
  // @RequestMapping("/doSearchFromPano0107")
  // public String doSearchFromPano0107(Pano0105Form inForm) throws Exception {
  // pano0105InitService.doInit(inForm);
  // return viewJsp("/pano/pano/pano01/pano0105");
  // }

  /**
   * 从导航图上热点添加全景图画面返回的处理。
   * 
   * @return
   */
  // @RequestMapping("/doSearchFromPano0307")
  // public String doSearchFromPano0307(Pano0105Form inForm) throws Exception {
  // pano0105InitService.doInit(inForm);
  // return viewJsp("/pano/pano/pano01/pano0105");
  // }

  /**
   * 保存导航图上热点的雷达角度。
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/doSaveRadarHeading")
  public EasyJson<Object> doSaveRadarHeading(Pano0105Form inForm) throws Exception {
    return pano0105UpdateService.doSaveRadarHeading(inForm);
  }

  /**
   * 保存展览浮动信息层的位置。
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/doSaveFlowInfoOffset")
  public EasyJson<Object> doSaveFlowInfoOffset(Pano0105Form inForm) throws Exception {
    return pano0105UpdateService.doSaveFlowInfoOffset(inForm);
  }

  /**
   * 保存展览工具条的状态。
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/doSaveButtonsBar")
  public EasyJson<Object> doSaveButtonsBar(Pano0105Form inForm) throws Exception {
    return pano0105UpdateService.doSaveButtonsBar(inForm);
  }

  /**
   * 检查浮动层是否被保存。
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/doCheckFlowInfoLayer")
  public EasyJson<Object> doCheckFlowInfoLayer(Pano0105Form inForm) throws Exception {
    String result = pano0105SearchService.doCheckFlowInfoLayer(inForm);
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setObj(result);
    return easyJson;
  }

  /**
   * 检查浮动层是否被保存。
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/doDeleteFlowInfo")
  public EasyJson<Object> doDeleteFlowInfo(Pano0105Form inForm) throws Exception {
    return pano0105UpdateService.doDeleteFlowInfo(inForm);
  }
}
