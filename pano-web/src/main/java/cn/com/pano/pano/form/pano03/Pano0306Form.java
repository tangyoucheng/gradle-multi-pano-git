package cn.com.pano.pano.form.pano03;

import java.io.Serializable;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import cn.com.pano.pano.model.common01.PanoExpositionMap01Model;
import cn.com.platform.framework.form.AbstractForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@JsonIgnoreProperties({"mapImg"}) // 序列化时忽略的属性名称集合 ，加载类上，给出的属性都不序列化
@EqualsAndHashCode(callSuper = false)
public class Pano0306Form extends AbstractForm implements Serializable {

  private static final long serialVersionUID = 1L;
  // 展览ID
  public String pano0306ExpositionId;
  // 展览地图ID
  public String pano0306ExpositionMapId;
  // 展览地图名称
  public String pano0306ExpositionMapName;
  // 展览地图名称
  public String pano0306ExpositionMapPath;

  /** 图片(前台传后台用)。 */
  public MultipartFile[] mapImg;
  public String deleteImgPath;
  /** 图片(后台传前台用)。 */
  public PanoExpositionMap01Model[] mapImgList;

}
