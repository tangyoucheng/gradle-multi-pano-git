package cn.com.pano.panovr.controller.panovr01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.panovr.form.panovr01.PanoVr0107Form;
import cn.com.pano.panovr.service.panovr01.PanoVr0107EntryService;
import cn.com.pano.panovr.service.panovr01.PanoVr0107InitService;
import cn.com.platform.web.BaseController;

/**
 * 场景导航图上热点的操作
 * 
 * @author ouyangzidou
 * 
 */
@Controller
@RequestMapping("/" + PanoConstantsIF.URI_BASE_MEMBER + "/panoVr0107")
public class PanoVr0107Controller extends BaseController {
    @Autowired
    public PanoVr0107InitService vr0107InitService;
    @Autowired
    public PanoVr0107EntryService vr0107EntryService;
    
    @ModelAttribute
    public PanoVr0107Form setPanoVr0107Form(@ModelAttribute PanoVr0107Form inForm) {
      return inForm;
    }
    /**
     * 初期显示
     * 
     * @return
     */
    @RequestMapping("/")
    public String index(PanoVr0107Form inForm) throws Exception {
        vr0107InitService.doInit(inForm);
        return viewJsp("/pano/panovr/panovr01/panovr0107");
    }
    
    /**
     * 保存热点信息
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("/doSave")
    public String doSave(PanoVr0107Form inForm) throws Exception {
        vr0107EntryService.doSave(inForm);
        return viewJsp("/pano/panovr/panovr01/panovr0107");
    }
    
}
