package cn.com.platform.platformz.form.platformz99;

import java.time.LocalDateTime;
import cn.com.platform.framework.form.AbstractForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 触发器设定 重复任务form。
 * 
 * @author 王笃鹏
 * @date 2019-01-17
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Platformz99060101Form extends AbstractForm {
  private static final long serialVersionUID = 1L;
  /** 任务Id。 */
  public String jobId;
  /** 重复次数。 */
  public String repeatCount;
  /** 重复间隔。 */
  public String repeatInterval;
  /** 任务开始时间。 */
  public LocalDateTime startDate;
}
