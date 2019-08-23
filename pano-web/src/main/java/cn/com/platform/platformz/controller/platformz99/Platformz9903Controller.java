package cn.com.platform.platformz.controller.platformz99;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.web.BaseController;
import cn.com.platform.web.domain.Server;

/**
 * 服务器监控
 * 
 * @author 唐友成
 * @date 2018-12-18
 */
@Controller
@RequestMapping("/" + CommonConstantsIF.URI_BASE_MEMBER + "/Platformz9903")
public class Platformz9903Controller extends BaseController {

  @GetMapping("/")
  public String index(ModelMap mmap) throws SystemException {
    Server server = new Server();
    try {
      server.copyTo();
    } catch (Exception e) {
      throw new SystemException(e);
    }
    mmap.put("server", server);
    return viewThymeleaf("/platform/platformz/Platformz99/Platformz9903");
  }
}
