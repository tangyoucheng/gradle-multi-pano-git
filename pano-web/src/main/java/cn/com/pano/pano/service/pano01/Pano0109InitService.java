package cn.com.pano.pano.service.pano01;

import java.text.MessageFormat;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Lists;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.utils.FileServiceUtil;
import cn.com.pano.pano.common.utils.PanoCommonUtil;
import cn.com.pano.pano.form.pano01.Pano0109Form;
import cn.com.pano.pano.mapper.common.PanoExpositionMapper;
import cn.com.pano.pano.mapper.common01.PanoMaterial01Mapper;
import cn.com.pano.pano.model.common.PanoExposition;
import cn.com.pano.pano.model.common.PanoMaterial;
import cn.com.platform.framework.common.CodeValueRecord;
import cn.com.platform.web.BaseService;

/**
 * 展览编辑画面初期显示
 * 
 * @author shiwei
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0109InitService extends BaseService {

  @Autowired
  public PanoExpositionMapper panoExpositionMapper;
  @Autowired
  public PanoMaterial01Mapper panoMaterial01Mapper;

  /**
   * 展览编辑画面初期显示处理
   * 
   * @throws Exception
   */
  public void doInit(Pano0109Form _inForm) throws Exception {
    // 预加载文件类型radioBox
    _inForm.preloadFileTypeList = Lists.newArrayList();
    _inForm.preloadFileTypeList.add(new CodeValueRecord("平面图", "0"));
    _inForm.preloadFileTypeList.add(new CodeValueRecord("视频", "1"));

    // 展览类型radioBox
    _inForm.expositionTypeList = PanoCommonUtil.getExpositionTypeList(false);
    if (!ObjectUtils.isEmpty(_inForm.expositionId)) {
      // 根据展览Id检索该展览的信息
      PanoExposition panoExposition = panoExpositionMapper.selectByPrimaryKey(_inForm.expositionId);
      if (panoExposition != null) {
        _inForm.expositionName = panoExposition.expositionName;
        _inForm.preloadFileType = panoExposition.preloadFileType;
        _inForm.expositionType = panoExposition.expositionType;
        _inForm.oldmaterialPath = "";
        // 如果存在预加载文件
        if (!ObjectUtils.isEmpty(panoExposition.preloadFilePath)) {
          _inForm.oldmaterialPath = MessageFormat.format(
              PanoConstantsIF.VAL_APP_SERVER_W_PRELOADFILE + panoExposition.preloadFilePath,
              _inForm.expositionId);
          String _srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_PRELOADFILE,
              _inForm.expositionId);

          String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PRELOADFILE,
              _inForm.expositionId);

          FileServiceUtil.copyDirFromPublicStorageToAppServer(_srcPath, _destPath);
        }

        // 展览音乐
        if (!ObjectUtils.isEmpty(panoExposition.expositionSoundId)) {
          PanoMaterial panoMaterial =
              panoMaterial01Mapper.selectByPrimaryKey(panoExposition.expositionSoundId);
          if (panoMaterial != null && !ObjectUtils.isEmpty(panoMaterial.materialName)) {
            _inForm.expositionSoundName = panoMaterial.materialName;
            _inForm.materialIdFromPano0208 = panoMaterial.materialId;
          }
        }

      }

    }

  }

}
