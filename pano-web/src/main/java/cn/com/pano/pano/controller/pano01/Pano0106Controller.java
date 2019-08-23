package cn.com.pano.pano.controller.pano01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.form.pano01.Pano0106Form;
import cn.com.pano.pano.service.pano01.Pano0106InitService;
import cn.com.pano.pano.service.pano01.Pano0106UpdateService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;

/**
 * 场景导航图编辑。
 * 
 * @author ouyangzidou
 * 
 */
@Controller
@RequestMapping("/" + PanoConstantsIF.URI_BASE_MEMBER + "/pano0106")
public class Pano0106Controller extends BaseController {
  @Autowired
  public Pano0106InitService pano0106InitService;
  @Autowired
  public Pano0106UpdateService pano0106UpdateService;

  @ModelAttribute
  public Pano0106Form setPano0106Form(@ModelAttribute Pano0106Form inForm) {
    return inForm;
  }

  /**
   * 初期显示。
   * 
   * @return
   */
  @RequestMapping("/")
  public String index(Pano0106Form inForm) throws Exception {
    pano0106InitService.doInit(inForm);
    return viewJsp("/pano/pano/pano01/pano0106");
  }

  /**
   * 从新建导航图画面返回的处理
   * 
   * @return
   */
  // @RequestMapping("/doSearchFromPano0304")
  // public String doSearchFromPano0304(Pano0106Form inForm) throws Exception {
  // pano0106InitService.doInit(inForm);
  // return viewJsp("/pano/pano/pano01/pano0106");
  // }

  /**
   * 从选择导航图画面返回的处理
   * 
   * @return
   */
  // @RequestMapping("/doSearchFromPano0305")
  // public String doSearchFromPano0305(Pano0106Form inForm) throws Exception {
  // pano0106InitService.doInit(inForm);
  // return viewJsp("/pano/pano/pano01/pano0106");
  // }

  /**
   * 从编辑导航图画面返回的处理
   * 
   * @return
   */
  // @RequestMapping("/doSearchFromPano0306")
  // public String doSearchFromPano0306(Pano0106Form inForm) throws Exception {
  // pano0106InitService.doInit(inForm);
  // return viewJsp("/pano/pano/pano01/pano0106");
  // }

  /**
   * 导航图删除处理。
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/doDelete")
  public EasyJson<Object> doDelete(Pano0106Form inForm) throws Exception {
    return pano0106UpdateService.doDeleteMap(inForm);
  }
}
