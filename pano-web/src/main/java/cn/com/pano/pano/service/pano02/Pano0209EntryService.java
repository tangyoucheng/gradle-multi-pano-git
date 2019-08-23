package cn.com.pano.pano.service.pano02;

import java.util.List;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.form.pano02.Pano0209Form;
import cn.com.pano.pano.mapper.common.PanoExpositionMapper;
import cn.com.pano.pano.mapper.common01.PanoPanoramaHotspot01Mapper;
import cn.com.pano.pano.model.common.PanoExposition;
import cn.com.pano.pano.model.common.PanoPanoramaHotspot;
import cn.com.pano.pano.model.common.PanoPanoramaHotspotQuery;
import cn.com.pano.pano.model.common01.PanoPanoramaHotspot01Model;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 修改后的热点大小信息保存。
 * 
 * @author tangzhenzong
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0209EntryService extends BaseService {
  @Autowired
  public PanoPanoramaHotspot01Mapper panoPanoramaHotspot01Mapper;
  @Autowired
  public PanoExpositionMapper panoExpositionMapper;

  /**
   * 编辑热点、浮动信息层的大小。
   * 
   * @param inForm Pano0209Form
   * @return
   */
  public EasyJson<Object> doSave(Pano0209Form inForm) throws Exception {
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setMsg("保存成功！");
    // 如果是浮动信息层
    if (!ObjectUtils.isEmpty(inForm.pano0209flowFileId)) {
      PanoExposition panoExposition =
          panoExpositionMapper.selectByPrimaryKey(inForm.expositionIdForPano0209);
      if (panoExposition != null) {
        panoExposition.flowInfoFileScale = inForm.hotspotScale;
        updateAudit(panoExposition);
        panoExpositionMapper.updateByPrimaryKey(panoExposition);
        easyJson.setObj("true_layer");
        return easyJson;
      }
    } else if ("commonInfo".equals(inForm.commandTypeFromPano0105)) {
      // 编辑推荐路线信息情况
      PanoExposition panoExposition =
          panoExpositionMapper.selectByPrimaryKey(inForm.expositionIdForPano0209);
      if (panoExposition != null) {
        panoExposition.expositionRecommendInfo = inForm.recommendInfo;
        panoExposition.expoGoSceneTooltip = inForm.expoGoSceneInfo;
        panoExpositionMapper.updateByPrimaryKey(panoExposition);
        easyJson.setObj("recommend_info");
        return easyJson;
      }
    } else {
      // 普通热点情况
      PanoPanoramaHotspot panoPanoramaHotspot =
          panoPanoramaHotspot01Mapper.selectByPrimaryKey(inForm.pano0209hotspotId);
      panoPanoramaHotspot.hotspotScale = inForm.hotspotScale;
      updateAudit(panoPanoramaHotspot);
      // 导航热点的情况
      if (PanoConstantsIF.HOTSPOT_TYPE_HOTSPOT_CHANGE_SCENE
          .equals(panoPanoramaHotspot.hotspotType)) {
        // 导航热点信息编辑
        panoPanoramaHotspot.hotspotTooltip = inForm.expoHotspotTooltipInfo;
        // 判断在导航点编辑情况下是否被设置为了推荐路径点
        if (!ObjectUtils.isEmpty(inForm.recommendFlag)
            && PanoConstantsIF.RECOMMEND_FLAG_YES.equals(inForm.recommendFlag)) {
          // 设置当前被选中的点的信息
          PanoExposition panoExposition =
              panoExpositionMapper.selectByPrimaryKey(inForm.expositionIdForPano0209);
          if (!ObjectUtils.isEmpty(panoExposition.expositionRecommendInfo)) {
            panoPanoramaHotspot.recommendInfo = panoExposition.expositionRecommendInfo;
          } else {
            panoPanoramaHotspot.recommendInfo = "推荐路线";
          }
          if (!ObjectUtils.isEmpty(inForm.pano0209TheLastedSceneHotspotId)) {
            // 被选中的热点设为推荐路线点的同时，更新上个场景跳转时导航点的next字段
            PanoPanoramaHotspot lastSceneHotspot = panoPanoramaHotspot01Mapper
                .selectByPrimaryKey(inForm.pano0209TheLastedSceneHotspotId);
            lastSceneHotspot.nextRecommendHotspotId = inForm.pano0209hotspotId;
            panoPanoramaHotspot01Mapper.updateByPrimaryKey(lastSceneHotspot);
          } else {
            // 首个场景的推荐路线点设定，没有上个场景跳转的情况,生成虚拟热点，其nextRecommendHotspotId来存储首个场景里被设为推荐路径的点的ID
            PanoPanoramaHotspot fakeHotspot =
                panoPanoramaHotspot01Mapper.selectByPrimaryKey(inForm.expositionIdForPano0209);
            if (fakeHotspot != null) {
              fakeHotspot.nextRecommendHotspotId = inForm.pano0209hotspotId;
              panoPanoramaHotspot01Mapper.updateByPrimaryKey(fakeHotspot);
            } else {
              fakeHotspot = new PanoPanoramaHotspot();
              fakeHotspot.hotspotType =
                  PanoConstantsIF.HOTSPOT_TYPE_HOTSPOT_FOR_FIRST_SCENE_RECOMMEND;
              fakeHotspot.hotspotId = inForm.expositionIdForPano0209;
              fakeHotspot.panoramaId = panoPanoramaHotspot.panoramaId;
              fakeHotspot.nextRecommendHotspotId = inForm.pano0209hotspotId;
              createAudit(fakeHotspot);
              panoPanoramaHotspot01Mapper.insert(fakeHotspot);
            }
          }
          panoPanoramaHotspot01Mapper.updateByPrimaryKey(panoPanoramaHotspot);
          easyJson.setObj("true_guid_hotspot");
          return easyJson;

        } else {
          // 取消了勾选框的情况
          // 检索展览下所有的导航热点
          PanoPanoramaHotspotQuery panoramaHotspotQuery = new PanoPanoramaHotspotQuery();
          panoramaHotspotQuery.createCriteria().andPanoramaIdEqualTo(inForm.panoramaIdForPano0209);
          List<PanoPanoramaHotspot01Model> expoHotspotList =
              panoPanoramaHotspot01Mapper.selectByBaseModel(panoramaHotspotQuery);
          // 循环每一个导航热点
          for (PanoPanoramaHotspot01Model hotspot : expoHotspotList) {
            // 如果该热点是展览中任意一个导航热点的下一个推荐路限点
            if (inForm.pano0209hotspotId.equals(hotspot.nextRecommendHotspotId)) {
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
              panoPanoramaHotspot01Mapper.selectByPrimaryKey(inForm.expositionIdForPano0209);
          if (fakeHotspot != null
              && inForm.pano0209hotspotId.equals(fakeHotspot.nextRecommendHotspotId)) {
            fakeHotspot.nextRecommendHotspotId = "";
          }
          
          panoPanoramaHotspot01Mapper.updateByPrimaryKey(panoPanoramaHotspot);
          easyJson.setObj("false_guid_hotspot");
          return easyJson;
        }
      }
      panoPanoramaHotspot01Mapper.updateByPrimaryKey(panoPanoramaHotspot);
      easyJson.setObj("true_hotspot");
      return easyJson;
    }
    return easyJson;
  }
}
