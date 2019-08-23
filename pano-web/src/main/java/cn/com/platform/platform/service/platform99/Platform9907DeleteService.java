package cn.com.platform.platform.service.platform99;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import cn.com.platform.platform.form.platform99.Platform9907Form;
import cn.com.platform.platform.mapper.common01.PlatformJobLog01Mapper;
import cn.com.platform.platform.model.common.PlatformJobLog;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 删除任务日志service
 * 
 * @author 王笃鹏
 * @date 2019-01-17
 */
@Service
public class Platform9907DeleteService extends BaseService {

  @Autowired
  PlatformJobLog01Mapper platformJobLog01Mapper;

  public EasyJson<PlatformJobLog> doDelete(Platform9907Form inForm) {

    if (!ObjectUtils.isEmpty(inForm.uniqueKeyList)) {
      for (Object JobLogId : inForm.uniqueKeyList) {
        // 检索日志信息
        PlatformJobLog platformJobLog =
            platformJobLog01Mapper.selectByPrimaryKey(ObjectUtils.getDisplayString(JobLogId));
        if (platformJobLog != null) {
          // 删除日志信息
          platformJobLog01Mapper.deleteByPrimaryKey(platformJobLog.jobLogId);
        }
      }
    }

    EasyJson<PlatformJobLog> easyJson = new EasyJson<PlatformJobLog>();
    easyJson.setSuccess(true);
    easyJson.setMsg("操作成功");
    return easyJson;
  }
}
