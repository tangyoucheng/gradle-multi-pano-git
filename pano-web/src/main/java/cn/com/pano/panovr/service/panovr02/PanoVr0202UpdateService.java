package cn.com.pano.panovr.service.panovr02;

import java.text.MessageFormat;
import java.util.HashMap;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Maps;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.utils.FilesServiceUtil;
import cn.com.pano.pano.mapper.common01.PanoMaterial01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanorama01Mapper;
import cn.com.pano.pano.model.common.PanoMaterial;
import cn.com.pano.pano.model.common.PanoPanorama;
import cn.com.pano.panovr.form.panovr02.PanoVr0202Form;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.web.BaseService;

/**
 * 编辑场景的更新操作
 * 
 * @author ouyangzidou
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class PanoVr0202UpdateService extends BaseService {
  @Autowired
  public PanoPanorama01Mapper panoPanorama01Mapper;
  @Autowired
  public PanoMaterial01Mapper panoMaterial01Mapper;

  /**
   * 更新场景信息
   * 
   * @param _inForm
   * @throws Exception
   */
  public void doUpdate(PanoVr0202Form _inForm) throws Exception {
    // 全景图备份
    String _destPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_PANORAMA,
        _inForm.expositionId, _inForm.panoramaId + "/");
    FilesServiceUtil.savePanoramaFileToPublicStorage(_destPath, _inForm.panoramaId,
        PanoConstantsIF.VAL_PUBLIC_DIRECTORY_PANOS_L, PanoConstantsIF.EDIT_SCENE_FLAG);
    FilesServiceUtil.savePanoramaFileToPublicStorage(_destPath, _inForm.panoramaId,
        PanoConstantsIF.VAL_PUBLIC_DIRECTORY_PANOS_R, PanoConstantsIF.EDIT_SCENE_FLAG);
    String returnFilePath = _inForm.panoramaId;
    // 更新场景基本信息
    if (!ObjectUtils.isEmpty(_inForm.panoramaId)) {
      PanoPanorama panoPanorama = panoPanorama01Mapper.selectByPrimaryKey(_inForm.panoramaId);
      if (panoPanorama != null) {
        // 如果用户选择了“首图”勾选框
        if (!ObjectUtils.isEmpty(_inForm.startSceneFlag) && "1".equals(_inForm.startSceneFlag)) {
          HashMap<String, Object> condition = Maps.newHashMap();
          condition.put("expositionId", _inForm.expositionId);
          // 检索全景图首图
          PanoPanorama startPanoPanorama = panoPanorama01Mapper.selectStartScenePanoInfo(condition);
          // 检索当前展览下无全景图首图
          if (startPanoPanorama != null) {
            // 该展览下已有首图,修改其首图标识为NO
            startPanoPanorama.startSceneFlag = FlagStatus.Disable.toString();
            updateAudit(startPanoPanorama);
            panoPanorama01Mapper.updateByPrimaryKey(startPanoPanorama);
          }
          panoPanorama.startSceneFlag = _inForm.startSceneFlag;
        }

        // 为单个场景录入音乐
        if (!ObjectUtils.isEmpty(_inForm.materialIdFromPano0208)
            && !ObjectUtils.isEmpty(_inForm.panoMusicSelect)) {
          // 第一次登记音乐
          panoPanorama.panoramaSoundId = _inForm.materialIdFromPano0208;
        }

        if (ObjectUtils.isEmpty(_inForm.panoMusicSelect)) {
          panoPanorama.panoramaSoundId = "";
        }
        // panoPanorama.panoramaName = _inForm.panoramaName;
        String textName = _inForm.panoramaName.replaceAll("<", "＜");
        textName = textName.replaceAll(">", "＞");
        panoPanorama.panoramaName = textName;
        panoPanorama.panoramaPath = returnFilePath;
        // panoPanorama.notes = _inForm.notes;
        String text = _inForm.notes.replaceAll("<", "＜");
        text = text.replaceAll(">", "＞");
        panoPanorama.notes = text;
        updateAudit(panoPanorama);
        panoPanorama01Mapper.updateByPrimaryKey(panoPanorama);

      }
    }
  }

  /**
   * 取得最新的声音名称
   * 
   * @param _inForm
   * @throws Exception
   */
  public String doRefreshSoundName(PanoVr0202Form _inForm) throws Exception {
    if (!ObjectUtils.isEmpty(_inForm.materialIdFromPano0208)) {
      PanoMaterial panoMaterial =
          panoMaterial01Mapper.selectByPrimaryKey(_inForm.materialIdFromPano0208);
      if (panoMaterial != null && !ObjectUtils.isEmpty(panoMaterial.materialName)) {
        _inForm.panoramaBackGroundSoundName = panoMaterial.materialName;
        return _inForm.panoramaBackGroundSoundName;
      }
    }
    return "";
  }
}
