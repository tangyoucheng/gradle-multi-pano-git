package cn.com.pano.pano.dto.pano03;

import java.io.Serializable;
import cn.com.pano.pano.model.common.PanoMaterial;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 素材上传Dto。
 * 
 * @author ouyangzidou
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Pano0301Dto extends PanoMaterial implements Serializable {

  private static final long serialVersionUID = 1L;

  // 缩略图文件
  public String thumbFile;
  // 上传的文件
  public String uploadFile;
}
