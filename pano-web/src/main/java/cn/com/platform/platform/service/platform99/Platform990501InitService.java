package cn.com.platform.platform.service.platform99;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import cn.com.platform.framework.common.CodeValueRecord;
import cn.com.platform.framework.util.FwCodeUtil;
import cn.com.platform.platform.common.code.JobStatus;
import cn.com.platform.platform.form.platform99.Platform990501Form;
import cn.com.platform.platform.mapper.common01.PlatformJobCron01Mapper;
import cn.com.platform.platform.model.common.PlatformJobCron;
import cn.com.platform.util.MessageUtils;
import cn.com.platform.web.BaseService;

/**
 * 定时任务初期化service
 * 
 * @author 王笃鹏
 * @date 2019-01-17
 */
@Service
public class Platform990501InitService extends BaseService {

  @Autowired
  PlatformJobCron01Mapper platformJobCron01Mapper;

  public void doInit(Platform990501Form inForm) {

    if (!ObjectUtils.isEmpty(inForm.jobId)) {
      // 检索job信息
      PlatformJobCron platformJobCron =
          platformJobCron01Mapper.selectByPrimaryKey(ObjectUtils.getDisplayString(inForm.jobId));
      if (platformJobCron != null) {
        inForm.jobName = platformJobCron.getJobName();
        inForm.jobGroup = platformJobCron.getJobGroup();
        inForm.methodName = platformJobCron.getMethodName();
        inForm.methodParams = platformJobCron.getMethodParams();
        inForm.cronExpression = platformJobCron.getCronExpression();
        inForm.triggerRemark = platformJobCron.getTriggerRemark();
        inForm.priority = platformJobCron.getPriority();
        inForm.misfirePolicy = platformJobCron.getMisfirePolicy();
        inForm.jobStatus = platformJobCron.getJobStatus();
        inForm.remark = platformJobCron.getRemark();
      }
    }

    // 任务状态
    List<CodeValueRecord> jobStatusList = new ArrayList<CodeValueRecord>();
    JobStatus jobStatus = FwCodeUtil.stringToEnum(JobStatus.class, JobStatus.Normal.toString());
    jobStatusList.add(new CodeValueRecord(JobStatus.Normal.toString(),
        MessageUtils.getMessage(jobStatus.getMessageId())));
    jobStatus = FwCodeUtil.stringToEnum(JobStatus.class, JobStatus.Pause.toString());
    jobStatusList.add(new CodeValueRecord(JobStatus.Pause.toString(),
        MessageUtils.getMessage(jobStatus.getMessageId())));
    inForm.jobStatusList = jobStatusList;
  }
}
