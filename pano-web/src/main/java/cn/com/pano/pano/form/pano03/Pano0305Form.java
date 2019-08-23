package cn.com.pano.pano.form.pano03;

import java.io.Serializable;
import java.util.List;
import cn.com.pano.pano.model.common01.PanoExpositionMap01Model;
import cn.com.platform.framework.form.AbstractForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 为当前场景选择地图的Form
 * 
 * @author ouyangzidou
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Pano0305Form extends AbstractForm implements Serializable {

  private static final long serialVersionUID = 1L;
  // 选中的素材Id
  public String selectedExpositionMapId;
  // 选中的展览地图的名称
  public String selectedExpositionMapName;
  // 素材结果集
  public List<PanoExpositionMap01Model> expositionMapInfo;
  // 图的路径
  public String expositionMapPath;
  // 展览ID
  public String expositionId;
  // 展览名称
  public String expositionName;
  // 父画面当前全景图Id
  public String panoramaId;
  /* 分页显示信息 */
  public String[] pageShowInfos;
  // 通过上个导航热点跳转到下一个场景时，用该变量存储上个导航热点的ID
  public String lasthotspotIdFrom0106;
  // 当前应该显示的推荐路线点ID
  public String currenthotspotIdFrom0106;
  // 已经存在的展览地图的ID
  public String existedExpositionMapId;
}
