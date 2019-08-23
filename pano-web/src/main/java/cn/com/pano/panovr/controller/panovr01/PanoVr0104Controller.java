package cn.com.pano.panovr.controller.panovr01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.panovr.form.panovr01.PanoVr0104Form;
import cn.com.pano.panovr.service.panovr01.PanoVr0104DeleteService;
import cn.com.pano.panovr.service.panovr01.PanoVr0104EntryService;
import cn.com.pano.panovr.service.panovr01.PanoVr0104InitService;
import cn.com.pano.panovr.service.panovr01.PanoVr0104SearchService;
import cn.com.pano.panovr.service.panovr01.PanoVr0104UpdateService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;

/**
 * 全景图初期显示
 * 
 * @author ouyangzidou
 * 
 */
@Controller
@RequestMapping("/" + PanoConstantsIF.URI_BASE_MEMBER + "/panoVr0104")
public class PanoVr0104Controller extends BaseController {
  @Autowired
  public PanoVr0104InitService vr0104InitService;
  @Autowired
  public PanoVr0104SearchService vr0104SearchService;
  @Autowired
  public PanoVr0104EntryService vr0104EntryService;
  @Autowired
  public PanoVr0104DeleteService vr0104DeleteService;
  @Autowired
  public PanoVr0104UpdateService vr0104UpdateService;

  @ModelAttribute
  public PanoVr0104Form setPanoVr0104Form(@ModelAttribute PanoVr0104Form inForm) {
    return inForm;
  }

  /**
   * 初期显示
   * 
   * @return
   */
  @RequestMapping("/")
  public String index(PanoVr0104Form inForm) throws Exception {
    vr0104InitService.doInit(inForm);
    return viewJsp("/pano/panovr/panovr01/panovr0104");
  }

  /**
   * 从热点编辑画面返回的检索处理
   * 
   * @return
   * @throws Exception
   */
  @RequestMapping("/doSearchFromPanoVr0203")
  public String doSearchFromPanoVr0203(PanoVr0104Form inForm) throws Exception {
    vr0104SearchService.doShowHostspotInfoFromEdit(inForm);
    return viewJsp("/pano/panovr/panovr01/panovr0104");
  }

  /**
   * 从新建场景返回
   * 
   * @return
   * @throws Exception
   */
  @RequestMapping("/doSearchFromPanoVr0201")
  public String doSearchFromPanoVr0201(PanoVr0104Form inForm) throws Exception {
    vr0104SearchService.doShowHostspotInfoFromEdit(inForm);
    return viewJsp("/pano/panovr/panovr01/panovr0104");
  }

  /**
   * 从素材一览画面返回
   * 
   * @return
   * @throws Exception
   */
  @RequestMapping("/doSearchFromIc0302")
  public String doSearchFromIc0302(PanoVr0104Form inForm) throws Exception {
    vr0104SearchService.doShowHostspotInfoFromEdit(inForm);
    return viewJsp("/pano/panovr/panovr01/panovr0104");
  }

  /**
   * 从场景一览画面返回
   * 
   * @return
   * @throws Exception
   */
  @RequestMapping("/doSearchFromPanoVr0206")
  public String doSearchFromPanoVr0206(PanoVr0104Form inForm) throws Exception {
    vr0104SearchService.doShowHostspotInfoFromEdit(inForm);
    return viewJsp("/pano/panovr/panovr01/panovr0104");
  }

  /**
   * 从热点大小调整的画面返回
   * 
   * @return
   * @throws Exception
   */
  @RequestMapping("/doSearchFromPanoVr0209")
  public String doSearchFromPanoVr0209(PanoVr0104Form inForm) throws Exception {
    vr0104SearchService.doShowHostspotInfoFromEdit(inForm);
    return viewJsp("/pano/panovr/panovr01/panovr0104");
  }

  /**
   * 检查热点链接信息是否存在
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/doCheckHostspotInfo")
  public EasyJson<Object> doCheckHostspotInfo(PanoVr0104Form inForm) throws Exception {
    String result = vr0104SearchService.doCheckHostspotInfo(inForm);
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setObj(result);
    return easyJson;
  }

  /**
   * 显示热点链接的全景图信息
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/doShowHostspotInfo")
  public EasyJson<Object> doShowHostspotInfo(PanoVr0104Form inForm) throws Exception {
    String result = vr0104SearchService.doShowHostspotInfo(inForm);
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setObj(result);
    return easyJson;
  }

  /**
   * 显示小地图上热点链接的全景图信息
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/doShowMiniMapHotspotInfo")
  public EasyJson<Object> doShowMiniMapHotspotInfo(PanoVr0104Form inForm) throws Exception {
    String result = vr0104SearchService.doShowMiniMapHotspotInfo(inForm);
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setObj(result);
    return easyJson;
  }

  /**
   * 点击下拉框后跳转到对应场景
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/doShowFromPanoSelected")
  public EasyJson<Object> doShowFromPanoSelected(PanoVr0104Form inForm) throws Exception {
    String result = vr0104SearchService.doShowFromPanoSelected(inForm);
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setObj(result);
    return easyJson;
  }

  /**
   * 从热点添加全景图的画面返回的检索处理
   * 
   * @return
   * @throws Exception
   */
  @RequestMapping("/doSearchFromPanoVr0204")
  public String doSearchFromPanoVr0204(PanoVr0104Form inForm) throws Exception {
    vr0104SearchService.doShowHostspotInfoFromEdit(inForm);
    return viewJsp("/pano/panovr/panovr01/panovr0104");
  }

  /**
   * 从地图操作画面返回的检索处理
   * 
   * @return
   * @throws Exception
   */
  @RequestMapping("/doSearchFromPanoVr0105")
  public String doSearchFromPanoVr0105(PanoVr0104Form inForm) throws Exception {
    vr0104SearchService.doShowHostspotInfoFromEdit(inForm);
    return viewJsp("/pano/panovr/panovr01/panovr0104");
  }

  /**
   * 从新建素材的画面返回
   * 
   * @return
   * @throws Exception
   */
  @RequestMapping("/doSearchFromIc0301")
  public String doSearchFromIc0301(PanoVr0104Form inForm) throws Exception {
    vr0104SearchService.doShowHostspotInfoFromEdit(inForm);
    return viewJsp("/pano/panovr/panovr01/panovr0104");
  }

  /**
   * 从热点添加素材平面图的画面返回的检索处理
   * 
   * @return
   * @throws Exception
   */
  @RequestMapping("/doSearchFromIc0205")
  public String doSearchFromIc0205(PanoVr0104Form inForm) throws Exception {
    vr0104SearchService.doShowHostspotInfoFromEdit(inForm);
    return viewJsp("/pano/panovr/panovr01/panovr0104");
  }

  /**
   * 从编辑场景画面返回
   * 
   * @return
   * @throws Exception
   */
  @RequestMapping("/doSearchFromPanoVr0202")
  public String doSearchFromPanoVr0202(PanoVr0104Form inForm) throws Exception {
    vr0104SearchService.doShowHostspotInfoFromEdit(inForm);
    return viewJsp("/pano/panovr/panovr01/panovr0104");
  }

  /**
   * 设定场景初始化视角
   * 
   * @return
   */
  @RequestMapping("/doSaveLookatPoint")
  public String doSaveLookatPoint(PanoVr0104Form inForm) throws Exception {
    vr0104EntryService.doSaveLookatPoint(inForm);
    return viewJsp("/pano/panovr/panovr01/panovr0104");
  }

  /**
   * 删除处理
   * 
   * @return
   * @throws Exception
   */
  @RequestMapping("/doDeleteMap")
  public String doDeleteMap(PanoVr0104Form inForm) throws Exception {
    vr0104DeleteService.doDelete(inForm);
    vr0104InitService.doInit(inForm);
    return viewJsp("/pano/panovr/panovr01/panovr0104");
  }

  /**
   * 多边形返回
   * 
   * @return
   * @throws Exception
   */
  @RequestMapping("/doSearchFromPanoVr0108")
  public String doSearchFromPanoVr0108(PanoVr0104Form inForm) throws Exception {
    vr0104SearchService.doShowHostspotInfoFromEdit(inForm);
    return viewJsp("/pano/panovr/panovr01/panovr0104");
  }

  /**
   * 设置首场景
   * 
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/setFirstSence")
  public EasyJson<Object> setFirstSence(PanoVr0104Form inForm) throws Exception {
    String result = vr0104UpdateService.updateFirstSence(inForm);
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setObj(result);
    return easyJson;
  }

  /**
   * 返回 pano0102时取得pageStartRowNo
   * 
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/doGetPageStartRowNo")
  public EasyJson<Object> doGetPageStartRowNo(PanoVr0104Form inForm) throws Exception {
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setObj(vr0104InitService.pageStartRowNoFromPanoVr0104);
    return easyJson;
  }
}
