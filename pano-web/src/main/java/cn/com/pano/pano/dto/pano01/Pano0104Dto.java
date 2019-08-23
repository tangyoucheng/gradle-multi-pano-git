package cn.com.pano.pano.dto.pano01;

import java.io.Serializable;
import cn.com.pano.pano.model.common01.PanoPanoramaHotspot01Model;
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
public class Pano0104Dto extends PanoPanoramaHotspot01Model implements Serializable {

  private static final long serialVersionUID = 1L;

  // 视频的路径
  public String videoPath;
}
