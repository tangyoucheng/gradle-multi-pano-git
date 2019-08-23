package cn.com.pano.panovr.service.panovr03;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Maps;
import cn.com.pano.panovr.form.panovr03.PanoVr0307Form;
import cn.com.pano.panovr.mapper.panovr03.PanoVr0307Mapper;
import cn.com.platform.web.BaseService;

/**
 * 全景场景一览的初期显示处理
 * 
 * @author ouyangzidou
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class PanoVr0307InitService extends BaseService {

  @Autowired
  public PanoVr0307Mapper vr0307Mapper;

  /**
   * 检索数据库中的全景场景，取得结果集
   * 
   * @param _inForm
   * @throws Exception
   */
  public void doInit(PanoVr0307Form _inForm) throws Exception {
    // 检索全景图信息详细
    HashMap<String, Object> condition = Maps.newHashMap();
    condition.put("expositionId", _inForm.expositionId);
    int resultCount = vr0307Mapper.selectPanoramaInfoCount(condition);
    checkCount(_inForm, resultCount);
    _inForm.panoramaInfo = vr0307Mapper.selectPanoramaInfo(_inForm.pageStartRowNo, condition);

  }
}
