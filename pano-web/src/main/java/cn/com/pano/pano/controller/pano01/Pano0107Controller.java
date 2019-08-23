package cn.com.pano.pano.controller.pano01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.form.pano01.Pano0107Form;
import cn.com.pano.pano.service.pano01.Pano0107EntryService;
import cn.com.pano.pano.service.pano01.Pano0107InitService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;

/**
 * 场景导航图上热点的操作。
 * 
 * @author ouyangzidou
 * 
 */
@Controller
@RequestMapping("/" + PanoConstantsIF.URI_BASE_MEMBER + "/pano0107")
public class Pano0107Controller extends BaseController {
  @Autowired
  public Pano0107InitService pano0107InitService;
  @Autowired
  public Pano0107EntryService pano0107EntryService;

  @ModelAttribute
  public Pano0107Form setPano0107Form(@ModelAttribute Pano0107Form inForm) {
    return inForm;
  }

  /**
   * 初期显示。
   * 
   * @return
   */
  @RequestMapping("/")
  public String index(Pano0107Form inForm) throws Exception {
    pano0107InitService.doInit(inForm);
    return viewJsp("/pano/pano/pano01/pano0107");
  }

  /**
   * 保存热点信息。
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/doSave")
  public EasyJson<Object> doSave(Pano0107Form inForm) throws Exception {
    return pano0107EntryService.doSave(inForm);
  }

}
