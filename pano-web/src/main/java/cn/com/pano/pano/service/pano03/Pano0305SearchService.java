package cn.com.pano.pano.service.pano03;

import java.io.File;
import java.text.MessageFormat;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.form.pano03.Pano0305Form;
import cn.com.pano.pano.mapper.common01.PanoExpositionMap01Mapper;
import cn.com.pano.pano.model.common.PanoExpositionMapQuery;
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
public class Pano0305SearchService extends BaseService {

  @Autowired
  public PanoExpositionMap01Mapper panoExpositionMap01Mapper;

  /**
   * 检索设备详细信息。
   * 
   * @param inForm Pano0305Form
   */
  public EasyJson<Object> doSearchExpositionMap(Pano0305Form inForm) throws Exception {

    // 获取APP服务器侧文件目录。
    String destAppRelativePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_IMAGE,
        UserSessionUtils.getSessionId(), inForm.expositionId, "");
    File destAppRelativeFile = new File(FwFileUtils.getAbsolutePath(destAppRelativePath));
    // 全景的storage路径
    String srcPublicPath =
        MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_IMAGE, inForm.expositionId, "");
    File srcPublicFile = new File(srcPublicPath).getAbsoluteFile();
    // 拷贝完整的全景到APP服务器
    if (srcPublicFile.exists() && srcPublicFile.isDirectory()) {
      destAppRelativeFile.mkdirs();
      FileUtils.copyDirectory(srcPublicFile, destAppRelativeFile, true);
    }

    PanoExpositionMapQuery expositionMapQuery = new PanoExpositionMapQuery();
    expositionMapQuery.createCriteria().andExpositionIdEqualTo(inForm.expositionId);
    inForm.expositionMapInfo = panoExpositionMap01Mapper.selectByBaseModel(expositionMapQuery);

    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setRows(inForm.expositionMapInfo);
    return easyJson;
  }

}
