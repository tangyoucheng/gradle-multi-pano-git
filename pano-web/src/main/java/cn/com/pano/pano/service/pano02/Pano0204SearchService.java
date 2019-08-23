package cn.com.pano.pano.service.pano02;

import java.io.File;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Maps;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.form.pano02.Pano0204Form;
import cn.com.pano.pano.mapper.common01.PanoPanorama01Mapper;
import cn.com.pano.pano.model.common.PanoPanoramaQuery;
import cn.com.pano.pano.model.common01.PanoPanorama01Model;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.util.EasyJson;
import cn.com.platform.util.FwFileUtils;
import cn.com.platform.web.BaseService;

/**
 * 检索图信息。
 * 
 * @author ouyangzidou
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0204SearchService extends BaseService {

  @Autowired
  public PanoPanorama01Mapper panoPanorama01Mapper;

  /**
   * 检索场景详细信息。
   * 
   * @param inForm Pano0204Form
   * @return
   */
  public EasyJson<Object> doSearchPanorama(Pano0204Form inForm) throws Exception {

    // 获取APP服务器侧文件目录。
    String destAppRelativePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
        UserSessionUtils.getSessionId(), "pano0204/" + inForm.getExpositionId(), "");
    File destAppRelativeFile = new File(FwFileUtils.getAbsolutePath(destAppRelativePath));
    // 全景的storage路径
    String srcPublicPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_PANORAMA,
        inForm.expositionId, "");
    File srcPublicFile = new File(srcPublicPath).getAbsoluteFile();
    // 拷贝完整的全景到APP服务器
    if (srcPublicFile.exists() && srcPublicFile.isDirectory()) {
      destAppRelativeFile.mkdirs();
      FileUtils.copyDirectory(srcPublicFile, destAppRelativeFile, true);
    }

    HashMap<String, Object> conditions = Maps.newHashMap();
    conditions.put("expositionId", inForm.expositionId);
    conditions.put("panoramaId", inForm.currentPanoramaId);
    // 检索全部检索全景图
    List<PanoPanorama01Model> panoPanoramaList =
        panoPanorama01Mapper.selectHotspotPanorama(conditions);
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setRows(panoPanoramaList);
    return easyJson;
  }

}
