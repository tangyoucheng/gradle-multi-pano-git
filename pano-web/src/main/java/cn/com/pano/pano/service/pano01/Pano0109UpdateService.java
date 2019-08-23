package cn.com.pano.pano.service.pano01;

import java.text.MessageFormat;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.utils.FileServiceUtil;
import cn.com.pano.pano.form.pano01.Pano0109Form;
import cn.com.pano.pano.mapper.common.PanoExpositionMapper;
import cn.com.pano.pano.mapper.common01.PanoMaterial01Mapper;
import cn.com.pano.pano.model.common.PanoExposition;
import cn.com.pano.pano.model.common.PanoMaterial;
import cn.com.platform.web.BaseService;

/**
 * 展览更新删除操作
 * 
 * @author shiwei
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0109UpdateService extends BaseService {

  @Autowired
  public PanoExpositionMapper panoExpositionMapper;

  @Autowired
  public PanoMaterial01Mapper panoMaterial01Mapper;

  /**
   * 展览更新
   * 
   * @param _inForm
   * @throws Exception
   */
  public void doUpdateExpositionInfo(Pano0109Form _inForm) throws Exception {
    // 取得loginUserId
    String returnfilePath = "";
    // 展览基本信息更新操作
    if (!ObjectUtils.isEmpty(_inForm.expositionId)) {
      PanoExposition panoExposition = panoExpositionMapper.selectByPrimaryKey(_inForm.expositionId);
      if (panoExposition != null) {

        panoExposition.preloadFileType = "";
        panoExposition.expositionSoundId = "";

        // 如果勾选了音乐勾选框
        if ("3".equals(_inForm.expoSoundFlag)) {
          panoExposition.expositionSoundId = _inForm.materialIdFromPano0208;
        }

        // 如果勾选了预加载文件框
        if ("1".equals(_inForm.preloadFlag)) {
          panoExposition.preloadFileType = _inForm.preloadFileType;
          // 如果上传了新预加载文件
          if ("success".equals(_inForm.preloadFileUploadSuccess)) {
            String _destPath = MessageFormat
                .format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_PRELOADFILE, _inForm.expositionId);
            String fileName =
                FileServiceUtil.saveImageToPublicStorage(_inForm.expositionId, _destPath);
            if (!ObjectUtils.isEmpty(fileName)) {
              returnfilePath = fileName;
              panoExposition.preloadFilePath = returnfilePath;
            }
          }
        } else {
          panoExposition.preloadFilePath = "";
        }

        updateAudit(panoExposition);
        panoExpositionMapper.updateByPrimaryKey(panoExposition);
      }
    }

  }

  /**
   * 取得最新的声音名称
   * 
   * @param _inForm
   * @throws Exception
   */
  public String doRefreshSoundName(Pano0109Form _inForm) throws Exception {
    if (!ObjectUtils.isEmpty(_inForm.materialIdFromPano0208)) {
      PanoMaterial panoMaterial =
          panoMaterial01Mapper.selectByPrimaryKey(_inForm.materialIdFromPano0208);
      if (panoMaterial != null && !ObjectUtils.isEmpty(panoMaterial.materialName)) {
        return panoMaterial.materialName;
      }
    }
    return "";
  }

}
