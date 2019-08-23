package cn.com.pano.pano.form.pano01;

import java.io.Serializable;
import java.util.List;
import cn.com.pano.pano.dto.pano01.Pano0104Dto;
import cn.com.pano.pano.model.common.PanoExpositionMapHotspot;
import cn.com.pano.pano.model.common.PanoMaterial;
import cn.com.pano.pano.model.common.PanoPolygonHotspot;
import cn.com.platform.framework.common.CodeValueRecord;
import cn.com.platform.framework.form.AbstractForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 展览编辑Formクラス.
 * 
 * @version $Revision$
 * @author ouyangzidu
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Pano0103Form extends AbstractForm implements Serializable {

  private static final long serialVersionUID = 1L;

  /* 展览编号 */
  public String expositionId;
  /* 文件夹编号 */
  public String expositionFolderId;
  /* 展览名称 */
  public String expositionName;
  /* 展览备注 */
  public String expositionNotes;
  /* 展览音乐 */
  public String expositionSoundName;
  /* 展览类型 */
  public String expositionType;
  /* 展览类型选框 */
  public List<CodeValueRecord> expositionTypeList;
  /* 展览状态 */
  public String expositionStatus;
  /* 展览状态选框 */
  public List<CodeValueRecord> expositionStatusList;
  /* 展览状态说明 */
  public String expositionStatusNotes;
  // 展览开展时间
  public String expositionStartDate;
  // 展览撤展时间
  public String expositionEndDate;

  /* 展览VR标识单选框 */
  public List<CodeValueRecord> vrFlagList;
  /* 展览VR标识 */
  public String vrFlag;
}
