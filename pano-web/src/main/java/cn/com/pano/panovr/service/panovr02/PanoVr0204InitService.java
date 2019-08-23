package cn.com.pano.panovr.service.panovr02;

import java.text.MessageFormat;
import java.util.HashMap;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Maps;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.utils.FileServiceUtil;
import cn.com.pano.pano.mapper.common.PanoExpositionMapper;
import cn.com.pano.pano.mapper.common01.PanoPanorama01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanoramaHotspot01Mapper;
import cn.com.pano.pano.model.common.PanoExposition;
import cn.com.pano.pano.model.common.PanoPanorama;
import cn.com.pano.pano.model.common.PanoPanoramaHotspot;
import cn.com.pano.panovr.form.panovr02.PanoVr0204Form;
import cn.com.pano.panovr.mapper.panovr02.PanoVr0204Mapper;
import cn.com.platform.web.BaseService;

/**
 * 为导航热点添加全景图的初期显示
 * 
 * @author ouyangzidou
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class PanoVr0204InitService extends BaseService {

  @Autowired
  public PanoPanorama01Mapper panoPanorama01Mapper;
  @Autowired
  public PanoVr0204Mapper vr0204Mapper;
  @Autowired
  public PanoPanoramaHotspot01Mapper panoPanoramaHotspot01Mapper;
  @Autowired
  public PanoExpositionMapper panoExpositionMapper;

  /**
   * 为导航热点添加全景图的初期显示
   * 
   * @param _inForm
   * @throws Exception
   */
  public void doInit(PanoVr0204Form _inForm) throws Exception {
    HashMap<String, Object> conditions = Maps.newHashMap();
    conditions.put("expositionId", _inForm.expositionId);
    conditions.put("panoramaId", _inForm.currentPanoramaId);
    // 如果该热点上已有提示信息，则显示提示信息
    PanoPanoramaHotspot panoPanoramaHotspot =
        panoPanoramaHotspot01Mapper.selectByPrimaryKey(_inForm.selectedHotspotId);

    PanoExposition panoExposition = panoExpositionMapper.selectByPrimaryKey(_inForm.expositionId);
    if (!ObjectUtils.isEmpty(panoPanoramaHotspot.hotspotTooltip)) {
      _inForm.hotspotTooltip = panoPanoramaHotspot.hotspotTooltip;
    } else {
      if (!ObjectUtils.isEmpty(panoExposition.expoGoSceneTooltip)) {
        _inForm.hotspotTooltip = panoExposition.expoGoSceneTooltip;
        _inForm.expoGoSceneTooltip = panoExposition.expoGoSceneTooltip;
      }
    }
    int resultCount = vr0204Mapper.selectPanoInfoCount(conditions);
    checkCount(_inForm, resultCount);
    _inForm.panoramaList = vr0204Mapper.selectPanoInfo(conditions, _inForm.pageStartRowNo);
    if (_inForm.panoramaList != null) {
      for (PanoPanorama panoPanorama : _inForm.panoramaList) {
        panoPanorama.panoramaPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
            _inForm.expositionId, panoPanorama.panoramaPath + PanoConstantsIF.PANOS_SHOW_L_XML);
        // 全景图文件取得
        String _srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_PANORAMA,
            _inForm.expositionId, panoPanorama.panoramaId + "/");
        String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
            _inForm.expositionId, panoPanorama.panoramaId + "/");
        FileServiceUtil.getPanoramaFileFromPublicStorage(_srcPath, _destPath);

      }
    }

  }

}
