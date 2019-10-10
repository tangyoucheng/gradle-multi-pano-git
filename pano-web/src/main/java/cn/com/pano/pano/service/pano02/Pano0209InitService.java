package cn.com.pano.pano.service.pano02;

import java.io.File;
import java.text.MessageFormat;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.google.common.collect.Maps;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.dto.pano02.Pano020901Dto;
import cn.com.pano.pano.dto.pano02.Pano020902Dto;
import cn.com.pano.pano.form.pano02.Pano0209Form;
import cn.com.pano.pano.mapper.common.PanoExpositionMapper;
import cn.com.pano.pano.mapper.common01.PanoMaterial01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanorama01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanoramaHotspot01Mapper;
import cn.com.pano.pano.model.common.PanoExposition;
import cn.com.pano.pano.model.common.PanoMaterial;
import cn.com.pano.pano.model.common.PanoPanorama;
import cn.com.pano.pano.model.common.PanoPanoramaHotspot;
import cn.com.pano.pano.model.common.PanoPanoramaHotspotQuery;
import cn.com.pano.pano.model.common01.PanoPanoramaHotspot01Model;
import cn.com.platform.framework.common.CodeValueRecord;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.util.FwFileUtils;
import cn.com.platform.web.BaseService;

/**
 * 热点大小编辑初始化处理。
 * 
 * @author tangzhenzong
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0209InitService extends BaseService {
  @Autowired
  public PanoPanorama01Mapper panoPanorama01Mapper;
  @Autowired
  public PanoMaterial01Mapper panoMaterial01Mapper;
  @Autowired
  public PanoExpositionMapper panoExpositionMapper;
  @Autowired
  public PanoPanoramaHotspot01Mapper panoPanoramaHotspot01Mapper;

  /**
   * 初期化处理。
   * 
   * @param inForm Pano0209Form
   */
  public void doInit(Pano0209Form inForm) throws Exception {
    // 判断是做工具条按钮相关操作还是热点大小相关操作
    if ("buttons".equals(inForm.commandTypeFromPano0105)) {
      // 初始化工具条按钮列表
      Pano020901Dto pano0209dto = new Pano020901Dto();
      inForm.buttonsInfo = new ArrayList<Pano020901Dto>();
      pano0209dto.buttonName = "btn_in";
      pano0209dto.buttonName_CN = "放大";
      inForm.buttonsInfo.add(pano0209dto);
      pano0209dto = new Pano020901Dto();
      pano0209dto.buttonName = "btn_out";
      pano0209dto.buttonName_CN = "缩小";
      inForm.buttonsInfo.add(pano0209dto);
      pano0209dto = new Pano020901Dto();
      pano0209dto.buttonName = "btn_left";
      pano0209dto.buttonName_CN = "视角左移";
      inForm.buttonsInfo.add(pano0209dto);
      pano0209dto = new Pano020901Dto();
      pano0209dto.buttonName = "btn_right";
      pano0209dto.buttonName_CN = "视角右移";
      inForm.buttonsInfo.add(pano0209dto);
      pano0209dto = new Pano020901Dto();
      pano0209dto.buttonName = "btn_up";
      pano0209dto.buttonName_CN = "视角上移";
      inForm.buttonsInfo.add(pano0209dto);
      pano0209dto = new Pano020901Dto();
      pano0209dto.buttonName = "btn_down";
      pano0209dto.buttonName_CN = "视角下移";
      inForm.buttonsInfo.add(pano0209dto);
      // 2016/12/23 krpano19版不支持反向操作，隐藏此按钮
      // pano0209dto = new Pano020901Dto();
      // pano0209dto.buttonName = "btn_ctrlmode";
      // pano0209dto.buttonName_CN = "反转操作";
      // inForm.buttonsInfo.add( pano0209dto);
      pano0209dto = new Pano020901Dto();
      pano0209dto.buttonName = "btn_autorot";
      pano0209dto.buttonName_CN = "自动旋转";
      inForm.buttonsInfo.add(pano0209dto);
      pano0209dto = new Pano020901Dto();
      pano0209dto.buttonName = "btn_share";
      pano0209dto.buttonName_CN = "分享";
      inForm.buttonsInfo.add(pano0209dto);
      pano0209dto = new Pano020901Dto();
      pano0209dto.buttonName = "btn_fs";
      pano0209dto.buttonName_CN = "全屏";
      inForm.buttonsInfo.add(pano0209dto);
      inForm.buttonsInfoJson = objectMapper.writeValueAsString(inForm.buttonsInfo);
    } else if (!("commonInfo".equals(inForm.commandTypeFromPano0105))) {
      // 初始化大小选择
      inForm.hotspotSizeInfo = new ArrayList<>();
      inForm.hotspotSizeInfo.add(new CodeValueRecord("0.1", "0.1倍"));
      inForm.hotspotSizeInfo.add(new CodeValueRecord("0.2", "0.2倍"));
      inForm.hotspotSizeInfo.add(new CodeValueRecord("0.3", "0.3倍"));
      inForm.hotspotSizeInfo.add(new CodeValueRecord("0.4", "0.4倍"));
      inForm.hotspotSizeInfo.add(new CodeValueRecord("0.5", "0.5倍"));
      inForm.hotspotSizeInfo.add(new CodeValueRecord("0.6", "0.6倍"));
      inForm.hotspotSizeInfo.add(new CodeValueRecord("0.7", "0.7倍"));
      inForm.hotspotSizeInfo.add(new CodeValueRecord("0.8", "0.8倍"));
      inForm.hotspotSizeInfo.add(new CodeValueRecord("0.9", "0.9倍"));
      inForm.hotspotSizeInfo.add(new CodeValueRecord("1.0", "1倍"));
      inForm.hotspotSizeInfo.add(new CodeValueRecord("1.1", "1.1倍"));
      inForm.hotspotSizeInfo.add(new CodeValueRecord("1.2", "1.2倍"));
      inForm.hotspotSizeInfo.add(new CodeValueRecord("1.3", "1.3倍"));
      inForm.hotspotSizeInfo.add(new CodeValueRecord("1.4", "1.4倍"));
      inForm.hotspotSizeInfo.add(new CodeValueRecord("1.5", "1.5倍"));
      inForm.hotspotSizeInfo.add(new CodeValueRecord("1.6", "1.6倍"));
      inForm.hotspotSizeInfo.add(new CodeValueRecord("1.7", "1.7倍"));
      inForm.hotspotSizeInfo.add(new CodeValueRecord("1.8", "1.8倍"));
      inForm.hotspotSizeInfo.add(new CodeValueRecord("1.9", "1.9倍"));
      inForm.hotspotSizeInfo.add(new CodeValueRecord("2.0", "2倍"));
      if (ObjectUtils.isEmpty(inForm.hotspotScale)) {
        inForm.hotspotScale = "1.0";
      }
    }

    // 取得当前场景信息
    if (!ObjectUtils.isEmpty(inForm.panoramaIdForPano0209)) {
      // 检索展览下统一的推荐线路点提示信息
      PanoExposition panoExposition =
          panoExpositionMapper.selectByPrimaryKey(inForm.expositionIdForPano0209);
      if (panoExposition != null) {
        inForm.expoRecommendInfo = "推荐路线";
        if (!ObjectUtils.isEmpty(panoExposition.expositionRecommendInfo)) {
          inForm.expoRecommendInfo = panoExposition.expositionRecommendInfo;
        }
        if (!ObjectUtils.isEmpty(panoExposition.expoGoSceneTooltip)) {
          inForm.expoGoSceneInfo = panoExposition.expoGoSceneTooltip;
        }
      }

      if (!ObjectUtils.isEmpty(inForm.pano0209hotspotId)) {
        // 检索该热点是否是推荐线路点
        PanoPanoramaHotspot hotspot =
            panoPanoramaHotspot01Mapper.selectByPrimaryKey(inForm.pano0209hotspotId);
        if (hotspot != null) {
          inForm.recommendInfo = Objects.toString(hotspot.recommendInfo);
        }
      }
      PanoPanorama panoPanorama =
          panoPanorama01Mapper.selectByPrimaryKey(inForm.panoramaIdForPano0209);
      inForm.panoramaName = panoPanorama.panoramaName;
      // inForm.panoramaPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
      // inForm.expositionIdForPano0209,
      // panoPanorama.panoramaPath + PanoConstantsIF.PANOS_SHOW_L_XML);



      // 获取APP服务器侧文件目录。
      String destAppRelativePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
          UserSessionUtils.getSessionId(), "pano0209/" + inForm.expositionIdForPano0209,
          inForm.panoramaIdForPano0209);
      File destAppRelativeFile = new File(FwFileUtils.getAbsolutePath(destAppRelativePath));
      // 全景的storage路径
      String srcPublicPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_PANORAMA,
          inForm.expositionIdForPano0209, inForm.panoramaIdForPano0209);
      File srcPublicFile = new File(srcPublicPath).getAbsoluteFile();
      // 拷贝完整的全景到APP服务器
      if (srcPublicFile.exists() && srcPublicFile.isDirectory()) {
        destAppRelativeFile.mkdirs();
        FileUtils.copyDirectory(srcPublicFile, destAppRelativeFile, true);
      }
      inForm.panoramaPath = destAppRelativePath + PanoConstantsIF.PANOS_SHOW_L_XML;

      // 全景图文件取得
      // String _srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_PANORAMA,
      // inForm.expositionIdForPano0209, inForm.panoramaIdForPano0209 + "/");
      // String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
      // inForm.expositionIdForPano0209, inForm.expositionIdForPano0209 + "/");
      // FileServiceUtil.getPanoramaFileFromPublicStorage(_srcPath, _destPath);

      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      dbf.setIgnoringElementContentWhitespace(true);
      try {
        String xmlFilePath = FwFileUtils.getAbsolutePath(inForm.panoramaPath);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document xmldoc = db.parse(xmlFilePath);
        Element root = xmldoc.getDocumentElement();
        // 载入编辑视角点
        if (!ObjectUtils.isEmpty(inForm.positionAthForEdit)
            && !StringUtils.isEmpty(inForm.positionAtvForEdit)) {
          root.setAttribute("onstart",
              "lookat(" + inForm.positionAthForEdit + "," + inForm.positionAtvForEdit + ");");
        }

        // 调试相关设定
        root.setAttribute("showerrors", "true");
        root.setAttribute("debugmode", "true");
        root.setAttribute("logkey", "true");

        // 引入外部ＸＭＬ文件
        Element newElement = xmldoc.createElement("include");
        root.appendChild(newElement);
        newElement.setAttribute("url",
            "../../../../../../../static/pano/pano/common/template/recommend_path.xml");

        newElement = xmldoc.createElement("include");
        root.appendChild(newElement);
        newElement.setAttribute("url",
            "../../../../../../../static/pano/pano/common/template/anihotspots.xml");

        // 判断该popup是否由 pano0105的自定义工具条操作打开，如果是，则导入按钮xml
        if ("buttons".equals(inForm.commandTypeFromPano0105)) {
          newElement = xmldoc.createElement("include");
          root.appendChild(newElement);
          newElement.setAttribute("url",
              "../../../../../../../static/pano/pano/common/template/buttons-png-include.xml");
        }

        // krpano引擎图片加载完后，触发的事件
        newElement = xmldoc.createElement("events");
        root.appendChild(newElement);
        newElement.setAttribute("onloadcomplete", "js(doPano0209KrpanoOnloadcomplete(););");

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer former = factory.newTransformer();
        former.setOutputProperty(OutputKeys.INDENT, "yes");
        former.transform(new DOMSource(xmldoc), new StreamResult(new File(xmlFilePath)));

      } catch (Exception e) {
        throw new SystemException(e);
      }

    }
    if (!ObjectUtils.isEmpty(inForm.pano0209hotspotId)) {

      PanoPanoramaHotspot panoPanoramaHotspot =
          panoPanoramaHotspot01Mapper.selectByPrimaryKey(inForm.pano0209hotspotId);
      PanoMaterial panoMaterial = null;

      if (panoPanoramaHotspot != null) {
        // 取得热点的类型
        inForm.pano0209HotspotType = panoPanoramaHotspot.hotspotType;
        // 取得热点图片信息
        inForm.hotspotAth = panoPanoramaHotspot.hotspotAth;
        inForm.hotspotAtv = panoPanoramaHotspot.hotspotAtv;
        if (!ObjectUtils.isEmpty(panoPanoramaHotspot.hotspotScale)) {
          inForm.hotspotScale = panoPanoramaHotspot.hotspotScale;
        }

        panoMaterial = panoMaterial01Mapper.selectByPrimaryKey(panoPanoramaHotspot.hotspotImageId);

        // 判断是否是有推荐路径信息的导航热点
        if (PanoConstantsIF.HOTSPOT_TYPE_HOTSPOT_CHANGE_SCENE
            .equals(panoPanoramaHotspot.hotspotType)) {
          inForm.expoHotspotTooltipInfo = panoPanoramaHotspot.hotspotTooltip;
          // 检索展览下所有的导航热点
          PanoPanoramaHotspotQuery panoramaHotspotQuery = new PanoPanoramaHotspotQuery();
          panoramaHotspotQuery.createCriteria()
              .andPanoramaIdEqualTo(panoPanoramaHotspot.panoramaId);
          List<PanoPanoramaHotspot01Model> expoHotspotList =
              panoPanoramaHotspot01Mapper.selectByBaseModel(panoramaHotspotQuery);
          // 循环每一个导航热点
          for (PanoPanoramaHotspot hotspot : expoHotspotList) {
            // 如果该热点是展览中任意一个导航热点的下一个推荐路限点
            if (panoPanoramaHotspot.hotspotId.equals(hotspot.nextRecommendHotspotId)) {
              // 取得其路径的推荐信息
              inForm.recommendInfo = "推荐路线";
              break;
            }
          }
        }
      } else {
        panoMaterial = panoMaterial01Mapper.selectByPrimaryKey(inForm.pano0209hotspotId);
      }

      if (panoMaterial != null) {
        Pano020902Dto hotspotImageInfo = new Pano020902Dto();
        hotspotImageInfo.hasPngImage = "false";
        hotspotImageInfo.hotspotImagePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W,
            UserSessionUtils.getSessionId(), panoMaterial.materialPath);
        // String _srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_IMAGE,
        // PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
        // panoMaterial.materialId + "/");
        //
        // String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_IMAGE,
        // PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
        // panoMaterial.materialId + "/");
        // FileServiceUtil.copyDirFromPublicStorageToAppServer(_srcPath, _destPath);

        // 判断是否是gif图
        if (!ObjectUtils.isEmpty(panoMaterial.gifDelayTime)) {
          hotspotImageInfo.gifWidth = panoMaterial.gifWidth;
          hotspotImageInfo.gifHeight = panoMaterial.gifHeight;
          hotspotImageInfo.gifFrame = panoMaterial.gifFrameCount;
          hotspotImageInfo.gifDelayTime = panoMaterial.gifDelayTime;
          hotspotImageInfo.hasPngImage = "true";
        }

        inForm.hotspotImageInfo = hotspotImageInfo;
        inForm.hotspotImageInfoJson = objectMapper.writeValueAsString(hotspotImageInfo);
      }
      // if
      // (PanoConstantsIF.HOTSPOT_TYPE_HOTSPOT_CHANGE_SCENE.equals(panoPanoramaHotspot.hotspotType)
      // && PanoConstantsIF.RECOMMEND_FLAG_YES.equals(panoPanoramaHotspot.recommendFlag)) {
      // // 取得其路径的推荐信息
      // inForm.recommendInfo = panoPanoramaHotspot.recommendInfo;
      // }
    }

    if (!ObjectUtils.isEmpty(inForm.pano0203MusicHotspot)
        && "true".equals(inForm.pano0203MusicHotspot)) {

      PanoMaterial panoMaterial = new PanoMaterial();
      if (!ObjectUtils.isEmpty(inForm.firsthotspotImageIdPano0203)) {

        panoMaterial = panoMaterial01Mapper.selectByPrimaryKey(inForm.firsthotspotImageIdPano0203);
        if (panoMaterial != null && !ObjectUtils.isEmpty(panoMaterial.materialId)) {
          Pano020902Dto firstImageInfo = new Pano020902Dto();

          firstImageInfo.hotspotImagePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W,
              UserSessionUtils.getSessionId(), panoMaterial.materialPath);

          // String _srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_IMAGE,
          // PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
          // panoMaterial.materialId + "/");
          //
          // String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_IMAGE,
          // PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
          // panoMaterial.materialId + "/");
          // FileServiceUtil.copyDirFromPublicStorageToAppServer(_srcPath, _destPath);

          // 判断该素材是否是gif图并是否有生产对应的png图
          if (!ObjectUtils.isEmpty(panoMaterial.gifDelayTime)) {
            firstImageInfo.hasPngImage = "true";
            firstImageInfo.gifWidth = panoMaterial.gifWidth;
            firstImageInfo.gifHeight = panoMaterial.gifHeight;
            firstImageInfo.gifFrame = panoMaterial.gifFrameCount;
            firstImageInfo.gifDelayTime = panoMaterial.gifDelayTime;
          }
          inForm.firstImageInfo = firstImageInfo;
          inForm.firstImageInfoJson = objectMapper.writeValueAsString(firstImageInfo);

        }
      }

      if (!ObjectUtils.isEmpty(inForm.seconfhotspotImageIdPano0203)) {
        panoMaterial = panoMaterial01Mapper.selectByPrimaryKey(inForm.seconfhotspotImageIdPano0203);
        if (panoMaterial != null && !ObjectUtils.isEmpty(panoMaterial.materialId)) {
          Pano020902Dto secondImageInfo = new Pano020902Dto();

          secondImageInfo.hotspotImagePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W,
              UserSessionUtils.getSessionId(), panoMaterial.materialPath);

          // String _srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_IMAGE,
          // PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
          // panoMaterial.materialId + "/");
          //
          // String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_IMAGE,
          // PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
          // panoMaterial.materialId + "/");
          // FileServiceUtil.copyDirFromPublicStorageToAppServer(_srcPath, _destPath);

          // 判断该素材是否是gif图并是否有生产对应的png图
          if (!ObjectUtils.isEmpty(panoMaterial.gifDelayTime)) {
            secondImageInfo.hasPngImage = "true";
            secondImageInfo.gifWidth = panoMaterial.gifWidth;
            secondImageInfo.gifHeight = panoMaterial.gifHeight;
            secondImageInfo.gifFrame = panoMaterial.gifFrameCount;
            secondImageInfo.gifDelayTime = panoMaterial.gifDelayTime;
          }
          inForm.secondImageInfo = secondImageInfo;
          inForm.secondImageInfoJson = objectMapper.writeValueAsString(secondImageInfo);

        }
      }

    }

  }

}
