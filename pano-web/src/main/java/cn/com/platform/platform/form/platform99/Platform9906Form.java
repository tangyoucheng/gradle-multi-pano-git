package cn.com.platform.platform.form.platform99;

import java.util.List;
import cn.com.platform.framework.common.CodeValueRecord;
import cn.com.platform.framework.form.AbstractForm;
import cn.com.platform.platform.model.common01.PlatformJobRepeat01Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 重复任务form
 * 
 * @author 王笃鹏
 * @date 2019-01-17
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Platform9906Form extends AbstractForm {

  /** 任务名称。 */
  public String selectedJobName;
  /** 方法名称。 */
  public String selectedMethodName;
  /** 任务状态。 */
  public String selectedJobStatus;
  public List<CodeValueRecord> jobStatusList;

  /** 结果集。 */
  public List<PlatformJobRepeat01Model> jobList;
}
