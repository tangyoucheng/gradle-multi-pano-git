package cn.com.pano.pano.service.pano03;

import java.io.File;
import java.text.MessageFormat;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.form.pano03.Pano0308Form;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.util.FwFileUtils;
import cn.com.platform.web.BaseService;

/**
 * 展览目录编辑初始化处理。
 * 
 * @author shiwei
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0308InitService extends BaseService {

  /**
   * 初期化处理。
   * 
   * @param inForm Pano0308Form
   */
  public void doInit(Pano0308Form inForm) throws Exception {

    // 获取APP服务器侧文件目录。
    String destAppRelativePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
        UserSessionUtils.getSessionId(), "pano0308/" + inForm.getExpositionId(), "");
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
  }
}
