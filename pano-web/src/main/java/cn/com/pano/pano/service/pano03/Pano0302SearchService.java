package cn.com.pano.pano.service.pano03;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Maps;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.utils.FileServiceUtil;
import cn.com.pano.pano.common.utils.PanoCommonUtil;
import cn.com.pano.pano.form.pano03.Pano0302Form;
import cn.com.pano.pano.mapper.pano03.Pano0302Mapper;
import cn.com.pano.pano.model.common01.PanoMaterial01Model;
import cn.com.platform.framework.common.CodeValueRecord;
import cn.com.platform.framework.util.FwStringUtils;
import cn.com.platform.web.BaseService;
import cn.com.platform.web.view.PagerCommon;
import cn.com.platform.web.view.PagerForm;

/**
 * 检索素材信息
 * 
 * @author tangzhenzong
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0302SearchService extends BaseService {
  @Autowired
  public Pano0302Mapper  pano0302Mapper;

  /**
   * 素材检索处理
   * 
   * @param _inForm
   * @return
   * @throws Exception
   */
  public String searchMaterial(Pano0302Form _inForm) throws Exception {
    String searchResult = "";
    // 从数据库中检索素材信息
    HashMap<String, Object> condition = Maps.newHashMap();
    condition.put("materialId", FwStringUtils.getMatchParameter(_inForm.materialId));
    condition.put("materialName", FwStringUtils.getMatchParameter(_inForm.materialName));
    condition.put("materialTypeId", _inForm.materialTypeId);

    // 素材归属
    String _materialFolderPath = "common_material";
    if (!ObjectUtils.isEmpty(_inForm.materialBelongType)
        && PanoConstantsIF.MATERIAL_BELONGTYPE_EXPOSITION.equals(_inForm.materialBelongType)) {
      _materialFolderPath = _inForm.expositionId;
    }
    condition.put("expositionId", _materialFolderPath);

    int resultCount =  pano0302Mapper.selectMaterialInfoCount(condition);
    checkCount(_inForm, resultCount);

    // 再次设定类型对应字段
    _inForm.materialTypeMap = new HashMap<String, String>();
    // _inForm.materialTypeMap.put(PanoConstantsIF.MATERIAL_TYPE_IMAGE, "信息图");
    // _inForm.materialTypeMap.put(PanoConstantsIF.MATERIAL_TYPE_HOTSPOT_CHANGE_SCENE, "场景切换图标");
    // _inForm.materialTypeMap.put(PanoConstantsIF.MATERIAL_TYPE_HOTSPOT_IMAGE, "单点热点图标");
    // _inForm.materialTypeMap.put(PanoConstantsIF.MATERIAL_TYPE_HOTSPOT_LOGO, "LOGO热点图");
    // _inForm.materialTypeMap.put(PanoConstantsIF.MATERIAL_TYPE_SOUND, "音乐");
    // _inForm.materialTypeMap.put(PanoConstantsIF.MATERIAL_TYPE_VIDEO, "视频");
    // _inForm.materialTypeMap.put(PanoConstantsIF.MATERIAL_TYPE_FLOW_INFO_IMAGE, "浮动信息(图片)");
    // _inForm.materialTypeMap.put(PanoConstantsIF.MATERIAL_TYPE_FLOW_INFO_TEXT, "浮动信息(文字)");
    // _inForm.materialTypeMap.put(PanoConstantsIF.MATERIAL_TYPE_IMAGE_TEXT, "图文");
    List<CodeValueRecord> dataList = PanoCommonUtil.getMateialTypeList(true);
    for (CodeValueRecord data : dataList) {
      _inForm.materialTypeMap.put(data.getRecordCode(), data.getRecordValue());
    }

    // 翻页信息取得
    PagerForm pagerForm = new PagerForm();
    pagerForm.setFormId(" pano0302Form");
    pagerForm.setFormAction("");
    pagerForm.setPageSize(Objects.toString(_inForm.pageSize));
    pagerForm.setPageStartRowNo(Objects.toString(_inForm.pageStartRowNo));
    pagerForm.setRecordCount(Objects.toString(_inForm.recordCount));
    pagerForm.setUniqueFlag(true);
    pagerForm.setAjaxPageMethod("doAjaxPage");
    PagerCommon pagerCommon = new PagerCommon();
    _inForm.pageShowInfos = pagerCommon.doEditPageInfo(pagerForm);

    // 素材详细信息取得
    _inForm.materialInfo =  pano0302Mapper.selectMaterialInfo(condition, _inForm.pageStartRowNo);

    if (_inForm.materialInfo != null) {

      for (PanoMaterial01Model panoMaterial : _inForm.materialInfo) {

        panoMaterial.materialTypeId = _inForm.materialTypeMap.get(panoMaterial.materialTypeId);

        panoMaterial.materialPath =
            MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_IMAGE,
                PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
                panoMaterial.materialPath);

        String _srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_IMAGE,
            PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
            panoMaterial.materialId + "/");

        String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_IMAGE,
            PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
            panoMaterial.materialId + "/");
        FileServiceUtil.copyDirFromPublicStorageToAppServer(_srcPath, _destPath);
      }

    }

    searchResult = objectMapper.writeValueAsString(_inForm);

    return searchResult;
  }
}
