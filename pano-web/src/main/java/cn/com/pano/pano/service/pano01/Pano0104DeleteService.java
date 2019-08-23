package cn.com.pano.pano.service.pano01;


import java.io.File;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.form.pano01.Pano0104Form;
import cn.com.pano.pano.mapper.common01.PanoExpositionMapHotspot01Mapper;
import cn.com.pano.pano.mapper.common01.PanoHotspotUrl01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanorama01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanoramaHotspot01Mapper;
import cn.com.pano.pano.model.common.PanoExpositionMapHotspotQuery;
import cn.com.pano.pano.model.common.PanoHotspotUrlQuery;
import cn.com.pano.pano.model.common.PanoPanorama;
import cn.com.pano.pano.model.common.PanoPanoramaHotspot;
import cn.com.pano.pano.model.common.PanoPanoramaHotspotQuery;
import cn.com.pano.pano.model.common.PanoPanoramaQuery;
import cn.com.pano.pano.model.common01.PanoExpositionMapHotspot01Model;
import cn.com.pano.pano.model.common01.PanoHotspotUrl01Model;
import cn.com.pano.pano.model.common01.PanoPanorama01Model;
import cn.com.pano.pano.model.common01.PanoPanoramaHotspot01Model;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.util.EasyJson;
import cn.com.platform.util.FwFileUtils;
import cn.com.platform.web.BaseService;

/**
 * 删除当前场景的处理。
 * 
 * @author ouyangzidou
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0104DeleteService extends BaseService {
  @Autowired
  public PanoPanorama01Mapper panoPanorama01Mapper;
  @Autowired
  public PanoHotspotUrl01Mapper panoHotspotUrl01Mapper;
  @Autowired
  public PanoExpositionMapHotspot01Mapper panoExpositionMapHotspot01Mapper;
  @Autowired
  public PanoPanoramaHotspot01Mapper panoPanoramaHotspot01Mapper;

  /**
   * 删除当前场景的处理。
   * 
   * @param inForm Pano0104Form
   * @throws Exception 异常的场合
   */
  public EasyJson<Object> doDeletePano(Pano0104Form inForm) throws Exception {

    BigDecimal currentPanoSortKey = BigDecimal.ZERO;
    // 检索到当前全景图
    if (!ObjectUtils.isEmpty(inForm.panoramaId)) {
      // 1.删除其他场景中指向当前场景的热点下的全景图ID
      PanoPanorama panoPanorama = panoPanorama01Mapper.selectByPrimaryKey(inForm.panoramaId);
      if (panoPanorama != null) {
        // 先取得当前场景的sortKey
        if (ObjectUtils.isNotEmpty(panoPanorama.getPanoramaSortKey())) {
          currentPanoSortKey = panoPanorama.getPanoramaSortKey();
        }
        PanoHotspotUrlQuery panoHotspotUrlQuery = new PanoHotspotUrlQuery();
        panoHotspotUrlQuery.createCriteria().andHotspotUrlObjectIdEqualTo(inForm.panoramaId);
        List<PanoHotspotUrl01Model> panoHotspotUrlList =
            panoHotspotUrl01Mapper.selectByBaseModel(panoHotspotUrlQuery);
        if (ObjectUtils.isNotEmpty(panoHotspotUrlList)) {
          for (PanoHotspotUrl01Model panoHotspotUrl : panoHotspotUrlList) {
            // 改变指向当前全景图的上一张全景图中的热点的urlType
            PanoPanoramaHotspot panoPanoramaHotspot =
                panoPanoramaHotspot01Mapper.selectByPrimaryKey(panoHotspotUrl.getHotspotId());
            if (panoPanoramaHotspot != null) {
              panoPanoramaHotspot.setHotspotUrlType("");
              updateAudit(panoPanoramaHotspot);
              panoPanoramaHotspot01Mapper.updateByPrimaryKey(panoPanoramaHotspot);
            }
            // 删除HotspotUrl表中数据
            panoHotspotUrl01Mapper.deleteByPrimaryKey(panoHotspotUrl.hotspotId,
                panoHotspotUrl.hotspotUrlObjectId);
          }
        }

        // 2.如果当前全景图被地图上的热点指向,删除该热点下的全景图id
        if (!ObjectUtils.isEmpty(inForm.expositionMapId)) {
          PanoExpositionMapHotspotQuery panoExpositionMapHotspotQuery =
              new PanoExpositionMapHotspotQuery();
          panoExpositionMapHotspotQuery.createCriteria()
              .andExpositionMapIdEqualTo(inForm.expositionMapId);
          List<PanoExpositionMapHotspot01Model> result =
              panoExpositionMapHotspot01Mapper.selectByBaseModel(panoExpositionMapHotspotQuery);
          if (ObjectUtils.isNotEmpty(result)) {
            for (PanoExpositionMapHotspot01Model panoExpositionMapHotspot : result) {
              if (Objects.equals(inForm.panoramaId, panoExpositionMapHotspot.getPanoramaId())) {
                // 去掉热点下指向的全景图ID
                panoExpositionMapHotspot.setPanoramaId("");;
                updateAudit(panoExpositionMapHotspot);
                panoExpositionMapHotspot01Mapper.updateByPrimaryKey(panoExpositionMapHotspot);
              }
            }
          }
        }
        // 3.删掉全景图上所有热点
        PanoPanoramaHotspotQuery panoPanoramaHotspotQuery = new PanoPanoramaHotspotQuery();
        panoPanoramaHotspotQuery.createCriteria().andPanoramaIdEqualTo(inForm.panoramaId);
        List<PanoPanoramaHotspot01Model> result =
            panoPanoramaHotspot01Mapper.selectByBaseModel(panoPanoramaHotspotQuery);
        if (!result.isEmpty()) {
          panoPanoramaHotspot01Mapper.deleteByBaseModel(panoPanoramaHotspotQuery);
        }

        // 4.删除全景图
        panoPanorama01Mapper.deleteByPrimaryKey(panoPanorama.panoramaId);

        PanoPanoramaQuery panoPanoramaQuery = new PanoPanoramaQuery();
        panoPanoramaQuery.createCriteria().andExpositionIdEqualTo(inForm.expositionId);
        // 5.把被删除的场景之后的场景的sortKey分别-1
        if (currentPanoSortKey.intValue() != 0) {
          int sortKey = currentPanoSortKey.intValue();
          List<PanoPanorama01Model> panoList =
              panoPanorama01Mapper.selectByBaseModel(panoPanoramaQuery);
          for (PanoPanorama01Model pano : panoList) {
            if (pano != null && pano.getPanoramaSortKey() != null) {
              int othersSortKey = (pano.getPanoramaSortKey()).intValue();
              // 如果是被删除场景之后的场景
              if (othersSortKey > sortKey) {
                othersSortKey -= 1;
                pano.setPanoramaSortKey(new BigDecimal(othersSortKey));;
                panoPanorama01Mapper.updateByPrimaryKey(pano);
              }
            }
          }
        }

        // 6.如果删除的是首个场景，重新从展览中选择并设定首场景
        if (Objects.equals(FlagStatus.Enable.toString(), panoPanorama.getStartSceneFlag())) {
          // 检索全部检索全景图
          List<PanoPanorama01Model> panoList =
              panoPanorama01Mapper.selectByBaseModel(panoPanoramaQuery);
          // 抽取第一个全景图设置为首图
          if (ObjectUtils.isNotEmpty(panoList)) {
            PanoPanorama panorama = panoList.get(0);
            panorama.setStartSceneFlag(FlagStatus.Enable.toString());;
            panoPanorama01Mapper.updateByPrimaryKey(panorama);
          }
        }

        // 7.删除源文件
        // 获取APP服务器侧文件目录。
        String srcAppRelativePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_PANORAMA,
            UserSessionUtils.getSessionId(), inForm.getExpositionId(),
            inForm.getPanoramaId() + "/");
        FileUtils.deleteDirectory(new File(FwFileUtils.getAbsolutePath(srcAppRelativePath)));
        // 全景的storage路径
        String destPublicPath =
            MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_PANORAMA,
                inForm.expositionId, inForm.panoramaId + "/");
        FileUtils.deleteDirectory(new File(destPublicPath).getAbsoluteFile());

      }

    }

    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setMsg("删除成功！");
    return easyJson;
  }
}
