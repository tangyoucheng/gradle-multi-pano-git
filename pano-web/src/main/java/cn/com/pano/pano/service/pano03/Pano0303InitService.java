package cn.com.pano.pano.service.pano03;

import java.text.MessageFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.utils.FileServiceUtil;
import cn.com.pano.pano.common.utils.PanoCommonUtil;
import cn.com.pano.pano.form.pano03.Pano0303Form;
import cn.com.pano.pano.mapper.common01.PanoMaterial01Mapper;
import cn.com.pano.pano.model.common.PanoMaterial;
import cn.com.platform.framework.util.FwStringUtils;
import cn.com.platform.web.BaseService;

/**
 * 素材修改初始化
 * 
 * @author tangzhenzong
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0303InitService extends BaseService {

  @Autowired
  public PanoMaterial01Mapper panoMaterial01Mapper;

  /**
   * 素材种类单选框
   * 
   * @param _inForm
   * @throws Exception
   */
  public void doInit(Pano0303Form _inForm) throws Exception {

    // 生成随机的uploadKey
    _inForm.upload_key = FwStringUtils.getUniqueId();

    // _inForm.panoMaterialList = Lists.newArrayList();
    // _inForm.panoMaterialList.add(new CodeValueRecord("信息图",
    // PanoConstantsIF.MATERIAL_TYPE_IMAGE));
    // _inForm.panoMaterialList.add(new CodeValueRecord("场景切换图标",
    // PanoConstantsIF.MATERIAL_TYPE_HOTSPOT_CHANGE_SCENE));
    // _inForm.panoMaterialList.add(new CodeValueRecord("单点热点图标",
    // PanoConstantsIF.MATERIAL_TYPE_HOTSPOT_IMAGE));
    // _inForm.panoMaterialList.add(new CodeValueRecord("LOGO图",
    // PanoConstantsIF.MATERIAL_TYPE_HOTSPOT_LOGO));
    // _inForm.panoMaterialList.add(new CodeValueRecord("音乐", PanoConstantsIF.MATERIAL_TYPE_SOUND));
    // _inForm.panoMaterialList.add(new CodeValueRecord("视频", PanoConstantsIF.MATERIAL_TYPE_VIDEO));
    // _inForm.panoMaterialList.add(new CodeValueRecord("浮动信息(图片)",
    // PanoConstantsIF.MATERIAL_TYPE_FLOW_INFO_IMAGE));
    // _inForm.panoMaterialList.add(new CodeValueRecord("浮动信息(文字)",
    // PanoConstantsIF.MATERIAL_TYPE_FLOW_INFO_TEXT));
    // _inForm.panoMaterialList.add(new CodeValueRecord("图文",
    // PanoConstantsIF.MATERIAL_TYPE_IMAGE_TEXT));
    // 素材类型
    _inForm.panoMaterialList = PanoCommonUtil.getMateialTypeList(true);

    PanoMaterial panoMaterial = panoMaterial01Mapper.selectByPrimaryKey(_inForm.materialId);
    _inForm.materialTypeId = panoMaterial.materialTypeId;
    _inForm.notes = panoMaterial.notes;
    if (PanoConstantsIF.MATERIAL_TYPE_SOUND.equals(panoMaterial.materialTypeId)) {
      // 音频
      _inForm.oldmaterialPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_SOUND,
          PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
          panoMaterial.materialPath);
      String _srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_SOUND,
          PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
          _inForm.materialId + "/");
      String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_SOUND,
          PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
          _inForm.materialId + "/");
      FileServiceUtil.copyDirFromPublicStorageToAppServer(_srcPath, _destPath);
    } else if (PanoConstantsIF.MATERIAL_TYPE_VIDEO.equals(panoMaterial.materialTypeId)) {
      // 视频
      _inForm.oldmaterialPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_VIDEO,
          PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
          panoMaterial.materialPath);
      String _srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_VIDEO,
          PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
          _inForm.materialId + "/");
      String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_VIDEO,
          PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
          _inForm.materialId + "/");
      FileServiceUtil.copyDirFromPublicStorageToAppServer(_srcPath, _destPath);
    } else if (PanoConstantsIF.MATERIAL_TYPE_FLOW_INFO_TEXT.equals(_inForm.materialTypeId)) {
      // 文字素材
      _inForm.oldmaterialPath = "dummy";
      _inForm.oldTextflowInfo = panoMaterial.flowTextInfo;
    } else {
      _inForm.oldmaterialPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_IMAGE,
          PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
          panoMaterial.materialPath);
      String _srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_IMAGE,
          PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
          _inForm.materialId + "/");
      String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_IMAGE,
          PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
          _inForm.materialId + "/");
      FileServiceUtil.copyDirFromPublicStorageToAppServer(_srcPath, _destPath);
      // 图文时
      if (PanoConstantsIF.MATERIAL_TYPE_IMAGE_TEXT.equals(_inForm.materialTypeId)) {
        // 图文的文字信息
        _inForm.textInfo = panoMaterial.textInfo;
      }
    }
  }
}
