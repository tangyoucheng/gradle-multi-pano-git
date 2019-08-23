package cn.com.pano.pano.model.common01;

import java.math.BigDecimal;
import cn.com.pano.pano.model.common.PanoMaterial;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class PanoMaterial01Model extends PanoMaterial {
  // 热点上已有素材的sortKey
  public BigDecimal sortKey;
  // 素材名
  public String label;
  // 素材ID
  public String value;
  // 是否选中
  public boolean selected;
  // 排序
  public BigDecimal index;
  // 热点ID
  public String hotspotId;
  // 图文选项是否可以选择
  public boolean disabled;
  
  // 判断该素材是否是gif图并是否有生产对应的png图
  public String hasPngImage;
  
}
