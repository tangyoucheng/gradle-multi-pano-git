package cn.com.pano.panovr.controller.panovr02;

import java.io.File;
import java.text.MessageFormat;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.utils.FilesServiceUtil;
import cn.com.pano.panovr.form.panovr02.PanoVr0201Form;
import cn.com.pano.panovr.service.panovr02.PanoVr0201EntryService;
import cn.com.pano.panovr.service.panovr02.PanoVr0201InitService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.util.FwFileUtils;
import cn.com.platform.web.BaseController;

/**
 * 新建全景图首图。
 * 
 * @author ouyangzidu
 * 
 */
@Controller
@RequestMapping("/" + PanoConstantsIF.URI_BASE_MEMBER + "/panoVr0201")
public class PanoVr0201Controller extends BaseController {
  @Autowired
  public PanoVr0201InitService vr0201InitService;
  @Autowired
  public PanoVr0201EntryService vr0201EntryService;

  @ModelAttribute
  public PanoVr0201Form setPanoVr0201Form(@ModelAttribute PanoVr0201Form inForm) {
    return inForm;
  }

  /**
   * 初期显示处理。
   * 
   * @return
   */
  @RequestMapping("/")
  public String index(PanoVr0201Form inForm) throws Exception {
    vr0201InitService.doInit(inForm);
    return viewJsp("/pano/panovr/panovr02/panovr0201");
  }

  /**
   * 全景图录入。
   * 
   * @return
   * @throws Exception
   */
  @RequestMapping("/doEntry")
  public String doEntry(PanoVr0201Form inForm) throws Exception {
    vr0201EntryService.doInsertFirstPano(inForm);
    return viewJsp("/pano/panovr/panovr02/panovr0201");
  }

  /**
   * 取得全景图临时文件处理。
   * 
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/doGetTempFile")
  public EasyJson<Object> doGetTempFile(PanoVr0201Form inForm) throws Exception {
    String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_PANORAMA,
        inForm.expositionId, inForm.panoramaId + "/");
    String result = FilesServiceUtil.saveTempPanoramaFileToAppServer(_destPath, inForm.panoramaId,
        inForm.subFolderName);
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setObj(result);
    return easyJson;
  }

  /**
   * 删除临时文件处理。
   * 
   * @return
   * @throws Exception
   */
  @RequestMapping("/doDeleteTempFile")
  public String doDeleteTempFile(PanoVr0201Form inForm) throws Exception {

    String filePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_PANORAMA,
        inForm.expositionId, inForm.panoramaId + "/" + inForm.subFolderName + "/");
    // 删除全景图
    FileUtils.deleteDirectory(new File(FwFileUtils.getAbsolutePath(filePath)));
    return null;
  }

  /**
   * 取得最新的音乐名称。
   * 
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/doRefreshSoundName")
  public EasyJson<Object> doRefreshSoundName(PanoVr0201Form inForm) throws Exception {
    String result = vr0201EntryService.doRefreshSoundName(inForm);
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setObj(result);
    return easyJson;
  }

}
