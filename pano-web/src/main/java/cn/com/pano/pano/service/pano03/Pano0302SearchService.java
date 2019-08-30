package cn.com.pano.pano.service.pano03;

import java.util.HashMap;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Maps;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.form.pano03.Pano0302Form;
import cn.com.pano.pano.mapper.pano03.Pano0302Mapper;
import cn.com.platform.framework.util.FwStringUtils;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 检索素材信息。
 * 
 * @author tangzhenzong
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0302SearchService extends BaseService {
  @Autowired
  public Pano0302Mapper pano0302Mapper;

  /**
   * 素材检索处理。
   * 
   * @param inForm Pano0302Form
   * @return
   */
  public EasyJson<Object> searchMaterial(Pano0302Form inForm) throws Exception {
    // 从数据库检索素材信息
    HashMap<String, Object> condition = Maps.newHashMap();
    
    condition.put("materialTypeId", null);
    if (ObjectUtils.isNotEmpty(inForm.materialTypeId)) {
      condition.put("materialTypeId", inForm.materialTypeId);
    }
    
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

    long resultCount = pano0302Mapper.selectMaterialInfoCount(condition);

    // 素材信息详细
    inForm.materialInfo = pano0302Mapper.selectMaterialInfo(condition);

    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setTotal(resultCount);
    easyJson.setRows(inForm.materialInfo);;
    return easyJson;

  }
}
