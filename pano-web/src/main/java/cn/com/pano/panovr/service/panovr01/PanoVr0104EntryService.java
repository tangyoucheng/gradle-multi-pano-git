package cn.com.pano.panovr.service.panovr01;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.pano.pano.mapper.common01.PanoPanorama01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanoramaHotspot01Mapper;
import cn.com.pano.pano.model.common.PanoPanorama;
import cn.com.pano.pano.model.common.PanoPanoramaHotspot;
import cn.com.pano.panovr.form.panovr01.PanoVr0104Form;
import cn.com.platform.web.BaseService;

/**
 * 设定场景视角初始位置
 * 
 * @author ouyangzidou
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class PanoVr0104EntryService extends BaseService {

  @Autowired
  public PanoPanoramaHotspot01Mapper panoPanoramaHotspot01Mapper;
  @Autowired
  public PanoPanorama01Mapper panoPanorama01Mapper;

  /**
   * 设定场景视角初始位置
   * 
   * @throws Exception
   */
  public void doSaveLookatPoint(PanoVr0104Form _inForm) throws Exception {
    if (ObjectUtils.isEmpty(_inForm.selectedHotspotId)) {
      // 该场景没有被指向
      PanoPanorama panoPanorama = panoPanorama01Mapper.selectByPrimaryKey(_inForm.panoramaId);
      panoPanorama.panoramaVlookat = _inForm.panoramaVlookat;
      panoPanorama.panoramaHlookat = _inForm.panoramaHlookat;
      updateAudit(panoPanorama);
      panoPanorama01Mapper.updateByPrimaryKey(panoPanorama);
    } else {
      // 该场景已被热点指向
      PanoPanoramaHotspot panoPanoramaHotspot =
          panoPanoramaHotspot01Mapper.selectByPrimaryKey(_inForm.selectedHotspotId);
      panoPanoramaHotspot.panoramaVlookat = _inForm.panoramaVlookat;
      panoPanoramaHotspot.panoramaHlookat = _inForm.panoramaHlookat;
      updateAudit(panoPanoramaHotspot);
      panoPanoramaHotspot01Mapper.updateByPrimaryKey(panoPanoramaHotspot);
    }
  }
}
