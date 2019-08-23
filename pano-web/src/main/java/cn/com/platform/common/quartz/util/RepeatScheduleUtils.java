package cn.com.platform.common.quartz.util;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.NumberUtils;
import org.springframework.util.ObjectUtils;
import cn.com.platform.common.quartz.constant.ScheduleConstants;
import cn.com.platform.common.quartz.domain.RepeatJob;
import cn.com.platform.common.quartz.util.TaskException.Code;
import cn.com.platform.platform.common.code.JobStatus;

/**
 * 重复任务工具类
 * 
 * @author 王笃鹏
 * @date 2019-01-25
 *
 */
public class RepeatScheduleUtils {
  private static final Logger log = LoggerFactory.getLogger(RepeatScheduleUtils.class);

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
   * 获取触发器
   */
  public static SimpleTrigger getSimpleTrigger(Scheduler scheduler, String jobId) {
    try {
      return (SimpleTrigger) scheduler.getTrigger(getTriggerKey(jobId));
    } catch (SchedulerException e) {
      log.error("getRepeatTrigger 异常：", e);
    }
    return null;
  }

  /**
   * 创建重复任务
   */
  public static void createScheduleJob(Scheduler scheduler, RepeatJob job) {
    try {

      // 构建job信息
      JobDetail jobDetail =
          JobBuilder.newJob(ScheduleJob.class).withIdentity(getJobKey(job.getJobId())).build();

      // 调度构建器
      SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule();
      if (ObjectUtils.isEmpty(job.getRepeatCount())) {
        simpleScheduleBuilder = SimpleScheduleBuilder
            .repeatSecondlyForever(NumberUtils.parseNumber(job.getRepeatInterval(), Integer.class));
      } else {
        simpleScheduleBuilder = SimpleScheduleBuilder.repeatSecondlyForTotalCount(
            NumberUtils.parseNumber(job.getRepeatCount(), Integer.class),
            NumberUtils.parseNumber(job.getRepeatInterval(), Integer.class));
      }
      simpleScheduleBuilder = handleRepeatScheduleMisfirePolicy(job, simpleScheduleBuilder);

      // 构建一个新的trigger
      SimpleTrigger trigger = TriggerBuilder.newTrigger()
          .withPriority(NumberUtils.parseNumber(job.getPriority(), Integer.class))
          .startAt(job.getStartDate()).withIdentity(getTriggerKey(job.getJobId()))
          .withSchedule(simpleScheduleBuilder).build();

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
  public static void updateScheduleJob(Scheduler scheduler, RepeatJob job) {
    try {
      TriggerKey triggerKey = getTriggerKey(job.getJobId());

      // 表达式调度构建器
      SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder
          .repeatSecondlyForTotalCount(NumberUtils.parseNumber(job.getRepeatCount(), Integer.class),
              NumberUtils.parseNumber(job.getRepeatInterval(), Integer.class));
      simpleScheduleBuilder = handleRepeatScheduleMisfirePolicy(job, simpleScheduleBuilder);

      SimpleTrigger trigger = getSimpleTrigger(scheduler, job.getJobId());

      // 重新构建trigger
      trigger = trigger.getTriggerBuilder()
          .withPriority(NumberUtils.parseNumber(job.getPriority(), Integer.class))
          .startAt(job.getStartDate()).withIdentity(triggerKey).withSchedule(simpleScheduleBuilder)
          .build();

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
  public static int run(Scheduler scheduler, RepeatJob job) {
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

  public static SimpleScheduleBuilder handleRepeatScheduleMisfirePolicy(RepeatJob job,
      SimpleScheduleBuilder cb) throws TaskException {
    switch (job.getMisfirePolicy()) {
      case ScheduleConstants.MISFIRE_DEFAULT:
        return cb;
      case ScheduleConstants.REPEAT_MISFIRE_FIRE_NOW:
        return cb.withMisfireHandlingInstructionFireNow();
      case ScheduleConstants.REPEAT_MISFIRE_IGNORE_MISFIRES:
        return cb.withMisfireHandlingInstructionIgnoreMisfires();
      case ScheduleConstants.REPEAT_MISFIRE_NEXTWITH_EXISTINGCOUNT:
        return cb.withMisfireHandlingInstructionNextWithExistingCount();
      case ScheduleConstants.REPEAT_MISFIRE_NEXTWITH_REMAININGCOUNT:
        return cb.withMisfireHandlingInstructionNextWithRemainingCount();
      case ScheduleConstants.REPEAT_MISFIRE_NOWWITH_EXISTINGCOUNT:
        return cb.withMisfireHandlingInstructionNowWithExistingCount();
      case ScheduleConstants.REPEAT_MISFIRE_NOWWITH_REMAININGCOUNT:
        return cb.withMisfireHandlingInstructionNowWithRemainingCount();
      default:
        throw new TaskException("The task misfire policy '" + job.getMisfirePolicy()
            + "' cannot be used in repeat schedule tasks", Code.CONFIG_ERROR);
    }
  }
}
