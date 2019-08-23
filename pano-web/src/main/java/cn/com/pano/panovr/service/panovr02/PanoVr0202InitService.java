package cn.com.pano.panovr.service.panovr02;

import java.text.MessageFormat;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.utils.FilesServiceUtil;
import cn.com.pano.pano.mapper.common.PanoExpositionMapper;
import cn.com.pano.pano.mapper.common01.PanoMaterial01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanorama01Mapper;
import cn.com.pano.pano.model.common.PanoMaterial;
import cn.com.pano.pano.model.common.PanoPanorama;
import cn.com.pano.panovr.form.panovr02.PanoVr0202Form;
import cn.com.platform.web.BaseService;

/**
 * 场景编辑初期显示处理
 * 
 * @author ouyangzidou
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class PanoVr0202InitService extends BaseService {
  @Autowired
  public PanoPanorama01Mapper panoPanorama01Mapper;
  @Autowired
  public PanoMaterial01Mapper panoMaterial01Mapper;
  @Autowired
  public PanoExpositionMapper panoExpositionMapper;

  /**
   * 场景编辑初期显示
   * 
   * @param _inForm
   * @throws Exception
   */
  public void doInit(PanoVr0202Form _inForm) throws Exception {
    // 取得当前场景信息
    if (!ObjectUtils.isEmpty(_inForm.panoramaId)) {
      PanoPanorama panoPanorama = panoPanorama01Mapper.selectByPrimaryKey(_inForm.panoramaId);
      _inForm.panoramaName = panoPanorama.panoramaName;
      _inForm.notes = panoPanorama.notes;
      _inForm.isStartScene = panoPanorama.startSceneFlag;
      // 当前场景音乐信息
      if (!ObjectUtils.isEmpty(panoPanorama.panoramaSoundId)) {
        PanoMaterial panoMaterial =
            panoMaterial01Mapper.selectByPrimaryKey(panoPanorama.panoramaSoundId);
        if (panoMaterial != null && !ObjectUtils.isEmpty(panoMaterial.materialName)) {
          _inForm.panoramaBackGroundSoundName = panoMaterial.materialName;
        }
      }

      // else {
      // // 显示展览默认声音
      // PanoExposition panoExposition =
      // panoExpositionMapper.selectByPrimaryKey(_inForm.expositionId);
      // if (panoExposition != null && !ObjectUtils.isEmpty(panoExposition.expositionSoundId)) {
      // PanoMaterial panoMaterial =
      // panoMaterial01Mapper.selectByPrimaryKey(panoExposition.expositionSoundId);
      // if (panoMaterial != null && !ObjectUtils.isEmpty(panoMaterial.materialName)) {
      // _inForm.panoramaBackGroundSoundName = panoMaterial.materialName;
      // }
      // }
      // }

      _inForm.panoramaPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
          _inForm.expositionId, panoPanorama.panoramaPath);
      // 全景图文件取得
      String _srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_PANORAMA,
          _inForm.expositionId, _inForm.panoramaId + "/");
      String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
          _inForm.expositionId, _inForm.panoramaId + "/");
      _inForm.showCurrentMapExists =
          FilesServiceUtil.getPanoramaFileFromPublicStorage(_srcPath, _destPath);
    }
  }
}
