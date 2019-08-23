package cn.com.pano.panovr.service.panovr02;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Maps;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.utils.FileServiceUtil;
import cn.com.pano.pano.mapper.common01.PanoHotspotUrl01Mapper;
import cn.com.pano.pano.mapper.common01.PanoMaterial01Mapper;
import cn.com.pano.pano.model.common.PanoPanorama;
import cn.com.pano.panovr.form.panovr02.PanoVr0206Form;
import cn.com.pano.panovr.mapper.panovr02.PanoVr0206Mapper;
import cn.com.platform.framework.util.FwStringUtils;
import cn.com.platform.web.BaseService;

/**
 * 初期显示
 * 
 * @author ouyangzidou
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class PanoVr0206InitService extends BaseService {

  @Autowired
  public PanoVr0206Mapper vr0206Mapper;
  @Autowired
  public PanoHotspotUrl01Mapper panoHotspotUrl01Mapper;
  @Autowired
  public PanoMaterial01Mapper panoMaterial01Mapper;

  /**
   * 显示场景信息
   * 
   * @param _inForm
   * @throws Exception
   */
  public void doInit(PanoVr0206Form _inForm) throws Exception {
    // 从数据库中检索场景信息
    HashMap<String, Object> condition = Maps.newHashMap();
    condition.put("expositionId", Objects.toString(_inForm.expositionId));
    condition.put("panoramaId", FwStringUtils.getMatchParameter(_inForm.panoramaId));
    condition.put("panoramaName", FwStringUtils.getMatchParameter(_inForm.panoramaName));

    // 场景详细信息取得
    _inForm.panoramaInfo = vr0206Mapper.selectPanoramaInfo(condition);
    if (_inForm.panoramaInfo != null) {

      for (PanoPanorama panoPanorama : _inForm.panoramaInfo) {
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
