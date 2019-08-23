package cn.com.pano.pano.service.pano03;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.pano.pano.form.pano03.Pano0308Form;
import cn.com.pano.pano.mapper.common01.PanoPanorama01Mapper;
import cn.com.pano.pano.mapper.pano03.Pano0308Mapper;
import cn.com.pano.pano.model.common.PanoPanorama;
import cn.com.pano.pano.model.common.PanoPanoramaQuery;
import cn.com.pano.pano.model.common01.PanoPanorama01Model;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 修改后的缩略图显示状况保存。
 * 
 * @author shiwei
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0308EntryService extends BaseService {
  @Autowired
  public Pano0308Mapper pano0308Mapper;
  @Autowired
  public PanoPanorama01Mapper panoPanorama01Mapper;

  /**
   * 修改后的缩略图显示状况保存。
   * 
   * @param inForm Pano0308Form
   * @return
   */
  public EasyJson<Object> doSave(Pano0308Form inForm) throws Exception {

    // 展览id相关场景所有显示标志复位
    PanoPanorama record = new PanoPanorama();
    record.thumbnailShowFlag = FlagStatus.Disable.toString();
    PanoPanoramaQuery baseCondition = new PanoPanoramaQuery();
    baseCondition.createCriteria().andExpositionIdEqualTo(inForm.expositionId);
    panoPanorama01Mapper.updateByBaseModelSelective(record, baseCondition);


    if (inForm.thumbSelectedList != null) {
      for (int i = 0; i < inForm.thumbSelectedList.size(); i++) {
        String panoramaId = inForm.thumbSelectedList.get(i);
        // 选中项标志设置
        if (!ObjectUtils.isEmpty(panoramaId)) {

          PanoPanorama panoPanorama = panoPanorama01Mapper.selectByPrimaryKey(panoramaId);
          if (panoPanorama != null) {
            panoPanorama.thumbnailShowFlag = FlagStatus.Enable.toString();
            panoPanorama01Mapper.updateByPrimaryKey(panoPanorama);
          }
        }
      }
    }

    // 更新每个场景的缩略图备注
    if (inForm.thumbInfoList != null && inForm.thumbInfoList.size() > 0) {
      for (int i = 0; i < inForm.thumbInfoList.size(); i++) {
        PanoPanorama01Model thumbInfo = inForm.thumbInfoList.get(i);

        PanoPanorama panoPanorama = panoPanorama01Mapper.selectByPrimaryKey(thumbInfo.panoramaId);
        if (panoPanorama != null) {
          panoPanorama.thumbNote = thumbInfo.thumbNote;
          panoPanorama01Mapper.updateByPrimaryKey(panoPanorama);
        }
      }
    }

    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setSuccess(true);
    easyJson.setMsg("保存成功");
    return easyJson;
  }
}
