package cn.com.platform.platformz.form.platformz99;

import java.util.List;
import cn.com.platform.framework.common.CodeValueRecord;
import cn.com.platform.framework.form.AbstractForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 触发器设定 指定时间form
 * 
 * @author 王笃鹏
 * @date 2019-01-17
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Platformz99050101Form extends AbstractForm {
  /** 任务Id。 */
  public String jobId;
  /** 年 */
  public String years;
  /** 月 */
  public String months;
  /** 日期 */
  public String daysOfMonth;
  /** 星期 */
  public String daysOfWeek;
  /** 星期list */
  public List<CodeValueRecord> weekList;
  /** 時 */
  public String hours;
  /** 分 */
  public String minutes;
  /** 秒 */
  public String seconds;
}
