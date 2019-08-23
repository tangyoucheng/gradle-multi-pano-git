package cn.com.pano.pano.service.pano01;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.form.pano01.Pano0105Form;
import cn.com.pano.pano.mapper.common.PanoExpositionMapper;
import cn.com.pano.pano.mapper.common01.PanoExpositionMapHotspot01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanorama01Mapper;
import cn.com.pano.pano.model.common.PanoExposition;
import cn.com.pano.pano.model.common.PanoExpositionMapHotspot;
import cn.com.pano.pano.model.common.PanoPanorama;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 检索取得全景图信息。
 * 
 * @author ouyangzidou
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0105SearchService extends BaseService {

  @Autowired
  public PanoPanorama01Mapper panoPanorama01Mapper;
  @Autowired
  public PanoExpositionMapHotspot01Mapper panoExpositionMapHotspot01Mapper;
  @Autowired
  public PanoExpositionMapper panoExpositionMapper;

  /**
   * 检查热点链接信息是否存在。
   * 
   * @param inForm Pano0105Form
   */
  public EasyJson<Object> doCheckHostspotInfo(Pano0105Form inForm) throws Exception {
    EasyJson<Object> easyJson = new EasyJson<Object>();
    // 查找被选中的热点信息
    PanoExpositionMapHotspot panoExpositionMapHotspot =
        panoExpositionMapHotspot01Mapper.selectByPrimaryKey(inForm.selectedHotspotId);
    if (panoExpositionMapHotspot == null) {
      // 当前热点已被其他用户删除！
      easyJson.setObj(inForm);
      return easyJson;
    }
    // 热点下链接了全景图的场合
    if (!ObjectUtils.isEmpty(panoExpositionMapHotspot.panoramaId)) {
      PanoPanorama panoPanorama =
          panoPanorama01Mapper.selectByPrimaryKey(panoExpositionMapHotspot.panoramaId);
      inForm.panoramaPath = panoPanorama.panoramaId + PanoConstantsIF.PANOS_SHOW_L_XML;
      inForm.panoramaName = panoPanorama.panoramaName;

      // 取得雷达角度
      if (!ObjectUtils.isEmpty(panoExpositionMapHotspot.expositionMapHotspotHeading)) {
        inForm.radarHeading = panoExpositionMapHotspot.expositionMapHotspotHeading;
      } else {
        // 没有雷达角度，默认雷达角度
        inForm.radarHeading = "0";
      }
      easyJson.setObj(inForm);
      return easyJson;
    }
    // 热点下没有链接信息
    easyJson.setObj(inForm);
    return easyJson;
  }

  /**
   * 检查浮动层是否被保存。
   * 
   * @param inForm Pano0105Form
   */
  public String doCheckFlowInfoLayer(Pano0105Form inForm) throws Exception {
    PanoExposition panoExposition = panoExpositionMapper.selectByPrimaryKey(inForm.expositionId);
    if (panoExposition != null && !ObjectUtils.isEmpty(panoExposition.flowInfoFileId)) {
      if (inForm.flowInfoFileId.equals(panoExposition.flowInfoFileId)) {
        return "true";
      }
    }
    return "false";
  }

}
