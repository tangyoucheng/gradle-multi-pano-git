package cn.com.pano.pano.service.pano01;

import java.io.File;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Maps;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.utils.FileServiceUtil;
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
import cn.com.pano.pano.model.common.PanoExpositionMap;
import cn.com.pano.pano.model.common.PanoExpositionMapHotspot;
import cn.com.pano.pano.model.common.PanoHotspotUrl;
import cn.com.pano.pano.model.common.PanoMaterial;
import cn.com.pano.pano.model.common.PanoPanorama;
import cn.com.pano.pano.model.common.PanoPanoramaHotspot;
import cn.com.pano.pano.model.common.PanoPolygonHotspot;
import cn.com.pano.pano.model.common01.PanoExpositionMapHotspot01Model;
import cn.com.platform.util.FwFileUtils;
import cn.com.platform.web.BaseService;

/**
 * 展览更新删除操作
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
   * 展览更新
   * 
   * @param _inForm
   * @throws Exception
   */
  public void doUpdateExpositionInfo(Pano0103Form _inForm) throws Exception {
    // 取得loginUserId
    // 展览基本信息更新操作
    if (!ObjectUtils.isEmpty(_inForm.expositionId)) {
      PanoExposition panoExposition = panoExpositionMapper.selectByPrimaryKey(_inForm.expositionId);
      if (panoExposition != null) {

        panoExposition.setExpositionName(_inForm.expositionName);
        panoExposition.setNotes(_inForm.expositionNotes);
        panoExposition.setStatus(_inForm.expositionStatus);
        panoExposition.setStatusNotes(_inForm.expositionStatusNotes);
        panoExposition.setVrFlag(_inForm.vrFlag);
        if (!ObjectUtils.isEmpty(_inForm.expositionStartDate)) {
          panoExposition.setExpositionStartDate(LocalDate.parse(_inForm.expositionStartDate));
        }
        if (!ObjectUtils.isEmpty(_inForm.expositionEndDate)) {
          panoExposition.setExpositionEndDate(LocalDate.parse(_inForm.expositionEndDate));
        }

        updateAudit(panoExposition);
        panoExpositionMapper.updateByPrimaryKey(panoExposition);
      }
    }


    // 展览模式改变时，对panos_r文件进行适当调整
    HashMap<String, Object> condition = Maps.newHashMap();
    condition.put("expositionId", _inForm.expositionId);
    List<PanoPanorama> panoPanoramaList = panoPanorama01Mapper.findByExpositionId(condition);
    if (ObjectUtils.isNotEmpty(panoPanoramaList)) {
      for (PanoPanorama panoPanorama : panoPanoramaList) {
        String _storageFilePath =
            MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_PANORAMA,
                panoPanorama.getExpositionId(), panoPanorama.getPanoramaId());
        String _appFilePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
            panoPanorama.getExpositionId(), panoPanorama.getPanoramaId());

        if (Objects.equals("1", _inForm.vrFlag)) {
          // 复制panos_l的内容到panos_r中
          FileServiceUtil.copyDirInPublicStorage(_storageFilePath + "/panos_l/",
              _storageFilePath + "/panos_r/");
        } else {
          // 在PublicStorage中删除panos_r
          FileServiceUtil.deletePublicStorageFolder(_storageFilePath + "/panos_r");
          // 在服务器中删除整个场景信息
          FileUtils.deleteDirectory(new File(FwFileUtils.getAbsolutePath(_appFilePath)));
        }
      }
    }
  }


  /**
   * 展览删除
   * 
   * @param _inForm
   * @throws Exception
   */
  public void doDeleteExpositionInfo(Pano0103Form _inForm) throws Exception {

    if (!ObjectUtils.isEmpty(_inForm.expositionId)) {

      HashMap<String, Object> _conditions = Maps.newHashMap();
      _conditions.put("expositionId", _inForm.expositionId);

      // 删除对应多边形热点位置信息 后台管理表
      List<PanoPolygonHotspot> polygonListW =
          panoPolygonHotspot01Mapper.selectByExpositionId(_conditions);
      if (polygonListW != null) {
        for (PanoPolygonHotspot polygonHotspot : polygonListW) {
          panoPolygonHotspot01Mapper.deleteByPrimaryKey(polygonHotspot.polygonId);
        }
      }

      // 删除指定热点URL信息 后台管理表
      List<PanoHotspotUrl> urlListW = panoHotspotUrl01Mapper.selectByExpositionId(_conditions);
      if (urlListW != null) {
        for (PanoHotspotUrl hotspotUrl : urlListW) {
          panoHotspotUrl01Mapper.deleteByPrimaryKey(hotspotUrl.hotspotId,
              hotspotUrl.hotspotUrlObjectId);
        }
      }

      // 删除指定全景图热点的信息 后台管理表
      List<PanoPanoramaHotspot> hotspotListW =
          panoPanoramaHotspot01Mapper.selectHostSpotByExpositionId(_conditions);

      if (hotspotListW != null) {
        for (PanoPanoramaHotspot panoramaHotspot : hotspotListW) {
          panoPanoramaHotspot01Mapper.deleteByPrimaryKey(panoramaHotspot.hotspotId);
        }
      }

      // 删除展览地图热点信息 后台管理表
      List<PanoExpositionMapHotspot01Model> mapHotspotListW =
          panoExpositionMapHotspot01Mapper.selectByExpositionId(_conditions);
      if (!mapHotspotListW.isEmpty()) {
        for (PanoExpositionMapHotspot expositionMapHotspot : mapHotspotListW) {
          panoExpositionMapHotspot01Mapper
              .deleteByPrimaryKey(expositionMapHotspot.expositionMapHotspotId);
        }
      }

      // 删除展览地图信息 后台管理表
      List<PanoExpositionMap> mapListW =
          panoExpositionMap01Mapper.findByExpositionId(_inForm.expositionId);
      if (mapListW != null) {
        for (PanoExpositionMap expositionMap : mapListW) {
          panoExpositionMap01Mapper.deleteByPrimaryKey(expositionMap.expositionMapId);
        }
      }

      // 删除指定素材信息 后台管理表
      List<PanoMaterial> materialListW =
          panoMaterial01Mapper.findByExpositionId(_inForm.expositionId);
      if (materialListW != null) {
        for (PanoMaterial material : materialListW) {
          panoMaterial01Mapper.deleteByPrimaryKey(material.materialId);
        }
      }

      // 删除指定全景图信息 后台管理表
      List<PanoPanorama> listW = panoPanorama01Mapper.findByExpositionId(_conditions);
      if (listW != null) {
        for (PanoPanorama panorama : listW) {
          panoPanorama01Mapper.deleteByPrimaryKey(panorama.panoramaId);
        }
      }

      // 删除指定展览信息 后台管理表
      PanoExposition panoExposition = panoExpositionMapper.selectByPrimaryKey(_inForm.expositionId);
      if (panoExposition != null) {
        panoExpositionMapper.deleteByPrimaryKey(panoExposition.expositionId);
      }

      // 文件服务器上删除
      String filePathW = FwFileUtils
          .getAbsolutePath(MessageFormat.format("file_w_app/{0}/", _inForm.expositionId));
      FileUtils.deleteDirectory(new File(filePathW));

      // Storage<PublicStorage> fileListW = new PublicStorage("file_w/" + _inForm.expositionId +
      // "/");
      // fileListW.remove(true);

      // 2017/01/19 上传删除已发布展览文件的代码，现在为注释状态
      // // 如果先前有发布成功的展览，先删除先前发布的文件夹与压缩文件
      // String filePath =
      // MessageFormat.format("statictour_app/{0}/",
      // _inForm.expositionId);
      // FwFileUtils.deleteFolder(FwFileUtils.getAbsolutePath(filePath));
      // String zipPath =
      // MessageFormat.format("statictour_app/{0}.zip",
      // _inForm.expositionId);
      // FwFileUtils.deleteFolder(FwFileUtils.getAbsolutePath(zipPath));
      // String vrFilePath =
      // MessageFormat.format("statictour_app/{0}_vr/",
      // _inForm.expositionId);
      // FwFileUtils.deleteFolder(FwFileUtils.getAbsolutePath(vrFilePath));
      // String vrZipPath =
      // MessageFormat.format("statictour_app/{0}_vr.zip",
      // _inForm.expositionId);
      // FwFileUtils.deleteFolder(FwFileUtils.getAbsolutePath(vrZipPath));

    }
  }
}
