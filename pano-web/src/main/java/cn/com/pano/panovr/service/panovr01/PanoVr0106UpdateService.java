package cn.com.pano.panovr.service.panovr01;

import java.util.HashMap;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Maps;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.mapper.common01.PanoExpositionMap01Mapper;
import cn.com.pano.pano.model.common.PanoExpositionMap;
import cn.com.pano.panovr.form.panovr01.PanoVr0106Form;
import cn.com.pano.panovr.mapper.panovr01.PanoVr0106Mapper;
import cn.com.platform.web.BaseService;

@Service
@Transactional(rollbackFor = Throwable.class)
public class PanoVr0106UpdateService extends BaseService {

  @Autowired
  public PanoVr0106Mapper vr0106Mapper;
  @Autowired
  public PanoExpositionMap01Mapper panoExpositionMap01Mapper;

  public void doDeleteMap(PanoVr0106Form _inForm) throws Exception {


    if (!ObjectUtils.isEmpty(_inForm.expositionMapId)) {

      PanoExpositionMap map = panoExpositionMap01Mapper.selectByPrimaryKey(_inForm.expositionMapId);

      HashMap<String, Object> _conditions = Maps.newHashMap();
      _conditions.put("expositionMapId", _inForm.expositionMapId);

      // 删除展览地图上的热点
      vr0106Mapper.deleteMap(_conditions);

      // 更新展览地图使用状态
      if (map != null) {
        // 展览会地图使用状态 0：未使用、1：使用中
        map.expositionMapUseState = PanoConstantsIF.MAP_USE_STATE_NO;
        updateAudit(map);
        panoExpositionMap01Mapper.updateByPrimaryKey(map);
      }

    }
  }
}
