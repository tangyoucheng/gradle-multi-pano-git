package cn.com.pano.pano.form.pano02;

import java.io.Serializable;
import cn.com.platform.framework.form.AbstractForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 为导航热点添加全景图Form。
 * 
 * @author ouyangzidou
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Pano0204Form extends AbstractForm implements Serializable {

  private static final long serialVersionUID = 1L;
  // 展览ID
  public String expositionId;
  // 展览名称
  public String expositionName;

  // 父画面当前全景图Id
  public String currentPanoramaId;
  // 被选中的热点ID
  public String selectedHotspotId;
  
  // 被选中全景图ID
  public String selectedPanoramaId;
  
}
