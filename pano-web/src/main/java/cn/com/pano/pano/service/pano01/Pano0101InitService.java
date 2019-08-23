package cn.com.pano.pano.service.pano01;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.pano.pano.common.code.ExpositionStatus;
import cn.com.pano.pano.common.code.ExpositionType;
import cn.com.pano.pano.common.utils.PanoCommonUtil;
import cn.com.pano.pano.form.pano01.Pano0101Form;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.framework.util.FwStringUtils;
import cn.com.platform.web.BaseService;


/**
 * 展览创建页面初期化处理。
 * 
 * @author 唐友成
 * @date 2019-08-05
 *
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0101InitService extends BaseService {

  /**
   * 生成JSP页面上的radio列表。
   * 
   * @param inForm Pano0101Form
   * @throws Exception 异常的场合
   */
  public void doInit(Pano0101Form inForm) throws Exception {
    if (ObjectUtils.isEmpty(inForm.expositionId)) {
      inForm.expositionId = FwStringUtils.getUniqueId();
    }

    // 展览类型radioBox
    inForm.expositionTypeList = PanoCommonUtil.getCodeMessageList(ExpositionType.class, false);
    inForm.expositionType = ExpositionType.OUTPUT.toString();

    // 展览状态radioBox
    inForm.expositionStatusList = PanoCommonUtil.getCodeMessageList(ExpositionStatus.class, false);;

    // 展览模式radioBox
    inForm.vrFlagList = PanoCommonUtil.getVrTypeList(false);
    inForm.vrFlag = FlagStatus.Disable.toString();

    // 展览状态初期化默认为：规划中
    inForm.expositionStatus = ExpositionStatus.PLANNING.toString();
  }
}
