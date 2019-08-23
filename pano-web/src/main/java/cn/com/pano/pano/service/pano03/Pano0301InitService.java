package cn.com.pano.pano.service.pano03;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Lists;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.utils.PanoCommonUtil;
import cn.com.pano.pano.form.pano03.Pano0301Form;
import cn.com.platform.framework.common.CodeValueRecord;
import cn.com.platform.framework.util.FwStringUtils;
import cn.com.platform.web.BaseService;

/**
 * 素材新建画面初期显示。
 * 
 * @author tangzhenzong
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0301InitService extends BaseService {

  /**
   * 生成JSP页面上的radio列表。
   * 
   * @param inForm Pano0301Form
   */
  public void doInit(Pano0301Form inForm) throws Exception {
    inForm.materialId = FwStringUtils.getUniqueId();
    // 素材类型
    inForm.materialTypeList = PanoCommonUtil.getMateialTypeList(false);

    inForm.materialBelongTypeList = Lists.newArrayList();
    inForm.materialBelongTypeList
        .add(new CodeValueRecord(PanoConstantsIF.MATERIAL_BELONGTYPE_EXPOSITION, "当前展览素材"));
    inForm.materialBelongTypeList
        .add(new CodeValueRecord(PanoConstantsIF.MATERIAL_BELONGTYPE_COMMON, "公用素材"));
    inForm.materialBelongType = PanoConstantsIF.MATERIAL_BELONGTYPE_COMMON;

  }
}
