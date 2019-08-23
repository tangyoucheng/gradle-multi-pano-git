package cn.com.pano.pano.form.pano01;

import java.io.Serializable;
import java.util.List;
import cn.com.pano.pano.dto.pano01.Pano0104Dto;
import cn.com.pano.pano.model.common.PanoMaterial;
import cn.com.pano.pano.model.common.PanoPolygonHotspot;
import cn.com.pano.pano.model.common01.PanoExpositionMapHotspot01Model;
import cn.com.platform.framework.form.AbstractForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 全景图显示Form。
 * 
 * @author ouyangzidou
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Pano0104Form extends AbstractForm implements Serializable {

  private static final long serialVersionUID = 1L;

  // 展览ID
  public String expositionId;
  // 展览名称
  public String expositionName;
  // 全景图ID
  public String panoramaId;
  // 热点序列化结果
  public String hotspotInfoListJson;
  // 热点结果集
  public List<Pano0104Dto> hotspotInfoList;


  
  // 被选中的全景图上的热点ID
  public String selectedHotspotId;
  // 被选中的地图上的热点ID
  public String selectedMiniMapHotspotId;
  // 展览统一的推荐线路点的提示信息
  public String expositionRecommendInfo;
  // 展览统一的场景切换提示信息
  public String expoGoSceneTooltipInfo;
  // 场景下拉框信息
  // public List<CodeValueRecord> panoramaSelectInfo;
  // 场景背景音乐
  public String backGroundSoundPath;
  // 通过小地图迁移时，上个场景的全景图ID
  public String theLastPanoramaId;
  // 全景图首个场景标识
  public String startSceneFlag;
  // 全景图名称
  public String panoramaName;
  // 全景图路径
  public String panoramaPath;
  // 全景图说明
  public String notes;
  // 是否取得全景图
  public boolean showCurrentMapExists;
  // 展览下是否有地图
  public boolean miniMapCheck;
  // 场景的位置坐标
  public String panoramaVlookat;
  public String panoramaHlookat;
  // 地图ID
  public String expositionMapId;
  // 地图路径
  public String expositionMapPath;
  // 热点JSON
//  public String miniMapSpotInfoListJson;
  // 热点结果集
  public List<PanoExpositionMapHotspot01Model> miniMapSpotInfoList;
  // 编辑后热点
  public String spotInfoListSubmitJson;
  // 热点的提示信息
  public String hotspotTooltip;
  // 普通热点下的素材结果集
  public List<PanoMaterial> hotspotImageInfo;
  // 热点ID
  public String hotspotId;

//  public String pointInfoListJson;

  public List<PanoPolygonHotspot> pointList;
  // 传往编辑画面的视角点坐标
  public String positionAthForEdit;
  public String positionAtvForEdit;

  // 热点大小
  public String hotspotScale;
  // 地图热点雷达角度
  public String radarHeading;
  // 场景是否有独立歌曲Check
  public String hadIndependSound;
  // 通过上个导航热点跳转到下一个场景时，用该变量存储上个导航热点的ID
  public String theLastedSceneHotspotId;
  // 当前场景里应该出现的推荐路线点的ID
  public String currentRecommendHotspotId;
  // 判断是否由 pano0110画面跳至 pano0104画面，如果是，保存pageStartRowno
  public String thisFlagIsFromPano0110;

  // 热点的第一张图
  public Pano0104Dto firstImageInfo;
  // 热点的第二张图
  public Pano0104Dto secondImageInfo;

}
