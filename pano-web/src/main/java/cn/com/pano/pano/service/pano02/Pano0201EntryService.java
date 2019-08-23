package cn.com.pano.pano.service.pano02;

import java.io.File;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.utils.FileServiceUtil;
import cn.com.pano.pano.form.pano02.Pano0201Form;
import cn.com.pano.pano.mapper.common01.PanoMaterial01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanorama01Mapper;
import cn.com.pano.pano.model.common.PanoMaterial;
import cn.com.pano.pano.model.common.PanoPanorama;
import cn.com.pano.pano.model.common.PanoPanoramaQuery;
import cn.com.pano.pano.model.common01.PanoPanorama01Model;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.util.EasyJson;
import cn.com.platform.util.FwFileUtils;
import cn.com.platform.web.BaseService;

/**
 * 首个全景图登陆处理。
 * 
 * @author 唐友成
 * @date 2019-08-07
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0201EntryService extends BaseService {

  @Autowired
  public PanoPanorama01Mapper panoPanorama01Mapper;
  @Autowired
  public PanoMaterial01Mapper panoMaterial01Mapper;

  /**
   * 新建全景图首图处理。
   * 
   * @param inForm Pano0201Form
   * @throws Exception 异常的场合
   */
  public EasyJson<Object> doInsertFirstPano(Pano0201Form inForm) throws Exception {
    // 获取APP服务器侧文件目录。
    String srcAppRelativePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
        UserSessionUtils.getSessionId(), "pano0201/" + inForm.getExpositionId(),
        inForm.getPanoramaId() + "/");
    // 全景的storage路径
    String destPublicPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_PANORAMA,
        inForm.expositionId, inForm.panoramaId + "/");
    // String srcAppPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
    // inForm.expositionId, inForm.panoramaId + "/");

    File srcAppDirectory = new File(FwFileUtils.getAbsolutePath(srcAppRelativePath));
    if (srcAppDirectory.exists() && srcAppDirectory.listFiles().length > 0) {
      // 拷贝全景到publicStorage服务器，并做成全景相关文件
      FileServiceUtil.savePanoramaFileToPublicStorage(
          srcAppRelativePath + PanoConstantsIF.VAL_PUBLIC_DIRECTORY_PANOS_L + "/", destPublicPath);
      // 再拷贝完整的全景到APP服务器
      FileUtils.copyDirectory(new File(destPublicPath), srcAppDirectory, true);
    }

    // 录入全景图首图基本信息
    PanoPanorama panoPanorama = new PanoPanorama();
    panoPanorama.panoramaPath =
        MessageFormat.format("{0}/panoramas/{1}/", inForm.expositionId, inForm.panoramaId);

    panoPanorama.startSceneFlag = FlagStatus.Disable.toString();
    // 如果用户选择了“首图”勾选框
    if (!ObjectUtils.isEmpty(inForm.startSceneFlag)) {
      PanoPanoramaQuery conditions = new PanoPanoramaQuery();
      conditions.createCriteria().andExpositionIdEqualTo(inForm.expositionId);
      conditions.createCriteria().andStartSceneFlagEqualTo(FlagStatus.Enable.toString());
      // 检索全景图首图
      List<PanoPanorama01Model> startPanoPanoramaList =
          panoPanorama01Mapper.selectByBaseModel(conditions);
      // 检索当前展览下无全景图首图
      if (ObjectUtils.isNotEmpty(startPanoPanoramaList)) {
        // 该展览下已有首图,修改其首图标识为NO
        startPanoPanoramaList.get(0).startSceneFlag = FlagStatus.Disable.toString();
        updateAudit(startPanoPanoramaList.get(0));;
        panoPanorama01Mapper.updateByPrimaryKey(startPanoPanoramaList.get(0));
      }
      panoPanorama.startSceneFlag = inForm.startSceneFlag;
    }

    // 为单个场景录入音乐
    if (!ObjectUtils.isEmpty(inForm.materialIdFromPano0208)
        && !ObjectUtils.isEmpty(inForm.panoMusicSelect)) {
      panoPanorama.panoramaSoundId = inForm.materialIdFromPano0208;
    }
    panoPanorama.panoramaId = inForm.panoramaId;

    PanoPanoramaQuery conditions = new PanoPanoramaQuery();
    conditions.createCriteria().andExpositionIdEqualTo(inForm.expositionId);
    // 检索全部检索全景图
    List<PanoPanorama01Model> panoPanoramaList = panoPanorama01Mapper.selectByBaseModel(conditions);
    panoPanorama.panoramaSortKey = new BigDecimal(panoPanoramaList.size() + 1);

    panoPanorama.expositionId = inForm.expositionId;
    panoPanorama.panoramaName = inForm.panoramaName;
    panoPanorama.thumbNote = inForm.thumbNote;
    panoPanorama.notes = inForm.notes;
    panoPanorama.thumbnailShowFlag = FlagStatus.Enable.toString();
    createAudit(panoPanorama);
    panoPanorama01Mapper.insert(panoPanorama);

    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setMsg("登录成功！");
    return easyJson;
  }

  /**
   * 取得最新的声音名称。
   * 
   * @param inForm Pano0201Form
   * @throws Exception 异常的场合
   */
  public String doRefreshSoundName(Pano0201Form inForm) throws Exception {
    if (!ObjectUtils.isEmpty(inForm.materialIdFromPano0208)) {
      PanoMaterial panoMaterial =
          panoMaterial01Mapper.selectByPrimaryKey(inForm.materialIdFromPano0208);
      if (panoMaterial != null && !ObjectUtils.isEmpty(panoMaterial.materialName)) {
        return panoMaterial.materialName;
      }
    }
    return "";
  }
}
