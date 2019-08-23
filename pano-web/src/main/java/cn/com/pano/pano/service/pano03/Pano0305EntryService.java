package cn.com.pano.pano.service.pano03;

import java.util.List;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.form.pano03.Pano0305Form;
import cn.com.pano.pano.mapper.common01.PanoExpositionMap01Mapper;
import cn.com.pano.pano.mapper.common01.PanoExpositionMapHotspot01Mapper;
import cn.com.pano.pano.mapper.pano03.Pano0305Mapper;
import cn.com.pano.pano.model.common.PanoExpositionMap;
import cn.com.pano.pano.model.common.PanoExpositionMapHotspotQuery;
import cn.com.pano.pano.model.common.PanoExpositionMapQuery;
import cn.com.pano.pano.model.common01.PanoExpositionMap01Model;
import cn.com.pano.pano.model.common01.PanoExpositionMapHotspot01Model;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 录入场景地图处理
 * 
 * @author ouyangzidou
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0305EntryService extends BaseService {

  @Autowired
  public PanoExpositionMap01Mapper panoExpositionMap01Mapper;
  @Autowired
  public PanoExpositionMapHotspot01Mapper panoExpositionMapHotspot01Mapper;
  @Autowired
  public Pano0305Mapper pano0305Mapper;

  /**
   * 录入地图处理。
   * 
   * @param inForm Pano0305Form
   */
  public EasyJson<Object> doEntry(Pano0305Form inForm) throws Exception {

    // TODO 当前为默认一张地图，以后完善
    PanoExpositionMapQuery expositionMapQuery = new PanoExpositionMapQuery();
    expositionMapQuery.createCriteria().andExpositionIdEqualTo(inForm.expositionId);
    List<PanoExpositionMap01Model> resultDatas =
        panoExpositionMap01Mapper.selectByBaseModel(expositionMapQuery);
    for (PanoExpositionMap01Model panoExpositionMap : resultDatas) {
      // 更改地图状态为未使用
      panoExpositionMap.expositionMapUseState = PanoConstantsIF.MAP_USE_STATE_NO;
      updateAudit(panoExpositionMap);
      panoExpositionMap01Mapper.updateByPrimaryKey(panoExpositionMap);
    }

    PanoExpositionMap panoExpositionMap =
        panoExpositionMap01Mapper.selectByPrimaryKey(inForm.selectedExpositionMapId);
    if (panoExpositionMap != null) {
      // 更改地图状态为使用中
      panoExpositionMap.expositionMapUseState = PanoConstantsIF.MAP_USE_STATE_YES;
      updateAudit(panoExpositionMap);
      panoExpositionMap01Mapper.updateByPrimaryKey(panoExpositionMap);

      // 将旧地图上热点转移到新地图上
      if (!ObjectUtils.isEmpty(inForm.existedExpositionMapId)) {
        PanoExpositionMapHotspotQuery expositionMapHotspotQuery =
            new PanoExpositionMapHotspotQuery();
        expositionMapHotspotQuery.createCriteria()
            .andExpositionMapIdEqualTo(inForm.existedExpositionMapId);
        List<PanoExpositionMapHotspot01Model> expositionMapHotspotList =
            panoExpositionMapHotspot01Mapper.selectByBaseModel(expositionMapHotspotQuery);
        for (PanoExpositionMapHotspot01Model expositionMapHotspot : expositionMapHotspotList) {
          expositionMapHotspot.expositionMapId = panoExpositionMap.expositionMapId;
          panoExpositionMapHotspot01Mapper.updateByPrimaryKey(expositionMapHotspot);
        }
      }
    }
    // 删除其余未使用地图及其相关信息
    // pano0305Mapper.deleteMiniMapHotspot(conditions);
    // pano0305Mapper.deleteMiniMap(conditions);

    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setMsg("保存成功！");
    return easyJson;
  }
}
