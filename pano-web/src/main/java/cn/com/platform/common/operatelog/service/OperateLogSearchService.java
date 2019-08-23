package cn.com.platform.common.operatelog.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.com.platform.common.operatelog.form.OperateLogForm;
import cn.com.platform.framework.code.BusinessType;
import cn.com.platform.framework.util.FwCodeUtil;
import cn.com.platform.platform.mapper.common01.PlatformOperateLog01Mapper;
import cn.com.platform.platform.model.common01.PlatformOperateLog01Model;
import cn.com.platform.util.EasyJson;
import cn.com.platform.util.MessageUtils;
import cn.com.platform.web.BaseService;

/**
 * 操作日志共通检索service 。
 * 
 * @author 代仁宗
 * @date 2019-07-09
 *
 */

@Service
public class OperateLogSearchService extends BaseService {

  @Autowired
  PlatformOperateLog01Mapper platformOperateLog01Mapper;

  /**
   * 查询一览数据 。
   * 
   * @param inForm 。
   * @return 一览信息
   */
  public EasyJson<PlatformOperateLog01Model> doSearch(OperateLogForm inForm) {

    // SQL文的参数
    HashMap<String, Object> parameter = new HashMap<String, Object>();

    // 模块主键
    parameter.put("moduleId", inForm.getParameterModuleId());
    parameter.put("recordId", inForm.getParameterRecordId());

    // 排序
    parameter.put("orderByClause", getOrderInfo(inForm));
    // 数据索引
    parameter.put("offSet", (inForm.pageNumber - 1) * inForm.pageSize);
    // 每页的条数
    parameter.put("pageSize", inForm.pageSize);
    // 总件数
    inForm.recordCount = platformOperateLog01Mapper.selectOperateLogCount(parameter);
    if (inForm.recordCount > 0) {
      // 定义一个list
      List<PlatformOperateLog01Model> list = null;
      // 每页数据 查询出的数据list
      list = platformOperateLog01Mapper.selectOperateLogInfo(parameter);
      // 页面枚举转换
      for (PlatformOperateLog01Model ciszOperateLogModel : list) {
        // 查询出枚举 对应相应 文字
        BusinessType businessType = FwCodeUtil.stringToEnum(BusinessType.class,
            ciszOperateLogModel.getBusinessType().toString());
        ciszOperateLogModel
            .setBusinessTypeName(MessageUtils.getMessage(businessType.getMessageId()));
      }
      // 赋值
      inForm.operateLogList = list;
    }

    EasyJson<PlatformOperateLog01Model> easyJson = new EasyJson<PlatformOperateLog01Model>();
    // setRows 表格有翻页
    easyJson.setRows(inForm.operateLogList);
    easyJson.setTotal(inForm.recordCount);
    return easyJson;
  }

  /**
   * 取得排序信息。
   * 
   * @param inForm form
   * @return 排序信息
   */
  private String getOrderInfo(OperateLogForm inForm) {
    Map<Object, Object> orderkeyInfo = new HashMap<>();
    // orderkeyInfo.put("dispFlag", "disp_flag");
    orderkeyInfo.put("title",
        "CONVERT( cisd_team_system.TEAM_SYSTEM_TYPE USING gbk ) COLLATE gbk_chinese_ci");
    // 按照创建时间倒序排列
    if (orderkeyInfo.get(inForm.sortName) == null) {
      return null;
    }
    return orderkeyInfo.get(inForm.sortName) + " " + inForm.sortOrder;
  }

}
