package cn.com.pano.panovr.controller.panovr01;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.common.collect.Maps;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.panovr.form.panovr01.PanoVr0106Form;
import cn.com.pano.panovr.service.panovr01.PanoVr0106InitService;
import cn.com.pano.panovr.service.panovr01.PanoVr0106UpdateService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;

/**
 * 场景导航图编辑
 * 
 * @author ouyangzidou
 * 
 */
@Controller
@RequestMapping("/" + PanoConstantsIF.URI_BASE_MEMBER + "/panoVr0106")
public class PanoVr0106Controller extends BaseController {
  @Autowired
  public PanoVr0106InitService vr0106InitService;
  @Autowired
  public PanoVr0106UpdateService vr0106UpdateService;

  @ModelAttribute
  public PanoVr0106Form setPanoVr0106Form(@ModelAttribute PanoVr0106Form inForm) {
    return inForm;
  }

  /**
   * 初期显示
   * 
   * @return
   */
  @RequestMapping("/")
  public String index(PanoVr0106Form inForm) throws Exception {
    vr0106InitService.doInit(inForm);
    return viewJsp("/pano/panovr/panovr01/panovr0106");
  }

  /**
   * 从新建导航图画面返回的处理
   * 
   * @return
   */
  @RequestMapping("/doSearchFromIc0304")
  public String doSearchFromIc0304(PanoVr0106Form inForm) throws Exception {
    vr0106InitService.doInit(inForm);
    return viewJsp("/pano/panovr/panovr01/panovr0106");
  }

  /**
   * 从选择导航图画面返回的处理
   * 
   * @return
   */
  @RequestMapping("/doSearchFromIc0305")
  public String doSearchFromIc0305(PanoVr0106Form inForm) throws Exception {
    vr0106InitService.doInit(inForm);
    return viewJsp("/pano/panovr/panovr01/panovr0106");
  }

  /**
   * 从编辑导航图画面返回的处理
   * 
   * @return
   */
  @RequestMapping("/doSearchFromIc0306")
  public String doSearchFromIc0306(PanoVr0106Form inForm) throws Exception {
    vr0106InitService.doInit(inForm);
    return viewJsp("/pano/panovr/panovr01/panovr0106");
  }

  /**
   * 导航图删除处理
   * 
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/doDelete")
  public EasyJson<Object> doDelete(PanoVr0106Form inForm) {
    List<Map<String, Object>> errorList = new LinkedList<Map<String, Object>>();

    String _errorMsg = "";
    try {
      vr0106UpdateService.doDeleteMap(inForm);
    } catch (Exception e) {
      Map<String, Object> errorMessageMap = Maps.newHashMap();
      errorMessageMap.put("name", "");
      errorMessageMap.put("msgsInfo", e.getMessage());
      errorList.add(errorMessageMap);
    }

    Map<String, Object> jsonErrorMessageMap = Maps.newHashMap();
    if (ObjectUtils.isEmpty(_errorMsg)) {
      // top
      jsonErrorMessageMap.put("result", "OK");

    } else {
      // top
      jsonErrorMessageMap.put("result", "NG");
      // top -> [errors]
      jsonErrorMessageMap.put("errors", errorList);
    }
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setObj(jsonErrorMessageMap);
    return easyJson;
  }
}
