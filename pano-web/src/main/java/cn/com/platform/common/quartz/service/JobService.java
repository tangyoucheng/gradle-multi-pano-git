package cn.com.platform.common.quartz.service;

import javax.annotation.PostConstruct;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.com.platform.platform.mapper.common01.PlatformJobCron01Mapper;
import cn.com.platform.platform.mapper.common01.PlatformJobRepeat01Mapper;

/**
 * job调度信息 服务层
 * 
 * @author ruoyi
 */
@Service
public class JobService {
  @Autowired
  private Scheduler scheduler;

  @Autowired
  private PlatformJobCron01Mapper platformJobCron01Mapper;
  @Autowired
  private PlatformJobRepeat01Mapper platformJobRepeat01Mapper;

  /**
   * 项目启动时，初始化定时器
   */
  @PostConstruct
  public void init() {
    // 定时任务
//    List<PlatformJobF01Dto> jobList = ciszJob01Mapper.selectJobAll();
//    for (PlatformJobF01Dto job : jobList) {
//      CronTrigger cronTrigger = CronScheduleUtils.getCronTrigger(scheduler, job.getJobId());
//      // 如果不存在，则创建
//      CronJob cronJob = new CronJob();
//      BeanUtils.copyProperties(job, cronJob);
//      if (cronTrigger == null) {
//        CronScheduleUtils.createScheduleJob(scheduler, cronJob);
//      } else {
//        CronScheduleUtils.updateScheduleJob(scheduler, cronJob);
//      }
//    }
    // 重复任务
//    List<PlatformJobRepeatF01Dto> jobRepeatList = platformJobRepeat01Mapper.selectJobAll();
//    for (PlatformJobRepeatF01Dto job : jobRepeatList) {
//      SimpleTrigger simpleTrigger = RepeatScheduleUtils.getSimpleTrigger(scheduler, job.getJobId());
//      // 如果不存在，则创建
//      RepeatJob repeatJob = new RepeatJob();
//      BeanUtils.copyProperties(job, repeatJob);
//      if (simpleTrigger == null) {
//        RepeatScheduleUtils.createScheduleJob(scheduler, repeatJob);
//      } else {
//        RepeatScheduleUtils.updateScheduleJob(scheduler, repeatJob);
//      }
//    }
  }
}
