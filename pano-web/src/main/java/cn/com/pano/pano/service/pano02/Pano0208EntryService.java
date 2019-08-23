package cn.com.pano.pano.service.pano02;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.pano.pano.form.pano02.Pano0208Form;
import cn.com.pano.pano.mapper.common.PanoExpositionMapper;
import cn.com.pano.pano.mapper.common01.PanoPanorama01Mapper;
import cn.com.pano.pano.model.common.PanoExposition;
import cn.com.platform.web.BaseService;

/**
 * 录入声音信息
 * 
 * @author ouyangzidou
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0208EntryService extends BaseService {
  @Autowired
  public PanoExpositionMapper panoExpositionMapper;
  @Autowired
  public PanoPanorama01Mapper panoPanorama01Mapper;

  /**
   * 声音的录入处理
   * 
   * @param _inForm
   * @throws Exception
   */
  public void doEntrySound(Pano0208Form _inForm) throws Exception {
    if (!ObjectUtils.isEmpty(_inForm.expositionId)) {
      PanoExposition panoExposition = panoExpositionMapper.selectByPrimaryKey(_inForm.expositionId);
      if (panoExposition != null) {
        // 录入音乐
        panoExposition.expositionSoundId = _inForm.selectedMaterialId;
        panoExpositionMapper.updateByPrimaryKey(panoExposition);
      }
    }
  }
}
