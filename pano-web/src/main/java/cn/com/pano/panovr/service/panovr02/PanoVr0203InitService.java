package cn.com.pano.panovr.service.panovr02;

import java.io.File;
import java.math.BigDecimal;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.google.common.collect.Maps;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.utils.FileServiceUtil;
import cn.com.pano.pano.mapper.common01.PanoHotspotUrl01Mapper;
import cn.com.pano.pano.mapper.common01.PanoMaterial01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanorama01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanoramaHotspot01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPolygonHotspot01Mapper;
import cn.com.pano.pano.model.common.PanoHotspotUrl;
import cn.com.pano.pano.model.common.PanoMaterial;
import cn.com.pano.pano.model.common.PanoPanorama;
import cn.com.pano.pano.model.common.PanoPanoramaHotspot;
import cn.com.pano.pano.model.common.PanoPolygonHotspot;
import cn.com.pano.panovr.dto.panovr02.PanoVr0203Dto;
import cn.com.pano.panovr.form.panovr02.PanoVr0203Form;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.util.FwFileUtils;
import cn.com.platform.web.BaseService;

/**
 * 编辑热点信息画面的初期化处理
 * 
 * @author ouyangzidou
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class PanoVr0203InitService extends BaseService {

  @Autowired
  public PanoMaterial01Mapper panoMaterial01Mapper;
  @Autowired
  public PanoPanorama01Mapper panoPanorama01Mapper;
  @Autowired
  public PanoPanoramaHotspot01Mapper panoPanoramaHotspot01Mapper;
  @Autowired
  public PanoPolygonHotspot01Mapper panoPolygonHotspot01Mapper;
  @Autowired
  public PanoHotspotUrl01Mapper panoHotspotUrl01Mapper;

  /**
   * 初期化处理
   * 
   * @param _inForm
   * @throws Exception
   */
  public void doInit(PanoVr0203Form _inForm) throws Exception {
    // 检索到当前全景图
    PanoPanorama panoPanorama = panoPanorama01Mapper.selectByPrimaryKey(_inForm.panoramaId);
    if (panoPanorama != null) {

      _inForm.panoramaPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
          _inForm.expositionId, panoPanorama.panoramaPath + PanoConstantsIF.PANOS_SHOW_L_XML);
      _inForm.panoramaName = panoPanorama.panoramaName;
      // 全景图文件取得
      String _srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_PANORAMA,
          _inForm.expositionId, _inForm.panoramaId + "/");
      String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
          _inForm.expositionId, _inForm.panoramaId + "/");
      _inForm.showCurrentMapExists =
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
        newElement.setAttribute("url", "../../../../framework/panorama/template/tooltip.xml");

        newElement = xmldoc.createElement("include");
        root.appendChild(newElement);
        newElement.setAttribute("url", "../../../../framework/panorama/template/anihotspots.xml");

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

      if (conditions.size() > 0) {
        List<PanoPanoramaHotspot> selectHotSpot =
            panoPanoramaHotspot01Mapper.selectHotSpot(conditions);
        if (selectHotSpot != null && !selectHotSpot.isEmpty()) {
          BeanUtils.copyProperties(selectHotSpot, _inForm.spotInfoList);
          for (PanoVr0203Dto vr0203Dto : _inForm.spotInfoList) {

            conditions.put("hotspotId", vr0203Dto.hotspotId);

            if (!ObjectUtils.isEmpty(vr0203Dto.hotspotImageId)) {
              eidtHotspotImagePath(_inForm, vr0203Dto);
            }
            // 如果是多边形热点
            if (PanoConstantsIF.HOTSPOT_TYPE_HOTSPOT_POLYGON.equals(vr0203Dto.hotspotType)) {
              List<PanoPolygonHotspot> selectPoint =
                  panoPolygonHotspot01Mapper.selectBypolygonId(conditions);
              if (selectPoint != null) {
                vr0203Dto.pointList = selectPoint;
              }
            }
            // 如果是音乐热点
            if (PanoConstantsIF.HOTSPOT_TYPE_HOTSPOT_MUSIC.equals(vr0203Dto.hotspotType)) {

              List<PanoHotspotUrl> urlInfoList =
                  panoHotspotUrl01Mapper.selectByHotspotId(conditions);
              if (urlInfoList != null && urlInfoList.size() > 0) {
                for (PanoHotspotUrl urlInfo : urlInfoList) {
                  if (BigDecimal.ZERO.equals(urlInfo.sortKey)) {
                    for (PanoHotspotUrl urlInfo_2 : urlInfoList) {
                      if (!BigDecimal.ZERO.equals(urlInfo_2.sortKey)
                          && !vr0203Dto.hotspotImageId.equals(urlInfo_2.hotspotUrlObjectId)) {
                        vr0203Dto.secondHotspotImageId = urlInfo_2.hotspotUrlObjectId;
                        vr0203Dto.secondSortkey = urlInfo_2.sortKey.toString();

                      } else if (!"0".equals(urlInfo_2.sortKey.toString())
                          && vr0203Dto.hotspotImageId.equals(urlInfo_2.hotspotUrlObjectId)) {
                        vr0203Dto.firstSortkey = urlInfo_2.sortKey.toString();
                        vr0203Dto.firstHotspotImageId = urlInfo_2.hotspotUrlObjectId;
                      }
                    }
                  }
                }
              }

            }
          }
          _inForm.spotInfoListJson = objectMapper.writeValueAsString(_inForm.spotInfoList);
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
  private void eidtHotspotImagePath(PanoVr0203Form _inForm, PanoVr0203Dto vr0203Dto)
      throws Exception {
    PanoMaterial panoMaterial = new PanoMaterial();

    panoMaterial = panoMaterial01Mapper.selectByPrimaryKey(vr0203Dto.hotspotImageId);
    if (panoMaterial != null && !ObjectUtils.isEmpty(panoMaterial.materialId)) {

      vr0203Dto.materialTypeId = panoMaterial.materialTypeId;
      vr0203Dto.hotspotImagePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_IMAGE,
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
      vr0203Dto.hasPngImage = "false";
      if (!ObjectUtils.isEmpty(panoMaterial.gifDelayTime)) {
        vr0203Dto.hasPngImage = "true";
        vr0203Dto.gifWidth = panoMaterial.gifWidth;
        vr0203Dto.gifHeight = panoMaterial.gifHeight;
        vr0203Dto.gifFrame = panoMaterial.gifFrameCount;
        vr0203Dto.gifDelayTime = panoMaterial.gifDelayTime;
      }

    }

  }

  /* 得到第二个素材的信息 */
  public String doGetSelectedMaterialInfo(PanoVr0203Form _inForm) throws Exception {
    PanoVr0203Dto vr0203Dto = new PanoVr0203Dto();
    PanoMaterial panoMaterial =
        panoMaterial01Mapper.selectByPrimaryKey(_inForm. pano0208seconfMaterialId);
    vr0203Dto.hasPngImage = "false";
    // vr0203Dto.isFlagMuiscHotspot = "true";
    vr0203Dto.materialPath = panoMaterial.materialPath;
    vr0203Dto.materialTypeId = panoMaterial.materialTypeId;
    // 判断是否是gif图
    if (!ObjectUtils.isEmpty(panoMaterial.gifDelayTime)) {
      vr0203Dto.gifWidth = panoMaterial.gifWidth;
      vr0203Dto.gifHeight = panoMaterial.gifHeight;
      vr0203Dto.gifFrame = panoMaterial.gifFrameCount;
      vr0203Dto.gifDelayTime = panoMaterial.gifDelayTime;
      vr0203Dto.hasPngImage = "true";

    }

    vr0203Dto.materialPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_IMAGE,
        PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId, vr0203Dto.materialPath);

    String _srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_IMAGE,
        PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
        panoMaterial.materialId + "/");

    String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_IMAGE,
        PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
        panoMaterial.materialId + "/");
    FileServiceUtil.copyDirFromPublicStorageToAppServer(_srcPath, _destPath);
    String searchResult = objectMapper.writeValueAsString(vr0203Dto);
    return searchResult;
  }
}
