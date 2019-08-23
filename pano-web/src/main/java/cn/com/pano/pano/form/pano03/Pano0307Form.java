package cn.com.pano.pano.form.pano03;

import java.io.Serializable;
import java.util.List;
import cn.com.pano.pano.model.common.PanoPanorama;
import cn.com.platform.framework.form.AbstractForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 为地图上热点选择场景全景图的Form
 * 
 * @author ouyangzidou
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Pano0307Form extends AbstractForm implements Serializable {

  private static final long serialVersionUID = 1L;
  // 一览中选中的全景图Id
  public String selectedPanoramaId;
  // 小地图上选中的热点ID
  public String selectedHotspotId;
  // 全景图结果集
  public List<PanoPanorama> panoramaInfo;
  // 全景图的路径
  public String panoramaPath;
  // 展览ID
  public String expositionId;
  // 展览名称
  public String expositionName;
  // 父画面当前全景图Id
  public String currentPanoramaId;
  /* 分页显示信息 */
  public String[] pageShowInfos;
}
