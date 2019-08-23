package cn.com.pano.panovr.service.panovr01;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import org.apache.commons.lang3.StringUtils;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.google.common.collect.Maps;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.code.HotspotType;
import cn.com.pano.pano.form.pano01.Pano0110Form;
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
import cn.com.pano.pano.model.common.PanoExpositionMapHotspotQuery;
import cn.com.pano.pano.model.common.PanoExpositionMapQuery;
import cn.com.pano.pano.model.common.PanoHotspotUrlQuery;
import cn.com.pano.pano.model.common.PanoMaterial;
import cn.com.pano.pano.model.common.PanoPanorama;
import cn.com.pano.pano.model.common.PanoPanoramaHotspot;
import cn.com.pano.pano.model.common.PanoPanoramaHotspotQuery;
import cn.com.pano.pano.model.common.PanoPanoramaQuery;
import cn.com.pano.pano.model.common.PanoPolygonHotspot;
import cn.com.pano.pano.model.common.PanoPolygonHotspotQuery;
import cn.com.pano.pano.model.common01.PanoExpositionMap01Model;
import cn.com.pano.pano.model.common01.PanoExpositionMapHotspot01Model;
import cn.com.pano.pano.model.common01.PanoHotspotUrl01Model;
import cn.com.pano.pano.model.common01.PanoPanorama01Model;
import cn.com.pano.pano.model.common01.PanoPanoramaHotspot01Model;
import cn.com.pano.pano.model.common01.PanoPolygonHotspot01Model;
import cn.com.pano.pano.service.pano01.Pano0110CreateStaticTourService;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.util.FwFileUtils;
import cn.com.platform.util.MessageUtils;
import cn.com.platform.web.BaseService;

/**
 * 静态展览生成
 * 
 * @author yangyuzhen
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0110CreateStaticVrService extends BaseService {
  @Autowired
  public PanoExpositionMapper panoExpositionMapper;
  @Autowired
  public PanoExpositionMap01Mapper panoExpositionMap01Mapper;
  @Autowired
  public PanoPolygonHotspot01Mapper panoPolygonHotspot01Mapper;
  @Autowired
  public PanoHotspotUrl01Mapper panoHotspotUrl01Mapper;
  @Autowired
  public PanoMaterial01Mapper panoMaterial01Mapper;
  @Autowired
  public PanoPanorama01Mapper panoPanorama01Mapper;
  @Autowired
  public PanoPanoramaHotspot01Mapper panoPanoramaHotspot01Mapper;
  @Autowired
  public PanoExpositionMapHotspot01Mapper panoExpositionMapHotspot01Mapper;
  @Autowired
  public Pano0110CreateStaticTourService pano0110CreateStaticTourService;

  /**
   * 生成静态展览
   * 
   * @param inForm
   * @throws Exception
   */
  public void doCreateStaticVr(Pano0110Form inForm) throws Exception {
    String outputExpositionId = inForm.selectedExpositionId + "_vr";
    // 如果先前有发布成功的，先删除先前发布的文件夹
    String destPath = MessageFormat.format("statictour_app/{0}/", outputExpositionId);
    FileUtils.deleteDirectory(new File(FwFileUtils.getAbsolutePath(destPath)));
    // 将展览数据拷贝到静态页面目录
    String srcPath =
        MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W, inForm.selectedExpositionId);
    File srcPublicFile = new File(srcPath).getAbsoluteFile();
    if (srcPublicFile.exists() && srcPublicFile.isDirectory()) {
      FileUtils.copyDirectory(srcPublicFile, new File(FwFileUtils.getAbsolutePath(destPath)), true);
    }

    // 将热点素材拷贝到静态页面目录下
    // 公共素材
    srcPath =
        MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W, "material/common_material");
    srcPublicFile = new File(srcPath).getAbsoluteFile();
    destPath = MessageFormat.format("statictour_app/{0}/com_mats/", outputExpositionId);
    if (srcPublicFile.exists() && srcPublicFile.isDirectory()) {
      FileUtils.copyDirectory(srcPublicFile, new File(FwFileUtils.getAbsolutePath(destPath)), true);
    }
    // 展览素材
    srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W,
        "material/" + inForm.selectedExpositionId);
    srcPublicFile = new File(srcPath).getAbsoluteFile();
    destPath = MessageFormat.format("statictour_app/{0}/expo_mats/", outputExpositionId);
    if (srcPublicFile.exists() && srcPublicFile.isDirectory()) {
      FileUtils.copyDirectory(srcPublicFile, new File(FwFileUtils.getAbsolutePath(destPath)), true);
    }

    // 将framework拷贝到静态页面目录
    srcPath = "static/pano/pano/common/";
    File srcFile = new File(FwFileUtils.getAbsolutePath(srcPath));
    destPath = MessageFormat.format("statictour_app/{0}/framework/panorama/", outputExpositionId);
    if (srcFile.exists() && srcFile.isDirectory()) {
      FileUtils.copyDirectory(srcFile, new File(FwFileUtils.getAbsolutePath(destPath)), true);
    }

    // 将 show.xml拷贝到静态页面目录
    srcPath = "static/pano/pano/common/template/staticvtour/";
    srcFile = new File(FwFileUtils.getAbsolutePath(srcPath));
    destPath = MessageFormat.format("statictour_app/{0}/", outputExpositionId);
    if (srcFile.exists() && srcFile.isDirectory()) {
      FileUtils.copyDirectory(srcFile, new File(FwFileUtils.getAbsolutePath(destPath)), true);
    }

    // 生成base.js文件
    pano0110CreateStaticTourService.createJs(inForm, destPath);
    creatXml(inForm, destPath);

    // 压缩展览
    File expositionFile = new File(FwFileUtils
        .getAbsolutePath(MessageFormat.format("statictour_app/{0}", outputExpositionId)));
    List<File> files = new ArrayList<File>();
    files.add(expositionFile);
    File zipFile = new File(FwFileUtils
        .getAbsolutePath(MessageFormat.format("statictour_app/{0}.zip", outputExpositionId)));
    FwFileUtils.toZip(files, zipFile);
  }

  /**
   * 创建show.xml
   * 
   * @param inForm
   * @throws Exception
   */
  public void creatXml(Pano0110Form inForm, String _destPath) throws Exception {
    String xmlPath = _destPath + "show.xml";

    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    dbf.setIgnoringElementContentWhitespace(true);

    String xmlFilePath = FwFileUtils.getAbsolutePath(xmlPath);
    DocumentBuilder db = dbf.newDocumentBuilder();
    Document _xmldoc = db.parse(xmlFilePath);

    Element oldRoot = _xmldoc.getDocumentElement();
    // 清空XML
    _xmldoc.removeChild(oldRoot);

    // 根节点追加
    Element newRoot = _xmldoc.createElement("krpano");
    _xmldoc.appendChild(newRoot);
    PanoPanoramaQuery panoramaQuery = new PanoPanoramaQuery();
    panoramaQuery.createCriteria().andExpositionIdEqualTo(inForm.selectedExpositionId);
    panoramaQuery.setOrderByClause("panorama_sort_key ASC");
    List<PanoPanorama01Model> sceneInfoList = panoPanorama01Mapper.selectByBaseModel(panoramaQuery);
    PanoPanorama01Model firstSceneInfo = sceneInfoList.get(0);

    // 拼接onstart事件
    StringBuffer onstartEvent = new StringBuffer();
    // 设定收个场景对应的缩略图的边框。
    onstartEvent.append("\r\n        ");
    onstartEvent.append("skin_getBoder('v_" + firstSceneInfo.panoramaId + "');");
    if (!ObjectUtils.isEmpty(firstSceneInfo.panoramaHlookat)
        && !StringUtils.isEmpty(firstSceneInfo.panoramaVlookat)) {
      // 加载场景的参数
      StringBuffer _args = new StringBuffer();
      _args.append("view[v_" + firstSceneInfo.panoramaId + "_view].hlookat="
          + firstSceneInfo.panoramaHlookat);
      _args.append("&view[v_" + firstSceneInfo.panoramaId + "_view].vlookat="
          + firstSceneInfo.panoramaVlookat);
      // 加载场景
      onstartEvent.append("\r\n        ");
      onstartEvent.append("loadscene(v_" + firstSceneInfo.panoramaId + ",");
      onstartEvent.append(_args);
      onstartEvent.append(",MERGE,BLEND(1));");
    } else {
      onstartEvent.append("\r\n        ");
      onstartEvent.append("loadscene(v_" + firstSceneInfo.panoramaId + ",null,MERGE,BLEND(1));");
    }

    // 检索onstart场景有无公共或独立背景音乐
    PanoExposition panoExposition =
        panoExpositionMapper.selectByPrimaryKey(inForm.selectedExpositionId);
    String aboutSound = "";
    if (panoExposition.expositionSoundId != null
        && !ObjectUtils.isEmpty(panoExposition.expositionSoundId)) {
      // 有公共音乐
      PanoMaterial expositionSound =
          panoMaterial01Mapper.selectByPrimaryKey(panoExposition.expositionSoundId);
      aboutSound =
          "playsound(expositionbgsnd," + doGetMaterialFolderPath(expositionSound.expositionId,
              inForm, expositionSound.materialPath) + ",0);";
      if (firstSceneInfo.panoramaSoundId != null) {
        // 如果该场景 有音乐
        PanoMaterial panoramaSound =
            panoMaterial01Mapper.selectByPrimaryKey(firstSceneInfo.panoramaSoundId);
        if (panoramaSound != null && !ObjectUtils.isEmpty(panoramaSound.materialPath)) {
          aboutSound += "stopsound('expositionbgsnd');";
        }
      }
      // 如果该场景没有音乐 播放展览音乐
      onstartEvent.append("\r\n        ");
      onstartEvent.append(aboutSound);
    }

    // 检索与场景相关的地图热点
    PanoExpositionMapHotspotQuery expositionMapHotspotQuery = new PanoExpositionMapHotspotQuery();
    expositionMapHotspotQuery.createCriteria().andPanoramaIdEqualTo(firstSceneInfo.panoramaId);
    List<PanoExpositionMapHotspot01Model> mapHotspotList =
        panoExpositionMapHotspot01Mapper.selectByBaseModel(expositionMapHotspotQuery);
    // 检索相关联的地图热点
    if (ObjectUtils.isNotEmpty(mapHotspotList)) {
      PanoExpositionMapHotspot01Model mapHotspot = mapHotspotList.get(0);
      onstartEvent.append("\r\n        ");
      onstartEvent.append("activatespot(V_" + mapHotspot.expositionMapHotspotId + ",0);");
      // 初始化时设定首场景地图上选中的热点zorder小于其他地图上热点，true为需要在拼接时换行
      onstartEvent = setMapHotspotZorder(onstartEvent, true, inForm, mapHotspot);
      // 设定相关联的地图热点的雷达角度
      if (!ObjectUtils.isEmpty(mapHotspot.expositionMapHotspotHeading)) {
        onstartEvent.append("\r\n        ");
        onstartEvent
            .append("set(layer[radar].heading," + mapHotspot.expositionMapHotspotHeading + ");");
      }
    } else {
      onstartEvent.append("\r\n        ");
      onstartEvent.append("activatespot(V_,0);");
    }

    // 在非手机端
    onstartEvent.append("\r\n        ");
    onstartEvent.append("if(device.mobile == false,");
    // 初期化隐藏工具栏
    onstartEvent.append("\r\n        ");
    onstartEvent.append("skin_bar_v();");
    // 拼接自定义工具条的内容
    PanoExposition expo = panoExpositionMapper.selectByPrimaryKey(inForm.selectedExpositionId);
    if (expo != null && !ObjectUtils.isEmpty(expo.expositionSelectedButtons)) {
      String[] strs = expo.expositionSelectedButtons.split(",");
      // 显示自定义工作条中的按钮
      for (int i = 0; i < strs.length; i++) {
        onstartEvent.append("\r\n        ");
        onstartEvent.append("set(layer[" + strs[i] + "].x," + i * 40 + ");");
        onstartEvent.append("\r\n        ");
        onstartEvent.append("set(layer[" + strs[i] + "].visible,true);");
        onstartEvent.append("\r\n        ");
        onstartEvent.append("set(layer[defaultskin_buttons].width," + strs.length * 40 + ");");
      }
    }
    onstartEvent.append("\r\n        ");
    onstartEvent.append(");");

    // 设置页面title
    if (panoExposition != null) {
      newRoot.setAttribute("exprotitle", panoExposition.expositionName);
    }

    // 拼接推荐信息plugin
    onstartEvent.append("\r\n        ");
    onstartEvent.append("showrecommend(3,recommedInfoHotspot);");
    // 载入首个场景时，点亮首场景里的推荐线路点
    PanoPanoramaHotspot currentHotspot =
        panoPanoramaHotspot01Mapper.selectByPrimaryKey(inForm.selectedExpositionId);
    if (currentHotspot != null && !ObjectUtils.isEmpty(currentHotspot.nextRecommendHotspotId)) {

      onstartEvent.append("\r\n        ");
      onstartEvent.append("set(hotspot[recommedInfoHotspot].ath, get(hotspot[v_"
          + currentHotspot.nextRecommendHotspotId + "].ath));");
      onstartEvent.append("\r\n        ");
      onstartEvent.append("set(hotspot[recommedInfoHotspot].atv, get(hotspot[v_"
          + currentHotspot.nextRecommendHotspotId + "].atv));");
      onstartEvent.append("\r\n        ");
      onstartEvent.append("set(hotspot[recommedInfoHotspot].visible,true);");

      // 设置推荐路线点的distorted为false
      onstartEvent.append("\r\n        ");
      onstartEvent
          .append("set(hotspot[v_" + currentHotspot.nextRecommendHotspotId + "].distorted,false);");
    }
    onstartEvent.append("\r\n        ");

    newRoot.setAttribute("onstart", "onstart_action();");

    // 调试相关设定
    newRoot.setAttribute("showerrors", "false");
    newRoot.setAttribute("debugmode", "false");
    newRoot.setAttribute("logkey", "false");

    // 引入外部ＸＭＬ文件

    Comment elementComment = _xmldoc.createComment("引入外部ＸＭＬ文件");
    newRoot.appendChild(elementComment);
    Element newElement = _xmldoc.createElement("include");
    newRoot.appendChild(newElement);
    newElement.setAttribute("url", "framework/panorama/template/video/videoplayer.xml");

    newElement = _xmldoc.createElement("include");
    newRoot.appendChild(newElement);
    newElement.setAttribute("url", "framework/panorama/template/video/videointerface.xml");

    newElement = _xmldoc.createElement("include");
    newRoot.appendChild(newElement);
    newElement.setAttribute("url", "framework/panorama/template/video/videoplayercontrol.xml");

    newElement = _xmldoc.createElement("include");
    newRoot.appendChild(newElement);
    newElement.setAttribute("url", "framework/panorama/template/progress_loadinganimation.xml");

    newElement = _xmldoc.createElement("include");
    newRoot.appendChild(newElement);
    newElement.setAttribute("url", "framework/panorama/template/progress_loadingpercent.xml");

    newElement = _xmldoc.createElement("include");
    newRoot.appendChild(newElement);
    newElement.setAttribute("url", "framework/panorama/template/buttons-png-include.xml");

    newElement = _xmldoc.createElement("include");
    newRoot.appendChild(newElement);
    newElement.setAttribute("url", "framework/panorama/template/tooltip.xml");

    newElement = _xmldoc.createElement("include");
    newRoot.appendChild(newElement);
    newElement.setAttribute("url", "framework/panorama/template/radar.xml");

    newElement = _xmldoc.createElement("include");
    newRoot.appendChild(newElement);
    newElement.setAttribute("url", "framework/panorama/template/sound.xml");

    newElement = _xmldoc.createElement("include");
    newRoot.appendChild(newElement);
    newElement.setAttribute("url", "framework/panorama/template/anihotspots.xml");

    newElement = _xmldoc.createElement("include");
    newRoot.appendChild(newElement);
    newElement.setAttribute("url", "framework/panorama/template/recommend_path.xml");

    newElement = _xmldoc.createElement("include");
    newRoot.appendChild(newElement);
    newElement.setAttribute("url", "framework/panorama/template/skin/vtourskin.xml");

    newElement = _xmldoc.createElement("include");
    newRoot.appendChild(newElement);
    newElement.setAttribute("url", "%SWFPATH%/plugins/webvr.xml");

    // 2016/12/28 vr模式下增加缩略图操作与键盘操作
    newElement = _xmldoc.createElement("include");
    newRoot.appendChild(newElement);
    newElement.setAttribute("url", "%SWFPATH%/plugins/html5_vr.xml");

    // 展览onstartAction生成
    elementComment = _xmldoc.createComment("展览初期化Action");
    newRoot.appendChild(elementComment);
    Element onstartAction = _xmldoc.createElement("action");
    newRoot.appendChild(onstartAction);
    onstartAction.setAttribute("name", "onstart_action");
    onstartAction.setTextContent(onstartEvent.toString());

    // 展览gyroAction生成
    Element gyroAction = _xmldoc.createElement("action");
    newRoot.appendChild(gyroAction);
    gyroAction.setAttribute("name", "gyro_available_info");
    // 拼接gyro事件
    StringBuffer gyroEvent = new StringBuffer();
    gyroEvent.append("\r\n        ");
    gyroEvent.append("set(layer[skin_btn_gyro_v].visible,true);");
    gyroEvent.append("\r\n        ");
    gyroEvent.append("set(layer[skin_btn_gyro].visible,true);");
    gyroEvent.append("\r\n        ");
    gyroEvent.append("if(device.mobile == false,");
    gyroEvent.append("\r\n        ");
    gyroEvent.append("set(layer[snd_v].y,130);");
    gyroEvent.append("\r\n        ");
    gyroEvent.append("set(layer[skin_btn_thumbs_v].y,170);");
    gyroEvent.append("\r\n        ");
    gyroEvent.append("set(layer[skin_btn_rt_v].y,210);");
    gyroEvent.append("\r\n        ");
    gyroEvent.append(",");
    gyroEvent.append("\r\n        ");
    gyroEvent.append("set(layer[snd_v].x,10);");
    gyroEvent.append("\r\n        ");
    gyroEvent.append("set(layer[snd_v].y,382.5);");
    gyroEvent.append("\r\n        ");
    gyroEvent.append("set(layer[skin_btn_thumbs_v].y,472.5);");
    gyroEvent.append("\r\n        ");
    gyroEvent.append("set(layer[skin_btn_rt_v].y,562.5);");
    gyroEvent.append("\r\n        ");
    gyroEvent.append(");");
    gyroEvent.append("\r\n        ");
    gyroAction.setTextContent(gyroEvent.toString());

    elementComment = _xmldoc.createComment("VR模式的设定");
    newRoot.appendChild(elementComment);
    Element vrPlugin = _xmldoc.createElement("plugin");
    newRoot.appendChild(vrPlugin);
    vrPlugin.setAttribute("name", "WebVR");
    vrPlugin.setAttribute("mobilevr_fake_support", "true");
    vrPlugin.setAttribute("onentervr", "normalhs_enterVR();webvr_onentervr();");
    vrPlugin.setAttribute("onexitvr",
        "webvr_onexitvr();normalhs_exitVR();vr_menu_setvisibility(false);start_thumbs(exit);");

    // elementComment = _xmldoc.createComment("VR按钮的设定");
    // newRoot.appendChild(elementComment);
    // Element vrLayer = _xmldoc.createElement("layer");
    // newRoot.appendChild(vrLayer);
    // vrLayer.setAttribute("name", "webvr_enterbutton");
    // vrLayer.setAttribute("align", "bottom");
    // vrLayer.setAttribute("url",
    // "framework/panorama/template/skin/others/cardboard_logo.png");
    // vrLayer.setAttribute("y", "10%");
    // 缩略图边框设定
    elementComment = _xmldoc.createComment("缩略图边框设定");
    newRoot.appendChild(elementComment);
    Element layerMenu = _xmldoc.createElement("layer");
    newRoot.appendChild(layerMenu);
    layerMenu.setAttribute("name", "skin_thumbborder");
    layerMenu.setAttribute("keep", "true");
    layerMenu.setAttribute("url", "framework/panorama/images/positionicon/thumbborder.png");
    layerMenu.setAttribute("visible", "false");
    layerMenu.setAttribute("enabled", "false");
    layerMenu.setAttribute("align", "lefttop");
    layerMenu.setAttribute("scale", "0.5");
    layerMenu.setAttribute("ox", "-2");
    layerMenu.setAttribute("oy", "-2");

    // 右键菜单追加
    elementComment = _xmldoc.createComment("右键菜单设定");
    newRoot.appendChild(elementComment);
    Element contextMenu = _xmldoc.createElement("contextmenu");
    newRoot.appendChild(contextMenu);
    contextMenu.setAttribute("enterfs", "全屏");
    contextMenu.setAttribute("exitfs", "退出全屏");

    Element contextMenuItem = _xmldoc.createElement("item");
    contextMenu.appendChild(contextMenuItem);
    contextMenuItem.setAttribute("name", "fs");
    contextMenuItem.setAttribute("caption", "FULLSCREEN");
    contextMenuItem = _xmldoc.createElement("item");
    contextMenu.appendChild(contextMenuItem);
    contextMenuItem.setAttribute("name", "title");
    contextMenuItem.setAttribute("caption", inForm.expositionName);

    // 检索展览下所有场景，按sort_key排序
    panoramaQuery = new PanoPanoramaQuery();
    panoramaQuery.createCriteria().andExpositionIdEqualTo(inForm.selectedExpositionId);
    panoramaQuery.setOrderByClause("panorama_sort_key");
    List<PanoPanorama01Model> panoramaList = panoPanorama01Mapper.selectByBaseModel(panoramaQuery);
    if (panoramaList != null && !panoramaList.isEmpty()) {
      // 做成展览入口动画
      elementComment = _xmldoc.createComment("展览入口动画");
      newRoot.appendChild(elementComment);
      createExpositionPreload(inForm, _xmldoc, newRoot, firstSceneInfo);
      // 生成展览导航图
      elementComment = _xmldoc.createComment("展览导航图");
      newRoot.appendChild(elementComment);
      boolean hasMiniMap = createExpositionMap(inForm, _xmldoc, newRoot);
      // 做成缩略图
      elementComment = _xmldoc.createComment("场景的缩略图");
      newRoot.appendChild(elementComment);
      createThumbNodes(inForm, _xmldoc, newRoot, panoramaList, hasMiniMap);
      // 生成展览浮动信息层
      elementComment = _xmldoc.createComment("展览浮动信息层");
      newRoot.appendChild(elementComment);
      createExpositionFlowInfolayer(inForm, _xmldoc, newRoot);
      // 在其后导入gallery，避免gallerty层被其他层至下
      newElement = _xmldoc.createElement("include");
      newRoot.appendChild(newElement);
      newElement.setAttribute("url", "framework/panorama/template/gallery/swipe_gallery.xml");
      // 做成场景
      elementComment = _xmldoc.createComment("展览的场景");
      newRoot.appendChild(elementComment);
      createSceneNodes(inForm, _xmldoc, newRoot, panoramaList, firstSceneInfo.panoramaId);
    }
    // 输出编辑后的ＸＭＬ文件
    TransformerFactory factory = TransformerFactory.newInstance();
    Transformer former = factory.newTransformer();
    former.setOutputProperty(OutputKeys.INDENT, "yes");
    former.transform(new DOMSource(_xmldoc), new StreamResult(new File(xmlFilePath)));

    // 格式化生成后的ＸＭＬ文件
    SAXReader reader = new SAXReader();
    // 加载xml文件
    org.dom4j.Document dom4jDoc = reader.read(xmlFilePath);
    // 格式化输出格式
    OutputFormat format = OutputFormat.createPrettyPrint();
    format.setEncoding("UTF-8");
    format.setTrimText(false);
    // 将document写入到文件
    OutputStreamWriter outstream =
        new OutputStreamWriter(new FileOutputStream(xmlFilePath), "UTF-8");
    XMLWriter xmlWriter = new XMLWriter(outstream, format);
    xmlWriter.write(dom4jDoc);
    xmlWriter.flush();
    xmlWriter.close();

    // 发布成功后,最后更新展览信息的发布日
    panoExposition.releaseDate = LocalDateTime.now();
    panoExpositionMapper.updateByPrimaryKey(panoExposition);
  }

  /**
   * 生成展览入口动画
   * 
   * @param _inForm
   * @param _xmldoc
   * @param _newRoot
   * @throws Exception
   */
  public void createExpositionPreload(Pano0110Form _inForm, Document _xmldoc, Element _newRoot,
      PanoPanorama firstSceneInfo) throws Exception {
    // 检索当前展览下有无预加载文件
    PanoExposition exposition =
        panoExpositionMapper.selectByPrimaryKey(_inForm.selectedExpositionId);
    // 有预加载文件，制作layer
    if (exposition != null && !ObjectUtils.isEmpty(exposition.preloadFilePath)) {
      // 跳过按钮层
      Element skipButtonLayer = _xmldoc.createElement("layer");
      _newRoot.appendChild(skipButtonLayer);
      skipButtonLayer.setAttribute("name", "skip_layer");
      skipButtonLayer.setAttribute("zorder", "104");
      skipButtonLayer.setAttribute("keep", "true");
      skipButtonLayer.setAttribute("visible", "false");
      skipButtonLayer.setAttribute("x", "10");
      skipButtonLayer.setAttribute("y", "10");
      skipButtonLayer.setAttribute("width", "64");
      skipButtonLayer.setAttribute("height", "prop");
      skipButtonLayer.setAttribute("align", "rightbottom");
      skipButtonLayer.setAttribute("url",
          "framework/panorama/template/staticvtour/skip_button.png");
      skipButtonLayer.setAttribute("scale", "1");

      StringBuffer skipEvent = new StringBuffer();
      if ("1".equals(exposition.preloadFileType)) {
        // 为视频类型
        PanoExposition panoExposition =
            panoExpositionMapper.selectByPrimaryKey(_inForm.selectedExpositionId);
        if (panoExposition != null) {
          // 如果该展览有背景音乐
          if (!ObjectUtils.isEmpty(panoExposition.expositionSoundId)) {
            if (!ObjectUtils.isEmpty(firstSceneInfo.panoramaSoundId)) {
              // 第一个场景也有场景音乐
              PanoMaterial panoramaSound =
                  panoMaterial01Mapper.selectByPrimaryKey(firstSceneInfo.panoramaSoundId);
              if (panoramaSound != null) {
                skipEvent.append("layer[videoplayer_plugin].pause(); playsound(scenebgsnd,"
                    + doGetMaterialFolderPath(panoramaSound.expositionId, _inForm,
                        panoramaSound.materialPath)
                    + ",0);");
              }
            } else {
              // 第一个场景没有音乐 播放展览音乐
              PanoMaterial expositionSound =
                  panoMaterial01Mapper.selectByPrimaryKey(panoExposition.expositionSoundId);
              skipEvent.append("layer[videoplayer_plugin].pause(); playsound(expositionbgsnd,"
                  + doGetMaterialFolderPath(expositionSound.expositionId, _inForm,
                      expositionSound.materialPath)
                  + ",0);");
            }
          } else {
            // 展览没有音乐
            if (!ObjectUtils.isEmpty(firstSceneInfo.panoramaSoundId)) {
              PanoMaterial panoramaSound =
                  panoMaterial01Mapper.selectByPrimaryKey(firstSceneInfo.panoramaSoundId);
              if (panoramaSound != null) {
                // 第一个场景有背景音乐
                skipEvent.append("layer[videoplayer_plugin].pause(); playsound(scenebgsnd, "
                    + doGetMaterialFolderPath(panoramaSound.expositionId, _inForm,
                        panoramaSound.materialPath)
                    + ",0);");
              }
            } else {
              // 第一个场景没有背景音乐
              skipEvent.append("layer[videoplayer_plugin].pause();");

            }

          }
        }

      }
      skipEvent.append("set(layer[videoplayer_bg].visible,false);");
      skipEvent.append("set(layer[preload_container].visible,false);");
      skipEvent.append("set(layer[skip_layer].visible,false);");
      skipEvent.append("set(layer[count_down_text].visible, false);");
      skipEvent.append("set(layer[skip_layer].onclick,'');");
      skipButtonLayer.setAttribute("onclick", skipEvent.toString());
      skipButtonLayer.setAttribute("enabled", "true");

      // 显示倒计时的层
      Element countDownLayer = _xmldoc.createElement("layer");
      _newRoot.appendChild(countDownLayer);
      countDownLayer.setAttribute("name", "count_down_text");
      countDownLayer.setAttribute("keep", "true");
      countDownLayer.setAttribute("zorder", "105");
      countDownLayer.setAttribute("background", "false");
      countDownLayer.setAttribute("x", "22");
      countDownLayer.setAttribute("y", "21");
      countDownLayer.setAttribute("url", "%SWFPATH%/plugins/textfield.swf");
      countDownLayer.setAttribute("align", "rightbottom");
      countDownLayer.setAttribute("border", "false");
      countDownLayer.setAttribute("autoheight", "true");
      countDownLayer.setAttribute("css",
          "text-align:center; color:#BBBBBB; font-family:Arial; font-size:18px;");
      countDownLayer.setAttribute("textshadow", "2");
      countDownLayer.setAttribute("html", "");
      countDownLayer.setAttribute("onclick", skipEvent.toString());
      countDownLayer.setAttribute("enabled", "true");

      // preload图的容器层
      Element preloadLayerContainer = _xmldoc.createElement("layer");
      _newRoot.appendChild(preloadLayerContainer);
      preloadLayerContainer.setAttribute("name", "preload_container");
      preloadLayerContainer.setAttribute("preload", "true");
      preloadLayerContainer.setAttribute("handcursor", "true");
      preloadLayerContainer.setAttribute("enabled", "true");
      preloadLayerContainer.setAttribute("children", "true");
      preloadLayerContainer.setAttribute("bgcapture", "true");
      preloadLayerContainer.setAttribute("visible", "true");
      preloadLayerContainer.setAttribute("zorder", "90");
      preloadLayerContainer.setAttribute("type", "container");
      preloadLayerContainer.setAttribute("maskchildren", "true");
      preloadLayerContainer.setAttribute("keep", "true");
      preloadLayerContainer.setAttribute("width", "100%");
      preloadLayerContainer.setAttribute("height", "100%");
      preloadLayerContainer.setAttribute("bgcolor", "0xFFFFFF");
      preloadLayerContainer.setAttribute("bgalpha", "1");

      // 容器里的图片层
      Element preloadImg = _xmldoc.createElement("layer");
      preloadLayerContainer.appendChild(preloadImg);
      preloadImg.setAttribute("name", "preload_file_layer");
      preloadImg.setAttribute("keep", "true");
      preloadImg.setAttribute("zorder", "100");
      preloadImg.setAttribute("x", "0");
      preloadImg.setAttribute("y", "0");
      preloadImg.setAttribute("align", "center");
      preloadImg.setAttribute("scale", "1");
      preloadImg.setAttribute("enabled", "false");

      // 判断展览预加载文件类型
      if ("0".equals(exposition.preloadFileType)) {
        // 图片类型
        preloadImg.setAttribute("url", "preloadFile/" + exposition.preloadFilePath);
        preloadImg.setAttribute("width", "100%");
        preloadImg.setAttribute("height", "100%");
      }
      if ("1".equals(exposition.preloadFileType)) {
        // 视频类型
        preloadImg.setAttribute("url", "framework/panorama/template/staticvtour/dumy.jpg");
        preloadImg.setAttribute("onloaded",
            "stopallsounds();videoplayer_open(preloadFile/" + exposition.preloadFilePath + ");  ");
      }
    }

  }

  /**
   * 生成缩略图
   * 
   * @param _inForm
   * @param _xmldoc
   * @param _newRoot
   * @param panoramaList
   * @throws Exception
   */
  private void createThumbNodes(Pano0110Form _inForm, Document _xmldoc, Element _newRoot,
      List<PanoPanorama01Model> panoramaList, boolean _hasMiniMap) throws Exception {
    Element thumbStyleElement = _xmldoc.createElement("style");
    _newRoot.appendChild(thumbStyleElement);
    thumbStyleElement.setAttribute("name", "thumbStyle");
    thumbStyleElement.setAttribute("align", "top");
    thumbStyleElement.setAttribute("edge", "center");
    thumbStyleElement.setAttribute("scalechildren", "true");
    thumbStyleElement.setAttribute("ondown", "tween(scale,1.1,0.1);");
    thumbStyleElement.setAttribute("onup", "tween(scale, 1.0, 0.25, easeOutBounce);");
    thumbStyleElement.setAttribute("onover", "ondown();");
    thumbStyleElement.setAttribute("onout", "onup();");

    // 缩略图容器层
    Element Layer_skin_control_bar_v = _xmldoc.createElement("layer");
    Layer_skin_control_bar_v.setAttribute("visible", "true");
    Layer_skin_control_bar_v.setAttribute("name", "skin_control_bar_v");
    Layer_skin_control_bar_v.setAttribute("keep", "true");
    Layer_skin_control_bar_v.setAttribute("type", "container");
    Layer_skin_control_bar_v.setAttribute("bgcolor", "0x000000");
    Layer_skin_control_bar_v.setAttribute("bgalpha", "0.3");
    Layer_skin_control_bar_v.setAttribute("align", "leftbottom");
    Layer_skin_control_bar_v.setAttribute("width", "40");
    Layer_skin_control_bar_v.setAttribute("height", "100%");
    Layer_skin_control_bar_v.setAttribute("x", "20");
    Layer_skin_control_bar_v.setAttribute("y", "0");
    Layer_skin_control_bar_v.setAttribute("x_opened", "20");
    Layer_skin_control_bar_v.setAttribute("x_closed", "-42");
    Layer_skin_control_bar_v.setAttribute("zorder", "1");
    Layer_skin_control_bar_v.setAttribute("bgcapture", "true");
    Layer_skin_control_bar_v.setAttribute("handcursor", "false");

    // 2016/12/20增加VR图标
    Element vrLayer = _xmldoc.createElement("layer");
    vrLayer.setAttribute("name", "webvr_enterbutton_v");
    vrLayer.setAttribute("url", "framework/panorama/template/skin/others/cardboard_logo.png");
    vrLayer.setAttribute("y", "10");
    vrLayer.setAttribute("x", "4");
    vrLayer.setAttribute("align", "lefttop");
    vrLayer.setAttribute("scale", "0.4");
    vrLayer.setAttribute("visible", "true");
    vrLayer.setAttribute("style", "skin_base|skin_glow");
    vrLayer.setAttribute("onclick", "webvr.enterVR();enterVR();");

    Element Plugin_snd_v = _xmldoc.createElement("plugin");
    Plugin_snd_v.setAttribute("name", "snd_v");
    Plugin_snd_v.setAttribute("url", "framework/panorama/template/skin/soundonoff.png");
    Plugin_snd_v.setAttribute("crop", "0|0|50|50");
    Plugin_snd_v.setAttribute("align", "leftbottom");
    Plugin_snd_v.setAttribute("alpha", "0.5");
    Plugin_snd_v.setAttribute("onover", "tween(alpha,1);");
    Plugin_snd_v.setAttribute("onout", "tween(alpha,0.25);");
    Plugin_snd_v.setAttribute("x", "6.5");
    Plugin_snd_v.setAttribute("y", "90");
    Plugin_snd_v.setAttribute("scale", "0.6");
    Plugin_snd_v.setAttribute("onclick",
        "switch(soundinterface.mute); switch(crop, 0|0|50|50, 0|50|50|50);");

    Element Layer_skin_btmborder_v = _xmldoc.createElement("layer");
    Layer_skin_btmborder_v.setAttribute("name", "skin_btmborder_v");
    Layer_skin_btmborder_v.setAttribute("url", "framework/panorama/template/skin/vtourskin_v.png");
    Layer_skin_btmborder_v.setAttribute("crop", "576|0|12|60");
    Layer_skin_btmborder_v.setAttribute("align", "left");
    Layer_skin_btmborder_v.setAttribute("edge", "right");
    Layer_skin_btmborder_v.setAttribute("height", "100%");
    Layer_skin_btmborder_v.setAttribute("width", "12");
    Layer_skin_btmborder_v.setAttribute("x", "0");
    Layer_skin_btmborder_v.setAttribute("y", "-1");
    Layer_skin_btmborder_v.setAttribute("enabled", "false");

    Element Layer_skin_btn_thumbs_v = _xmldoc.createElement("layer");
    Layer_skin_btn_thumbs_v.setAttribute("name", "skin_btn_thumbs_v");
    Layer_skin_btn_thumbs_v.setAttribute("style", "skin_base|skin_glow");
    Layer_skin_btn_thumbs_v.setAttribute("crop", "0|128|64|64");
    Layer_skin_btn_thumbs_v.setAttribute("align", "rightbottom");
    Layer_skin_btn_thumbs_v.setAttribute("x", "4");
    Layer_skin_btn_thumbs_v.setAttribute("y", "130");
    Layer_skin_btn_thumbs_v.setAttribute("scale", "0.5");
    Layer_skin_btn_thumbs_v.setAttribute("ondown2", "skin_thumbs_v();");

    Element Layer_skin_btn_map_v = _xmldoc.createElement("layer");
    Layer_skin_btn_map_v.setAttribute("name", "skin_btn_map_v");
    Layer_skin_btn_map_v.setAttribute("style", "skin_base|skin_glow");
    Layer_skin_btn_map_v.setAttribute("crop", "64|128|64|64");
    Layer_skin_btn_map_v.setAttribute("align", "rightbottom");
    Layer_skin_btn_map_v.setAttribute("x", "4");
    Layer_skin_btn_map_v.setAttribute("y", "50");
    Layer_skin_btn_map_v.setAttribute("scale", "0.5");
    Layer_skin_btn_map_v.setAttribute("visible", "true");

    Element Layer_skin_btn_rt_v = _xmldoc.createElement("layer");
    Layer_skin_btn_rt_v.setAttribute("name", "skin_btn_rt_v");
    Layer_skin_btn_rt_v.setAttribute("style", "skin_base|skin_glow");
    Layer_skin_btn_rt_v.setAttribute("crop", "64|192|64|64");
    Layer_skin_btn_rt_v.setAttribute("align", "rightbottom");
    Layer_skin_btn_rt_v.setAttribute("x", "4");
    Layer_skin_btn_rt_v.setAttribute("y", "170");
    Layer_skin_btn_rt_v.setAttribute("scale", "0.5");
    Layer_skin_btn_rt_v.setAttribute("hadMap", "no");

    // 陀螺仪
    Element layer_skin_btn_gyro_v = _xmldoc.createElement("layer");
    layer_skin_btn_gyro_v.setAttribute("name", "skin_btn_gyro_v");
    layer_skin_btn_gyro_v.setAttribute("style", "skin_base|skin_glow");
    layer_skin_btn_gyro_v.setAttribute("crop", "0|384|64|64");
    layer_skin_btn_gyro_v.setAttribute("x", "4");
    layer_skin_btn_gyro_v.setAttribute("y", "90");
    layer_skin_btn_gyro_v.setAttribute("align", "rightbottom");
    layer_skin_btn_gyro_v.setAttribute("devices", "html5");
    layer_skin_btn_gyro_v.setAttribute("scale", "0.5");
    layer_skin_btn_gyro_v.setAttribute("visible", "false");
    layer_skin_btn_gyro_v.setAttribute("onclick", "switch(plugin[skin_gyro_v].enabled);");

    Element plugin_skin_gyro_v = _xmldoc.createElement("plugin");
    plugin_skin_gyro_v.setAttribute("name", "skin_gyro_v");
    plugin_skin_gyro_v.setAttribute("devices", "html5");
    plugin_skin_gyro_v.setAttribute("url", "framework/panorama/viewer/plugins/gyro.js");
    plugin_skin_gyro_v.setAttribute("enabled", "false");
    plugin_skin_gyro_v.setAttribute("camroll", "true");
    plugin_skin_gyro_v.setAttribute("friction", "0");
    plugin_skin_gyro_v.setAttribute("velastic", "0");
    plugin_skin_gyro_v.setAttribute("onavailable", "gyro_available_info();");

    StringBuffer _downAttr = new StringBuffer();
    _downAttr.append("skin_switchBoder();");
    _downAttr.append("if(layer[skin_thumbs].state == 'closed'");
    _downAttr.append("   ,set(layer[layer_nimi_map_container].y, 64);");
    _downAttr.append("   ,set(layer[layer_nimi_map_container].y, 163););");
    _downAttr.append("set(layer[skin_control_bar_v].visible,false);");
    _downAttr.append("skin_swapVH();");

    // 如果有展览导航图，则给地图控制按钮绑定click事件
    if (_hasMiniMap) {
      Layer_skin_btn_map_v.setAttribute("onclick",
          "if(layer[layer_nimi_map_container].scale >0, closemap_v();,openmap_v();)");
      Layer_skin_btn_rt_v.setAttribute("hadMap", "yes");
      // 重写skin_btn_up按钮的click事件，为其添加setMapClickEvent方法
      _downAttr.append("set(layer[skin_btn_up].onclick, ");
      _downAttr.append("skin_switchBoder('2');");
      _downAttr.append("if(device.mobile");
      _downAttr.append("   ,set(layer[layer_nimi_map_container].y, 98);,");
      _downAttr.append("   ,set(layer[layer_nimi_map_container].y, 64););");
      _downAttr.append("set(layer[skin_control_bar_v].visible, true);");
      _downAttr.append("skin_swapVH();");
      _downAttr.append("setMapClickEvent(););");

    }

    Layer_skin_btn_rt_v.setAttribute("ondown2", _downAttr.toString());

    Element Layer_skin_btn_hide_v = _xmldoc.createElement("layer");
    Layer_skin_btn_hide_v.setAttribute("name", "skin_btn_hide_v");
    Layer_skin_btn_hide_v.setAttribute("style", "skin_glow");
    Layer_skin_btn_hide_v.setAttribute("url", "framework/panorama/template/skin/vtourskin_v.png");
    Layer_skin_btn_hide_v.setAttribute("crop", "128|0|64|64");
    Layer_skin_btn_hide_v.setAttribute("align", "rightbottom");
    Layer_skin_btn_hide_v.setAttribute("x", "5");
    Layer_skin_btn_hide_v.setAttribute("y", "10");
    Layer_skin_btn_hide_v.setAttribute("scale", "0.5");
    Layer_skin_btn_hide_v.setAttribute("onclick", "skin_bar_v();");

    Element Layer_thumbContainer_v = _xmldoc.createElement("layer");
    Layer_thumbContainer_v.setAttribute("align", "right");
    Layer_thumbContainer_v.setAttribute("bgalpha", "0");
    Layer_thumbContainer_v.setAttribute("bgcolor", "0x00ff00");
    Layer_thumbContainer_v.setAttribute("height", "100%");
    Layer_thumbContainer_v.setAttribute("keep", "true");
    Layer_thumbContainer_v.setAttribute("name", "thumbContainer_v");
    Layer_thumbContainer_v.setAttribute("type", "container");
    Layer_thumbContainer_v.setAttribute("width", "118");
    Layer_thumbContainer_v.setAttribute("x", "-118");
    Layer_thumbContainer_v.setAttribute("y", "-1");
    Layer_thumbContainer_v.setAttribute("maskchildren", "true");

    Element Layer_thumbContainer = _xmldoc.createElement("layer");
    Layer_thumbContainer.setAttribute("align", "leftbottom");
    Layer_thumbContainer.setAttribute("bgalpha", "0.3");
    Layer_thumbContainer.setAttribute("bgcolor", "0x000000");
    Layer_thumbContainer.setAttribute("height", "100%");
    Layer_thumbContainer.setAttribute("keep", "true");
    Layer_thumbContainer.setAttribute("name", "thumbContainer");
    Layer_thumbContainer.setAttribute("state", "closed");
    Layer_thumbContainer.setAttribute("type", "container");
    Layer_thumbContainer.setAttribute("width", "105");
    Layer_thumbContainer.setAttribute("x", "-105");
    Layer_thumbContainer.setAttribute("x_opened", "-3");
    Layer_thumbContainer.setAttribute("x_closed", "-105");
    Layer_thumbContainer.setAttribute("y", "0");

    Element Layer_thumbBarLayer = _xmldoc.createElement("layer");
    Layer_thumbBarLayer.setAttribute("align", "topleft");
    Layer_thumbBarLayer.setAttribute("height", "100%");
    Layer_thumbBarLayer.setAttribute("keep", "true");
    Layer_thumbBarLayer.setAttribute("name", "thumbBarLayer");
    Layer_thumbBarLayer.setAttribute("type", "container");
    Layer_thumbBarLayer.setAttribute("width", "105");

    Element Layer_scrollarea = _xmldoc.createElement("layer");
    Layer_scrollarea.setAttribute("align", "center");
    Layer_scrollarea.setAttribute("alturl", "%SWFPATH%/plugins/scrollarea.js");
    Layer_scrollarea.setAttribute("direction", "v");
    Layer_scrollarea.setAttribute("name", "scrollarea");
    Layer_scrollarea.setAttribute("onhover_autoscrolling", "false");
    Layer_scrollarea.setAttribute("onloaded", "setcenter(0,0);");
    Layer_scrollarea.setAttribute("url", "%SWFPATH%/plugins/scrollarea.swf");
    Layer_scrollarea.setAttribute("width", "95");
    Layer_scrollarea.setAttribute("x", "-2");

    Layer_thumbBarLayer.appendChild(Layer_scrollarea);

    Element Layer_skin_topborder_v = _xmldoc.createElement("layer");
    Layer_skin_topborder_v.setAttribute("name", "skin_topborder_v");
    Layer_skin_topborder_v.setAttribute("url", "framework/panorama/template/skin/vtourskin_v.png");
    Layer_skin_topborder_v.setAttribute("crop", "627|0|12|60");
    Layer_skin_topborder_v.setAttribute("align", "right");
    Layer_skin_topborder_v.setAttribute("edge", "left");
    Layer_skin_topborder_v.setAttribute("height", "100%");
    Layer_skin_topborder_v.setAttribute("width", "12");
    Layer_skin_topborder_v.setAttribute("x", "1");
    Layer_skin_topborder_v.setAttribute("y", "-1");
    Layer_skin_topborder_v.setAttribute("enabled", "false");

    Layer_thumbContainer.appendChild(Layer_thumbBarLayer);
    Layer_thumbContainer.appendChild(Layer_skin_topborder_v);
    Layer_thumbContainer_v.appendChild(Layer_thumbContainer);

    Element Layer_showBtnContainer = _xmldoc.createElement("layer");
    Layer_showBtnContainer.setAttribute("visible", "false");
    Layer_showBtnContainer.setAttribute("align", "leftbottom");
    Layer_showBtnContainer.setAttribute("bgalpha", "0");
    Layer_showBtnContainer.setAttribute("bgcolor", "0x000000");
    Layer_showBtnContainer.setAttribute("height", "100%");
    Layer_showBtnContainer.setAttribute("keep", "true");
    Layer_showBtnContainer.setAttribute("name", "showBtnContainer");
    Layer_showBtnContainer.setAttribute("state", "opened");
    Layer_showBtnContainer.setAttribute("type", "container");
    Layer_showBtnContainer.setAttribute("width", "20");
    Layer_showBtnContainer.setAttribute("x", "48");
    Layer_showBtnContainer.setAttribute("y", "0");
    Layer_showBtnContainer.setAttribute("zorder", "1");

    Element Layer_skin_btn_show_v = _xmldoc.createElement("layer");
    Layer_skin_btn_show_v.setAttribute("name", "skin_btn_show_v");
    Layer_skin_btn_show_v.setAttribute("style", "skin_glow");
    Layer_skin_btn_show_v.setAttribute("url", "framework/panorama/template/skin/vtourskin_v.png");
    Layer_skin_btn_show_v.setAttribute("crop", "128|64|64|64");
    Layer_skin_btn_show_v.setAttribute("align", "center");
    Layer_skin_btn_show_v.setAttribute("scale", "0.5");
    Layer_skin_btn_show_v.setAttribute("onclick", "skin_bar_v()");

    Layer_showBtnContainer.appendChild(Layer_skin_btn_show_v);
    Layer_skin_control_bar_v.appendChild(vrLayer);
    Layer_skin_control_bar_v.appendChild(Plugin_snd_v);
    Layer_skin_control_bar_v.appendChild(Layer_skin_btmborder_v);
    Layer_skin_control_bar_v.appendChild(Layer_skin_btn_thumbs_v);
    Layer_skin_control_bar_v.appendChild(Layer_skin_btn_map_v);
    Layer_skin_control_bar_v.appendChild(Layer_skin_btn_rt_v);
    Layer_skin_control_bar_v.appendChild(layer_skin_btn_gyro_v);
    Layer_skin_control_bar_v.appendChild(plugin_skin_gyro_v);
    Layer_skin_control_bar_v.appendChild(Layer_skin_btn_hide_v);
    Layer_skin_control_bar_v.appendChild(Layer_thumbContainer_v);
    Layer_skin_control_bar_v.appendChild(Layer_showBtnContainer);
    _newRoot.appendChild(Layer_skin_control_bar_v);

    int nPos = 0;
    for (int i = 0; i < panoramaList.size(); i++) {
      PanoPanorama panoPanorama = panoramaList.get(i);
      // 判断是否显示缩略图，0为不显示，1为显示
      if ("0".equals(panoPanorama.thumbnailShowFlag)) {
        continue;
      }
      String sceneId = "v_" + panoPanorama.panoramaId;
      Element thumbLayer = _xmldoc.createElement("layer");

      Layer_scrollarea.appendChild(thumbLayer);
      thumbLayer.setAttribute("name", sceneId + "_v");
      // String thumbFilePath = "panoramas/" +
      // panoPanorama.panoramaPath.replace("show.xml", "thumb.jpg");
      // 场景路径例子：5it1jtu7gdvu1/panoramas/5it1l7fuhzmnh/(展览ID/panoramas/场景ID/)
      String thumbFilePath =
          panoPanorama.panoramaPath.replace(panoPanorama.expositionId + "/", "") + "thumb.jpg";
      thumbLayer.setAttribute("url", thumbFilePath);
      thumbLayer.setAttribute("style", "thumbStyle");
      thumbLayer.setAttribute("y", Objects.toString(50 + 90 * nPos));
      nPos++;
      Layer_scrollarea.setAttribute("height", Objects.toString(90 * nPos + 10));

      // 拼接click事件
      StringBuffer thumbClickEvent = new StringBuffer();
      // 关闭推荐线路文字浮动的显示
      thumbClickEvent.append("set(hotspot[recommedInfoHotspot].visible,false);");
      // 检索与场景相关的地图热点
      PanoExpositionMapHotspotQuery expositionMapHotspotQuery = new PanoExpositionMapHotspotQuery();
      expositionMapHotspotQuery.createCriteria().andPanoramaIdEqualTo(panoPanorama.panoramaId);
      List<PanoExpositionMapHotspot01Model> mapHotspotList =
          panoExpositionMapHotspot01Mapper.selectByBaseModel(expositionMapHotspotQuery);
      // 检索相关联的地图热点
      if (ObjectUtils.isNotEmpty(mapHotspotList)) {
        PanoExpositionMapHotspot01Model mapHotspot = mapHotspotList.get(0);
        thumbClickEvent.append("activatespot(V_" + mapHotspot.expositionMapHotspotId + ",0);");
        // 设置当前点击的热点zorder小于其他热点zorder，false为不需要在拼接时换行
        thumbClickEvent = setMapHotspotZorder(thumbClickEvent, false, _inForm, mapHotspot);
        // 设定相关联的地图热点的雷达角度
        if (!ObjectUtils.isEmpty(mapHotspot.expositionMapHotspotHeading)) {
          thumbClickEvent
              .append("set(layer[radar].heading," + mapHotspot.expositionMapHotspotHeading + ");");
        }
      } else {
        thumbClickEvent.append("activatespot(V_,0);");
      }
      // 视角点事件
      StringBuffer lookatEvent = new StringBuffer();
      // 该场景下存在音乐的情况
      PanoExposition expo = panoExpositionMapper.selectByPrimaryKey(panoPanorama.expositionId);
      if (expo.expositionSoundId != null && !ObjectUtils.isEmpty(expo.expositionSoundId)) {

        String expositionSoundId = expo.expositionSoundId;
        PanoMaterial panoMaterial = panoMaterial01Mapper.selectByPrimaryKey(expositionSoundId);
        // 判断跳转至的场景有无视角点
        if (!ObjectUtils.isEmpty(panoPanorama.panoramaHlookat)
            && !StringUtils.isEmpty(panoPanorama.panoramaVlookat)) {
          lookatEvent.append("view.hlookat=" + panoPanorama.panoramaHlookat + "&view.vlookat="
              + panoPanorama.panoramaVlookat);

          thumbClickEvent.append("playBefore(v_" + panoPanorama.panoramaId + ","
              + doGetMaterialFolderPath(panoMaterial.expositionId, _inForm,
                  panoMaterial.materialPath)
              + "," + lookatEvent.toString() + ");");
        } else {
          thumbClickEvent
              .append("playBefore(v_" + panoPanorama.panoramaId + "," + doGetMaterialFolderPath(
                  panoMaterial.expositionId, _inForm, panoMaterial.materialPath) + ",null);");
        }
      } else {
        // 没有背景音乐
        // 是否有视角点
        if (!ObjectUtils.isEmpty(panoPanorama.panoramaHlookat)
            && !StringUtils.isEmpty(panoPanorama.panoramaVlookat)) {
          lookatEvent.append("view.hlookat=" + panoPanorama.panoramaHlookat + "&view.vlookat="
              + panoPanorama.panoramaVlookat);

          thumbClickEvent.append("playBefore(v_" + panoPanorama.panoramaId + ", null,"
              + lookatEvent.toString() + " );");
        } else {
          thumbClickEvent.append("playBefore(v_" + panoPanorama.panoramaId + ",null,null);");
        }

      }

      thumbClickEvent.append("skin_getBoder('v_" + panoPanorama.panoramaId + "');");
      thumbLayer.setAttribute("onclick", "stopsound(hotspotsnd);" + thumbClickEvent.toString());
    }
  }

  /**
   * 生成场景
   * 
   * @param _inForm
   * @param _xmldoc
   * @param _newRoot
   * @param panoramaList
   */
  private void createSceneNodes(Pano0110Form _inForm, Document _xmldoc, Element _newRoot,
      List<PanoPanorama01Model> panoramaList, String firstSceneId) throws Exception {
    for (int i = 0; i < panoramaList.size(); i++) {
      PanoPanorama panoPanorama = panoramaList.get(i);
      String sceneId = "v_" + panoPanorama.panoramaId;
      Element sceneElement = _xmldoc.createElement("scene");
      _newRoot.appendChild(sceneElement);
      sceneElement.setAttribute("name", sceneId);
      // 判断是否显示缩略图，0为不显示，1为显示
      if ("1".equals(panoPanorama.thumbnailShowFlag)) {
        sceneElement.setAttribute("thumburl",
            "panoramas/" + panoPanorama.panoramaId + "/thumb.jpg");
        // 缩略图备注
        if (!ObjectUtils.isEmpty(panoPanorama.thumbNote)) {
          sceneElement.setAttribute("title", panoPanorama.thumbNote);
        } else {
          // 没有备注默认显示场景名称
          sceneElement.setAttribute("title", panoPanorama.panoramaName);
        }
      }

      // 场景的preview节点追加
      Element previewElement = _xmldoc.createElement("preview");
      sceneElement.appendChild(previewElement);
      previewElement.setAttribute("url", "panoramas/" + panoPanorama.panoramaId + "/preview.jpg");
      // 场景的view节点追加
      Element viewElement = _xmldoc.createElement("view");
      sceneElement.appendChild(viewElement);

      viewElement.setAttribute("fovtype", "MFOV");
      // 获取视角设定值
      String fov = MessageUtils.getMessage("pano.setting.fov");
      viewElement.setAttribute("fov", fov);
      viewElement.setAttribute("fovmin", "60");
      viewElement.setAttribute("fovmax", "140");
      // 场景的image节点追加stereo="true" stereolabels="L|R"
      Element imageElement = _xmldoc.createElement("image");
      sceneElement.appendChild(imageElement);
      imageElement.setAttribute("stereo", "true");
      imageElement.setAttribute("stereolabels", "l|r");

      // 场景的image节点的cube节点追加
      Element imageCubeElement = _xmldoc.createElement("cube");
      imageElement.appendChild(imageCubeElement);
      imageCubeElement.setAttribute("url",
          "panoramas/" + panoPanorama.panoramaId + "/panos_%t/sphere_%s.jpg");

      Element mobileElement = _xmldoc.createElement("mobile");
      imageElement.appendChild(mobileElement);
      // 场景的moblie节点的cube节点追加
      Element mobileCubeElement = _xmldoc.createElement("cube");
      mobileElement.appendChild(mobileCubeElement);
      mobileCubeElement.setAttribute("url",
          "panoramas/" + panoPanorama.panoramaId + "/panos_%t/mobile_%s.jpg");

      // 场景背景音乐设定
      createBgSound(_inForm, _xmldoc, sceneElement, panoPanorama, firstSceneId);
      // 生成场景的热点
      Comment elementComment = _xmldoc.createComment("场景热点");
      sceneElement.appendChild(elementComment);
      createSceneHotspotNodes(panoPanorama, _inForm, _xmldoc, sceneElement);

      _xmldoc.getElementById("radar");

    }
  }

  /**
   * 生成场景的热点以及热点下的相关信息
   * 
   * @param _panoramaList
   * @param _inForm
   * @param _xmldoc
   * @param _sceneElement
   * @throws Exception
   */
  private void createSceneHotspotNodes(PanoPanorama _panoPanorama, Pano0110Form _inForm,
      Document _xmldoc, Element _sceneElement) throws Exception {

    HashMap<String, Object> _conditions = Maps.newHashMap();
    _conditions.put("panoramaId", _panoPanorama.panoramaId);
    _conditions.put("deleteFlag", FlagStatus.Disable.toString());
    _conditions.put("hotspotType", HotspotType.CHANGE_SCENE.toString());
    // 检索当前场景下的热点
    List<PanoPanoramaHotspot01Model> hotspotList =
        panoPanoramaHotspot01Mapper.selectHotSpotAndMaterialInfo(_conditions);

    for (int i = 0; i < hotspotList.size(); i++) {
      PanoPanoramaHotspot01Model hotspot = hotspotList.get(i);
      // 判断热点类型
      if ((PanoConstantsIF.HOTSPOT_TYPE_HOTSPOT_IMAGE).equals(hotspot.hotspotType)) {
        // 1.普通热点
        Element hotspotElement = _xmldoc.createElement("hotspot");
        _sceneElement.appendChild(hotspotElement);
        hotspotElement.setAttribute("name", "v_" + hotspot.hotspotId);
        if (!ObjectUtils.isEmpty(hotspot.hotspotScale)) {
          hotspotElement.setAttribute("scale", hotspot.hotspotScale);
        }

        hotspotElement.setAttribute("atv", hotspot.hotspotAtv);
        hotspotElement.setAttribute("ath", hotspot.hotspotAth);
        hotspotElement.setAttribute("zoom", "true");
        hotspotElement.setAttribute("zorder", "1");
        hotspotElement.setAttribute("distorted", "true");
        hotspotElement.setAttribute("tooltip", hotspot.hotspotTooltip);

        // 判断该热点图标是否为gif图
        PanoMaterial hotspotMaterial = panoMaterial01Mapper.selectByPrimaryKey(hotspot.materialId);
        if (!ObjectUtils.isEmpty(hotspot.gifDelayTime)) {
          // 创建style
          Element gifStyleElement = _xmldoc.createElement("style");
          _sceneElement.appendChild(gifStyleElement);
          gifStyleElement.setAttribute("name", "v_" + hotspot.hotspotId + "_gif");
          hotspot.materialPath =
              hotspot.materialPath.substring(0, hotspot.materialPath.lastIndexOf(".")) + ".png";
          gifStyleElement.setAttribute("url",
              doGetMaterialFolderPath(hotspotMaterial.expositionId, _inForm, hotspot.materialPath));
          gifStyleElement.setAttribute("crop", "0|0|" + hotspot.gifWidth + "|" + hotspot.gifHeight);
          gifStyleElement.setAttribute("framewidth", hotspot.gifWidth);
          gifStyleElement.setAttribute("frameheight", hotspot.gifHeight);
          gifStyleElement.setAttribute("frame", "0");
          gifStyleElement.setAttribute("lastframe", hotspot.gifFrameCount);
          gifStyleElement.setAttribute("onloaded",
              "hotspot_animate(" + hotspot.gifDelayTime + ");");
          hotspotElement.setAttribute("style", "v_" + hotspot.hotspotId + "_gif|tooltip|normalhs");

        } else {
          hotspotElement.setAttribute("style", "tooltip|normalhs");
          hotspotElement.setAttribute("url",
              doGetMaterialFolderPath(hotspotMaterial.expositionId, _inForm, hotspot.materialPath));
        }

        // 检查该热点下有无素材或外部链接，没有则不加载click事件
        PanoHotspotUrlQuery hotspotUrlQuery = new PanoHotspotUrlQuery();
        hotspotUrlQuery.createCriteria().andHotspotIdEqualTo(hotspot.hotspotId);
        hotspotUrlQuery.setOrderByClause("sort_key");
        if (PanoConstantsIF.HOTSPOT_URL_TYPE_IMAGE.equals(hotspot.hotspotUrlType)) {
          // 热点下是信息图
          List<PanoHotspotUrl01Model> resultList =
              panoHotspotUrl01Mapper.selectByBaseModel(hotspotUrlQuery);
          if (resultList != null && resultList.size() > 0) {
            hotspotElement.setAttribute("galleryname", "v_" + hotspot.hotspotId + "_gallery");
            hotspotElement.setAttribute("onclick", "show_gallery(get(galleryname))");
          }
        } else if (PanoConstantsIF.HOTSPOT_URL_TYPE_LINK.equals(hotspot.hotspotUrlType)) {
          // 热点下是外部链接
          hotspotElement.setAttribute("onclick",
              "openurl('" + hotspot.externalLinkAddress + "',_blank)");
        } else if (PanoConstantsIF.HOTSPOT_URL_TYPE_VIDEO.equals(hotspot.hotspotUrlType)) {
          // 热点下是视频
          List<PanoHotspotUrl01Model> resultList =
              panoHotspotUrl01Mapper.selectByBaseModel(hotspotUrlQuery);
          if (resultList != null && resultList.size() > 0) {
            PanoMaterial material =
                panoMaterial01Mapper.selectByPrimaryKey(resultList.get(0).hotspotUrlObjectId);
            if (material != null) {
              StringBuffer onclick = new StringBuffer();
              onclick.append("setMusicHotspotDefalt();openvideo('"
                  + doGetMaterialFolderPath(material.expositionId, _inForm, material.materialPath)
                  + "');");
              // 停止背景音乐
              onclick.append(
                  "stopsound('expositionbgsnd');stopsound('scenebgsnd');stopsound(hotspotsnd);");

              // 结束视频播放时恢复背景音乐
              if (!ObjectUtils.isEmpty(_sceneElement.getAttribute("onstart"))) {
                onclick.append("set(layer[bgVideoClose].onclick,closevideo();"
                    + _sceneElement.getAttribute("onstart") + ")");
              } else {
                // 检索展览会公共音乐
                PanoExposition panoExposition =
                    panoExpositionMapper.selectByPrimaryKey(_inForm.selectedExpositionId);
                String commonSound = "";
                if (!ObjectUtils.isEmpty(panoExposition.expositionSoundId)) {
                  // 有公共音乐
                  PanoMaterial expositionSound =
                      panoMaterial01Mapper.selectByPrimaryKey(panoExposition.expositionSoundId);
                  commonSound = "playsound(expositionbgsnd," + doGetMaterialFolderPath(
                      expositionSound.expositionId, _inForm, expositionSound.materialPath) + ",0);";
                  onclick.append("set(layer[bgVideoClose].onclick,closevideo();"
                      + commonSound.toString() + ")");
                } else {
                  onclick.append("set(layer[bgVideoClose].onclick,closevideo();)");
                }

              }

              hotspotElement.setAttribute("onclick", onclick.toString());

            }
          }
        } else if (PanoConstantsIF.HOTSPOT_URL_TYPE_TEXT_IMAGE.equals(hotspot.hotspotUrlType)) {
          hotspotElement.setAttribute("onclick",
              "js(doOpenTextImgPage('" + hotspot.hotspotId + "'))");
        }

        // 增加显示点下素材信息用的gallery节点
        if (PanoConstantsIF.HOTSPOT_URL_TYPE_IMAGE.equals(hotspot.hotspotUrlType)) {
          Element galleryElement = _xmldoc.createElement("gallery");
          _sceneElement.appendChild(galleryElement);
          galleryElement.setAttribute("name", "v_" + hotspot.hotspotId + "_gallery");

          hotspotUrlQuery = new PanoHotspotUrlQuery();
          hotspotUrlQuery.createCriteria().andHotspotIdEqualTo(hotspot.hotspotId);
          hotspotUrlQuery.setOrderByClause("sort_key");
          List<PanoHotspotUrl01Model> urlList =
              panoHotspotUrl01Mapper.selectByBaseModel(hotspotUrlQuery);
          if (urlList != null && urlList.size() > 0) {
            for (PanoHotspotUrl01Model url : urlList) {
              PanoMaterial panoMaterial =
                  panoMaterial01Mapper.selectByPrimaryKey(url.hotspotUrlObjectId);
              if (panoMaterial != null) {
                // 在gallery节点下增加img节点显示热点下的素材图
                Element galleryImgElement = _xmldoc.createElement("img");
                galleryElement.appendChild(galleryImgElement);
                galleryImgElement.setAttribute("name", "img" + url.sortKey);
                // 如果该素材是gif图，创建style
                if (panoMaterial.materialPath.toLowerCase().endsWith(".gif")
                    && !ObjectUtils.isEmpty(panoMaterial.gifDelayTime)) {
                  panoMaterial.materialPath = panoMaterial.materialPath.substring(0,
                      panoMaterial.materialPath.lastIndexOf(".")) + ".png";
                  // 创建style
                  Element galleryStyleElement = _xmldoc.createElement("style");
                  _sceneElement.appendChild(galleryStyleElement);
                  galleryStyleElement.setAttribute("name",
                      "v_" + panoMaterial.materialId + "_gifstyle");
                  galleryStyleElement.setAttribute("url", doGetMaterialFolderPath(
                      panoMaterial.expositionId, _inForm, panoMaterial.materialPath));
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
                  galleryImgElement.setAttribute("url", doGetMaterialFolderPath(
                      panoMaterial.expositionId, _inForm, panoMaterial.materialPath));
                }
              }
            }
          }
        }

      }

      // 2.场景导航热点
      if ((PanoConstantsIF.HOTSPOT_TYPE_HOTSPOT_CHANGE_SCENE).equals(hotspot.hotspotType)) {
        // 查看该导航热点下有无存在的场景，有则显示该热点。
        PanoHotspotUrlQuery hotspotUrlQuery = new PanoHotspotUrlQuery();
        hotspotUrlQuery.createCriteria().andHotspotIdEqualTo(hotspot.hotspotId);
        hotspotUrlQuery.setOrderByClause("sort_key");
        List<PanoHotspotUrl01Model> hotspotUrl =
            panoHotspotUrl01Mapper.selectByBaseModel(hotspotUrlQuery);
        if (hotspotUrl != null && hotspotUrl.size() > 0) {
          PanoPanorama pano =
              panoPanorama01Mapper.selectByPrimaryKey(hotspotUrl.get(0).hotspotUrlObjectId);
          if (pano != null) {
            // 创建热点节点
            Element hotspotElement = _xmldoc.createElement("hotspot");
            _sceneElement.appendChild(hotspotElement);
            hotspotElement.setAttribute("name", "v_" + hotspot.hotspotId);
            if (!ObjectUtils.isEmpty(hotspot.hotspotScale)) {
              hotspotElement.setAttribute("scale", hotspot.hotspotScale);
            }
            hotspotElement.setAttribute("atv", hotspot.hotspotAtv);
            hotspotElement.setAttribute("ath", hotspot.hotspotAth);
            hotspotElement.setAttribute("zoom", "true");
            hotspotElement.setAttribute("zorder", "1");
            hotspotElement.setAttribute("tooltip", hotspot.hotspotTooltip);

            hotspotElement.setAttribute("distorted", "true");

            // 判断该热点图标是否为gif图
            PanoMaterial hotspotMaterial =
                panoMaterial01Mapper.selectByPrimaryKey(hotspot.materialId);
            if (!ObjectUtils.isEmpty(hotspot.gifDelayTime)) {
              // 创建style
              Element gifStyleElement = _xmldoc.createElement("style");
              _sceneElement.appendChild(gifStyleElement);
              gifStyleElement.setAttribute("name", "v_" + hotspot.hotspotId + "_gif");
              hotspot.materialPath =
                  hotspot.materialPath.substring(0, hotspot.materialPath.lastIndexOf(".")) + ".png";
              gifStyleElement.setAttribute("url", doGetMaterialFolderPath(
                  hotspotMaterial.expositionId, _inForm, hotspot.materialPath));
              gifStyleElement.setAttribute("crop",
                  "0|0|" + hotspot.gifWidth + "|" + hotspot.gifHeight);
              gifStyleElement.setAttribute("framewidth", hotspot.gifWidth);
              gifStyleElement.setAttribute("frameheight", hotspot.gifHeight);
              gifStyleElement.setAttribute("frame", "0");
              gifStyleElement.setAttribute("lastframe", hotspot.gifFrameCount);
              gifStyleElement.setAttribute("onloaded",
                  "hotspot_animate(" + hotspot.gifDelayTime + ");");
              hotspotElement.setAttribute("style", "v_" + hotspot.hotspotId + "_gif|tooltip");

            } else {
              hotspotElement.setAttribute("style", "tooltip");
              hotspotElement.setAttribute("url", doGetMaterialFolderPath(
                  hotspotMaterial.expositionId, _inForm, hotspot.materialPath));
            }

            // 开始拼接onclick事件
            StringBuffer hotspotClickEvent = new StringBuffer();
            // 检索该热点导航到的全景图ID
            hotspotUrlQuery = new PanoHotspotUrlQuery();
            hotspotUrlQuery.createCriteria().andHotspotIdEqualTo(hotspot.hotspotId);
            hotspotUrlQuery.setOrderByClause("sort_key");
            List<PanoHotspotUrl01Model> url =
                panoHotspotUrl01Mapper.selectByBaseModel(hotspotUrlQuery);
            String panoId = "";
            if (url != null && url.size() > 0) {
              panoId = url.get(0).hotspotUrlObjectId;
            }
            // 检索与场景相关的地图热点
            PanoExpositionMapHotspotQuery expositionMapHotspotQuery =
                new PanoExpositionMapHotspotQuery();
            expositionMapHotspotQuery.createCriteria().andPanoramaIdEqualTo(panoId);
            List<PanoExpositionMapHotspot01Model> mapHotspotList =
                panoExpositionMapHotspot01Mapper.selectByBaseModel(expositionMapHotspotQuery);
            // 检索相关联的地图热点
            if (ObjectUtils.isNotEmpty(mapHotspotList)) {
              PanoExpositionMapHotspot01Model mapHotspot = mapHotspotList.get(0);
              // 设置当前点击的热点zorder小于其他热点zorder，false为不需要在拼接时换行
              hotspotClickEvent =
                  setMapHotspotZorder(hotspotClickEvent, false, _inForm, mapHotspot);
              hotspotClickEvent
                  .append("activatespot(V_" + mapHotspot.expositionMapHotspotId + ",0);");
              // 设定相关联的地图热点的雷达角度
              if (!ObjectUtils.isEmpty(mapHotspot.expositionMapHotspotHeading)) {
                hotspotClickEvent.append(
                    "set(layer[radar].heading," + mapHotspot.expositionMapHotspotHeading + ");");
              }
            } else {
              hotspotClickEvent.append("activatespot(V_,0);");
            }

            // 判断从当前热点跳转到的场景有无视角点
            // 视角点事件
            StringBuffer lookatEvent = new StringBuffer();

            // 跳转时判断背景音乐
            PanoExposition panoExposition =
                panoExpositionMapper.selectByPrimaryKey(_panoPanorama.expositionId);
            if (panoExposition != null && !ObjectUtils.isEmpty(panoExposition.expositionSoundId)) {
              // 有背景音乐
              PanoMaterial expositionSound =
                  panoMaterial01Mapper.selectByPrimaryKey(panoExposition.expositionSoundId);
              // 是否有视角点
              if (!ObjectUtils.isEmpty(hotspot.panoramaHlookat)
                  && !StringUtils.isEmpty(hotspot.panoramaVlookat)) {
                lookatEvent.append("view.hlookat=" + hotspot.panoramaHlookat + "&view.vlookat="
                    + hotspot.panoramaVlookat);

                hotspotClickEvent
                    .append("set(layer[tooltip].visible,false);playBefore(v_"
                        + panoId + ", " + doGetMaterialFolderPath(expositionSound.expositionId,
                            _inForm, expositionSound.materialPath)
                        + "," + lookatEvent.toString() + " );");
              } else {
                hotspotClickEvent.append("set(layer[tooltip].visible,false);playBefore(v_" + panoId
                    + ", " + doGetMaterialFolderPath(expositionSound.expositionId, _inForm,
                        expositionSound.materialPath)
                    + ",null );");
              }

            } else {
              // 没有背景音乐
              // 是否有视角点
              if (!ObjectUtils.isEmpty(hotspot.panoramaHlookat)
                  && !StringUtils.isEmpty(hotspot.panoramaVlookat)) {
                lookatEvent.append("view.hlookat=" + hotspot.panoramaHlookat + "&view.vlookat="
                    + hotspot.panoramaVlookat);

                hotspotClickEvent.append("set(layer[tooltip].visible,false);playBefore(v_" + panoId
                    + ", null," + lookatEvent.toString() + " );");
              } else {
                hotspotClickEvent.append(
                    "set(layer[tooltip].visible,false);playBefore(v_" + panoId + ",null,null);");
              }

            }

            hotspotClickEvent.append("skin_getBoder('v_" + panoId + "');");

            // 检索该导航点有无绑定的下一个场景的推荐路线点ID
            PanoPanoramaHotspot currentHotspot =
                panoPanoramaHotspot01Mapper.selectByPrimaryKey(hotspot.hotspotId);
            if (currentHotspot != null
                && !StringUtils.isEmpty(currentHotspot.nextRecommendHotspotId)
                && !ObjectUtils.isEmpty(panoId)) {
              // 检查下一个推荐线路导航热点是否绑定了场景，若没有绑定场景，则展览中不会出现该热点，此时应置推荐线路plugin可见为false
              hotspotUrlQuery = new PanoHotspotUrlQuery();
              hotspotUrlQuery.createCriteria()
                  .andHotspotIdEqualTo(currentHotspot.nextRecommendHotspotId);
              hotspotUrlQuery.setOrderByClause("sort_key");
              List<PanoHotspotUrl01Model> nextHotspotUrl =
                  panoHotspotUrl01Mapper.selectByBaseModel(hotspotUrlQuery);
              // 检索下一个推荐线路点是否在当前显示的场景上
              // 取得场景里所有热点
              PanoPanoramaHotspotQuery panoramaHotspotQuery = new PanoPanoramaHotspotQuery();
              panoramaHotspotQuery.createCriteria().andPanoramaIdEqualTo(panoId);
              panoramaHotspotQuery.setOrderByClause("hotspot_type DESC");
              List<PanoPanoramaHotspot01Model> currrentPanoHotspotList =
                  panoPanoramaHotspot01Mapper.selectByBaseModel(panoramaHotspotQuery);
              boolean hotspotIsInCurrentPano = false;
              if (currrentPanoHotspotList != null && currrentPanoHotspotList.size() > 0) {
                // 查找下一个推荐线路点是否在当前显示的场景上
                for (PanoPanoramaHotspot01Model crtHotspot : currrentPanoHotspotList) {
                  if (currentHotspot.nextRecommendHotspotId.equals(crtHotspot.hotspotId)) {
                    // 下一个推荐线路热点在所要显示的场景上
                    hotspotIsInCurrentPano = true;
                  }
                }
              }
              if (nextHotspotUrl != null && nextHotspotUrl.size() > 0 && hotspotIsInCurrentPano) {

                // 如果没有进入VR模式，点亮一下场景里的推荐路线点
                hotspotClickEvent.append("set(hotspot[recommedInfoHotspot].ath, get(hotspot[v_"
                    + currentHotspot.nextRecommendHotspotId + "].ath));"
                    + "set(hotspot[recommedInfoHotspot].atv, get(hotspot[v_"
                    + currentHotspot.nextRecommendHotspotId + "].atv));"
                    + "set(hotspot[recommedInfoHotspot].visible,true);");
                // 设置推荐路线点的distorted为false
                hotspotClickEvent.append("set(hotspot[v_" + currentHotspot.nextRecommendHotspotId
                    + "].distorted,false)");

                // 设置上个导航点的distorted为true
                hotspotClickEvent
                    .append("set(hotspot[v_" + currentHotspot.hotspotId + "].distorted,true)");
              } else {
                // 若没下一个推荐线路点没有场景，关闭推荐线路文字浮动的显示
                hotspotClickEvent.append("set(hotspot[recommedInfoHotspot].visible,false);");
              }
            } else {
              // 若没有下一个场景的推荐线路点，关闭推荐线路文字浮动的显示
              hotspotClickEvent.append("set(hotspot[recommedInfoHotspot].visible,false);");
            }

            hotspotElement.setAttribute("onclick",
                "stopsound(hotspotsnd);" + hotspotClickEvent.toString());
          }
        }
      }

      if ((PanoConstantsIF.HOTSPOT_TYPE_HOTSPOT_LOGO).equals(hotspot.hotspotType)) {
        // 3.LOGO热点
        Element hotspotElement = _xmldoc.createElement("hotspot");
        _sceneElement.appendChild(hotspotElement);
        hotspotElement.setAttribute("name", "v_" + hotspot.hotspotId);
        if (!ObjectUtils.isEmpty(hotspot.hotspotScale)) {
          hotspotElement.setAttribute("scale", hotspot.hotspotScale);
        }
        PanoMaterial hotspotMaterial = panoMaterial01Mapper.selectByPrimaryKey(hotspot.materialId);
        hotspotElement.setAttribute("atv", hotspot.hotspotAtv);
        hotspotElement.setAttribute("ath", hotspot.hotspotAth);
        hotspotElement.setAttribute("zoom", "true");
        hotspotElement.setAttribute("zorder", "1");
        hotspotElement.setAttribute("distorted", "true");
        hotspotElement.setAttribute("tooltip", hotspot.hotspotTooltip);
        hotspotElement.setAttribute("style", "tooltip|normalhs");
        hotspotElement.setAttribute("url",
            doGetMaterialFolderPath(hotspotMaterial.expositionId, _inForm, hotspot.materialPath));
        hotspotElement.setAttribute("handcursor", "false");

      }

      if ((PanoConstantsIF.HOTSPOT_TYPE_HOTSPOT_POLYGON).equals(hotspot.hotspotType)) {
        // 4.多边形热点
        // 找到多边形
        PanoPolygonHotspotQuery polygonHotspotQuery = new PanoPolygonHotspotQuery();
        polygonHotspotQuery.createCriteria().andPolygonIdEqualTo(hotspot.hotspotId);
        List<PanoPolygonHotspot01Model> polygonPointList =
            panoPolygonHotspot01Mapper.selectByBaseModel(polygonHotspotQuery);
        if (polygonPointList != null && polygonPointList.size() > 0) {
          Element hotspotElement = _xmldoc.createElement("hotspot");
          _sceneElement.appendChild(hotspotElement);
          hotspotElement.setAttribute("name", "v_" + hotspot.hotspotId);
          hotspotElement.setAttribute("tooltip", hotspot.hotspotTooltip);
          hotspotElement.setAttribute("style", "tooltip|normalhs");
          hotspotElement.setAttribute("zoom", "true");
          hotspotElement.setAttribute("zorder", "0");
          hotspotElement.setAttribute("distorted", "true");

          // 判断当前多边形有无颜色和透明度
          if (!ObjectUtils.isEmpty(hotspot.polygonFillalpha)
              && !StringUtils.isEmpty(hotspot.polygonFillcolor)) {
            hotspotElement.setAttribute("fillcolor", hotspot.polygonFillcolor);
            hotspotElement.setAttribute("borderalpha", "0.1");
            hotspotElement.setAttribute("fillalpha", "0.1");
            // 多边形的鼠标悬停和离开事件
            hotspotElement.setAttribute("onhover",
                "set('hotspot[v_" + hotspot.hotspotId + "].fillcolor'," + hotspot.polygonFillcolor
                    + ");" + "set('hotspot[v_" + hotspot.hotspotId + "].fillalpha',"
                    + hotspot.polygonFillalpha + ");" + "set('hotspot[v_" + hotspot.hotspotId
                    + "].borderwidth',0);");

            hotspotElement.setAttribute("onloaded",
                "set(hotspot[v_" + hotspot.hotspotId + "].onout,callToolTipOutEvent();"
                    + "set(hotspot[v_" + hotspot.hotspotId + "].fillalpha,0.1);" + "set(hotspot[v_"
                    + hotspot.hotspotId + "].borderalpha,0.1););");
          } else {
            hotspotElement.setAttribute("bordercolor", "0x489620");
            hotspotElement.setAttribute("fillcolor", "0x489620");
            hotspotElement.setAttribute("fillalpha", "0.4");
          }

          // 检查该热点下有无素材或外部链接，没有则不加载click事件
          PanoHotspotUrlQuery hotspotUrlQuery = new PanoHotspotUrlQuery();
          hotspotUrlQuery.createCriteria().andHotspotIdEqualTo(hotspot.hotspotId);
          hotspotUrlQuery.setOrderByClause("sort_key");
          if (PanoConstantsIF.HOTSPOT_URL_TYPE_IMAGE.equals(hotspot.hotspotUrlType)) {
            // 热点下链接素材
            List<PanoHotspotUrl01Model> resultList =
                panoHotspotUrl01Mapper.selectByBaseModel(hotspotUrlQuery);
            if (resultList != null && resultList.size() > 0) {
              hotspotElement.setAttribute("galleryname", "v_" + hotspot.hotspotId + "_gallery");
              hotspotElement.setAttribute("onclick", "show_gallery(get(galleryname))");
            }
          } else if (PanoConstantsIF.HOTSPOT_URL_TYPE_LINK.equals(hotspot.hotspotUrlType)) {
            // 热点下是外部链接
            hotspotElement.setAttribute("onclick",
                "openurl('" + hotspot.externalLinkAddress + "',_blank)");
          } else if (PanoConstantsIF.HOTSPOT_URL_TYPE_VIDEO.equals(hotspot.hotspotUrlType)) {
            // 热点下是视频
            List<PanoHotspotUrl01Model> resultList =
                panoHotspotUrl01Mapper.selectByBaseModel(hotspotUrlQuery);
            if (resultList != null && resultList.size() > 0) {
              PanoMaterial hotspotMaterial =
                  panoMaterial01Mapper.selectByPrimaryKey(resultList.get(0).hotspotUrlObjectId);
              if (hotspotMaterial != null) {
                StringBuffer onclick = new StringBuffer();
                onclick.append("setMusicHotspotDefalt();openvideo('" + doGetMaterialFolderPath(
                    hotspotMaterial.expositionId, _inForm, hotspotMaterial.materialPath) + "');");
                // 停止背景音乐
                onclick.append(
                    "stopsound('expositionbgsnd');stopsound('scenebgsnd');stopsound(hotspotsnd);");

                // 结束视频播放时恢复背景音乐
                if (!ObjectUtils.isEmpty(_sceneElement.getAttribute("onstart"))) {
                  onclick.append("set(layer[bgVideoClose].onclick,closevideo();"
                      + _sceneElement.getAttribute("onstart") + ")");
                } else {
                  // 检索展览会公共音乐
                  PanoExposition panoExposition =
                      panoExpositionMapper.selectByPrimaryKey(_inForm.selectedExpositionId);
                  String commonSound = "";
                  if (panoExposition.expositionSoundId != null
                      && !StringUtils.isEmpty(panoExposition.expositionSoundId)) {
                    // 有公共音乐
                    PanoMaterial expositionSound =
                        panoMaterial01Mapper.selectByPrimaryKey(panoExposition.expositionSoundId);
                    commonSound = "playsound(expositionbgsnd,"
                        + doGetMaterialFolderPath(expositionSound.expositionId, _inForm,
                            expositionSound.materialPath)
                        + ",0);";
                    onclick.append("set(layer[bgVideoClose].onclick,closevideo();"
                        + commonSound.toString() + ")");
                  } else {
                    onclick.append("set(layer[bgVideoClose].onclick,closevideo();)");
                  }

                }

                hotspotElement.setAttribute("onclick", onclick.toString());
              }
            }
          } else if (PanoConstantsIF.HOTSPOT_URL_TYPE_TEXT_IMAGE.equals(hotspot.hotspotUrlType)) {
            hotspotElement.setAttribute("onclick",
                "js(doOpenTextImgPage('" + hotspot.hotspotId + "'))");
          }

          // 遍历多边形下的点
          for (PanoPolygonHotspot polygonPoint : polygonPointList) {
            Element pointElement = _xmldoc.createElement("point");
            hotspotElement.appendChild(pointElement);
            pointElement.setAttribute("atv", polygonPoint.polygonPointAtv);
            pointElement.setAttribute("ath", polygonPoint.polygonPointAth);
          }
          // 增加显示点下素材信息用的gallery节点
          if (PanoConstantsIF.HOTSPOT_URL_TYPE_IMAGE.equals(hotspot.hotspotUrlType)) {
            Element galleryElement = _xmldoc.createElement("gallery");
            _sceneElement.appendChild(galleryElement);
            galleryElement.setAttribute("name", "v_" + hotspot.hotspotId + "_gallery");

            hotspotUrlQuery = new PanoHotspotUrlQuery();
            hotspotUrlQuery.createCriteria().andHotspotIdEqualTo(hotspot.hotspotId);
            hotspotUrlQuery.setOrderByClause("sort_key");
            List<PanoHotspotUrl01Model> urlList =
                panoHotspotUrl01Mapper.selectByBaseModel(hotspotUrlQuery);
            if (urlList != null && urlList.size() > 0) {
              for (PanoHotspotUrl01Model url : urlList) {
                PanoMaterial panoMaterial =
                    panoMaterial01Mapper.selectByPrimaryKey(url.hotspotUrlObjectId);
                if (panoMaterial != null) {
                  // 在gallery节点下增加img节点显示热点下的素材图
                  Element galleryImgElement = _xmldoc.createElement("img");
                  galleryElement.appendChild(galleryImgElement);
                  galleryImgElement.setAttribute("name", "img" + url.sortKey);
                  // 如果该素材是gif图，创建style
                  if (panoMaterial.materialPath.toLowerCase().endsWith(".gif")
                      && !ObjectUtils.isEmpty(panoMaterial.gifDelayTime)) {
                    panoMaterial.materialPath = panoMaterial.materialPath.substring(0,
                        panoMaterial.materialPath.lastIndexOf(".")) + ".png";
                    // 创建style
                    Element galleryStyleElement = _xmldoc.createElement("style");
                    _sceneElement.appendChild(galleryStyleElement);
                    galleryStyleElement.setAttribute("name",
                        "v_" + panoMaterial.materialId + "_gifstyle");
                    galleryStyleElement.setAttribute("url", doGetMaterialFolderPath(
                        panoMaterial.expositionId, _inForm, panoMaterial.materialPath));
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
                    galleryImgElement.setAttribute("url", doGetMaterialFolderPath(
                        panoMaterial.expositionId, _inForm, panoMaterial.materialPath));
                  }
                }
              }
            }
          }
        }
      }

      if ((PanoConstantsIF.HOTSPOT_TYPE_HOTSPOT_MUSIC).equals(hotspot.hotspotType)) {
        // 5.音频热点
        String fisrtMaterialUrl = "";
        String secondMaterialUrl = "";
        String soundpath = "";
        String othersoundpath = "";

        if (!ObjectUtils.isEmpty(_panoPanorama.panoramaSoundId)) {
          PanoMaterial panoMaterial =
              panoMaterial01Mapper.selectByPrimaryKey(_panoPanorama.panoramaSoundId);
          othersoundpath = panoMaterial.materialPath;
          othersoundpath =
              doGetMaterialFolderPath(panoMaterial.expositionId, _inForm, othersoundpath);
        } else if (!StringUtils.isEmpty(panoExpositionMapper
            .selectByPrimaryKey(_panoPanorama.expositionId).expositionSoundId)) {
          PanoMaterial panoMaterial = panoMaterial01Mapper.selectByPrimaryKey(panoExpositionMapper
              .selectByPrimaryKey(_panoPanorama.expositionId).expositionSoundId);
          othersoundpath = panoMaterial.materialPath;
          othersoundpath =
              doGetMaterialFolderPath(panoMaterial.expositionId, _inForm, othersoundpath);
        }

        PanoHotspotUrlQuery hotspotUrlQuery = new PanoHotspotUrlQuery();
        hotspotUrlQuery.createCriteria().andHotspotIdEqualTo(hotspot.hotspotId);
        hotspotUrlQuery.setOrderByClause("sort_key");
        List<PanoHotspotUrl01Model> result =
            panoHotspotUrl01Mapper.selectByBaseModel(hotspotUrlQuery);
        if (result != null && result.size() > 0) {
          for (PanoHotspotUrl01Model panoHotspotUrl : result) {
            if (BigDecimal.ZERO.equals(panoHotspotUrl.sortKey)) {
              if (!panoHotspotUrl.hotspotUrlObjectId.equals("musicMaterialId")) {
                PanoMaterial panoMaterial =
                    panoMaterial01Mapper.selectByPrimaryKey(panoHotspotUrl.hotspotUrlObjectId);
                if (panoMaterial != null) {
                  soundpath = panoMaterial.materialPath;
                  soundpath =
                      doGetMaterialFolderPath(panoMaterial.expositionId, _inForm, soundpath);
                }
              }
            } else {
              if (panoHotspotUrl.hotspotUrlObjectId.equals(panoPanoramaHotspot01Mapper
                  .selectByPrimaryKey(hotspot.hotspotId).hotspotImageId)) {

                // 创建第一个图的热点
                Element hotspotElement = _xmldoc.createElement("hotspot");
                _sceneElement.appendChild(hotspotElement);
                hotspotElement.setAttribute("hotspot_name", "v_" + hotspot.hotspotId);
                hotspotElement.setAttribute("name", "v_" + hotspot.hotspotId + "_1");
                if (!ObjectUtils.isEmpty(hotspot.hotspotScale)) {
                  hotspotElement.setAttribute("scale", hotspot.hotspotScale);
                }

                hotspotElement.setAttribute("atv", hotspot.hotspotAtv);
                hotspotElement.setAttribute("ath", hotspot.hotspotAth);
                hotspotElement.setAttribute("zoom", "true");
                hotspotElement.setAttribute("zorder", "1");
                hotspotElement.setAttribute("distorted", "true");
                hotspotElement.setAttribute("tooltip", hotspot.hotspotTooltip);

                PanoMaterial panoMaterial =
                    panoMaterial01Mapper.selectByPrimaryKey(panoHotspotUrl.hotspotUrlObjectId);

                // 创建style
                if (!ObjectUtils.isEmpty(panoMaterial.gifDelayTime)) {
                  Element gifStyleElement = _xmldoc.createElement("style");
                  _sceneElement.appendChild(gifStyleElement);
                  gifStyleElement.setAttribute("name", "v_" + panoMaterial.materialId + "_gif");
                  fisrtMaterialUrl = panoMaterial.materialPath.substring(0,
                      panoMaterial.materialPath.lastIndexOf(".")) + ".png";
                  gifStyleElement.setAttribute("url", doGetMaterialFolderPath(
                      panoMaterial.expositionId, _inForm, fisrtMaterialUrl));
                  gifStyleElement.setAttribute("crop",
                      "0|0|" + panoMaterial.gifWidth + "|" + panoMaterial.gifHeight);
                  gifStyleElement.setAttribute("framewidth", panoMaterial.gifWidth);
                  gifStyleElement.setAttribute("frameheight", panoMaterial.gifHeight);
                  gifStyleElement.setAttribute("frame", "0");
                  gifStyleElement.setAttribute("lastframe", panoMaterial.gifFrameCount);
                  gifStyleElement.setAttribute("onloaded",
                      "hotspot_animate(" + panoMaterial.gifDelayTime + ");");
                  hotspotElement.setAttribute("style",
                      "v_" + panoMaterial.materialId + "_gif|tooltip|normalhs");

                } else {
                  hotspotElement.setAttribute("style", "tooltip|normalhs");
                  hotspotElement.setAttribute("url", doGetMaterialFolderPath(
                      panoMaterial.expositionId, _inForm, panoMaterial.materialPath));
                }

                // 检查该热点下有音乐
                if (PanoConstantsIF.HOTSPOT_URL_TYPE_SOUND.equals(hotspot.hotspotUrlType)) {

                  hotspotElement.setAttribute("musicFlg", "1");
                  hotspotElement.setAttribute("soundpath", soundpath);
                  hotspotElement.setAttribute("othersoundpath", othersoundpath);
                  hotspotElement.setAttribute("onclick",
                      "playHotspotMusic(get(musicFlg),get(hotspot_name),get(soundpath),get(othersoundpath))");
                }

              } else if (!panoHotspotUrl.hotspotUrlObjectId.equals(panoPanoramaHotspot01Mapper
                  .selectByPrimaryKey(hotspot.hotspotId).hotspotImageId)) {

                // 做第二个图的热点
                Element hotspotElement = _xmldoc.createElement("hotspot");
                _sceneElement.appendChild(hotspotElement);
                hotspotElement.setAttribute("visible", "false");
                hotspotElement.setAttribute("hotspot_name", "v_" + hotspot.hotspotId);
                hotspotElement.setAttribute("name", "v_" + hotspot.hotspotId + "_2");
                if (!ObjectUtils.isEmpty(hotspot.hotspotScale)) {
                  hotspotElement.setAttribute("scale", hotspot.hotspotScale);
                }

                hotspotElement.setAttribute("atv", hotspot.hotspotAtv);
                hotspotElement.setAttribute("ath", hotspot.hotspotAth);
                hotspotElement.setAttribute("zoom", "true");
                hotspotElement.setAttribute("zorder", "1");
                hotspotElement.setAttribute("distorted", "true");
                hotspotElement.setAttribute("tooltip", hotspot.hotspotTooltip);

                PanoMaterial panoMaterial =
                    panoMaterial01Mapper.selectByPrimaryKey(panoHotspotUrl.hotspotUrlObjectId);

                if (!ObjectUtils.isEmpty(panoMaterial.gifDelayTime)) {
                  Element gifStyleElement = _xmldoc.createElement("style");
                  _sceneElement.appendChild(gifStyleElement);
                  gifStyleElement.setAttribute("name", "v_" + panoMaterial.materialId + "_gif");
                  secondMaterialUrl = panoMaterial.materialPath.substring(0,
                      panoMaterial.materialPath.lastIndexOf(".")) + ".png";
                  // 创建style
                  gifStyleElement.setAttribute("url", doGetMaterialFolderPath(
                      panoMaterial.expositionId, _inForm, secondMaterialUrl));
                  gifStyleElement.setAttribute("crop",
                      "0|0|" + panoMaterial.gifWidth + "|" + panoMaterial.gifHeight);
                  gifStyleElement.setAttribute("framewidth", panoMaterial.gifWidth);
                  gifStyleElement.setAttribute("frameheight", panoMaterial.gifHeight);
                  gifStyleElement.setAttribute("frame", "0");
                  gifStyleElement.setAttribute("lastframe", panoMaterial.gifFrameCount);
                  gifStyleElement.setAttribute("onloaded",
                      "hotspot_animate(" + panoMaterial.gifDelayTime + ");");
                  hotspotElement.setAttribute("style",
                      "v_" + panoMaterial.materialId + "_gif|tooltip");
                } else {
                  hotspotElement.setAttribute("style", "tooltip");
                  hotspotElement.setAttribute("url", doGetMaterialFolderPath(
                      panoMaterial.expositionId, _inForm, panoMaterial.materialPath));
                }
                // 检查该热点下有音乐
                if (PanoConstantsIF.HOTSPOT_URL_TYPE_SOUND.equals(hotspot.hotspotUrlType)) {

                  hotspotElement.setAttribute("musicFlg", "2");
                  hotspotElement.setAttribute("soundpath", soundpath);
                  hotspotElement.setAttribute("othersoundpath", othersoundpath);

                  hotspotElement.setAttribute("onclick",
                      "playHotspotMusic(get(musicFlg),get(hotspot_name),get(soundpath),get(othersoundpath))");
                }
              }

            }
          }
        }

      }
    }
  }

  /**
   * 生成场景上的导航图
   * 
   * @param _inForm
   * @param _xmldoc
   * @param _newRoot
   * @throws Exception
   */
  public boolean createExpositionMap(Pano0110Form _inForm, Document _xmldoc, Element _newRoot)
      throws Exception {
    PanoExpositionMapQuery expositionMapQuery = new PanoExpositionMapQuery();
    expositionMapQuery.createCriteria().andExpositionIdEqualTo(_inForm.selectedExpositionId)
        .andExpositionMapUseStateEqualTo(FlagStatus.Enable.toString());
    List<PanoExpositionMap01Model> panoExpositionMapList =
        panoExpositionMap01Mapper.selectByBaseModel(expositionMapQuery);

    if (ObjectUtils.isNotEmpty(panoExpositionMapList)) {
      PanoExpositionMap01Model panoExpositionMap = panoExpositionMapList.get(0);

      Element mapContainerElement = _xmldoc.createElement("layer");
      _newRoot.appendChild(mapContainerElement);
      mapContainerElement.setAttribute("name", "layer_nimi_map_container");
      mapContainerElement.setAttribute("url",
          panoExpositionMap.expositionMapPath.replace(_inForm.selectedExpositionId + "/", ""));
      mapContainerElement.setAttribute("keep", "true");
      mapContainerElement.setAttribute("align", "rightbottom");
      mapContainerElement.setAttribute("x", "10");
      mapContainerElement.setAttribute("y", "64");
      mapContainerElement.setAttribute("bgcolor", "0xCCCCCC");
      mapContainerElement.setAttribute("bgalpha", "0.5");
      mapContainerElement.setAttribute("scalechildren", "true");
      mapContainerElement.setAttribute("maskchildren", "true");
      mapContainerElement.setAttribute("handcursor", "false");
      mapContainerElement.setAttribute("zorder", "2");
      mapContainerElement.setAttribute("scale", "0");

      createMapHotspotNodes(_inForm, _xmldoc, _newRoot, panoExpositionMap);

      return true;
    }
    return false;
  }

  /**
   * 生成场景上导航图上的热点
   * 
   * @param _inForm
   * @param _xmldoc
   * @param _newRoot
   * @param panoExpositionMap
   * @throws Exception
   */
  public void createMapHotspotNodes(Pano0110Form _inForm, Document _xmldoc, Element _newRoot,
      PanoExpositionMap panoExpositionMap) throws Exception {

    PanoExpositionMapHotspotQuery expositionMapHotspotQuery = new PanoExpositionMapHotspotQuery();
    expositionMapHotspotQuery.createCriteria()
        .andExpositionMapIdEqualTo(panoExpositionMap.expositionMapId).andPanoramaIdIsNotNull()
        .andPanoramaIdNotEqualTo("");
    List<PanoExpositionMapHotspot01Model> panoExpositionMapHotspotList =
        panoExpositionMapHotspot01Mapper.selectByBaseModel(expositionMapHotspotQuery);

    if (panoExpositionMapHotspotList != null) {
      for (PanoExpositionMapHotspot01Model MapHotspot : panoExpositionMapHotspotList) {

        PanoPanorama panoPanorama = panoPanorama01Mapper.selectByPrimaryKey(MapHotspot.panoramaId);
        if (panoPanorama != null) {
          Element mapHotspotElement = _xmldoc.createElement("layer");
          _newRoot.appendChild(mapHotspotElement);
          mapHotspotElement.setAttribute("name", "v_" + MapHotspot.expositionMapHotspotId);

          mapHotspotElement.setAttribute("parent", "layer_nimi_map_container");

          // 读取热点的url
          PanoExpositionMapHotspot panoExpositionMapHotspot = panoExpositionMapHotspot01Mapper
              .selectByPrimaryKey(MapHotspot.expositionMapHotspotId);
          // 如果存在地图热点url
          if (panoExpositionMapHotspot != null
              && !StringUtils.isEmpty(panoExpositionMapHotspot.expositionMapHotspotUrl)) {
            // 地图图片路径修正20190819
            String expositionMapHotspotUrl = panoExpositionMapHotspot.expositionMapHotspotUrl;
            expositionMapHotspotUrl = expositionMapHotspotUrl
                .replace("static/pano/pano/common/template", "framework/panorama/template");
            mapHotspotElement.setAttribute("url", expositionMapHotspotUrl);
          } else {
            mapHotspotElement.setAttribute("url",
                "framework/panorama/template/mappoint_orange.png");
          }
          mapHotspotElement.setAttribute("align", "rightbottom");
          mapHotspotElement.setAttribute("bgalpha", "1.0");
          mapHotspotElement.setAttribute("x", MapHotspot.expositionMapHotspotX);
          mapHotspotElement.setAttribute("y", MapHotspot.expositionMapHotspotY);
          mapHotspotElement.setAttribute("handcursor", "true");
          mapHotspotElement.setAttribute("keep", "true");

          // 展览音乐获得
          PanoExposition expo = panoExpositionMapper.selectByPrimaryKey(panoPanorama.expositionId);
          // 拼接click事件
          StringBuffer mapHotspotClickEvent = new StringBuffer();
          // 设置当前点击的热点zorder小于其他热点zorder，false为不需要在拼接时换行
          mapHotspotClickEvent =
              setMapHotspotZorder(mapHotspotClickEvent, false, _inForm, MapHotspot);
          // 关闭推荐线路文字浮动的显示
          mapHotspotClickEvent.append("set(hotspot[recommedInfoHotspot].visible,false);");
          // 检索相关联的地图热点
          expositionMapHotspotQuery = new PanoExpositionMapHotspotQuery();
          expositionMapHotspotQuery.createCriteria().andPanoramaIdEqualTo(panoPanorama.panoramaId);
          List<PanoExpositionMapHotspot01Model> mapHotspotList =
              panoExpositionMapHotspot01Mapper.selectByBaseModel(expositionMapHotspotQuery);
          // 检索相关联的地图热点
          if (ObjectUtils.isNotEmpty(mapHotspotList)) {
            mapHotspotClickEvent
                .append("activatespot(V_" + MapHotspot.expositionMapHotspotId + ",0);");
          } else {
            mapHotspotClickEvent.append("activatespot(V_,0);");
          }
          // 设定相关联的地图热点的雷达角度
          if (!ObjectUtils.isEmpty(MapHotspot.expositionMapHotspotHeading)) {
            mapHotspotClickEvent.append(
                "set(layer[radar].heading," + MapHotspot.expositionMapHotspotHeading + ");");
          }
          // 视角点事件
          StringBuffer lookatEvent = new StringBuffer();
          // 该场景下存在音乐的情况
          if (expo.expositionSoundId != null && !ObjectUtils.isEmpty(expo.expositionSoundId)) {

            String expositionSoundId = expo.expositionSoundId;
            PanoMaterial panoMaterial = panoMaterial01Mapper.selectByPrimaryKey(expositionSoundId);
            // 判断跳转至的场景有无视角点
            if (!ObjectUtils.isEmpty(panoPanorama.panoramaHlookat)
                && !StringUtils.isEmpty(panoPanorama.panoramaVlookat)) {
              lookatEvent.append("view.hlookat=" + panoPanorama.panoramaHlookat + "&view.vlookat="
                  + panoPanorama.panoramaVlookat);

              mapHotspotClickEvent.append("playBefore(v_" + MapHotspot.panoramaId + ","
                  + doGetMaterialFolderPath(panoMaterial.expositionId, _inForm,
                      panoMaterial.materialPath)
                  + "," + lookatEvent.toString() + ");");
            } else {
              mapHotspotClickEvent
                  .append("playBefore(v_" + MapHotspot.panoramaId + "," + doGetMaterialFolderPath(
                      panoMaterial.expositionId, _inForm, panoMaterial.materialPath) + ",null);");
            }
          } else {
            // 没有背景音乐
            // 是否有视角点
            if (!ObjectUtils.isEmpty(panoPanorama.panoramaHlookat)
                && !StringUtils.isEmpty(panoPanorama.panoramaVlookat)) {
              lookatEvent.append("view.hlookat=" + panoPanorama.panoramaHlookat + "&view.vlookat="
                  + panoPanorama.panoramaVlookat);
              mapHotspotClickEvent.append("playBefore(v_" + MapHotspot.panoramaId + ", null,"
                  + lookatEvent.toString() + " );");
            } else {
              mapHotspotClickEvent.append("playBefore(v_" + MapHotspot.panoramaId + ",null,null);");
            }

          }

          mapHotspotClickEvent.append("skin_getBoder('v_" + MapHotspot.panoramaId + "');");

          // 设置click事件
          mapHotspotElement.setAttribute("onclick",
              "stopsound(hotspotsnd);" + mapHotspotClickEvent.toString());
        }
      }
    }
  }

  /**
   * 添加背景音乐
   * 
   * @param _inForm
   * @param _xmldoc
   * @param _newRoot
   * @param panoPanorama
   * @throws Exception
   */
  public void createBgSound(Pano0110Form _inForm, Document _xmldoc, Element _newRoot,
      PanoPanorama panoPanorama, String firstSceneId) throws Exception {
    PanoExposition panoExposition =
        panoExpositionMapper.selectByPrimaryKey(_inForm.selectedExpositionId);
    // 该展览有音乐的情况
    StringBuffer onstartPlayMusicEvent = new StringBuffer();
    if (panoExposition.expositionSoundId != null
        && !ObjectUtils.isEmpty(panoExposition.expositionSoundId)) {
      if (!ObjectUtils.isEmpty(panoPanorama.panoramaSoundId)) {
        PanoMaterial panoramaSound =
            panoMaterial01Mapper.selectByPrimaryKey(panoPanorama.panoramaSoundId);
        if (!ObjectUtils.isEmpty(panoramaSound.materialPath)) {
          // 如果该场景有歌曲, 场景标识 设为true
          onstartPlayMusicEvent
              .append("playsound(scenebgsnd, " + doGetMaterialFolderPath(panoramaSound.expositionId,
                  _inForm, panoramaSound.materialPath) + ",0);");
          _newRoot.setAttribute("sceneSoundFlag", "true");
        } else {
          // 播放展览音乐 ,场景标识 设为true
          _newRoot.setAttribute("sceneSoundFlag", "false");
        }
      } else {
        // 该场景没有声音 ,播放展览音乐 ,场景标识 设为true
        _newRoot.setAttribute("sceneSoundFlag", "false");
      }
      _newRoot.setAttribute("onstart", onstartPlayMusicEvent.toString());
    } else {
      // 该展览没有音乐的情况，进一步检查是否有个别场景有歌曲，有则设定其onstart事件
      if (!ObjectUtils.isEmpty(panoPanorama.panoramaSoundId)) {
        PanoMaterial panoramaSound =
            panoMaterial01Mapper.selectByPrimaryKey(panoPanorama.panoramaSoundId);
        if (!ObjectUtils.isEmpty(panoramaSound.materialPath)) {
          // 如果该场景有歌曲, 场景标识 设为true
          onstartPlayMusicEvent
              .append("playsound(scenebgsnd, " + doGetMaterialFolderPath(panoramaSound.expositionId,
                  _inForm, panoramaSound.materialPath) + ",0);");
          _newRoot.setAttribute("sceneSoundFlag", "true");
        } else {
          // 播放展览音乐 ,场景标识 设为true
          _newRoot.setAttribute("sceneSoundFlag", "false");
        }
      } else {
        // 该场景没有声音 ,播放展览音乐 ,场景标识 设为true
        _newRoot.setAttribute("sceneSoundFlag", "false");
      }
      _newRoot.setAttribute("onstart", onstartPlayMusicEvent.toString());
    }
  }

  /**
   * 做成展览浮动信息层
   * 
   * @param _inForm
   * @param _xmldoc
   * @param _newRoot
   * @throws Exception
   */
  public void createExpositionFlowInfolayer(Pano0110Form _inForm, Document _xmldoc,
      Element _newRoot) throws Exception {
    PanoExposition panoExcposition =
        panoExpositionMapper.selectByPrimaryKey(_inForm.selectedExpositionId);
    if (!ObjectUtils.isEmpty(panoExcposition.flowInfoFileId)) {
      Element flowInfoLayerElement = _xmldoc.createElement("layer");
      _newRoot.appendChild(flowInfoLayerElement);

      // 检索浮动信息层相关数据
      PanoMaterial panoMaterial =
          panoMaterial01Mapper.selectByPrimaryKey(panoExcposition.flowInfoFileId);
      // 如果素材存在
      if (panoMaterial != null && !ObjectUtils.isEmpty(panoMaterial.materialTypeId)) {
        flowInfoLayerElement.setAttribute("name", "layer_flow_info");
        flowInfoLayerElement.setAttribute("keep", "true");
        flowInfoLayerElement.setAttribute("handcursor", "false");
        flowInfoLayerElement.setAttribute("border", "false");
        flowInfoLayerElement.setAttribute("ondown", "draglayer();");
        flowInfoLayerElement.setAttribute("align", "righttop");
        flowInfoLayerElement.setAttribute("x", panoExcposition.flowInfoFileX);
        flowInfoLayerElement.setAttribute("y", panoExcposition.flowInfoFileY);
        flowInfoLayerElement.setAttribute("scale", panoExcposition.flowInfoFileScale);
        // 图片类型的浮动层
        if (PanoConstantsIF.MATERIAL_TYPE_FLOW_INFO_IMAGE.equals(panoMaterial.materialTypeId)) {
          flowInfoLayerElement.setAttribute("url", doGetMaterialFolderPath(
              panoMaterial.expositionId, _inForm, panoMaterial.materialPath));
        }
        // 文字类型的浮动层
        if (PanoConstantsIF.MATERIAL_TYPE_FLOW_INFO_TEXT.equals(panoMaterial.materialTypeId)) {
          flowInfoLayerElement.setAttribute("url", "%SWFPATH%/plugins/textfield.swf");
          flowInfoLayerElement.setAttribute("html", panoMaterial.flowTextInfo);
          flowInfoLayerElement.setAttribute("css",
              "text-align:center; font-family:Arial; font-weight:bold; font-size:32px;");
        }
        flowInfoLayerElement.setAttribute("backgroundalpha", "0");
        flowInfoLayerElement.setAttribute("height", "auto");
        flowInfoLayerElement.setAttribute("width", "auto");
      }
    }
  }

  /**
   * 修改地图上热点的zorder，保证扇形不覆盖住任何热点造成地图上热点无法点击
   * 
   * @param strbuf
   * @param flag
   * @param _inForm
   * @param mapHotspot
   * @return
   * @throws Exception
   */
  public StringBuffer setMapHotspotZorder(StringBuffer strbuf, boolean newlineFlag,
      Pano0110Form pano0110Form, PanoExpositionMapHotspot mapHotspot) throws Exception {
    // 查看是否需要在拼接时换行
    if (newlineFlag) {
      // 做展览onstart拼接时，需要换行
      strbuf.append("\r\n        ");
    }
    strbuf.append("set(layer[V_" + mapHotspot.expositionMapHotspotId + "].zorder,2);");
    // 设置其他地图上热点的zorder
    HashMap<String, Object> expoIdCondition = Maps.newHashMap();
    expoIdCondition.put("expositionId", pano0110Form.selectedExpositionId);
    List<PanoExpositionMapHotspot01Model> mapHotspotList =
        panoExpositionMapHotspot01Mapper.selectByExpositionId(expoIdCondition);
    if (mapHotspotList != null && mapHotspotList.size() > 0) {
      for (PanoExpositionMapHotspot01Model otherMapHotspot : mapHotspotList) {
        if (!mapHotspot.expositionMapHotspotId.equals(otherMapHotspot.expositionMapHotspotId)) {
          if (newlineFlag) {
            // 做展览onstart拼接时，需要换行
            strbuf.append("\r\n        ");
          }
          strbuf.append("set(layer[V_" + otherMapHotspot.expositionMapHotspotId + "].zorder,3);");
        }
      }
    }
    return strbuf;
  }

  /**
   * 生成静态页面上素材的url时，转换素材url的文件夹路径，若原路径为“common_material”,则改为“com_mats”,反之为“expo_mats”
   * 
   * @param _oldFolderName
   * @return
   * @throws Exception
   */
  public String doGetMaterialFolderPath(String oldFolderName, Pano0110Form inForm,
      String materialPath) throws Exception {
    return doGetMaterialFolderPath(oldFolderName, inForm.selectedExpositionId, materialPath);
  }

  /**
   * 生成静态页面上素材的url时，转换素材url的文件夹路径，若原路径为“common_material”,则改为“com_mats”,反之为“expo_mats”
   * 
   * @param _oldFolderName
   * @return
   * @throws Exception
   */
  public String doGetMaterialFolderPath(String oldFolderName, String selectedExpositionId,
      String materialPath) throws Exception {
    if (!ObjectUtils.isEmpty(oldFolderName)) {
      if ("common_material".equals(oldFolderName)) {
        // 该素材为公共素材，改路径为com_mats
        // return "com_mats";
        return StringUtils.replace(materialPath, "material/common_material", "com_mats");
      }
      if (oldFolderName.equals(selectedExpositionId)) {
        // 该素材为展览素材，改路径为expo_mats
        // return "expo_mats";
        return StringUtils.replace(materialPath, "material/" + selectedExpositionId, "com_mats");
      }
    }
    return null;
  }
}
