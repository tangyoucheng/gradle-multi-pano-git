package cn.com.pano.pano.form.pano02;

import java.util.List;
import cn.com.pano.pano.dto.pano01.Pano0104Dto;
import cn.com.pano.pano.form.pano01.Pano0104Form;
import cn.com.pano.pano.model.common.PanoExpositionMapHotspot;
import cn.com.pano.pano.model.common.PanoMaterial;
import cn.com.pano.pano.model.common.PanoPolygonHotspot;
import cn.com.platform.framework.common.CodeValueRecord;
import cn.com.platform.framework.form.AbstractForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Eq020801Formクラス.
 * 
 * @version $Revision$
 * @author yaowei
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Pano020101Form extends AbstractForm {

  private static final long serialVersionUID = 1L;
  // 全景图ID
  public String mapId;
  // 全景图名称
  public String mapName;
  /* notes备注 */
  public String notes;


}
