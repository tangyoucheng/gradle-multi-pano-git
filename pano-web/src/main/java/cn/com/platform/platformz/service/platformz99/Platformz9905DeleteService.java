package cn.com.platform.platformz.service.platformz99;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import cn.com.platform.common.quartz.util.CronScheduleUtils;
import cn.com.platform.platform.mapper.common01.PlatformJobCron01Mapper;
import cn.com.platform.platform.model.common.PlatformJobCron;
import cn.com.platform.platformz.form.platformz99.Platformz9905Form;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 删除任务service
 * 
 * @author 王笃鹏
 * @date 2019-01-17
 */
@Service
public class Platformz9905DeleteService extends BaseService {

  @Autowired
  PlatformJobCron01Mapper platformJobCron01Mapper;
  @Autowired
  private Scheduler scheduler;

  public EasyJson<PlatformJobCron> doDelete(Platformz9905Form inForm) {

    if (!ObjectUtils.isEmpty(inForm.uniqueKeyList)) {
      for (Object JobId : inForm.uniqueKeyList) {
        // 检索job信息
        PlatformJobCron platformJobCron =
            platformJobCron01Mapper.selectByPrimaryKey(ObjectUtils.getDisplayString(JobId));
        if (platformJobCron != null) {
          // 删除job信息
          platformJobCron01Mapper.deleteByPrimaryKey(platformJobCron.jobId);
          // 删除job
          CronScheduleUtils.deleteScheduleJob(scheduler, platformJobCron.getJobId());
        }
      }
    }

    EasyJson<PlatformJobCron> easyJson = new EasyJson<PlatformJobCron>();
    easyJson.setSuccess(true);
    easyJson.setMsg("操作成功");
    return easyJson;
  }
}
