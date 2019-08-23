package cn.com.pano.pano.controller.pano01;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.form.pano01.Pano0104Form;
import cn.com.pano.pano.service.pano01.Pano0104DeleteService;
import cn.com.pano.pano.service.pano01.Pano0104EntryService;
import cn.com.pano.pano.service.pano01.Pano0104InitService;
import cn.com.pano.pano.service.pano01.Pano0104SearchService;
import cn.com.pano.pano.service.pano01.Pano0104UpdateService;
import cn.com.pano.pano.service.pano01.Pano0110CreateStaticTourService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;

/**
 * 全景图初期显示。
 * 
 * @author ouyangzidou
 * 
 */
@Controller
@RequestMapping("/" + PanoConstantsIF.URI_BASE_MEMBER + "/pano0104")
public class Pano0104Controller extends BaseController {
  @Autowired
  public Pano0104InitService pano0104InitService;
  @Autowired
  public Pano0104SearchService pano0104SearchService;
  @Autowired
  public Pano0104EntryService pano0104EntryService;
  @Autowired
  public Pano0104DeleteService pano0104DeleteService;
  @Autowired
  public Pano0104UpdateService pano0104UpdateService;
  @Autowired
  public Pano0110CreateStaticTourService pano0110CreateStaticTourService;

  @ModelAttribute
  public Pano0104Form setPano0104Form(@ModelAttribute Pano0104Form inForm) {
    return inForm;
  }

  /**
   * 初期显示。
   * 
   * @return
   */
  @RequestMapping("/")
  public String index(Pano0104Form inForm) throws Exception {
    pano0104InitService.doInit(inForm);
    return viewJsp("/pano/pano/pano01/pano0104");
  }

  /**
   * 场景检索处理。
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/doSearchPano")
  public EasyJson<Object> doSearchPano(Pano0104Form inForm) throws Exception {
    return pano0104SearchService.doSearchPano(inForm);
  }

  /**
   * 场景元素检索处理。
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/doSearchPanoElementInfo")
  public EasyJson<Object> doSearchPanoElementInfo(Pano0104Form inForm) throws Exception {
    return pano0104SearchService.doSearchPanoElementInfo(inForm);
  }

  /**
   * 保存排序。
   * 
   * @param inForm Pano0104Form
   * @return
   */
  @ResponseBody
  @PostMapping(path = "/doUpdateOrder")
  public EasyJson<Object> doUpdateOrder(Pano0104Form inForm) {
    return pano0104UpdateService.doUpdatePanoOrder(inForm);
  }

  /**
   * 删除处理。
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/doDeletePano")
  public EasyJson<Object> doDeletePano(Pano0104Form inForm) throws Exception {
    return pano0104DeleteService.doDeletePano(inForm);
  }


  /**
   * 设定场景初始化视角。
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/doSaveLookatPoint")
  public EasyJson<Object> doSaveLookatPoint(Pano0104Form inForm) throws Exception {
    return pano0104EntryService.doSaveLookatPoint(inForm);
  }


  // 变更前的代码 ########
  /**
   * 从热点编辑画面返回的检索处理。
   * 
   * @return
   * @throws Exception
   */
  // @RequestMapping("/doSearchFromPano0203")
  // public String doSearchFromPano0203(Pano0104Form inForm) throws Exception {
  // pano0104SearchService.doShowHostspotInfoFromEdit(inForm);
  // return viewJsp("/pano/pano/pano01/pano0104");
  // }

  /**
   * 从新建场景返回。
   * 
   * @return
   * @throws Exception
   */
  // @RequestMapping("/doSearchFromPano0201")
  // public String doSearchFromPano0201(Pano0104Form inForm) throws Exception {
  // pano0104SearchService.doShowHostspotInfoFromEdit(inForm);
  // return viewJsp("/pano/pano/pano01/pano0104");
  // }

  /**
   * 从素材一览画面返回。
   * 
   * @return
   * @throws Exception
   */
  // @RequestMapping("/doSearchFromPano0302")
  // public String doSearchFromPano0302(Pano0104Form inForm) throws Exception {
  // pano0104SearchService.doShowHostspotInfoFromEdit(inForm);
  // return viewJsp("/pano/pano/pano01/pano0104");
  // }

  /**
   * 从场景一览画面返回。
   * 
   * @return
   * @throws Exception
   */
  // @RequestMapping("/doSearchFromPano0206")
  // public String doSearchFromPano0206(Pano0104Form inForm) throws Exception {
  // pano0104SearchService.doShowHostspotInfoFromEdit(inForm);
  // return viewJsp("/pano/pano/pano01/pano0104");
  // }

  /**
   * 从热点大小调整的画面返回。
   * 
   * @return
   * @throws Exception
   */
  // @RequestMapping("/doSearchFromPano0209")
  // public String doSearchFromPano0209(Pano0104Form inForm) throws Exception {
  // pano0104SearchService.doShowHostspotInfoFromEdit(inForm);
  // return viewJsp("/pano/pano/pano01/pano0104");
  // }

  /**
   * 检查热点链接信息是否存在。
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/doCheckHostspotInfo")
  public EasyJson<Object> doCheckHostspotInfo(Pano0104Form inForm) throws Exception {
    String result = pano0104SearchService.doCheckHostspotInfo(inForm);
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setObj(result);
    return easyJson;
  }

  /**
   * 显示热点链接的全景图信息。
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/doShowHostspotInfo")
  public EasyJson<Object> doShowHostspotInfo(Pano0104Form inForm) throws Exception {
    return pano0104SearchService.doShowHostspotInfo(inForm);
  }

  /**
   * 显示小地图上热点链接的全景图信息。
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/doShowMiniMapHotspotInfo")
  public EasyJson<Object> doShowMiniMapHotspotInfo(Pano0104Form inForm) throws Exception {
    return pano0104SearchService.doShowMiniMapHotspotInfo(inForm);
  }

  /**
   * 点击下拉框后跳转到对应场景。
   * 
   * @return
   */
  // @ResponseBody
  // @RequestMapping("/doShowFromPanoSelected")
  // public EasyJson<Object> doShowFromPanoSelected(Pano0104Form inForm) throws Exception {
  // String result = pano0104SearchService.doShowFromPanoSelected(inForm);
  // EasyJson<Object> easyJson = new EasyJson<Object>();
  // easyJson.setObj(result);
  // return easyJson;
  // }

  /**
   * 从热点添加全景图的画面返回的检索处理。
   * 
   * @return
   * @throws Exception
   */
  // @RequestMapping("/doSearchFromPano0204")
  // public String doSearchFromPano0204(Pano0104Form inForm) throws Exception {
  // pano0104SearchService.doShowHostspotInfoFromEdit(inForm);
  // return viewJsp("/pano/pano/pano01/pano0104");
  // }

  /**
   * 从地图操作画面返回的检索处理。
   * 
   * @return
   * @throws Exception
   */
  // @RequestMapping("/doSearchFromPano0105")
  // public String doSearchFromPano0105(Pano0104Form inForm) throws Exception {
  // pano0104SearchService.doShowHostspotInfoFromEdit(inForm);
  // return viewJsp("/pano/pano/pano01/pano0104");
  // }

  /**
   * 从新建素材的画面返回。
   * 
   * @return
   * @throws Exception
   */
  // @RequestMapping("/doSearchFromPano0301")
  // public String doSearchFromPano0301(Pano0104Form inForm) throws Exception {
  // pano0104SearchService.doShowHostspotInfoFromEdit(inForm);
  // return viewJsp("/pano/pano/pano01/pano0104");
  // }

  /**
   * 从热点添加素材平面图的画面返回的检索处理。
   * 
   * @return
   * @throws Exception
   */
  // @RequestMapping("/doSearchFromPano0205")
  // public String doSearchFromPano0205(Pano0104Form inForm) throws Exception {
  // pano0104SearchService.doShowHostspotInfoFromEdit(inForm);
  // return viewJsp("/pano/pano/pano01/pano0104");
  // }

  /**
   * 从编辑场景画面返回。
   * 
   * @return
   * @throws Exception
   */
  // @RequestMapping("/doSearchFromPano0202")
  // public String doSearchFromPano0202(Pano0104Form inForm) throws Exception {
  // pano0104SearchService.doShowHostspotInfoFromEdit(inForm);
  // return viewJsp("/pano/pano/pano01/pano0104");
  // }

  /**
   * 多边形返回。
   * 
   * @return
   * @throws Exception
   */
  // @RequestMapping("/doSearchFromPano0108")
  // public String doSearchFromPano0108(Pano0104Form inForm) throws Exception {
  // pano0104SearchService.doShowHostspotInfoFromEdit(inForm);
  // return viewJsp("/pano/pano/pano01/pano0104");
  // }

  /**
   * 取得当前热点的所有图文信息。
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/doGetTextImgData")
  public String doGetTextImgData(Pano0104Form inForm) throws Exception {
    String js = "";
    if (ObjectUtils.isNotEmpty(inForm.expositionId)) {
      js = pano0110CreateStaticTourService.createJsStr(inForm.expositionId, " pano0104");
    }
    // EasyJson<Object> easyJson = new EasyJson<Object>();
    // easyJson.setObj(js);
    return js;
  }
}
