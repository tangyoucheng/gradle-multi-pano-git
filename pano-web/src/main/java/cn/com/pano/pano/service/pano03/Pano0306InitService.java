package cn.com.pano.pano.service.pano03;

import java.io.File;
import java.text.MessageFormat;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.form.pano03.Pano0306Form;
import cn.com.pano.pano.mapper.common01.PanoExpositionMap01Mapper;
import cn.com.pano.pano.model.common.PanoExpositionMap;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.util.FwFileUtils;
import cn.com.platform.web.BaseService;

/**
 * 导航图更新初始化处理。
 * 
 * @author 唐友成
 * @date 2019-08-19
 *
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0306InitService extends BaseService {

  @Autowired
  public PanoExpositionMap01Mapper panoExpositionMap01Mapper;

  /**
   * 初期化。
   * 
   * @param inForm Pano0306Form
   */
  public void doInit(Pano0306Form inForm) throws Exception {

    // 获取地图名称和地图路径
    if (!ObjectUtils.isEmpty(inForm.pano0306ExpositionMapId)) {

      PanoExpositionMap panoExpositionMap =
          panoExpositionMap01Mapper.selectByPrimaryKey(inForm.pano0306ExpositionMapId);
      inForm.pano0306ExpositionMapName = panoExpositionMap.expositionMapName;
      inForm.pano0306ExpositionId = panoExpositionMap.expositionId;

      inForm.pano0306ExpositionMapPath =
          MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W,
              UserSessionUtils.getSessionId(), panoExpositionMap.expositionMapPath);

    }

    // 获取APP服务器侧文件目录。
    String destAppRelativePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_IMAGE,
        UserSessionUtils.getSessionId(), inForm.pano0306ExpositionId, "");
    File destAppRelativeFile = new File(FwFileUtils.getAbsolutePath(destAppRelativePath));
    // 全景的storage路径
    String srcPublicPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_IMAGE,
        inForm.pano0306ExpositionId, "");
    File srcPublicFile = new File(srcPublicPath).getAbsoluteFile();
    // 拷贝完整的全景到APP服务器
    if (srcPublicFile.exists() && srcPublicFile.isDirectory()) {
      destAppRelativeFile.mkdirs();
      FileUtils.copyDirectory(srcPublicFile, destAppRelativeFile, true);
    }

  }
}
