package cn.com.pano.pano.service.pano03;

import java.util.Objects;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.form.pano03.Pano0306Form;
import cn.com.pano.pano.mapper.common01.PanoExpositionMap01Mapper;
import cn.com.pano.pano.mapper.common01.PanoExpositionMapHotspot01Mapper;
import cn.com.pano.pano.model.common.PanoExpositionMap;
import cn.com.pano.pano.model.common.PanoExpositionMapHotspotQuery;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 导航图编辑。
 * 
 * @author 唐友成
 * @date 2019-08-19
 *
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0305UpdateService extends BaseService {
  @Autowired
  public PanoExpositionMap01Mapper panoExpositionMap01Mapper;
  public PanoExpositionMapHotspot01Mapper panoExpositionMapHotspot01Mapper;


  /**
   * 删除该素材。
   * 
   * @param inForm Pano0306Form
   */
  public EasyJson<Object> doDeleteExpositionMap(Pano0306Form inForm) throws Exception {

    if (!ObjectUtils.isEmpty(inForm.uniqueKeyList)) {
      for (Object expositionMapIdObject : inForm.uniqueKeyList) {
        String expositionMapId = Objects.toString(expositionMapIdObject, "");
        PanoExpositionMap panoExpositionMap =
            panoExpositionMap01Mapper.selectByPrimaryKey(expositionMapId);

        // 若该地图为当前展览地图 删除展览地图上的热点
        if (PanoConstantsIF.MAP_USE_STATE_YES.equals(panoExpositionMap.expositionMapUseState)) {
          PanoExpositionMapHotspotQuery expositionMapHotspotQuery =
              new PanoExpositionMapHotspotQuery();
          expositionMapHotspotQuery.createCriteria().andExpositionMapIdEqualTo(expositionMapId);
          panoExpositionMapHotspot01Mapper.deleteByBaseModel(expositionMapHotspotQuery);
        }

        // 删除该地图素材
        panoExpositionMap01Mapper.deleteByPrimaryKey(panoExpositionMap.expositionMapId);

      }
    }

    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setMsg("删除成功！");
    return easyJson;

  }

}
