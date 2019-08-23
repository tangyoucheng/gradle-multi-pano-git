package cn.com.pano.pano.service.pano02;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.utils.PanoCommonUtil;
import cn.com.pano.pano.dto.pano02.Pano0205Dto;
import cn.com.pano.pano.form.pano02.Pano0205Form;
import cn.com.pano.pano.mapper.common01.PanoHotspotUrl01Mapper;
import cn.com.pano.pano.mapper.common01.PanoMaterial01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanoramaHotspot01Mapper;
import cn.com.pano.pano.mapper.pano02.Pano0205Mapper;
import cn.com.pano.pano.model.common.PanoHotspotUrl;
import cn.com.pano.pano.model.common.PanoHotspotUrlQuery;
import cn.com.pano.pano.model.common.PanoMaterial;
import cn.com.pano.pano.model.common.PanoPanoramaHotspot;
import cn.com.pano.pano.model.common01.PanoHotspotUrl01Model;
import cn.com.pano.pano.model.common01.PanoMaterial01Model;
import cn.com.platform.framework.common.CodeValueRecord;
import cn.com.platform.web.BaseService;

/**
 * 普通热点加素材处理的初期显示。
 * 
 * @author ouyangzidou
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0205InitService extends BaseService {

  @Autowired
  public Pano0205Mapper pano0205Mapper;
  @Autowired
  public PanoPanoramaHotspot01Mapper panoPanoramaHotspot01Mapper;
  @Autowired
  public PanoHotspotUrl01Mapper panoHotspotUrl01Mapper;
  @Autowired
  public PanoMaterial01Mapper panoMaterial01Mapper;

  /**
   * 检索数据库中的素材信息，取得结果集。
   * 
   * @param inForm Pano0205Form
   */
  public void doInit(Pano0205Form inForm) throws Exception {

    if (ObjectUtils.isEmpty(inForm.selectedHotspotId)) {
      return;
    }

    // 拷贝展览素材和公共素材文件到APP服务器
    PanoCommonUtil.copyMaterialFromStorageToApp(inForm.expositionId);

    PanoHotspotUrlQuery hotspotUrlQuery = new PanoHotspotUrlQuery();
    hotspotUrlQuery.createCriteria().andHotspotIdEqualTo(inForm.selectedHotspotId);
    hotspotUrlQuery.setOrderByClause("sort_key");
    List<PanoHotspotUrl01Model> urlInfo = panoHotspotUrl01Mapper.selectByBaseModel(hotspotUrlQuery);
    if (urlInfo != null && urlInfo.size() > 0) {
      for (PanoHotspotUrl pano0104Hotspot : urlInfo) {
        if (pano0104Hotspot.sortKey == null) {
          break;
        }
        if (BigDecimal.ZERO.equals(pano0104Hotspot.sortKey)) {
          inForm.urlType = PanoConstantsIF.HOTSPOT_URL_TYPE_SOUND;
          break;
        }
      }
    }

    // url种类radioBox
    inForm.urlTypeList = Lists.newArrayList();
    inForm.urlTypeList.add(new CodeValueRecord(PanoConstantsIF.HOTSPOT_URL_TYPE_IMAGE, "信息图"));
    if (inForm.urlType == PanoConstantsIF.HOTSPOT_URL_TYPE_SOUND) {
      inForm.urlTypeList.add(new CodeValueRecord(PanoConstantsIF.HOTSPOT_URL_TYPE_SOUND, "音乐"));
    }
    inForm.urlTypeList.add(new CodeValueRecord(PanoConstantsIF.HOTSPOT_URL_TYPE_VIDEO, "视频"));
    inForm.urlTypeList.add(new CodeValueRecord(PanoConstantsIF.HOTSPOT_URL_TYPE_LINK, "外部链接"));
    inForm.urlTypeList.add(new CodeValueRecord(PanoConstantsIF.HOTSPOT_URL_TYPE_TEXT_IMAGE, "图文"));

    // 素材归属radioBox
    inForm.materialBelongTypeList = Lists.newArrayList();
    inForm.materialBelongTypeList
        .add(new CodeValueRecord(PanoConstantsIF.MATERIAL_BELONGTYPE_COMMON, "公用素材"));
    inForm.materialBelongTypeList
        .add(new CodeValueRecord(PanoConstantsIF.MATERIAL_BELONGTYPE_EXPOSITION, "当前展览素材"));
    inForm.materialBelongType = PanoConstantsIF.MATERIAL_BELONGTYPE_COMMON;


    // 判断当前热点是否是已有素材的热点进行再编辑
    PanoPanoramaHotspot panoPanoramaHotspot =
        panoPanoramaHotspot01Mapper.selectByPrimaryKey(inForm.selectedHotspotId);

    inForm.existedMaterialInfo = Lists.newArrayList();

    // 如果是图片信息类型的热点
    if (PanoConstantsIF.HOTSPOT_URL_TYPE_IMAGE.equals(panoPanoramaHotspot.hotspotUrlType)) {
      inForm.urlType = PanoConstantsIF.HOTSPOT_URL_TYPE_IMAGE;
      if (urlInfo != null && urlInfo.size() > 0) {
        // 该热点已经有素材图，属于再编辑情况

        for (PanoHotspotUrl01Model panoHotspotUrl : urlInfo) {
          PanoMaterial panoMaterial =
              panoMaterial01Mapper.selectByPrimaryKey(panoHotspotUrl.hotspotUrlObjectId);
          if (panoMaterial != null) {

            PanoMaterial01Model material01Model = new PanoMaterial01Model();
            // 检索到sortKey和相关信息
            material01Model.sortKey = panoHotspotUrl.sortKey;
            material01Model.materialPath = panoMaterial.materialPath;
            material01Model.materialId = panoMaterial.materialId;
            material01Model.materialName = panoMaterial.materialName;
            material01Model.notes = panoMaterial.notes;
            inForm.existedMaterialInfo.add(material01Model);
          }
        }

      }
    } else if (PanoConstantsIF.HOTSPOT_URL_TYPE_VIDEO.equals(panoPanoramaHotspot.hotspotUrlType)) {
      // 视频热点
      inForm.urlType = PanoConstantsIF.HOTSPOT_URL_TYPE_VIDEO;
      if (urlInfo != null && urlInfo.size() > 0) {
        // 已选的视频素材ID
        inForm.existedVideoId = urlInfo.get(0).hotspotUrlObjectId;
        // 取得已选素材的素材归属
        PanoMaterial material = panoMaterial01Mapper.selectByPrimaryKey(inForm.existedVideoId);

        if (material != null && !ObjectUtils.isEmpty(material.expositionId)) {

          inForm.existedMaterialInfo.add((PanoMaterial01Model) material);

          // 当前展览会素材
          if (material.expositionId.equals(inForm.expositionId)) {
            inForm.materialBelongType = PanoConstantsIF.MATERIAL_BELONGTYPE_EXPOSITION;
          }
          // 公共素材
          if (material.expositionId.equals("common_material")) {
            inForm.materialBelongType = PanoConstantsIF.MATERIAL_BELONGTYPE_COMMON;
          }
        }
      }
    } else if (PanoConstantsIF.HOTSPOT_URL_TYPE_LINK.equals(panoPanoramaHotspot.hotspotUrlType)) {
      inForm.urlType = PanoConstantsIF.HOTSPOT_URL_TYPE_LINK;
      inForm.externalLinkAddress = panoPanoramaHotspot.externalLinkAddress;
    } else if (PanoConstantsIF.HOTSPOT_URL_TYPE_SOUND.equals(inForm.urlType)) {
      // 如果是音乐类型的热点
      inForm.urlType = PanoConstantsIF.HOTSPOT_URL_TYPE_SOUND;
      if (urlInfo != null && urlInfo.size() > 0) {
        // 该热点已经有音乐，属于再编辑情况

        for (PanoHotspotUrl01Model panoHotspotUrl : urlInfo) {
          if (BigDecimal.ZERO.equals(panoHotspotUrl.sortKey)) {

            PanoMaterial panoMaterial =
                panoMaterial01Mapper.selectByPrimaryKey(panoHotspotUrl.hotspotUrlObjectId);
            if (!panoHotspotUrl.hotspotUrlObjectId.equals("musicMaterialId")
                && panoMaterial != null) {

              inForm.existedMaterialInfo.add((PanoMaterial01Model) panoMaterial);

              inForm.existedSoundId = panoHotspotUrl.hotspotUrlObjectId;
              // 当前展览会素材
              if (inForm.expositionId.equals(panoMaterial.expositionId)) {
                inForm.materialBelongType = PanoConstantsIF.MATERIAL_BELONGTYPE_EXPOSITION;
              }
              // 公共素材
              if ("common_material".equals(panoMaterial.expositionId)) {
                inForm.materialBelongType = PanoConstantsIF.MATERIAL_BELONGTYPE_COMMON;
              }
            }
          }
        }
      }
      // 图文的时候
    } else if (PanoConstantsIF.HOTSPOT_URL_TYPE_TEXT_IMAGE
        .equals(panoPanoramaHotspot.hotspotUrlType)) {
      inForm.urlType = PanoConstantsIF.HOTSPOT_URL_TYPE_TEXT_IMAGE;
      getTextImgList(inForm);
    }


    // 判断该热点是否为多边形热点
    inForm.isPolygon = "";
    // 取得已有toolitip
    if (!ObjectUtils.isEmpty(panoPanoramaHotspot.hotspotTooltip)) {
      inForm.hotspotTooltip = panoPanoramaHotspot.hotspotTooltip;
    }
    if (PanoConstantsIF.HOTSPOT_TYPE_HOTSPOT_POLYGON.equals(panoPanoramaHotspot.hotspotType)) {
      inForm.isPolygon = "true";
    }

    // 多边形的情况下取得颜色
    if (panoPanoramaHotspot != null && !ObjectUtils.isEmpty(panoPanoramaHotspot.polygonFillcolor)) {
      String fillColor = panoPanoramaHotspot.polygonFillcolor;
      fillColor = fillColor.replaceFirst("0x", "#");
      inForm.polygonFillcolor = fillColor;
      inForm.polygonFillalpha = panoPanoramaHotspot.polygonFillalpha;
    }

    inForm.existedMaterialInfoJson = objectMapper.writeValueAsString(inForm.existedMaterialInfo);

  }

  /**
   * 图文素材的取得处理。
   * 
   */
  public void getTextImgList(Pano0205Form inForm) throws Exception {
    HashMap<String, Object> condition = Maps.newHashMap();
    // 当前展览素材
    condition.put("expositionId", inForm.expositionId);
    // 热点ID
    condition.put("hotspotId", inForm.selectedHotspotId);
    // 材质类型ID
    condition.put("materialTypeId", PanoConstantsIF.MATERIAL_TYPE_IMAGE_TEXT);
    List<PanoMaterial01Model> dataList = pano0205Mapper.selectTextImgMaterialInfo(condition);
    for (PanoMaterial01Model dto : dataList) {
      // 取得该热点已有的素材结果集

      // 排序
      String notes = "";
      if (!ObjectUtils.isEmpty(dto.notes)) {
        notes = "  (" + dto.notes + ")";
      }
      // 素材名
      dto.label = dto.materialName + notes;
      // 素材ID
      dto.value = dto.materialId;
      // 是否选择
      if (dto.sortKey != null) {
        dto.selected = true;
      } else {
        dto.selected = false;
      }
      // 是否可能选择
      dto.disabled = false;
      inForm.existedMaterialInfo.add(dto);
    }
  }
}
