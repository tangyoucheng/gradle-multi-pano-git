package cn.com.platform.platform.form.platform99;

import java.util.List;
import cn.com.platform.framework.common.CodeValueRecord;
import cn.com.platform.framework.form.AbstractForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 新建定时任务form
 * 
 * @author 王笃鹏
 * @date 2019-01-17
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Platform990501Form extends AbstractForm {

  /** 任务Id。 */
  public String jobId;
  /** 任务名称。 */
  public String jobName;
  /** 任务组名。 */
  public String jobGroup;
  /** 方法名称。 */
  public String methodName;
  /** 方法参数。 */
  public String methodParams;
  /** cron表达式。 */
  public String cronExpression;
  /** 触发器说明。 */
  public String triggerRemark;
  /** 优先级。 */
  public String priority;
  /** 执行策略。 */
  public String misfirePolicy;
  /** 任务状态。 */
  public String jobStatus;
  public List<CodeValueRecord> jobStatusList;
  /** 备注。 */
  public String remark;
  /** 触发器 Json。 */
  public String triggerJson;
}
