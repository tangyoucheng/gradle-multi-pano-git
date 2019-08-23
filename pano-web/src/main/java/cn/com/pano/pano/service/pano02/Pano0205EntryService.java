package cn.com.pano.pano.service.pano02;

import java.math.BigDecimal;
import java.util.Objects;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.NumberUtils;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.form.pano02.Pano0205Form;
import cn.com.pano.pano.mapper.common01.PanoHotspotUrl01Mapper;
import cn.com.pano.pano.mapper.common01.PanoMaterial01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanoramaHotspot01Mapper;
import cn.com.pano.pano.model.common.PanoHotspotUrl;
import cn.com.pano.pano.model.common.PanoHotspotUrlQuery;
import cn.com.pano.pano.model.common.PanoMaterial;
import cn.com.pano.pano.model.common.PanoPanoramaHotspot;
import cn.com.pano.pano.model.common01.PanoHotspotUrl01Model;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 热点链接素材更新处理。
 * 
 * @author admin
 *
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0205EntryService extends BaseService {

  @Autowired
  public PanoMaterial01Mapper panoMaterial01Mapper;
  @Autowired
  public PanoHotspotUrl01Mapper panoHotspotUrl01Mapper;
  @Autowired
  public PanoPanoramaHotspot01Mapper panoPanoramaHotspot01Mapper;

  /**
   * 热点链接素材更新。
   * 
   * @param inForm Pano0205Form
   */
  public EasyJson<Object> doEntry(Pano0205Form inForm) throws Exception {

    // 更新热点的基本信息
    PanoPanoramaHotspot panoPanoramaHotspot =
        panoPanoramaHotspot01Mapper.selectByPrimaryKey(inForm.selectedHotspotId);
    if (panoPanoramaHotspot != null) {

      PanoHotspotUrlQuery hotspotUrlQuery = new PanoHotspotUrlQuery();
      hotspotUrlQuery.createCriteria().andHotspotIdEqualTo(inForm.selectedHotspotId);

      // 判断热点素材种类
      if (PanoConstantsIF.HOTSPOT_URL_TYPE_LINK.equals(inForm.urlType)) {
        // 解除热点原有的图片素材
        panoHotspotUrl01Mapper.deleteByBaseModel(hotspotUrlQuery);
        // 设置当前热点的链接信息为外部链接信息（5）
        panoPanoramaHotspot.hotspotUrlType = PanoConstantsIF.HOTSPOT_URL_TYPE_LINK;
        panoPanoramaHotspot.externalLinkAddress = inForm.externalLinkAddress;

      } else if (PanoConstantsIF.HOTSPOT_URL_TYPE_IMAGE.equals(inForm.urlType)) {
        // 撤销热点原有外部链接信息
        panoPanoramaHotspot.externalLinkAddress = "";
        // 解除热点原有的图片素材
        panoHotspotUrl01Mapper.deleteByBaseModel(hotspotUrlQuery);
        // 设置当前热点的链接信息为素材图（2）
        panoPanoramaHotspot.hotspotUrlType = PanoConstantsIF.HOTSPOT_URL_TYPE_IMAGE;
        // 插入热点URL信息
        if (ObjectUtils.isNotEmpty(inForm.hotspotUrlInfoList)) {
          for (int i = 0; i < inForm.hotspotUrlInfoList.size(); i++) {
            PanoHotspotUrl01Model hotspotUrl = inForm.hotspotUrlInfoList.get(i);
            // 新规
            PanoHotspotUrl panoHotspotUrl = new PanoHotspotUrl();
            panoHotspotUrl.hotspotId = inForm.selectedHotspotId;
            panoHotspotUrl.hotspotUrlObjectId = hotspotUrl.hotspotUrlObjectId;
            int sortKey = i + 1;
            panoHotspotUrl.sortKey =
                NumberUtils.parseNumber(Objects.toString(sortKey), BigDecimal.class);
            createAudit(panoHotspotUrl);
            panoHotspotUrl01Mapper.insert(panoHotspotUrl);
          }
        }
      } else if (PanoConstantsIF.HOTSPOT_URL_TYPE_SOUND.equals(inForm.urlType)) {
        // 撤销热点原有外部链接信息
        panoPanoramaHotspot.externalLinkAddress = "";
        // 设置当前热点的链接信息为音乐（3）
        panoPanoramaHotspot.hotspotUrlType = PanoConstantsIF.HOTSPOT_URL_TYPE_SOUND;
        // 插入热点URL信息

        if (!ObjectUtils.isEmpty(inForm.selectedMaterialId)) {
          PanoMaterial sound = panoMaterial01Mapper.selectByPrimaryKey(inForm.selectedMaterialId);
          if (sound != null && !ObjectUtils.isEmpty(sound.materialPath)) {
            // 先删除sortKey为0的素材
            panoHotspotUrl01Mapper.deleteByBaseModel(hotspotUrlQuery);
            // 新规
            PanoHotspotUrl panoHotspotUrl = new PanoHotspotUrl();
            panoHotspotUrl.hotspotId = inForm.selectedHotspotId;
            panoHotspotUrl.hotspotUrlObjectId = sound.materialId;
            panoHotspotUrl.sortKey = BigDecimal.ZERO;
            createAudit(panoHotspotUrl);
            panoHotspotUrl01Mapper.insert(panoHotspotUrl);
          }
        }

      } else if (PanoConstantsIF.HOTSPOT_URL_TYPE_VIDEO.equals(inForm.urlType)) {
        // 视频
        // 解除热点原有的图片素材
        panoHotspotUrl01Mapper.deleteByBaseModel(hotspotUrlQuery);
        // 撤销热点原有外部链接信息
        panoPanoramaHotspot.externalLinkAddress = "";
        // 设置当前热点的链接信息为视频
        panoPanoramaHotspot.hotspotUrlType = PanoConstantsIF.HOTSPOT_URL_TYPE_VIDEO;
        // 插入热点URL信息
        PanoHotspotUrl panoHotspotUrl = new PanoHotspotUrl();
        panoHotspotUrl.hotspotId = inForm.selectedHotspotId;
        panoHotspotUrl.hotspotUrlObjectId = inForm.selectedMaterialId;
        createAudit(panoHotspotUrl);
        panoHotspotUrl01Mapper.insert(panoHotspotUrl);

      } else if (PanoConstantsIF.HOTSPOT_URL_TYPE_TEXT_IMAGE.equals(inForm.urlType)) {
        // 撤销热点原有外部链接信息
        panoPanoramaHotspot.externalLinkAddress = "";
        // 解除热点原有的图片素材
        panoHotspotUrl01Mapper.deleteByBaseModel(hotspotUrlQuery);
        // 设置当前热点的链接信息为素材图（2）
        panoPanoramaHotspot.hotspotUrlType = PanoConstantsIF.HOTSPOT_URL_TYPE_TEXT_IMAGE;
        // 插入热点URL信息
        if (inForm.hotspotUrlInfoList != null && !inForm.hotspotUrlInfoList.isEmpty()) {
          for (int i = 0; i < inForm.hotspotUrlInfoList.size(); i++) {
            PanoHotspotUrl01Model hotspotUrl01Model = inForm.hotspotUrlInfoList.get(i);
            // 新规
            PanoHotspotUrl panoHotspotUrl = new PanoHotspotUrl();
            panoHotspotUrl.hotspotId = inForm.selectedHotspotId;
            panoHotspotUrl.hotspotUrlObjectId = hotspotUrl01Model.hotspotUrlObjectId;
            int sortKey = i + 1;
            panoHotspotUrl.sortKey =
                NumberUtils.parseNumber(Objects.toString(sortKey), BigDecimal.class);
            createAudit(panoHotspotUrl);
            panoHotspotUrl01Mapper.insert(panoHotspotUrl);
          }
        }
      }

      panoPanoramaHotspot.hotspotTooltip = inForm.hotspotTooltip;

      // 如果是多边形热点
      if ("true".equals(inForm.isPolygon)) {
        panoPanoramaHotspot.polygonFillcolor = inForm.polygonFillcolor;
        panoPanoramaHotspot.polygonFillalpha = inForm.polygonFillalpha;
      }

      panoPanoramaHotspot01Mapper.updateByPrimaryKey(panoPanoramaHotspot);
    }

    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setMsg("保存成功！");
    return easyJson;

  }

}
