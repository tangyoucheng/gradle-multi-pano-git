package cn.com.pano.panovr.service.panovr02;

import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Maps;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.mapper.common.PanoExpositionMapper;
import cn.com.pano.pano.mapper.common01.PanoPanoramaHotspot01Mapper;
import cn.com.pano.pano.model.common.PanoExposition;
import cn.com.pano.pano.model.common.PanoPanoramaHotspot;
import cn.com.pano.panovr.form.panovr02.PanoVr0209Form;
import cn.com.platform.web.BaseService;

/**
 * 修改后的热点大小信息保存
 * 
 * @author tangzhenzong
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class PanoVr0209EntryService extends BaseService {
  @Autowired
  public PanoPanoramaHotspot01Mapper panoPanoramaHotspot01Mapper;
  @Autowired
  public PanoExpositionMapper panoExpositionMapper;

  /**
   * 编辑热点、浮动信息层的大小
   * 
   * @param _inForm
   * @return
   * @throws Exception
   */
  public String doSave(PanoVr0209Form _inForm) throws Exception {
    // 如果是浮动信息层
    if (!ObjectUtils.isEmpty(_inForm.vr0209flowFileId)) {
      PanoExposition panoExposition =
          panoExpositionMapper.selectByPrimaryKey(_inForm.expositionIdForPanoVr0209);
      if (panoExposition != null) {
        panoExposition.flowInfoFileScale = _inForm.hotspotScale;
        updateAudit(panoExposition);
        panoExpositionMapper.updateByPrimaryKey(panoExposition);
        return "true_layer";
      }
    } else if ("commonInfo".equals(_inForm.commandTypeFromPanoVr0105)) {
      // 编辑推荐路线信息情况
      PanoExposition panoExposition =
          panoExpositionMapper.selectByPrimaryKey(_inForm.expositionIdForPanoVr0209);
      if (panoExposition != null) {
        panoExposition.expositionRecommendInfo = _inForm.recommendInfo;
        panoExposition.expoGoSceneTooltip = _inForm.ExpoGoSceneInfo;
        panoExpositionMapper.updateByPrimaryKey(panoExposition);
        return "recommend_info";
      }
    } else {
      // 普通热点情况
      PanoPanoramaHotspot panoPanoramaHotspot =
          panoPanoramaHotspot01Mapper.selectByPrimaryKey(_inForm.vr0209hotspotId);
      panoPanoramaHotspot.hotspotScale = _inForm.hotspotScale;
      updateAudit(panoPanoramaHotspot);
      // 导航热点的情况
      if (PanoConstantsIF.HOTSPOT_TYPE_HOTSPOT_CHANGE_SCENE
          .equals(panoPanoramaHotspot.hotspotType)) {
        // 导航热点信息编辑
        panoPanoramaHotspot.hotspotTooltip = _inForm.expoHotspotTooltipInfo;
        // 判断在导航点编辑情况下是否被设置为了推荐路径点
        if (!ObjectUtils.isEmpty(_inForm.recommendFlag)
            && PanoConstantsIF.RECOMMEND_FLAG_YES.equals(_inForm.recommendFlag)) {
          // 设置当前被选中的点的信息
          PanoExposition panoExposition =
              panoExpositionMapper.selectByPrimaryKey(_inForm.expositionIdForPanoVr0209);
          if (!ObjectUtils.isEmpty(panoExposition.expositionRecommendInfo)) {
            panoPanoramaHotspot.recommendInfo = panoExposition.expositionRecommendInfo;
          } else {
            panoPanoramaHotspot.recommendInfo = "推荐路线";
          }
          if (!ObjectUtils.isEmpty(_inForm.vr0209TheLastedSceneHotspotId)) {
            // 被选中的热点设为推荐路线点的同时，更新上个场景跳转时导航点的next字段
            PanoPanoramaHotspot lastSceneHotspot =
                panoPanoramaHotspot01Mapper.selectByPrimaryKey(_inForm.vr0209TheLastedSceneHotspotId);
            lastSceneHotspot.nextRecommendHotspotId = _inForm.vr0209hotspotId;
            panoPanoramaHotspot01Mapper.updateByPrimaryKey(lastSceneHotspot);
          } else {
            // 首个场景的推荐路线点设定，没有上个场景跳转的情况,生成虚拟热点，其nextRecommendHotspotId来存储首个场景里被设为推荐路径的点的ID
            PanoPanoramaHotspot fakeHotspot =
                panoPanoramaHotspot01Mapper.selectByPrimaryKey(_inForm.expositionIdForPanoVr0209);
            if (fakeHotspot != null) {
              fakeHotspot.nextRecommendHotspotId = _inForm.vr0209hotspotId;
              panoPanoramaHotspot01Mapper.updateByPrimaryKey(fakeHotspot);
            } else {
              fakeHotspot = new PanoPanoramaHotspot();
              fakeHotspot.hotspotType =
                  PanoConstantsIF.HOTSPOT_TYPE_HOTSPOT_FOR_FIRST_SCENE_RECOMMEND;
              fakeHotspot.hotspotId = _inForm.expositionIdForPanoVr0209;
              fakeHotspot.panoramaId = panoPanoramaHotspot.panoramaId;
              fakeHotspot.nextRecommendHotspotId = _inForm.vr0209hotspotId;
              createAudit(fakeHotspot);
              panoPanoramaHotspot01Mapper.insert(fakeHotspot);
            }
          }
          panoPanoramaHotspot01Mapper.updateByPrimaryKey(panoPanoramaHotspot);
          return "true_guid_hotspot";

        } else {
          // 取消了勾选框的情况
          // 检索展览下所有的导航热点
          HashMap<String, Object> condition = Maps.newHashMap();
          List<PanoPanoramaHotspot> expoHotspotList =
              panoPanoramaHotspot01Mapper.selectHostSpotByExpositionId(condition);
          // 循环每一个导航热点
          for (PanoPanoramaHotspot hotspot : expoHotspotList) {
            // 如果该热点是展览中任意一个导航热点的下一个推荐路限点
            if (_inForm.vr0209hotspotId.equals(hotspot.nextRecommendHotspotId)) {
              // 先置上一个导航点的nextRecommendHotspotId字段为空
              PanoPanoramaHotspot lastHotspot =
                  panoPanoramaHotspot01Mapper.selectByPrimaryKey(hotspot.hotspotId);
              lastHotspot.nextRecommendHotspotId = "";
              panoPanoramaHotspot01Mapper.updateByPrimaryKey(lastHotspot);
              // 至推荐信息为空
              panoPanoramaHotspot.recommendInfo = "";
              break;
            }
          }

          // 如果该点是首场景的点
          PanoPanoramaHotspot fakeHotspot =
              panoPanoramaHotspot01Mapper.selectByPrimaryKey(_inForm.expositionIdForPanoVr0209);
          if (fakeHotspot != null
              && _inForm.vr0209hotspotId.equals(fakeHotspot.nextRecommendHotspotId)) {
            fakeHotspot.nextRecommendHotspotId = "";
          }

          panoPanoramaHotspot01Mapper.updateByPrimaryKey(panoPanoramaHotspot);
          return "false_guid_hotspot";
        }
      }
      panoPanoramaHotspot01Mapper.updateByPrimaryKey(panoPanoramaHotspot);
      return "true_hotspot";
    }
    return "false";
  }
}
