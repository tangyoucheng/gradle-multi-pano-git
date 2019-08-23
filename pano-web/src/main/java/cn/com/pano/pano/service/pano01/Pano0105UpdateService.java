package cn.com.pano.pano.service.pano01;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.pano.pano.form.pano01.Pano0105Form;
import cn.com.pano.pano.mapper.common.PanoExpositionMapper;
import cn.com.pano.pano.mapper.common01.PanoExpositionMapHotspot01Mapper;
import cn.com.pano.pano.model.common.PanoExposition;
import cn.com.pano.pano.model.common.PanoExpositionMapHotspot;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 展览浮动信息的相关保存操作。
 * 
 * @author ouyangzidou
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0105UpdateService extends BaseService {
  @Autowired
  public PanoExpositionMapper panoExpositionMapper;
  @Autowired
  public PanoExpositionMapHotspot01Mapper panoExpositionMapHotspot01Mapper;

  /**
   * 保存导航图上热点的雷达角度。
   * 
   * @param inForm Pano0105Form
   */
  public EasyJson<Object> doSaveRadarHeading(Pano0105Form inForm) throws Exception {
    // 取得loginUserId
    // 如果雷达角度存在

    EasyJson<Object> easyJson = new EasyJson<Object>();
    if (!ObjectUtils.isEmpty(inForm.radarHeading)
        && !ObjectUtils.isEmpty(inForm.selectedHotspotId)) {

      PanoExpositionMapHotspot panoExpositionMapHotspot =
          panoExpositionMapHotspot01Mapper.selectByPrimaryKey(inForm.selectedHotspotId);

      if (panoExpositionMapHotspot != null) {
        panoExpositionMapHotspot.expositionMapHotspotHeading = inForm.radarHeading;
        updateAudit(panoExpositionMapHotspot);
        panoExpositionMapHotspot01Mapper.updateByPrimaryKey(panoExpositionMapHotspot);

        // 读取最新的数据库中的导航图上热点信息
        // PanoExpositionMapHotspotQuery expositionMapHotspotQuery =
        // new PanoExpositionMapHotspotQuery();
        // expositionMapHotspotQuery.createCriteria()
        // .andExpositionMapIdEqualTo(inForm.expositionMapId);
        // inForm.miniMapSpotInfoList =
        // panoExpositionMapHotspot01Mapper.selectByBaseModel(expositionMapHotspotQuery);
        // if (inForm.miniMapSpotInfoList != null) {
        // for (PanoExpositionMapHotspot expositionMapHotspot : inForm.miniMapSpotInfoList) {
        // if (inForm.panoramaId.equals(expositionMapHotspot.panoramaId)) {
        // inForm.selectedHotspotId = expositionMapHotspot.expositionMapHotspotId;
        // }
        // }
        // }
        // inForm.miniMapSpotInfoListJson =
        // objectMapper.writeValueAsString(inForm.miniMapSpotInfoList);

      }

    }
    easyJson.setObj(inForm);
    return easyJson;
  }

  /**
   * 保存展览浮动信息层的位置
   * 
   * @param inForm
   * @throws Exception
   */
  public EasyJson<Object> doSaveFlowInfoOffset(Pano0105Form inForm) throws Exception {
    // 如果浮动信息及其坐标存在
    if (!ObjectUtils.isEmpty(inForm.flowInfoFileId) && !ObjectUtils.isEmpty(inForm.flowInfoFileX)
        && !ObjectUtils.isEmpty(inForm.flowInfoFileY)) {

      PanoExposition panoExposition = panoExpositionMapper.selectByPrimaryKey(inForm.expositionId);

      if (panoExposition != null) {
        panoExposition.flowInfoFileId = inForm.flowInfoFileId;
        panoExposition.flowInfoFileX = inForm.flowInfoFileX;
        panoExposition.flowInfoFileY = inForm.flowInfoFileY;
        panoExposition.flowInfoFileScale = inForm.flowInfoFileScale;
        updateAudit(panoExposition);
        panoExpositionMapper.updateByPrimaryKey(panoExposition);
      }
    }
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setMsg("保存成功！");;
    return easyJson;
  }

  /**
   * 保存展览工具条的状态
   * 
   * @param inForm
   * @return
   * @throws Exception
   */
  public EasyJson<Object> doSaveButtonsBar(Pano0105Form inForm) throws Exception {
    PanoExposition panoExposition = panoExpositionMapper.selectByPrimaryKey(inForm.expositionId);
    panoExposition.expositionSelectedButtons = inForm.selectedButtons;
    panoExpositionMapper.updateByPrimaryKey(panoExposition);
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setMsg("保存成功！");;
    return easyJson;
  }

  /**
   * 删除浮动信息
   * 
   * @param inForm
   * @return
   * @throws Exception
   */
  public EasyJson<Object> doDeleteFlowInfo(Pano0105Form inForm) throws Exception {
    // 取得loginUserId

    PanoExposition panoExposition = panoExpositionMapper.selectByPrimaryKey(inForm.expositionId);
    if (panoExposition != null) {
      panoExposition.flowInfoFileId = "";
      panoExposition.flowInfoFileX = "";
      panoExposition.flowInfoFileY = "";
      panoExposition.flowInfoFileScale = "";
      updateAudit(panoExposition);
      panoExpositionMapper.updateByPrimaryKey(panoExposition);
    }
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setMsg("删除成功！");;
    return easyJson;
  }
}
