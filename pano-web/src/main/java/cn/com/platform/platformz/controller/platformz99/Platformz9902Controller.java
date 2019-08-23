package cn.com.platform.platformz.controller.platformz99;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.web.BaseController;

/**
 * druid 监控
 * 
 * @author 唐友成
 * @date 2018-12-18
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_MEMBER + "/Platformz9902")
public class Platformz9902Controller extends BaseController {

  @GetMapping("/")
  public String index() {
    return redirect("/" + CommonConstantsIF.URI_BASE_MEMBER + "/monitor/druid/index");
  }
}
