package cn.com.pano.pano.service.pano02;

import java.io.File;
import java.text.MessageFormat;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.form.pano02.Pano0204Form;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.util.FwFileUtils;
import cn.com.platform.web.BaseService;

/**
 * 为导航热点添加全景图的初期显示。
 * 
 * @author ouyangzidou
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0204InitService extends BaseService {

  /**
   * 为导航热点添加全景图的初期显示。
   * 
   * @param inForm Pano0204Form
   */
  public void doInit(Pano0204Form inForm) throws Exception {

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
  }

}
