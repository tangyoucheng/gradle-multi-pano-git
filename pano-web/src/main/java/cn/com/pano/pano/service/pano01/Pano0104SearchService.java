package cn.com.pano.pano.service.pano01;

import java.io.File;
import java.text.MessageFormat;
import java.util.Arrays;
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
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.google.common.collect.Maps;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.code.HotspotType;
import cn.com.pano.pano.common.code.HotspotUrlType;
import cn.com.pano.pano.common.utils.FileServiceUtil;
import cn.com.pano.pano.common.utils.PanoCommonUtil;
import cn.com.pano.pano.dto.pano01.Pano0104Dto;
import cn.com.pano.pano.form.pano01.Pano0104Form;
import cn.com.pano.pano.mapper.common.PanoExpositionMapper;
import cn.com.pano.pano.mapper.common01.PanoExpositionMap01Mapper;
import cn.com.pano.pano.mapper.common01.PanoExpositionMapHotspot01Mapper;
import cn.com.pano.pano.mapper.common01.PanoHotspotUrl01Mapper;
import cn.com.pano.pano.mapper.common01.PanoMaterial01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanorama01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanoramaHotspot01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPolygonHotspot01Mapper;
import cn.com.pano.pano.mapper.pano01.Pano0104Mapper;
import cn.com.pano.pano.model.common.PanoExposition;
import cn.com.pano.pano.model.common.PanoExpositionMapHotspot;
import cn.com.pano.pano.model.common.PanoExpositionMapHotspotQuery;
import cn.com.pano.pano.model.common.PanoExpositionMapQuery;
import cn.com.pano.pano.model.common.PanoHotspotUrlQuery;
import cn.com.pano.pano.model.common.PanoMaterial;
import cn.com.pano.pano.model.common.PanoPanorama;
import cn.com.pano.pano.model.common.PanoPanoramaHotspot;
import cn.com.pano.pano.model.common.PanoPanoramaHotspotQuery;
import cn.com.pano.pano.model.common.PanoPanoramaQuery;
import cn.com.pano.pano.model.common.PanoPolygonHotspotQuery;
import cn.com.pano.pano.model.common01.PanoExpositionMap01Model;
import cn.com.pano.pano.model.common01.PanoHotspotUrl01Model;
import cn.com.pano.pano.model.common01.PanoPanorama01Model;
import cn.com.pano.pano.model.common01.PanoPanoramaHotspot01Model;
import cn.com.pano.pano.model.common01.PanoPolygonHotspot01Model;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.util.EasyJson;
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
public class Pano0104SearchService extends BaseService {

  @Autowired
  public PanoExpositionMapper panoExpositionMapper;
  @Autowired
  public PanoPanoramaHotspot01Mapper panoPanoramaHotspot01Mapper;
  @Autowired
  public Pano0104Mapper pano0104Mapper;
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
  public PanoPanorama01Mapper panoPanorama01Mapper;

  /**
   * 检查热点链接信息是否存在。
   * 
   * @param inForm Pano0104Form
   * @throws Exception 异常的场合
   */
  public EasyJson<Object> doSearchPano(Pano0104Form inForm) throws Exception {

    // 获取APP服务器侧文件目录。
    String destAppRelativePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
        UserSessionUtils.getSessionId(), "pano0104/" + inForm.getExpositionId(), "");
    inForm.panoramaPath = destAppRelativePath + PanoConstantsIF.PANOS_SHOW_L_XML;
    File destAppRelativeFile = new File(FwFileUtils.getAbsolutePath(destAppRelativePath));
    // 全景的storage路径
    String srcPublicPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_PANORAMA,
        inForm.expositionId, "");
    File srcPublicFile = new File(srcPublicPath).getAbsoluteFile();
    // 拷贝完整的全景到APP服务器
    if (srcPublicFile.exists() && srcPublicFile.isDirectory()) {
      destAppRelativeFile.mkdirs();
      FileUtils.copyDirectory(srcPublicFile, destAppRelativeFile, true);
    }

    PanoPanoramaQuery conditions = new PanoPanoramaQuery();
    conditions.createCriteria().andExpositionIdEqualTo(inForm.expositionId);
    conditions.setOrderByClause("panorama_sort_key ASC");
    // 检索全部检索全景图
    List<PanoPanorama01Model> panoPanoramaList = panoPanorama01Mapper.selectByBaseModel(conditions);
    // 加载下拉框信息
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setRows(panoPanoramaList);
    return easyJson;
  }

  /**
   * 检查场景的所有元素信息。
   * 
   * @param inForm Pano0104Form
   * @throws Exception 异常的场合
   */
  public EasyJson<Object> doSearchPanoElementInfo(Pano0104Form inForm) throws Exception {
    // 检索场景检查热点链接信息
    doSearchPanoHotSpotInfo(inForm);

    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setObj(inForm);
    return easyJson;
  }

  /**
   * 检索场景检查热点链接信息。
   * 
   * @param inForm Pano0104Form
   * @throws Exception 异常的场合
   */
  public void doSearchPanoHotSpotInfo(Pano0104Form inForm) throws Exception {

    // 检索展览下统一的推荐线路点提示信息
    PanoExposition panoExposition = panoExpositionMapper.selectByPrimaryKey(inForm.expositionId);
    // if (!ObjectUtils.isEmpty(panoExposition.expositionRecommendInfo)) {
    // inForm.expositionRecommendInfo = panoExposition.expositionRecommendInfo;
    // } else {
    // inForm.expositionRecommendInfo = "推荐路线";
    // }
    // 检索展览下场景切换统一的提示信息
    if (!ObjectUtils.isEmpty(panoExposition.getExpoGoSceneTooltip())) {
      inForm.expoGoSceneTooltipInfo = panoExposition.getExpoGoSceneTooltip();

    }

    // 全景图文件取得
    // String srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_PANORAMA,
    // inForm.expositionId, inForm.panoramaId + "/");
    // String destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
    // inForm.expositionId, inForm.panoramaId + "/");
    // inForm.showCurrentMapExists =
    // FileServiceUtil.getPanoramaFileFromPublicStorage(srcPath, destPath);

    // 获取APP服务器侧文件目录。
    String destAppRelativePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
        UserSessionUtils.getSessionId(), "pano0104/" + inForm.getExpositionId(), inForm.panoramaId);
    inForm.panoramaPath = destAppRelativePath + PanoConstantsIF.PANOS_SHOW_L_XML;
    File destAppRelativeFile = new File(FwFileUtils.getAbsolutePath(destAppRelativePath));
    // 全景的storage路径
    String srcPublicPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_PANORAMA,
        inForm.expositionId, inForm.panoramaId);
    File srcPublicFile = new File(srcPublicPath).getAbsoluteFile();
    // 拷贝完整的全景到APP服务器
    if (srcPublicFile.exists() && srcPublicFile.isDirectory()) {
      destAppRelativeFile.mkdirs();
      FileUtils.copyDirectory(srcPublicFile, destAppRelativeFile, true);
    }

    // 拷贝展览素材和公共素材文件到APP服务器
    PanoCommonUtil.copyMaterialFromStorageToApp(inForm.expositionId);

    // 检索展览背景音乐
    // 取得展览名称
    inForm.expositionName = panoExposition.getExpositionName();
    if (panoExposition != null && !ObjectUtils.isEmpty(panoExposition.getExpositionSoundId())) {
      // 取得声音文件
      PanoMaterial panoMaterial =
          panoMaterial01Mapper.selectByPrimaryKey(panoExposition.getExpositionSoundId());
      inForm.backGroundSoundPath =
          MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W, UserSessionUtils.getSessionId(),
              PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.getMaterialPath());
    }

    // 检索该全景图下有无独立音乐
    if (!ObjectUtils.isEmpty(inForm.panoramaId)) {
      PanoPanorama panoPanorama = panoPanorama01Mapper.selectByPrimaryKey(inForm.panoramaId);
      if (panoPanorama != null && !ObjectUtils.isEmpty(panoPanorama.getPanoramaSoundId())) {
        // 取得声音文件
        PanoMaterial panoMaterial =
            panoMaterial01Mapper.selectByPrimaryKey(panoPanorama.getPanoramaSoundId());
        inForm.backGroundSoundPath =
            MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W, UserSessionUtils.getSessionId(),
                PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.getMaterialPath());
      }
    }

    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    dbf.setIgnoringElementContentWhitespace(true);
    try {
      // String xmlFilePath = FwFileUtils.getAbsolutePath(inForm.panoramaPath);
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document xmldoc = db.parse(FwFileUtils.getAbsolutePath(destAppRelativePath + "/show_l.xml"));
      Element root = xmldoc.getDocumentElement();
      if (!ObjectUtils.isEmpty(inForm.backGroundSoundPath)) {
        root.setAttribute("onstart", "playsound(bgsnd," + inForm.backGroundSoundPath + ", 0);");
      }

      // 调试相关设定
      root.setAttribute("showerrors", "true");
      root.setAttribute("debugmode", "true");
      root.setAttribute("logkey", "true");
      
      // root.setAttribute("onstart", "showlog(true);");
      // 引入外部ＸＭＬ文件
      Element newElement = xmldoc.createElement("include");
      root.appendChild(newElement);
      newElement.setAttribute("url",
          "../../../../../../../static/pano/pano/common/template/gallery/swipe_gallery.xml");

      newElement = xmldoc.createElement("include");
      root.appendChild(newElement);
      newElement.setAttribute("url",
          "../../../../../../../static/pano/pano/common/template/tooltip.xml");

      newElement = xmldoc.createElement("include");
      root.appendChild(newElement);
      newElement.setAttribute("url",
          "../../../../../../../static/pano/pano/common/template/radar.xml");

      newElement = xmldoc.createElement("include");
      root.appendChild(newElement);
      newElement.setAttribute("url",
          "../../../../../../../static/pano/pano/common/template/sound.xml");

      newElement = xmldoc.createElement("include");
      root.appendChild(newElement);
      newElement.setAttribute("url",
          "../../../../../../../static/pano/pano/common/template/anihotspots.xml");

      newElement = xmldoc.createElement("include");
      root.appendChild(newElement);
      newElement.setAttribute("url",
          "../../../../../../../static/pano/pano/common/template/recommend_path.xml");

      newElement = xmldoc.createElement("include");
      root.appendChild(newElement);
      newElement.setAttribute("url",
          "../../../../../../../static/pano/pano/common/template/video/videointerface.xml");

      newElement = xmldoc.createElement("include");
      root.appendChild(newElement);
      newElement.setAttribute("url",
          "../../../../../../../static/pano/pano/common/template/video/videoplayercontrol.xml");

      newElement = xmldoc.createElement("include");
      root.appendChild(newElement);
      newElement.setAttribute("url",
          "../../../../../../../static/pano/pano/common/template/video/videoplayer.xml");

      // krpano引擎图片加载完后，触发的事件
      newElement = xmldoc.createElement("events");
      root.appendChild(newElement);
      newElement.setAttribute("onloadcomplete", "js(doPano0104KrpanoOnloadcomplete(););");

      TransformerFactory factory = TransformerFactory.newInstance();
      Transformer former = factory.newTransformer();
      former.setOutputProperty(OutputKeys.INDENT, "yes");
      former.transform(new DOMSource(xmldoc), new StreamResult(
          new File(FwFileUtils.getAbsolutePath(destAppRelativePath + "/show_l.xml"))));

    } catch (Exception e) {
      throw new SystemException(e);
    }

    // 读取数据库中的热点信息
    PanoPanoramaHotspotQuery conditions = new PanoPanoramaHotspotQuery();
    conditions.createCriteria().andPanoramaIdEqualTo(inForm.panoramaId);
    List<PanoPanoramaHotspot01Model> selectHotSpot =
        panoPanoramaHotspot01Mapper.selectByBaseModel(conditions);
    if (selectHotSpot != null && !selectHotSpot.isEmpty()) {
      inForm.hotspotInfoList = Arrays.asList(objectMapper
          .readValue(objectMapper.writeValueAsString(selectHotSpot), Pano0104Dto[].class));

      for (Pano0104Dto pano0104Dto : inForm.hotspotInfoList) {
        // 取得首场景中应该显示的推荐路线点ID
        PanoPanoramaHotspot fakeHotspot =
            panoPanoramaHotspot01Mapper.selectByPrimaryKey(pano0104Dto.hotspotId);
        if (fakeHotspot != null && ObjectUtils.isNotEmpty(fakeHotspot.getNextRecommendHotspotId())
            && fakeHotspot.getNextRecommendHotspotId().equals(pano0104Dto.getHotspotId())) {
          inForm.currentRecommendHotspotId = pano0104Dto.getHotspotId();
        }
        if (ObjectUtils.isNotEmpty(pano0104Dto.getHotspotImageId())) {
          eidtHotspotImagePath(inForm, pano0104Dto);
        }
        if (Objects.equals(HotspotType.POLYGON.toString(), pano0104Dto.getHotspotType())) {

          PanoPolygonHotspotQuery polygonHotspotQuery = new PanoPolygonHotspotQuery();
          polygonHotspotQuery.createCriteria().andPolygonIdEqualTo(pano0104Dto.hotspotId);
          List<PanoPolygonHotspot01Model> selectPoint =
              panoPolygonHotspot01Mapper.selectByBaseModel(polygonHotspotQuery);
          if (selectPoint != null) {
            pano0104Dto.pointList = selectPoint;
          }
        }
        // 如果该热点是视频热点，取得其下视频文件
        getVideoFile(pano0104Dto);

        // 前往生成gallery的操作
        doGetHotspotImageUrl(inForm, pano0104Dto);

      }
    }


    // 检索有无地图
    inForm.miniMapCheck = false;
    PanoExpositionMapQuery expositionMapQuery = new PanoExpositionMapQuery();
    expositionMapQuery.createCriteria().andExpositionIdEqualTo(inForm.expositionId);
    List<PanoExpositionMap01Model> panoExpositionMapList =
        panoExpositionMap01Mapper.selectByBaseModel(expositionMapQuery);
    if (ObjectUtils.isNotEmpty(panoExpositionMapList)) {
      PanoExpositionMap01Model panoExpositionMap = panoExpositionMapList.get(0);
      inForm.expositionMapPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W,
          UserSessionUtils.getSessionId(), "pano0104/" + panoExpositionMap.getExpositionMapPath());
      inForm.expositionMapId = panoExpositionMap.getExpositionMapId();

      // String srcMapPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_IMAGE,
      // inForm.expositionId, panoExpositionMap.getExpositionMapId() + "/");
      //
      // String destMapPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_IMAGE,
      // inForm.expositionId, panoExpositionMap.getExpositionMapId() + "/");
      // inForm.miniMapCheck =
      // FileServiceUtil.copyDirFromPublicStorageToAppServer(srcMapPath, destMapPath);
      inForm.miniMapCheck = true;
    }
    if (ObjectUtils.isNotEmpty(inForm.expositionMapId)) {
      // 读取数据库中的地图上热点信息
      PanoExpositionMapHotspotQuery expositionMapHotspotQuery = new PanoExpositionMapHotspotQuery();
      expositionMapHotspotQuery.createCriteria().andExpositionMapIdEqualTo(inForm.expositionMapId);
      inForm.miniMapSpotInfoList =
          panoExpositionMapHotspot01Mapper.selectByBaseModel(expositionMapHotspotQuery);
      if (inForm.miniMapSpotInfoList != null) {
        for (PanoExpositionMapHotspot expositionMapHotspot : inForm.miniMapSpotInfoList) {
          if (inForm.panoramaId.equals(expositionMapHotspot.getPanoramaId())) {
            inForm.selectedMiniMapHotspotId = expositionMapHotspot.getExpositionMapHotspotId();
            // 检索地图热点的雷达角度
            if (!ObjectUtils.isEmpty(expositionMapHotspot.getExpositionMapHotspotHeading())) {
              inForm.radarHeading = expositionMapHotspot.getExpositionMapHotspotHeading();
            } else {
              // 没有雷达角度，默认雷达角度
              inForm.radarHeading = "0";
            }
          }
        }
      }
      // inForm.miniMapSpotInfoListJson =
      // objectMapper.writeValueAsString(inForm.miniMapSpotInfoList);
    }

  }

  /**
   * 检查热点链接信息是否存在。
   * 
   * @param inForm
   * @throws Exception
   */
  public String doCheckHostspotInfo(Pano0104Form inForm) throws Exception {
    // 查找被选中的热点的URL类型信息

    PanoPanoramaHotspot panoPanoramaHotspot =
        panoPanoramaHotspot01Mapper.selectByPrimaryKey(inForm.selectedHotspotId);
    if (panoPanoramaHotspot == null) {
      // 当前热点已被其他用户删除！
      return "delete";
    }

    // 热点下链接全景图的场合
    if (HotspotUrlType.PANORAMA.toString().equals(panoPanoramaHotspot.getHotspotUrlType())) {
      return HotspotUrlType.PANORAMA.toString();
    }
    // 热点下链接素材图的场合
    if (HotspotUrlType.IMAGE.toString().equals(panoPanoramaHotspot.getHotspotUrlType())) {
      return HotspotUrlType.IMAGE.toString();
    }
    // 热点下链接声音的场合
    if (HotspotUrlType.SOUND.toString().equals(panoPanoramaHotspot.getHotspotUrlType())) {
      return HotspotUrlType.SOUND.toString();
    }
    // 热点下链接视屏的场合
    if (HotspotUrlType.VIDEO.toString().equals(panoPanoramaHotspot.getHotspotUrlType())) {
      return HotspotUrlType.VIDEO.toString();
    }
    // 热点下链接外部链接地址的场合
    if (HotspotUrlType.LINK.toString().equals(panoPanoramaHotspot.getHotspotUrlType())) {
      return HotspotUrlType.LINK.toString();
    }
    // 热点下链接素材图文的场合
    if (HotspotUrlType.TEXT_IMAGE.toString().equals(panoPanoramaHotspot.getHotspotUrlType())) {
      return HotspotUrlType.TEXT_IMAGE.toString();
    }
    // 热点下没有链接信息
    return "";
  }

  /**
   * 显示热点链接的全景图信息。
   * 
   * @param inForm Pano0104Form
   */
  public EasyJson<Object> doShowHostspotInfo(Pano0104Form inForm) throws Exception {

    // HashMap<String, Object> conditions = Maps.newHashMap();
    // conditions.put("hotspotId", inForm.selectedHotspotId);
    // conditions.put("expositionId", inForm.expositionId);

    PanoHotspotUrlQuery hotspotUrlQuery = new PanoHotspotUrlQuery();
    hotspotUrlQuery.createCriteria().andHotspotIdEqualTo(inForm.selectedHotspotId);
    List<PanoHotspotUrl01Model> panoHotspotUrlList =
        panoHotspotUrl01Mapper.selectByBaseModel(hotspotUrlQuery);
    if (ObjectUtils.isNotEmpty(panoHotspotUrlList)) {
      PanoHotspotUrl01Model panoHotspotUrl = panoHotspotUrlList.get(0);
      // 通过上个导航点ID，取得当前跳转至的场景中应该出现的推荐线路点的ID
      inForm.currentRecommendHotspotId = "";
      if (!ObjectUtils.isEmpty(inForm.theLastedSceneHotspotId)) {
        PanoPanoramaHotspot lastHotspot =
            panoPanoramaHotspot01Mapper.selectByPrimaryKey(inForm.theLastedSceneHotspotId);
        if (lastHotspot != null && !ObjectUtils.isEmpty(lastHotspot.getNextRecommendHotspotId())) {
          inForm.currentRecommendHotspotId = lastHotspot.getNextRecommendHotspotId();
        }
      }
      inForm.panoramaId = panoHotspotUrl.getHotspotUrlObjectId();


      // 更新XML文件
      String destAppRelativePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
          UserSessionUtils.getSessionId(), "pano0104/" + inForm.getExpositionId(),
          inForm.panoramaId + PanoConstantsIF.PANOS_SHOW_L_XML);

      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      dbf.setIgnoringElementContentWhitespace(true);
      try {
        // String xmlFilePath = FwFileUtils.getAbsolutePath(inForm.panoramaPath);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document xmldoc = db.parse(FwFileUtils.getAbsolutePath(destAppRelativePath));
        Element root = xmldoc.getDocumentElement();
        if (!ObjectUtils.isEmpty(inForm.backGroundSoundPath)) {
          root.setAttribute("onstart", "playsound(bgsnd," + inForm.backGroundSoundPath + ", 0);");
        }

        // 调试相关设定
        root.setAttribute("showerrors", "true");
        root.setAttribute("debugmode", "true");
        root.setAttribute("logkey", "true");
        
        // root.setAttribute("onstart", "showlog(true);");
        // 引入外部ＸＭＬ文件
        Element newElement = xmldoc.createElement("include");
        root.appendChild(newElement);
        newElement.setAttribute("url",
            "../../../../../../../static/pano/pano/common/template/gallery/swipe_gallery.xml");

        newElement = xmldoc.createElement("include");
        root.appendChild(newElement);
        newElement.setAttribute("url",
            "../../../../../../../static/pano/pano/common/template/tooltip.xml");

        newElement = xmldoc.createElement("include");
        root.appendChild(newElement);
        newElement.setAttribute("url",
            "../../../../../../../static/pano/pano/common/template/radar.xml");

        newElement = xmldoc.createElement("include");
        root.appendChild(newElement);
        newElement.setAttribute("url",
            "../../../../../../../static/pano/pano/common/template/sound.xml");

        newElement = xmldoc.createElement("include");
        root.appendChild(newElement);
        newElement.setAttribute("url",
            "../../../../../../../static/pano/pano/common/template/anihotspots.xml");

        newElement = xmldoc.createElement("include");
        root.appendChild(newElement);
        newElement.setAttribute("url",
            "../../../../../../../static/pano/pano/common/template/recommend_path.xml");

        newElement = xmldoc.createElement("include");
        root.appendChild(newElement);
        newElement.setAttribute("url",
            "../../../../../../../static/pano/pano/common/template/video/videointerface.xml");

        newElement = xmldoc.createElement("include");
        root.appendChild(newElement);
        newElement.setAttribute("url",
            "../../../../../../../static/pano/pano/common/template/video/videoplayercontrol.xml");

        newElement = xmldoc.createElement("include");
        root.appendChild(newElement);
        newElement.setAttribute("url",
            "../../../../../../../static/pano/pano/common/template/video/videoplayer.xml");

        // krpano引擎图片加载完后，触发的事件
        newElement = xmldoc.createElement("events");
        root.appendChild(newElement);
        newElement.setAttribute("onloadcomplete", "js(doPano0104KrpanoOnloadcomplete(););");

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer former = factory.newTransformer();
        former.setOutputProperty(OutputKeys.INDENT, "yes");
        former.transform(new DOMSource(xmldoc),
            new StreamResult(new File(FwFileUtils.getAbsolutePath(destAppRelativePath))));

      } catch (Exception e) {
        throw new SystemException(e);
      }


      // 加载下拉框信息
      // List<PanoPanorama> panoInfo = pano0104Mapper.selectPanoInfo(conditions);
      // if (panoInfo != null && panoInfo.size() > 0) {
      // inForm.panoramaSelectInfo = Lists.newArrayList();
      // for (PanoPanorama panoList : panoInfo) {
      // inForm.panoramaSelectInfo
      // .add(new CodeValueRecord(panoList.getPanoramaName(), panoList.getPanoramaId()));
      // }
      // }
      // 全景图文件取得
      // String _srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_PANORAMA,
      // inForm.expositionId, panoPanorama.getPanoramaId() + "/");
      // String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
      // inForm.expositionId, panoPanorama.getPanoramaId() + "/");
      // inForm.showCurrentMapExists =
      // FileServiceUtil.getPanoramaFileFromPublicStorage(_srcPath, _destPath);

      // 检索场景视角点
      PanoPanoramaHotspot panoPanoramaHotspot =
          panoPanoramaHotspot01Mapper.selectByPrimaryKey(inForm.selectedHotspotId);
      inForm.panoramaHlookat = Objects.toString(panoPanoramaHotspot.getPanoramaHlookat(), null);
      inForm.panoramaVlookat = Objects.toString(panoPanoramaHotspot.getPanoramaVlookat(), null);

      PanoPanorama panoPanorama = panoPanorama01Mapper.selectByPrimaryKey(inForm.panoramaId);
      // 读取数据库中的热点信息
      PanoPanoramaHotspotQuery panoramaHotspotQuery = new PanoPanoramaHotspotQuery();
      panoramaHotspotQuery.createCriteria().andPanoramaIdEqualTo(panoPanorama.panoramaId);
      List<PanoPanoramaHotspot01Model> selectHotSpot =
          panoPanoramaHotspot01Mapper.selectByBaseModel(panoramaHotspotQuery);
      if (selectHotSpot != null && !selectHotSpot.isEmpty()) {

        inForm.hotspotInfoList = Arrays.asList(objectMapper
            .readValue(objectMapper.writeValueAsString(selectHotSpot), Pano0104Dto[].class));

        for (Pano0104Dto pano0104Dto : inForm.hotspotInfoList) {
          if (!ObjectUtils.isEmpty(pano0104Dto.hotspotImageId)) {
            eidtHotspotImagePath(inForm, pano0104Dto);
          }
          if (Objects.equals(HotspotType.POLYGON.toString(), pano0104Dto.hotspotType)) {

            PanoPolygonHotspotQuery polygonHotspotQuery = new PanoPolygonHotspotQuery();
            polygonHotspotQuery.createCriteria().andPolygonIdEqualTo(pano0104Dto.hotspotId);
            List<PanoPolygonHotspot01Model> selectPoint =
                panoPolygonHotspot01Mapper.selectByBaseModel(polygonHotspotQuery);

            if (selectPoint != null) {
              pano0104Dto.pointList = selectPoint;
            }
          }

          // 视频热点下视频文件取得
          getVideoFile(pano0104Dto);

          // 前往生成gallery的操作
          doGetHotspotImageUrl(inForm, pano0104Dto);

        }
      }

      inForm.panoramaId = panoPanorama.panoramaId;
      inForm.panoramaName = panoPanorama.panoramaName;



      // inForm.panoramaPath = destAppRelativePath;
      // inForm.panoramaPath = panoPanorama.panoramaPath + PanoConstantsIF.PANOS_SHOW_L_XML;
      inForm.panoramaPath = inForm.panoramaId + PanoConstantsIF.PANOS_SHOW_L_XML;


      if (ObjectUtils.isNotEmpty(inForm.expositionMapId)) {
        // 读取数据库中的地图上热点信息
        inForm.selectedMiniMapHotspotId = "";
        PanoExpositionMapHotspotQuery expositionMapHotspotQuery =
            new PanoExpositionMapHotspotQuery();
        expositionMapHotspotQuery.createCriteria()
            .andExpositionMapIdEqualTo(inForm.expositionMapId);
        inForm.miniMapSpotInfoList =
            panoExpositionMapHotspot01Mapper.selectByBaseModel(expositionMapHotspotQuery);
        if (inForm.miniMapSpotInfoList != null) {
          for (PanoExpositionMapHotspot expositionMapHotspot : inForm.miniMapSpotInfoList) {
            if (inForm.panoramaId.equals(expositionMapHotspot.panoramaId)) {
              inForm.selectedMiniMapHotspotId = expositionMapHotspot.expositionMapHotspotId;
              // 检索地图热点的雷达角度
              if (!ObjectUtils.isEmpty(expositionMapHotspot.expositionMapHotspotHeading)) {
                inForm.radarHeading = expositionMapHotspot.expositionMapHotspotHeading;
              }
            }
          }
        }
      }
      // inForm.miniMapSpotInfoListJson =
      // objectMapper.writeValueAsString(inForm.miniMapSpotInfoList);
      // 检索当前场景有无独立音乐
      if (doCheckSoundOfPanorama(panoPanorama, inForm)) {
        inForm.hadIndependSound = "yes";
      }

    }

    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setObj(inForm);
    return easyJson;
  }

  /**
   * 显示热点链接的全景图信息
   * 
   * @param inForm
   * @throws Exception
   * @throws SystemException
   */
  // public void doShowHostspotInfoFromEdit(Pano0104Form inForm) throws Exception, SystemException {
  // // 检索全景图
  // HashMap<String, Object> allPanocondition = Maps.newHashMap();
  // allPanocondition.put("expositionId", inForm.expositionId);
  // PanoPanorama panoPanorama = panoPanorama01Mapper.selectByPrimaryKey(inForm.panoramaId);
  // // 如果从删除当前场景的操作返回，则显示首场景
  // if (panoPanorama == null) {
  // // // 检索全景图首图
  // panoPanorama = panoPanorama01Mapper.selectStartScenePanoInfo(allPanocondition);
  // // 没有任何全景图
  // if (panoPanorama == null) {
  // PanoExposition panoExposition =
  // panoExpositionMapper.selectByPrimaryKey(inForm.expositionId);
  // inForm.expositionName = panoExposition.expositionName;
  // inForm.panoramaId = "";
  // return;
  // }
  // }
  // inForm.panoramaName = panoPanorama.panoramaName;
  // inForm.panoramaId = panoPanorama.panoramaId;
  // // 检索展览下统一的推荐线路点提示信息
  // PanoExposition panoExposition = panoExpositionMapper.selectByPrimaryKey(inForm.expositionId);
  // inForm.expoGoSceneTooltipInfo = panoExposition.expoGoSceneTooltip;
  // if (!ObjectUtils.isEmpty(panoExposition.expositionRecommendInfo)) {
  // inForm.expositionRecommendInfo = panoExposition.expositionRecommendInfo;
  // } else {
  // inForm.expositionRecommendInfo = "推荐路线";
  // }
  // // 取得展览名称
  // inForm.expositionName = panoExposition.expositionName;
  // // 检索全部检索全景图
  // List<PanoPanorama> panoPanoramaList = pano0104Mapper.selectPanoInfo(allPanocondition);
  // // 检索首场景
  // HashMap<String, Object> startSceneCondition = Maps.newHashMap();
  // startSceneCondition.put("expositionId", inForm.expositionId);
  // PanoPanorama startPanoPanorama =
  // panoPanorama01Mapper.selectStartScenePanoInfo(startSceneCondition);
  // // 检索当前展览下无全景图首图
  // if (startPanoPanorama == null && panoPanoramaList != null && panoPanoramaList.size() > 0) {
  // // 该展览下暂时没有首图，抽取第一个全景图设置为首图
  // PanoPanorama panorama = panoPanoramaList.get(0);
  // panorama.startSceneFlag = FlagStatus.Enable.toString();
  // panoPanorama01Mapper.updateByPrimaryKey(panorama);
  // }
  //
  // // 通过上个导航点ID，取得当前跳转至的场景中应该出现的推荐线路点的ID
  // inForm.currentRecommendHotspotId = "";
  // if (startPanoPanorama != null && inForm.panoramaId.equals(startPanoPanorama.panoramaId)) {
  // // 当前返回的场景是首场景，显示首场景的推荐路线点
  // PanoPanoramaHotspot lastHotspot =
  // panoPanoramaHotspot01Mapper.selectByPrimaryKey(inForm.expositionId);
  // if (lastHotspot != null && !ObjectUtils.isEmpty(lastHotspot.nextRecommendHotspotId)) {
  // inForm.currentRecommendHotspotId = lastHotspot.nextRecommendHotspotId;
  // }
  // } else if (!ObjectUtils.isEmpty(inForm.theLastedSceneHotspotId)) {
  // // 不是首个场景，做普通检索
  // PanoPanoramaHotspot lastHotspot =
  // panoPanoramaHotspot01Mapper.selectByPrimaryKey(inForm.theLastedSceneHotspotId);
  // if (lastHotspot != null && !ObjectUtils.isEmpty(lastHotspot.nextRecommendHotspotId)) {
  // inForm.currentRecommendHotspotId = lastHotspot.nextRecommendHotspotId;
  // }
  // }
  // // 加载下拉框信息
  // HashMap<String, Object> _condition = Maps.newHashMap();
  // _condition.put("expositionId", inForm.expositionId);
  // List<PanoPanorama> panoInfo = pano0104Mapper.selectPanoInfo(_condition);
  // // if (panoInfo != null && panoInfo.size() > 0) {
  // // inForm.panoramaSelectInfo = Lists.newArrayList();
  // // for (PanoPanorama panoList : panoInfo) {
  // // inForm.panoramaSelectInfo
  // // .add(new CodeValueRecord(panoList.panoramaName, panoList.panoramaId));
  // // }
  // // }
  // if (panoPanorama != null) {
  // inForm.panoramaPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
  // inForm.expositionId, panoPanorama.panoramaPath + PanoConstantsIF.PANOS_SHOW_L_XML);
  // // 全景图文件取得
  // String _srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_PANORAMA,
  // inForm.expositionId, inForm.panoramaId + "/");
  // String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
  // inForm.expositionId, inForm.panoramaId + "/");
  // inForm.showCurrentMapExists =
  // FileServiceUtil.getPanoramaFileFromPublicStorage(_srcPath, _destPath);
  //
  // // 检索展览音乐
  // doCheckSoundOfExposition(inForm);
  // // 检索当前场景有无独立音乐
  // doCheckSoundOfPanorama(panoPanorama, inForm);
  //
  // DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
  // dbf.setIgnoringElementContentWhitespace(true);
  // try {
  // String xmlFilePath = FwFileUtils.getAbsolutePath(inForm.panoramaPath);
  // DocumentBuilder db = dbf.newDocumentBuilder();
  // Document xmldoc = db.parse(xmlFilePath);
  // Element root = xmldoc.getDocumentElement();
  // // 拼接onstart事件
  // StringBuffer onstartEvent = new StringBuffer();
  // // 载入场景音乐
  // if (!ObjectUtils.isEmpty(inForm.backGroundSoundPath)) {
  // onstartEvent
  // .append("stopallsounds();playsound(bgsnd," + inForm.backGroundSoundPath + ", 0);");
  // }
  // // 载入编辑视角点
  // if (!ObjectUtils.isEmpty(inForm.positionAthForEdit)
  // && !StringUtils.isEmpty(inForm.positionAtvForEdit)) {
  // onstartEvent.append(
  // "lookat(" + inForm.positionAthForEdit + "," + inForm.positionAtvForEdit + ");");
  // }
  // root.setAttribute("onstart", onstartEvent.toString());
  // // 引入外部ＸＭＬ文件
  // Element newElement = xmldoc.createElement("include");
  // root.appendChild(newElement);
  // newElement.setAttribute("url",
  // "../../../../framework/panorama/template/gallery/swipe_gallery.xml");
  //
  // newElement = xmldoc.createElement("include");
  // root.appendChild(newElement);
  // newElement.setAttribute("url", "../../../../framework/panorama/template/tooltip.xml");
  //
  // newElement = xmldoc.createElement("include");
  // root.appendChild(newElement);
  // newElement.setAttribute("url", "../../../../framework/panorama/template/radar.xml");
  //
  // newElement = xmldoc.createElement("include");
  // root.appendChild(newElement);
  // newElement.setAttribute("url", "../../../../framework/panorama/template/sound.xml");
  //
  // newElement = xmldoc.createElement("include");
  // root.appendChild(newElement);
  // newElement.setAttribute("url", "../../../../framework/panorama/template/anihotspots.xml");
  //
  // newElement = xmldoc.createElement("include");
  // root.appendChild(newElement);
  // newElement.setAttribute("url",
  // "../../../../framework/panorama/template/recommend_path.xml");
  //
  // newElement = xmldoc.createElement("include");
  // root.appendChild(newElement);
  // newElement.setAttribute("url",
  // "../../../../framework/panorama/template/video/videointerface.xml");
  //
  // newElement = xmldoc.createElement("include");
  // root.appendChild(newElement);
  // newElement.setAttribute("url",
  // "../../../../framework/panorama/template/video/videoplayercontrol.xml");
  //
  // newElement = xmldoc.createElement("include");
  // root.appendChild(newElement);
  // newElement.setAttribute("url",
  // "../../../../framework/panorama/template/video/videoplayer.xml");
  //
  // // krpano引擎图片加载完后，触发的事件
  // newElement = xmldoc.createElement("events");
  // root.appendChild(newElement);
  // newElement.setAttribute("onloadcomplete", "js(doPano0104KrpanoOnready(););");
  //
  // TransformerFactory factory = TransformerFactory.newInstance();
  // Transformer former = factory.newTransformer();
  // former.setOutputProperty(OutputKeys.INDENT, "yes");
  // former.transform(new DOMSource(xmldoc), new StreamResult(new File(xmlFilePath)));
  //
  // } catch (Exception e) {
  // throw new SystemException(e);
  // }
  // }
  // if (inForm.showCurrentMapExists) {
  // // 读取数据库中的热点信息
  // HashMap<String, Object> conditions = Maps.newHashMap();
  // conditions.put("panoramaId", inForm.panoramaId);
  // conditions.put("polygonId", inForm.hotspotId);
  // if (conditions.size() > 0) {
  // inForm.hotspotInfoListJson = "";
  // List<PanoPanoramaHotspot> selectHotSpot =
  // panoPanoramaHotspot01Mapper.selectHotSpot(conditions);
  // if (selectHotSpot != null && !selectHotSpot.isEmpty()) {
  // // 多边形热点坐标点序列化
  //
  // BeanUtils.copyProperties(selectHotSpot, inForm.hotspotInfoList);
  //
  // for (Pano0104Dto pano0104Dto : inForm.hotspotInfoList) {
  // // pano0104Dto.pointLists = inForm.pointInfoListJson;
  // if (!ObjectUtils.isEmpty(pano0104Dto.hotspotImageId)) {
  // eidtHotspotImagePath(inForm, pano0104Dto);
  // }
  // if (Objects.equals(PanoConstantsIF.HOTSPOT_TYPE_HOTSPOT_POLYGON,
  // pano0104Dto.hotspotType)) {
  //
  // PanoPolygonHotspotQuery polygonHotspotQuery = new PanoPolygonHotspotQuery();
  // polygonHotspotQuery.createCriteria().andPolygonIdEqualTo(pano0104Dto.hotspotId);
  // List<PanoPolygonHotspot01Model> selectPoint =
  // panoPolygonHotspot01Mapper.selectByBaseModel(polygonHotspotQuery);
  //
  // if (selectPoint != null) {
  // pano0104Dto.pointList = selectPoint;
  // }
  // }
  //
  // // 视频热点下视频文件取得
  // getVideoFile(pano0104Dto);
  // // 前往生成gallery的操作
  // doGetHotspotImageUrl(inForm, pano0104Dto);
  // }
  // inForm.hotspotInfoListJson = objectMapper.writeValueAsString(inForm.hotspotInfoList);
  // }
  // }
  // }
  //
  // // 检索有无地图
  // inForm.miniMapCheck = false;
  // HashMap<String, Object> condition = Maps.newHashMap();
  // condition.put("expositionId", inForm.expositionId);
  // PanoExpositionMap panoExpositionMap =
  // panoExpositionMap01Mapper.selectExpositionMapInfo(condition);
  // if (panoExpositionMap != null) {
  // inForm.expositionMapPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_IMAGE,
  // inForm.expositionId, panoExpositionMap.expositionMapPath);
  // inForm.expositionMapId = panoExpositionMap.expositionMapId;
  //
  // String srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_IMAGE,
  // inForm.expositionId, panoExpositionMap.expositionMapId + "/");
  //
  // String destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_IMAGE,
  // inForm.expositionId, panoExpositionMap.expositionMapId + "/");
  // inForm.miniMapCheck = FileServiceUtil.copyDirFromPublicStorageToAppServer(srcPath, destPath);
  // }
  // if (inForm.miniMapCheck) {
  // // 读取数据库中的地图上热点信息
  // inForm.selectedMiniMapHotspotId = "";
  //
  // PanoExpositionMapHotspotQuery expositionMapHotspotQuery = new PanoExpositionMapHotspotQuery();
  // expositionMapHotspotQuery.createCriteria().andExpositionMapIdEqualTo(inForm.expositionMapId);
  // inForm.miniMapSpotInfoList =
  // panoExpositionMapHotspot01Mapper.selectByBaseModel(expositionMapHotspotQuery);
  // if (inForm.miniMapSpotInfoList != null) {
  // for (PanoExpositionMapHotspot expositionMapHotspot : inForm.miniMapSpotInfoList) {
  // if (inForm.panoramaId.equals(expositionMapHotspot.panoramaId)) {
  // inForm.selectedMiniMapHotspotId = expositionMapHotspot.expositionMapHotspotId;
  // // 检索地图热点的雷达角度
  // if (!ObjectUtils.isEmpty(expositionMapHotspot.expositionMapHotspotHeading)) {
  // inForm.radarHeading = expositionMapHotspot.expositionMapHotspotHeading;
  // }
  // }
  // }
  // }
  // // inForm.miniMapSpotInfoListJson =
  // // objectMapper.writeValueAsString(inForm.miniMapSpotInfoList);
  // }
  // }

  /**
   * 检查地图上热点的链接信息。
   * 
   * @param inForm Pano0104Form
   */
  public EasyJson<Object> doShowMiniMapHotspotInfo(Pano0104Form inForm) throws Exception {
    EasyJson<Object> easyJson = new EasyJson<Object>();
    // 查找被选中的热点信息
    PanoExpositionMapHotspot panoExpositionMapHotspot =
        panoExpositionMapHotspot01Mapper.selectByPrimaryKey(inForm.selectedMiniMapHotspotId);
    if (panoExpositionMapHotspot == null) {
      // 当前热点已被其他用户删除！
      easyJson.setObj(inForm);
      return easyJson;
    }

    inForm.miniMapCheck = false;
    PanoExpositionMapQuery expositionMapQuery = new PanoExpositionMapQuery();
    expositionMapQuery.createCriteria().andExpositionIdEqualTo(inForm.expositionId);
    List<PanoExpositionMap01Model> panoExpositionMapList =
        panoExpositionMap01Mapper.selectByBaseModel(expositionMapQuery);
    if (ObjectUtils.isNotEmpty(panoExpositionMapList)) {
      PanoExpositionMap01Model panoExpositionMap = panoExpositionMapList.get(0);
      inForm.expositionMapPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W,
          UserSessionUtils.getSessionId(), "pano0104/" + panoExpositionMap.getExpositionMapPath());
      inForm.expositionMapId = panoExpositionMap.getExpositionMapId();

      // String srcMapPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_IMAGE,
      // inForm.expositionId, panoExpositionMap.getExpositionMapId() + "/");
      //
      // String destMapPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_IMAGE,
      // inForm.expositionId, panoExpositionMap.getExpositionMapId() + "/");
      // inForm.miniMapCheck =
      // FileServiceUtil.copyDirFromPublicStorageToAppServer(srcMapPath, destMapPath);
      inForm.miniMapCheck = true;
    }
    if (ObjectUtils.isNotEmpty(inForm.expositionMapId)) {
      // 读取数据库中的地图上热点信息
      PanoExpositionMapHotspotQuery expositionMapHotspotQuery = new PanoExpositionMapHotspotQuery();
      expositionMapHotspotQuery.createCriteria().andExpositionMapIdEqualTo(inForm.expositionMapId);
      inForm.miniMapSpotInfoList =
          panoExpositionMapHotspot01Mapper.selectByBaseModel(expositionMapHotspotQuery);
      if (inForm.miniMapSpotInfoList != null) {
        for (PanoExpositionMapHotspot expositionMapHotspot : inForm.miniMapSpotInfoList) {
          if (ObjectUtils.isNotEmpty(panoExpositionMapHotspot.panoramaId)
              && panoExpositionMapHotspot.panoramaId.equals(expositionMapHotspot.getPanoramaId())) {
            inForm.selectedMiniMapHotspotId = expositionMapHotspot.getExpositionMapHotspotId();
            // 没有雷达角度，默认雷达角度
            inForm.radarHeading = "0";
            // 检索地图热点的雷达角度
            if (!ObjectUtils.isEmpty(expositionMapHotspot.getExpositionMapHotspotHeading())) {
              inForm.radarHeading = expositionMapHotspot.getExpositionMapHotspotHeading();
            }
          }
        }
      }
      // inForm.miniMapSpotInfoListJson =
      // objectMapper.writeValueAsString(inForm.miniMapSpotInfoList);
    }

    // 热点下链接了全景图的场合
    if (ObjectUtils.isNotEmpty(panoExpositionMapHotspot.panoramaId)) {
      // 取得热点的雷达角度
      if (!ObjectUtils.isEmpty(panoExpositionMapHotspot.expositionMapHotspotHeading)) {
        inForm.radarHeading = panoExpositionMapHotspot.expositionMapHotspotHeading;
      }
      PanoPanorama panoPanorama =
          panoPanorama01Mapper.selectByPrimaryKey(panoExpositionMapHotspot.panoramaId);

      inForm.panoramaId = panoExpositionMapHotspot.panoramaId;
      inForm.panoramaPath = panoExpositionMapHotspot.panoramaId + PanoConstantsIF.PANOS_SHOW_L_XML;

      // 读取数据库中的热点信息
      PanoPanoramaHotspotQuery panoramaHotspotQuery = new PanoPanoramaHotspotQuery();
      panoramaHotspotQuery.createCriteria().andPanoramaIdEqualTo(panoPanorama.panoramaId);
      List<PanoPanoramaHotspot01Model> selectHotSpot =
          panoPanoramaHotspot01Mapper.selectByBaseModel(panoramaHotspotQuery);
      if (selectHotSpot != null && !selectHotSpot.isEmpty()) {

        inForm.hotspotInfoList = Arrays.asList(objectMapper
            .readValue(objectMapper.writeValueAsString(selectHotSpot), Pano0104Dto[].class));

        for (Pano0104Dto pano0104Dto : inForm.hotspotInfoList) {
          if (!ObjectUtils.isEmpty(pano0104Dto.hotspotImageId)) {
            eidtHotspotImagePath(inForm, pano0104Dto);
          }
          if (Objects.equals(PanoConstantsIF.HOTSPOT_TYPE_HOTSPOT_POLYGON,
              pano0104Dto.hotspotType)) {

            PanoPolygonHotspotQuery polygonHotspotQuery = new PanoPolygonHotspotQuery();
            polygonHotspotQuery.createCriteria().andPolygonIdEqualTo(pano0104Dto.hotspotId);
            List<PanoPolygonHotspot01Model> selectPoint =
                panoPolygonHotspot01Mapper.selectByBaseModel(polygonHotspotQuery);

            if (selectPoint != null) {
              pano0104Dto.pointList = selectPoint;
            }
          }

          // 视频热点下视频文件取得
          getVideoFile(pano0104Dto);
          // 前往生成gallery的操作
          doGetHotspotImageUrl(inForm, pano0104Dto);

        }
      }
      inForm.panoramaId = panoPanorama.panoramaId;
      inForm.panoramaName = panoPanorama.panoramaName;
      inForm.radarHeading = panoExpositionMapHotspot.expositionMapHotspotHeading;

      // 检索当前场景有无独立音乐
      if (doCheckSoundOfPanorama(panoPanorama, inForm)) {
        inForm.hadIndependSound = "yes";
      }
    }

    easyJson.setObj(inForm);
    return easyJson;
  }

  /**
   * 点击下拉框后跳转到对应场景
   * 
   * @param pano0104Form
   * @return
   * @throws Exception
   */
  // public String doShowFromPanoSelected(Pano0104Form inForm) throws Exception {
  // if (!ObjectUtils.isEmpty(inForm.panoramaId)) {
  // // 加载下拉框信息
  // HashMap<String, Object> _condition = Maps.newHashMap();
  // _condition.put("expositionId", inForm.expositionId);
  // List<PanoPanorama> panoInfo = pano0104Mapper.selectPanoInfo(_condition);
  // // if (panoInfo != null && panoInfo.size() > 0) {
  // // inForm.panoramaSelectInfo = Lists.newArrayList();
  // // for (PanoPanorama panoList : panoInfo) {
  // // inForm.panoramaSelectInfo
  // // .add(new CodeValueRecord(panoList.panoramaName, panoList.panoramaId));
  // // }
  // // }
  // PanoPanorama panoPanorama = panoPanorama01Mapper.selectByPrimaryKey(inForm.panoramaId);
  // // 设置场景视角点
  // inForm.panoramaHlookat = Objects.toString(panoPanorama.panoramaHlookat);
  // inForm.panoramaVlookat = Objects.toString(panoPanorama.panoramaVlookat);
  //
  // String filePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
  // inForm.expositionId, panoPanorama.panoramaPath + PanoConstantsIF.PANOS_SHOW_L_XML);
  // inForm.panoramaPath = filePath;
  // // 全景图文件取得
  // String _srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_PANORAMA,
  // inForm.expositionId, panoPanorama.panoramaId + "/");
  // String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
  // inForm.expositionId, panoPanorama.panoramaId + "/");
  // inForm.showCurrentMapExists =
  // FileServiceUtil.getPanoramaFileFromPublicStorage(_srcPath, _destPath);
  //
  // if (inForm.showCurrentMapExists) {
  // // 读取数据库中的热点信息
  // HashMap<String, Object> conditions = Maps.newHashMap();
  // conditions.put("panoramaId", panoPanorama.panoramaId);
  // if (conditions.size() > 0) {
  // List<PanoPanoramaHotspot> selectHotSpot =
  // panoPanoramaHotspot01Mapper.selectHotSpot(conditions);
  // if (selectHotSpot != null && !selectHotSpot.isEmpty()) {
  // BeanUtils.copyProperties(selectHotSpot, inForm.hotspotInfoList);
  // for (Pano0104Dto pano0104Dto : inForm.hotspotInfoList) {
  // if (!ObjectUtils.isEmpty(pano0104Dto.hotspotImageId)) {
  // eidtHotspotImagePath(inForm, pano0104Dto);
  // }
  // if (Objects.equals(PanoConstantsIF.HOTSPOT_TYPE_HOTSPOT_POLYGON,
  // pano0104Dto.hotspotType)) {
  // conditions.put("hotspotId", pano0104Dto.hotspotId);
  // List<PanoPolygonHotspot> selectPoint =
  // panoPolygonHotspot01Mapper.selectBypolygonId(conditions);
  // if (selectPoint != null) {
  // pano0104Dto.pointLists = selectPoint;
  // }
  // }
  //
  // // 视频热点下视频文件取得
  // getVideoFile(pano0104Dto);
  // // 前往生成gallery的操作
  // doGetHotspotImageUrl(inForm, pano0104Dto);
  //
  // }
  // }
  // }
  //
  // inForm.panoramaId = panoPanorama.panoramaId;
  // inForm.panoramaName = panoPanorama.panoramaName;
  // inForm.panoramaPath = panoPanorama.panoramaPath + PanoConstantsIF.PANOS_SHOW_L_XML;
  //
  // }
  //
  // // 读取数据库中的地图上热点信息
  // _condition.put("expositionMapId", inForm.expositionMapId);
  // inForm.selectedMiniMapHotspotId = "";
  // if (_condition.size() > 0) {
  // inForm.miniMapSpotInfoList =
  // panoExpositionMapHotspot01Mapper.selectMapHotspotInfo(_condition);
  // if (inForm.miniMapSpotInfoList != null) {
  // for (PanoExpositionMapHotspot expositionMapHotspot : inForm.miniMapSpotInfoList) {
  // if (inForm.panoramaId.equals(expositionMapHotspot.panoramaId)) {
  // inForm.selectedMiniMapHotspotId = expositionMapHotspot.expositionMapHotspotId;
  // // 检索地图热点的雷达角度
  // if (!ObjectUtils.isEmpty(expositionMapHotspot.expositionMapHotspotHeading)) {
  // inForm.radarHeading = expositionMapHotspot.expositionMapHotspotHeading;
  // }
  // }
  // }
  // }
  // // inForm.miniMapSpotInfoListJson =
  // // objectMapper.writeValueAsString(inForm.miniMapSpotInfoList);
  // }
  // // 检索当前场景有无独立音乐
  // if (doCheckSoundOfPanorama(panoPanorama, inForm)) {
  // inForm.hadIndependSound = "yes";
  // }
  // return objectMapper.writeValueAsString(inForm);
  //
  // }
  // return "";
  // }

  /**
   * 热点图片路径编辑
   * 
   * @param inForm
   * @param pano0203Dto
   * @throws Exception
   */
  private void eidtHotspotImagePath(Pano0104Form inForm, Pano0104Dto pano0104Dto) throws Exception {
    PanoMaterial panoMaterial = panoMaterial01Mapper.selectByPrimaryKey(pano0104Dto.hotspotImageId);
    if (panoMaterial != null && !ObjectUtils.isEmpty(panoMaterial.materialId)) {

      pano0104Dto.hotspotImagePath = panoMaterial.materialPath;

      // pano0104Dto.hotspotImagePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_IMAGE,
      // PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
      // panoMaterial.materialPath);
      //
      // String _srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_IMAGE,
      // PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
      // panoMaterial.materialId + "/");
      //
      // String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_IMAGE,
      // PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
      // panoMaterial.materialId + "/");
      // FileServiceUtil.copyDirFromPublicStorageToAppServer(_srcPath, _destPath);

      // 判断该素材是否是gif图并是否有生产对应的png图
      pano0104Dto.hasPngImage = "false";
      if (!ObjectUtils.isEmpty(panoMaterial.gifDelayTime)) {
        pano0104Dto.hasPngImage = "true";
        pano0104Dto.gifWidth = panoMaterial.gifWidth;
        pano0104Dto.gifHeight = panoMaterial.gifHeight;
        pano0104Dto.gifFrameCount = panoMaterial.gifFrameCount;
        pano0104Dto.gifDelayTime = panoMaterial.gifDelayTime;
      }

    }
  }

  /**
   * 取得普通热点链接的素材图信息
   * 
   * @param inForm
   * @return
   * @throws Exception
   * @throws Exception
   */
  public void doGetHotspotImageUrl(Pano0104Form inForm, Pano0104Dto pano0104Dto) throws Exception {
    // 为热点内容为素材信息图的热点生成gallery层
    if (ObjectUtils.isNotEmpty(pano0104Dto.getHotspotId())
        && PanoConstantsIF.HOTSPOT_URL_TYPE_IMAGE.equals(pano0104Dto.getHotspotUrlType())) {
      // 检查是否有素材图，有则为该热点添加gallery节点
      PanoHotspotUrlQuery conditions = new PanoHotspotUrlQuery();
      conditions.createCriteria().andHotspotIdEqualTo(pano0104Dto.getHotspotId());
      List<PanoHotspotUrl01Model> resultList = panoHotspotUrl01Mapper.selectByBaseModel(conditions);
      if (resultList != null) {
        // 场景的gallery节点追加
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringElementContentWhitespace(true);

        String destAppRelativePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
            UserSessionUtils.getSessionId(), "pano0104/" + inForm.getExpositionId(),
            inForm.panoramaId);
        String panoramaPath = destAppRelativePath + PanoConstantsIF.PANOS_SHOW_L_XML;
        if (inForm.panoramaPath.contains(PanoConstantsIF.APP_SERVER_TEMP_SESSION_FOLDER)) {
          panoramaPath = inForm.panoramaPath;
        }

        String xmlFilePath = FwFileUtils.getAbsolutePath(panoramaPath);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document _xmldoc = db.parse(xmlFilePath);
        Element oldRoot = _xmldoc.getDocumentElement();
        Element galleryElement = _xmldoc.createElement("gallery");
        oldRoot.appendChild(galleryElement);
        galleryElement.setAttribute("name", "v_" + pano0104Dto.getHotspotId() + "_gallery");

        for (PanoHotspotUrl01Model panoHotspotUrl : resultList) {
          // gallery下的img节点追加
          Element galleryImgElement = _xmldoc.createElement("img");
          galleryElement.appendChild(galleryImgElement);
          galleryImgElement.setAttribute("name", "img" + panoHotspotUrl.getSortKey());
          // 检索素材信息
          PanoMaterial panoMaterial =
              panoMaterial01Mapper.selectByPrimaryKey(panoHotspotUrl.getHotspotUrlObjectId());
          if (panoMaterial != null) {
            // 如果该素材是gif图
            if (panoMaterial.getMaterialPath().toLowerCase().endsWith(".gif")
                && !ObjectUtils.isEmpty(panoMaterial.getGifDelayTime())) {

              // 转为png
              String materialPath = panoMaterial.getMaterialPath().substring(0,
                  panoMaterial.getMaterialPath().lastIndexOf(".")) + ".png";

              materialPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W,
                  UserSessionUtils.getSessionId(), panoMaterial.getMaterialPath());


              // 创建style
              Element galleryStyleElement = _xmldoc.createElement("style");
              oldRoot.appendChild(galleryStyleElement);
              galleryStyleElement.setAttribute("name",
                  "v_" + panoMaterial.getMaterialId() + "_gifstyle");
              galleryStyleElement.setAttribute("url", "../../../../../../../" + materialPath);
              galleryStyleElement.setAttribute("crop",
                  "0|0|" + panoMaterial.getGifWidth() + "|" + panoMaterial.getGifHeight());
              galleryStyleElement.setAttribute("framewidth", panoMaterial.getGifWidth());
              galleryStyleElement.setAttribute("frameheight", panoMaterial.getGifHeight());
              galleryStyleElement.setAttribute("frame", "0");
              galleryStyleElement.setAttribute("lastframe", panoMaterial.getGifFrameCount());
              galleryStyleElement.setAttribute("onloaded",
                  "hotspot_animate(" + panoMaterial.getGifDelayTime() + ");");
              // 为img节点设置style属性
              galleryImgElement.setAttribute("gifstyle",
                  "v_" + panoMaterial.getMaterialId() + "_gifstyle");

            } else {

              // 转为png
              String materialPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W,
                  UserSessionUtils.getSessionId(), panoMaterial.getMaterialPath());

              galleryImgElement.setAttribute("url", "../../../../../../../" + materialPath);
            }
            // // 拷贝素材文件
            // String _srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_IMAGE,
            // PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.getExpositionId(),
            // panoMaterial.getMaterialId() + "/");
            //
            // String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_IMAGE,
            // PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.getExpositionId(),
            // panoMaterial.getMaterialId() + "/");
            // FileServiceUtil.copyDirFromPublicStorageToAppServer(_srcPath, _destPath);

          }
        }
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer former = factory.newTransformer();
        former.setOutputProperty(OutputKeys.INDENT, "yes");
        former.transform(new DOMSource(_xmldoc), new StreamResult(new File(xmlFilePath)));
      }
    } else if (HotspotUrlType.TEXT_IMAGE.toString().equals(pano0104Dto.getHotspotUrlType())) {
      // 为热点内容为图文
      // 检查是否有素材图
      // HashMap<String, Object> conditions = Maps.newHashMap();
      // conditions.put("hotspotId", pano0104Dto.getHotspotId());
      // List<PanoHotspotUrl> resultList = panoHotspotUrl01Mapper.selectByHotspotId(conditions);
      // if (resultList != null) {
      // for (PanoHotspotUrl panoHotspotUrl : resultList) {
      // // 检索素材信息
      // PanoMaterial panoMaterial =
      // panoMaterial01Mapper.selectByPrimaryKey(panoHotspotUrl.getHotspotUrlObjectId());
      // if (panoMaterial != null) {
      // // 拷贝素材文件
      // String _srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_IMAGE,
      // PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.getExpositionId(),
      // panoMaterial.getMaterialId() + "/");
      //
      // String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_IMAGE,
      // PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.getExpositionId(),
      // panoMaterial.getMaterialId() + "/");
      // FileServiceUtil.copyDirFromPublicStorageToAppServer(_srcPath, _destPath);
      // }
      // }
      // }
    }

  }

  /**
   * 取得视频热点下的视屏文件
   * 
   * @throws SystemException
   * 
   * @throws Exception
   */
  public void getVideoFile(Pano0104Dto pano0104Dto) throws SystemException {
    if (HotspotUrlType.VIDEO.toString().equals(pano0104Dto.getHotspotUrlType())) {
      PanoHotspotUrlQuery conditions = new PanoHotspotUrlQuery();
      conditions.createCriteria().andHotspotIdEqualTo(pano0104Dto.getHotspotId());
      List<PanoHotspotUrl01Model> hotspotUrl = panoHotspotUrl01Mapper.selectByBaseModel(conditions);

      if (hotspotUrl != null && hotspotUrl.size() > 0) {
        String materialId = hotspotUrl.get(0).getHotspotUrlObjectId();
        PanoMaterial panoMaterial = panoMaterial01Mapper.selectByPrimaryKey(materialId);
        pano0104Dto.videoPath = panoMaterial.materialPath;
        // pano0104Dto.videoPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_VIDEO,
        // PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.getExpositionId(),
        // panoMaterial.getMaterialPath());
        //
        // String srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_VIDEO,
        // PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.getExpositionId(),
        // panoMaterial.getMaterialId() + "/");
        //
        // String destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_VIDEO,
        // PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.getExpositionId(),
        // panoMaterial.getMaterialId() + "/");
        // FileServiceUtil.copyDirFromPublicStorageToAppServer(srcPath, destPath);
      }

    }
  }

  /**
   * 检查单个场景下的背景音乐
   * 
   * @param inForm
   * @throws Exception
   */
  public boolean doCheckSoundOfPanorama(PanoPanorama panoPanorama, Pano0104Form inForm)
      throws Exception {
    inForm.backGroundSoundPath = "";
    // 检索该全景图下有无独立音乐
    if (!ObjectUtils.isEmpty(inForm.panoramaId)) {
      if (panoPanorama != null && !ObjectUtils.isEmpty(panoPanorama.panoramaSoundId)) {
        // 取得声音文件
        PanoMaterial panoMaterial =
            panoMaterial01Mapper.selectByPrimaryKey(panoPanorama.panoramaSoundId);
        inForm.backGroundSoundPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_SOUND,
            PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
            panoMaterial.materialPath);

        String srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_SOUND,
            PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
            panoMaterial.materialId + "/");

        String destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_SOUND,
            PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
            panoMaterial.materialId + "/");
        return FileServiceUtil.copyDirFromPublicStorageToAppServer(srcPath, destPath);
      }
    }

    // 取展览背景音乐
    PanoExposition panoExposition = panoExpositionMapper.selectByPrimaryKey(inForm.expositionId);
    if (!ObjectUtils.isEmpty(panoExposition.expositionSoundId)) {
      // 取展览背景音乐
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
      FileServiceUtil.copyDirFromPublicStorageToAppServer(srcPath, destPath);
    }
    return false;
  }

  /**
   * 检索展览下的背景音乐
   * 
   * @param inForm
   * @throws Exception
   */
  public void doCheckSoundOfExposition(Pano0104Form inForm) throws Exception {
    // 检索展览背景音乐
    if (ObjectUtils.isNotEmpty(inForm.expositionId)) {
      PanoExposition panoExposition = panoExpositionMapper.selectByPrimaryKey(inForm.expositionId);
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
        FileServiceUtil.copyDirFromPublicStorageToAppServer(srcPath, destPath);
      }
    }
  }
}
