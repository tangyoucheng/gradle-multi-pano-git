package cn.com.pano.panovr.service.panovr01;

import java.io.File;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Maps;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.utils.FilesServiceUtil;
import cn.com.pano.pano.mapper.common01.PanoExpositionMapHotspot01Mapper;
import cn.com.pano.pano.mapper.common01.PanoHotspotUrl01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanorama01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanoramaHotspot01Mapper;
import cn.com.pano.pano.model.common.PanoExpositionMapHotspot;
import cn.com.pano.pano.model.common.PanoHotspotUrl;
import cn.com.pano.pano.model.common.PanoPanorama;
import cn.com.pano.pano.model.common.PanoPanoramaHotspot;
import cn.com.pano.panovr.form.panovr01.PanoVr0104Form;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.util.FwFileUtils;
import cn.com.platform.web.BaseService;

/**
 * 删除当前场景的处理
 * 
 * @author ouyangzidou
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class PanoVr0104DeleteService extends BaseService {
  @Autowired
  public PanoPanorama01Mapper panoPanorama01Mapper;
  @Autowired
  public PanoHotspotUrl01Mapper panoHotspotUrl01Mapper;
  @Autowired
  public PanoExpositionMapHotspot01Mapper panoExpositionMapHotspot01Mapper;
  @Autowired
  public PanoPanoramaHotspot01Mapper panoPanoramaHotspot01Mapper;

  /**
   * 删除当前场景的处理
   * 
   * @param _inForm
   * @throws Exception
   */
  public void doDelete(PanoVr0104Form _inForm) throws Exception {
    HashMap<String, Object> conditions = Maps.newHashMap();

    BigDecimal currentPanoSortKey = new BigDecimal(0);
    // 检索到当前全景图
    if (!ObjectUtils.isEmpty(_inForm.panoramaId)) {
      // 1.删除其他场景中指向当前场景的热点下的全景图ID
      PanoPanorama panoPanorama = panoPanorama01Mapper.selectByPrimaryKey(_inForm.panoramaId);
      if (panoPanorama != null) {
        // 先取得当前场景的sortKey
        if (panoPanorama.panoramaSortKey != null) {
          currentPanoSortKey = panoPanorama.panoramaSortKey;
        }
        conditions.put("panoramaId", _inForm.panoramaId);
        List<PanoHotspotUrl> panoHotspotUrlList =
            panoHotspotUrl01Mapper.selectByPanoramaId(conditions);
        if (panoHotspotUrlList != null && panoHotspotUrlList.size() > 0) {
          for (PanoHotspotUrl panoHotspotUrl : panoHotspotUrlList) {
            // 改变指向当前全景图的上一张全景图中的热点的urlType
            PanoPanoramaHotspot panoPanoramaHotspot =
                panoPanoramaHotspot01Mapper.selectByPrimaryKey(panoHotspotUrl.hotspotId);
            if (panoPanoramaHotspot != null) {
              panoPanoramaHotspot.hotspotUrlType = "";
              updateAudit(panoPanoramaHotspot);
              panoPanoramaHotspot01Mapper.updateByPrimaryKey(panoPanoramaHotspot);
            }
            // 删除HotspotUrl表中数据
            panoHotspotUrl01Mapper.deleteByPrimaryKey(panoHotspotUrl.hotspotId,
                panoHotspotUrl.hotspotUrlObjectId);
          }
        }

        // 2.如果当前全景图被地图上的热点指向,删除该热点下的全景图id
        if (!ObjectUtils.isEmpty(_inForm.expositionMapId)) {
          conditions.put("expositionMapId", _inForm.expositionMapId);
          List<PanoExpositionMapHotspot> result =
              panoExpositionMapHotspot01Mapper.selectMapHotspotInfo(conditions);
          if (result != null && result.size() > 0) {
            for (PanoExpositionMapHotspot panoExpositionMapHotspot : result) {
              if (_inForm.panoramaId.equals(panoExpositionMapHotspot.panoramaId)) {
                // 去掉热点下指向的全景图ID
                panoExpositionMapHotspot.panoramaId = "";
                updateAudit(panoExpositionMapHotspot);
                panoExpositionMapHotspot01Mapper.updateByPrimaryKey(panoExpositionMapHotspot);
              }
            }
          }
        }
        // 3.删掉全景图上所有热点
        HashMap<String, Object> condition = Maps.newHashMap();
        condition.put("panoramaId", _inForm.panoramaId);
        List<PanoPanoramaHotspot> result = panoPanoramaHotspot01Mapper.selectHotSpot(condition);
        if (!result.isEmpty()) {
          panoPanoramaHotspot01Mapper.deleteHotSpot(condition);
        }

        // 4.删除全景图
        panoPanorama01Mapper.deleteByPrimaryKey(panoPanorama.panoramaId);

        HashMap<String, Object> panoCondition = Maps.newHashMap();
        panoCondition.put("expositionId", _inForm.expositionId);
        // 5.把被删除的场景之后的场景的sortKey分别-1
        if (currentPanoSortKey.intValue() != 0) {
          int sortKey = currentPanoSortKey.intValue();
          List<PanoPanorama> panoList = panoPanorama01Mapper.findByExpositionId(panoCondition);
          for (PanoPanorama pano : panoList) {
            if (pano != null && pano.panoramaSortKey != null) {
              int othersSortKey = (pano.panoramaSortKey).intValue();
              // 如果是被删除场景之后的场景
              if (othersSortKey > sortKey) {
                othersSortKey -= 1;
                BigDecimal _othersSortKey = new BigDecimal(othersSortKey);
                pano.panoramaSortKey = _othersSortKey;
                panoPanorama01Mapper.updateByPrimaryKey(pano);
              }
            }
          }
        }

        // 6.如果删除的是首个场景，重新从展览中选择并设定首场景
        if (FlagStatus.Enable.toString().equals(panoPanorama.startSceneFlag)) {
          // 检索全部检索全景图
          List<PanoPanorama> panoList = panoPanorama01Mapper.findByExpositionId(panoCondition);
          // 抽取第一个全景图设置为首图
          if (panoList != null && panoList.size() > 0) {
            PanoPanorama panorama = panoList.get(0);
            panorama.startSceneFlag = FlagStatus.Enable.toString();
            panoPanorama01Mapper.updateByPrimaryKey(panorama);
          }
        }

        // 7.删除源文件
        String filePath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_PANORAMA,
            _inForm.expositionId, _inForm.panoramaId);
        FilesServiceUtil.deletePublicStorageFolder(filePath);
        String filePath1 = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
            _inForm.expositionId, _inForm.panoramaId);
        FileUtils.deleteDirectory(new File(FwFileUtils.getAbsolutePath(filePath1)));
        String filePath2 = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_PANORAMA,
            _inForm.expositionId, _inForm.panoramaId);
        FileUtils.deleteDirectory(new File(FwFileUtils.getAbsolutePath(filePath2)));

      }

    }

  }
}
