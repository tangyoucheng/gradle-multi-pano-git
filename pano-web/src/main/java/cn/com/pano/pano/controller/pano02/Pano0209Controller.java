package cn.com.pano.pano.controller.pano02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.form.pano02.Pano0209Form;
import cn.com.pano.pano.service.pano02.Pano0209EntryService;
import cn.com.pano.pano.service.pano02.Pano0209InitService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;

/**
 * 编辑热点的大小。
 * 
 * @author tangzhenzong
 * 
 */
@Controller
@RequestMapping("/" + PanoConstantsIF.URI_BASE_MEMBER + "/pano0209")
public class Pano0209Controller extends BaseController {
  @Autowired
  public Pano0209InitService pano0209InitService;
  @Autowired
  public Pano0209EntryService pano0209EntryService;

  @ModelAttribute
  public Pano0209Form setPano0209Form(@ModelAttribute Pano0209Form inForm) {
    return inForm;
  }

  /**
   * 初始化。
   * 
   * @return
   */
  @RequestMapping("/")
  public String index(Pano0209Form inForm) throws Exception {
    pano0209InitService.doInit(inForm);
    return viewJsp("/pano/pano/pano02/pano0209");
  }

  // /**
  // * 当前热点的信息
  // *
  // * @return
  // * @throws Exception
  // */
  // @RequestMapping("/")
  // public String doShowHotspotInfo() throws Exception {
  // String result = pano0209InitService.doShowHotspotInfo( inForm);
  // ResponseUtil.write(result);
  // return null;
  // }

  /**
   * 保存热点大小。
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/doSaveHotspotScale")
  public EasyJson<Object> doSaveHotspotScale(Pano0209Form inForm) throws Exception {
    return pano0209EntryService.doSave(inForm);
  }

}
