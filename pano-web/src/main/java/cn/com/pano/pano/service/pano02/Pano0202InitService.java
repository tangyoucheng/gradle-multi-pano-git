package cn.com.pano.pano.service.pano02;

import java.io.File;
import java.text.MessageFormat;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.form.pano02.Pano0202Form;
import cn.com.pano.pano.mapper.common.PanoExpositionMapper;
import cn.com.pano.pano.mapper.common01.PanoMaterial01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanorama01Mapper;
import cn.com.pano.pano.model.common.PanoMaterial;
import cn.com.pano.pano.model.common.PanoPanorama;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.util.FwFileUtils;
import cn.com.platform.web.BaseService;

/**
 * 场景编辑初期显示处理。
 * 
 * @author ouyangzidou
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0202InitService extends BaseService {
  @Autowired
  public PanoPanorama01Mapper panoPanorama01Mapper;
  @Autowired
  public PanoMaterial01Mapper panoMaterial01Mapper;
  @Autowired
  public PanoExpositionMapper panoExpositionMapper;

  /**
   * 场景编辑初期显示。
   * 
   * @param inForm Pano0202Form
   * @throws Exception 异常的场合
   */
  public void doInit(Pano0202Form inForm) throws Exception {

    // 获取APP服务器侧文件目录。
    String destAppRelativePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
        UserSessionUtils.getSessionId(), "pano0202/" + inForm.getExpositionId(),
        inForm.getPanoramaId());
    inForm.panoramaPath = destAppRelativePath;
    File destAppRelativeFile = new File(FwFileUtils.getAbsolutePath(inForm.panoramaPath));
    // 全景的storage路径
    String srcPublicPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_PANORAMA,
        inForm.expositionId, inForm.getPanoramaId());
    File srcPublicFile = new File(srcPublicPath).getAbsoluteFile();
    // 拷贝完整的全景到APP服务器
    if (srcPublicFile.exists() && srcPublicFile.isDirectory()) {
      destAppRelativeFile.mkdirs();
      FileUtils.copyDirectory(srcPublicFile, destAppRelativeFile, true);
    }

    PanoPanorama panoPanorama = panoPanorama01Mapper.selectByPrimaryKey(inForm.panoramaId);
    inForm.panoramaName = panoPanorama.panoramaName;
    inForm.notes = panoPanorama.notes;
    inForm.thumbNote = panoPanorama.thumbNote;
    inForm.isStartScene = panoPanorama.startSceneFlag;
    // 当前场景音乐信息
    if (!ObjectUtils.isEmpty(panoPanorama.panoramaSoundId)) {
      PanoMaterial panoMaterial =
          panoMaterial01Mapper.selectByPrimaryKey(panoPanorama.panoramaSoundId);
      if (panoMaterial != null && !ObjectUtils.isEmpty(panoMaterial.materialName)) {
        inForm.panoramaBackGroundSoundName = panoMaterial.materialName;
      }
    }

    // else {
    // // 显示展览默认声音
    // PanoExposition panoExposition =
    // panoExpositionMapper.selectByPrimaryKey(_inForm.expositionId);
    // if (panoExposition != null && !ObjectUtils.isEmpty(panoExposition.expositionSoundId)) {
    // PanoMaterial panoMaterial =
    // panoMaterial01Mapper.selectByPrimaryKey(panoExposition.expositionSoundId);
    // if (panoMaterial != null && !ObjectUtils.isEmpty(panoMaterial.materialName)) {
    // _inForm.panoramaBackGroundSoundName = panoMaterial.materialName;
    // }
    // }
    // }
  }
}
