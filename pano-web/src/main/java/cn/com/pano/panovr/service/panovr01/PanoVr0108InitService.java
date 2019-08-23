package cn.com.pano.panovr.service.panovr01;

import java.io.File;
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
import cn.com.pano.pano.mapper.common01.PanoPanorama01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanoramaHotspot01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPolygonHotspot01Mapper;
import cn.com.pano.pano.model.common.PanoPanorama;
import cn.com.pano.pano.model.common.PanoPanoramaHotspot;
import cn.com.pano.pano.model.common.PanoPolygonHotspot;
import cn.com.pano.panovr.dto.panovr01.PanoVr0108Dto;
import cn.com.pano.panovr.form.panovr01.PanoVr0108Form;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.util.FwFileUtils;
import cn.com.platform.web.BaseService;

/**
 * 
 * @author tangzhenzong
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class PanoVr0108InitService extends BaseService {

  @Autowired
  public PanoPanorama01Mapper panoPanorama01Mapper;
  @Autowired
  public PanoPanoramaHotspot01Mapper panoPanoramaHotspot01Mapper;
  @Autowired
  public PanoPolygonHotspot01Mapper panoPolygonHotspot01Mapper;

  /**
   * 初期化处理
   * 
   * @param _inForm
   * @throws Exception
   */
  public void doInit(PanoVr0108Form _inForm) throws Exception {

    PanoPanorama panoPanorama = panoPanorama01Mapper.selectByPrimaryKey(_inForm.panoramaId);

    if (panoPanorama != null) {
      _inForm.panoramaId = panoPanorama.panoramaId;
      _inForm.panoramaName = panoPanorama.panoramaName;
      _inForm.panoramaPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
          _inForm.expositionId, panoPanorama.panoramaPath + PanoConstantsIF.PANOS_SHOW_L_XML);

      // 全景图文件取得
      String _srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_PANORAMA,
          _inForm.expositionId, _inForm.panoramaId + "/");
      String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
          _inForm.expositionId, _inForm.panoramaId + "/");
      _inForm.showCurrentMapExists =
          FileServiceUtil.getPanoramaFileFromPublicStorage(_srcPath, _destPath);

      String panoramaPath = _inForm.panoramaPath;

      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      dbf.setIgnoringElementContentWhitespace(true);
      try {
        String xmlFilePath = FwFileUtils.getAbsolutePath(panoramaPath);
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
        Element newElement = xmldoc.createElement("plugin");
        root.appendChild(newElement);
        newElement.setAttribute("url", "%SWFPATH%/plugins/customEditor.swf");
        newElement.setAttribute("name", "customEditor");

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer former = factory.newTransformer();
        former.setOutputProperty(OutputKeys.INDENT, "yes");
        former.transform(new DOMSource(xmldoc), new StreamResult(new File(xmlFilePath)));

      } catch (Exception e) {
        throw new SystemException(e);
      }
      if (_inForm.showCurrentMapExists) {
        // 读取数据库中的多边形热点的信息
        HashMap<String, Object> conditions = Maps.newHashMap();
        conditions.put("panoramaId", _inForm.panoramaId);
        if (conditions.size() > 0) {
          List<PanoPanoramaHotspot> selectHotSpot =
              panoPanoramaHotspot01Mapper.selectPolygonHotSpot(conditions);
          if (selectHotSpot != null && !selectHotSpot.isEmpty()) {

            BeanUtils.copyProperties(selectHotSpot, _inForm.spotInfoList);
            for (PanoVr0108Dto vr0108Dto : _inForm.spotInfoList) {

              if (!ObjectUtils.isEmpty(vr0108Dto.hotspotId)) {
                conditions.put("hotspotId", vr0108Dto.hotspotId);
                List<PanoPolygonHotspot> selectPoint =
                    panoPolygonHotspot01Mapper.selectBypolygonId(conditions);
                if (selectPoint != null) {
                  vr0108Dto.pointList = selectPoint;
                }
              }
            }
            _inForm.spotInfoListJson = objectMapper.writeValueAsString(_inForm.spotInfoList);

          }
        }
      }
    }

  }
}
