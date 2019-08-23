package cn.com.platform.platform.model.common01;

import cn.com.platform.platform.model.common.PlatformJobCron;

/**
 * 定时任务dto
 * 
 * @author 王笃鹏
 * @date 2018-08-20
 *
 */
public class PlatformJobCron01Model extends PlatformJobCron {
  /** 任务状态。 */
  public String jobStatusName;

  public String getJobStatusName() {
    return jobStatusName;
  }
}
