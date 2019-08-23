package cn.com.pano.panovr.service.panovr02;

import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Maps;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.mapper.common01.PanoHotspotUrl01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanorama01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanoramaHotspot01Mapper;
import cn.com.pano.pano.model.common.PanoHotspotUrl;
import cn.com.pano.pano.model.common.PanoPanoramaHotspot;
import cn.com.pano.panovr.form.panovr02.PanoVr0204Form;
import cn.com.platform.web.BaseService;

/**
 * 导航热点链接图更新处理
 * 
 * @return
 * @throws Exception
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class PanoVr0204EntryService extends BaseService {

  @Autowired
  public PanoPanoramaHotspot01Mapper panoPanoramaHotspot01Mapper;
  @Autowired
  public PanoPanorama01Mapper panoPanorama01Mapper;
  @Autowired
  public PanoHotspotUrl01Mapper panoHotspotUrl01Mapper;

  /**
   * 热点链接图更新
   * 
   * @param loginUserId
   * @param _inForm
   * @throws Exception
   */
  public void doEntry(PanoVr0204Form _inForm) throws Exception {

    // 更新热点的信息
    PanoPanoramaHotspot panoPanoramaHotspot =
        panoPanoramaHotspot01Mapper.selectByPrimaryKey(_inForm.selectedHotspotId);
    panoPanoramaHotspot.hotspotUrlType = PanoConstantsIF.HOTSPOT_URL_TYPE_PANORAMA;
    updateAudit(panoPanoramaHotspot);
    panoPanoramaHotspot01Mapper.updateByPrimaryKey(panoPanoramaHotspot);

    // 更新热点url信息
    HashMap<String, Object> condition = Maps.newHashMap();
    condition.put("hotspotId", panoPanoramaHotspot.hotspotId);
    List<PanoHotspotUrl> panoHotspotUrllist = panoHotspotUrl01Mapper.selectByHotspotId(condition);
    if (panoHotspotUrllist != null && panoHotspotUrllist.size() > 0) {
      // 该场景切换热点下已有场景,删除该热点下的场景
      String _hotspotId = panoHotspotUrllist.get(0).hotspotId;
      String _urlObjectId = panoHotspotUrllist.get(0).hotspotUrlObjectId;
      PanoHotspotUrl panoHotspotUrl =
          panoHotspotUrl01Mapper.selectByPrimaryKey(_hotspotId, _urlObjectId);
      if (panoHotspotUrl != null) {
        panoHotspotUrl01Mapper.deleteByPrimaryKey(panoHotspotUrl.hotspotId,
            panoHotspotUrl.hotspotUrlObjectId);
      }
    }
    // 该场景切换热点下没有场景
    PanoHotspotUrl panoHotspotUrl = new PanoHotspotUrl();
    panoHotspotUrl.hotspotId = panoPanoramaHotspot.hotspotId;
    // 该热点的UrlObjectId对应为下拉框中选中的全景图的Id
    panoHotspotUrl.hotspotUrlObjectId = _inForm.selectedPanoramaId;
    createAudit(panoHotspotUrl);
    panoHotspotUrl01Mapper.insert(panoHotspotUrl);

  }
}
