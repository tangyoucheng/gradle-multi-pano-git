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
import cn.com.pano.panovr.dto.panovr01.PanoVr0104Dto;
import cn.com.pano.panovr.form.panovr01.PanoVr0104Form;
import cn.com.pano.panovr.mapper.panovr01.PanoVr0104Mapper;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.framework.common.CodeValueRecord;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.util.FwFileUtils;
import cn.com.platform.web.BaseService;

/**
 * 检索取得全景图信息
 * 
 * @author ouyangzidou
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class PanoVr0104SearchService extends BaseService {

  @Autowired
  public PanoPanoramaHotspot01Mapper panoPanoramaHotspot01Mapper;
  public PanoVr0104Mapper vr0104Mapper;
  @Autowired
  public PanoExpositionMap01Mapper panoExpositionMap01Mapper;
  @Autowired
  public PanoExpositionMapHotspot01Mapper panoExpositionMapHotspot01Mapper;
  @Autowired
  public PanoMaterial01Mapper panoMaterial01Mapper;
  @Autowired
  public PanoHotspotUrl01Mapper panoHotspotUrl01Mapper;
  @Autowired
  public PanoPolygonHotspot01Mapper panoPolygonHotspot01Mapper;
  @Autowired
  public PanoExpositionMapper panoExpositionMapper;
  @Autowired
  public PanoPanorama01Mapper panoPanorama01Mapper;

  /**
   * 检查热点链接信息是否存在
   * 
   * @param _inForm
   * @throws Exception
   */
  public String doCheckHostspotInfo(PanoVr0104Form _inForm) throws Exception {
    // 查找被选中的热点的URL类型信息
    PanoPanoramaHotspot panoPanoramaHotspot =
        panoPanoramaHotspot01Mapper.selectByPrimaryKey(_inForm.selectedHotspotId);
    if (panoPanoramaHotspot == null) {
      // 当前热点已被其他用户删除！
      return "delete";
    }
    // 热点下链接全景图的场合
    if (PanoConstantsIF.HOTSPOT_URL_TYPE_PANORAMA.equals(panoPanoramaHotspot.hotspotUrlType)) {
      return PanoConstantsIF.HOTSPOT_URL_TYPE_PANORAMA;
    }
    // 热点下链接素材图的场合
    if (PanoConstantsIF.HOTSPOT_URL_TYPE_IMAGE.equals(panoPanoramaHotspot.hotspotUrlType)) {
      return PanoConstantsIF.HOTSPOT_URL_TYPE_IMAGE;
    }
    // 热点下链接声音的场合
    if (PanoConstantsIF.HOTSPOT_URL_TYPE_SOUND.equals(panoPanoramaHotspot.hotspotUrlType)) {
      return PanoConstantsIF.HOTSPOT_URL_TYPE_SOUND;
    }
    // 热点下链接视屏的场合
    if (PanoConstantsIF.HOTSPOT_URL_TYPE_VIDEO.equals(panoPanoramaHotspot.hotspotUrlType)) {
      return PanoConstantsIF.HOTSPOT_URL_TYPE_VIDEO;
    }

    // 热点下链接外部链接地址的场合
    if (PanoConstantsIF.HOTSPOT_URL_TYPE_LINK.equals(panoPanoramaHotspot.hotspotUrlType)) {
      return PanoConstantsIF.HOTSPOT_URL_TYPE_LINK;
    }

    // 热点下链接素材图文的场合
    if (PanoConstantsIF.HOTSPOT_URL_TYPE_TEXT_IMAGE.equals(panoPanoramaHotspot.hotspotUrlType)) {
      return PanoConstantsIF.HOTSPOT_URL_TYPE_TEXT_IMAGE;
    }
    // 热点下没有链接信息
    return "";
  }

  /**
   * 显示热点链接的全景图信息
   * 
   * @param _inForm
   * @throws Exception
   */
  public String doShowHostspotInfo(PanoVr0104Form _inForm) throws Exception {
    HashMap<String, Object> conditions = Maps.newHashMap();
    conditions.put("hotspotId", _inForm.selectedHotspotId);
    conditions.put("expositionId", _inForm.expositionId);
    PanoHotspotUrl panoHotspotUrl = vr0104Mapper.selectHotspotUrlInfo(conditions);
    if (panoHotspotUrl != null && !ObjectUtils.isEmpty(panoHotspotUrl.hotspotUrlObjectId)) {
      // 通过上个导航点ID，取得当前跳转至的场景中应该出现的推荐线路点的ID
      _inForm.currentRecommendHotspotId = "";
      if (!ObjectUtils.isEmpty(_inForm.theLastedSceneHotspotId)) {
        PanoPanoramaHotspot lastHotspot =
            panoPanoramaHotspot01Mapper.selectByPrimaryKey(_inForm.theLastedSceneHotspotId);
        if (lastHotspot != null && !ObjectUtils.isEmpty(lastHotspot.nextRecommendHotspotId)) {
          _inForm.currentRecommendHotspotId = lastHotspot.nextRecommendHotspotId;
        }
      }
      _inForm.panoramaId = panoHotspotUrl.hotspotUrlObjectId;
      // 加载下拉框信息
      List<PanoPanorama> panoInfo = vr0104Mapper.selectPanoInfo(conditions);
      if (panoInfo != null && panoInfo.size() > 0) {
        _inForm.panoramaSelectInfo = Lists.newArrayList();
        for (PanoPanorama panoList : panoInfo) {
          _inForm.panoramaSelectInfo
              .add(new CodeValueRecord(panoList.panoramaName, panoList.panoramaId));
        }
      }
      PanoPanorama panoPanorama = panoPanorama01Mapper.selectByPrimaryKey(_inForm.panoramaId);

      String filePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
          _inForm.expositionId, panoPanorama.panoramaPath);
      _inForm.panoramaPath = filePath;
      // 全景图文件取得
      String _srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_PANORAMA,
          _inForm.expositionId, panoPanorama.panoramaId + "/");
      String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
          _inForm.expositionId, panoPanorama.panoramaId + "/");
      _inForm.showCurrentMapExists =
          FileServiceUtil.getPanoramaFileFromPublicStorage(_srcPath, _destPath);

      // 检索场景视角点
      PanoPanoramaHotspot panoPanoramaHotspot =
          panoPanoramaHotspot01Mapper.selectByPrimaryKey(_inForm.selectedHotspotId);
      _inForm.panoramaHlookat = Objects.toString(panoPanoramaHotspot.panoramaHlookat);
      _inForm.panoramaVlookat = Objects.toString(panoPanoramaHotspot.panoramaVlookat);

      if (_inForm.showCurrentMapExists) {
        // 读取数据库中的热点信息
        conditions.put("panoramaId", panoPanorama.panoramaId);
        if (conditions.size() > 0) {
          List<PanoPanoramaHotspot> selectHotSpot =
              panoPanoramaHotspot01Mapper.selectHotSpot(conditions);
          if (selectHotSpot != null && !selectHotSpot.isEmpty()) {
            BeanUtils.copyProperties(selectHotSpot, _inForm.spotInfoList);
            for (PanoVr0104Dto vr0104Dto : _inForm.spotInfoList) {
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

              // 视频热点下视频文件取得
              getVideoFile(vr0104Dto);

              // 前往生成gallery的操作
              doGetHotspotImageUrl(_inForm, vr0104Dto);

            }
          }
        }

        _inForm.panoramaId = panoPanorama.panoramaId;
        _inForm.panoramaName = panoPanorama.panoramaName;
        _inForm.panoramaPath = panoPanorama.panoramaPath;

      }

      // 读取数据库中的地图上热点信息
      conditions.put("expositionMapId", _inForm.expositionMapId);
      _inForm.selectedMiniMapHotspotId = "";
      _inForm.miniMapSpotInfoList =
          panoExpositionMapHotspot01Mapper.selectMapHotspotInfo(conditions);
      if (_inForm.miniMapSpotInfoList != null) {
        for (PanoExpositionMapHotspot expositionMapHotspot : _inForm.miniMapSpotInfoList) {
          if (_inForm.panoramaId.equals(expositionMapHotspot.panoramaId)) {
            _inForm.selectedMiniMapHotspotId = expositionMapHotspot.expositionMapHotspotId;
            // 检索地图热点的雷达角度
            if (!ObjectUtils.isEmpty(expositionMapHotspot.expositionMapHotspotHeading)) {
              _inForm.radarHeading = expositionMapHotspot.expositionMapHotspotHeading;
            }
          }
        }
      }
      // _inForm.miniMapSpotInfoListJson =
      // objectMapper.writeValueAsString(_inForm.miniMapSpotInfoList);
      // 检索当前场景有无独立音乐
      if (doCheckSoundOfPanorama(panoPanorama, _inForm)) {
        _inForm.hadIndependSound = "yes";
      }
      return objectMapper.writeValueAsString(_inForm);

    }
    return "";
  }

  /**
   * 显示热点链接的全景图信息
   * 
   * @param _inForm
   * @throws Exception
   */
  public void doShowHostspotInfoFromEdit(PanoVr0104Form _inForm) throws Exception {
    // 检索全景图
    HashMap<String, Object> allPanocondition = Maps.newHashMap();
    allPanocondition.put("expositionId", _inForm.expositionId);
    PanoPanorama panoPanorama = panoPanorama01Mapper.selectByPrimaryKey(_inForm.panoramaId);
    // 如果从删除当前场景的操作返回，则显示首场景
    if (panoPanorama == null) {
      // // 检索全景图首图
      panoPanorama = panoPanorama01Mapper.selectStartScenePanoInfo(allPanocondition);
      // 没有任何全景图
      if (panoPanorama == null) {
        PanoExposition panoExposition =
            panoExpositionMapper.selectByPrimaryKey(_inForm.expositionId);
        _inForm.expositionName = panoExposition.expositionName;
        _inForm.panoramaId = "";
        return;
      }
    }
    _inForm.panoramaName = panoPanorama.panoramaName;
    _inForm.panoramaId = panoPanorama.panoramaId;
    // 检索展览下统一的推荐线路点提示信息
    PanoExposition panoExposition = panoExpositionMapper.selectByPrimaryKey(_inForm.expositionId);
    _inForm.expoGoSceneTooltipInfo = panoExposition.expoGoSceneTooltip;
    if (!ObjectUtils.isEmpty(panoExposition.expositionRecommendInfo)) {
      _inForm.expositionRecommendInfo = panoExposition.expositionRecommendInfo;
    } else {
      _inForm.expositionRecommendInfo = "推荐路线";
    }
    // 取得展览名称
    _inForm.expositionName = panoExposition.expositionName;
    // 检索全部检索全景图
    List<PanoPanorama> panoPanoramaList = vr0104Mapper.selectPanoInfo(allPanocondition);
    // 检索首场景
    HashMap<String, Object> startSceneCondition = Maps.newHashMap();
    startSceneCondition.put("expositionId", _inForm.expositionId);
    PanoPanorama startPanoPanorama =
        panoPanorama01Mapper.selectStartScenePanoInfo(startSceneCondition);
    // 检索当前展览下无全景图首图
    if (startPanoPanorama == null && panoPanoramaList != null && panoPanoramaList.size() > 0) {
      // 该展览下暂时没有首图，抽取第一个全景图设置为首图
      PanoPanorama panorama = panoPanoramaList.get(0);
      panorama.startSceneFlag = FlagStatus.Enable.toString();
      panoPanorama01Mapper.updateByPrimaryKey(panorama);
    }

    // 通过上个导航点ID，取得当前跳转至的场景中应该出现的推荐线路点的ID
    _inForm.currentRecommendHotspotId = "";
    if (startPanoPanorama != null && _inForm.panoramaId.equals(startPanoPanorama.panoramaId)) {
      // 当前返回的场景是首场景，显示首场景的推荐路线点
      PanoPanoramaHotspot lastHotspot =
          panoPanoramaHotspot01Mapper.selectByPrimaryKey(_inForm.expositionId);
      if (lastHotspot != null && !ObjectUtils.isEmpty(lastHotspot.nextRecommendHotspotId)) {
        _inForm.currentRecommendHotspotId = lastHotspot.nextRecommendHotspotId;
      }
    } else if (!ObjectUtils.isEmpty(_inForm.theLastedSceneHotspotId)) {
      // 不是首个场景，做普通检索
      PanoPanoramaHotspot lastHotspot =
          panoPanoramaHotspot01Mapper.selectByPrimaryKey(_inForm.theLastedSceneHotspotId);
      if (lastHotspot != null && !ObjectUtils.isEmpty(lastHotspot.nextRecommendHotspotId)) {
        _inForm.currentRecommendHotspotId = lastHotspot.nextRecommendHotspotId;
      }
    }
    // 加载下拉框信息
    HashMap<String, Object> _condition = Maps.newHashMap();
    _condition.put("expositionId", _inForm.expositionId);
    List<PanoPanorama> panoInfo = vr0104Mapper.selectPanoInfo(_condition);
    if (panoInfo != null && panoInfo.size() > 0) {
      _inForm.panoramaSelectInfo = Lists.newArrayList();
      for (PanoPanorama panoList : panoInfo) {
        _inForm.panoramaSelectInfo
            .add(new CodeValueRecord(panoList.panoramaName, panoList.panoramaId));
      }
    }
    if (panoPanorama != null) {
      _inForm.panoramaPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
          _inForm.expositionId, panoPanorama.panoramaPath + "/show_l.xml");
      // 全景图文件取得
      String _srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_PANORAMA,
          _inForm.expositionId, _inForm.panoramaId + "/");
      String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
          _inForm.expositionId, _inForm.panoramaId + "/");
      _inForm.showCurrentMapExists =
          FilesServiceUtil.getPanoramaFileFromPublicStorage(_srcPath, _destPath);

      // 检索展览音乐
      doCheckSoundOfExposition(_inForm);
      // 检索当前场景有无独立音乐
      doCheckSoundOfPanorama(panoPanorama, _inForm);

      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      dbf.setIgnoringElementContentWhitespace(true);
      try {
        String xmlFilePath = FwFileUtils.getAbsolutePath(_inForm.panoramaPath);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document xmldoc = db.parse(xmlFilePath);
        Element root = xmldoc.getDocumentElement();
        // 拼接onstart事件
        StringBuffer onstartEvent = new StringBuffer();
        // 载入场景音乐
        if (!ObjectUtils.isEmpty(_inForm.backGroundSoundPath)) {
          onstartEvent
              .append("stopallsounds();playsound(bgsnd," + _inForm.backGroundSoundPath + ", 0);");
        }
        // 载入编辑视角点
        if (!ObjectUtils.isEmpty(_inForm.positionAthForEdit)
            && !StringUtils.isEmpty(_inForm.positionAtvForEdit)) {
          onstartEvent.append(
              "lookat(" + _inForm.positionAthForEdit + "," + _inForm.positionAtvForEdit + ");");
        }
        root.setAttribute("onstart", onstartEvent.toString());
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
        newElement.setAttribute("url",
            "../../../../framework/panorama/template/recommend_path.xml");

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
    }
    if (_inForm.showCurrentMapExists) {
      // 读取数据库中的热点信息
      HashMap<String, Object> conditions = Maps.newHashMap();
      conditions.put("panoramaId", _inForm.panoramaId);
      conditions.put("polygonId", _inForm.hotspotId);
      if (conditions.size() > 0) {
        _inForm.spotInfoListJson = "";
        List<PanoPanoramaHotspot> selectHotSpot =
            panoPanoramaHotspot01Mapper.selectHotSpot(conditions);
        if (selectHotSpot != null && !selectHotSpot.isEmpty()) {
          // 多边形热点坐标点序列化
          BeanUtils.copyProperties(selectHotSpot, _inForm.spotInfoList);
          for (PanoVr0104Dto vr0104Dto : _inForm.spotInfoList) {
            // vr0104Dto.pointLists = _inForm.pointInfoListJson;
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

            // 视频热点下视频文件取得
            getVideoFile(vr0104Dto);
            // 前往生成gallery的操作
            doGetHotspotImageUrl(_inForm, vr0104Dto);
          }
          _inForm.spotInfoListJson = objectMapper.writeValueAsString(_inForm.spotInfoList);
        }
      }
    }

    // 检索有无地图
    _inForm.miniMapCheck = false;
    HashMap<String, Object> condition = Maps.newHashMap();
    condition.put("expositionId", _inForm.expositionId);
    PanoExpositionMap panoExpositionMap =
        panoExpositionMap01Mapper.selectExpositionMapInfo(condition);
    if (panoExpositionMap != null) {
      _inForm.expositionMapPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_IMAGE,
          _inForm.expositionId, panoExpositionMap.expositionMapPath);
      _inForm.expositionMapId = panoExpositionMap.expositionMapId;

      String srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_IMAGE,
          _inForm.expositionId, panoExpositionMap.expositionMapId + "/");

      String destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_IMAGE,
          _inForm.expositionId, panoExpositionMap.expositionMapId + "/");
      _inForm.miniMapCheck = FilesServiceUtil.copyDirFromPublicStorageToAppServer(srcPath, destPath);
    }
    if (_inForm.miniMapCheck) {
      // 读取数据库中的地图上热点信息
      condition.put("expositionMapId", _inForm.expositionMapId);
      _inForm.selectedMiniMapHotspotId = "";
      if (condition.size() > 0) {
        _inForm.miniMapSpotInfoList =
            panoExpositionMapHotspot01Mapper.selectMapHotspotInfo(condition);
        if (_inForm.miniMapSpotInfoList != null) {
          for (PanoExpositionMapHotspot expositionMapHotspot : _inForm.miniMapSpotInfoList) {
            if (_inForm.panoramaId.equals(expositionMapHotspot.panoramaId)) {
              _inForm.selectedMiniMapHotspotId = expositionMapHotspot.expositionMapHotspotId;
              // 检索地图热点的雷达角度
              if (!ObjectUtils.isEmpty(expositionMapHotspot.expositionMapHotspotHeading)) {
                _inForm.radarHeading = expositionMapHotspot.expositionMapHotspotHeading;
              }
            }
          }
        }
        _inForm.miniMapSpotInfoListJson =
            objectMapper.writeValueAsString(_inForm.miniMapSpotInfoList);
      }
    }
  }

  /**
   * 检查地图上热点的链接信息
   * 
   * @param _inForm
   * @throws Exception
   */
  public String doShowMiniMapHotspotInfo(PanoVr0104Form _inForm) throws Exception {
    // 查找被选中的热点信息
    PanoExpositionMapHotspot panoExpositionMapHotspot =
        panoExpositionMapHotspot01Mapper.selectByPrimaryKey(_inForm.selectedMiniMapHotspotId);
    if (panoExpositionMapHotspot == null) {
      // 当前热点已被其他用户删除！
      return "delete";
    }
    // 热点下链接了全景图的场合
    if (!ObjectUtils.isEmpty(panoExpositionMapHotspot.panoramaId)) {
      // 取得热点的雷达角度
      if (!ObjectUtils.isEmpty(panoExpositionMapHotspot.expositionMapHotspotHeading)) {
        _inForm.radarHeading = panoExpositionMapHotspot.expositionMapHotspotHeading;
      }
      // 加载下拉框信息
      HashMap<String, Object> _condition = Maps.newHashMap();
      _condition.put("expositionId", _inForm.expositionId);
      List<PanoPanorama> panoInfo = vr0104Mapper.selectPanoInfo(_condition);
      if (panoInfo != null && panoInfo.size() > 0) {
        _inForm.panoramaSelectInfo = Lists.newArrayList();
        for (PanoPanorama panoList : panoInfo) {
          _inForm.panoramaSelectInfo
              .add(new CodeValueRecord(panoList.panoramaName, panoList.panoramaId));
        }
      }
      PanoPanorama panoPanorama =
          panoPanorama01Mapper.selectByPrimaryKey(panoExpositionMapHotspot.panoramaId);

      String filePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
          _inForm.expositionId, panoPanorama.panoramaPath + "/show_l.xml");
      _inForm.panoramaPath = filePath;
      // 全景图文件取得
      String _srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_PANORAMA,
          _inForm.expositionId, panoPanorama.panoramaId + "/");
      String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
          _inForm.expositionId, panoPanorama.panoramaId + "/");
      _inForm.showCurrentMapExists =
          FilesServiceUtil.getPanoramaFileFromPublicStorage(_srcPath, _destPath);

      if (_inForm.showCurrentMapExists) {
        // 读取数据库中的热点信息
        HashMap<String, Object> conditions = Maps.newHashMap();
        conditions.put("panoramaId", panoPanorama.panoramaId);
        if (conditions.size() > 0) {
          List<PanoPanoramaHotspot> selectHotSpot =
              panoPanoramaHotspot01Mapper.selectHotSpot(conditions);
          if (selectHotSpot != null && !selectHotSpot.isEmpty()) {
            BeanUtils.copyProperties(selectHotSpot, _inForm.spotInfoList);
            for (PanoVr0104Dto vr0104Dto : _inForm.spotInfoList) {
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

              // 视频热点下视频文件取得
              getVideoFile(vr0104Dto);
              // 前往生成gallery的操作
              doGetHotspotImageUrl(_inForm, vr0104Dto);

            }
          }
        }
        _inForm.panoramaId = panoPanorama.panoramaId;
        _inForm.panoramaName = panoPanorama.panoramaName;
        _inForm.panoramaPath = panoPanorama.panoramaPath + "/show_l.xml";
        _inForm.radarHeading = panoExpositionMapHotspot.expositionMapHotspotHeading;

      }
      // 检索当前场景有无独立音乐
      if (doCheckSoundOfPanorama(panoPanorama, _inForm)) {
        _inForm.hadIndependSound = "yes";
      }
      return objectMapper.writeValueAsString(_inForm);

    }
    // 热点下没有链接信息
    return "";
  }

  /**
   * 点击下拉框后跳转到对应场景
   * 
   * @param vr0104Form
   * @return
   * @throws Exception
   */
  public String doShowFromPanoSelected(PanoVr0104Form _inForm) throws Exception {
    if (!ObjectUtils.isEmpty(_inForm.panoramaId)) {
      // 加载下拉框信息
      HashMap<String, Object> _condition = Maps.newHashMap();
      _condition.put("expositionId", _inForm.expositionId);
      List<PanoPanorama> panoInfo = vr0104Mapper.selectPanoInfo(_condition);
      if (panoInfo != null && panoInfo.size() > 0) {
        _inForm.panoramaSelectInfo = Lists.newArrayList();
        for (PanoPanorama panoList : panoInfo) {
          _inForm.panoramaSelectInfo
              .add(new CodeValueRecord(panoList.panoramaName, panoList.panoramaId));
        }
      }
      PanoPanorama panoPanorama = panoPanorama01Mapper.selectByPrimaryKey(_inForm.panoramaId);
      // 设置场景视角点
      _inForm.panoramaHlookat = Objects.toString(panoPanorama.panoramaHlookat);
      _inForm.panoramaVlookat = Objects.toString(panoPanorama.panoramaVlookat);

      String filePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
          _inForm.expositionId, panoPanorama.panoramaPath + "/show_l.xml");
      _inForm.panoramaPath = filePath;
      // 全景图文件取得
      String _srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_PANORAMA,
          _inForm.expositionId, panoPanorama.panoramaId + "/");
      String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
          _inForm.expositionId, panoPanorama.panoramaId + "/");
      _inForm.showCurrentMapExists =
          FilesServiceUtil.getPanoramaFileFromPublicStorage(_srcPath, _destPath);

      if (_inForm.showCurrentMapExists) {
        // 读取数据库中的热点信息
        HashMap<String, Object> conditions = Maps.newHashMap();
        conditions.put("panoramaId", panoPanorama.panoramaId);
        if (conditions.size() > 0) {
          List<PanoPanoramaHotspot> selectHotSpot =
              panoPanoramaHotspot01Mapper.selectHotSpot(conditions);
          if (selectHotSpot != null && !selectHotSpot.isEmpty()) {
            BeanUtils.copyProperties(selectHotSpot, _inForm.spotInfoList);
            for (PanoVr0104Dto vr0104Dto : _inForm.spotInfoList) {
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

              // 视频热点下视频文件取得
              getVideoFile(vr0104Dto);
              // 前往生成gallery的操作
              doGetHotspotImageUrl(_inForm, vr0104Dto);

            }
          }
        }

        _inForm.panoramaId = panoPanorama.panoramaId;
        _inForm.panoramaName = panoPanorama.panoramaName;
        _inForm.panoramaPath = panoPanorama.panoramaPath + "/show_l.xml";

      }

      // 读取数据库中的地图上热点信息
      _condition.put("expositionMapId", _inForm.expositionMapId);
      _inForm.selectedMiniMapHotspotId = "";
      if (_condition.size() > 0) {
        _inForm.miniMapSpotInfoList =
            panoExpositionMapHotspot01Mapper.selectMapHotspotInfo(_condition);
        if (_inForm.miniMapSpotInfoList != null) {
          for (PanoExpositionMapHotspot expositionMapHotspot : _inForm.miniMapSpotInfoList) {
            if (_inForm.panoramaId.equals(expositionMapHotspot.panoramaId)) {
              _inForm.selectedMiniMapHotspotId = expositionMapHotspot.expositionMapHotspotId;
              // 检索地图热点的雷达角度
              if (!ObjectUtils.isEmpty(expositionMapHotspot.expositionMapHotspotHeading)) {
                _inForm.radarHeading = expositionMapHotspot.expositionMapHotspotHeading;
              }
            }
          }
        }
        // _inForm.miniMapSpotInfoListJson =
        // objectMapper.writeValueAsString(_inForm.miniMapSpotInfoList);
      }
      // 检索当前场景有无独立音乐
      if (doCheckSoundOfPanorama(panoPanorama, _inForm)) {
        _inForm.hadIndependSound = "yes";
      }
      return objectMapper.writeValueAsString(_inForm);

    }
    return "";
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

  /**
   * 取得普通热点链接的素材图信息
   * 
   * @param _inForm
   * @return
   * @throws Exception
   */
  public void doGetHotspotImageUrl(PanoVr0104Form _inForm, PanoVr0104Dto vr0104Dto)
      throws Exception {
    // 为热点内容为素材信息图的热点生成gallery层
    if (!ObjectUtils.isEmpty(vr0104Dto.hotspotId)
        && PanoConstantsIF.HOTSPOT_URL_TYPE_IMAGE.equals(vr0104Dto.hotspotUrlType)) {
      // 检查是否有素材图，有则为该热点添加gallery节点
      HashMap<String, Object> conditions = Maps.newHashMap();
      conditions.put("hotspotId", vr0104Dto.hotspotId);
      List<PanoHotspotUrl> resultList = panoHotspotUrl01Mapper.selectByHotspotId(conditions);
      if (resultList != null) {
        // 场景的gallery节点追加
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringElementContentWhitespace(true);
        if (!_inForm.panoramaPath.endsWith("show_l.xml")) {
          _inForm.panoramaPath = _inForm.panoramaPath + "/show_l.xml";
        }
        String xmlFilePath = FwFileUtils.getAbsolutePath(_inForm.panoramaPath);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document _xmldoc = db.parse(xmlFilePath);
        Element oldRoot = _xmldoc.getDocumentElement();
        Element galleryElement = _xmldoc.createElement("gallery");
        oldRoot.appendChild(galleryElement);
        galleryElement.setAttribute("name", "v_" + vr0104Dto.hotspotId + "_gallery");

        for (PanoHotspotUrl panoHotspotUrl : resultList) {
          // gallery下的img节点追加
          Element galleryImgElement = _xmldoc.createElement("img");
          galleryElement.appendChild(galleryImgElement);
          galleryImgElement.setAttribute("name", "img" + panoHotspotUrl.sortKey);
          // 检索素材信息
          PanoMaterial panoMaterial =
              panoMaterial01Mapper.selectByPrimaryKey(panoHotspotUrl.hotspotUrlObjectId);
          if (panoMaterial != null) {
            // 如果该素材是gif图
            if (panoMaterial.materialPath.toLowerCase().endsWith(".gif")
                && !ObjectUtils.isEmpty(panoMaterial.gifDelayTime)) {
              // 转为png
              panoMaterial.materialPath =
                  panoMaterial.materialPath.substring(0, panoMaterial.materialPath.lastIndexOf("."))
                      + ".png";

              panoMaterial.materialPath =
                  MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_IMAGE,
                      PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
                      panoMaterial.materialPath);

              // 创建style
              Element galleryStyleElement = _xmldoc.createElement("style");
              oldRoot.appendChild(galleryStyleElement);
              galleryStyleElement.setAttribute("name",
                  "v_" + panoMaterial.materialId + "_gifstyle");
              galleryStyleElement.setAttribute("url", "../../../../" + panoMaterial.materialPath);
              galleryStyleElement.setAttribute("crop",
                  "0|0|" + panoMaterial.gifWidth + "|" + panoMaterial.gifHeight);
              galleryStyleElement.setAttribute("framewidth", panoMaterial.gifWidth);
              galleryStyleElement.setAttribute("frameheight", panoMaterial.gifHeight);
              galleryStyleElement.setAttribute("frame", "0");
              galleryStyleElement.setAttribute("lastframe", panoMaterial.gifFrameCount);
              galleryStyleElement.setAttribute("onloaded",
                  "hotspot_animate(" + panoMaterial.gifDelayTime + ");");
              // 为img节点设置style属性
              galleryImgElement.setAttribute("gifstyle",
                  "v_" + panoMaterial.materialId + "_gifstyle");

            } else {

              panoMaterial.materialPath =
                  MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_IMAGE,
                      PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
                      panoMaterial.materialPath);

              galleryImgElement.setAttribute("url", "../../../../" + panoMaterial.materialPath);
            }
            // 拷贝素材文件
            String _srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_IMAGE,
                PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
                panoMaterial.materialId + "/");

            String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_IMAGE,
                PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
                panoMaterial.materialId + "/");
            FilesServiceUtil.copyDirFromPublicStorageToAppServer(_srcPath, _destPath);

          }
        }
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer former = factory.newTransformer();
        former.setOutputProperty(OutputKeys.INDENT, "yes");
        former.transform(new DOMSource(_xmldoc), new StreamResult(new File(xmlFilePath)));
      }
    } else if (PanoConstantsIF.HOTSPOT_URL_TYPE_TEXT_IMAGE.equals(vr0104Dto.hotspotUrlType)) {
      // 为热点内容为图文
      // 检查是否有素材图
      HashMap<String, Object> conditions = Maps.newHashMap();
      conditions.put("hotspotId", vr0104Dto.hotspotId);
      List<PanoHotspotUrl> resultList = panoHotspotUrl01Mapper.selectByHotspotId(conditions);
      if (resultList != null) {
        for (PanoHotspotUrl panoHotspotUrl : resultList) {
          // 检索素材信息
          PanoMaterial panoMaterial =
              panoMaterial01Mapper.selectByPrimaryKey(panoHotspotUrl.hotspotUrlObjectId);
          if (panoMaterial != null) {
            // 拷贝素材文件
            String _srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_IMAGE,
                PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
                panoMaterial.materialId + "/");

            String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_IMAGE,
                PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
                panoMaterial.materialId + "/");
            FileServiceUtil.copyDirFromPublicStorageToAppServer(_srcPath, _destPath);
          }
        }
      }
    }

  }

  /**
   * 取得视频热点下的视屏文件
   * 
   * @throws Exception
   */
  public void getVideoFile(PanoVr0104Dto vr0104Dto) throws Exception {
    if (PanoConstantsIF.HOTSPOT_URL_TYPE_VIDEO.equals(vr0104Dto.hotspotUrlType)) {
      HashMap<String, Object> hotspotId = Maps.newHashMap();
      hotspotId.put("hotspotId", vr0104Dto.hotspotId);
      List<PanoHotspotUrl> hotspotUrl = panoHotspotUrl01Mapper.selectByHotspotId(hotspotId);

      if (hotspotUrl != null && hotspotUrl.size() > 0) {

        String materialId = hotspotUrl.get(0).hotspotUrlObjectId;

        PanoMaterial panoMaterial = panoMaterial01Mapper.selectByPrimaryKey(materialId);
        vr0104Dto.videoPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_VIDEO,
            PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
            panoMaterial.materialPath);

        String srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_VIDEO,
            PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
            panoMaterial.materialId + "/");

        String destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_VIDEO,
            PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
            panoMaterial.materialId + "/");
        FilesServiceUtil.copyDirFromPublicStorageToAppServer(srcPath, destPath);
      }

    }
  }

  /**
   * 检查单个场景下的背景音乐
   * 
   * @param _inForm
   * @throws Exception
   */
  public boolean doCheckSoundOfPanorama(PanoPanorama panoPanorama, PanoVr0104Form _inForm)
      throws Exception {
    _inForm.backGroundSoundPath = "";
    // 检索该全景图下有无独立音乐
    if (!ObjectUtils.isEmpty(_inForm.panoramaId)) {
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
        return FilesServiceUtil.copyDirFromPublicStorageToAppServer(srcPath, destPath);
      }
    }

    // 取展览背景音乐
    PanoExposition panoExposition = panoExpositionMapper.selectByPrimaryKey(_inForm.expositionId);
    if (!ObjectUtils.isEmpty(panoExposition.expositionSoundId)) {
      // 取展览背景音乐
      PanoMaterial panoMaterial =
          panoMaterial01Mapper.selectByPrimaryKey(panoExposition.expositionSoundId);
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
    return false;
  }

  /**
   * 检索展览下的背景音乐
   * 
   * @param _inForm
   * @throws Exception
   */
  public void doCheckSoundOfExposition(PanoVr0104Form _inForm) throws Exception {
    // 检索展览背景音乐
    if (!ObjectUtils.isEmpty(_inForm.expositionId)) {
      PanoExposition panoExposition = panoExpositionMapper.selectByPrimaryKey(_inForm.expositionId);
      if (panoExposition != null && !ObjectUtils.isEmpty(panoExposition.expositionSoundId)) {
        // 取得声音文件
        PanoMaterial panoMaterial =
            panoMaterial01Mapper.selectByPrimaryKey(panoExposition.expositionSoundId);
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
  }

}
