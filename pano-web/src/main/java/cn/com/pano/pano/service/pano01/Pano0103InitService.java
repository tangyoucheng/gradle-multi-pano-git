package cn.com.pano.pano.service.pano01;

import java.text.MessageFormat;
import java.time.format.DateTimeFormatter;
import org.apache.commons.lang3.ObjectUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.code.ExpositionStatus;
import cn.com.pano.pano.common.code.ExpositionType;
import cn.com.pano.pano.common.utils.FileServiceUtil;
import cn.com.pano.pano.form.pano01.Pano0103Form;
import cn.com.pano.pano.mapper.common.PanoExpositionMapper;
import cn.com.pano.pano.mapper.common01.PanoMaterial01Mapper;
import cn.com.pano.pano.model.common.PanoExposition;
import cn.com.pano.pano.model.common.PanoMaterial;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.framework.common.CodeValueRecord;
import cn.com.platform.framework.util.FwCodeUtil;
import cn.com.platform.util.MessageUtils;
import cn.com.platform.web.BaseService;

/**
 * 展览编辑画面初期显示
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
   * 展览编辑画面初期显示处理
   * 
   * @throws Exception
   */
  public void doInit(Pano0103Form _inForm) throws Exception {

    // 展览类型radioBox
    _inForm.expositionTypeList = Lists.newArrayList();
    // 引进展
    ExpositionType expositionType =
        FwCodeUtil.stringToEnum(ExpositionType.class, ExpositionType.IMPORT.toString());
    _inForm.expositionTypeList.add(new CodeValueRecord(ExpositionType.IMPORT.toString(),
        MessageUtils.getMessage(expositionType.getMessageId())));
    // 外展
    expositionType =
        FwCodeUtil.stringToEnum(ExpositionType.class, ExpositionType.OUTPUT.toString());
    _inForm.expositionTypeList.add(new CodeValueRecord(ExpositionType.OUTPUT.toString(),
        MessageUtils.getMessage(expositionType.getMessageId())));

    // 展览状态radioBox
    _inForm.expositionStatusList = Lists.newArrayList();
    // 规划中
    ExpositionStatus expositionStatus =
        FwCodeUtil.stringToEnum(ExpositionStatus.class, ExpositionStatus.PLANNING.toString());
    _inForm.expositionStatusList.add(new CodeValueRecord(ExpositionStatus.PLANNING.toString(),
        MessageUtils.getMessage(expositionStatus.getMessageId())));
    // 进行中
    expositionStatus =
        FwCodeUtil.stringToEnum(ExpositionStatus.class, ExpositionStatus.PROGRESS.toString());
    _inForm.expositionStatusList.add(new CodeValueRecord(ExpositionStatus.PROGRESS.toString(),
        MessageUtils.getMessage(expositionStatus.getMessageId())));
    // 已结束
    expositionStatus =
        FwCodeUtil.stringToEnum(ExpositionStatus.class, ExpositionStatus.OVER.toString());
    _inForm.expositionStatusList.add(new CodeValueRecord(ExpositionStatus.OVER.toString(),
        MessageUtils.getMessage(expositionStatus.getMessageId())));

    // 展览模式radioBox
    _inForm.vrFlagList = Lists.newArrayList();
    _inForm.vrFlagList.add(new CodeValueRecord("非VR展", FlagStatus.Disable.toString()));
    _inForm.vrFlagList.add(new CodeValueRecord("VR展", FlagStatus.Enable.toString()));

    if (!ObjectUtils.isEmpty(_inForm.expositionId)) {
      // 根据展览Id检索该展览的信息
      PanoExposition panoExposition = panoExpositionMapper.selectByPrimaryKey(_inForm.expositionId);
      if (panoExposition != null) {
        _inForm.expositionFolderId = panoExposition.getExpositionFolderId();
        _inForm.expositionName = panoExposition.getExpositionName();
        _inForm.expositionNotes = panoExposition.getNotes();
        _inForm.expositionType = panoExposition.getExpositionType();
        _inForm.expositionStatus = panoExposition.getStatus();
        _inForm.vrFlag = panoExposition.getVrFlag();
        _inForm.expositionStatusNotes = panoExposition.getStatusNotes();
        // 如果存在预加载文件
        if (!ObjectUtils.isEmpty(panoExposition.getPreloadFilePath())) {
          String _srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_PRELOADFILE,
              _inForm.expositionId);

          String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PRELOADFILE,
              _inForm.expositionId);

           FileServiceUtil.copyDirFromPublicStorageToAppServer(_srcPath, _destPath);
        }

        // 展览音乐
        if (!ObjectUtils.isEmpty(panoExposition.getExpositionSoundId())) {
          PanoMaterial panoMaterial =
              panoMaterial01Mapper.selectByPrimaryKey(panoExposition.getExpositionSoundId());
          if (panoMaterial != null && !ObjectUtils.isEmpty(panoMaterial.getMaterialName())) {
            _inForm.expositionSoundName = panoMaterial.getMaterialName();
          }
        }

        // 展览开展、撤展时间
        if (!ObjectUtils.isEmpty(panoExposition.getExpositionStartDate())) {
          _inForm.expositionStartDate =
              panoExposition.getExpositionStartDate().format(DateTimeFormatter.ISO_LOCAL_DATE);
        }

        if (!ObjectUtils.isEmpty(panoExposition.getExpositionEndDate())) {
          _inForm.expositionEndDate =
              panoExposition.getExpositionEndDate().format(DateTimeFormatter.ISO_LOCAL_DATE);
        }

      }

    }

  }
}
