package cn.com.pano.pano.form.pano03;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import cn.com.pano.pano.dto.pano01.Pano0104Dto;
import cn.com.pano.pano.form.pano01.Pano0104Form;
import cn.com.pano.pano.model.common.PanoExpositionMapHotspot;
import cn.com.pano.pano.model.common.PanoMaterial;
import cn.com.pano.pano.model.common.PanoPolygonHotspot;
import cn.com.pano.pano.model.common01.PanoExpositionMap01Model;
import cn.com.pano.pano.model.common01.PanoPanorama01Model;
import cn.com.platform.framework.common.CodeValueRecord;
import cn.com.platform.framework.form.AbstractForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 场景上地图登录Form
 * 
 * @author ouyangzidu
 * 
 */
@Data
@JsonIgnoreProperties({"mapImg"}) // 序列化时忽略的属性名称集合 ，加载类上，给出的属性都不序列化
@EqualsAndHashCode(callSuper = false)
public class Pano0304Form extends AbstractForm {

  private static final long serialVersionUID = 1L;
  /* 地图ID */
  public String expositionMapId;
  /* 地图名称 */
  public String expositionMapName;
  /* 地图相对路径 */
  public String expositionMapPath;
  /* 备注 */
  public String notes;
  // 展览ID
  public String expositionId;
  // 展览名称
  public String expositionName;
  // 父画面当前全景图Id
  public String panoramaId;
  // 通过上个导航热点跳转到下一个场景时，用该变量存储上个导航热点的ID
  public String lasthotspotIdFrom0106;
  // 当前应该显示的推荐路线点ID
  public String currenthotspotIdFrom0106;
  // 已经存在的展览地图ID
  public String existedExpositionMapId;

  /** 图片(前台传后台用)。 */
  public MultipartFile[] mapImg;
  public String deleteImgPath;
  /** 图片(后台传前台用)。 */
  public PanoExpositionMap01Model[] mapImgList;

}
