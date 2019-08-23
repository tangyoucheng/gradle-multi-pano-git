package cn.com.platform.platformz.service.platformz99;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import com.google.gson.Gson;
import cn.com.platform.common.quartz.domain.CronJob;
import cn.com.platform.common.quartz.trigger.impl.DatetimeTrigger;
import cn.com.platform.common.quartz.util.CronScheduleUtils;
import cn.com.platform.framework.util.FwStringUtils;
import cn.com.platform.platform.mapper.common01.PlatformJobCron01Mapper;
import cn.com.platform.platform.model.common.PlatformJobCron;
import cn.com.platform.platformz.form.platformz99.Platformz990501Form;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 新建定时任务service
 * 
 * @author 王笃鹏
 * @date 2019-01-17
 */
@Service
public class Platformz990501EntryService extends BaseService {

  @Autowired
  PlatformJobCron01Mapper platformJobCron01Mapper;
  @Autowired
  private Scheduler scheduler;

  public EasyJson<PlatformJobCron> doEntry(Platformz990501Form inForm) {

    EasyJson<PlatformJobCron> easyJson = new EasyJson<PlatformJobCron>();
    if (!ObjectUtils.isEmpty(inForm.triggerJson)) {
      Gson gson = new Gson();
      DatetimeTrigger trigger = gson.fromJson(inForm.triggerJson, DatetimeTrigger.class);
      inForm.cronExpression = toCronExpression(trigger);
    }
    
    // 验证触发器
    if (ObjectUtils.isEmpty(inForm.cronExpression)) {
      easyJson.setSuccess(false);
      easyJson.setMsg("请重新设定触发器。");
      return easyJson;
    }
    CronTriggerImpl trigger = new CronTriggerImpl();
    try {
      trigger.setCronExpression(inForm.cronExpression);
      Date date = trigger.computeFirstFireTime(null);
      if (date == null || date.before(new Date())) {
        easyJson.setSuccess(false);
        easyJson.setMsg("当前触发器永远不会触发，请重新设定。");
        return easyJson;
      }
    } catch (Exception e) {
      easyJson.setSuccess(false);
      easyJson.setMsg(e.getMessage());
      return easyJson;
    }

    PlatformJobCron platformJobCron = new PlatformJobCron();
    platformJobCron.setJobGroup("系统默认");
    platformJobCron.setJobName(inForm.jobName);
    platformJobCron.setJobStatus(inForm.jobStatus);
    platformJobCron.setMethodName(inForm.methodName);
    platformJobCron.setMethodParams(inForm.methodParams);
    platformJobCron.setCronExpression(inForm.cronExpression);
    platformJobCron.setTriggerRemark(inForm.triggerRemark);
    platformJobCron.setPriority(inForm.priority);
    platformJobCron.setMisfirePolicy(Integer.toString(Trigger.MISFIRE_INSTRUCTION_SMART_POLICY));
    platformJobCron.setJobStatus(inForm.jobStatus);

    platformJobCron.setDeleteFlag(false);
    updateAudit(platformJobCron);


    // 检索job信息
    PlatformJobCron job = platformJobCron01Mapper.selectByPrimaryKey(inForm.jobId);
    if (job != null) {
      platformJobCron.setJobId(job.getJobId());
      // 更新job
      CronJob cronJob = new CronJob();
      BeanUtils.copyProperties(platformJobCron, cronJob);
      CronScheduleUtils.updateScheduleJob(scheduler, cronJob);
      // 更新job信息
      platformJobCron01Mapper.updateByPrimaryKey(platformJobCron);
    } else {
      platformJobCron.setJobId(FwStringUtils.getUniqueId());
      createAudit(platformJobCron);
      // 新建job
      CronJob cronJob = new CronJob();
      BeanUtils.copyProperties(platformJobCron, cronJob);
      CronScheduleUtils.createScheduleJob(scheduler, cronJob);
      // 新建job信息
      platformJobCron01Mapper.insert(platformJobCron);
    }

    easyJson.setSuccess(true);
    easyJson.setMsg("保存成功");
    return easyJson;
  }

  /**
   * cron表达式作成
   * 
   * @param trigger
   */
  private String toCronExpression(DatetimeTrigger trigger) {
    String years = toExpressionString(null);
    String months = toExpressionString(null);
    String daysOfWeek = toExpressionString(null);
    String daysOfMonth = toExpressionString(null);
    String hours = toExpressionString(null);
    String minutes = toExpressionString(null);
    if (null != trigger.getYears() && 0 < trigger.getYears().size()) {
      years = toExpressionString(trigger.getYears());
    }
    if (null != trigger.getMonths() && 0 < trigger.getMonths().size()) {
      months = toExpressionString(trigger.getMonths());
    }
    if (null != trigger.getDaysOfWeek() && 0 < trigger.getDaysOfWeek().size()) {
      daysOfWeek = toExpressionString(trigger.getDaysOfWeek());
      daysOfMonth = toExpressionString(new TreeSet<Integer>());
    } else if (null != trigger.getDaysOfMonth() && 0 < trigger.getDaysOfMonth().size()) {
      daysOfWeek = toExpressionString(new TreeSet<Integer>());
      daysOfMonth = toExpressionString(trigger.getDaysOfMonth());
    } else {
      daysOfWeek = toExpressionString(new TreeSet<Integer>());
      daysOfMonth = toExpressionString(null);
    }
    if (null != trigger.getHours() && 0 < trigger.getHours().size()) {
      hours = toExpressionString(trigger.getHours());
    }
    if (null != trigger.getMinutes() && 0 < trigger.getMinutes().size()) {
      minutes = toExpressionString(trigger.getMinutes());
    }
    final String cronExpression = String.format("%s %s %s %s %s %s %s", "0", minutes, hours,
        daysOfMonth, months, daysOfWeek, years);
    return cronExpression;
  }

  /**
   * toExpressionString
   * 
   * @param set set
   * @return String
   */
  private static String toExpressionString(final Set<Integer> set) {
    if (null == set) {
      return "*";
    }
    if (0 == set.size()) {
      return "?";
    }
    final StringBuilder builder = new StringBuilder();
    final Iterator<Integer> iterator = set.iterator();
    boolean first = true;
    while (iterator.hasNext()) {
      final Integer value = iterator.next();
      if (!first) {
        builder.append(",");
      }
      builder.append(value);
      first = false;
    }
    return builder.toString();
  }
}
