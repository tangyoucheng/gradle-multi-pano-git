package cn.com.pano.pano.service.pano02;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.NumberUtils;
import com.google.common.collect.Maps;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.dto.pano02.Pano0203Dto;
import cn.com.pano.pano.form.pano02.Pano0203Form;
import cn.com.pano.pano.mapper.common01.PanoHotspotUrl01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanoramaHotspot01Mapper;
import cn.com.pano.pano.model.common.PanoHotspotUrl;
import cn.com.pano.pano.model.common.PanoPanoramaHotspot;
import cn.com.pano.pano.model.common.PanoPanoramaHotspotQuery;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.framework.util.FwStringUtils;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 保存热点信息。
 * 
 * @author ouyangzidou
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0203UpdateService extends BaseService {

  @Autowired
  public PanoHotspotUrl01Mapper panoHotspotUrl01Mapper;
  @Autowired
  public PanoPanoramaHotspot01Mapper panoPanoramaHotspot01Mapper;

  /**
   * 保存处理。
   * 
   * @param inForm Pano0203Form
   * @return
   */
  public EasyJson<Object> doSave(Pano0203Form inForm) throws Exception {
    // 先修改平面图上所有热点状态为1
    PanoPanoramaHotspot record = new PanoPanoramaHotspot();
    record.deleteFlag = true;
    PanoPanoramaHotspotQuery baseCondition = new PanoPanoramaHotspotQuery();
    baseCondition.createCriteria().andPanoramaIdEqualTo(inForm.panoramaId);
    panoPanoramaHotspot01Mapper.updateByBaseModelSelective(record, baseCondition);
    // 如果保存时存在热点坐标
    // inForm.hotspotInfoList = FwJsonUtil.jsonToList(inForm.spotInfoListSubmitJson,
    // Pano0203Dto.class);
    if (inForm.hotspotInfoList != null) {

      // 循环热点
      for (Pano0203Dto hotspot : inForm.hotspotInfoList) {

        // 检索录入的热点坐标
        PanoPanoramaHotspot panoPanoramaHotspot =
            panoPanoramaHotspot01Mapper.selectByPrimaryKey(hotspot.hotspotId);
        // 热点存在
        if (panoPanoramaHotspot != null) {
          // 该热点存在，做普通更新录入操作
          panoPanoramaHotspot.deleteFlag = false;
          // 不为多边形时录入坐标
          if (!PanoConstantsIF.HOTSPOT_TYPE_HOTSPOT_POLYGON.equals(hotspot.hotspotType)) {
            panoPanoramaHotspot.hotspotImageId = hotspot.hotspotImageId;
            if (PanoConstantsIF.HOTSPOT_TYPE_HOTSPOT_MUSIC.equals(hotspot.hotspotType)) {
              // 更新音乐热点所属url
              HashMap<String, Object> condition = Maps.newHashMap();
              condition.put("hotspotId", hotspot.hotspotId);
              // 清除音乐热点下除了音乐素材外的所有url
              panoHotspotUrl01Mapper.deleteMusicHotspotUrlInfo(condition);

              PanoHotspotUrl firstMusicHotspotUrl = new PanoHotspotUrl();
              firstMusicHotspotUrl.hotspotId = hotspot.hotspotId;
              firstMusicHotspotUrl.hotspotUrlObjectId = hotspot.hotspotImageId;
              firstMusicHotspotUrl.sortKey =
                  NumberUtils.parseNumber(hotspot.firstSortkey, BigDecimal.class);
              createAudit(firstMusicHotspotUrl);;
              panoHotspotUrl01Mapper.insert(firstMusicHotspotUrl);

              PanoHotspotUrl secondMusicHotspotUrl = new PanoHotspotUrl();
              secondMusicHotspotUrl.hotspotId = hotspot.hotspotId;
              secondMusicHotspotUrl.hotspotUrlObjectId = hotspot.secondHotspotImageId;
              secondMusicHotspotUrl.sortKey =
                  NumberUtils.parseNumber(hotspot.secondSortkey, BigDecimal.class);
              createAudit(secondMusicHotspotUrl);;
              panoHotspotUrl01Mapper.insert(secondMusicHotspotUrl);

            }

            panoPanoramaHotspot.hotspotAth = hotspot.hotspotAth;
            panoPanoramaHotspot.hotspotAtv = hotspot.hotspotAtv;
            panoPanoramaHotspot.hotspotScale = hotspot.hotspotScale;
          }

          panoPanoramaHotspot.lastUpdateUserId = UserSessionUtils.getUserName();
          panoPanoramaHotspot.lastUpdateDate = LocalDateTime.now();
          panoPanoramaHotspot01Mapper.updateByPrimaryKey(panoPanoramaHotspot);
        } else {
          // 该热点不存在，新规
          panoPanoramaHotspot = new PanoPanoramaHotspot();
          panoPanoramaHotspot.panoramaId = inForm.panoramaId;
          panoPanoramaHotspot.hotspotId = FwStringUtils.getUniqueId();
          panoPanoramaHotspot.hotspotAth = hotspot.hotspotAth;
          panoPanoramaHotspot.hotspotAtv = hotspot.hotspotAtv;
          panoPanoramaHotspot.hotspotScale = hotspot.hotspotScale;
          panoPanoramaHotspot.hotspotType = hotspot.hotspotType;
          panoPanoramaHotspot.hotspotImageId = hotspot.hotspotImageId;
          createAudit(panoPanoramaHotspot);;
          panoPanoramaHotspot01Mapper.insert(panoPanoramaHotspot);

          // 如果是音乐热点
          if (PanoConstantsIF.HOTSPOT_TYPE_HOTSPOT_MUSIC.equals(hotspot.hotspotType)) {

            PanoHotspotUrl fakeMusicHotspotUrl = new PanoHotspotUrl();
            fakeMusicHotspotUrl.hotspotId = panoPanoramaHotspot.hotspotId;
            fakeMusicHotspotUrl.hotspotUrlObjectId = "musicMaterialId";
            fakeMusicHotspotUrl.sortKey = BigDecimal.ZERO;
            createAudit(fakeMusicHotspotUrl);
            panoHotspotUrl01Mapper.insert(fakeMusicHotspotUrl);

            PanoHotspotUrl firstMusicHotspotUrl = new PanoHotspotUrl();
            firstMusicHotspotUrl.hotspotId = panoPanoramaHotspot.hotspotId;
            firstMusicHotspotUrl.hotspotUrlObjectId = hotspot.hotspotImageId;
            firstMusicHotspotUrl.sortKey =
                NumberUtils.parseNumber(hotspot.firstSortkey, BigDecimal.class);
            createAudit(firstMusicHotspotUrl);
            panoHotspotUrl01Mapper.insert(firstMusicHotspotUrl);

            PanoHotspotUrl secondMusicHotspotUrl = new PanoHotspotUrl();
            secondMusicHotspotUrl.hotspotId = panoPanoramaHotspot.hotspotId;
            secondMusicHotspotUrl.hotspotUrlObjectId = hotspot.secondHotspotImageId;
            secondMusicHotspotUrl.sortKey =
                NumberUtils.parseNumber(hotspot.secondSortkey, BigDecimal.class);
            createAudit(secondMusicHotspotUrl);
            panoHotspotUrl01Mapper.insert(secondMusicHotspotUrl);
          }

        }

      }
    }

    // 最后删除余下flg为1的热点
    baseCondition = new PanoPanoramaHotspotQuery();
    baseCondition.createCriteria().andDeleteFlagEqualTo(true)
        .andPanoramaIdEqualTo(inForm.panoramaId);
    panoPanoramaHotspot01Mapper.deleteByBaseModel(baseCondition);

    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setSuccess(true);
    easyJson.setMsg("保存成功");
    return easyJson;

  }
}
