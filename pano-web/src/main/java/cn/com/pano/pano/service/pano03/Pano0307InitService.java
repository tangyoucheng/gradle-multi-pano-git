package cn.com.pano.pano.service.pano03;

import java.io.File;
import java.text.MessageFormat;
import java.util.HashMap;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Maps;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.form.pano03.Pano0307Form;
import cn.com.pano.pano.mapper.pano03.Pano0307Mapper;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.util.FwFileUtils;
import cn.com.platform.web.BaseService;

/**
 * 全景场景一览的初期显示处理。
 * 
 * @author ouyangzidou
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0307InitService extends BaseService {

  @Autowired
  public Pano0307Mapper pano0307Mapper;

  /**
   * 检索数据库中的全景场景，取得结果集。
   * 
   * @param inForm Pano0307Form
   */
  public void doInit(Pano0307Form inForm) throws Exception {

    // 获取APP服务器侧文件目录。
    String destAppRelativePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
        UserSessionUtils.getSessionId(), "pano0307/" + inForm.getExpositionId(), "");
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
