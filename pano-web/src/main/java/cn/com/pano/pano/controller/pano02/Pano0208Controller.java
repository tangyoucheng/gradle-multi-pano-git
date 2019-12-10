package cn.com.pano.pano.controller.pano02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.form.pano02.Pano0208Form;
import cn.com.pano.pano.service.pano02.Pano0208DeleteService;
import cn.com.pano.pano.service.pano02.Pano0208EntryService;
import cn.com.pano.pano.service.pano02.Pano0208InitService;
import cn.com.pano.pano.service.pano02.Pano0208SearchService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;

/**
 * 普通热点添加素材链接。
 * 
 * @author ouyangzidou
 * 
 */
@Controller
@RequestMapping("/" + PanoConstantsIF.URI_BASE_MEMBER + "/pano0208")
public class Pano0208Controller extends BaseController {

  @Autowired
  public Pano0208InitService pano0208InitService;
  @Autowired
  public Pano0208SearchService pano0208SearchService;
  @Autowired
  public Pano0208DeleteService pano0208DeleteService;
  @Autowired
  public Pano0208EntryService pano0208EntryService;

  @ModelAttribute
  public Pano0208Form setPano0208Form(@ModelAttribute Pano0208Form inForm) {
    return inForm;
  }

  /**
   * 初期表示。
   * 
   * @return 返回结果
   */
  @RequestMapping("/")
  public String index(Pano0208Form inForm) throws Exception {
    pano0208InitService.doInit(inForm);
    return viewJsp("/pano/pano/pano02/pano0208");
  }

  /**
   * 检索素材信息。
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/doSearch")
  public EasyJson<Object> doSearch(Pano0208Form inForm) throws Exception {
    return pano0208SearchService.searchMaterial(inForm);
  }

  /**
   * 删除处理。
   * 
   * @param inForm Pano0306Form
   */
  @ResponseBody
  @RequestMapping("/doDelete")
  public EasyJson<Object> doDelete(Pano0208Form inForm) throws Exception {
    return pano0208DeleteService.doDeleteMaterial(inForm);
  }

  /**
   * 得到素材Id和path等(单点热点种类为音频)。
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/doGetSelectedMaterialInfo")
  public EasyJson<Object> doGetSelectedMaterialInfo(Pano0208Form inForm) throws Exception {
    String result = pano0208SearchService.doGetSelectedMaterialInfo(inForm);
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setObj(result);
    return easyJson;
  }

}
