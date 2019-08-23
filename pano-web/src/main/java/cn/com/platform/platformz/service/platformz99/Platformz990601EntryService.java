package cn.com.platform.platformz.service.platformz99;

import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import com.google.gson.Gson;
import cn.com.platform.common.quartz.domain.RepeatJob;
import cn.com.platform.common.quartz.util.RepeatScheduleUtils;
import cn.com.platform.framework.util.FwStringUtils;
import cn.com.platform.platform.mapper.common01.PlatformJobRepeat01Mapper;
import cn.com.platform.platform.model.common.PlatformJobRepeat;
import cn.com.platform.platformz.form.platformz99.Platformz99060101Form;
import cn.com.platform.platformz.form.platformz99.Platformz990601Form;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 新建重复任务service
 * 
 * @author 王笃鹏
 * @date 2019-01-17
 */
@Service
public class Platformz990601EntryService extends BaseService {

  @Autowired
  PlatformJobRepeat01Mapper platformJobRepeat01Mapper;
  @Autowired
  private Scheduler scheduler;

  public EasyJson<PlatformJobRepeat> doEntry(Platformz990601Form inForm) {

    Platformz99060101Form trigger = new Platformz99060101Form();

    EasyJson<PlatformJobRepeat> easyJson = new EasyJson<PlatformJobRepeat>();
    if (!ObjectUtils.isEmpty(inForm.triggerJson)) {
      Gson gson = new Gson();
      trigger = gson.fromJson(inForm.triggerJson, Platformz99060101Form.class);
    }

    PlatformJobRepeat platformJobRepeat = new PlatformJobRepeat();
    platformJobRepeat.setJobGroup("系统默认");
    platformJobRepeat.setJobName(inForm.jobName);
    platformJobRepeat.setJobStatus(inForm.jobStatus);
    platformJobRepeat.setMethodName(inForm.methodName);
    platformJobRepeat.setMethodParams(inForm.methodParams);
    platformJobRepeat.setMethodParams(inForm.methodParams);
    platformJobRepeat.setRepeatCount(trigger.repeatCount);
    platformJobRepeat.setRepeatInterval(trigger.repeatInterval);
    platformJobRepeat.setStartDate(trigger.startDate);
    // platformJobRepeat.setEndDate(inForm.endDate);
    platformJobRepeat.setTriggerRemark(inForm.triggerRemark);
    platformJobRepeat.setPriority(inForm.priority);
    platformJobRepeat.setMisfirePolicy(Integer.toString(Trigger.MISFIRE_INSTRUCTION_SMART_POLICY));
    platformJobRepeat.setJobStatus(inForm.jobStatus);

    platformJobRepeat.setDeleteFlag(false);
    updateAudit(platformJobRepeat);

    // 检索job信息
    PlatformJobRepeat job =
        platformJobRepeat01Mapper.selectByPrimaryKey(ObjectUtils.getDisplayString(inForm.jobId));
    if (job != null) {
      platformJobRepeat.setJobId(job.getJobId());
      // 更新job信息
      platformJobRepeat01Mapper.updateByPrimaryKey(platformJobRepeat);
      // 更新job
      RepeatJob repeatJob = new RepeatJob();
      BeanUtils.copyProperties(platformJobRepeat, repeatJob);
      RepeatScheduleUtils.updateScheduleJob(scheduler, repeatJob);
    } else {
      platformJobRepeat.setJobId(FwStringUtils.getUniqueId());
      createAudit(platformJobRepeat);
      // 新建job
      RepeatJob repeatJob = new RepeatJob();
      BeanUtils.copyProperties(platformJobRepeat, repeatJob);
      RepeatScheduleUtils.createScheduleJob(scheduler, repeatJob);
      // 新建job信息
      platformJobRepeat01Mapper.insert(platformJobRepeat);
    }

    easyJson.setSuccess(true);
    easyJson.setMsg("保存成功");
    return easyJson;
  }
}
