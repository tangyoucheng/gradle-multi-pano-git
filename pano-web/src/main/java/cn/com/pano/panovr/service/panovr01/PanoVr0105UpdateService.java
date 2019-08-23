package cn.com.pano.panovr.service.panovr01;

import java.util.HashMap;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Maps;
import cn.com.pano.pano.mapper.common.PanoExpositionMapper;
import cn.com.pano.pano.mapper.common01.PanoExpositionMapHotspot01Mapper;
import cn.com.pano.pano.model.common.PanoExposition;
import cn.com.pano.pano.model.common.PanoExpositionMapHotspot;
import cn.com.pano.panovr.form.panovr01.PanoVr0105Form;
import cn.com.platform.web.BaseService;

/**
 * 展览浮动信息的相关保存操作
 * 
 * @author ouyangzidou
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class PanoVr0105UpdateService extends BaseService {
  @Autowired
  public PanoExpositionMapper panoExpositionMapper;
  @Autowired
  public PanoExpositionMapHotspot01Mapper panoExpositionMapHotspot01Mapper;

  /**
   * 保存导航图上热点的雷达角度
   * 
   * @param _inForm
   * @throws Exception
   */
  public String doSaveRadarHeading(PanoVr0105Form _inForm) throws Exception {
    // 如果雷达角度存在
    if (!ObjectUtils.isEmpty(_inForm.radarHeading)
        && !ObjectUtils.isEmpty(_inForm.selectedHotspotId)) {

      PanoExpositionMapHotspot panoExpositionMapHotspot =
          panoExpositionMapHotspot01Mapper.selectByPrimaryKey(_inForm.selectedHotspotId);

      if (panoExpositionMapHotspot != null) {
        panoExpositionMapHotspot.expositionMapHotspotHeading = _inForm.radarHeading;
        updateAudit(panoExpositionMapHotspot);
        panoExpositionMapHotspot01Mapper.updateByPrimaryKey(panoExpositionMapHotspot);

        // 读取最新的数据库中的导航图上热点信息
        HashMap<String, Object> conditions = Maps.newHashMap();
        conditions.put("expositionMapId", _inForm.expositionMapId);
        if (conditions.size() > 0) {
          _inForm.miniMapSpotInfoList =
              panoExpositionMapHotspot01Mapper.selectMapHotspotInfo(conditions);
          if (_inForm.miniMapSpotInfoList != null) {
            for (PanoExpositionMapHotspot expositionMapHotspot : _inForm.miniMapSpotInfoList) {
              if (_inForm.panoramaId.equals(expositionMapHotspot.panoramaId)) {
                _inForm.selectedHotspotId = expositionMapHotspot.expositionMapHotspotId;
              }
            }
          }
          _inForm.miniMapSpotInfoListJson =
              objectMapper.writeValueAsString(_inForm.miniMapSpotInfoList);
        }

        return objectMapper.writeValueAsString(_inForm);
      }

    }
    return "false";
  }

  /**
   * 保存展览浮动信息层的位置
   * 
   * @param _inForm
   * @throws Exception
   */
  public String doSaveFlowInfoOffset(PanoVr0105Form _inForm) throws Exception {
    // 如果浮动信息及其坐标存在
    if (!ObjectUtils.isEmpty(_inForm.flowInfoFileId) && !ObjectUtils.isEmpty(_inForm.flowInfoFileX)
        && !StringUtils.isEmpty(_inForm.flowInfoFileY)) {

      PanoExposition panoExposition = panoExpositionMapper.selectByPrimaryKey(_inForm.expositionId);

      if (panoExposition != null) {
        panoExposition.flowInfoFileId = _inForm.flowInfoFileId;
        panoExposition.flowInfoFileX = _inForm.flowInfoFileX;
        panoExposition.flowInfoFileY = _inForm.flowInfoFileY;
        panoExposition.flowInfoFileScale = _inForm.flowInfoFileScale;
        updateAudit(panoExposition);
        panoExpositionMapper.updateByPrimaryKey(panoExposition);
        return "true";
      }
    }
    return "false";
  }

  /**
   * 保存展览工具条的状态
   * 
   * @param _inForm
   * @return
   * @throws Exception
   */
  public String doSaveButtonsBar(PanoVr0105Form _inForm) throws Exception {
    PanoExposition panoExposition = panoExpositionMapper.selectByPrimaryKey(_inForm.expositionId);
    panoExposition.expositionSelectedButtons = _inForm.selectedButtons;
    panoExpositionMapper.updateByPrimaryKey(panoExposition);
    return "true";
  }

  /**
   * 删除浮动信息
   * 
   * @param _inForm
   * @return
   * @throws Exception
   */
  public String doDeleteFlowInfo(PanoVr0105Form _inForm) throws Exception {

    PanoExposition panoExposition = panoExpositionMapper.selectByPrimaryKey(_inForm.expositionId);
    if (panoExposition != null) {
      panoExposition.flowInfoFileId = "";
      panoExposition.flowInfoFileX = "";
      panoExposition.flowInfoFileY = "";
      panoExposition.flowInfoFileScale = "";
      updateAudit(panoExposition);
      panoExpositionMapper.updateByPrimaryKey(panoExposition);
      return "true";
    }
    return "false";
  }
}
