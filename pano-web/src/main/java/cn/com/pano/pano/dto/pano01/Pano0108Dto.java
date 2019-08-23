package cn.com.pano.pano.dto.pano01;

import java.io.Serializable;
import cn.com.pano.pano.model.common01.PanoPanoramaHotspot01Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author tangzhenzong
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Pano0108Dto extends PanoPanoramaHotspot01Model implements Serializable {

  private static final long serialVersionUID = 1L;

  // 全景图热点详细信息集合
  public String hotspotPointListJson;

}
