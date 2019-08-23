package cn.com.pano.panovr.service.panovr03;

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
import cn.com.pano.panovr.form.panovr03.PanoVr0307Form;
import cn.com.pano.panovr.mapper.panovr03.PanoVr0307Mapper;
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
public class PanoVr0307SearchService extends BaseService {

  @Autowired
  public PanoVr0307Mapper vr0307Mapper;
  @Autowired
  public PanoPanorama01Mapper panoPanorama01Mapper;

  /**
   * 检索场景详细信息
   * 
   * @throws Exception
   */
  public String doSearchExpositionMap(PanoVr0307Form _inForm) throws Exception {
    String searchResult = "";
    // 件数检查
    HashMap<String, Object> condition = Maps.newHashMap();
    condition.put("expositionId", _inForm.expositionId);
    int resultCount = vr0307Mapper.selectPanoramaInfoCount(condition);
    checkCount(_inForm, resultCount);
    // 翻页信息取得
    PagerForm pagerForm = new PagerForm();
    pagerForm.setFormId("vr0307Form");
    pagerForm.setFormAction("");
    pagerForm.setPageSize(Objects.toString(_inForm.pageSize));
    pagerForm.setPageStartRowNo(Objects.toString(_inForm.pageStartRowNo));
    pagerForm.setRecordCount(Objects.toString(_inForm.recordCount));
    pagerForm.setUniqueFlag(true);
    pagerForm.setAjaxPageMethod("doAjaxPage");
    PagerCommon pagerCommon = new PagerCommon();
    _inForm.pageShowInfos = pagerCommon.doEditPageInfo(pagerForm);
    // 设备信息详细
    _inForm.panoramaInfo = vr0307Mapper.selectPanoramaInfo(_inForm.pageStartRowNo, condition);
    searchResult = objectMapper.writeValueAsString(_inForm);
    return searchResult;
  }

  /**
   * 取得场景全景图
   * 
   * @param _inForm
   * @throws Exception
   */
  public String doGetPreviewImage(PanoVr0307Form _inForm) throws Exception {
    PanoPanorama panoPanorama = panoPanorama01Mapper.selectByPrimaryKey(_inForm.selectedPanoramaId);
    _inForm.panoramaPath = "";
    if (panoPanorama != null) {

      _inForm.panoramaPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
          _inForm.expositionId, panoPanorama.panoramaPath + PanoConstantsIF.PANOS_SHOW_L_XML);
      // 全景图文件取得
      String _srcPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_PANORAMA,
          _inForm.expositionId, panoPanorama.panoramaId + "/");
      String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
          _inForm.expositionId, panoPanorama.panoramaId + "/");
      FileServiceUtil.getPanoramaFileFromPublicStorage(_srcPath, _destPath);

    }
    return _inForm.panoramaPath;
  }
}
