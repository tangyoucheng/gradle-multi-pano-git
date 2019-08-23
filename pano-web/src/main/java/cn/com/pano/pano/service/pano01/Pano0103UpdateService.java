package cn.com.pano.pano.service.pano01;

import java.io.File;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Maps;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.form.pano01.Pano0103Form;
import cn.com.pano.pano.mapper.common.PanoExpositionMapper;
import cn.com.pano.pano.mapper.common01.PanoExpositionMap01Mapper;
import cn.com.pano.pano.mapper.common01.PanoExpositionMapHotspot01Mapper;
import cn.com.pano.pano.mapper.common01.PanoHotspotUrl01Mapper;
import cn.com.pano.pano.mapper.common01.PanoMaterial01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanorama01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanoramaHotspot01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPolygonHotspot01Mapper;
import cn.com.pano.pano.model.common.PanoExposition;
import cn.com.pano.pano.model.common.PanoExpositionMapQuery;
import cn.com.pano.pano.model.common.PanoHotspotUrl;
import cn.com.pano.pano.model.common.PanoMaterialQuery;
import cn.com.pano.pano.model.common.PanoPanoramaQuery;
import cn.com.pano.pano.model.common.PanoPolygonHotspot;
import cn.com.pano.pano.model.common01.PanoExpositionMap01Model;
import cn.com.pano.pano.model.common01.PanoExpositionMapHotspot01Model;
import cn.com.pano.pano.model.common01.PanoMaterial01Model;
import cn.com.pano.pano.model.common01.PanoPanorama01Model;
import cn.com.pano.pano.model.common01.PanoPanoramaHotspot01Model;
import cn.com.pano.pano.model.common01.PanoPolygonHotspot01Model;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 展览更新删除操作。
 * 
 * @author ouyangzidu
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0103UpdateService extends BaseService {

  @Autowired
  public PanoPolygonHotspot01Mapper panoPolygonHotspot01Mapper;
  @Autowired
  public PanoHotspotUrl01Mapper panoHotspotUrl01Mapper;
  @Autowired
  public PanoPanoramaHotspot01Mapper panoPanoramaHotspot01Mapper;
  @Autowired
  public PanoExpositionMapHotspot01Mapper panoExpositionMapHotspot01Mapper;
  @Autowired
  public PanoExpositionMap01Mapper panoExpositionMap01Mapper;
  @Autowired
  public PanoPanorama01Mapper panoPanorama01Mapper;
  @Autowired
  public PanoMaterial01Mapper panoMaterial01Mapper;
  @Autowired
  public PanoExpositionMapper panoExpositionMapper;

  /**
   * 展览更新。
   * 
   * @param inForm Pano0103Form
   */
  public EasyJson<Object> doUpdateExpositionInfo(Pano0103Form inForm) throws Exception {
    // 取得loginUserId
    // 展览基本信息更新操作
    if (ObjectUtils.isNotEmpty(inForm.expositionId)) {
      PanoExposition panoExposition = panoExpositionMapper.selectByPrimaryKey(inForm.expositionId);
      if (panoExposition != null) {

        panoExposition.setExpositionName(inForm.expositionName);
        panoExposition.setNotes(inForm.expositionNotes);
        panoExposition.setStatus(inForm.expositionStatus);
        panoExposition.setStatusNotes(inForm.expositionStatusNotes);
        panoExposition.setVrFlag(inForm.vrFlag);
        if (ObjectUtils.isNotEmpty(inForm.expositionStartDate)) {
          panoExposition.setExpositionStartDate(LocalDate.parse(inForm.expositionStartDate));
        }
        if (ObjectUtils.isNotEmpty(inForm.expositionEndDate)) {
          panoExposition.setExpositionEndDate(LocalDate.parse(inForm.expositionEndDate));
        }

        updateAudit(panoExposition);
        panoExpositionMapper.updateByPrimaryKey(panoExposition);
      }
    }

    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setMsg("数据登录成功！");
    return easyJson;

  }


  /**
   * 展览删除。
   * 
   * @param inForm Pano0103Form
   */
  public EasyJson<Object> doDeleteExpositionInfo(Pano0103Form inForm) throws Exception {

    if (!ObjectUtils.isEmpty(inForm.expositionId)) {

      HashMap<String, Object> conditions = Maps.newHashMap();
      conditions.put("expositionId", inForm.expositionId);

      // 删除对应多边形热点位置信息 后台管理表
      List<PanoPolygonHotspot01Model> polygonListW =
          panoPolygonHotspot01Mapper.selectPolygonHotspotByExpositionId(conditions);
      if (polygonListW != null) {
        for (PanoPolygonHotspot polygonHotspot : polygonListW) {
          panoPolygonHotspot01Mapper.deleteByPrimaryKey(polygonHotspot.polygonId);
        }
      }

      // 删除指定热点URL信息 后台管理表
      List<PanoHotspotUrl> urlListW =
          panoHotspotUrl01Mapper.selectHotspotUrlByExpositionId(conditions);
      if (urlListW != null) {
        for (PanoHotspotUrl hotspotUrl : urlListW) {
          panoHotspotUrl01Mapper.deleteByPrimaryKey(hotspotUrl.hotspotId,
              hotspotUrl.hotspotUrlObjectId);
        }
      }

      // 删除指定全景图热点的信息 后台管理表
      List<PanoPanoramaHotspot01Model> hotspotListW =
          panoPanoramaHotspot01Mapper.selectPanoramaHostSpotByExpositionId(conditions);

      if (hotspotListW != null) {
        for (PanoPanoramaHotspot01Model panoramaHotspot : hotspotListW) {
          panoPanoramaHotspot01Mapper.deleteByPrimaryKey(panoramaHotspot.hotspotId);
        }
      }

      // 删除展览地图热点信息 后台管理表
      List<PanoExpositionMapHotspot01Model> mapHotspotListW =
          panoExpositionMapHotspot01Mapper.selectByExpositionId(conditions);
      if (!mapHotspotListW.isEmpty()) {
        for (PanoExpositionMapHotspot01Model expositionMapHotspot : mapHotspotListW) {
          panoExpositionMapHotspot01Mapper
              .deleteByPrimaryKey(expositionMapHotspot.expositionMapHotspotId);
        }
      }

      // 删除展览地图信息 后台管理表
      PanoExpositionMapQuery expositionMapQuery = new PanoExpositionMapQuery();
      expositionMapQuery.createCriteria().andExpositionIdEqualTo(inForm.expositionId);
      List<PanoExpositionMap01Model> mapListW =
          panoExpositionMap01Mapper.selectByBaseModel(expositionMapQuery);
      if (mapListW != null) {
        for (PanoExpositionMap01Model expositionMap : mapListW) {
          panoExpositionMap01Mapper.deleteByPrimaryKey(expositionMap.expositionMapId);
        }
      }

      // 删除指定素材信息 后台管理表
      PanoMaterialQuery materialQuery = new PanoMaterialQuery();
      materialQuery.createCriteria().andExpositionIdEqualTo(inForm.expositionId);
      List<PanoMaterial01Model> materialListW =
          panoMaterial01Mapper.selectByBaseModel(materialQuery);
      if (materialListW != null) {
        for (PanoMaterial01Model material : materialListW) {
          panoMaterial01Mapper.deleteByPrimaryKey(material.materialId);
        }
      }

      // 删除指定全景图信息 后台管理表
      PanoPanoramaQuery panoramaQuery = new PanoPanoramaQuery();
      panoramaQuery.createCriteria().andExpositionIdEqualTo(inForm.expositionId);
      List<PanoPanorama01Model> listW = panoPanorama01Mapper.selectByBaseModel(panoramaQuery);
      if (listW != null) {
        for (PanoPanorama01Model panorama : listW) {
          panoPanorama01Mapper.deleteByPrimaryKey(panorama.panoramaId);
        }
      }

      // 删除指定展览信息 后台管理表
      PanoExposition panoExposition = panoExpositionMapper.selectByPrimaryKey(inForm.expositionId);
      if (panoExposition != null) {
        panoExpositionMapper.deleteByPrimaryKey(panoExposition.expositionId);
      }

      // 文件服务器上删除
      String srcPublicPath =
          MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W, inForm.expositionId);
      File srcPublicFile = new File(srcPublicPath).getAbsoluteFile();
      FileUtils.deleteDirectory(srcPublicFile);

      // Storage<PublicStorage> fileListW = new PublicStorage("file_w/" + inForm.expositionId +
      // "/");
      // fileListW.remove(true);

      // 2017/01/19 上传删除已发布展览文件的代码，现在为注释状态
      // // 如果先前有发布成功的展览，先删除先前发布的文件夹与压缩文件
      // String filePath =
      // MessageFormat.format("statictour_app/{0}/",
      // inForm.expositionId);
      // FwFileUtils.deleteFolder(FwFileUtils.getAbsolutePath(filePath));
      // String zipPath =
      // MessageFormat.format("statictour_app/{0}.zip",
      // inForm.expositionId);
      // FwFileUtils.deleteFolder(FwFileUtils.getAbsolutePath(zipPath));
      // String vrFilePath =
      // MessageFormat.format("statictour_app/{0}_vr/",
      // inForm.expositionId);
      // FwFileUtils.deleteFolder(FwFileUtils.getAbsolutePath(vrFilePath));
      // String vrZipPath =
      // MessageFormat.format("statictour_app/{0}_vr.zip",
      // inForm.expositionId);
      // FwFileUtils.deleteFolder(FwFileUtils.getAbsolutePath(vrZipPath));

    }
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setMsg("数据删除成功！");
    return easyJson;
  }
}
