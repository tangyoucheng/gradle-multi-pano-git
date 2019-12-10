package cn.com.pano.pano.service.pano03;

import java.text.MessageFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Lists;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.utils.FileServiceUtil;
import cn.com.pano.pano.common.utils.PanoCommonUtil;
import cn.com.pano.pano.form.pano03.Pano0303Form;
import cn.com.pano.pano.mapper.common01.PanoMaterial01Mapper;
import cn.com.pano.pano.model.common.PanoMaterial;
import cn.com.platform.framework.common.CodeValueRecord;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.framework.util.FwStringUtils;
import cn.com.platform.web.BaseService;

/**
 * 素材修改初始化。
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
   * 素材种类单选框。
   * 
   * @param inForm 表单数据
   * @throws Exception 异常的场合
   */
  public void doInit(Pano0303Form inForm) throws Exception {

    // 生成随机的uploadKey
    // inForm.panoMaterialList = Lists.newArrayList();
    // inForm.panoMaterialList.add(new CodeValueRecord("信息图",
    // PanoConstantsIF.MATERIAL_TYPE_IMAGE));
    // inForm.panoMaterialList.add(new CodeValueRecord("场景切换图标",
    // PanoConstantsIF.MATERIAL_TYPE_HOTSPOT_CHANGE_SCENE));
    // inForm.panoMaterialList.add(new CodeValueRecord("单点热点图标",
    // PanoConstantsIF.MATERIAL_TYPE_HOTSPOT_IMAGE));
    // inForm.panoMaterialList.add(new CodeValueRecord("LOGO图",
    // PanoConstantsIF.MATERIAL_TYPE_HOTSPOT_LOGO));
    // inForm.panoMaterialList.add(new CodeValueRecord("音乐", PanoConstantsIF.MATERIAL_TYPE_SOUND));
    // inForm.panoMaterialList.add(new CodeValueRecord("视频", PanoConstantsIF.MATERIAL_TYPE_VIDEO));
    // inForm.panoMaterialList.add(new CodeValueRecord("浮动信息(图片)",
    // PanoConstantsIF.MATERIAL_TYPE_FLOW_INFO_IMAGE));
    // inForm.panoMaterialList.add(new CodeValueRecord("浮动信息(文字)",
    // PanoConstantsIF.MATERIAL_TYPE_FLOW_INFO_TEXT));
    // inForm.panoMaterialList.add(new CodeValueRecord("图文",
    // PanoConstantsIF.MATERIAL_TYPE_IMAGE_TEXT));
    // 素材类型
    inForm.materialTypeList = PanoCommonUtil.getMateialTypeList(true);

    inForm.materialBelongTypeList = Lists.newArrayList();
    inForm.materialBelongTypeList
        .add(new CodeValueRecord(PanoConstantsIF.MATERIAL_BELONGTYPE_EXPOSITION, "当前展览素材"));
    inForm.materialBelongTypeList
        .add(new CodeValueRecord(PanoConstantsIF.MATERIAL_BELONGTYPE_COMMON, "公用素材"));
    inForm.materialBelongType = PanoConstantsIF.MATERIAL_BELONGTYPE_COMMON;

    PanoMaterial panoMaterial = panoMaterial01Mapper.selectByPrimaryKey(inForm.materialId);
    inForm.materialTypeId = panoMaterial.materialTypeId;
    inForm.notes = panoMaterial.notes;
    if (PanoConstantsIF.MATERIAL_TYPE_SOUND.equals(panoMaterial.materialTypeId)) {
      // 音频
      inForm.oldmaterialPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_SOUND,
          PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
          panoMaterial.materialPath);
      String srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_SOUND,
          PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
          inForm.materialId + "/");
      String destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_SOUND,
          PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
          inForm.materialId + "/");
      FileServiceUtil.copyDirFromPublicStorageToAppServer(srcPath, destPath);
    } else if (PanoConstantsIF.MATERIAL_TYPE_VIDEO.equals(panoMaterial.materialTypeId)) {
      // 视频
      inForm.oldmaterialPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_VIDEO,
          PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
          panoMaterial.materialPath);
      String srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_VIDEO,
          PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
          inForm.materialId + "/");
      String destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_VIDEO,
          PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
          inForm.materialId + "/");
      FileServiceUtil.copyDirFromPublicStorageToAppServer(srcPath, destPath);
    } else if (PanoConstantsIF.MATERIAL_TYPE_FLOW_INFO_TEXT.equals(inForm.materialTypeId)) {
      // 文字素材
      inForm.oldmaterialPath = "dummy";
      inForm.oldTextflowInfo = panoMaterial.flowTextInfo;
    } else {

      inForm.oldmaterialPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W,
          UserSessionUtils.getSessionId(), panoMaterial.materialPath);

      inForm.oldGifWidth = panoMaterial.gifWidth;
      inForm.oldGifHeight = panoMaterial.gifHeight;
      inForm.oldGifFrameCount = panoMaterial.gifFrameCount;
      inForm.oldGifDelayTime = panoMaterial.gifDelayTime;

      // 图文时
      if (PanoConstantsIF.MATERIAL_TYPE_IMAGE_TEXT.equals(inForm.materialTypeId)) {
        // 图文的文字信息
        inForm.oldTextInfo = panoMaterial.textInfo;
      }
    }
  }
}
