package cn.com.pano.pano.service.pano01;

import java.io.File;
import java.text.MessageFormat;
import java.util.Arrays;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.code.HotspotType;
import cn.com.pano.pano.dto.pano01.Pano0108Dto;
import cn.com.pano.pano.dto.pano02.Pano0203Dto;
import cn.com.pano.pano.form.pano01.Pano0108Form;
import cn.com.pano.pano.mapper.common01.PanoPanorama01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanoramaHotspot01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPolygonHotspot01Mapper;
import cn.com.pano.pano.model.common.PanoPanorama;
import cn.com.pano.pano.model.common.PanoPanoramaHotspotQuery;
import cn.com.pano.pano.model.common.PanoPolygonHotspotQuery;
import cn.com.pano.pano.model.common01.PanoPanoramaHotspot01Model;
import cn.com.pano.pano.model.common01.PanoPolygonHotspot01Model;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.util.FwFileUtils;
import cn.com.platform.web.BaseService;

/**
 * 多边形热点编辑页面初始化。
 * 
 * @author tangzhenzong
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0108InitService extends BaseService {

  @Autowired
  public PanoPanorama01Mapper panoPanorama01Mapper;
  @Autowired
  public PanoPanoramaHotspot01Mapper panoPanoramaHotspot01Mapper;
  @Autowired
  public PanoPolygonHotspot01Mapper panoPolygonHotspot01Mapper;

  /**
   * 初期化处理。
   * 
   * @param inForm Pano0108Form
   */
  public void doInit(Pano0108Form inForm) throws Exception {

    PanoPanorama panoPanorama = panoPanorama01Mapper.selectByPrimaryKey(inForm.panoramaId);

    if (panoPanorama != null) {
      inForm.panoramaId = panoPanorama.panoramaId;
      inForm.panoramaName = panoPanorama.panoramaName;
      inForm.panoramaPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
          inForm.expositionId, panoPanorama.panoramaPath + PanoConstantsIF.PANOS_SHOW_L_XML);

      // 全景图文件取得
      // 获取APP服务器侧文件目录。
      String destAppRelativePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
          UserSessionUtils.getSessionId(), "pano0108/" + inForm.getExpositionId(),
          inForm.panoramaId);
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

      String panoramaPath = destAppRelativePath + PanoConstantsIF.PANOS_SHOW_L_XML;

      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      dbf.setIgnoringElementContentWhitespace(true);
      try {
        String xmlFilePath = FwFileUtils.getAbsolutePath(panoramaPath);
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
        Element newElement = xmldoc.createElement("plugin");
        root.appendChild(newElement);
        newElement.setAttribute("url", "%SWFPATH%/plugins/customEditor.swf");
        newElement.setAttribute("name", "customEditor");

        // krpano引擎图片加载完后，触发的事件
        newElement = xmldoc.createElement("events");
        root.appendChild(newElement);
        newElement.setAttribute("onloadcomplete", "js(doPano0108KrpanoOnloadcomplete(););");


        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer former = factory.newTransformer();
        former.setOutputProperty(OutputKeys.INDENT, "yes");
        former.transform(new DOMSource(xmldoc), new StreamResult(new File(xmlFilePath)));

      } catch (Exception e) {
        throw new SystemException(e);
      }
      // 读取数据库中的多边形热点的信息
      PanoPanoramaHotspotQuery panoramaHotspotQuery = new PanoPanoramaHotspotQuery();
      panoramaHotspotQuery.createCriteria().andPanoramaIdEqualTo(inForm.panoramaId)
          .andHotspotTypeEqualTo(HotspotType.POLYGON.toString());

      List<PanoPanoramaHotspot01Model> selectHotSpot =
          panoPanoramaHotspot01Mapper.selectByBaseModel(panoramaHotspotQuery);
      if (selectHotSpot != null && !selectHotSpot.isEmpty()) {

        inForm.spotInfoList = Arrays.asList(objectMapper
            .readValue(objectMapper.writeValueAsString(selectHotSpot), Pano0108Dto[].class));

        for (Pano0108Dto pano0108Dto : inForm.spotInfoList) {

          if (!ObjectUtils.isEmpty(pano0108Dto.hotspotId)) {
            PanoPolygonHotspotQuery polygonHotspotQuery = new PanoPolygonHotspotQuery();
            polygonHotspotQuery.createCriteria().andPolygonIdEqualTo(pano0108Dto.hotspotId);
            List<PanoPolygonHotspot01Model> selectPoint =
                panoPolygonHotspot01Mapper.selectByBaseModel(polygonHotspotQuery);
            if (selectPoint != null) {
              pano0108Dto.pointList = selectPoint;
            }
          }
        }
        inForm.spotInfoListJson = objectMapper.writeValueAsString(inForm.spotInfoList);

      }
    }

  }
}
