package cn.com.pano.pano.controller.pano03;

import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.form.pano03.Pano0302Form;
import cn.com.pano.pano.service.pano03.Pano0302InitService;
import cn.com.pano.pano.service.pano03.Pano0302SearchService;
import cn.com.pano.pano.service.pano03.Pano0302UpdateService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;

/**
 * 素材一览
 * 
 * @author tangzhenzong
 * 
 */
@Controller
@RequestMapping("/" + PanoConstantsIF.URI_BASE_MEMBER + "/pano0302")
public class Pano0302Controller extends BaseController {

  @Autowired
  public Pano0302SearchService pano0302SearchService;
  @Autowired
  public Pano0302InitService pano0302InitService;
  @Autowired
  public Pano0302UpdateService pano0302UpdateService;

  @ModelAttribute
  public Pano0302Form setPano0302Form(@ModelAttribute Pano0302Form inForm) {
    return inForm;
  }

  /**
   * 初期表示。
   * 
   * @return 返回结果
   */
  @RequestMapping("/")
  public String index(Pano0302Form inForm) throws Exception {
    pano0302InitService.doInit(inForm);
    inForm.pageStartRowNo = 0;
    pano0302SearchService.searchMaterial(inForm);
    return viewJsp("/pano/pano/pano03/pano0302");
  }

  /**
   * 检索素材信息
   * 
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/doSearch")
  public EasyJson<Object> doSearch(Pano0302Form inForm) throws Exception {
    inForm.pageStartRowNo = 0;
    String result = pano0302SearchService.searchMaterial(inForm);
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setObj(result);
    return easyJson;
  }

  /**
   * 移动素材归属
   * 
   * @return
   * @throws Exception
   */
  @RequestMapping("/doMoveMaterials")
  public String doMoveMaterials(Pano0302Form inForm) throws Exception {
    pano0302UpdateService.doMoveMaterials(inForm);
    return viewJsp("/pano/pano/pano03/pano0302");
  }

  /**
   * 分页处理
   * 
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/doPage")
  public EasyJson<Object> doPage(Pano0302Form inForm) throws Exception {
    String result = pano0302SearchService.searchMaterial(inForm);
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setObj(result);
    return easyJson;
  }

}
