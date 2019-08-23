package cn.com.pano.panovr.service.panovr01;

import java.util.HashMap;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Maps;
import cn.com.pano.pano.mapper.common01.PanoExpositionMapHotspot01Mapper;
import cn.com.pano.pano.model.common.PanoExpositionMapHotspot;
import cn.com.pano.panovr.form.panovr01.PanoVr0107Form;
import cn.com.platform.framework.util.FwJsonUtil;
import cn.com.platform.framework.util.FwStringUtils;
import cn.com.platform.web.BaseService;

/**
 * 热点编辑画面的录入处理
 * 
 * @author ouyangzidou
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class PanoVr0107EntryService extends BaseService {

  @Autowired
  public PanoExpositionMapHotspot01Mapper panoExpositionMapHotspot01Mapper;

  @SuppressWarnings("unchecked")
  public void doSave(PanoVr0107Form _inForm) throws Exception {

    HashMap<String, Object> conditions = Maps.newHashMap();
    conditions.put("expositionMapId", _inForm.expositionMapId);
    // 先修改图上所有热点状态为1
    panoExpositionMapHotspot01Mapper.changeDeleteFlag(conditions);
    // 如果保存时存在热点坐标
    if (!ObjectUtils.isEmpty(_inForm.spotInfoListSubmitJson)) {
      _inForm.miniMapSpotInfoList =
          FwJsonUtil.jsonToList(_inForm.spotInfoListSubmitJson, PanoExpositionMapHotspot.class);
      // 分割字符串取热点数组
      if (_inForm.miniMapSpotInfoList != null) {
        // 循环热点
        for (PanoExpositionMapHotspot mapHotspot : _inForm.miniMapSpotInfoList) {
          // 检索录入的热点坐标
          PanoExpositionMapHotspot panoExpositionMapHotspot =
              panoExpositionMapHotspot01Mapper.selectByPrimaryKey(mapHotspot.expositionMapHotspotId);
          // 热点存在
          if (panoExpositionMapHotspot != null) {
            // 该热点存在但没有全景图，做普通更新录入操作
            panoExpositionMapHotspot.expositionMapHotspotX = mapHotspot.expositionMapHotspotX;
            panoExpositionMapHotspot.expositionMapHotspotY = mapHotspot.expositionMapHotspotY;
            panoExpositionMapHotspot.deleteFlag = false;
            panoExpositionMapHotspot01Mapper.updateByPrimaryKey(panoExpositionMapHotspot);
            continue;

          }
          // 该热点不存在，新规
          panoExpositionMapHotspot = new PanoExpositionMapHotspot();
          panoExpositionMapHotspot.expositionMapId = _inForm.expositionMapId;
          panoExpositionMapHotspot.expositionMapHotspotId = FwStringUtils.getUniqueId();
          panoExpositionMapHotspot.expositionMapHotspotX = mapHotspot.expositionMapHotspotX;
          panoExpositionMapHotspot.expositionMapHotspotY = mapHotspot.expositionMapHotspotY;
          panoExpositionMapHotspot.expositionMapHotspotUrl = mapHotspot.expositionMapHotspotUrl;
          createAudit(panoExpositionMapHotspot);
          panoExpositionMapHotspot01Mapper.insert(panoExpositionMapHotspot);

        }
      }
    }
    // 最后删除余下flg为1的热点
    panoExpositionMapHotspot01Mapper.deleteSpot(conditions);

  }
}
