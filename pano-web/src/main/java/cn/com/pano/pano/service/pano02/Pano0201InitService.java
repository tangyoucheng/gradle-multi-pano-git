package cn.com.pano.pano.service.pano02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.pano.pano.form.pano02.Pano0201Form;
import cn.com.pano.pano.mapper.common01.PanoPanorama01Mapper;
import cn.com.platform.framework.util.FwStringUtils;
import cn.com.platform.web.BaseService;

/**
 * 新建首个全景图初期化。
 * 
 * @author ouyangzidu
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0201InitService extends BaseService {

  @Autowired
  public PanoPanorama01Mapper panoPanorama01Mapper;

  /**
   * 初期化处理。
   * 
   * @param inForm Pano0201Form
   * @throws Exception 异常的场合
   */
  public void doInit(Pano0201Form inForm) throws Exception {
    inForm.panoramaId = FwStringUtils.getUniqueId();
    // HashMap<String, Object> condition = Maps.newHashMap();
    // condition.put("expositionId", _inForm.expositionId);
    // // 取得当前展览下场景个数生成默认序号
    // List<PanoPanorama> list = panoPanorama01Mapper.findByExpositionId(condition);
    // if (list != null && !list.isEmpty()) {
    // // 如果已有场景，设置默认序号为场景个数加1
    // int count = list.size() + 1;
    // BigDecimal bigDecimal = new BigDecimal(count);
    // _inForm.panoramaSortKey = bigDecimal;
    // } else {
    // // 如果没有场景，设置序号为1
    // BigDecimal bigDecimal = new BigDecimal(1);
    // _inForm.panoramaSortKey = bigDecimal;
    // }

  }
}
