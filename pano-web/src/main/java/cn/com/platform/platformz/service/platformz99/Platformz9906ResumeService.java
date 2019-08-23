package cn.com.platform.platformz.service.platformz99;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import cn.com.platform.common.quartz.util.RepeatScheduleUtils;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.platform.mapper.common01.PlatformJobRepeat01Mapper;
import cn.com.platform.platform.model.common.PlatformJobRepeat;
import cn.com.platform.platformz.form.platformz99.Platformz9906Form;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 恢复任务service
 * 
 * @author 王笃鹏
 * @date 2019-01-17
 */
@Service
public class Platformz9906ResumeService extends BaseService {

  @Autowired
  PlatformJobRepeat01Mapper platformJobRepeat01Mapper;
  @Autowired
  private Scheduler scheduler;

  public EasyJson<PlatformJobRepeat> doResume(Platformz9906Form inForm) {

    if (!ObjectUtils.isEmpty(inForm.uniqueKeyList)) {
      for (Object JobId : inForm.uniqueKeyList) {
        // 检索job信息
        PlatformJobRepeat platformJobRepeat =
            platformJobRepeat01Mapper.selectByPrimaryKey(ObjectUtils.getDisplayString(JobId));
        if (platformJobRepeat != null) {
          // 更新job信息
          platformJobRepeat.setJobStatus(FlagStatus.Enable.toString());
          platformJobRepeat01Mapper.updateByPrimaryKey(platformJobRepeat);
          // 恢复job
          RepeatScheduleUtils.resumeJob(scheduler, platformJobRepeat.getJobId());
        }
      }
    }

    EasyJson<PlatformJobRepeat> easyJson = new EasyJson<PlatformJobRepeat>();
    easyJson.setSuccess(true);
    easyJson.setMsg("操作成功");
    return easyJson;
  }
}
