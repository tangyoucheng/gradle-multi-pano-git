package cn.com.pano.pano.controller.pano01;

import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.form.pano01.Pano0108Form;
import cn.com.pano.pano.service.pano01.Pano0108InitService;
import cn.com.pano.pano.service.pano01.Pano0108SaveService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;

/**
 * 全景图多边形编辑
 * 
 * @author admin
 * 
 */
@Controller
@RequestMapping("/" + PanoConstantsIF.URI_BASE_MEMBER + "/pano0108")
public class Pano0108Controller extends BaseController {

  @Autowired
  public Pano0108InitService pano0108InitService;
  @Autowired
  public Pano0108SaveService pano0108SaveService;

  @ModelAttribute
  public Pano0108Form setPano0108Form(@ModelAttribute Pano0108Form inForm) {
    return inForm;
  }

  /**
   * 初期显示
   * 
   * @return
   */
  @RequestMapping("/")
  public String index(Pano0108Form inForm) throws Exception {
    pano0108InitService.doInit(inForm);
    return viewJsp("/pano/pano/pano01/pano0108");
  }

  /**
   * 保存多边形信息
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/doSave")
  public EasyJson<Object> doSave(Pano0108Form inForm) throws Exception {
    return pano0108SaveService.doSave(inForm);
  }
}
