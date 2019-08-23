package cn.com.pano.panovr.service.panovr02;

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
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.google.common.collect.Maps;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.utils.FileServiceUtil;
import cn.com.pano.pano.mapper.common.PanoExpositionMapper;
import cn.com.pano.pano.mapper.common01.PanoMaterial01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanorama01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanoramaHotspot01Mapper;
import cn.com.pano.pano.model.common.PanoExposition;
import cn.com.pano.pano.model.common.PanoMaterial;
import cn.com.pano.pano.model.common.PanoPanorama;
import cn.com.pano.pano.model.common.PanoPanoramaHotspot;
import cn.com.pano.panovr.dto.panovr02.PanoVr020901Dto;
import cn.com.pano.panovr.dto.panovr02.PanoVr020902Dto;
import cn.com.pano.panovr.form.panovr02.PanoVr0209Form;
import cn.com.platform.framework.common.CodeValueRecord;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.util.FwFileUtils;
import cn.com.platform.web.BaseService;

/**
 * 热点大小编辑初始化处理
 * 
 * @author tangzhenzong
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class PanoVr0209InitService extends BaseService {
  @Autowired
  public PanoPanorama01Mapper panoPanorama01Mapper;
  @Autowired
  public PanoMaterial01Mapper panoMaterial01Mapper;
  @Autowired
  public PanoExpositionMapper panoExpositionMapper;
  @Autowired
  public PanoPanoramaHotspot01Mapper panoPanoramaHotspot01Mapper;

  public void doInit(PanoVr0209Form _inForm) throws Exception {
    // 判断是做工具条按钮相关操作还是热点大小相关操作
    if ("buttons".equals(_inForm.commandTypeFromPanoVr0105)) {
      // 初始化工具条按钮列表
      PanoVr020901Dto vr0209dto = new PanoVr020901Dto();
      _inForm.buttonsInfo = new ArrayList<PanoVr020901Dto>();
      vr0209dto.buttonName = "btn_in";
      vr0209dto.buttonName_CN = "放大";
      _inForm.buttonsInfo.add(vr0209dto);
      vr0209dto = new PanoVr020901Dto();
      vr0209dto.buttonName = "btn_out";
      vr0209dto.buttonName_CN = "缩小";
      _inForm.buttonsInfo.add(vr0209dto);
      vr0209dto = new PanoVr020901Dto();
      vr0209dto.buttonName = "btn_left";
      vr0209dto.buttonName_CN = "视角左移";
      _inForm.buttonsInfo.add(vr0209dto);
      vr0209dto = new PanoVr020901Dto();
      vr0209dto.buttonName = "btn_right";
      vr0209dto.buttonName_CN = "视角右移";
      _inForm.buttonsInfo.add(vr0209dto);
      vr0209dto = new PanoVr020901Dto();
      vr0209dto.buttonName = "btn_up";
      vr0209dto.buttonName_CN = "视角上移";
      _inForm.buttonsInfo.add(vr0209dto);
      vr0209dto = new PanoVr020901Dto();
      vr0209dto.buttonName = "btn_down";
      vr0209dto.buttonName_CN = "视角下移";
      _inForm.buttonsInfo.add(vr0209dto);
      // 2016/12/23 krpano19版不支持反向操作，隐藏此按钮
      // vr0209dto = new PanoVr020901Dto();
      // vr0209dto.buttonName = "btn_ctrlmode";
      // vr0209dto.buttonName_CN = "反转操作";
      // _inForm.buttonsInfo.add(vr0209dto);
      vr0209dto = new PanoVr020901Dto();
      vr0209dto.buttonName = "btn_autorot";
      vr0209dto.buttonName_CN = "自动旋转";
      _inForm.buttonsInfo.add(vr0209dto);
      vr0209dto = new PanoVr020901Dto();
      vr0209dto.buttonName = "btn_share";
      vr0209dto.buttonName_CN = "分享";
      _inForm.buttonsInfo.add(vr0209dto);
      vr0209dto = new PanoVr020901Dto();
      vr0209dto.buttonName = "btn_fs";
      vr0209dto.buttonName_CN = "全屏";
      _inForm.buttonsInfo.add(vr0209dto);
    } else if (!("commonInfo".equals(_inForm.commandTypeFromPanoVr0105))) {
      // 初始化大小选择
      _inForm.hotspotSizeInfo = new ArrayList<>();
      _inForm.hotspotSizeInfo.add(new CodeValueRecord("0.1倍", "0.1"));
      _inForm.hotspotSizeInfo.add(new CodeValueRecord("0.2倍", "0.2"));
      _inForm.hotspotSizeInfo.add(new CodeValueRecord("0.3倍", "0.3"));
      _inForm.hotspotSizeInfo.add(new CodeValueRecord("0.4倍", "0.4"));
      _inForm.hotspotSizeInfo.add(new CodeValueRecord("0.5倍", "0.5"));
      _inForm.hotspotSizeInfo.add(new CodeValueRecord("0.6倍", "0.6"));
      _inForm.hotspotSizeInfo.add(new CodeValueRecord("0.7倍", "0.7"));
      _inForm.hotspotSizeInfo.add(new CodeValueRecord("0.8倍", "0.8"));
      _inForm.hotspotSizeInfo.add(new CodeValueRecord("0.9倍", "0.9"));
      _inForm.hotspotSizeInfo.add(new CodeValueRecord("1.0倍", "1"));
      _inForm.hotspotSizeInfo.add(new CodeValueRecord("1.1倍", "1.1"));
      _inForm.hotspotSizeInfo.add(new CodeValueRecord("1.2倍", "1.2"));
      _inForm.hotspotSizeInfo.add(new CodeValueRecord("1.3倍", "1.3"));
      _inForm.hotspotSizeInfo.add(new CodeValueRecord("1.4倍", "1.4"));
      _inForm.hotspotSizeInfo.add(new CodeValueRecord("1.5倍", "1.5"));
      _inForm.hotspotSizeInfo.add(new CodeValueRecord("1.6倍", "1.6"));
      _inForm.hotspotSizeInfo.add(new CodeValueRecord("1.7倍", "1.7"));
      _inForm.hotspotSizeInfo.add(new CodeValueRecord("1.8倍", "1.8"));
      _inForm.hotspotSizeInfo.add(new CodeValueRecord("1.9倍", "1.9"));
      _inForm.hotspotSizeInfo.add(new CodeValueRecord("2.0倍", "2"));
    }

    // 取得当前场景信息
    if (!ObjectUtils.isEmpty(_inForm.panoramaIdForPanoVr0209)) {
      // 检索展览下统一的推荐线路点提示信息
      PanoExposition panoExposition =
          panoExpositionMapper.selectByPrimaryKey(_inForm.expositionIdForPanoVr0209);
      if (panoExposition != null) {
        _inForm.expoRecommendInfo = "推荐路线";
        if (!ObjectUtils.isEmpty(panoExposition.expositionRecommendInfo)) {
          _inForm.expoRecommendInfo = panoExposition.expositionRecommendInfo;
        }
        if (!ObjectUtils.isEmpty(panoExposition.expoGoSceneTooltip)) {
          _inForm.ExpoGoSceneInfo = panoExposition.expoGoSceneTooltip;
        }
      }

      if (!ObjectUtils.isEmpty(_inForm.vr0209hotspotId)) {
        // 检索该热点是否是推荐线路点
        PanoPanoramaHotspot hotspot =
            panoPanoramaHotspot01Mapper.selectByPrimaryKey(_inForm.vr0209hotspotId);
        if (hotspot != null) {
          _inForm.recommendInfo = Objects.toString(hotspot.recommendInfo);
        }
      }
      PanoPanorama panoPanorama =
          panoPanorama01Mapper.selectByPrimaryKey(_inForm.panoramaIdForPanoVr0209);
      _inForm.panoramaName = panoPanorama.panoramaName;
      _inForm.panoramaPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
          _inForm.expositionIdForPanoVr0209,
          panoPanorama.panoramaPath + PanoConstantsIF.PANOS_SHOW_L_XML);
      // 全景图文件取得
      String _srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_PANORAMA,
          _inForm.expositionIdForPanoVr0209, _inForm.panoramaIdForPanoVr0209 + "/");
      String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
          _inForm.expositionIdForPanoVr0209, _inForm.expositionIdForPanoVr0209 + "/");
      FileServiceUtil.getPanoramaFileFromPublicStorage(_srcPath, _destPath);

      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      dbf.setIgnoringElementContentWhitespace(true);
      try {
        String xmlFilePath = FwFileUtils.getAbsolutePath(_inForm.panoramaPath);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document xmldoc = db.parse(xmlFilePath);
        Element root = xmldoc.getDocumentElement();
        // 载入编辑视角点
        if (!ObjectUtils.isEmpty(_inForm.positionAthForEdit)
            && !StringUtils.isEmpty(_inForm.positionAtvForEdit)) {
          root.setAttribute("onstart",
              "lookat(" + _inForm.positionAthForEdit + "," + _inForm.positionAtvForEdit + ");");
        }

        // 引入外部ＸＭＬ文件
        Element newElement = xmldoc.createElement("include");
        root.appendChild(newElement);
        newElement.setAttribute("url",
            "../../../../framework/panorama/template/recommend_path.xml");

        newElement = xmldoc.createElement("include");
        root.appendChild(newElement);
        newElement.setAttribute("url", "../../../../framework/panorama/template/anihotspots.xml");

        // 判断该popup是否由vr0105的自定义工具条操作打开，如果是，则导入按钮xml
        if ("buttons".equals(_inForm.commandTypeFromPanoVr0105)) {
          newElement = xmldoc.createElement("include");
          root.appendChild(newElement);
          newElement.setAttribute("url",
              "../../../../framework/panorama/template/buttons-png-include.xml");
        }

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer former = factory.newTransformer();
        former.setOutputProperty(OutputKeys.INDENT, "yes");
        former.transform(new DOMSource(xmldoc), new StreamResult(new File(xmlFilePath)));

      } catch (Exception e) {
        throw new SystemException(e);
      }

    }
    if (!ObjectUtils.isEmpty(_inForm.vr0209hotspotId)) {

      PanoPanoramaHotspot panoPanoramaHotspot =
          panoPanoramaHotspot01Mapper.selectByPrimaryKey(_inForm.vr0209hotspotId);
      PanoMaterial panoMaterial = null;

      if (panoPanoramaHotspot != null) {
        // 取得热点的类型
        _inForm.vr0209HotspotType = panoPanoramaHotspot.hotspotType;
        // 取得热点图片信息
        _inForm.hotspotAth = panoPanoramaHotspot.hotspotAth;
        _inForm.hotspotAtv = panoPanoramaHotspot.hotspotAtv;
        _inForm.hotspotScale = "1.0";
        if (!ObjectUtils.isEmpty(panoPanoramaHotspot.hotspotScale)) {
          _inForm.hotspotScale = panoPanoramaHotspot.hotspotScale;
        }

        panoMaterial = panoMaterial01Mapper.selectByPrimaryKey(panoPanoramaHotspot.hotspotImageId);

        // 判断是否是有推荐路径信息的导航热点
        if (PanoConstantsIF.HOTSPOT_TYPE_HOTSPOT_CHANGE_SCENE
            .equals(panoPanoramaHotspot.hotspotType)) {
          _inForm.expoHotspotTooltipInfo = panoPanoramaHotspot.hotspotTooltip;
          // 检索展览下所有的导航热点
          HashMap<String, Object> condition = Maps.newHashMap();
          List<PanoPanoramaHotspot> expoHotspotList =
              panoPanoramaHotspot01Mapper.selectHostSpotByExpositionId(condition);
          // 循环每一个导航热点
          for (PanoPanoramaHotspot hotspot : expoHotspotList) {
            // 如果该热点是展览中任意一个导航热点的下一个推荐路限点
            if (panoPanoramaHotspot.hotspotId.equals(hotspot.nextRecommendHotspotId)) {
              // 取得其路径的推荐信息
              _inForm.recommendInfo = "推荐路线";
              break;
            }
          }
        }
      } else {
        panoMaterial = panoMaterial01Mapper.selectByPrimaryKey(_inForm.vr0209hotspotId);
      }

      if (panoMaterial != null) {
        PanoVr020902Dto hotspotImageInfo = new PanoVr020902Dto();
        hotspotImageInfo.hasPngImage = "false";
        hotspotImageInfo.hotspotImagePath =
            MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_IMAGE,
                PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
                panoMaterial.materialPath);
        String _srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_IMAGE,
            PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
            panoMaterial.materialId + "/");

        String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_IMAGE,
            PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
            panoMaterial.materialId + "/");
        FileServiceUtil.copyDirFromPublicStorageToAppServer(_srcPath, _destPath);

        // 判断是否是gif图
        if (!ObjectUtils.isEmpty(panoMaterial.gifDelayTime)) {
          hotspotImageInfo.gifWidth = panoMaterial.gifWidth;
          hotspotImageInfo.gifHeight = panoMaterial.gifHeight;
          hotspotImageInfo.gifFrame = panoMaterial.gifFrameCount;
          hotspotImageInfo.gifDelayTime = panoMaterial.gifDelayTime;
          hotspotImageInfo.hasPngImage = "true";
        }

        _inForm.hotspotImageInfo = hotspotImageInfo;
        _inForm.hotspotImageInfoJson = objectMapper.writeValueAsString(hotspotImageInfo);
      }
      // if
      // (PanoConstantsIF.HOTSPOT_TYPE_HOTSPOT_CHANGE_SCENE.equals(panoPanoramaHotspot.hotspotType)
      // && PanoConstantsIF.RECOMMEND_FLAG_YES.equals(panoPanoramaHotspot.recommendFlag)) {
      // // 取得其路径的推荐信息
      // _inForm.recommendInfo = panoPanoramaHotspot.recommendInfo;
      // }
    }

    if (!ObjectUtils.isEmpty(_inForm.vr0203MusicHotspot)
        && "true".equals(_inForm.vr0203MusicHotspot)) {

      PanoMaterial panoMaterial = new PanoMaterial();
      if (!ObjectUtils.isEmpty(_inForm.firsthotspotImageIdPanoVr0203)) {

        panoMaterial = panoMaterial01Mapper.selectByPrimaryKey(_inForm.firsthotspotImageIdPanoVr0203);
        if (panoMaterial != null && !ObjectUtils.isEmpty(panoMaterial.materialId)) {
          PanoVr020902Dto firstImageInfo = new PanoVr020902Dto();

          firstImageInfo.hotspotImagePath =
              MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_IMAGE,
                  PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
                  panoMaterial.materialPath);

          String _srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_IMAGE,
              PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
              panoMaterial.materialId + "/");

          String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_IMAGE,
              PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
              panoMaterial.materialId + "/");
          FileServiceUtil.copyDirFromPublicStorageToAppServer(_srcPath, _destPath);

          // 判断该素材是否是gif图并是否有生产对应的png图
          if (!ObjectUtils.isEmpty(panoMaterial.gifDelayTime)) {
            firstImageInfo.hasPngImage = "true";
            firstImageInfo.gifWidth = panoMaterial.gifWidth;
            firstImageInfo.gifHeight = panoMaterial.gifHeight;
            firstImageInfo.gifFrame = panoMaterial.gifFrameCount;
            firstImageInfo.gifDelayTime = panoMaterial.gifDelayTime;
          }
          _inForm.firstImageInfo = firstImageInfo;
          _inForm.firstImageInfoJson = objectMapper.writeValueAsString(firstImageInfo);

        }
      }

      if (!ObjectUtils.isEmpty(_inForm.seconfhotspotImageIdPanoVr0203)) {
        panoMaterial =
            panoMaterial01Mapper.selectByPrimaryKey(_inForm.seconfhotspotImageIdPanoVr0203);
        if (panoMaterial != null && !ObjectUtils.isEmpty(panoMaterial.materialId)) {
          PanoVr020902Dto secondImageInfo = new PanoVr020902Dto();

          secondImageInfo.hotspotImagePath =
              MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_IMAGE,
                  PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
                  panoMaterial.materialPath);

          String _srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_IMAGE,
              PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
              panoMaterial.materialId + "/");

          String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_IMAGE,
              PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
              panoMaterial.materialId + "/");
          FileServiceUtil.copyDirFromPublicStorageToAppServer(_srcPath, _destPath);

          // 判断该素材是否是gif图并是否有生产对应的png图
          if (!ObjectUtils.isEmpty(panoMaterial.gifDelayTime)) {
            secondImageInfo.hasPngImage = "true";
            secondImageInfo.gifWidth = panoMaterial.gifWidth;
            secondImageInfo.gifHeight = panoMaterial.gifHeight;
            secondImageInfo.gifFrame = panoMaterial.gifFrameCount;
            secondImageInfo.gifDelayTime = panoMaterial.gifDelayTime;
          }
          _inForm.secondImageInfo = secondImageInfo;
          _inForm.secondImageInfoJson = objectMapper.writeValueAsString(secondImageInfo);

        }
      }

    }

  }

}
