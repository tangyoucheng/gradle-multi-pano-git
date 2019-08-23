package cn.com.pano.pano.controller.pano03;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.platform.web.BaseController;

/**
 * 场景上多边形热点编辑手册
 * 
 * @author yangyuzhen
 * 
 */
@Controller
@RequestMapping("/" + PanoConstantsIF.URI_BASE_MEMBER + "/pano0309")
public class Pano0309Controller extends BaseController {

  /**
   * 初期显示处理
   * 
   * @return
   * @throws Exception
   */
  @RequestMapping("/")
  public String index() throws Exception {
    return viewJsp("/pano/pano/pano03/pano0309");
  }
}
