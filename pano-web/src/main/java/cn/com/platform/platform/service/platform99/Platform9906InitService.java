package cn.com.platform.platform.service.platform99;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.com.platform.framework.common.CodeValueRecord;
import cn.com.platform.framework.util.FwCodeUtil;
import cn.com.platform.platform.common.code.JobStatus;
import cn.com.platform.platform.form.platform99.Platform9906Form;
import cn.com.platform.platform.mapper.common01.PlatformJobRepeat01Mapper;
import cn.com.platform.util.MessageUtils;
import cn.com.platform.web.BaseService;

/**
 * 定时任务初期化service
 * 
 * @author 王笃鹏
 * @date 2019-01-17
 */
@Service
public class Platform9906InitService extends BaseService {

  @Autowired
  PlatformJobRepeat01Mapper platformJobRepeat01Mapper;

  public void doInit(Platform9906Form inForm) {

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
