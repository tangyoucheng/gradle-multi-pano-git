package cn.com.pano.pano.service.pano01;

import java.io.File;
import java.text.MessageFormat;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.google.common.collect.Lists;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.utils.PanoCommonUtil;
import cn.com.pano.pano.form.pano01.Pano0105Form;
import cn.com.pano.pano.mapper.common.PanoExpositionMapper;
import cn.com.pano.pano.mapper.common01.PanoExpositionMap01Mapper;
import cn.com.pano.pano.mapper.common01.PanoExpositionMapHotspot01Mapper;
import cn.com.pano.pano.mapper.common01.PanoMaterial01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanorama01Mapper;
import cn.com.pano.pano.model.common.PanoExposition;
import cn.com.pano.pano.model.common.PanoExpositionMapHotspotQuery;
import cn.com.pano.pano.model.common.PanoExpositionMapQuery;
import cn.com.pano.pano.model.common.PanoMaterial;
import cn.com.pano.pano.model.common.PanoPanorama;
import cn.com.pano.pano.model.common01.PanoExpositionMap01Model;
import cn.com.pano.pano.model.common01.PanoExpositionMapHotspot01Model;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.framework.common.CodeValueRecord;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.util.FwFileUtils;
import cn.com.platform.web.BaseService;


/**
 * 导航图操作画面的初期化处理。
 * 
 * @author ouyangzidou
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0105InitService extends BaseService {

  @Autowired
  public PanoPanorama01Mapper panoPanorama01Mapper;
  @Autowired
  public PanoExpositionMap01Mapper panoExpositionMap01Mapper;
  @Autowired
  public PanoExpositionMapHotspot01Mapper panoExpositionMapHotspot01Mapper;
  @Autowired
  public PanoExpositionMapper panoExpositionMapper;
  @Autowired
  public PanoMaterial01Mapper panoMaterial01Mapper;

  /**
   * 初期化处理。
   * 
   * @param inForm Pano0105Form
   */
  public void doInit(Pano0105Form inForm) throws Exception {
    // 设置指令选择
    inForm.commandTypeList = Lists.newArrayList();

    // 检索到当前全景图
    PanoPanorama panoPanorama = panoPanorama01Mapper.selectByPrimaryKey(inForm.panoramaId);
    if (panoPanorama != null) {
      inForm.commandTypeList
          .add(new CodeValueRecord(PanoConstantsIF.EXPOSITION_COOMMANDTYPE_MINMAP, "导航图操作"));
      inForm.commandTypeList
          .add(new CodeValueRecord(PanoConstantsIF.EXPOSITION_COOMMANDTYPE_FLOW_INFO, "浮动效果操作"));


      // 获取APP服务器侧文件目录。
      String destAppRelativePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W,
          UserSessionUtils.getSessionId(), "pano0105/" + inForm.getExpositionId(), "");
      File destAppRelativeFile = new File(FwFileUtils.getAbsolutePath(destAppRelativePath));
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

      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      dbf.setIgnoringElementContentWhitespace(true);
      try {
        inForm.panoramaPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
            UserSessionUtils.getSessionId(), "pano0105/" + inForm.getExpositionId(),
            inForm.panoramaId + "/show_l.xml");
        String xmlFilePath = FwFileUtils.getAbsolutePath(inForm.panoramaPath);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document xmldoc = db.parse(xmlFilePath);
        Element root = xmldoc.getDocumentElement();

        // 载入编辑视角点
        if (ObjectUtils.isNotEmpty(inForm.positionAthForEdit)
            && ObjectUtils.isNotEmpty(inForm.positionAtvForEdit)) {
          root.setAttribute("onstart",
              "lookat(" + inForm.positionAthForEdit + "," + inForm.positionAtvForEdit + ");");
        }

        // 引入外部ＸＭＬ文件
        Element newElement = xmldoc.createElement("include");
        root.appendChild(newElement);
        newElement.setAttribute("url",
            "../../../../../../../static/pano/pano/common/template/dragable-hotspots.xml");

        newElement = xmldoc.createElement("include");
        root.appendChild(newElement);
        newElement.setAttribute("url",
            "../../../../../../../static/pano/pano/common/template/dragable-layers.xml");

        newElement = xmldoc.createElement("include");
        root.appendChild(newElement);
        newElement.setAttribute("url",
            "../../../../../../../static/pano/pano/common/template/buttons-png-include.xml");

        newElement = xmldoc.createElement("include");
        root.appendChild(newElement);
        newElement.setAttribute("url",
            "../../../../../../../static/pano/pano/common/template/tooltip.xml");

        newElement = xmldoc.createElement("include");
        root.appendChild(newElement);
        newElement.setAttribute("url",
            "../../../../../../../static/pano/pano/common/template/radar.xml");

        // krpano引擎图片加载完后，触发的事件
        newElement = xmldoc.createElement("events");
        root.appendChild(newElement);
        newElement.setAttribute("onloadcomplete", "js(doPano0105KrpanoOnloadcomplete(););");

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer former = factory.newTransformer();
        former.setOutputProperty(OutputKeys.INDENT, "yes");
        former.transform(new DOMSource(xmldoc), new StreamResult(new File(xmlFilePath)));

      } catch (Exception e) {
        throw new SystemException(e);
      }

      // 检索有无导航图
      inForm.miniMapCheck = false;
      PanoExpositionMapQuery expositionMapQuery = new PanoExpositionMapQuery();
      expositionMapQuery.createCriteria().andExpositionIdEqualTo(inForm.expositionId)
          .andExpositionMapUseStateEqualTo(FlagStatus.Enable.toString());
      List<PanoExpositionMap01Model> panoExpositionMapList =
          panoExpositionMap01Mapper.selectByBaseModel(expositionMapQuery);
      if (ObjectUtils.isNotEmpty(panoExpositionMapList)) {
        PanoExpositionMap01Model panoExpositionMap = panoExpositionMapList.get(0);

        inForm.expositionMapPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W,
            UserSessionUtils.getSessionId(), "pano0105/" + panoExpositionMap.expositionMapPath);

        inForm.expositionMapId = panoExpositionMap.expositionMapId;

        inForm.miniMapCheck = true;
      }
      if (inForm.miniMapCheck) {
        // 读取数据库中的导航图上热点信息
        PanoExpositionMapHotspotQuery expositionMapHotspotQuery =
            new PanoExpositionMapHotspotQuery();
        expositionMapHotspotQuery.createCriteria()
            .andExpositionMapIdEqualTo(inForm.expositionMapId);

        inForm.miniMapSpotInfoList =
            panoExpositionMapHotspot01Mapper.selectByBaseModel(expositionMapHotspotQuery);
        for (PanoExpositionMapHotspot01Model expositionMapHotspot : inForm.miniMapSpotInfoList) {
          if (inForm.panoramaId.equals(expositionMapHotspot.panoramaId)) {
            inForm.selectedHotspotId = expositionMapHotspot.expositionMapHotspotId;
            // 检索地图热点的雷达角度
            if (!ObjectUtils.isEmpty(expositionMapHotspot.expositionMapHotspotHeading)) {
              inForm.radarHeading = expositionMapHotspot.expositionMapHotspotHeading;
            } else {
              // 没有雷达角度，默认雷达角度
              inForm.radarHeading = "0";
            }
          }
        }
        inForm.miniMapSpotInfoListJson =
            objectMapper.writeValueAsString(inForm.miniMapSpotInfoList);
      }

      // 检索有无浮动信息层
      PanoExposition panoExposition = panoExpositionMapper.selectByPrimaryKey(inForm.expositionId);
      if (!ObjectUtils.isEmpty(panoExposition.flowInfoFileId)) {
        // 取得浮动信息层信息
        PanoMaterial panoMaterial =
            panoMaterial01Mapper.selectByPrimaryKey(panoExposition.flowInfoFileId);
        if (panoMaterial != null) {
          // 如过是图片浮动信息层
          if (PanoConstantsIF.MATERIAL_TYPE_FLOW_INFO_IMAGE.equals(panoMaterial.materialTypeId)) {
            inForm.flowInfoFilePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W,
                UserSessionUtils.getSessionId(), panoMaterial.materialPath);
          }
          // 如果是文字浮动层
          if (PanoConstantsIF.MATERIAL_TYPE_FLOW_INFO_TEXT.equals(panoMaterial.materialTypeId)) {
            inForm.flowInfoFileInfo = panoMaterial.flowTextInfo;
          }
          inForm.flowInfoType = panoMaterial.materialTypeId;
          inForm.flowInfoFileX = panoExposition.flowInfoFileX;
          inForm.flowInfoFileY = panoExposition.flowInfoFileY;
          inForm.flowInfoFileId = panoExposition.flowInfoFileId;
          inForm.flowInfoFileScale = panoExposition.flowInfoFileScale;

        }
      }

      // 检索工具条选定的按钮
      inForm.selectedButtons = "";
      if (!ObjectUtils.isEmpty(panoExposition.expositionSelectedButtons)) {
        inForm.selectedButtons = panoExposition.expositionSelectedButtons;
      }
    }

    inForm.commandTypeList
        .add(new CodeValueRecord(PanoConstantsIF.EXPOSITION_COOMMANDTYPE_TOOL, "场景工具操作"));
  }
}
