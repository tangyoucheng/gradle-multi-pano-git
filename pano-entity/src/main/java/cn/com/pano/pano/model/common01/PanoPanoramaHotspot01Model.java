package cn.com.pano.pano.model.common01;

import java.util.List;
import com.google.common.collect.Lists;
import cn.com.pano.pano.model.common.PanoPanoramaHotspot;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PanoPanoramaHotspot01Model extends PanoPanoramaHotspot {

  // 素材ID
  public String materialId;
  // 素材路径
  public String materialPath;
  // 热点的素材图结果集
  public List<String> hotspotMaterialPath = Lists.newArrayList();
  // 多边形热点对应坐标点
  public List<PanoPolygonHotspot01Model> pointList = Lists.newArrayList();
  // gif宽
  public String gifWidth;
  // gif高
  public String gifHeight;
  // gif帧数
  public String gifFrameCount;
  // gif间隔
  public String gifDelayTime;
  

  // pano0203页面追加 SATRT
  // 原料类型ID
  public String materialTypeId;
  // 热点图的路径
  public String hotspotImagePath;
  // 判断该素材是否是gif图并是否有生产对应的png图
  public String hasPngImage;
  // 音乐热点第二张图的id
  public String secondHotspotImageId;
  //
  public String firstHotspotImageId;
  //
  public String firstSortkey;
  //
  public String secondSortkey;
  // pano0203页面追加 END

}
