package cn.com.platform.platform.service.platform99;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.com.platform.framework.util.FwCodeUtil;
import cn.com.platform.platform.common.code.JobStatus;
import cn.com.platform.platform.form.platform99.Platform9906Form;
import cn.com.platform.platform.mapper.common01.PlatformJobRepeat01Mapper;
import cn.com.platform.platform.model.common01.PlatformJobRepeat01Model;
import cn.com.platform.util.EasyJson;
import cn.com.platform.util.MessageUtils;
import cn.com.platform.web.BaseService;

/**
 * 定时任务查询service
 * 
 * @author 王笃鹏
 * @date 2019-01-17
 */
@Service
public class Platform9906SearchService extends BaseService {

  @Autowired
  PlatformJobRepeat01Mapper platformJobRepeat01Mapper;

  public EasyJson<PlatformJobRepeat01Model> doSearch(Platform9906Form inForm) {

    // SQL文的参数
    HashMap<String, Object> parameter = new HashMap<String, Object>();
    // 任务名称
    parameter.put("jobName", inForm.selectedJobName);
    // 方法名称
    parameter.put("methodName", inForm.selectedMethodName);
    // 任务状态
    parameter.put("jobStatus", inForm.selectedJobStatus);
    // 排序
    parameter.put("orderByClause", getOrderInfo(inForm));
    // 数据索引
    parameter.put("offSet", (inForm.pageNumber - 1) * inForm.pageSize);
    // 每页的条数
    parameter.put("pageSize", inForm.pageSize);

    // 总件数
    inForm.recordCount = platformJobRepeat01Mapper.selectJobsCount(parameter);
    if (inForm.recordCount > 0) {
      // 每页数据
      inForm.jobList = platformJobRepeat01Mapper.selectJobsInfo(parameter);
      for (PlatformJobRepeat01Model platformJobRepeatF01Dto : inForm.jobList) {
        // 任务状态
        JobStatus jobStatus =
            FwCodeUtil.stringToEnum(JobStatus.class, platformJobRepeatF01Dto.getJobStatus());
        platformJobRepeatF01Dto.jobStatusName = MessageUtils.getMessage(jobStatus.getMessageId());
      }
    }

    EasyJson<PlatformJobRepeat01Model> easyJson = new EasyJson<PlatformJobRepeat01Model>();
    easyJson.setRows(inForm.jobList);
    easyJson.setTotal(inForm.recordCount);
    return easyJson;
  }

  /**
   * 取得排序信息。
   * 
   * @param inForm 房源列表form
   * @return 排序信息
   */
  private String getOrderInfo(Platform9906Form inForm) {
    Map<Object, Object> orderkeyInfo = new HashMap<>();
    orderkeyInfo.put("jobName", "JOB_NAME");
    if (orderkeyInfo.get(inForm.sortName) == null) {
      return null;
    }
    return orderkeyInfo.get(inForm.sortName) + " " + inForm.sortOrder;
  }
}
