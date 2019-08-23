package cn.com.pano.pano.dto.pano02;

import java.io.Serializable;
import java.math.BigDecimal;
import cn.com.pano.pano.model.common.PanoMaterial;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 检索热点已有素材的dto。
 * 
 * @author ouyangzidou
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Pano0205Dto extends PanoMaterial implements Serializable {
  private static final long serialVersionUID = 1L;

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
}
