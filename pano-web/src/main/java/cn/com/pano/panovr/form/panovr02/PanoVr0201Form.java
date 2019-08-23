package cn.com.pano.panovr.form.panovr02;

import java.math.BigDecimal;
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
@EqualsAndHashCode(callSuper = false)
public class PanoVr0201Form extends AbstractForm {

  private static final long serialVersionUID = 1L;
  // 展览Id
  public String expositionId;
  // 全景图ID
  public String panoramaId;
  // 全景图名称
  public String panoramaName;
  // 全景图路径
  public String panoramaPath;
  // notes备注
  public String notes;
  // 是否为首个场景
  public String startSceneFlag;
  // 是否选择了音乐
  public String panoMusicSelect;
  // 场景显示顺序
  public BigDecimal panoramaSortKey;
  // 由选取声音素材画面传回的音乐ID
  public String materialIdFromPano0208;

  public String subFolderName;
}
