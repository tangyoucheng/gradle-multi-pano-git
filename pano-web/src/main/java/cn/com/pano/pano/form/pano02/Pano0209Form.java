package cn.com.pano.pano.form.pano02;

import java.io.Serializable;
import java.util.List;
import cn.com.pano.pano.dto.pano02.Pano020901Dto;
import cn.com.pano.pano.dto.pano02.Pano020902Dto;
import cn.com.platform.framework.common.CodeValueRecord;
import cn.com.platform.framework.form.AbstractForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 热点大小编辑Form。
 * 
 * @author tangzhenzong
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Pano0209Form extends AbstractForm implements Serializable {
  private static final long serialVersionUID = 1L;
  
  // 展览ID
  public String expositionIdForPano0209;
  // 展览名称
  public String expositionName;
  // 全景图ID
  public String panoramaIdForPano0209;
  // 全景图名称
  public String panoramaName;
  // 全景图路径
  public String panoramaPath;
  // 选中热点的ID
  public String pano0209hotspotId;
  // 是否为推荐线路点
  public String recommendFlag;
  // 热点上的推荐信息
  public String recommendInfo;
  // 展览下统一的推荐信息
  public String expoRecommendInfo;
  // 导航热点下的信息
  public String expoHotspotTooltipInfo;
  // 导航热点上的统一提示信息
  public String expoGoSceneInfo;
  // 热点大小信息集
  public List<CodeValueRecord> hotspotSizeInfo;
  // 热点大小值
  public String hotspotScale;
  // 热点类型
  public String pano0209HotspotType;
  // // 热点图片路径
  // public String hotspotImagePath;
  // 热点坐标
  public String hotspotAth;
  public String hotspotAtv;
  // 视角参数
  public String positionAthForEdit;
  public String positionAtvForEdit;
  // 浮动信息层ID
  public String pano0209flowFileId;
  // 浮动信息层类型
  public String pano0209flowFileType;
  // 图片浮动信息路径
  public String pano0209flowFilePath;
  // 文字浮动信息内容
  public String pano0209flowFileInfo;
  // 浮动层坐标
  public String pano0209layerX;
  public String pano0209layerY;
  // 所传数据是否来自Pano0105
  public String commandTypeFromPano0105;
  // 工具条按钮集合
  public List<Pano020901Dto> buttonsInfo;
  // 跳转到此场景时上个场景的导航点ID
  public String pano0209TheLastedSceneHotspotId;
  public String lastedHotspotIdFrom0105;
  // 设置了推荐路线点后传回该点ID
  public String pano0209RecommendHotspotId;
  public String currentHotspotIdFrom0105;
  // 来自热点编辑画面的热点坐标和热点imageUrl
  public String pano0203HotspotName;
  public String pano0203HotspotAth;
  public String pano0203HotspotAtv;

  public String pano0203MusicHotspot;
  public String seconfhotspotImageIdPano0203;
  public String firsthotspotImageIdPano0203;
  public String firstSortKeyPano0203;
  public String secondSortKeyPano0203;

  // 热点的第一张图
  public Pano020902Dto firstImageInfo;
  public String firstImageInfoJson;
  // 热点的第二张图
  public Pano020902Dto secondImageInfo;
  public String secondImageInfoJson;

  // 单张图热点信息
  public Pano020902Dto hotspotImageInfo;
  public String hotspotImageInfoJson;

  public String pano0203HotspotImageId;
  // pano0203操作标识
  public String pano0203OperateFlag;

  // 编辑后的工具条所选按钮
  public String selectedButtons;
}
