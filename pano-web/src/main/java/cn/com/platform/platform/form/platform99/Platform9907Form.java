package cn.com.platform.platform.form.platform99;

import java.util.List;
import cn.com.platform.framework.common.CodeValueRecord;
import cn.com.platform.framework.form.AbstractForm;
import cn.com.platform.platform.model.common.PlatformJobLog;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 任务日志form
 * 
 * @author 王笃鹏
 * @date 2019-01-17
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Platform9907Form extends AbstractForm {

  /** 任务名称。 */
  public String selectedJobName;
  /** 方法名称。 */
  public String selectedMethodName;
  /** 执行状态。 */
  public String selectedStatus;
  public List<CodeValueRecord> statusList;

  /** 结果集。 */
  public List<PlatformJobLog> jobLogList;
}
