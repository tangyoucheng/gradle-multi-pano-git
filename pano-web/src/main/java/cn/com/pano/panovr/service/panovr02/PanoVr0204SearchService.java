package cn.com.pano.panovr.service.panovr02;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Maps;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.utils.FileServiceUtil;
import cn.com.pano.pano.mapper.common01.PanoPanorama01Mapper;
import cn.com.pano.pano.model.common.PanoPanorama;
import cn.com.pano.panovr.form.panovr02.PanoVr0204Form;
import cn.com.pano.panovr.mapper.panovr02.PanoVr0204Mapper;
import cn.com.platform.web.BaseService;
import cn.com.platform.web.view.PagerCommon;
import cn.com.platform.web.view.PagerForm;

/**
 * 检索图信息
 * 
 * @author ouyangzidou
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class PanoVr0204SearchService extends BaseService {

  @Autowired
  public PanoPanorama01Mapper panoPanorama01Mapper;
  @Autowired
  public PanoVr0204Mapper vr0204Mapper;

  /**
   * 检索场景详细信息
   * 
   * @param _inForm
   * @return
   * @throws Exception
   */
  public String doSearchPanorama(PanoVr0204Form _inForm) throws Exception {
    String searchResult = "";
    // 场景数检查
    HashMap<String, Object> condition = Maps.newHashMap();
    condition.put("expositionId", _inForm.expositionId);
    condition.put("panoramaId", _inForm.currentPanoramaId);
    int resultCount = vr0204Mapper.selectPanoInfoCount(condition);
    checkCount(_inForm, resultCount);
    // 翻页信息取得
    PagerForm pagerForm = new PagerForm();
    pagerForm.setFormId("vr0204Form");
    pagerForm.setFormAction("");
    pagerForm.setPageSize(Objects.toString(_inForm.pageSize));
    pagerForm.setPageStartRowNo(Objects.toString(_inForm.pageStartRowNo));
    pagerForm.setRecordCount(Objects.toString(_inForm.recordCount));
    pagerForm.setUniqueFlag(true);
    pagerForm.setAjaxPageMethod("doAjaxPage");
    PagerCommon pagerCommon = new PagerCommon();
    _inForm.pageShowInfos = pagerCommon.doEditPageInfo(pagerForm);
    _inForm.panoramaList = vr0204Mapper.selectPanoInfo(condition, _inForm.pageStartRowNo);
    if (_inForm.panoramaList != null) {
      for (PanoPanorama panoPanorama : _inForm.panoramaList) {

        panoPanorama.panoramaPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
            _inForm.expositionId, panoPanorama.panoramaPath + PanoConstantsIF.PANOS_SHOW_L_XML);
        // 全景图文件取得
        String _srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_PANORAMA,
            _inForm.expositionId, panoPanorama.panoramaId + "/");
        String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
            _inForm.expositionId, panoPanorama.panoramaId + "/");
        FileServiceUtil.getPanoramaFileFromPublicStorage(_srcPath, _destPath);

      }
    }

    searchResult = objectMapper.writeValueAsString(_inForm);
    return searchResult;
  }

}
