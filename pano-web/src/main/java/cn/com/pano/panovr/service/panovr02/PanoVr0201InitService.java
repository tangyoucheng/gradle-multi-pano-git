package cn.com.pano.panovr.service.panovr02;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Maps;
import cn.com.pano.pano.mapper.common01.PanoPanorama01Mapper;
import cn.com.pano.pano.model.common.PanoPanorama;
import cn.com.pano.panovr.form.panovr02.PanoVr0201Form;
import cn.com.platform.framework.util.FwStringUtils;
import cn.com.platform.web.BaseService;

/**
 * 新建首个全景图初期化
 * 
 * @author ouyangzidu
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class PanoVr0201InitService extends BaseService {

  @Autowired
  public PanoPanorama01Mapper panoPanorama01Mapper;

  /**
   * 
   * @param _inForm
   * @throws Exception
   */
  public void doInit(PanoVr0201Form _inForm) throws Exception {
    _inForm.panoramaId = FwStringUtils.getUniqueId();
    HashMap<String, Object> condition = Maps.newHashMap();
    condition.put("expositionId", _inForm.expositionId);
    // 取得当前展览下场景个数生成默认序号
    List<PanoPanorama> list = panoPanorama01Mapper.findByExpositionId(condition);
    if (list != null && !list.isEmpty()) {
      // 如果已有场景，设置默认序号为场景个数加1
      int count = list.size() + 1;
      BigDecimal bigDecimal = new BigDecimal(count);
      _inForm.panoramaSortKey = bigDecimal;
    } else {
      // 如果没有场景，设置序号为1
      BigDecimal bigDecimal = new BigDecimal(1);
      _inForm.panoramaSortKey = bigDecimal;
    }

  }
}
