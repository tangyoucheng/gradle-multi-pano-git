package cn.com.pano.pano.service.pano03;

import java.text.MessageFormat;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.form.pano03.Pano0308Form;
import cn.com.pano.pano.mapper.common01.PanoPanorama01Mapper;
import cn.com.pano.pano.model.common.PanoPanoramaQuery;
import cn.com.pano.pano.model.common01.PanoPanorama01Model;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 展览目录编辑初始化处理。
 * 
 * @author shiwei
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0308SearchService extends BaseService {
  @Autowired
  public PanoPanorama01Mapper panoPanorama01Mapper;

  /**
   * 检索处理。
   * 
   * @param inForm Pano0308Form
   */
  public EasyJson<Object> doSearch(Pano0308Form inForm) throws Exception {


    PanoPanoramaQuery conditions = new PanoPanoramaQuery();
    conditions.createCriteria().andExpositionIdEqualTo(inForm.expositionId);
    conditions.setOrderByClause("panorama_sort_key ASC");
    // 检索全部检索全景图
    List<PanoPanorama01Model> panoPanoramaList = panoPanorama01Mapper.selectByBaseModel(conditions);

    // 场景详细信息取得
    inForm.thumbInfoList = panoPanoramaList;

    if (inForm.thumbInfoList != null && !inForm.thumbInfoList.isEmpty()) {
      for (int i = 0; i < inForm.thumbInfoList.size(); i++) {
        PanoPanorama01Model panoPanorama = inForm.thumbInfoList.get(i);
        // 图片路径
        String thumbFilePath =
            MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA + "/thumb.jpg",
                UserSessionUtils.getSessionId(), "pano0308/" + panoPanorama.expositionId,
                panoPanorama.panoramaId);
        panoPanorama.panoramaPath = "<img src=\"" + thumbFilePath + "\">";
        // 页面checkBox状态
        // if (FlagStatus.Enable.toString().equals(panoPanorama.thumbnailShowFlag)) {
        // panoPanorama.thumbnailShowFlag = "checked";
        // } else {
        // panoPanorama.thumbnailShowFlag = "";
        // }
      }
    }
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setSuccess(true);
    easyJson.setRows(inForm.thumbInfoList);
    return easyJson;
  }
}
