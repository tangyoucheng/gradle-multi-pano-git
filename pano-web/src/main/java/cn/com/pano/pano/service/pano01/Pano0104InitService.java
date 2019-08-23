package cn.com.pano.pano.service.pano01;

import java.io.File;
import java.text.MessageFormat;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.utils.PanoCommonUtil;
import cn.com.pano.pano.form.pano01.Pano0104Form;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.util.FwFileUtils;
import cn.com.platform.web.BaseService;


/**
 * 展览下全景图信息一览画面初期显示。
 * 
 * @author ouyangzidou
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0104InitService extends BaseService {

  // @Autowired
  // public PanoPanoramaHotspot01Mapper panoPanoramaHotspot01Mapper;
  // // @Autowired
  // // public Pano0104Mapper pano0104Mapper;
  // @Autowired
  // public PanoExpositionMap01Mapper panoExpositionMap01Mapper;
  // @Autowired
  // public PanoExpositionMapHotspot01Mapper panoExpositionMapHotspot01Mapper;
  // @Autowired
  // public PanoPanorama01Mapper panoPanorama01Mapper;
  // @Autowired
  // public PanoMaterial01Mapper panoMaterial01Mapper;
  // @Autowired
  // public PanoExpositionMapper panoExpositionMapper;
  // @Autowired
  // public PanoPolygonHotspot01Mapper panoPolygonHotspot01Mapper;
  // @Autowired
  // public Pano0104InitService pano0104InitService;
  // @Autowired
  // public Pano0104SearchService pano0104SearchService;
  // @Autowired
  // public Pano0110CreateStaticTourService pano0110CreateStaticTourService;
  // // 类静态成员变量，存储由0102传入的pageStartRowNo
  // public static String pageStartRowNoFromPano0104;

  /**
   * 场景初期显示操作。
   * 
   * @param inForm Pano0104Form
   * @throws Exception 异常的场合
   */
  public void doInit(Pano0104Form inForm) throws Exception {

    // 获取APP服务器侧文件目录。
    String destAppRelativePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W,
        UserSessionUtils.getSessionId(), "pano0104/" + inForm.getExpositionId(), "");
    inForm.panoramaPath = destAppRelativePath;
    File destAppRelativeFile = new File(FwFileUtils.getAbsolutePath(inForm.panoramaPath));
    // 全景的storage路径
    String srcPublicPath =
        MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W, inForm.expositionId, "");
    File srcPublicFile = new File(srcPublicPath).getAbsoluteFile();
    // 拷贝完整的全景到APP服务器
    if (srcPublicFile.exists() && srcPublicFile.isDirectory()) {
      destAppRelativeFile.mkdirs();
      FileUtils.copyDirectory(srcPublicFile, destAppRelativeFile, true);
    }

    // 拷贝展览素材和公共素材文件到APP服务器
    PanoCommonUtil.copyMaterialFromStorageToApp(inForm.expositionId);

    // HashMap<String, Object> condition = Maps.newHashMap();
    // condition.put("expositionId", _inForm.expositionId);
    // PanoPanoramaQuery conditions = new PanoPanoramaQuery();
    // conditions.createCriteria().andExpositionIdEqualTo(inForm.expositionId);
    // conditions.setOrderByClause("panorama_sort_key ASC");
    // // 检索全部检索全景图
    // List<PanoPanorama01Model> panoPanoramaList =
    // panoPanorama01Mapper.selectByBaseModel(conditions);

    // 加载下拉框信息
    // if (ObjectUtils.isNotEmpty(panoPanoramaList)) {
    // inForm.panoramaSelectInfo = Lists.newArrayList();
    // for (PanoPanorama01Model panoList : panoPanoramaList) {
    // inForm.panoramaSelectInfo
    // .add(new CodeValueRecord(panoList.getPanoramaName(), panoList.getPanoramaId()));
    // }
    // }
    // 检索全景图首图
    // List<PanoPanorama01Model> startPanoPanoramaList =
    // panoPanorama01Mapper.selectByBaseModel(conditions);
    // 检索展览背景音乐
    // if (!ObjectUtils.isEmpty(inForm.expositionId)) {
    // PanoExposition panoExposition = panoExpositionMapper.selectByPrimaryKey(inForm.expositionId);
    // // 取得展览名称
    // inForm.expositionName = panoExposition.getExpositionName();
    // if (panoExposition != null && !ObjectUtils.isEmpty(panoExposition.getExpositionSoundId())) {
    // // 取得声音文件
    // PanoMaterial panoMaterial =
    // panoMaterial01Mapper.selectByPrimaryKey(panoExposition.getExpositionSoundId());
    // inForm.backGroundSoundPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_SOUND,
    // PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.getExpositionId(),
    // panoMaterial.getMaterialPath());
    //
    // String srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_SOUND,
    // PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.getExpositionId(),
    // panoMaterial.getMaterialId() + "/");
    //
    // String destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_SOUND,
    // PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.getExpositionId(),
    // panoMaterial.getMaterialId() + "/");
    // FileServiceUtil.copyDirFromPublicStorageToAppServer(srcPath, destPath);
    // }
    // }
    // 检索当前展览下无全景图首图
    // if (ObjectUtils.isNotEmpty(startPanoPanoramaList) &&
    // ObjectUtils.isNotEmpty(panoPanoramaList)) {
    // // 该展览下暂时没有首图，抽取第一个全景图设置为首图
    // PanoPanorama panorama = panoPanoramaList.get(0);
    // panorama.setStartSceneFlag(FlagStatus.Enable.toString());;
    // panoPanorama01Mapper.updateByPrimaryKey(panorama);
    // inForm.panoramaId = panorama.getPanoramaId();
    // inForm.panoramaName = panorama.getPanoramaName();
    // inForm.panoramaPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
    // inForm.expositionId, panorama.getPanoramaPath() + PanoConstantsIF.PANOS_SHOW_L_XML);
    // doSearchMapInfo(inForm);
    // }
    // 展览下有全景图首图，显示首图
    // if (ObjectUtils.isNotEmpty(panoPanoramaList)) {
    // PanoPanorama panoPanorama = panoPanoramaList.get(0);
    // inForm.panoramaId = panoPanorama.getPanoramaId();
    // inForm.panoramaName = panoPanorama.getPanoramaName();
    // // inForm.panoramaPath =
    // // MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA, inForm.expositionId,
    // // startPanoPanoramaList.get(0).getPanoramaPath() + PanoConstantsIF.PANOS_SHOW_L_XML);
    //
    //// doSearchMapInfo(inForm);
    // // 检索场景视角点
    // inForm.panoramaHlookat = Objects.toString(panoPanorama.panoramaHlookat);
    // inForm.panoramaVlookat = Objects.toString(panoPanorama.panoramaVlookat);
    // }
  }

  /**
   * 有场景的情况下，加载场景相关信息的方法。
   * 
   * @param inForm Pano0104Form
   * @throws Exception
   */
  // public void doSearchMapInfo(Pano0104Form inForm) throws Exception {
  // // 检索展览下统一的推荐线路点提示信息
  // PanoExposition panoExposition = panoExpositionMapper.selectByPrimaryKey(inForm.expositionId);
  // // if (!ObjectUtils.isEmpty(panoExposition.expositionRecommendInfo)) {
  // // _inForm.expositionRecommendInfo = panoExposition.expositionRecommendInfo;
  // // } else {
  // // _inForm.expositionRecommendInfo = "推荐路线";
  // // }
  // // 检索展览下场景切换统一的提示信息
  // if (!ObjectUtils.isEmpty(panoExposition.getExpoGoSceneTooltip())) {
  // inForm.expoGoSceneTooltipInfo = panoExposition.getExpoGoSceneTooltip();
  //
  // }
  //
  // // 全景图文件取得
  // String srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_PANORAMA,
  // inForm.expositionId, inForm.panoramaId + "/");
  // String destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
  // inForm.expositionId, inForm.panoramaId + "/");
  // inForm.showCurrentMapExists =
  // FileServiceUtil.getPanoramaFileFromPublicStorage(srcPath, destPath);
  //
  // // 检索该全景图下有无独立音乐
  // if (!ObjectUtils.isEmpty(inForm.panoramaId)) {
  //
  // PanoPanorama panoPanorama = panoPanorama01Mapper.selectByPrimaryKey(inForm.panoramaId);
  // if (panoPanorama != null && !ObjectUtils.isEmpty(panoPanorama.getPanoramaSoundId())) {
  // // 取得声音文件
  // PanoMaterial panoMaterial =
  // panoMaterial01Mapper.selectByPrimaryKey(panoPanorama.getPanoramaSoundId());
  // inForm.backGroundSoundPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_SOUND,
  // PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.getExpositionId(),
  // panoMaterial.getMaterialPath());
  //
  // String srcSoundPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_SOUND,
  // PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.getExpositionId(),
  // panoMaterial.getMaterialId() + "/");
  //
  // String destSoundPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_SOUND,
  // PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.getExpositionId(),
  // panoMaterial.getMaterialId() + "/");
  // FileServiceUtil.copyDirFromPublicStorageToAppServer(srcSoundPath, destSoundPath);
  // }
  // }
  //
  // DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
  // dbf.setIgnoringElementContentWhitespace(true);
  // try {
  // // String xmlFilePath = FwFileUtils.getAbsolutePath(inForm.panoramaPath);
  // DocumentBuilder db = dbf.newDocumentBuilder();
  // Document xmldoc = db.parse(
  // FwFileUtils.getAbsolutePath(inForm.panoramaPath + inForm.panoramaId + "/show_l.xml"));
  // Element root = xmldoc.getDocumentElement();
  // if (!ObjectUtils.isEmpty(inForm.backGroundSoundPath)) {
  // root.setAttribute("onstart", "playsound(bgsnd," + inForm.backGroundSoundPath + ", 0);");
  // }
  // // root.setAttribute("onstart", "showlog(true);");
  // // 引入外部ＸＭＬ文件
  // Element newElement = xmldoc.createElement("include");
  // root.appendChild(newElement);
  // newElement.setAttribute("url",
  // "../../../../../../static/pano/pano/common/template/gallery/swipe_gallery.xml");
  //
  // newElement = xmldoc.createElement("include");
  // root.appendChild(newElement);
  // newElement.setAttribute("url",
  // "../../../../../../static/pano/pano/common/template/tooltip.xml");
  //
  // newElement = xmldoc.createElement("include");
  // root.appendChild(newElement);
  // newElement.setAttribute("url",
  // "../../../../../../static/pano/pano/common/template/radar.xml");
  //
  // newElement = xmldoc.createElement("include");
  // root.appendChild(newElement);
  // newElement.setAttribute("url",
  // "../../../../../../static/pano/pano/common/template/sound.xml");
  //
  // newElement = xmldoc.createElement("include");
  // root.appendChild(newElement);
  // newElement.setAttribute("url",
  // "../../../../../../static/pano/pano/common/template/anihotspots.xml");
  //
  // newElement = xmldoc.createElement("include");
  // root.appendChild(newElement);
  // newElement.setAttribute("url",
  // "../../../../../../static/pano/pano/common/template/recommend_path.xml");
  //
  // newElement = xmldoc.createElement("include");
  // root.appendChild(newElement);
  // newElement.setAttribute("url",
  // "../../../../../../static/pano/pano/common/template/video/videointerface.xml");
  //
  // newElement = xmldoc.createElement("include");
  // root.appendChild(newElement);
  // newElement.setAttribute("url",
  // "../../../../../../static/pano/pano/common/template/video/videoplayercontrol.xml");
  //
  // newElement = xmldoc.createElement("include");
  // root.appendChild(newElement);
  // newElement.setAttribute("url",
  // "../../../../../../static/pano/pano/common/template/video/videoplayer.xml");
  //
  // // krpano引擎图片加载完后，触发的事件
  // newElement = xmldoc.createElement("events");
  // root.appendChild(newElement);
  // newElement.setAttribute("onloadcomplete", "js(doPano0104KrpanoOnready(););");
  //
  // TransformerFactory factory = TransformerFactory.newInstance();
  // Transformer former = factory.newTransformer();
  // former.setOutputProperty(OutputKeys.INDENT, "yes");
  // former.transform(new DOMSource(xmldoc), new StreamResult(new File(
  // FwFileUtils.getAbsolutePath(inForm.panoramaPath + inForm.panoramaId + "/show_l.xml"))));
  //
  // } catch (Exception e) {
  // throw new SystemException(e);
  // }
  // if (inForm.showCurrentMapExists) {
  // // 读取数据库中的热点信息
  // HashMap<String, Object> conditions = Maps.newHashMap();
  // conditions.put("panoramaId", inForm.panoramaId);
  // if (conditions.size() > 0) {
  // List<PanoPanoramaHotspot> selectHotSpot =
  // panoPanoramaHotspot01Mapper.selectHotSpot(conditions);
  // if (selectHotSpot != null && !selectHotSpot.isEmpty()) {
  //
  // BeanUtils.copyProperties(selectHotSpot, inForm.hotspotInfoList);
  //
  // for (Pano0104Dto pano0104Dto : inForm.hotspotInfoList) {
  // // 取得首场景中应该显示的推荐路线点ID
  // PanoPanoramaHotspot fakeHotspot =
  // panoPanoramaHotspot01Mapper.selectByPrimaryKey(inForm.expositionId);
  // if (fakeHotspot != null
  // && ObjectUtils.isNotEmpty(fakeHotspot.getNextRecommendHotspotId())
  // && fakeHotspot.getNextRecommendHotspotId().equals(pano0104Dto.getHotspotId())) {
  // inForm.currentRecommendHotspotId = pano0104Dto.getHotspotId();
  // }
  // if (ObjectUtils.isNotEmpty(pano0104Dto.getHotspotImageId())) {
  // eidtHotspotImagePath(inForm, pano0104Dto);
  // }
  // if (Objects.equals(HotspotType.POLYGON.toString(), pano0104Dto.getHotspotType())) {
  // conditions.put("hotspotId", pano0104Dto.getHotspotId());
  // List<PanoPolygonHotspot> selectPoint =
  // panoPolygonHotspot01Mapper.selectBypolygonId(conditions);
  // if (selectPoint != null) {
  // pano0104Dto.pointLists = selectPoint;
  // }
  // }
  // // 如果该热点是视频热点，取得其下视频文件
  // pano0104SearchService.getVideoFile(pano0104Dto);
  //
  // // 前往生成gallery的操作
  // pano0104SearchService.doGetHotspotImageUrl(inForm, pano0104Dto);
  //
  // }
  // try {
  // inForm.hotspotInfoListJson = objectMapper.writeValueAsString(inForm.hotspotInfoList);
  // } catch (JsonProcessingException e) {
  // throw new SystemException(e);
  // }
  // }
  //
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
  // inForm.expositionId, panoExpositionMap.getExpositionMapPath());
  // inForm.expositionMapId = panoExpositionMap.getExpositionMapId();
  //
  // String srcMapPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_IMAGE,
  // inForm.expositionId, panoExpositionMap.getExpositionMapId() + "/");
  //
  // String destMapPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_IMAGE,
  // inForm.expositionId, panoExpositionMap.getExpositionMapId() + "/");
  // inForm.miniMapCheck =
  // FileServiceUtil.copyDirFromPublicStorageToAppServer(srcMapPath, destMapPath);
  // }
  // if (inForm.miniMapCheck) {
  // // 读取数据库中的地图上热点信息
  // conditions.put("expositionMapId", inForm.expositionMapId);
  // if (conditions.size() > 0) {
  // inForm.miniMapSpotInfoList =
  // panoExpositionMapHotspot01Mapper.selectMapHotspotInfo(conditions);
  // if (inForm.miniMapSpotInfoList != null) {
  // for (PanoExpositionMapHotspot expositionMapHotspot : inForm.miniMapSpotInfoList) {
  // if (inForm.panoramaId.equals(expositionMapHotspot.getPanoramaId())) {
  // inForm.selectedMiniMapHotspotId = expositionMapHotspot.getExpositionMapHotspotId();
  // // 检索地图热点的雷达角度
  // if (!ObjectUtils.isEmpty(expositionMapHotspot.getExpositionMapHotspotHeading())) {
  // inForm.radarHeading = expositionMapHotspot.getExpositionMapHotspotHeading();
  // } else {
  // // 没有雷达角度，默认雷达角度
  // inForm.radarHeading = "0";
  // }
  // }
  // }
  // }
  // try {
  // inForm.miniMapSpotInfoListJson =
  // objectMapper.writeValueAsString(inForm.miniMapSpotInfoList);
  // } catch (JsonProcessingException e) {
  // throw new SystemException(e);
  // }
  // }
  // }
  // }
  // }

  /**
   * 热点图片路径编辑
   * 
   * @param inForm
   * @param pano0203Dto
   * @throws SystemException
   * @throws Exception
   */
  // private void eidtHotspotImagePath(Pano0104Form inForm, Pano0104Dto pano0104Dto)
  // throws SystemException {
  // PanoMaterial panoMaterial =
  // panoMaterial01Mapper.selectByPrimaryKey(pano0104Dto.getHotspotImageId());
  // if (panoMaterial != null && !ObjectUtils.isEmpty(panoMaterial.getMaterialId())) {
  //
  // pano0104Dto.hotspotImagePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_IMAGE,
  // PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.getExpositionId(),
  // panoMaterial.getMaterialPath());
  //
  // String srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_IMAGE,
  // PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.getExpositionId(),
  // panoMaterial.getMaterialId() + "/");
  //
  // String destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_IMAGE,
  // PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.getExpositionId(),
  // panoMaterial.getMaterialId() + "/");
  // FileServiceUtil.copyDirFromPublicStorageToAppServer(srcPath, destPath);
  //
  // // 判断该素材是否是gif图并是否有生产对应的png图
  // pano0104Dto.hasPngImage = "false";
  // if (!ObjectUtils.isEmpty(panoMaterial.getGifDelayTime())) {
  // pano0104Dto.hasPngImage = "true";
  // pano0104Dto.gifWidth = panoMaterial.getGifWidth();
  // pano0104Dto.gifHeight = panoMaterial.getGifHeight();
  // pano0104Dto.gifFrameCount = panoMaterial.getGifFrameCount();
  // pano0104Dto.gifDelayTime = panoMaterial.getGifDelayTime();
  // }
  // }
  // }

}
