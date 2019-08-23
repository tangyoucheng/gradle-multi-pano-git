package cn.com.pano.pano.service.pano02;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.NumberUtils;
import com.google.common.collect.Lists;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.code.HotspotType;
import cn.com.pano.pano.common.code.MaterialType;
import cn.com.pano.pano.common.utils.FileServiceUtil;
import cn.com.pano.pano.dto.pano02.Pano0208Dto;
import cn.com.pano.pano.form.pano02.Pano0208Form;
import cn.com.pano.pano.mapper.common01.PanoHotspotUrl01Mapper;
import cn.com.pano.pano.mapper.common01.PanoMaterial01Mapper;
import cn.com.pano.pano.model.common.PanoMaterial;
import cn.com.platform.framework.common.CodeValueRecord;
import cn.com.platform.framework.util.FwCodeUtil;
import cn.com.platform.util.MessageUtils;
import cn.com.platform.web.BaseService;

/**
 * 热点素材选择画面初期化处理。
 * 
 * @author tangzhenzong
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0208InitService extends BaseService {

  @Autowired
  public PanoMaterial01Mapper panoMaterial01Mapper;
  @Autowired
  public PanoHotspotUrl01Mapper panoHotspotUrl01Mapper;

  /**
   * 热点种类单选框初始化。
   * 
   * @param inForm Pano0208Form
   */
  public void doInit(Pano0208Form inForm) throws Exception {

    // 素材种类
    // inForm.panoMaterialList = Lists.newArrayList();
    // inForm.panoMaterialList.add(new CodeValueRecord(MaterialType.IMAGE.toString(), "平面图"));
    // inForm.panoMaterialList
    // .add(new CodeValueRecord(MaterialType.HOTSPOT_CHANGE_SCENE.toString(), "场景切换图"));
    // inForm.panoMaterialList
    // .add(new CodeValueRecord(MaterialType.HOTSPOT_IMAGE.toString(), "普通热点图"));

    // 素材归属种类
    inForm.materialBelongTypeList = Lists.newArrayList();
    inForm.materialBelongTypeList
        .add(new CodeValueRecord(PanoConstantsIF.MATERIAL_BELONGTYPE_EXPOSITION, "当前展览素材"));
    inForm.materialBelongTypeList
        .add(new CodeValueRecord(PanoConstantsIF.MATERIAL_BELONGTYPE_COMMON, "公用素材"));
    inForm.materialBelongType = PanoConstantsIF.MATERIAL_BELONGTYPE_COMMON;

    // 热点类别
    inForm.hotspotTypeChoiceList = Lists.newArrayList();
    if (ObjectUtils.isNotEmpty(inForm.hotspotOldType)) {
      MaterialType materialType =
          FwCodeUtil.stringToEnum(MaterialType.class, inForm.hotspotOldType);
      switch (materialType) {
        case HOTSPOT_CHANGE_SCENE:
          inForm.hotspotTypeChoiceList.add(new CodeValueRecord(inForm.hotspotOldType,
              MessageUtils.getMessage(materialType.getMessageId())));
          break;
        case HOTSPOT_IMAGE:
          inForm.hotspotTypeChoiceList.add(new CodeValueRecord(inForm.hotspotOldType,
              MessageUtils.getMessage(materialType.getMessageId())));
          break;
        case HOTSPOT_LOGO:
          inForm.hotspotTypeChoiceList.add(new CodeValueRecord(inForm.hotspotOldType,
              MessageUtils.getMessage(materialType.getMessageId())));
          break;
        case FLOW_INFO_IMAGE:
          inForm.hotspotTypeChoiceList.add(new CodeValueRecord(inForm.hotspotOldType,
              MessageUtils.getMessage(materialType.getMessageId())));
          break;
        case FLOW_INFO_TEXT:
          inForm.hotspotTypeChoiceList.add(new CodeValueRecord(inForm.hotspotOldType,
              MessageUtils.getMessage(materialType.getMessageId())));
          break;
        default:
          break;
      }
      inForm.hotspotTypeChoice = inForm.hotspotOldType;
    } else {
      inForm.hotspotTypeChoiceList
          .add(new CodeValueRecord(MaterialType.HOTSPOT_CHANGE_SCENE.toString(), "场景切换"));
      inForm.hotspotTypeChoiceList
          .add(new CodeValueRecord(MaterialType.HOTSPOT_IMAGE.toString(), "普通热点"));
      inForm.hotspotTypeChoiceList
          .add(new CodeValueRecord(MaterialType.HOTSPOT_LOGO.toString(), "LOGO热点"));
      inForm.hotspotTypeChoice = MaterialType.HOTSPOT_CHANGE_SCENE.toString();
    }


    if (PanoConstantsIF.HOTSPOT_TYPE_HOTSPOT_MUSIC.equals(inForm.hotspotTypeChoice)) {
      inForm.existedMaterialInfo = new ArrayList<Pano0208Dto>();
      PanoMaterial panoMaterial_1 =
          panoMaterial01Mapper.selectByPrimaryKey(inForm.firsthotspotImageIdPano0203);
      PanoMaterial panoMaterial_2 =
          panoMaterial01Mapper.selectByPrimaryKey(inForm.seconfhotspotImageIdPano0203);
      if (panoMaterial_1 != null) {
        // 取得该热点第一张图
        panoMaterial_1.materialPath =
            MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_IMAGE,
                PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial_1.expositionId,
                panoMaterial_1.materialPath);

        String _srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_IMAGE,
            PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial_1.expositionId,
            inForm.selectedMaterialId + "/");

        String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_IMAGE,
            PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial_1.expositionId,
            inForm.selectedMaterialId + "/");
        FileServiceUtil.copyDirFromPublicStorageToAppServer(_srcPath, _destPath);
        Pano0208Dto pano0208dto = new Pano0208Dto();
        // 检索到sortKey和相关信息
        pano0208dto.sortKey =
            NumberUtils.parseNumber(inForm.firstSortKeyPano0203, BigDecimal.class);
        pano0208dto.materialPath = panoMaterial_1.materialPath;
        pano0208dto.materialId = panoMaterial_1.materialId;
        pano0208dto.materialName = panoMaterial_1.materialName;
        pano0208dto.notes = panoMaterial_1.notes;
        inForm.existedMaterialInfo.add(pano0208dto);
      }
      if (panoMaterial_2 != null) {
        // 取得该热点第二张图
        panoMaterial_2.materialPath =
            MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_IMAGE,
                PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial_2.expositionId,
                panoMaterial_2.materialPath);

        String _srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_IMAGE,
            PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial_2.expositionId,
            inForm.selectedMaterialId + "/");

        String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_IMAGE,
            PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial_2.expositionId,
            inForm.selectedMaterialId + "/");
        FileServiceUtil.copyDirFromPublicStorageToAppServer(_srcPath, _destPath);
        Pano0208Dto pano0208dto = new Pano0208Dto();
        // 检索到sortKey和相关信息
        pano0208dto.sortKey =
            NumberUtils.parseNumber(inForm.secondSortKeyPano0203, BigDecimal.class);
        pano0208dto.materialPath = panoMaterial_2.materialPath;
        pano0208dto.materialId = panoMaterial_2.materialId;
        pano0208dto.materialName = panoMaterial_2.materialName;
        pano0208dto.notes = panoMaterial_2.notes;
        inForm.existedMaterialInfo.add(pano0208dto);
      }
    }

  }

}
