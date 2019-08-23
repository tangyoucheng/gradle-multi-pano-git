package cn.com.platform.platform.controller.platform02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.platform.form.platform02.Platform0203Form;
import cn.com.platform.platform.service.platform02.Platform0203EntryService;
import cn.com.platform.platform.service.platform02.Platform0203InitService;
import cn.com.platform.platform.service.platform02.Platform0203SearchService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;


/**
 * 菜单管理。
 * 
 * @author 唐友成
 * @date 2018-08-12
 *
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_ADMIN + "/platform0203")
public class Platform0203Controller extends BaseController {

  @Autowired
  Platform0203InitService platform0203InitService;
  @Autowired
  Platform0203SearchService platform0203SearchService;
  @Autowired
  Platform0203EntryService platform0203EntryService;

  @ModelAttribute
  public Platform0203Form setPlatform0203Form(@ModelAttribute Platform0203Form inForm) {
    return inForm;
  }

  /**
   * 初期化处理。
   * 
   * @param inForm Platform0203Form
   * @return
   */
  @RequestMapping("/")
  public String doInit(Platform0203Form inForm) {
    platform0203InitService.doInit(inForm);
    return viewJsp("/platform/platform/platform02/platform0203");
  }

  /**
   * 角色菜单检索处理。
   * 
   * @param inForm Platform0203Form
   * @return
   */
  @ResponseBody
  @RequestMapping(path = "/doSearch")
  public EasyJson<Object> doSearch(Platform0203Form inForm) {
    platform0203SearchService.doSearch(inForm);

    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setObj(inForm.menuList);;
    return easyJson;
  }

  /**
   * 保存处理。
   * 
   * @return 显示页面
   * @throws Exception 异常的场合
   */
  @ResponseBody
  @RequestMapping(path = "/doEntry")
  public EasyJson<Object> doEntry(Platform0203Form inForm) throws Exception {
    platform0203EntryService.doEntry(inForm);

    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setSuccess(true);
    easyJson.setMsg("保存成功！");

    return easyJson;
  }

}
