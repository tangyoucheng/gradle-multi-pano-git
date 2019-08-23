package cn.com.pano.pano.service.pano01;

import java.util.HashMap;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Maps;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.code.HotspotType;
import cn.com.pano.pano.dto.pano01.Pano0108Dto;
import cn.com.pano.pano.form.pano01.Pano0108Form;
import cn.com.pano.pano.mapper.common01.PanoPanoramaHotspot01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPolygonHotspot01Mapper;
import cn.com.pano.pano.mapper.pano01.Pano0108Mapper;
import cn.com.pano.pano.model.common.PanoPanoramaHotspot;
import cn.com.pano.pano.model.common.PanoPanoramaHotspotQuery;
import cn.com.pano.pano.model.common.PanoPolygonHotspot;
import cn.com.pano.pano.model.common.PanoPolygonHotspotQuery;
import cn.com.platform.framework.util.FwJsonUtil;
import cn.com.platform.framework.util.FwStringUtils;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 多边形热点存储。
 * 
 * @author tangzhenzong
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0108SaveService extends BaseService {

  @Autowired
  public Pano0108Mapper pano0108Mapper;
  @Autowired
  public PanoPanoramaHotspot01Mapper panoPanoramaHotspot01Mapper;
  @Autowired
  public PanoPolygonHotspot01Mapper panoPolygonHotspot01Mapper;

  /**
   * 保存处理。
   * 
   * @param inForm Pano0108Form
   */
  @SuppressWarnings("unchecked")
  public EasyJson<Object> doSave(Pano0108Form inForm) throws Exception {
    // 先 修改平面图上所有热点状态为1
    PanoPanoramaHotspot record = new PanoPanoramaHotspot();
    record.deleteFlag = true;
    PanoPanoramaHotspotQuery baseCondition = new PanoPanoramaHotspotQuery();
    baseCondition.createCriteria().andPanoramaIdEqualTo(inForm.panoramaId)
        .andHotspotTypeEqualTo(HotspotType.POLYGON.toString());
    panoPanoramaHotspot01Mapper.updateByBaseModelSelective(record, baseCondition);
    // 删除多边形数据
    HashMap<String, Object> conditions = Maps.newHashMap();
    conditions.put("panoramaId", inForm.panoramaId);
    conditions.put("hotspotType", HotspotType.POLYGON.toString());
    pano0108Mapper.deletePolygonPoint(conditions);
    // 如果保存的时候存在热点坐标
    if (!ObjectUtils.isEmpty(inForm.spotInfoListJson)) {
      inForm.spotInfoList = FwJsonUtil.jsonToList(inForm.spotInfoListJson, Pano0108Dto.class);

      if (inForm.spotInfoList != null) {
        for (Pano0108Dto hotspot : inForm.spotInfoList) {
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
            panoPanoramaHotspot.panoramaId = inForm.panoramaId;
            panoPanoramaHotspot.hotspotId = mapHotspotId;
            panoPanoramaHotspot.hotspotType = PanoConstantsIF.HOTSPOT_TYPE_HOTSPOT_POLYGON;
            createAudit(panoPanoramaHotspot);
            panoPanoramaHotspot01Mapper.insert(panoPanoramaHotspot);
          }
          // 通过id[mapHotspotId] 清空多边形点的表
          PanoPolygonHotspotQuery polygonHotspotQuery = new PanoPolygonHotspotQuery();
          polygonHotspotQuery.createCriteria().andPolygonIdEqualTo(mapHotspotId);
          panoPolygonHotspot01Mapper.deleteByBaseModel(polygonHotspotQuery);

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

    // 最后删除余下flg为1的热点
    baseCondition = new PanoPanoramaHotspotQuery();
    baseCondition.createCriteria().andDeleteFlagEqualTo(true)
        .andPanoramaIdEqualTo(inForm.panoramaId)
        .andHotspotTypeEqualTo(HotspotType.POLYGON.toString());;
    panoPanoramaHotspot01Mapper.deleteByBaseModel(baseCondition);

    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setSuccess(true);
    easyJson.setMsg("保存成功");
    return easyJson;
  }
}
