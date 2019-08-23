package cn.com.pano.pano.controller.pano02;

import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.form.pano02.Pano0206Form;
import cn.com.pano.pano.service.pano02.Pano0206DeleteService;
import cn.com.pano.pano.service.pano02.Pano0206InitService;
import cn.com.pano.pano.service.pano02.Pano0206SaveService;
import cn.com.pano.pano.service.pano02.Pano0206SearchService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;

/**
 * 场景一览
 * 
 * @author ouyangzidu
 * 
 */
@Controller
@RequestMapping("/" + PanoConstantsIF.URI_BASE_MEMBER + "/pano0206")
public class Pano0206Controller extends BaseController {
  @Autowired
  public Pano0206InitService pano0206InitService;
  @Autowired
  public Pano0206SearchService pano0206SearchService;
  @Autowired
  public Pano0206DeleteService pano0206DeleteService;
  @Autowired
  public Pano0206SaveService pano0206SaveService;

  @ModelAttribute
  public Pano0206Form setPano0206Form(@ModelAttribute Pano0206Form inForm) {
    return inForm;
  }

  /**
   * 初期显示处理
   * 
   * @return
   */
  @RequestMapping("/")
  public String index(Pano0206Form inForm) throws Exception {
    pano0206InitService.doInit(inForm);
    return viewJsp("/pano/pano/pano02/pano0206");
  }

  /**
   * 保存场景顺序状态
   * 
   * @return
   */
  @RequestMapping("/doSaveSortKey")
  public String doSaveSortKey(Pano0206Form inForm) throws Exception {
    pano0206SaveService.doSave(inForm);
    return viewJsp("/pano/pano/pano02/pano0206");
  }

  /**
   * 检索场景信息
   * 
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/doSearch")
  public EasyJson<Object> doSearch(Pano0206Form inForm) throws Exception {
    String result = pano0206SearchService.searchMaterial(inForm);
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
  public String doDeleteScene(Pano0206Form inForm) throws Exception {
    pano0206DeleteService.doDelete(inForm);
    pano0206InitService.doInit(inForm);
    return viewJsp("/pano/pano/pano02/pano0206");
  }

  /**
   * 从编辑场景画面返回
   * 
   * @return
   * @throws Exception
   */
  @RequestMapping("/doSearchFromPano0202")
  public String doSearchFromPano0202(Pano0206Form inForm) throws Exception {
    pano0206InitService.doInit(inForm);
    return viewJsp("/pano/pano/pano02/pano0206");
  }

  /**
   * 从保存场景序号操作返回
   * 
   * @return
   * @throws Exception
   */
  @RequestMapping("/doSearchFromEditSortKey")
  public String doSearchFromEditSortKey(Pano0206Form inForm) throws Exception {
    pano0206InitService.doInit(inForm);
    return viewJsp("/pano/pano/pano02/pano0206");
  }

}
