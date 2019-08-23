package cn.com.pano.pano.service.pano03;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Lists;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.utils.PanoCommonUtil;
import cn.com.pano.pano.form.pano03.Pano0302Form;
import cn.com.platform.framework.common.CodeValueRecord;
import cn.com.platform.web.BaseService;

/**
 * 素材一览初始化
 * 
 * @author tangzhenzong
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0302InitService extends BaseService {
  /**
   * 热点种类下拉框初始化
   * 
   * @param _inForm
   */
  public void doInit(Pano0302Form _inForm) throws Exception {
    // 下拉框内容
    // _inForm.materialTypeSelectInfo = Lists.newArrayList();
    // _inForm.materialTypeSelectInfo.add(new CodeValueRecord("全部", ""));
    // _inForm.materialTypeSelectInfo.add(new CodeValueRecord("信息图",
    // PanoConstantsIF.MATERIAL_TYPE_IMAGE));
    // _inForm.materialTypeSelectInfo.add(new CodeValueRecord("LOGO图",
    // PanoConstantsIF.MATERIAL_TYPE_HOTSPOT_LOGO));
    // _inForm.materialTypeSelectInfo.add(new CodeValueRecord("场景切换图标",
    // PanoConstantsIF.MATERIAL_TYPE_HOTSPOT_CHANGE_SCENE));
    // _inForm.materialTypeSelectInfo.add(new CodeValueRecord("单点热点图标",
    // PanoConstantsIF.MATERIAL_TYPE_HOTSPOT_IMAGE));
    // _inForm.materialTypeSelectInfo.add(new CodeValueRecord("音乐",
    // PanoConstantsIF.MATERIAL_TYPE_SOUND));
    // _inForm.materialTypeSelectInfo.add(new CodeValueRecord("视频",
    // PanoConstantsIF.MATERIAL_TYPE_VIDEO));
    // _inForm.materialTypeSelectInfo
    // .add(new CodeValueRecord("浮动信息(图片)", PanoConstantsIF.MATERIAL_TYPE_FLOW_INFO_IMAGE));
    // _inForm.materialTypeSelectInfo
    // .add(new CodeValueRecord("浮动信息(文字)", PanoConstantsIF.MATERIAL_TYPE_FLOW_INFO_TEXT));
    // _inForm.materialTypeSelectInfo.add(new CodeValueRecord("图文",
    // PanoConstantsIF.MATERIAL_TYPE_IMAGE_TEXT));
    // 素材类型
    _inForm.materialTypeSelectInfo = PanoCommonUtil.getMateialTypeList(true);

    _inForm.materialBelongTypeList = Lists.newArrayList();
    _inForm.materialBelongTypeList
        .add(new CodeValueRecord("当前展览素材", PanoConstantsIF.MATERIAL_BELONGTYPE_EXPOSITION));
    _inForm.materialBelongTypeList
        .add(new CodeValueRecord("公用素材", PanoConstantsIF.MATERIAL_BELONGTYPE_COMMON));

    // 由展览编辑内打开素材一览时，设置默认显示展览素材
    if (!ObjectUtils.isEmpty(_inForm.openFromPano0104)) {
      _inForm.materialBelongType = PanoConstantsIF.MATERIAL_BELONGTYPE_EXPOSITION;
    }
  }
}
