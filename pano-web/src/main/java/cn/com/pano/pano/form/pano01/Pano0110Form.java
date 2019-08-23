package cn.com.pano.pano.form.pano01;

import java.io.Serializable;
import java.util.List;
import cn.com.pano.pano.model.common01.PanoExposition01Model;
import cn.com.platform.framework.common.CodeValueRecord;
import cn.com.platform.framework.form.AbstractForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 展厅管理。
 * 
 * @author 唐友成
 * @date 2019-08-05
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Pano0110Form extends AbstractForm implements Serializable {
  private static final long serialVersionUID = 1L;

  /* 展览ID 检索条件 */
  public String expositionId;
  /* 展览名称 检索条件 */
  public String expositionName;
  /* 展览开展时间 检索条件 */
  public String expositionStartDate;
  /* 展览闭展时间 检索条件 */
  public String expositionEndDate;
  /* 展览状态 检索条件 */
  public String expositionStatus;
  // 展览状态下拉框信息
  public List<CodeValueRecord> expositionStatusList;
  /* 展览类型 检索条件 */
  public String expositionType;
  // 展览类型下拉框信息
  public List<CodeValueRecord> expositionTypeList;
  /* 展览信息结果集 */
  public List<PanoExposition01Model> expositionInfo;
  /* 明细行中被选中的展览ID */
  public String selectedExpositionId;
  /* 由Pano0104返回的pageStartRowNo */
  public String pageStartRowNoFromPano0104;

}
