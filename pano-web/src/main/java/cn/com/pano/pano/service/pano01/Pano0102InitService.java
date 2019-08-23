package cn.com.pano.pano.service.pano01;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.pano.pano.common.utils.PanoCommonUtil;
import cn.com.pano.pano.form.pano01.Pano0102Form;
import cn.com.platform.web.BaseService;

/**
 * 展览一览画面处理初期化。
 * 
 * @author 唐友成
 * @date 2019-08-01
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0102InitService extends BaseService {


  /**
   * 初期化处理。
   * 
   * @param inForm Pano0102Form
   * @throws Exception 异常的场合
   */
  public void doInit(Pano0102Form inForm) throws Exception {
    // 展览状态下拉框内容
    inForm.expositionStatusList = PanoCommonUtil.getExpositionStatusList(true);
    // 展览类型下拉框内容
    inForm.expositionTypeList = PanoCommonUtil.getExpositionTypeList(true);

  }
}
