package cn.com.pano.pano.service.pano01;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Maps;
import cn.com.pano.pano.form.pano01.Pano0107Form;
import cn.com.pano.pano.mapper.common01.PanoExpositionMapHotspot01Mapper;
import cn.com.pano.pano.model.common.PanoExpositionMapHotspot;
import cn.com.pano.pano.model.common.PanoExpositionMapHotspotQuery;
import cn.com.platform.framework.util.FwStringUtils;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 热点编辑画面的录入处理。
 * 
 * @author ouyangzidou
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0107EntryService extends BaseService {

  @Autowired
  public PanoExpositionMapHotspot01Mapper panoExpositionMapHotspot01Mapper;

  /**
   * 登录处理。
   * 
   * @param inForm Pano0107Form
   */
  public EasyJson<Object> doSave(Pano0107Form inForm) throws Exception {

    // 先修改图上所有热点状态为1
    PanoExpositionMapHotspot record = new PanoExpositionMapHotspot();
    record.deleteFlag = true;
    PanoExpositionMapHotspotQuery baseCondition = new PanoExpositionMapHotspotQuery();
    baseCondition.createCriteria().andExpositionMapIdEqualTo(inForm.expositionMapId);
    panoExpositionMapHotspot01Mapper.updateByBaseModelSelective(record, baseCondition);
    // 如果保存时存在热点坐标
    // 分割字符串取热点数组
    if (inForm.miniMapSpotInfoList != null) {
      // 循环热点
      for (PanoExpositionMapHotspot mapHotspot : inForm.miniMapSpotInfoList) {
        // 检索录入的热点坐标
        PanoExpositionMapHotspot panoExpositionMapHotspot =
            panoExpositionMapHotspot01Mapper.selectByPrimaryKey(mapHotspot.expositionMapHotspotId);
        // 热点存在
        if (panoExpositionMapHotspot != null) {
          // 该热点存在但没有全景图，做普通更新录入操作
          panoExpositionMapHotspot.expositionMapHotspotX = mapHotspot.expositionMapHotspotX;
          panoExpositionMapHotspot.expositionMapHotspotY = mapHotspot.expositionMapHotspotY;
          panoExpositionMapHotspot.deleteFlag = false;
          updateAudit(panoExpositionMapHotspot);
          panoExpositionMapHotspot01Mapper.updateByPrimaryKey(panoExpositionMapHotspot);
          continue;

        }
        // 该热点不存在，新规
        panoExpositionMapHotspot = new PanoExpositionMapHotspot();
        panoExpositionMapHotspot.expositionMapId = inForm.expositionMapId;
        panoExpositionMapHotspot.expositionMapHotspotId = FwStringUtils.getUniqueId();
        panoExpositionMapHotspot.expositionMapHotspotX = mapHotspot.expositionMapHotspotX;
        panoExpositionMapHotspot.expositionMapHotspotY = mapHotspot.expositionMapHotspotY;
        panoExpositionMapHotspot.expositionMapHotspotUrl = mapHotspot.expositionMapHotspotUrl;
        createAudit(panoExpositionMapHotspot);
        panoExpositionMapHotspot01Mapper.insert(panoExpositionMapHotspot);

      }
    }
    // 最后删除余下flg为1的热点
    PanoExpositionMapHotspotQuery expositionMapHotspotQuery = new PanoExpositionMapHotspotQuery();
    expositionMapHotspotQuery.createCriteria().andDeleteFlagEqualTo(true);
    panoExpositionMapHotspot01Mapper.deleteByBaseModel(expositionMapHotspotQuery);

    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setMsg("保存成功！");
    return easyJson;
  }
}
