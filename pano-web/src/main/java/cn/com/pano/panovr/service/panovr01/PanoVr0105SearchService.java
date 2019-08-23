package cn.com.pano.panovr.service.panovr01;

import java.text.MessageFormat;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.utils.FileServiceUtil;
import cn.com.pano.pano.mapper.common.PanoExpositionMapper;
import cn.com.pano.pano.mapper.common01.PanoExpositionMapHotspot01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanorama01Mapper;
import cn.com.pano.pano.model.common.PanoExposition;
import cn.com.pano.pano.model.common.PanoExpositionMapHotspot;
import cn.com.pano.pano.model.common.PanoPanorama;
import cn.com.pano.panovr.form.panovr01.PanoVr0105Form;
import cn.com.platform.web.BaseService;

/**
 * 检索取得全景图信息
 * 
 * @author ouyangzidou
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class PanoVr0105SearchService extends BaseService {

  @Autowired
  public PanoPanorama01Mapper panoPanorama01Mapper;
  @Autowired
  public PanoExpositionMapHotspot01Mapper panoExpositionMapHotspot01Mapper;
  @Autowired
  public PanoExpositionMapper panoExpositionMapper;

  /**
   * 检查热点链接信息是否存在
   * 
   * @param _inForm
   * @throws Exception
   */
  public String doCheckHostspotInfo(PanoVr0105Form _inForm) throws Exception {
    // 查找被选中的热点信息
    PanoExpositionMapHotspot panoExpositionMapHotspot =
        panoExpositionMapHotspot01Mapper.selectByPrimaryKey(_inForm.selectedHotspotId);
    if (panoExpositionMapHotspot == null) {
      // 当前热点已被其他用户删除！
      return "delete";
    }
    // 热点下链接了全景图的场合
    if (!ObjectUtils.isEmpty(panoExpositionMapHotspot.panoramaId)) {
      PanoPanorama panoPanorama =
          panoPanorama01Mapper.selectByPrimaryKey(panoExpositionMapHotspot.panoramaId);
      _inForm.panoramaPath = panoPanorama.panoramaPath + PanoConstantsIF.PANOS_SHOW_L_XML;
      _inForm.panoramaName = panoPanorama.panoramaName;

      // 全景图文件取得
      String _srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_PANORAMA,
          _inForm.expositionId, panoPanorama.panoramaId + "/");
      String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
          _inForm.expositionId, panoPanorama.panoramaId + "/");
      FileServiceUtil.getPanoramaFileFromPublicStorage(_srcPath, _destPath);

      // 取得雷达角度
      if (!ObjectUtils.isEmpty(panoExpositionMapHotspot.expositionMapHotspotHeading)) {
        _inForm.radarHeading = panoExpositionMapHotspot.expositionMapHotspotHeading;
      } else {
        // 没有雷达角度，默认雷达角度
        _inForm.radarHeading = "0";
      }
      return objectMapper.writeValueAsString(_inForm);
    }
    // 热点下没有链接信息
    return "";
  }

  /**
   * 检查浮动层是否被保存
   * 
   * @param _inForm
   * @throws Exception
   */
  public String doCheckFlowInfoLayer(PanoVr0105Form _inForm) throws Exception {
    PanoExposition panoExposition = panoExpositionMapper.selectByPrimaryKey(_inForm.expositionId);
    if (panoExposition != null && !ObjectUtils.isEmpty(panoExposition.flowInfoFileId)) {
      if (_inForm.flowInfoFileId.equals(panoExposition.flowInfoFileId)) {
        return "true";
      }
    }
    return "false";
  }

}
