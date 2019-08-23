package cn.com.platform.platform.model.common01;

import cn.com.platform.platform.model.common.PlatformJobRepeat;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 重复任务dto
 * 
 * @author 王笃鹏
 * @date 2018-08-20
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class PlatformJobRepeat01Model extends PlatformJobRepeat {
  /** 任务状态。 */
  public String jobStatusName;

  public String getJobStatusName() {
    return jobStatusName;
  }

}
