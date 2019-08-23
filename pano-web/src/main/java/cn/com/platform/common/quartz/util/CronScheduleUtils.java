package cn.com.platform.common.quartz.util;

import java.util.Date;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.NumberUtils;
import cn.com.platform.common.quartz.constant.ScheduleConstants;
import cn.com.platform.common.quartz.domain.CronJob;
import cn.com.platform.common.quartz.util.TaskException.Code;
import cn.com.platform.platform.common.code.JobStatus;

/**
 * 定时任务工具类
 * 
 * @author ruoyi
 *
 */
public class CronScheduleUtils {
  private static final Logger log = LoggerFactory.getLogger(CronScheduleUtils.class);

  /**
   * 获取触发器key
   */
  public static TriggerKey getTriggerKey(String jobId) {
    return TriggerKey.triggerKey(ScheduleConstants.TASK_CLASS_NAME + jobId);
  }

  /**
   * 获取jobKey
   */
  public static JobKey getJobKey(String jobId) {
    return JobKey.jobKey(ScheduleConstants.TASK_CLASS_NAME + jobId);
  }

  /**
   * 获取表达式触发器
   */
  public static CronTrigger getCronTrigger(Scheduler scheduler, String jobId) {
    try {
      return (CronTrigger) scheduler.getTrigger(getTriggerKey(jobId));
    } catch (SchedulerException e) {
      log.error("getCronTrigger 异常：", e);
    }
    return null;
  }

  /**
   * 创建定时任务
   */
  public static void createScheduleJob(Scheduler scheduler, CronJob job) {
    try {
      // 验证触发器
      if (!triggerValidate(job.getCronExpression())) {
        return;
      }

      // 构建job信息
      JobDetail jobDetail =
          JobBuilder.newJob(ScheduleJob.class).withIdentity(getJobKey(job.getJobId())).build();

      // 表达式调度构建器
      CronScheduleBuilder cronScheduleBuilder =
          CronScheduleBuilder.cronSchedule(job.getCronExpression());
      cronScheduleBuilder = handleCronScheduleMisfirePolicy(job, cronScheduleBuilder);

      // 按新的cronExpression表达式构建一个新的trigger
      CronTrigger trigger = TriggerBuilder.newTrigger()
          .withPriority(NumberUtils.parseNumber(job.getPriority(), Integer.class))
          .withIdentity(getTriggerKey(job.getJobId())).withSchedule(cronScheduleBuilder).build();

      // 放入参数，运行时的方法可以获取
      jobDetail.getJobDataMap().put(ScheduleConstants.TASK_PROPERTIES, job);

      scheduler.scheduleJob(jobDetail, trigger);

      // 暂停任务
      if (job.getJobStatus().equals(JobStatus.Pause.toString())) {
        pauseJob(scheduler, job.getJobId());
      }
    } catch (SchedulerException e) {
      log.error("createScheduleJob 异常：", e);
    } catch (TaskException e) {
      log.error("createScheduleJob 异常：", e);
    }
  }

  /**
   * 更新定时任务
   */
  public static void updateScheduleJob(Scheduler scheduler, CronJob job) {
    try {
      // 验证触发器
      if (!triggerValidate(job.getCronExpression())) {
        return;
      }

      TriggerKey triggerKey = getTriggerKey(job.getJobId());

      // 表达式调度构建器
      CronScheduleBuilder cronScheduleBuilder =
          CronScheduleBuilder.cronSchedule(job.getCronExpression());
      cronScheduleBuilder = handleCronScheduleMisfirePolicy(job, cronScheduleBuilder);

      CronTrigger trigger = getCronTrigger(scheduler, job.getJobId());

      // 按新的cronExpression表达式重新构建trigger
      trigger = trigger.getTriggerBuilder()
          .withPriority(NumberUtils.parseNumber(job.getPriority(), Integer.class))
          .withIdentity(triggerKey).withSchedule(cronScheduleBuilder).build();

      // 参数
      trigger.getJobDataMap().put(ScheduleConstants.TASK_PROPERTIES, job);

      scheduler.rescheduleJob(triggerKey, trigger);

      // 暂停任务
      if (job.getJobStatus().equals(JobStatus.Pause.toString())) {
        pauseJob(scheduler, job.getJobId());
      }

    } catch (SchedulerException e) {
      log.error("SchedulerException 异常：", e);
    } catch (TaskException e) {
      log.error("SchedulerException 异常：", e);
    }
  }

  /**
   * 立即执行任务
   */
  public static int run(Scheduler scheduler, CronJob job) {
    int rows = 0;
    try {
      // 参数
      JobDataMap dataMap = new JobDataMap();
      dataMap.put(ScheduleConstants.TASK_PROPERTIES, job);

      scheduler.triggerJob(getJobKey(job.getJobId()), dataMap);
      rows = 1;
    } catch (SchedulerException e) {
      log.error("run 异常：", e);
    }
    return rows;
  }

  /**
   * 暂停任务
   */
  public static void pauseJob(Scheduler scheduler, String string) {
    try {
      scheduler.pauseJob(getJobKey(string));
    } catch (SchedulerException e) {
      log.error("pauseJob 异常：", e);
    }
  }

  /**
   * 恢复任务
   */
  public static void resumeJob(Scheduler scheduler, String jobId) {
    try {
      scheduler.resumeJob(getJobKey(jobId));
    } catch (SchedulerException e) {
      log.error("resumeJob 异常：", e);
    }
  }

  /**
   * 删除定时任务
   */
  public static void deleteScheduleJob(Scheduler scheduler, String jobId) {
    try {
      scheduler.deleteJob(getJobKey(jobId));
    } catch (SchedulerException e) {
      log.error("deleteScheduleJob 异常：", e);
    }
  }

  public static CronScheduleBuilder handleCronScheduleMisfirePolicy(CronJob job,
      CronScheduleBuilder cb) throws TaskException {
    switch (job.getMisfirePolicy()) {
      case ScheduleConstants.MISFIRE_DEFAULT:
        return cb;
      case ScheduleConstants.CRON_MISFIRE_IGNORE_MISFIRES:
        return cb.withMisfireHandlingInstructionIgnoreMisfires();
      case ScheduleConstants.CRON_MISFIRE_FIRE_AND_PROCEED:
        return cb.withMisfireHandlingInstructionFireAndProceed();
      case ScheduleConstants.CRON_MISFIRE_DO_NOTHING:
        return cb.withMisfireHandlingInstructionDoNothing();
      default:
        throw new TaskException("The task misfire policy '" + job.getMisfirePolicy()
            + "' cannot be used in cron schedule tasks", Code.CONFIG_ERROR);
    }
  }

  /**
   * 验证触发器
   */
  public static boolean triggerValidate(String cronExpression) {
    CronTriggerImpl trigger = new CronTriggerImpl();
    try {
      trigger.setCronExpression(cronExpression);
      Date date = trigger.computeFirstFireTime(null);
      return date != null && date.after(new Date());
    } catch (Exception e) {
      log.error("cronExpression 异常：", e);
      return false;
    }
  }
}
