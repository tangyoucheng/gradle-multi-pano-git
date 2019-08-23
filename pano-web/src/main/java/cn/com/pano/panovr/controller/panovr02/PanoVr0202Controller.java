package cn.com.pano.panovr.controller.panovr02;

import java.io.File;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.utils.FilesServiceUtil;
import cn.com.pano.panovr.form.panovr02.PanoVr0202Form;
import cn.com.pano.panovr.service.panovr02.PanoVr0202InitService;
import cn.com.pano.panovr.service.panovr02.PanoVr0202UpdateService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.util.FwFileUtils;
import cn.com.platform.web.BaseController;

/**
 * 编辑场景的基本信息
 * 
 * @author ouyangzidu
 * 
 */
@Controller
@RequestMapping("/" + PanoConstantsIF.URI_BASE_MEMBER + "/panoVr0202")
public class PanoVr0202Controller extends BaseController {

  @Autowired
  public PanoVr0202InitService vr0202InitService;
  @Autowired
  public PanoVr0202UpdateService vr0202UpdateService;

  @ModelAttribute
  public PanoVr0202Form setPanoVr0202Form(@ModelAttribute PanoVr0202Form inForm) {
    return inForm;
  }

  /**
   * 初期显示处理
   * 
   * @return
   * @throws Exception
   */
  @RequestMapping("/")
  public String index(PanoVr0202Form inForm) throws Exception {
    // 每次取预览图时先删掉一次临时文件
    FilesServiceUtil.clearSessionScopeStorage("");
    doDeleteTempFile(inForm);
    vr0202InitService.doInit(inForm);
    return viewJsp("/pano/panovr/panovr02/panovr0202");
  }

  /**
   * 登录处理
   * 
   * @return
   * @throws Exception
   */
  @RequestMapping("/doUpdate")
  public String doUpdate(PanoVr0202Form inForm) throws Exception {
    vr0202UpdateService.doUpdate(inForm);
    return viewJsp("/pano/panovr/panovr02/panovr0202");
  }

  /**
   * 取得最新的音乐名称
   * 
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/doRefreshSoundName")
  public EasyJson<Object> doRefreshSoundName(PanoVr0202Form inForm) throws Exception {
    String result = vr0202UpdateService.doRefreshSoundName(inForm);
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setObj(result);
    return easyJson;
  }

  /**
   * 取得全景图临时文件处理
   * 
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/doGetTempFile")
  public EasyJson<Object> doGetTempFile(PanoVr0202Form inForm) throws Exception {
    String _destPath = PanoConstantsIF.VAL_APP_SERVER_W_TEMP_PANORAMA + inForm.panoramaId + "/";
    String result = FilesServiceUtil.saveTempPanoramaFileToAppServer(_destPath, inForm.panoramaId,
        inForm.vr0202subFolderName);
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setObj(result);
    return easyJson;
  }

  /**
   * 删除临时文件处理
   * 
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/doDeleteTempFile")
  public EasyJson<Object> doDeleteTempFile(PanoVr0202Form inForm) throws Exception {
    String filePath = PanoConstantsIF.VAL_APP_SERVER_W_TEMP_PANORAMA + inForm.panoramaId;
    // 删除全景图
    FileUtils.deleteDirectory(new File(FwFileUtils.getAbsolutePath(filePath)));
    EasyJson<Object> easyJson = new EasyJson<Object>();
    return easyJson;
  }

}
