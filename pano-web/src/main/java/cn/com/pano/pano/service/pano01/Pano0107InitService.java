package cn.com.pano.pano.service.pano01;

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
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.utils.FileServiceUtil;
import cn.com.pano.pano.form.pano01.Pano0107Form;
import cn.com.pano.pano.mapper.common01.PanoExpositionMap01Mapper;
import cn.com.pano.pano.mapper.common01.PanoExpositionMapHotspot01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanorama01Mapper;
import cn.com.pano.pano.model.common.PanoExpositionMap;
import cn.com.pano.pano.model.common.PanoExpositionMapHotspotQuery;
import cn.com.pano.pano.model.common.PanoExpositionMapQuery;
import cn.com.pano.pano.model.common.PanoPanorama;
import cn.com.pano.pano.model.common01.PanoExpositionMap01Model;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.framework.common.CodeValueRecord;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.util.FwFileUtils;
import cn.com.platform.web.BaseService;

/**
 * 编辑热点的初期化处理。
 * 
 * @author ouyangzidou
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0107InitService extends BaseService {

  @Autowired
  public PanoPanorama01Mapper panoPanorama01Mapper;
  @Autowired
  public PanoExpositionMap01Mapper panoExpositionMap01Mapper;
  @Autowired
  public PanoExpositionMapHotspot01Mapper panoExpositionMapHotspot01Mapper;

  /**
   * 初期化处理。
   * 
   * @param inForm Pano0107Form
   */
  public void doInit(Pano0107Form inForm) throws Exception {
    // 检索到当前全景图
    PanoPanorama panoPanorama = panoPanorama01Mapper.selectByPrimaryKey(inForm.panoramaId);
    if (panoPanorama != null) {
      // 加载热点样式下拉框
      inForm.hotspotStyleList = Lists.newArrayList();
      inForm.hotspotStyleList
          .add(new CodeValueRecord("static/pano/pano/common/template/mappoint_blue.png", "蓝色"));
      inForm.hotspotStyleList
          .add(new CodeValueRecord("static/pano/pano/common/template/mappoint_orange.png", "黄色"));
      inForm.hotspotStyleList
          .add(new CodeValueRecord("static/pano/pano/common/template/mappoint_pink.png", "粉色"));
      inForm.hotspotStyleList
          .add(new CodeValueRecord("static/pano/pano/common/template/mappoint_purple.png", "紫色"));


      // 获取APP服务器侧文件目录。
      String destAppRelativePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W,
          UserSessionUtils.getSessionId(), "pano0107/" + inForm.getExpositionId(), "");
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

      inForm.panoramaName = panoPanorama.panoramaName;

      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      dbf.setIgnoringElementContentWhitespace(true);
      try {
        inForm.panoramaPath =
            MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W, UserSessionUtils.getSessionId(),
                "pano0107/" + panoPanorama.panoramaPath + "show_l.xml");
        String xmlFilePath = FwFileUtils.getAbsolutePath(inForm.panoramaPath);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document xmldoc = db.parse(xmlFilePath);
        Element root = xmldoc.getDocumentElement();

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
            "../../../../../../../static/pano/pano/common/template/tooltip.xml");

        // krpano引擎图片加载完后，触发的事件
        newElement = xmldoc.createElement("events");
        root.appendChild(newElement);
        newElement.setAttribute("onloadcomplete", "js(doPano0107KrpanoOnloadcomplete(););");

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer former = factory.newTransformer();
        former.setOutputProperty(OutputKeys.INDENT, "yes");
        former.transform(new DOMSource(xmldoc), new StreamResult(new File(xmlFilePath)));

      } catch (Exception e) {
        throw new SystemException(e);
      }

      // 检索有无导航图
      PanoExpositionMapQuery expositionMapQuery = new PanoExpositionMapQuery();
      expositionMapQuery.createCriteria().andExpositionIdEqualTo(inForm.expositionId)
          .andExpositionMapUseStateEqualTo(FlagStatus.Enable.toString());
      List<PanoExpositionMap01Model> panoExpositionMapList =
          panoExpositionMap01Mapper.selectByBaseModel(expositionMapQuery);
      inForm.miniMapCheck = false;
      if (ObjectUtils.isNotEmpty(panoExpositionMapList)) {
        PanoExpositionMap01Model panoExpositionMap = panoExpositionMapList.get(0);

        inForm.expositionMapPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W,
            UserSessionUtils.getSessionId(), "pano0107/" + panoExpositionMap.expositionMapPath);

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
        inForm.miniMapSpotInfoListJson =
            objectMapper.writeValueAsString(inForm.miniMapSpotInfoList);
      }
    }

  }
}
