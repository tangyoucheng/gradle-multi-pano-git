package cn.com.platform.platform.service.platform99;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.com.platform.platform.model.common.PlatformJobLog;
import cn.com.platform.platform.form.platform99.Platform9907Form;
import cn.com.platform.platform.mapper.common01.PlatformJobLog01Mapper;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 任务日志查询service
 * 
 * @author 王笃鹏
 * @date 2019-01-17
 */
@Service
public class Platform9907SearchService extends BaseService {

  @Autowired
  PlatformJobLog01Mapper platformJobLog01Mapper;

  public EasyJson<PlatformJobLog> doSearch(Platform9907Form inForm) {

    // SQL文的参数
    HashMap<String, Object> parameter = new HashMap<String, Object>();
    // 任务名称
    parameter.put("jobName", inForm.selectedJobName);
    // 方法名称
    parameter.put("methodName", inForm.selectedMethodName);
    // 任务状态
    parameter.put("status", inForm.selectedStatus);
    // 排序
    parameter.put("orderByClause", getOrderInfo(inForm));
    // 数据索引
    parameter.put("offSet", (inForm.pageNumber - 1) * inForm.pageSize);
    // 每页的条数
    parameter.put("pageSize", inForm.pageSize);

    // 总件数
    inForm.recordCount = platformJobLog01Mapper.selectJobLogsCount(parameter);
    if (inForm.recordCount > 0) {
      // 每页数据
      inForm.jobLogList = platformJobLog01Mapper.selectJobLogsInfo(parameter);
    }

    EasyJson<PlatformJobLog> easyJson = new EasyJson<PlatformJobLog>();
    easyJson.setRows(inForm.jobLogList);
    easyJson.setTotal(inForm.recordCount);
    return easyJson;
  }

  /**
   * 取得排序信息。
   * 
   * @param inForm 房源列表form
   * @return 排序信息
   */
  private String getOrderInfo(Platform9907Form inForm) {
    Map<Object, Object> orderkeyInfo = new HashMap<>();
    orderkeyInfo.put("jobName", "JOB_NAME");
    if (orderkeyInfo.get(inForm.sortName) == null) {
      return null;
    }
    return orderkeyInfo.get(inForm.sortName) + " " + inForm.sortOrder;
  }
}
