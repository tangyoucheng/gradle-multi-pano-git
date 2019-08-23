package cn.com.pano.pano.service.pano02;

import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Maps;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.form.pano02.Pano0205Form;
import cn.com.pano.pano.mapper.common01.PanoMaterial01Mapper;
import cn.com.pano.pano.mapper.pano02.Pano0205Mapper;
import cn.com.pano.pano.model.common01.PanoMaterial01Model;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 检索图信息。
 * 
 * @author ouyangzidou
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0205SearchService extends BaseService {

  @Autowired
  public Pano0205Mapper pano0205Mapper;
  @Autowired
  public PanoMaterial01Mapper panoMaterial01Mapper;

  /**
   * 检索素材详细信息。
   * 
   * @param inForm Pano0205Form
   */
  public EasyJson<Object> doSearchMaterial(Pano0205Form inForm) throws Exception {
    // 素材归属
    String materialFolderPath = "common_material";
    if (!ObjectUtils.isEmpty(inForm.materialBelongType)
        && PanoConstantsIF.MATERIAL_BELONGTYPE_EXPOSITION.equals(inForm.materialBelongType)) {
      materialFolderPath = inForm.expositionId;
    }

    String materialTypeId = "";
    // 通过页面上选择的热点url类型，确定检索条件中的素材类型
    if (PanoConstantsIF.HOTSPOT_URL_TYPE_IMAGE.equals(inForm.urlType)) {
      materialTypeId = PanoConstantsIF.MATERIAL_TYPE_IMAGE;
    }
    if (PanoConstantsIF.HOTSPOT_URL_TYPE_SOUND.equals(inForm.urlType)) {
      materialTypeId = PanoConstantsIF.MATERIAL_TYPE_SOUND;
    }
    if (PanoConstantsIF.HOTSPOT_URL_TYPE_VIDEO.equals(inForm.urlType)) {
      materialTypeId = PanoConstantsIF.MATERIAL_TYPE_VIDEO;
    }
    if (PanoConstantsIF.HOTSPOT_URL_TYPE_TEXT_IMAGE.equals(inForm.urlType)) {
      materialTypeId = PanoConstantsIF.MATERIAL_TYPE_IMAGE_TEXT;
    }

    HashMap<String, Object> condition = Maps.newHashMap();
    condition.put("materialTypeId", materialTypeId);
    // 素材归属
    condition.put("expositionId", materialFolderPath);

    // 数据索引
    condition.put("offSet", (inForm.pageNumber - 1) * inForm.pageSize);
    // 每页的条数
    condition.put("pageSize", inForm.pageSize);

    // 件数检查
    long resultCount = pano0205Mapper.selectMaterialInfoCount(condition);

    // 翻页检索素材路径
    List<PanoMaterial01Model> result = pano0205Mapper.selectMaterialInfo(condition);

    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setTotal(resultCount);
    easyJson.setRows(result);
    return easyJson;
  }

}
