package cn.com.pano.pano.service.pano03;

import java.io.File;
import java.text.MessageFormat;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.form.pano03.Pano0304Form;
import cn.com.pano.pano.mapper.common01.PanoExpositionMap01Mapper;
import cn.com.pano.pano.model.common.PanoExpositionMap;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.util.EasyJson;
import cn.com.platform.util.FwFileUtils;
import cn.com.platform.web.BaseService;

/**
 * 登录新地图。
 * 
 * @author ouyangzidou
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0304EntryService extends BaseService {
  @Autowired
  public PanoExpositionMap01Mapper panoExpositionMap01Mapper;

  /**
   * 登录新地图。
   * 
   */
  public EasyJson<Object> doInsertExpositionMap(Pano0304Form inForm) throws Exception {
    // 获取APP服务器侧文件目录。
    String srcAppRelativePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_IMAGE,
        UserSessionUtils.getSessionId(), "pano0304/" + inForm.expositionId,
        inForm.expositionMapId + "/");
    // 全景的storage路径
    String destPublicPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_IMAGE,
        inForm.expositionId, inForm.expositionMapId + "/");
    File srcAppDirectory = new File(FwFileUtils.getAbsolutePath(srcAppRelativePath));
    if (srcAppDirectory.exists() && srcAppDirectory.listFiles().length > 0) {
      // 拷贝到storage服务器
      FileUtils.copyDirectory(srcAppDirectory, new File(destPublicPath), true);
    }

    // 插入数据
    PanoExpositionMap panoExpositionMap = new PanoExpositionMap();
    panoExpositionMap.expositionMapId = inForm.expositionMapId;
    panoExpositionMap.expositionId = inForm.expositionId;
    panoExpositionMap.expositionMapName = inForm.expositionMapName;
    panoExpositionMap.expositionMapPath = inForm.expositionMapPath;
    panoExpositionMap.expositionMapUseState = "0";
    panoExpositionMap.notes = inForm.notes;
    createAudit(panoExpositionMap);
    panoExpositionMap01Mapper.insert(panoExpositionMap);

    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setMsg("登录成功！");
    return easyJson;

  }
}
