package cn.com.pano.pano.service.pano03;

import java.io.File;
import java.text.MessageFormat;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.form.pano03.Pano0306Form;
import cn.com.pano.pano.mapper.common01.PanoExpositionMap01Mapper;
import cn.com.pano.pano.mapper.common01.PanoExpositionMapHotspot01Mapper;
import cn.com.pano.pano.model.common.PanoExpositionMap;
import cn.com.pano.pano.model.common.PanoExpositionMapHotspotQuery;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.util.EasyJson;
import cn.com.platform.util.FwFileUtils;
import cn.com.platform.web.BaseService;

/**
 * 导航图编辑。
 * 
 * @author 唐友成
 * @date 2019-08-19
 *
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0306UpdateService extends BaseService {
  @Autowired
  public PanoExpositionMap01Mapper panoExpositionMap01Mapper;
  public PanoExpositionMapHotspot01Mapper panoExpositionMapHotspot01Mapper;

  /**
   * 更新素材信息。
   * 
   * @param inForm Pano0306Form
   */
  public EasyJson<Object> doUpdataExpositionMap(Pano0306Form inForm) throws Exception {

    PanoExpositionMap panoExpositionMap =
        panoExpositionMap01Mapper.selectByPrimaryKey(inForm.pano0306ExpositionMapId);
    // 更新导航图
    if (ObjectUtils.isNotEmpty(inForm.pano0306ExpositionMapPath)) {

      // 获取APP服务器侧文件目录。
      String srcAppRelativePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_IMAGE,
          UserSessionUtils.getSessionId(), "pano0306/" + inForm.pano0306ExpositionId,
          inForm.pano0306ExpositionMapId + "/");
      // 全景的storage路径
      String destPublicPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_IMAGE,
          inForm.pano0306ExpositionId, inForm.pano0306ExpositionMapId + "/");
      File srcAppDirectory = new File(FwFileUtils.getAbsolutePath(srcAppRelativePath));
      if (srcAppDirectory.exists() && srcAppDirectory.listFiles().length > 0) {
        // 拷贝到storage服务器
        FileUtils.copyDirectory(srcAppDirectory, new File(destPublicPath), true);
      }

      panoExpositionMap.expositionMapPath = inForm.pano0306ExpositionMapPath;
    }
    // 更新导航图名称
    panoExpositionMap.expositionMapName = inForm.pano0306ExpositionMapName;
    updateAudit(panoExpositionMap);

    panoExpositionMap01Mapper.updateByPrimaryKey(panoExpositionMap);

    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setMsg("保存成功！");
    return easyJson;

  }

  /**
   * 删除该素材。
   * 
   * @param inForm Pano0306Form
   */
  public EasyJson<Object> doDeleteExpositionMap(Pano0306Form inForm) throws Exception {

    if (!ObjectUtils.isEmpty(inForm.pano0306ExpositionMapId)) {

      PanoExpositionMap panoExpositionMap =
          panoExpositionMap01Mapper.selectByPrimaryKey(inForm.pano0306ExpositionMapId);

      // 若该地图为当前展览地图 删除展览地图上的热点
      if (PanoConstantsIF.MAP_USE_STATE_YES.equals(panoExpositionMap.expositionMapUseState)) {
        PanoExpositionMapHotspotQuery expositionMapHotspotQuery =
            new PanoExpositionMapHotspotQuery();
        expositionMapHotspotQuery.createCriteria()
            .andExpositionMapIdEqualTo(inForm.pano0306ExpositionMapId);
        panoExpositionMapHotspot01Mapper.deleteByBaseModel(expositionMapHotspotQuery);
      }

      // 删除该地图素材
      panoExpositionMap01Mapper.deleteByPrimaryKey(panoExpositionMap.expositionMapId);

    }

    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setMsg("删除成功！");
    return easyJson;

  }

}
