package cn.com.pano.panovr.service.panovr03;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.commons.lang3.ObjectUtils;
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
import cn.com.pano.pano.common.utils.FileServiceUtil;
import cn.com.pano.pano.mapper.common01.PanoHotspotUrl01Mapper;
import cn.com.pano.pano.mapper.common01.PanoMaterial01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanorama01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanoramaHotspot01Mapper;
import cn.com.pano.pano.model.common.PanoHotspotUrl;
import cn.com.pano.pano.model.common.PanoMaterial;
import cn.com.pano.pano.model.common.PanoPanorama;
import cn.com.pano.pano.model.common.PanoPanoramaHotspot;
import cn.com.pano.panovr.dto.panovr01.PanoVr0104Dto;
import cn.com.pano.panovr.form.panovr03.PanoVr0310Form;
import cn.com.platform.util.FwFileUtils;
import cn.com.platform.web.BaseService;

/**
 * 音频热点预览画面初期显示
 * 
 * @author yangyuzhen
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class PanoVr0310InitService extends BaseService {

  @Autowired
  public PanoPanoramaHotspot01Mapper panoPanoramaHotspot01Mapper;
  @Autowired
  public PanoPanorama01Mapper panoPanorama01Mapper;
  @Autowired
  public PanoHotspotUrl01Mapper panoHotspotUrl01Mapper;
  @Autowired
  public PanoMaterial01Mapper panoMaterial01Mapper;

  /**
   * 生成JSP页面上的全景图与音频热点
   * 
   * @param _inForm
   * @throws Exception
   */
  public void doInit(PanoVr0310Form _inForm) throws Exception {

    if (!ObjectUtils.isEmpty(_inForm.vr0310panoramaId)) {
      PanoPanorama panorama = panoPanorama01Mapper.selectByPrimaryKey(_inForm.vr0310panoramaId);
      _inForm.vr0310panoramaPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
          panorama.expositionId, panorama.panoramaPath);
      _inForm.vr0310panoramaPath = _inForm.vr0310panoramaPath + "/popup_show.xml";
      // 全景图文件取得
      String _srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_PANORAMA,
          panorama.expositionId, _inForm.vr0310panoramaId + "/");
      String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
          panorama.expositionId, _inForm.vr0310panoramaId + "/");
      FileServiceUtil.getPanoramaFileFromPublicStorage(_srcPath, _destPath);

    }

    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    dbf.setIgnoringElementContentWhitespace(true);

    String xmlFilePath = FwFileUtils.getAbsolutePath(_inForm.vr0310panoramaPath);
    DocumentBuilder db = dbf.newDocumentBuilder();
    Document _xmldoc = db.parse(xmlFilePath);

    Element oldRoot = _xmldoc.getDocumentElement();
    // 清空XML
    _xmldoc.removeChild(oldRoot);

    // 根节点追加
    Element newRoot = _xmldoc.createElement("krpano");
    _xmldoc.appendChild(newRoot);
    // 引入外部ＸＭＬ文件
    Comment elementComment = _xmldoc.createComment("引入外部ＸＭＬ文件");
    newRoot.appendChild(elementComment);
    Element newElement = _xmldoc.createElement("include");
    newRoot.appendChild(newElement);
    newElement.setAttribute("url", "../../../../framework/panorama/template/sound.xml");

    newElement = _xmldoc.createElement("include");
    newRoot.appendChild(newElement);
    newElement.setAttribute("url", "../../../../framework/panorama/template/tooltip.xml");

    newElement = _xmldoc.createElement("include");
    newRoot.appendChild(newElement);
    newElement.setAttribute("url", "../../../../framework/panorama/template/anihotspots.xml");

    // 场景的preview节点追加
    Element previewElement = _xmldoc.createElement("preview");
    newRoot.appendChild(previewElement);
    previewElement.setAttribute("url", "preview.jpg");
    // 场景的view节点追加
    Element viewElement = _xmldoc.createElement("view");
    newRoot.appendChild(viewElement);
    viewElement.setAttribute("fovtype", "MFOV");
    viewElement.setAttribute("fov", "100");
    viewElement.setAttribute("fovmin", "50");
    viewElement.setAttribute("fovmax", "150");
    viewElement.setAttribute("hlookat", _inForm.positionAthForEdit);
    viewElement.setAttribute("vlookat", _inForm.positionAtvForEdit);
    // 场景的image节点追加
    Element imageElement = _xmldoc.createElement("image");
    newRoot.appendChild(imageElement);
    // 场景的image节点的cube节点追加
    Element imageCubeElement = _xmldoc.createElement("cube");
    imageElement.appendChild(imageCubeElement);
    imageCubeElement.setAttribute("url", "panos_l/sphere_%s.jpg");

    PanoVr0310Form vr0310FormInfo = MusicHostspotInfo(_inForm);
    String fisrtMaterialUrl = "../../../../" + vr0310FormInfo.firstImageInfo.hotspotImagePath;
    String secondMaterialUrl = "../../../../" + vr0310FormInfo.secondImageInfo.hotspotImagePath;
    String soundpath = "";
    String othersoundpath = "";

    PanoPanoramaHotspot hotspot =
        panoPanoramaHotspot01Mapper.selectByPrimaryKey(_inForm.vr0310selectedHotspotId);
    HashMap<String, Object> _conditions = Maps.newHashMap();
    _conditions.put("hotspotId", hotspot.hotspotId);
    List<PanoHotspotUrl> result = panoHotspotUrl01Mapper.selectByHotspotId(_conditions);
    if (result != null && result.size() > 0) {
      for (PanoHotspotUrl panoHotspotUrl : result) {
        if ("0".equals(panoHotspotUrl.sortKey.toString())) {
          if (!panoHotspotUrl.hotspotUrlObjectId.equals("musicMaterialId")) {
            PanoMaterial panoMaterial =
                panoMaterial01Mapper.selectByPrimaryKey(panoHotspotUrl.hotspotUrlObjectId);
            if (panoMaterial != null) {
              soundpath = panoMaterial.materialPath;
              soundpath = panoMaterial.expositionId + "/sounds/" + soundpath;

              String srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_SOUND,
                  PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
                  panoMaterial.materialId + "/");

              String destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_SOUND,
                  PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
                  panoMaterial.materialId + "/");
              FileServiceUtil.copyDirFromPublicStorageToAppServer(srcPath, destPath);
            }
          }
        } else {
          if (panoHotspotUrl.hotspotUrlObjectId.equals(
              panoPanoramaHotspot01Mapper.selectByPrimaryKey(hotspot.hotspotId).hotspotImageId)) {

            // 创建第一个图的热点
            Element hotspotElement = _xmldoc.createElement("hotspot");
            newRoot.appendChild(hotspotElement);
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
              newRoot.appendChild(gifStyleElement);
              gifStyleElement.setAttribute("name", "v_" + panoMaterial.materialId + "_gif");
              fisrtMaterialUrl =
                  fisrtMaterialUrl.substring(0, fisrtMaterialUrl.lastIndexOf(".")) + ".png";
              gifStyleElement.setAttribute("url", fisrtMaterialUrl);
              gifStyleElement.setAttribute("crop",
                  "0|0|" + panoMaterial.gifWidth + "|" + panoMaterial.gifHeight);
              gifStyleElement.setAttribute("framewidth", panoMaterial.gifWidth);
              gifStyleElement.setAttribute("frameheight", panoMaterial.gifHeight);
              gifStyleElement.setAttribute("frame", "0");
              gifStyleElement.setAttribute("lastframe", panoMaterial.gifFrameCount);
              gifStyleElement.setAttribute("onloaded",
                  "hotspot_animate(" + panoMaterial.gifDelayTime + ");");
              hotspotElement.setAttribute("style", "v_" + panoMaterial.materialId + "_gif|tooltip");

            } else {
              hotspotElement.setAttribute("style", "tooltip");
              hotspotElement.setAttribute("url", fisrtMaterialUrl);
            }

            // 检查该热点下有音乐
            if (PanoConstantsIF.HOTSPOT_URL_TYPE_SOUND.equals(hotspot.hotspotUrlType)) {

              hotspotElement.setAttribute("musicFlg", "1");
              hotspotElement.setAttribute("soundpath",
                  MessageFormat.format("file_w_app/material/{0}", soundpath));
              hotspotElement.setAttribute("othersoundpath", othersoundpath);
              hotspotElement.setAttribute("onclick",
                  "playHotspotMusic(get(musicFlg),get(hotspot_name),get(soundpath),get(othersoundpath))");
            }

          } else if (!panoHotspotUrl.hotspotUrlObjectId.equals(
              panoPanoramaHotspot01Mapper.selectByPrimaryKey(hotspot.hotspotId).hotspotImageId)) {

            // 做第二个图的热点
            Element hotspotElement = _xmldoc.createElement("hotspot");
            newRoot.appendChild(hotspotElement);
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
              newRoot.appendChild(gifStyleElement);
              gifStyleElement.setAttribute("name", "v_" + panoMaterial.materialId + "_gif");
              secondMaterialUrl =
                  secondMaterialUrl.substring(0, secondMaterialUrl.lastIndexOf(".")) + ".png";
              // 创建style
              gifStyleElement.setAttribute("url", secondMaterialUrl);
              gifStyleElement.setAttribute("crop",
                  "0|0|" + panoMaterial.gifWidth + "|" + panoMaterial.gifHeight);
              gifStyleElement.setAttribute("framewidth", panoMaterial.gifWidth);
              gifStyleElement.setAttribute("frameheight", panoMaterial.gifHeight);
              gifStyleElement.setAttribute("frame", "0");
              gifStyleElement.setAttribute("lastframe", panoMaterial.gifFrameCount);
              gifStyleElement.setAttribute("onloaded",
                  "hotspot_animate(" + panoMaterial.gifDelayTime + ");");
              hotspotElement.setAttribute("style", "v_" + panoMaterial.materialId + "_gif|tooltip");
            } else {
              hotspotElement.setAttribute("style", "tooltip");
              hotspotElement.setAttribute("url", secondMaterialUrl);
            }
            // 检查该热点下有音乐
            if (PanoConstantsIF.HOTSPOT_URL_TYPE_SOUND.equals(hotspot.hotspotUrlType)) {

              hotspotElement.setAttribute("musicFlg", "2");
              hotspotElement.setAttribute("soundpath",
                  MessageFormat.format("file_w_app/material/{0}", soundpath));
              hotspotElement.setAttribute("othersoundpath", othersoundpath);

              hotspotElement.setAttribute("onclick",
                  "playHotspotMusic(get(musicFlg),get(hotspot_name),get(soundpath),get(othersoundpath))");
            }
          }

        }
      }
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

  }

  /**
   * 音频热点的信息
   * 
   * @param _inForm
   * @return
   * @throws Exception
   */
  public PanoVr0310Form MusicHostspotInfo(PanoVr0310Form _inForm) throws Exception {
    PanoVr0104Dto vr0104Dto = new PanoVr0104Dto();
    PanoPanoramaHotspot hotspotInfo =
        panoPanoramaHotspot01Mapper.selectByPrimaryKey(_inForm.vr0310selectedHotspotId);
    vr0104Dto.hotspotScale = hotspotInfo.hotspotScale;
    vr0104Dto.hotspotAth = hotspotInfo.hotspotAth;
    vr0104Dto.hotspotAtv = hotspotInfo.hotspotAtv;
    vr0104Dto.hotspotImageId = hotspotInfo.hotspotImageId;
    //
    String fisrtMaterialId = "";
    String secondMaterialId = "";
    HashMap<String, Object> condition = Maps.newHashMap();
    condition.put("hotspotId", _inForm.vr0310selectedHotspotId);
    List<PanoHotspotUrl> hotspotUrl = panoHotspotUrl01Mapper.selectByHotspotId(condition);

    if (hotspotUrl != null && hotspotUrl.size() > 0) {
      for (PanoHotspotUrl urlInfo : hotspotUrl) {
        if (!"0".equals(urlInfo.sortKey.toString())) {
          if (urlInfo.hotspotUrlObjectId.equals(panoPanoramaHotspot01Mapper
              .selectByPrimaryKey(_inForm.vr0310selectedHotspotId).hotspotImageId)) {
            fisrtMaterialId = urlInfo.hotspotUrlObjectId;
          } else if (!urlInfo.hotspotUrlObjectId.equals(panoPanoramaHotspot01Mapper
              .selectByPrimaryKey(_inForm.vr0310selectedHotspotId).hotspotImageId)) {
            secondMaterialId = urlInfo.hotspotUrlObjectId;
          }

        }
      }
    }

    // 音频热点第一张图的信息
    if (!ObjectUtils.isEmpty(fisrtMaterialId)) {
      vr0104Dto.hasPngImage = "false";
      PanoMaterial material = panoMaterial01Mapper.selectByPrimaryKey(fisrtMaterialId);
      vr0104Dto.hotspotImagePath = material.materialPath;
      if (material != null && !ObjectUtils.isEmpty(material.gifDelayTime)) {
        vr0104Dto.gifWidth = material.gifWidth;
        vr0104Dto.gifHeight = material.gifHeight;
        vr0104Dto.gifFrame = material.gifFrameCount;
        vr0104Dto.gifDelayTime = material.gifDelayTime;
        vr0104Dto.hasPngImage = "true";

      }
      vr0104Dto.hotspotImagePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_IMAGE,
          PanoConstantsIF.MATERIAL_FOLDER_NAME + material.expositionId, vr0104Dto.hotspotImagePath);

      String srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_IMAGE,
          PanoConstantsIF.MATERIAL_FOLDER_NAME + material.expositionId, material.materialId + "/");

      String destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_IMAGE,
          PanoConstantsIF.MATERIAL_FOLDER_NAME + material.expositionId, material.materialId + "/");
      FileServiceUtil.copyDirFromPublicStorageToAppServer(srcPath, destPath);
      _inForm.firstImageInfo = vr0104Dto;
    }

    PanoVr0104Dto vr0104Dto_2 = new PanoVr0104Dto();
    vr0104Dto_2.hotspotScale = hotspotInfo.hotspotScale;
    vr0104Dto_2.hotspotAth = hotspotInfo.hotspotAth;
    vr0104Dto_2.hotspotAtv = hotspotInfo.hotspotAtv;
    vr0104Dto_2.hotspotImageId = hotspotInfo.hotspotImageId;
    // 音频热点第二张图的信息
    if (!ObjectUtils.isEmpty(secondMaterialId)) {
      vr0104Dto_2.hasPngImage = "false";
      PanoMaterial material = panoMaterial01Mapper.selectByPrimaryKey(secondMaterialId);
      vr0104Dto_2.hotspotImagePath = material.materialPath;
      if (material != null && !ObjectUtils.isEmpty(material.gifDelayTime)) {
        vr0104Dto_2.gifWidth = material.gifWidth;
        vr0104Dto_2.gifHeight = material.gifHeight;
        vr0104Dto_2.gifFrame = material.gifFrameCount;
        vr0104Dto_2.gifDelayTime = material.gifDelayTime;
        vr0104Dto_2.hasPngImage = "true";

      }
      vr0104Dto_2.hotspotImagePath =
          MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_IMAGE,
              PanoConstantsIF.MATERIAL_FOLDER_NAME + material.expositionId,
              vr0104Dto_2.hotspotImagePath);

      String srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_IMAGE,
          PanoConstantsIF.MATERIAL_FOLDER_NAME + material.expositionId, material.materialId + "/");

      String destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_IMAGE,
          PanoConstantsIF.MATERIAL_FOLDER_NAME + material.expositionId, material.materialId + "/");
      FileServiceUtil.copyDirFromPublicStorageToAppServer(srcPath, destPath);
      _inForm.secondImageInfo = vr0104Dto_2;
    }
    return _inForm;
  }

}
