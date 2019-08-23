package cn.com.pano.pano.service.pano02;

import java.io.File;
import java.text.MessageFormat;
import java.util.HashMap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Maps;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.utils.FileServiceUtil;
import cn.com.pano.pano.form.pano02.Pano0202Form;
import cn.com.pano.pano.mapper.common01.PanoMaterial01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanorama01Mapper;
import cn.com.pano.pano.model.common.PanoMaterial;
import cn.com.pano.pano.model.common.PanoPanorama;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.util.EasyJson;
import cn.com.platform.util.FwFileUtils;
import cn.com.platform.web.BaseService;

/**
 * 编辑场景的更新操作。
 * 
 * @author ouyangzidou
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0202UpdateService extends BaseService {
  @Autowired
  public PanoPanorama01Mapper panoPanorama01Mapper;
  @Autowired
  public PanoMaterial01Mapper panoMaterial01Mapper;

  /**
   * 更新场景信息。
   * 
   * @param inForm Pano0202Form
   * @throws Exception 异常的场合
   */
  public EasyJson<Object> doUpdate(Pano0202Form inForm) throws Exception {
    // 获取APP服务器侧文件目录。
    String srcAppRelativePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
        UserSessionUtils.getSessionId(), "compare_file/pano0202/" + inForm.getExpositionId(),
        inForm.getPanoramaId() + "/");
    // 全景的storage路径
    String destPublicPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_PANORAMA,
        inForm.expositionId, inForm.panoramaId + "/");
    // String srcAppPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
    // inForm.expositionId, inForm.panoramaId + "/");

    File srcAppDirectory = new File(FwFileUtils.getAbsolutePath(srcAppRelativePath));
    if (srcAppDirectory.exists() && srcAppDirectory.listFiles().length > 0) {
      // 拷贝全景到publicStorage服务器，并做成全景相关文件
      FileServiceUtil.savePanoramaFileToPublicStorage(
          srcAppRelativePath + PanoConstantsIF.VAL_PUBLIC_DIRECTORY_PANOS_L + "/", destPublicPath);
      // 再拷贝完整的全景到APP服务器
      FileUtils.copyDirectory(new File(destPublicPath), srcAppDirectory, true);
    }

    // 更新场景基本信息
    if (ObjectUtils.isNotEmpty(inForm.panoramaId)) {
      PanoPanorama panoPanorama = panoPanorama01Mapper.selectByPrimaryKey(inForm.panoramaId);
      if (panoPanorama != null) {
        // 如果用户选择了“首图”勾选框
        if (ObjectUtils.isNotEmpty(inForm.startSceneFlag) && "1".equals(inForm.startSceneFlag)) {
          HashMap<String, Object> condition = Maps.newHashMap();
          condition.put("expositionId", inForm.expositionId);
          // 检索全景图首图
          PanoPanorama startPanoPanorama = panoPanorama01Mapper.selectStartScenePanoInfo(condition);
          // 检索当前展览下无全景图首图
          if (startPanoPanorama != null) {
            // 该展览下已有首图,修改其首图标识为NO
            startPanoPanorama.startSceneFlag = FlagStatus.Disable.toString();
            updateAudit(startPanoPanorama);
            panoPanorama01Mapper.updateByPrimaryKey(startPanoPanorama);
          }
          panoPanorama.startSceneFlag = inForm.startSceneFlag;
        }

        // 为单个场景录入音乐
        if (ObjectUtils.isNotEmpty(inForm.materialIdFromPano0208)
            && ObjectUtils.isNotEmpty(inForm.panoMusicSelect)) {
          // 第一次登记音乐
          panoPanorama.panoramaSoundId = inForm.materialIdFromPano0208;
        }

        if (ObjectUtils.isEmpty(inForm.panoMusicSelect)) {
          panoPanorama.panoramaSoundId = "";
        }
        // panoPanorama.panoramaName = _inForm.panoramaName;
        String textName = inForm.panoramaName.replaceAll("<", "＜");
        textName = textName.replaceAll(">", "＞");
        panoPanorama.panoramaName = textName;
        panoPanorama.panoramaPath =
            MessageFormat.format("{0}/panoramas/{1}/", inForm.expositionId, inForm.panoramaId);
        // panoPanorama.notes = _inForm.notes;
        String text = inForm.notes.replaceAll("<", "＜");
        text = text.replaceAll(">", "＞");
        panoPanorama.notes = text;
        panoPanorama.thumbNote = inForm.thumbNote;
        updateAudit(panoPanorama);;
        panoPanorama01Mapper.updateByPrimaryKey(panoPanorama);

      }
    }

    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setMsg("登录成功！");
    return easyJson;
  }

  /**
   * 取得最新的声音名称。
   * 
   * @param inForm Pano0202Form
   * @throws Exception 异常的场合
   */
  public String doRefreshSoundName(Pano0202Form inForm) throws Exception {
    if (!ObjectUtils.isEmpty(inForm.materialIdFromPano0208)) {
      PanoMaterial panoMaterial =
          panoMaterial01Mapper.selectByPrimaryKey(inForm.materialIdFromPano0208);
      if (panoMaterial != null && !ObjectUtils.isEmpty(panoMaterial.materialName)) {
        inForm.panoramaBackGroundSoundName = panoMaterial.materialName;
        return inForm.panoramaBackGroundSoundName;
      }
    }
    return "";
  }
}
