package cn.com.pano.panovr.service.panovr01;

import java.util.HashMap;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Maps;
import cn.com.pano.pano.mapper.common01.PanoMaterial01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanorama01Mapper;
import cn.com.pano.pano.model.common.PanoPanorama;
import cn.com.pano.panovr.form.panovr01.PanoVr0104Form;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.web.BaseService;

@Service
@Transactional(rollbackFor = Throwable.class)
public class PanoVr0104UpdateService extends BaseService {

  @Autowired
  public PanoPanorama01Mapper panoPanorama01Mapper;
  @Autowired
  public PanoMaterial01Mapper panoMaterial01Mapper;

  /**
   * 更新场景信息
   * 
   * @param _inForm
   * @throws Exception
   */
  public String updateFirstSence(PanoVr0104Form _inForm) throws Exception {
    // 更新场景基本信息
    if (!ObjectUtils.isEmpty(_inForm.panoramaId)) {
      PanoPanorama panoPanorama = panoPanorama01Mapper.selectByPrimaryKey(_inForm.panoramaId);
      if (panoPanorama != null) {
        HashMap<String, Object> condition = Maps.newHashMap();
        condition.put("expositionId", _inForm.expositionId);
        // 检索全景图首图
        PanoPanorama startPanoPanorama = panoPanorama01Mapper.selectStartScenePanoInfo(condition);
        // 检索当前展览下无全景图首图
        if (startPanoPanorama != null) {
          // 该展览下已有首图,修改其首图标识为NO
          startPanoPanorama.startSceneFlag = FlagStatus.Disable.toString();
          updateAudit(startPanoPanorama);
          panoPanorama01Mapper.updateByPrimaryKey(startPanoPanorama);
        }
        panoPanorama.startSceneFlag = FlagStatus.Enable.toString();
        panoPanorama01Mapper.updateByPrimaryKey(panoPanorama);
        return "yes";
      }
    }
    return "false";
  }
}
