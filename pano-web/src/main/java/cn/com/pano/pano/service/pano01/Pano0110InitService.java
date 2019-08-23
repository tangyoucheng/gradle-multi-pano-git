package cn.com.pano.pano.service.pano01;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Lists;
import cn.com.pano.pano.common.code.ExpositionStatus;
import cn.com.pano.pano.common.utils.PanoCommonUtil;
import cn.com.pano.pano.form.pano01.Pano0110Form;
import cn.com.platform.framework.common.CodeValueRecord;
import cn.com.platform.framework.util.FwCodeUtil;
import cn.com.platform.util.MessageUtils;
import cn.com.platform.web.BaseService;

/**
 * 
 * @author yangyuzhen
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0110InitService extends BaseService {
  /**
   * 
   * @param pano0110Form
   * @throws Exception
   */
  public void doInit(Pano0110Form inForm) throws Exception {
    // 展览状态下拉框内容
    inForm.expositionStatusList = PanoCommonUtil.getExpositionStatusList(true);
    // 展览类型下拉框内容
    inForm.expositionTypeList = PanoCommonUtil.getExpositionTypeList(true);

  }
}
