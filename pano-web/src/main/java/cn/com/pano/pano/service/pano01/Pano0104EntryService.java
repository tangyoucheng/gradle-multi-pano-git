package cn.com.pano.pano.service.pano01;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.pano.pano.form.pano01.Pano0104Form;
import cn.com.pano.pano.mapper.common01.PanoPanorama01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanoramaHotspot01Mapper;
import cn.com.pano.pano.model.common.PanoPanorama;
import cn.com.pano.pano.model.common.PanoPanoramaHotspot;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 设定场景视角初始位置。
 * 
 * @author ouyangzidou
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0104EntryService extends BaseService {

  @Autowired
  public PanoPanoramaHotspot01Mapper panoPanoramaHotspot01Mapper;
  @Autowired
  public PanoPanorama01Mapper panoPanorama01Mapper;

  /**
   * 设定场景视角初始位置。
   * 
   * @throws Exception 异常的场合
   */
  public EasyJson<Object> doSaveLookatPoint(Pano0104Form inForm) throws Exception {
    // 取得loginUserId
    if (ObjectUtils.isEmpty(inForm.selectedHotspotId)) {
      // 该场景没有被指向
      PanoPanorama panoPanorama = panoPanorama01Mapper.selectByPrimaryKey(inForm.panoramaId);
      panoPanorama.setPanoramaVlookat(inForm.panoramaVlookat);;
      panoPanorama.setPanoramaHlookat(inForm.panoramaHlookat);;
      updateAudit(panoPanorama);
      panoPanorama01Mapper.updateByPrimaryKey(panoPanorama);
    } else {
      // 该场景已被热点指向
      PanoPanoramaHotspot panoPanoramaHotspot =
          panoPanoramaHotspot01Mapper.selectByPrimaryKey(inForm.selectedHotspotId);
      panoPanoramaHotspot.setPanoramaVlookat(inForm.panoramaVlookat);;
      panoPanoramaHotspot.setPanoramaHlookat(inForm.panoramaHlookat);;
      updateAudit(panoPanoramaHotspot);
      panoPanoramaHotspot01Mapper.updateByPrimaryKey(panoPanoramaHotspot);
    }

    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setSuccess(true);
    easyJson.setMsg("保存成功");
    return easyJson;
  }
}
