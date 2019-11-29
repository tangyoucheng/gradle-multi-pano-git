package cn.com.pano.pano.service.pano02;

import java.text.MessageFormat;
import java.util.HashMap;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Maps;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.utils.FileServiceUtil;
import cn.com.pano.pano.dto.pano02.Pano0208Dto;
import cn.com.pano.pano.form.pano02.Pano0208Form;
import cn.com.pano.pano.mapper.common01.PanoMaterial01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanoramaHotspot01Mapper;
import cn.com.pano.pano.mapper.pano02.Pano0208Mapper;
import cn.com.pano.pano.model.common.PanoMaterial;
import cn.com.pano.pano.model.common01.PanoMaterial01Model;
import cn.com.platform.framework.util.FwStringUtils;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 检索热点素材信息。
 * 
 * @author tangzhenzong
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0208SearchService extends BaseService {

  @Autowired
  public Pano0208Mapper pano0208Mapper;
  @Autowired
  public PanoMaterial01Mapper panoMaterial01Mapper;
  @Autowired
  public PanoPanoramaHotspot01Mapper panoPanoramaHotspot01Mapper;

  /**
   * 素材查询处理。
   * 
   * @param inForm Pano0208Form
   * @return
   */
  public EasyJson<Object> searchMaterial(Pano0208Form inForm) throws Exception {
    // 从数据库检索素材信息
    HashMap<String, Object> condition = Maps.newHashMap();
    condition.put("materialTypeId", inForm.hotspotTypeChoice);
    condition.put("materialId", FwStringUtils.getMatchParameter(inForm.materialId));
    condition.put("materialName", FwStringUtils.getMatchParameter(inForm.materialName));
    // 素材归属
    String materialFolderPath = "common_material";
    if (!ObjectUtils.isEmpty(inForm.materialBelongType)
        && PanoConstantsIF.MATERIAL_BELONGTYPE_EXPOSITION.equals(inForm.materialBelongType)) {
      materialFolderPath = inForm.expositionId;
    }
    condition.put("expositionId", materialFolderPath);
    // 数据索引
    condition.put("offSet", (inForm.pageNumber - 1) * inForm.pageSize);
    // 每页的条数
    condition.put("pageSize", inForm.pageSize);

    long resultCount = pano0208Mapper.selectMaterialInfoCount(condition);

    // 素材信息详细
    inForm.materialInfo = pano0208Mapper.selectMaterialInfo(condition);
    if (inForm.materialInfo != null) {
      for (PanoMaterial01Model panoMaterial01Model : inForm.materialInfo) {
        // 判断该素材是否是gif图并是否有生产对应的png图
        panoMaterial01Model.hasPngImage = "false";
        if (!ObjectUtils.isEmpty(panoMaterial01Model.gifDelayTime)) {
          panoMaterial01Model.hasPngImage = "true";
        }
      }
    }
    
    // if (inForm.materialInfo != null) {
    // for (PanoMaterial01Model pano0208Dto : inForm.materialInfo) {
    // String _srcPath;
    // String _destPath;
    // // 如果是音频素材
    // if (PanoConstantsIF.MATERIAL_TYPE_SOUND.equals(inForm.materialTypeId)) {
    // _srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_SOUND,
    // PanoConstantsIF.MATERIAL_FOLDER_NAME + pano0208Dto.expositionId,
    // pano0208Dto.materialId + "/");
    // _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_SOUND,
    // PanoConstantsIF.MATERIAL_FOLDER_NAME + pano0208Dto.expositionId,
    // pano0208Dto.materialId + "/");
    //
    // FileServiceUtil.copyDirFromPublicStorageToAppServer(_srcPath, _destPath);
    //
    // } else if (PanoConstantsIF.MATERIAL_TYPE_FLOW_INFO_TEXT.equals(inForm.materialTypeId)) {
    // // 文字素材
    // pano0208Dto.materialPath = "dummy";
    // } else {
    // // 图片素材
    //
    // _srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_IMAGE,
    // PanoConstantsIF.MATERIAL_FOLDER_NAME + pano0208Dto.expositionId,
    // pano0208Dto.materialId + "/");
    //
    // _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_IMAGE,
    // PanoConstantsIF.MATERIAL_FOLDER_NAME + pano0208Dto.expositionId,
    // pano0208Dto.materialId + "/");
    // FileServiceUtil.copyDirFromPublicStorageToAppServer(_srcPath, _destPath);
    //
    // }
    //
    // // 判断该素材是否是gif图并是否有生产对应的png图
    // pano0208Dto.hasPngImage = "false";
    // if (!ObjectUtils.isEmpty(pano0208Dto.gifDelayTime)) {
    // pano0208Dto.hasPngImage = "true";
    // }
    // }
    // }

    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setTotal(resultCount);
    easyJson.setRows(inForm.materialInfo);;
    return easyJson;

  }

  /**
   * 得到素材Id和path等(单点热点种类为音频)。
   * 
   * @param inForm Pano0208Form
   * @return
   */
  public String doGetSelectedMaterialInfo(Pano0208Form inForm) throws Exception {
    Pano0208Dto pano0208Dto = new Pano0208Dto();
    pano0208Dto.hasPngImage = "false";
    PanoMaterial panoMaterial = panoMaterial01Mapper.selectByPrimaryKey(inForm.selectedMaterialId);
    pano0208Dto.materialPath = panoMaterial.materialPath;
    pano0208Dto.materialTypeId = panoMaterial.materialTypeId;
    pano0208Dto.expositionId = panoMaterial.expositionId;
    // 判断是否是gif图
    if (!ObjectUtils.isEmpty(panoMaterial.gifDelayTime)) {
      pano0208Dto.gifWidth = panoMaterial.gifWidth;
      pano0208Dto.gifHeight = panoMaterial.gifHeight;
      pano0208Dto.gifFrameCount = panoMaterial.gifFrameCount;
      pano0208Dto.gifDelayTime = panoMaterial.gifDelayTime;
      pano0208Dto.hasPngImage = "true";

    }

    pano0208Dto.materialPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_IMAGE,
        PanoConstantsIF.MATERIAL_FOLDER_NAME + pano0208Dto.expositionId, pano0208Dto.materialPath);

    String _srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_IMAGE,
        PanoConstantsIF.MATERIAL_FOLDER_NAME + pano0208Dto.expositionId,
        inForm.selectedMaterialId + "/");

    String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_IMAGE,
        PanoConstantsIF.MATERIAL_FOLDER_NAME + pano0208Dto.expositionId,
        inForm.selectedMaterialId + "/");
    FileServiceUtil.copyDirFromPublicStorageToAppServer(_srcPath, _destPath);

    String searchResult = objectMapper.writeValueAsString(pano0208Dto);
    return searchResult;
  }

}
