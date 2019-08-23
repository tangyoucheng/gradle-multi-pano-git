package cn.com.pano.pano.service.pano02;

import java.io.File;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
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
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.google.common.collect.Maps;
import com.mchange.v2.beans.BeansUtils;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.utils.FileServiceUtil;
import cn.com.pano.pano.common.utils.PanoCommonUtil;
import cn.com.pano.pano.dto.pano02.Pano0203Dto;
import cn.com.pano.pano.form.pano02.Pano0203Form;
import cn.com.pano.pano.mapper.common01.PanoHotspotUrl01Mapper;
import cn.com.pano.pano.mapper.common01.PanoMaterial01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanorama01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanoramaHotspot01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPolygonHotspot01Mapper;
import cn.com.pano.pano.model.common.PanoHotspotUrl;
import cn.com.pano.pano.model.common.PanoHotspotUrlQuery;
import cn.com.pano.pano.model.common.PanoMaterial;
import cn.com.pano.pano.model.common.PanoPanorama;
import cn.com.pano.pano.model.common.PanoPanoramaHotspot;
import cn.com.pano.pano.model.common.PanoPanoramaHotspotQuery;
import cn.com.pano.pano.model.common.PanoPolygonHotspot;
import cn.com.pano.pano.model.common.PanoPolygonHotspotQuery;
import cn.com.pano.pano.model.common01.PanoHotspotUrl01Model;
import cn.com.pano.pano.model.common01.PanoPanoramaHotspot01Model;
import cn.com.pano.pano.model.common01.PanoPolygonHotspot01Model;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.framework.util.FwJsonUtil;
import cn.com.platform.util.FwFileUtils;
import cn.com.platform.web.BaseService;

/**
 * 编辑热点信息画面的初期化处理。
 * 
 * @author ouyangzidou
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0203InitService extends BaseService {

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
   * 初期化处理。
   * 
   * @param inForm Pano0203Form
   */
  public void doInit(Pano0203Form inForm) throws Exception {
    // 检索到当前全景图
    PanoPanorama panoPanorama = panoPanorama01Mapper.selectByPrimaryKey(inForm.panoramaId);
    if (panoPanorama != null) {

      // inForm.panoramaPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
      // inForm.expositionId, panoPanorama.panoramaPath + PanoConstantsIF.PANOS_SHOW_L_XML);
      // inForm.panoramaName = panoPanorama.panoramaName;
      // // 全景图文件取得
      // String _srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_PANORAMA,
      // inForm.expositionId, inForm.panoramaId + "/");
      // String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
      // inForm.expositionId, inForm.panoramaId + "/");
      // inForm.showCurrentMapExists =
      // FileServiceUtil.getPanoramaFileFromPublicStorage(_srcPath, _destPath);


      // 获取APP服务器侧文件目录。
      String destAppRelativePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
          UserSessionUtils.getSessionId(), "pano0203/" + inForm.getExpositionId(),
          inForm.getPanoramaId());
      File destAppRelativeFile = new File(FwFileUtils.getAbsolutePath(destAppRelativePath));
      // 全景的storage路径
      String srcPublicPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_PANORAMA,
          inForm.expositionId, inForm.getPanoramaId());
      File srcPublicFile = new File(srcPublicPath).getAbsoluteFile();
      // 拷贝完整的全景到APP服务器
      // if (srcPublicFile.exists() && srcPublicFile.isDirectory()) {
      // destAppRelativeFile.mkdirs();
      FileUtils.copyDirectory(srcPublicFile, destAppRelativeFile, true);
      // }

      // 拷贝素材文件到APP服务器
      // PanoCommonUtil.copyMaterialFromStorageToApp(inForm.expositionId);


      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      dbf.setIgnoringElementContentWhitespace(true);
      try {

        String xmlFilePath = FwFileUtils.getAbsolutePath(destAppRelativePath + "/show_l.xml");
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document xmldoc = db.parse(xmlFilePath);
        Element root = xmldoc.getDocumentElement();
        // 载入编辑视角点
        if (!ObjectUtils.isEmpty(inForm.positionAthForEdit)
            && !StringUtils.isEmpty(inForm.positionAtvForEdit)) {
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
            "../../../../../../../static/pano/pano/common/template/tooltip.xml");

        newElement = xmldoc.createElement("include");
        root.appendChild(newElement);
        newElement.setAttribute("url",
            "../../../../../../../static/pano/pano/common/template/anihotspots.xml");

        // krpano引擎图片加载完后，触发的事件
        newElement = xmldoc.createElement("events");
        root.appendChild(newElement);
        newElement.setAttribute("onloadcomplete", "js(doPano0203KrpanoOnloadcomplete(););");

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer former = factory.newTransformer();
        former.setOutputProperty(OutputKeys.INDENT, "yes");
        former.transform(new DOMSource(xmldoc), new StreamResult(new File(xmlFilePath)));

      } catch (Exception e) {
        throw new SystemException(e);
      }
    }

    // 读取数据库中的热点信息
    PanoPanoramaHotspotQuery conditions = new PanoPanoramaHotspotQuery();
    conditions.createCriteria().andPanoramaIdEqualTo(inForm.panoramaId);
    List<PanoPanoramaHotspot01Model> selectHotSpot =
        panoPanoramaHotspot01Mapper.selectByBaseModel(conditions);

    if (selectHotSpot != null && !selectHotSpot.isEmpty()) {
      inForm.hotspotInfoList = Arrays.asList(objectMapper
          .readValue(objectMapper.writeValueAsString(selectHotSpot), Pano0203Dto[].class));
      for (Pano0203Dto pano0203Dto : inForm.hotspotInfoList) {

        if (!ObjectUtils.isEmpty(pano0203Dto.hotspotImageId)) {
          eidtHotspotImagePath(inForm, pano0203Dto);
        }
        // 如果是多边形热点
        if (PanoConstantsIF.HOTSPOT_TYPE_HOTSPOT_POLYGON.equals(pano0203Dto.hotspotType)) {

          PanoPolygonHotspotQuery polygonHotspotQuery = new PanoPolygonHotspotQuery();
          polygonHotspotQuery.createCriteria().andPolygonIdEqualTo(pano0203Dto.hotspotId);
          List<PanoPolygonHotspot01Model> selectPoint =
              panoPolygonHotspot01Mapper.selectByBaseModel(polygonHotspotQuery);

          if (selectPoint != null) {
            pano0203Dto.pointList = selectPoint;
          }
        }
        // 如果是音乐热点
        if (PanoConstantsIF.HOTSPOT_TYPE_HOTSPOT_MUSIC.equals(pano0203Dto.hotspotType)) {

          PanoHotspotUrlQuery hotspotUrlQuery = new PanoHotspotUrlQuery();
          hotspotUrlQuery.createCriteria().andHotspotIdEqualTo(pano0203Dto.hotspotId);
          List<PanoHotspotUrl01Model> urlInfoList =
              panoHotspotUrl01Mapper.selectByBaseModel(hotspotUrlQuery);

          if (urlInfoList != null && urlInfoList.size() > 0) {
            for (PanoHotspotUrl01Model urlInfo : urlInfoList) {
              if ("0".equals(urlInfo.sortKey.toString())) {
                for (PanoHotspotUrl01Model urlInfo_2 : urlInfoList) {
                  if (!"0".equals(urlInfo_2.sortKey.toString())
                      && !pano0203Dto.hotspotImageId.equals(urlInfo_2.hotspotUrlObjectId)) {
                    pano0203Dto.secondHotspotImageId = urlInfo_2.hotspotUrlObjectId;
                    pano0203Dto.secondSortkey = urlInfo_2.sortKey.toString();

                  } else if (!"0".equals(urlInfo_2.sortKey.toString())
                      && pano0203Dto.hotspotImageId.equals(urlInfo_2.hotspotUrlObjectId)) {
                    pano0203Dto.firstSortkey = urlInfo_2.sortKey.toString();
                    pano0203Dto.firstHotspotImageId = urlInfo_2.hotspotUrlObjectId;
                  }
                }
              }
            }
          }

        }
      }
      inForm.hotspotInfoListJson = objectMapper.writeValueAsString(inForm.hotspotInfoList);
    }

  }

  /**
   * 热点图片路径编辑
   * 
   * @param inForm
   * @param pano0203Dto
   * @throws Exception
   */
  private void eidtHotspotImagePath(Pano0203Form inForm, Pano0203Dto pano0203Dto) throws Exception {
    PanoMaterial panoMaterial = panoMaterial01Mapper.selectByPrimaryKey(pano0203Dto.hotspotImageId);
    if (panoMaterial != null && !ObjectUtils.isEmpty(panoMaterial.materialId)) {

      pano0203Dto.materialTypeId = panoMaterial.materialTypeId;
      pano0203Dto.hotspotImagePath = panoMaterial.materialPath;

      // String _srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_IMAGE,
      // PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
      // panoMaterial.materialId + "/");
      //
      // String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_IMAGE,
      // PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
      // panoMaterial.materialId + "/");
      // FileServiceUtil.copyDirFromPublicStorageToAppServer(_srcPath, _destPath);

      // 判断该素材是否是gif图并是否有生产对应的png图
      pano0203Dto.hasPngImage = "false";
      if (!ObjectUtils.isEmpty(panoMaterial.gifDelayTime)) {
        pano0203Dto.hasPngImage = "true";
        pano0203Dto.gifWidth = panoMaterial.gifWidth;
        pano0203Dto.gifHeight = panoMaterial.gifHeight;
        pano0203Dto.gifFrameCount = panoMaterial.gifFrameCount;
        pano0203Dto.gifDelayTime = panoMaterial.gifDelayTime;
      }

    }

  }

  /* 得到第二个素材的信息 */
  public String doGetSelectedMaterialInfo(Pano0203Form inForm) throws Exception {
    Pano0203Dto pano0203Dto = new Pano0203Dto();
    PanoMaterial panoMaterial =
        panoMaterial01Mapper.selectByPrimaryKey(inForm.pano0208seconfMaterialId);
    pano0203Dto.hasPngImage = "false";
    // pano0203Dto.isFlagMuiscHotspot = "true";
    pano0203Dto.materialPath = panoMaterial.materialPath;
    pano0203Dto.materialTypeId = panoMaterial.materialTypeId;
    // 判断是否是gif图
    if (!ObjectUtils.isEmpty(panoMaterial.gifDelayTime)) {
      pano0203Dto.gifWidth = panoMaterial.gifWidth;
      pano0203Dto.gifHeight = panoMaterial.gifHeight;
      pano0203Dto.gifFrameCount = panoMaterial.gifFrameCount;
      pano0203Dto.gifDelayTime = panoMaterial.gifDelayTime;
      pano0203Dto.hasPngImage = "true";

    }

    pano0203Dto.materialPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_IMAGE,
        PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId, pano0203Dto.materialPath);

    String _srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_IMAGE,
        PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
        panoMaterial.materialId + "/");

    String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_IMAGE,
        PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
        panoMaterial.materialId + "/");
    FileServiceUtil.copyDirFromPublicStorageToAppServer(_srcPath, _destPath);
    String searchResult = objectMapper.writeValueAsString(pano0203Dto);
    return searchResult;
  }
}
