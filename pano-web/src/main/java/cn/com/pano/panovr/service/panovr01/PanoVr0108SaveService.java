package cn.com.pano.panovr.service.panovr01;

import java.util.HashMap;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Maps;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.mapper.common01.PanoPanoramaHotspot01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPolygonHotspot01Mapper;
import cn.com.pano.pano.model.common.PanoPanoramaHotspot;
import cn.com.pano.pano.model.common.PanoPolygonHotspot;
import cn.com.pano.panovr.dto.panovr01.PanoVr0108Dto;
import cn.com.pano.panovr.form.panovr01.PanoVr0108Form;
import cn.com.pano.panovr.mapper.panovr01.PanoVr0108Mapper;
import cn.com.platform.framework.util.FwJsonUtil;
import cn.com.platform.framework.util.FwStringUtils;
import cn.com.platform.web.BaseService;

/**
 * 多边形热点存储
 * 
 * @author tangzhenzong
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class PanoVr0108SaveService extends BaseService {

  @Autowired
  public PanoVr0108Mapper vr0108Mapper;
  @Autowired
  public PanoPanoramaHotspot01Mapper panoPanoramaHotspot01Mapper;
  @Autowired
  public PanoPolygonHotspot01Mapper panoPolygonHotspot01Mapper;

  @SuppressWarnings("unchecked")
  public void doSave(PanoVr0108Form _inForm) throws Exception {
    HashMap<String, Object> conditions = Maps.newHashMap();
    conditions.put("panoramaId", _inForm.panoramaId);
    // 先 修改平面图上所有热点状态为1
    vr0108Mapper.changeDeleteFlag(conditions);
    // 删除多边形数据
    vr0108Mapper.deletePolygon(conditions);
    // 如果保存的时候存在热点坐标
    if (!ObjectUtils.isEmpty(_inForm.spotInfoListJson)) {
      _inForm.spotInfoList = FwJsonUtil.jsonToList(_inForm.spotInfoListJson, PanoVr0108Dto.class);

      if (_inForm.spotInfoList != null) {
        for (PanoVr0108Dto hotspot : _inForm.spotInfoList) {
          String mapHotspotId = hotspot.hotspotId;
          // 检索录入的热点坐标
          PanoPanoramaHotspot panoPanoramaHotspot =
              panoPanoramaHotspot01Mapper.selectByPrimaryKey(mapHotspotId);
          // 热点存在
          if (panoPanoramaHotspot != null) {
            panoPanoramaHotspot.deleteFlag = false;
            updateAudit(panoPanoramaHotspot);
            panoPanoramaHotspot01Mapper.updateByPrimaryKey(panoPanoramaHotspot);
          } else {
            // 该热点不存在，新规
            mapHotspotId = FwStringUtils.getUniqueId();
            panoPanoramaHotspot = new PanoPanoramaHotspot();
            panoPanoramaHotspot.panoramaId = _inForm.panoramaId;
            panoPanoramaHotspot.hotspotId = mapHotspotId;
            panoPanoramaHotspot.hotspotType = PanoConstantsIF.HOTSPOT_TYPE_HOTSPOT_POLYGON;
            createAudit(panoPanoramaHotspot);
            panoPanoramaHotspot01Mapper.insert(panoPanoramaHotspot);
          }
          // 通过id[mapHotspotId] 清空多边形点的表
          conditions.put("polygonId", mapHotspotId);
          panoPolygonHotspot01Mapper.clearPanoPolygonHotspot(conditions);

          hotspot.pointList =
              FwJsonUtil.jsonToList(hotspot.hotspotPointListJson, PanoPolygonHotspot.class);
          for (PanoPolygonHotspot polygonHotspot : hotspot.pointList) {

            PanoPolygonHotspot panoPolygonHotspot = new PanoPolygonHotspot();
            panoPolygonHotspot.polygonId = mapHotspotId;
            panoPolygonHotspot.polygonPointId = FwStringUtils.getUniqueId();
            panoPolygonHotspot.polygonPointAth = polygonHotspot.polygonPointAth;
            panoPolygonHotspot.polygonPointAtv = polygonHotspot.polygonPointAtv;
            createAudit(panoPolygonHotspot);
            panoPolygonHotspot01Mapper.insert(panoPolygonHotspot);
          }

        }
      }
    }
    vr0108Mapper.deleteSpot(conditions);

  }
}
