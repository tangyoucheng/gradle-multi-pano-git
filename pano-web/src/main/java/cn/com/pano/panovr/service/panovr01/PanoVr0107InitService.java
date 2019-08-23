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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.utils.FileServiceUtil;
import cn.com.pano.pano.mapper.common01.PanoExpositionMap01Mapper;
import cn.com.pano.pano.mapper.common01.PanoExpositionMapHotspot01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanorama01Mapper;
import cn.com.pano.pano.model.common.PanoExpositionMap;
import cn.com.pano.pano.model.common.PanoPanorama;
import cn.com.pano.panovr.form.panovr01.PanoVr0107Form;
import cn.com.platform.framework.common.CodeValueRecord;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.util.FwFileUtils;
import cn.com.platform.web.BaseService;

/**
 * 编辑热点的初期化处理
 * 
 * @author ouyangzidou
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class PanoVr0107InitService extends BaseService {

  @Autowired
  public PanoPanorama01Mapper panoPanorama01Mapper;
  @Autowired
  public PanoExpositionMap01Mapper panoExpositionMap01Mapper;
  @Autowired
  public PanoExpositionMapHotspot01Mapper panoExpositionMapHotspot01Mapper;

  /**
   * 初期化处理
   * 
   * @param _inForm
   * @throws Exception
   */
  public void doInit(PanoVr0107Form _inForm) throws Exception {
    // 检索到当前全景图
    PanoPanorama panoPanorama = panoPanorama01Mapper.selectByPrimaryKey(_inForm.panoramaId);
    if (panoPanorama != null) {
      // 加载热点样式下拉框
      _inForm.hotspotStyleList = Lists.newArrayList();
      _inForm.hotspotStyleList
          .add(new CodeValueRecord("蓝色", "framework/panorama/template/mappoint_blue.png"));
      _inForm.hotspotStyleList
          .add(new CodeValueRecord("黄色", "framework/panorama/template/mappoint_orange.png"));
      _inForm.hotspotStyleList
          .add(new CodeValueRecord("粉色", "framework/panorama/template/mappoint_pink.png"));
      _inForm.hotspotStyleList
          .add(new CodeValueRecord("紫色", "framework/panorama/template/mappoint_purple.png"));

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
        newElement.setAttribute("url", "../../../../framework/panorama/template/tooltip.xml");

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
          _inForm.miniMapSpotInfoListJson =
              objectMapper.writeValueAsString(_inForm.miniMapSpotInfoList);
        }
      }
    }

  }
}
