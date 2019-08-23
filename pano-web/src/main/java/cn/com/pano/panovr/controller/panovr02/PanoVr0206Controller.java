package cn.com.pano.panovr.controller.panovr02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.panovr.form.panovr02.PanoVr0206Form;
import cn.com.pano.panovr.service.panovr02.PanoVr0206DeleteService;
import cn.com.pano.panovr.service.panovr02.PanoVr0206InitService;
import cn.com.pano.panovr.service.panovr02.PanoVr0206SaveService;
import cn.com.pano.panovr.service.panovr02.PanoVr0206SearchService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;

/**
 * 场景一览
 * 
 * @author ouyangzidu
 * 
 */
@Controller
@RequestMapping("/" + PanoConstantsIF.URI_BASE_MEMBER + "/panoVr0206")
public class PanoVr0206Controller extends BaseController {
  @Autowired
  public PanoVr0206InitService vr0206InitService;
  @Autowired
  public PanoVr0206SearchService vr0206SearchService;
  @Autowired
  public PanoVr0206DeleteService vr0206DeleteService;
  @Autowired
  public PanoVr0206SaveService vr0206SaveService;

  @ModelAttribute
  public PanoVr0206Form setPanoVr0206Form(@ModelAttribute PanoVr0206Form inForm) {
    return inForm;
  }

  /**
   * 初期显示处理
   * 
   * @return
   */
  @RequestMapping("/")
  public String index(PanoVr0206Form inForm) throws Exception {
    vr0206InitService.doInit(inForm);
    return viewJsp("/pano/panovr/panovr02/panovr0206");
  }

  /**
   * 保存场景顺序状态
   * 
   * @return
   */
  @RequestMapping("/doSaveSortKey")
  public String doSaveSortKey(PanoVr0206Form inForm) throws Exception {
    vr0206SaveService.doSave(inForm);
    return viewJsp("/pano/panovr/panovr02/panovr0206");
  }

  /**
   * 检索场景信息
   * 
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/doSearch")
  public EasyJson<Object> doSearch(PanoVr0206Form inForm) throws Exception {
    String result = vr0206SearchService.searchMaterial(inForm);
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setObj(result);
    return easyJson;
  }

  /**
   * 删除处理
   * 
   * @return
   * @throws Exception
   */
  @RequestMapping("/doDeleteScene")
  public String doDeleteScene(PanoVr0206Form inForm) throws Exception {
    vr0206DeleteService.doDelete(inForm);
    vr0206InitService.doInit(inForm);
    return viewJsp("/pano/panovr/panovr02/panovr0206");
  }

  /**
   * 从编辑场景画面返回
   * 
   * @return
   * @throws Exception
   */
  @RequestMapping("/doSearchFromPanoVr0202")
  public String doSearchFromPanoVr0202(PanoVr0206Form inForm) throws Exception {
    vr0206InitService.doInit(inForm);
    return viewJsp("/pano/panovr/panovr02/panovr0206");
  }

  /**
   * 从保存场景序号操作返回
   * 
   * @return
   * @throws Exception
   */
  @RequestMapping("/doSearchFromEditSortKey")
  public String doSearchFromEditSortKey(PanoVr0206Form inForm) throws Exception {
    vr0206InitService.doInit(inForm);
    return viewJsp("/pano/panovr/panovr02/panovr0206");
  }

}
