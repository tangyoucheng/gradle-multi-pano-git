package cn.com.pano.panovr.service.panovr01;

import java.io.File;
import java.text.MessageFormat;
import java.util.HashMap;
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
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.utils.FileServiceUtil;
import cn.com.pano.pano.mapper.common.PanoExpositionMapper;
import cn.com.pano.pano.mapper.common01.PanoExpositionMap01Mapper;
import cn.com.pano.pano.mapper.common01.PanoExpositionMapHotspot01Mapper;
import cn.com.pano.pano.mapper.common01.PanoMaterial01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanorama01Mapper;
import cn.com.pano.pano.model.common.PanoExposition;
import cn.com.pano.pano.model.common.PanoExpositionMap;
import cn.com.pano.pano.model.common.PanoExpositionMapHotspot;
import cn.com.pano.pano.model.common.PanoMaterial;
import cn.com.pano.pano.model.common.PanoPanorama;
import cn.com.pano.panovr.form.panovr01.PanoVr0105Form;
import cn.com.platform.framework.common.CodeValueRecord;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.util.FwFileUtils;
import cn.com.platform.web.BaseService;

/**
 * 导航图操作画面的初期化处理
 * 
 * @author ouyangzidou
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class PanoVr0105InitService extends BaseService {

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
   * 初期化处理
   * 
   * @param _inForm
   * @throws Exception
   */
  public void doInit(PanoVr0105Form _inForm) throws Exception {
    // 设置指令选择
    _inForm.commandTypeList = Lists.newArrayList();

    // 检索到当前全景图
    PanoPanorama panoPanorama = panoPanorama01Mapper.selectByPrimaryKey(_inForm.panoramaId);
    if (panoPanorama != null) {
      _inForm.commandTypeList
          .add(new CodeValueRecord("导航图操作", PanoConstantsIF.EXPOSITION_COOMMANDTYPE_MINMAP));
      _inForm.commandTypeList
          .add(new CodeValueRecord("浮动效果操作", PanoConstantsIF.EXPOSITION_COOMMANDTYPE_FLOW_INFO));

      _inForm.panoramaPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
          _inForm.expositionId, panoPanorama.panoramaPath + PanoConstantsIF.PANOS_SHOW_L_XML);
      _inForm.panoramaName = panoPanorama.panoramaName;
      // 全景图文件取得
      String _srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_PANORAMA,
          _inForm.expositionId, _inForm.panoramaId + "/");
      String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
          _inForm.expositionId, _inForm.panoramaId + "/");
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
            "../../../../framework/panorama/template/dragable-hotspots.xml");

        newElement = xmldoc.createElement("include");
        root.appendChild(newElement);
        newElement.setAttribute("url",
            "../../../../framework/panorama/template/dragable-layers.xml");

        newElement = xmldoc.createElement("include");
        root.appendChild(newElement);
        newElement.setAttribute("url",
            "../../../../framework/panorama/template/buttons-png-include.xml");

        newElement = xmldoc.createElement("include");
        root.appendChild(newElement);
        newElement.setAttribute("url", "../../../../framework/panorama/template/tooltip.xml");

        newElement = xmldoc.createElement("include");
        root.appendChild(newElement);
        newElement.setAttribute("url", "../../../../framework/panorama/template/radar.xml");

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer former = factory.newTransformer();
        former.setOutputProperty(OutputKeys.INDENT, "yes");
        former.transform(new DOMSource(xmldoc), new StreamResult(new File(xmlFilePath)));

      } catch (Exception e) {
        throw new SystemException(e);
      }

      // 检索有无导航图
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
        _inForm.miniMapCheck = FileServiceUtil.copyDirFromPublicStorageToAppServer(srcPath, destPath);
      }
      if (_inForm.miniMapCheck) {
        // 读取数据库中的导航图上热点信息
        HashMap<String, Object> conditions = Maps.newHashMap();
        conditions.put("expositionMapId", _inForm.expositionMapId);
        if (conditions.size() > 0) {
          _inForm.miniMapSpotInfoList =
              panoExpositionMapHotspot01Mapper.selectMapHotspotInfo(conditions);
          if (_inForm.miniMapSpotInfoList != null) {
            for (PanoExpositionMapHotspot expositionMapHotspot : _inForm.miniMapSpotInfoList) {
              if (_inForm.panoramaId.equals(expositionMapHotspot.panoramaId)) {
                _inForm.selectedHotspotId = expositionMapHotspot.expositionMapHotspotId;
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

      // 检索有无浮动信息层
      PanoExposition panoExposition = panoExpositionMapper.selectByPrimaryKey(_inForm.expositionId);
      if (!ObjectUtils.isEmpty(panoExposition.flowInfoFileId)) {
        // 取得浮动信息层信息
        PanoMaterial panoMaterial =
            panoMaterial01Mapper.selectByPrimaryKey(panoExposition.flowInfoFileId);
        if (panoMaterial != null) {
          // 如过是图片浮动信息层
          if (PanoConstantsIF.MATERIAL_TYPE_FLOW_INFO_IMAGE.equals(panoMaterial.materialTypeId)) {
            _inForm.flowInfoFilePath =
                MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_IMAGE,
                    PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
                    panoMaterial.materialPath);

            String srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_IMAGE,
                PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
                panoMaterial.materialId + "/");

            String destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_IMAGE,
                PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
                panoMaterial.materialId + "/");
            FileServiceUtil.copyDirFromPublicStorageToAppServer(srcPath, destPath);
          }
          // 如果是文字浮动层
          if (PanoConstantsIF.MATERIAL_TYPE_FLOW_INFO_TEXT.equals(panoMaterial.materialTypeId)) {
            _inForm.flowInfoFileInfo = panoMaterial.flowTextInfo;
          }
          _inForm.flowInfoType = panoMaterial.materialTypeId;
          _inForm.flowInfoFileX = panoExposition.flowInfoFileX;
          _inForm.flowInfoFileY = panoExposition.flowInfoFileY;
          _inForm.flowInfoFileId = panoExposition.flowInfoFileId;
          _inForm.flowInfoFileScale = panoExposition.flowInfoFileScale;

        }
      }

      // 检索工具条选定的按钮
      _inForm.selectedButtons = "";
      if (!ObjectUtils.isEmpty(panoExposition.expositionSelectedButtons)) {
        _inForm.selectedButtons = panoExposition.expositionSelectedButtons;
      }
    }

    _inForm.commandTypeList
        .add(new CodeValueRecord("场景工具操作", PanoConstantsIF.EXPOSITION_COOMMANDTYPE_TOOL));
  }
}
