package cn.com.platform.platformz.service.platformz99;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.com.platform.framework.util.FwCodeUtil;
import cn.com.platform.platform.common.code.JobStatus;
import cn.com.platform.platform.mapper.common01.PlatformJobCron01Mapper;
import cn.com.platform.platform.model.common01.PlatformJobCron01Model;
import cn.com.platform.platformz.form.platformz99.Platformz9905Form;
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
public class Platformz9905SearchService extends BaseService {

  @Autowired
  PlatformJobCron01Mapper platformJobCron01Mapper;

  public EasyJson<PlatformJobCron01Model> doSearch(Platformz9905Form inForm) {

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
    inForm.recordCount = platformJobCron01Mapper.selectJobsCount(parameter);
    if (inForm.recordCount > 0) {
      // 每页数据
      inForm.jobList = platformJobCron01Mapper.selectJobsInfo(parameter);
      for (PlatformJobCron01Model platformJobCronF01Dto : inForm.jobList) {
        // 任务状态
        JobStatus jobStatus =
            FwCodeUtil.stringToEnum(JobStatus.class, platformJobCronF01Dto.getJobStatus());
        platformJobCronF01Dto.jobStatusName = MessageUtils.getMessage(jobStatus.getMessageId());
      }
    }

    EasyJson<PlatformJobCron01Model> easyJson = new EasyJson<PlatformJobCron01Model>();
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
  private String getOrderInfo(Platformz9905Form inForm) {
    Map<Object, Object> orderkeyInfo = new HashMap<>();
    orderkeyInfo.put("jobName", "JOB_NAME");
    if (orderkeyInfo.get(inForm.sortName) == null) {
      return null;
    }
    return orderkeyInfo.get(inForm.sortName) + " " + inForm.sortOrder;
  }
}
