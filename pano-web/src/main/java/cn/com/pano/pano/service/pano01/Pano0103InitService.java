package cn.com.pano.pano.service.pano01;

import java.text.MessageFormat;
import java.time.format.DateTimeFormatter;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.code.ExpositionStatus;
import cn.com.pano.pano.common.code.ExpositionType;
import cn.com.pano.pano.common.utils.FileServiceUtil;
import cn.com.pano.pano.common.utils.PanoCommonUtil;
import cn.com.pano.pano.form.pano01.Pano0103Form;
import cn.com.pano.pano.mapper.common.PanoExpositionMapper;
import cn.com.pano.pano.mapper.common01.PanoMaterial01Mapper;
import cn.com.pano.pano.model.common.PanoExposition;
import cn.com.pano.pano.model.common.PanoMaterial;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.web.BaseService;

/**
 * 展览编辑画面初期显示。
 * 
 * @author ouyangzidou
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0103InitService extends BaseService {

  @Autowired
  public PanoExpositionMapper panoExpositionMapper;
  @Autowired
  public PanoMaterial01Mapper panoMaterial01Mapper;

  /**
   * 展览编辑画面初期显示处理。
   * 
   */
  public void doInit(Pano0103Form inForm) throws Exception {

    // 展览类型radioBox
    inForm.expositionTypeList = PanoCommonUtil.getCodeMessageList(ExpositionType.class, false);
    inForm.expositionType = ExpositionType.OUTPUT.toString();

    // 展览状态radioBox
    inForm.expositionStatusList = PanoCommonUtil.getCodeMessageList(ExpositionStatus.class, false);;

    // 展览模式radioBox
    inForm.vrFlagList = PanoCommonUtil.getVrTypeList(false);
    inForm.vrFlag = FlagStatus.Disable.toString();

    // 展览状态初期化默认为：规划中
    inForm.expositionStatus = ExpositionStatus.PLANNING.toString();

    if (!ObjectUtils.isEmpty(inForm.expositionId)) {
      // 根据展览Id检索该展览的信息
      PanoExposition panoExposition = panoExpositionMapper.selectByPrimaryKey(inForm.expositionId);
      if (panoExposition != null) {
        inForm.expositionFolderId = panoExposition.getExpositionFolderId();
        inForm.expositionName = panoExposition.getExpositionName();
        inForm.expositionNotes = panoExposition.getNotes();
        inForm.expositionType = panoExposition.getExpositionType();
        inForm.expositionStatus = panoExposition.getStatus();
        inForm.vrFlag = panoExposition.getVrFlag();
        inForm.expositionStatusNotes = panoExposition.getStatusNotes();
        // 如果存在预加载文件
        if (!ObjectUtils.isEmpty(panoExposition.getPreloadFilePath())) {
          String _srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_PRELOADFILE,
              inForm.expositionId);

          String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PRELOADFILE,
              inForm.expositionId);

          FileServiceUtil.copyDirFromPublicStorageToAppServer(_srcPath, _destPath);
        }

        // 展览音乐
        if (!ObjectUtils.isEmpty(panoExposition.getExpositionSoundId())) {
          PanoMaterial panoMaterial =
              panoMaterial01Mapper.selectByPrimaryKey(panoExposition.getExpositionSoundId());
          if (panoMaterial != null && !ObjectUtils.isEmpty(panoMaterial.getMaterialName())) {
            inForm.expositionSoundName = panoMaterial.getMaterialName();
          }
        }

        // 展览开展、撤展时间
        if (!ObjectUtils.isEmpty(panoExposition.getExpositionStartDate())) {
          inForm.expositionStartDate =
              panoExposition.getExpositionStartDate().format(DateTimeFormatter.ISO_LOCAL_DATE);
        }

        if (!ObjectUtils.isEmpty(panoExposition.getExpositionEndDate())) {
          inForm.expositionEndDate =
              panoExposition.getExpositionEndDate().format(DateTimeFormatter.ISO_LOCAL_DATE);
        }
      }
    }
  }
}
