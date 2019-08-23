package cn.com.pano.panovr.controller.panovr01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.panovr.form.panovr01.PanoVr0108Form;
import cn.com.pano.panovr.service.panovr01.PanoVr0108InitService;
import cn.com.pano.panovr.service.panovr01.PanoVr0108SaveService;
import cn.com.platform.web.BaseController;

/**
 * 全景图多边形编辑
 * 
 * @author admin
 * 
 */
@Controller
@RequestMapping("/" + PanoConstantsIF.URI_BASE_MEMBER + "/panoVr0108")
public class PanoVr0108Controller extends BaseController {
    
    @Autowired
    public PanoVr0108InitService vr0108InitService;
    @Autowired
    public PanoVr0108SaveService vr0108SaveService;
    
    @ModelAttribute
    public PanoVr0108Form setPanoVr0108Form(@ModelAttribute PanoVr0108Form inForm) {
      return inForm;
    }
    /**
     * 初期显示
     * 
     * @return
     */
    @RequestMapping("/")
    public String index(PanoVr0108Form inForm) throws Exception {
        vr0108InitService.doInit(inForm);
        return viewJsp("/pano/panovr/panovr01/panovr0108");
    }
    
    /**
     * 保存多边形信息
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("/doSave")
    public String doSave(PanoVr0108Form inForm) throws Exception {
        vr0108SaveService.doSave(inForm);
        return viewJsp("/pano/panovr/panovr01/panovr0108");
    }
}
