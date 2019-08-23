package cn.com.pano.pano.controller.pano02;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.common.collect.Maps;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.form.pano02.Pano0205Form;
import cn.com.pano.pano.service.pano02.Pano0205EntryService;
import cn.com.pano.pano.service.pano02.Pano0205InitService;
import cn.com.pano.pano.service.pano02.Pano0205SearchService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;

/**
 * 普通热点添加素材链接。
 * 
 * @author ouyangzidou
 * 
 */
@Controller
@RequestMapping("/" + PanoConstantsIF.URI_BASE_MEMBER + "/pano0205")
public class Pano0205Controller extends BaseController {

  @Autowired
  public Pano0205EntryService pano0205EntryService;
  @Autowired
  public Pano0205InitService pano0205InitService;
  @Autowired
  public Pano0205SearchService pano0205SearchService;

  @ModelAttribute
  public Pano0205Form setPano0205Form(@ModelAttribute Pano0205Form inForm) {
    return inForm;
  }

  /**
   * 初期表示。
   * 
   * @return 返回结果
   */
  @RequestMapping("/")
  public String index(Pano0205Form inForm) throws Exception {
    pano0205InitService.doInit(inForm);
    return viewJsp("/pano/pano/pano02/pano0205");
  }

  /**
   * 登录处理。
   * 
   * @return 返回结果
   */
  @ResponseBody
  @RequestMapping("/doEntry")
  public EasyJson<Object> doEntry(Pano0205Form inForm) throws Exception {
    return pano0205EntryService.doEntry(inForm);
  }

  /**
   * 检索处理。
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/doSearch")
  public EasyJson<Object> doSearch(Pano0205Form inForm) throws Exception {
    return pano0205SearchService.doSearchMaterial(inForm);
  }

  /**
   * 图文列表取得处理
   * 
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/searchTextImgList")
  public EasyJson<Object> searchTextImgList(Pano0205Form inForm) throws Exception {
    pano0205InitService.getTextImgList(inForm);
    Map<String, Object> result = Maps.newHashMap();
    result.put("result", "OK");
    result.put("data", inForm.textImgList);
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setObj(result);
    return easyJson;
  }
}
