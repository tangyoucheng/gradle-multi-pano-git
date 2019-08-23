package cn.com.pano.pano.form.pano02;

import java.math.BigDecimal;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import cn.com.pano.pano.model.common01.PanoPanorama01Model;
import cn.com.platform.framework.form.AbstractForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 全景图首图上传Formクラス.。
 * 
 * @version $Revision$
 * @author ouyangzidu
 * @since 1.0
 */
@Data
@JsonIgnoreProperties({"panoImg"}) // 序列化时忽略的属性名称集合 ，加载类上，给出的属性都不序列化
@EqualsAndHashCode(callSuper = false)
public class Pano0201Form extends AbstractForm {

  private static final long serialVersionUID = 1L;
  // 展览Id
  public String expositionId;
  // 全景图ID
  public String panoramaId;
  // 全景图名称
  public String panoramaName;
  // 全景图路径
  public String panoramaPath;
  // 缩略图显示信息
  public String thumbNote;
  // notes备注
  public String notes;

  //  音乐素材的ID
  public String panoramaSoundId;
  // 场景背景音乐名
  public String panoramaSoundName;
  
  // TODO 废弃  是否为首个场景
  public String startSceneFlag;
  //  TODO 废弃 是否选择了音乐
  public String panoMusicSelect;
  // 场景显示顺序
  public BigDecimal panoramaSortKey;
  //  TODO 废弃  由选取声音素材画面传回的音乐ID
  public String materialIdFromPano0208;

  /** 图片(前台传后台用)。 */
  public MultipartFile[] panoImg;
  public String deleteImgPath;
  /** 图片(后台传前台用)。 */
  public PanoPanorama01Model[] panoImgList;
}
