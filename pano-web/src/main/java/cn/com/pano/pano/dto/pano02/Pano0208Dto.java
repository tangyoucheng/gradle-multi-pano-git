package cn.com.pano.pano.dto.pano02;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import cn.com.pano.pano.dto.pano01.Pano0104Dto;
import cn.com.pano.pano.form.pano01.Pano0104Form;
import cn.com.pano.pano.model.common.PanoExpositionMapHotspot;
import cn.com.pano.pano.model.common.PanoMaterial;
import cn.com.pano.pano.model.common.PanoPolygonHotspot;
import cn.com.platform.framework.common.CodeValueRecord;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 选择热点图片Dto
 * 
 * @author ouyangzidou
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Pano0208Dto extends PanoMaterial implements Serializable {

  private static final long serialVersionUID = 1L;

  // 判断该素材是否是gif图并是否有生产对应的png图
  public String hasPngImage;
  // 判断热点的sortKey
  public BigDecimal sortKey;

}
