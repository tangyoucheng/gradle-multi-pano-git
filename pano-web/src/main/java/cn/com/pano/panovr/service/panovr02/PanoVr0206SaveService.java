package cn.com.pano.panovr.service.panovr02;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.pano.pano.mapper.common01.PanoPanorama01Mapper;
import cn.com.pano.pano.model.common.PanoPanorama;
import cn.com.pano.panovr.form.panovr02.PanoVr0206Form;
import cn.com.platform.framework.util.FwJsonUtil;
import cn.com.platform.web.BaseService;

/**
 * 保存场景显示顺序的状态
 * 
 * @author ouyangzidou
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class PanoVr0206SaveService extends BaseService {
  @Autowired
  public PanoPanorama01Mapper panoPanorama01Mapper;

  /**
   * 保存场景显示顺序
   * 
   * @param _inForm
   * @throws Exception
   */
  public void doSave(PanoVr0206Form _inForm) throws Exception {
    List<PanoPanorama> panoramaInfoList =
        FwJsonUtil.jsonToList(_inForm.panoramaInfoJson, PanoPanorama.class);

    if (panoramaInfoList != null) {
      for (PanoPanorama _panoPanorama : panoramaInfoList) {
        // 更新场景的顺序信息
        PanoPanorama panoPanorama = panoPanorama01Mapper.selectByPrimaryKey(_panoPanorama.panoramaId);
        panoPanorama.panoramaSortKey = _panoPanorama.panoramaSortKey;
        updateAudit(panoPanorama);
        panoPanorama01Mapper.updateByPrimaryKey(panoPanorama);
      }
    }
  }
}
