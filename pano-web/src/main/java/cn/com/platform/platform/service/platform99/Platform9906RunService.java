package cn.com.platform.platform.service.platform99;

import org.quartz.Scheduler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import cn.com.platform.common.quartz.domain.RepeatJob;
import cn.com.platform.common.quartz.util.RepeatScheduleUtils;
import cn.com.platform.platform.form.platform99.Platform9906Form;
import cn.com.platform.platform.mapper.common01.PlatformJobRepeat01Mapper;
import cn.com.platform.platform.model.common.PlatformJobRepeat;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 立即运行任务service
 * 
 * @author 王笃鹏
 * @date 2019-01-17
 */
@Service
public class Platform9906RunService extends BaseService {

  @Autowired
  PlatformJobRepeat01Mapper platformJobRepeat01Mapper;
  @Autowired
  private Scheduler scheduler;

  public EasyJson<PlatformJobRepeat> doRun(Platform9906Form inForm) {

    if (!ObjectUtils.isEmpty(inForm.uniqueKeyList)) {
      for (Object JobId : inForm.uniqueKeyList) {
        // 检索job信息
        PlatformJobRepeat platformJobRepeat =
            platformJobRepeat01Mapper.selectByPrimaryKey(ObjectUtils.getDisplayString(JobId));
        if (platformJobRepeat != null) {
          // 立即运行job
          RepeatJob cronJob = new RepeatJob();
          BeanUtils.copyProperties(platformJobRepeat, cronJob);
          RepeatScheduleUtils.run(scheduler, cronJob);
        }
      }
    }

    EasyJson<PlatformJobRepeat> easyJson = new EasyJson<PlatformJobRepeat>();
    easyJson.setSuccess(true);
    easyJson.setMsg("操作成功");
    return easyJson;
  }
}
