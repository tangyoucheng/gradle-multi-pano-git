package cn.com.platform.common.quartz.util;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.apache.commons.lang3.StringUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.quartz.QuartzJobBean;
import cn.com.platform.common.quartz.constant.ScheduleConstants;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.framework.util.FwStringUtils;
import cn.com.platform.platform.mapper.common01.PlatformJobLog01Mapper;
import cn.com.platform.platform.model.common.PlatformJobCron;
import cn.com.platform.platform.model.common.PlatformJobLog;
import cn.com.platform.util.SpringUtils;

/**
 * 任务处理
 * 
 * @author ruoyi
 * @DisallowConcurrentExecution 禁止并发执行多个相同定义的JobDetail
 */
@DisallowConcurrentExecution
public class ScheduleJob extends QuartzJobBean {
  private static final Logger log = LoggerFactory.getLogger(ScheduleJob.class);

  private ExecutorService service = Executors.newSingleThreadExecutor();

  @Override
  protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    PlatformJobCron job = new PlatformJobCron();
    BeanUtils.copyProperties(context.getMergedJobDataMap().get(ScheduleConstants.TASK_PROPERTIES), job);

    PlatformJobLog jobLog = new PlatformJobLog();
    jobLog.setJobLogId(FwStringUtils.getUniqueId());
    jobLog.setJobId(job.getJobId());
    jobLog.setJobName(job.getJobName());
    jobLog.setJobGroup(job.getJobGroup());
    jobLog.setMethodName(job.getMethodName());
    jobLog.setMethodParams(job.getMethodParams());
    jobLog.setCreateTime(LocalDateTime.now());

    long startTime = System.currentTimeMillis();

    try {
      // 执行任务
      log.info("任务开始执行 - 名称：{} 方法：{}", job.getJobName(), job.getMethodName());
      ScheduleRunnable task =
          new ScheduleRunnable(job.getJobName(), job.getMethodName(), job.getMethodParams());
      Future<?> future = service.submit(task);
      future.get();
      long times = System.currentTimeMillis() - startTime;
      // 任务状态
      jobLog.setStatus(FlagStatus.Enable.toString());
      jobLog.setJobMessage(job.getJobName() + " 总共耗时：" + times + "毫秒");

      log.info("任务执行结束 - 名称：{} 耗时：{} 毫秒", job.getJobName(), times);
    } catch (Exception e) {
      log.info("任务执行失败 - 名称：{} 方法：{}", job.getJobName(), job.getMethodName());
      log.error("任务执行异常  - ：", e);
      long times = System.currentTimeMillis() - startTime;
      jobLog.setJobMessage(job.getJobName() + " 总共耗时：" + times + "毫秒");
      // 任务状态 1：成功 0：失败
      jobLog.setStatus(FlagStatus.Disable.toString());
      jobLog.setExceptionInfo(StringUtils.substring(e.getMessage(), 0, 2000));
    } finally {
      PlatformJobLog01Mapper platformJobLog01Mapper = SpringUtils.getBean(PlatformJobLog01Mapper.class);
      // 保存日志
      platformJobLog01Mapper.insert(jobLog);
    }
  }
}
