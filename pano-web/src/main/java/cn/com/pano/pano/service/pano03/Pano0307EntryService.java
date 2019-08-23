package cn.com.pano.pano.service.pano03;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.pano.pano.form.pano03.Pano0307Form;
import cn.com.pano.pano.mapper.common01.PanoExpositionMapHotspot01Mapper;
import cn.com.pano.pano.model.common.PanoExpositionMapHotspot;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 给地图上热点链接全景图信息。
 * 
 * @author ouyangzidou
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0307EntryService extends BaseService {

  @Autowired
  public PanoExpositionMapHotspot01Mapper panoExpositionMapHotspot01Mapper;

  /**
   * 给地图上热点链接全景图信息。
   * 
   * @param inForm Pano0307Form
   */
  public EasyJson<Object> doEntry(Pano0307Form inForm) throws Exception {
    // 检索热点是否存在
    if (!ObjectUtils.isEmpty(inForm.selectedHotspotId)
        && !ObjectUtils.isEmpty(inForm.selectedPanoramaId)) {
      PanoExpositionMapHotspot panoExpositionMapHotspot =
          panoExpositionMapHotspot01Mapper.selectByPrimaryKey(inForm.selectedHotspotId);
      if (panoExpositionMapHotspot != null) {
        // 更新地图热点信息
        panoExpositionMapHotspot.panoramaId = inForm.selectedPanoramaId;
        updateAudit(panoExpositionMapHotspot);
        panoExpositionMapHotspot01Mapper.updateByPrimaryKey(panoExpositionMapHotspot);
      }
    }
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setMsg("登录成功!");
    return easyJson;
  }
}
