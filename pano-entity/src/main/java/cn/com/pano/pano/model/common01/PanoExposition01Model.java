package cn.com.pano.pano.model.common01;

import cn.com.pano.pano.model.common.PanoExposition;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PanoExposition01Model extends PanoExposition {
  /* 展览场景的件数 */
  public int panoramaCount;

  /* 展览操作权限 */
  public String expoEditAcess;

  /* 资源操作权限 */
  public String folderEditAcess;

  /* 场景操作权限 */
  public String sceneEditAcess;

  /* 热点Id */
  public String hotspotId;
  /* 素材Path */
  public String materialPath;
  /* 文字信息 */
  public String textInfo;
  /* 素材ID */
  public String materialId;

  /* 展览状态名 */
  public String expositionStatusName;
  /* 展览类型名 */
  public String expositionTypeName;
}
