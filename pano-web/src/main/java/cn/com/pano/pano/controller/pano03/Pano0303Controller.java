package cn.com.pano.pano.controller.pano03;

import java.io.File;
import java.text.MessageFormat;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.utils.FileServiceUtil;
import cn.com.pano.pano.form.pano03.Pano0303Form;
import cn.com.pano.pano.mapper.common01.PanoMaterial01Mapper;
import cn.com.pano.pano.service.pano03.Pano0303InitService;
import cn.com.pano.pano.service.pano03.Pano0303UpdateService;
import cn.com.platform.framework.util.FwStringUtils;
import cn.com.platform.util.EasyJson;
import cn.com.platform.util.FwFileUtils;
import cn.com.platform.web.BaseController;

/**
 * 素材更新
 * 
 * @author tangzhenzong
 * 
 */
@Controller
@RequestMapping("/" + PanoConstantsIF.URI_BASE_MEMBER + "/pano0303")
public class Pano0303Controller extends BaseController {
  @Autowired
  public Pano0303InitService pano0303InitService;
  @Autowired
  public Pano0303UpdateService pano0303UpdateService;
  @Autowired
  public PanoMaterial01Mapper panoMaterial01Mapper;

  @ModelAttribute
  public Pano0303Form setPano0303Form(@ModelAttribute Pano0303Form inForm) {
    return inForm;
  }

  /**
   * 初期显示处理
   * 
   * @return
   * @throws Exception
   */
  @RequestMapping("/")
  public String index(Pano0303Form inForm) throws Exception {
    // 每次取预览图时先删掉一次临时文件
    FileServiceUtil.clearSessionScopeStorage("");
    doDeleteTempFile(inForm);
    if (ObjectUtils.isEmpty(inForm.materialId)) {
      inForm.materialId = FwStringUtils.getUniqueId();
    }
    pano0303InitService.doInit(inForm);
    return viewJsp("/pano/pano/pano03/pano0303");
  }

  /**
   * 更新处理
   * 
   * @param inForm
   * @throws Exception
   */
  @RequestMapping("/doUpdate")
  public String doUpdate(Pano0303Form inForm) throws Exception {

    pano0303UpdateService.doUpdataMaterial(inForm);

    return viewJsp("/pano/pano/pano03/pano0303");
  }

  /**
   * 删除处理
   * 
   * @param inForm
   * @throws Exception
   */
  @RequestMapping("/doDelete")
  public String doDelete(Pano0303Form inForm) throws Exception {

    pano0303UpdateService.doDeleteMaterial(inForm);
    return viewJsp("/pano/pano/pano03/pano0303");
  }

  /**
   * 取得临时文件处理
   * 
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/doGetTempFile")
  public EasyJson<Object> doGetTempFile(Pano0303Form inForm) throws Exception {

    String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_IMAGE,
        inForm.expositionId, inForm.materialId + "/");
    String result =
        FileServiceUtil.copyDirFromSessionStorageToAppServer(inForm.upload_key, _destPath);
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
  @RequestMapping("/doDeleteTempFile")
  public String doDeleteTempFile(Pano0303Form inForm) throws Exception {

    String filePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_IMAGE,
        inForm.expositionId, inForm.materialId + "/");
    FileUtils.deleteDirectory(new File(FwFileUtils.getAbsolutePath(filePath)));
    return null;
  }

}
