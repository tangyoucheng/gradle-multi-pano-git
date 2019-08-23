package cn.com.pano.pano.service.pano02;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.form.pano02.Pano0204Form;
import cn.com.pano.pano.mapper.common01.PanoHotspotUrl01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanoramaHotspot01Mapper;
import cn.com.pano.pano.model.common.PanoHotspotUrl;
import cn.com.pano.pano.model.common.PanoHotspotUrlQuery;
import cn.com.pano.pano.model.common.PanoPanoramaHotspot;
import cn.com.pano.pano.model.common01.PanoHotspotUrl01Model;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 导航热点链接图更新处理。
 * 
 * @return
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0204EntryService extends BaseService {

  @Autowired
  public PanoPanoramaHotspot01Mapper panoPanoramaHotspot01Mapper;
  @Autowired
  public PanoHotspotUrl01Mapper panoHotspotUrl01Mapper;

  /**
   * 热点链接图更新。
   * 
   * @param inForm Pano0204Form
   */
  public EasyJson<Object> doEntry(Pano0204Form inForm) throws Exception {

    // 更新热点的信息
    PanoPanoramaHotspot panoPanoramaHotspot =
        panoPanoramaHotspot01Mapper.selectByPrimaryKey(inForm.selectedHotspotId);
    panoPanoramaHotspot.hotspotUrlType = PanoConstantsIF.HOTSPOT_URL_TYPE_PANORAMA;
    updateAudit(panoPanoramaHotspot);
    panoPanoramaHotspot01Mapper.updateByPrimaryKey(panoPanoramaHotspot);

    // 更新热点url信息
    PanoHotspotUrlQuery hotspotUrlQuery = new PanoHotspotUrlQuery();
    hotspotUrlQuery.createCriteria().andHotspotIdEqualTo(panoPanoramaHotspot.hotspotId);
    List<PanoHotspotUrl01Model> panoHotspotUrllist =
        panoHotspotUrl01Mapper.selectByBaseModel(hotspotUrlQuery);
    if (panoHotspotUrllist != null && panoHotspotUrllist.size() > 0) {
      // 该场景切换热点下已有场景,删除该热点下的场景
      String hotspotId = panoHotspotUrllist.get(0).hotspotId;
      String urlObjectId = panoHotspotUrllist.get(0).hotspotUrlObjectId;
      PanoHotspotUrl panoHotspotUrl =
          panoHotspotUrl01Mapper.selectByPrimaryKey(hotspotId, urlObjectId);
      if (panoHotspotUrl != null) {
        panoHotspotUrl01Mapper.deleteByPrimaryKey(panoHotspotUrl.hotspotId,
            panoHotspotUrl.hotspotUrlObjectId);
      }
    }
    // 该场景切换热点下没有场景
    PanoHotspotUrl panoHotspotUrl = new PanoHotspotUrl();
    panoHotspotUrl.hotspotId = panoPanoramaHotspot.hotspotId;
    // 该热点的UrlObjectId对应为下拉框中选中的全景图的Id
    panoHotspotUrl.hotspotUrlObjectId = inForm.selectedPanoramaId;
    createAudit(panoHotspotUrl);
    panoHotspotUrl01Mapper.insert(panoHotspotUrl);

    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setMsg("登录成功！");;
    return easyJson;

  }
}
