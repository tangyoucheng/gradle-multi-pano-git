package cn.com.pano.pano.dto.pano02;

import java.util.List;
import cn.com.pano.pano.model.common01.PanoPanoramaHotspot01Model;
import cn.com.pano.pano.model.common01.PanoPolygonHotspot01Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 为热点添加素材Dto。
 * 
 * @author ouyangzidou
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Pano0203Dto extends PanoPanoramaHotspot01Model{

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
}
