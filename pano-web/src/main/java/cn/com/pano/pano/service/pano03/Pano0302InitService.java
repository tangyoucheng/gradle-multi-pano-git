package cn.com.pano.pano.service.pano03;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Lists;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.utils.PanoCommonUtil;
import cn.com.pano.pano.form.pano03.Pano0302Form;
import cn.com.platform.framework.common.CodeValueRecord;
import cn.com.platform.web.BaseService;

/**
 * 素材一览初始化。
 * 
 * @author tangzhenzong
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0302InitService extends BaseService {

  /**
   * 热点种类下拉框初始化。
   * 
   * @param inForm Pano0302Form
   */
  public void doInit(Pano0302Form inForm) throws Exception {
    // 素材类型
    inForm.materialTypeSelectInfo = PanoCommonUtil.getMateialTypeList(true);

    // 素材归属种类
    inForm.materialBelongTypeList = Lists.newArrayList();
    inForm.materialBelongTypeList
        .add(new CodeValueRecord(PanoConstantsIF.MATERIAL_BELONGTYPE_EXPOSITION, "当前展览素材"));
    inForm.materialBelongTypeList
        .add(new CodeValueRecord(PanoConstantsIF.MATERIAL_BELONGTYPE_COMMON, "公用素材"));
    inForm.materialBelongType = PanoConstantsIF.MATERIAL_BELONGTYPE_COMMON;

  }
}
