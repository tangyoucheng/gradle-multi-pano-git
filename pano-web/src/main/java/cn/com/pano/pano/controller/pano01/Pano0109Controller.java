package cn.com.pano.pano.controller.pano01;

import java.io.File;
import java.text.MessageFormat;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.utils.FileServiceUtil;
import cn.com.pano.pano.form.pano01.Pano0109Form;
import cn.com.pano.pano.service.pano01.Pano0109InitService;
import cn.com.pano.pano.service.pano01.Pano0109UpdateService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.util.FwFileUtils;
import cn.com.platform.web.BaseController;

/**
 * 
 * 展览效果编辑
 * 
 * @author shiwei
 * 
 */
@Controller
@RequestMapping("/" + PanoConstantsIF.URI_BASE_MEMBER + "/pano0109")
public class Pano0109Controller extends BaseController {
  public HttpServletRequest httpServletRequest;
  @Autowired
  public Pano0109UpdateService pano0109UpdateService;
  public Pano0109InitService pano0109InitService;

  @ModelAttribute
  public Pano0109Form setPano0109Form(@ModelAttribute Pano0109Form inForm) {
    return inForm;
  }

  /**
   * 初期显示处理
   * 
   * @return
   * @throws Exception
   */
  @RequestMapping("/")
  public String index(Pano0109Form inForm) throws Exception {
    // 每次取预览图时先删掉一次临时文件
    FileServiceUtil.clearSessionScopeStorage("");
    doDeleteTempFile(inForm);
    pano0109InitService.doInit(inForm);
    return viewJsp("/pano/pano/pano01/pano0109");
  }

  /**
   * 展览信息更新处理
   * 
   * @return
   * @throws Exception
   */
  @RequestMapping("/doUpdate")
  public String doUpdate(Pano0109Form inForm) throws Exception {
    pano0109UpdateService.doUpdateExpositionInfo(inForm);
    return viewJsp("/pano/pano/pano01/pano0109");
  }

  /**
   * 取得临时文件处理
   * 
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/doGetTempFile")
  public EasyJson<Object> doGetTempFile(Pano0109Form inForm) throws Exception {
    String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_PRELOADFILE,
        inForm.expositionId);
    String result =
        FileServiceUtil.copyDirFromSessionStorageToAppServer(inForm.expositionId, _destPath);
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
  public EasyJson<Object> doDeleteTempFile(Pano0109Form inForm) throws Exception {

    String filePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_PRELOADFILE,
        inForm.expositionId);
    FileUtils.deleteDirectory(new File(FwFileUtils.getAbsolutePath(filePath)));
    EasyJson<Object> easyJson = new EasyJson<Object>();
    return easyJson;
  }

  /**
   * 下载视频
   * 
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/doDownload")
  public EasyJson<Object> doDownload(Pano0109Form inForm) throws Exception {
    String filePath = FwFileUtils.getAbsolutePath(inForm.oldmaterialPath);
    // TODO DOWNLOAD
    // FileUtils.downloadFile(filePath, inForm.oldmaterialPath);
    EasyJson<Object> easyJson = new EasyJson<Object>();
    return easyJson;
  }

  /**
   * 取得最新的音乐名称
   * 
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/doRefreshSoundName")
  public EasyJson<Object> doRefreshSoundName(Pano0109Form inForm) throws Exception {
    String result = pano0109UpdateService.doRefreshSoundName(inForm);
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setObj(result);
    return easyJson;
  }
}
