package cn.com.pano.pano.service.pano01;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.form.pano01.Pano0106Form;
import cn.com.pano.pano.mapper.common01.PanoExpositionMap01Mapper;
import cn.com.pano.pano.mapper.common01.PanoExpositionMapHotspot01Mapper;
import cn.com.pano.pano.model.common.PanoExpositionMap;
import cn.com.pano.pano.model.common.PanoExpositionMapHotspotQuery;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 导航图编辑页面删除导航图功能。
 * 
 * @author 唐友成
 * @date 2019-08-19
 *
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0106UpdateService extends BaseService {

  @Autowired
  public PanoExpositionMap01Mapper panoExpositionMap01Mapper;
  @Autowired
  public PanoExpositionMapHotspot01Mapper panoExpositionMapHotspot01Mapper;

  /**
   * 导航图删除处理。
   * 
   * @param inForm Pano0106Form
   */
  public EasyJson<Object> doDeleteMap(Pano0106Form inForm) throws Exception {

    if (!ObjectUtils.isEmpty(inForm.expositionMapId)) {

      PanoExpositionMap map = panoExpositionMap01Mapper.selectByPrimaryKey(inForm.expositionMapId);

      PanoExpositionMapHotspotQuery expositionMapHotspotQuery = new PanoExpositionMapHotspotQuery();
      expositionMapHotspotQuery.createCriteria().andExpositionMapIdEqualTo(inForm.expositionMapId);
      // 删除展览地图上的热点
      panoExpositionMapHotspot01Mapper.deleteByBaseModel(expositionMapHotspotQuery);

      // 更新展览地图使用状态
      if (map != null) {
        // 展览会地图使用状态 0：未使用、1：使用中
        map.expositionMapUseState = PanoConstantsIF.MAP_USE_STATE_NO;
        updateAudit(map);
        panoExpositionMap01Mapper.updateByPrimaryKey(map);
      }

    }
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setMsg("删除成功！");
    return easyJson;
  }
}
