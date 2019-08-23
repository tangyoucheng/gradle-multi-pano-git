package cn.com.pano.panovr.service.panovr01;

import java.io.File;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.utils.FileServiceUtil;
import cn.com.pano.pano.common.utils.FilesServiceUtil;
import cn.com.pano.pano.mapper.common.PanoExpositionMapper;
import cn.com.pano.pano.mapper.common01.PanoExpositionMap01Mapper;
import cn.com.pano.pano.mapper.common01.PanoExpositionMapHotspot01Mapper;
import cn.com.pano.pano.mapper.common01.PanoMaterial01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanorama01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanoramaHotspot01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPolygonHotspot01Mapper;
import cn.com.pano.pano.model.common.PanoExposition;
import cn.com.pano.pano.model.common.PanoExpositionMap;
import cn.com.pano.pano.model.common.PanoExpositionMapHotspot;
import cn.com.pano.pano.model.common.PanoMaterial;
import cn.com.pano.pano.model.common.PanoPanorama;
import cn.com.pano.pano.model.common.PanoPanoramaHotspot;
import cn.com.pano.pano.model.common.PanoPanoramaQuery;
import cn.com.pano.pano.model.common.PanoPolygonHotspot;
import cn.com.pano.pano.model.common01.PanoPanorama01Model;
import cn.com.pano.panovr.dto.panovr01.PanoVr0104Dto;
import cn.com.pano.panovr.form.panovr01.PanoVr0104Form;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.framework.common.CodeValueRecord;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.util.FwFileUtils;
import cn.com.platform.web.BaseService;

/**
 * 展览下全景图信息一览画面初期显示
 * 
 * @author ouyangzidou
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class PanoVr0104InitService extends BaseService {

//  @Autowired
//  public PanoVr0104Mapper vr0104Mapper;
  @Autowired
  public PanoExpositionMap01Mapper panoExpositionMap01Mapper;
  @Autowired
  public PanoExpositionMapHotspot01Mapper panoExpositionMapHotspot01Mapper;
  @Autowired
  public PanoPanoramaHotspot01Mapper panoPanoramaHotspot01Mapper;
  @Autowired
  public PanoPanorama01Mapper panoPanorama01Mapper;
  @Autowired
  public PanoMaterial01Mapper panoMaterial01Mapper;
  @Autowired
  public PanoExpositionMapper panoExpositionMapper;
  @Autowired
  public PanoPolygonHotspot01Mapper panoPolygonHotspot01Mapper;
  @Autowired
  public PanoVr0104SearchService panoVr0104SearchService;
  // 类静态成员变量，存储由0102传入的pageStartRowNo
  @Autowired
  public static String pageStartRowNoFromPanoVr0104;

  /**
   * 场景初期显示操作
   * 
   * @param inForm
   * @throws Exception
   */
  public void doInit(PanoVr0104Form inForm) throws Exception {

    // 判断是否由 pano0110进入，如果是，则保存传入的pageStartRowNo
    if ("yes".equals(inForm.thisFlagIsFromPano0110)) {
      // 重置由0102传入的pageStartRowNo
      // vr0104InitService.pageStartRowNoFromvr0104 = "0";
      PanoVr0104InitService.pageStartRowNoFromPanoVr0104 = Objects.toString(inForm.pageStartRowNo);
    }

//    HashMap<String, Object> condition = Maps.newHashMap();
//    condition.put("expositionId", _inForm.expositionId);
    PanoPanoramaQuery conditions = new PanoPanoramaQuery();
    conditions.createCriteria().andExpositionIdEqualTo(inForm.expositionId);
    // 检索全部检索全景图
    List<PanoPanorama01Model> panoPanoramaList = panoPanorama01Mapper.selectByBaseModel(conditions);

    // 加载下拉框信息
    if (panoPanoramaList != null && panoPanoramaList.size() > 0) {
      inForm.panoramaSelectInfo = Lists.newArrayList();
      for (PanoPanorama01Model panoList : panoPanoramaList) {
        inForm.panoramaSelectInfo
            .add(new CodeValueRecord(panoList.panoramaId,panoList.panoramaName));
      }
    }
    // 检索全景图首图
    conditions.createCriteria().andStartSceneFlagEqualTo(FlagStatus.Enable.toString());
    List<PanoPanorama01Model> startPanoPanoramaList =
        panoPanorama01Mapper.selectByBaseModel(conditions);
    // 检索展览背景音乐
    if (!ObjectUtils.isEmpty(inForm.expositionId)) {
      PanoExposition panoExposition = panoExpositionMapper.selectByPrimaryKey(inForm.expositionId);
      // 取得展览名称
      inForm.expositionName = panoExposition.expositionName;
      if (panoExposition != null && !ObjectUtils.isEmpty(panoExposition.expositionSoundId)) {
        // 取得声音文件
        PanoMaterial panoMaterial =
            panoMaterial01Mapper.selectByPrimaryKey(panoExposition.expositionSoundId);
        inForm.backGroundSoundPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_SOUND,
            PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
            panoMaterial.materialPath);

        String srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_SOUND,
            PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
            panoMaterial.materialId + "/");

        String destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_SOUND,
            PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
            panoMaterial.materialId + "/");
        FilesServiceUtil.copyDirFromPublicStorageToAppServer(srcPath, destPath);
      }
    }
    // 检索当前展览下无全景图首图
    if (ObjectUtils.isNotEmpty(startPanoPanoramaList) && ObjectUtils.isNotEmpty(panoPanoramaList)) {
      // 该展览下暂时没有首图，抽取第一个全景图设置为首图
      PanoPanorama panorama = panoPanoramaList.get(0);
      panorama.startSceneFlag = FlagStatus.Enable.toString();
      panoPanorama01Mapper.updateByPrimaryKey(panorama);
      inForm.panoramaId = panorama.panoramaId;
      inForm.panoramaName = panorama.panoramaName;
      inForm.panoramaPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
          inForm.expositionId, panorama.panoramaPath + "/show_l.xml");
      doSearchMapInfo(inForm);
    }
    // 展览下有全景图首图，显示首图
    if (ObjectUtils.isNotEmpty(startPanoPanoramaList)) {
      inForm.panoramaId = startPanoPanoramaList.get(0).panoramaId;
      inForm.panoramaName = startPanoPanoramaList.get(0).panoramaName;
      inForm.panoramaPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
          inForm.expositionId, startPanoPanoramaList.get(0).panoramaPath + "/show_l.xml");

      doSearchMapInfo(inForm);
      // 检索场景视角点
      PanoPanorama PanoPanorama = panoPanorama01Mapper.selectByPrimaryKey(inForm.panoramaId);
      inForm.panoramaHlookat = Objects.toString(PanoPanorama.panoramaHlookat);
      inForm.panoramaVlookat = Objects.toString(PanoPanorama.panoramaVlookat);
    }
  }

  /**
   * 有场景的情况下，加载场景相关信息的方法
   * 
   * @param _inForm
   * @throws Exception
   */
  public void doSearchMapInfo(PanoVr0104Form _inForm) throws Exception {
    // 检索展览下统一的推荐线路点提示信息
    PanoExposition panoExposition = panoExpositionMapper.selectByPrimaryKey(_inForm.expositionId);
    if (!ObjectUtils.isEmpty(panoExposition.expositionRecommendInfo)) {
      _inForm.expositionRecommendInfo = panoExposition.expositionRecommendInfo;
    } else {
      _inForm.expositionRecommendInfo = "推荐路线";
    }
    // 检索展览下场景切换统一的提示信息
    if (!ObjectUtils.isEmpty(panoExposition.expoGoSceneTooltip)) {
      _inForm.expoGoSceneTooltipInfo = panoExposition.expoGoSceneTooltip;

    }

    // 全景图文件取得
    String _srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_PANORAMA,
        _inForm.expositionId, _inForm.panoramaId + "/");
    String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
        _inForm.expositionId, _inForm.panoramaId + "/");
    _inForm.showCurrentMapExists =
        FileServiceUtil.getPanoramaFileFromPublicStorage(_srcPath, _destPath);

    // 检索该全景图下有无独立音乐
    if (!ObjectUtils.isEmpty(_inForm.panoramaId)) {
      PanoPanorama panoPanorama = panoPanorama01Mapper.selectByPrimaryKey(_inForm.panoramaId);
      if (panoPanorama != null && !ObjectUtils.isEmpty(panoPanorama.panoramaSoundId)) {
        // 取得声音文件
        PanoMaterial panoMaterial =
            panoMaterial01Mapper.selectByPrimaryKey(panoPanorama.panoramaSoundId);
        _inForm.backGroundSoundPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_SOUND,
            PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
            panoMaterial.materialPath);

        String srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_SOUND,
            PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
            panoMaterial.materialId + "/");

        String destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_SOUND,
            PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
            panoMaterial.materialId + "/");
        FilesServiceUtil.copyDirFromPublicStorageToAppServer(srcPath, destPath);
      }
    }

    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    dbf.setIgnoringElementContentWhitespace(true);
    try {
      String xmlFilePath = FwFileUtils.getAbsolutePath(_inForm.panoramaPath);
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document xmldoc = db.parse(xmlFilePath);
      Element root = xmldoc.getDocumentElement();
      if (!ObjectUtils.isEmpty(_inForm.backGroundSoundPath)) {
        root.setAttribute("onstart", "playsound(bgsnd," + _inForm.backGroundSoundPath + ", 0);");
      }
      // 引入外部ＸＭＬ文件
      Element newElement = xmldoc.createElement("include");
      root.appendChild(newElement);
      newElement.setAttribute("url",
          "../../../../framework/panorama/template/gallery/swipe_gallery.xml");

      newElement = xmldoc.createElement("include");
      root.appendChild(newElement);
      newElement.setAttribute("url", "../../../../framework/panorama/template/tooltip.xml");

      newElement = xmldoc.createElement("include");
      root.appendChild(newElement);
      newElement.setAttribute("url", "../../../../framework/panorama/template/radar.xml");

      newElement = xmldoc.createElement("include");
      root.appendChild(newElement);
      newElement.setAttribute("url", "../../../../framework/panorama/template/sound.xml");

      newElement = xmldoc.createElement("include");
      root.appendChild(newElement);
      newElement.setAttribute("url", "../../../../framework/panorama/template/anihotspots.xml");

      newElement = xmldoc.createElement("include");
      root.appendChild(newElement);
      newElement.setAttribute("url", "../../../../framework/panorama/template/recommend_path.xml");

      newElement = xmldoc.createElement("include");
      root.appendChild(newElement);
      newElement.setAttribute("url",
          "../../../../framework/panorama/template/video/videointerface.xml");

      newElement = xmldoc.createElement("include");
      root.appendChild(newElement);
      newElement.setAttribute("url",
          "../../../../framework/panorama/template/video/videoplayercontrol.xml");

      newElement = xmldoc.createElement("include");
      root.appendChild(newElement);
      newElement.setAttribute("url",
          "../../../../framework/panorama/template/video/videoplayer.xml");

      TransformerFactory factory = TransformerFactory.newInstance();
      Transformer former = factory.newTransformer();
      former.setOutputProperty(OutputKeys.INDENT, "yes");
      former.transform(new DOMSource(xmldoc), new StreamResult(new File(xmlFilePath)));

    } catch (Exception e) {
      throw new SystemException(e);
    }
    if (_inForm.showCurrentMapExists) {
      // 读取数据库中的热点信息
      HashMap<String, Object> conditions = Maps.newHashMap();
      conditions.put("panoramaId", _inForm.panoramaId);
      if (conditions.size() > 0) {
        List<PanoPanoramaHotspot> selectHotSpot =
            panoPanoramaHotspot01Mapper.selectHotSpot(conditions);
        if (selectHotSpot != null && !selectHotSpot.isEmpty()) {
          BeanUtils.copyProperties(selectHotSpot, _inForm.spotInfoList);
          for (PanoVr0104Dto vr0104Dto : _inForm.spotInfoList) {
            // 取得首场景中应该显示的推荐路线点ID
            PanoPanoramaHotspot fakeHotspot =
                panoPanoramaHotspot01Mapper.selectByPrimaryKey(_inForm.expositionId);
            if (fakeHotspot != null && !StringUtils.isEmpty(fakeHotspot.nextRecommendHotspotId)
                && fakeHotspot.nextRecommendHotspotId.equals(vr0104Dto.hotspotId)) {
              _inForm.currentRecommendHotspotId = vr0104Dto.hotspotId;
            }
            if (!ObjectUtils.isEmpty(vr0104Dto.hotspotImageId)) {
              eidtHotspotImagePath(_inForm, vr0104Dto);
            }
            if (Objects.equals(PanoConstantsIF.HOTSPOT_TYPE_HOTSPOT_POLYGON,
                vr0104Dto.hotspotType)) {
              conditions.put("hotspotId", vr0104Dto.hotspotId);
              List<PanoPolygonHotspot> selectPoint =
                  panoPolygonHotspot01Mapper.selectBypolygonId(conditions);
              if (selectPoint != null) {
                vr0104Dto.pointLists = selectPoint;
              }
            }

            // 如果该热点是视频热点，取得其下视频文件
            panoVr0104SearchService.getVideoFile(vr0104Dto);

            // 前往生成gallery的操作
            panoVr0104SearchService.doGetHotspotImageUrl(_inForm, vr0104Dto);

          }
          _inForm.spotInfoListJson = objectMapper.writeValueAsString(_inForm.spotInfoList);
        }

      }

      // 检索有无地图
      _inForm.miniMapCheck = false;
      HashMap<String, Object> condition = Maps.newHashMap();
      condition.put("expositionId", _inForm.expositionId);
      PanoExpositionMap panoExpositionMap =
          panoExpositionMap01Mapper.selectExpositionMapInfo(condition);
      if (panoExpositionMap != null) {
        _inForm.expositionMapPath =
            MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_IMAGE, _inForm.expositionId,
                panoExpositionMap.expositionMapPath);
        _inForm.expositionMapId = panoExpositionMap.expositionMapId;

        String srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_IMAGE,
            _inForm.expositionId, panoExpositionMap.expositionMapId + "/");

        String destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_IMAGE,
            _inForm.expositionId, panoExpositionMap.expositionMapId + "/");
        _inForm.miniMapCheck =
            FilesServiceUtil.copyDirFromPublicStorageToAppServer(srcPath, destPath);
      }
      if (_inForm.miniMapCheck) {
        // 读取数据库中的地图上热点信息
        conditions.put("expositionMapId", _inForm.expositionMapId);
        if (conditions.size() > 0) {
          _inForm.miniMapSpotInfoList =
              panoExpositionMapHotspot01Mapper.selectMapHotspotInfo(conditions);
          if (_inForm.miniMapSpotInfoList != null) {
            for (PanoExpositionMapHotspot expositionMapHotspot : _inForm.miniMapSpotInfoList) {
              if (_inForm.panoramaId.equals(expositionMapHotspot.panoramaId)) {
                _inForm.selectedMiniMapHotspotId = expositionMapHotspot.expositionMapHotspotId;
                // 检索地图热点的雷达角度
                if (!ObjectUtils.isEmpty(expositionMapHotspot.expositionMapHotspotHeading)) {
                  _inForm.radarHeading = expositionMapHotspot.expositionMapHotspotHeading;
                } else {
                  // 没有雷达角度，默认雷达角度
                  _inForm.radarHeading = "0";
                }
              }
            }
          }
          _inForm.miniMapSpotInfoListJson =
              objectMapper.writeValueAsString(_inForm.miniMapSpotInfoList);
        }
      }
    }
  }

  /**
   * 热点图片路径编辑
   * 
   * @param _inForm
   * @param vr0203Dto
   * @throws Exception
   */
  private void eidtHotspotImagePath(PanoVr0104Form _inForm, PanoVr0104Dto vr0104Dto)
      throws Exception {
    PanoMaterial panoMaterial = panoMaterial01Mapper.selectByPrimaryKey(vr0104Dto.hotspotImageId);
    if (panoMaterial != null && !ObjectUtils.isEmpty(panoMaterial.materialId)) {

      vr0104Dto.hotspotImagePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_IMAGE,
          PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
          panoMaterial.materialPath);

      String _srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_IMAGE,
          PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
          panoMaterial.materialId + "/");

      String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_IMAGE,
          PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
          panoMaterial.materialId + "/");
      FilesServiceUtil.copyDirFromPublicStorageToAppServer(_srcPath, _destPath);

      // 判断该素材是否是gif图并是否有生产对应的png图
      vr0104Dto.hasPngImage = "false";
      if (!ObjectUtils.isEmpty(panoMaterial.gifDelayTime)) {
        vr0104Dto.hasPngImage = "true";
        vr0104Dto.gifWidth = panoMaterial.gifWidth;
        vr0104Dto.gifHeight = panoMaterial.gifHeight;
        vr0104Dto.gifFrame = panoMaterial.gifFrameCount;
        vr0104Dto.gifDelayTime = panoMaterial.gifDelayTime;
      }
    }
  }

}
