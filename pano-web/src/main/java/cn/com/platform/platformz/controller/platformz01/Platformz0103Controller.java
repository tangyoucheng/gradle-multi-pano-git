package cn.com.platform.platformz.controller.platformz01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.platformz.form.platformz01.Platformz0103Form;
import cn.com.platform.platformz.service.platformz01.Platformz0103EntryService;
import cn.com.platform.platformz.service.platformz01.Platformz0103InitService;
import cn.com.platform.platformz.service.platformz01.Platformz0103SearchService;
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
@RequestMapping("/" + CommonConstantsIF.URI_BASE_MEMBER + "/platformz0103")
public class Platformz0103Controller extends BaseController {

  @Autowired
  Platformz0103InitService platformz0103InitService;
  @Autowired
  Platformz0103SearchService platformz0103SearchService;
  @Autowired
  Platformz0103EntryService platformz0103EntryService;

  @ModelAttribute
  public Platformz0103Form setPlatformz0103Form(@ModelAttribute Platformz0103Form inForm) {
    return inForm;
  }

  /**
   * 初期化处理。
   * 
   * @param inForm Platformz0103Form
   * @return
   */
  @RequestMapping("/")
  public String doInit(Platformz0103Form inForm) {
    platformz0103InitService.doInit(inForm);
    return viewJsp("/platform/platformz/platformz01/platformz0103");
  }

  /**
   * 角色菜单检索处理。
   * 
   * @param inForm Platformz0103Form
   * @return
   */
  @ResponseBody
  @RequestMapping(path = "/doSearch")
  public EasyJson<Object> doSearch(Platformz0103Form inForm) {
    platformz0103SearchService.doSearch(inForm);

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
  public EasyJson<Object> doEntry(Platformz0103Form inForm) throws Exception {
    platformz0103EntryService.doEntry(inForm);

    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setSuccess(true);
    easyJson.setMsg("保存成功！");

    return easyJson;
  }

}
