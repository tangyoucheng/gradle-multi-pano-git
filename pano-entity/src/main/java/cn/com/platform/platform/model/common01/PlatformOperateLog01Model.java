package cn.com.platform.platform.model.common01;

import cn.com.platform.platform.model.common.PlatformOperateLog;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 操作日志共通Model。
 * 
 * @author 代仁宗
 * @date 2019-07-09
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class PlatformOperateLog01Model extends PlatformOperateLog {
  // 业务类型名
  private String businessTypeName;

  // 参数模块主键
  private String parameterModuleId;
  
  // 参数 数据记录主键
  private String parameterRecordId;

  public String getBusinessTypeName() {
    return businessTypeName;
  }

  public void setBusinessTypeName(String businessTypeName) {
    this.businessTypeName = businessTypeName;
  }

  public String getParameterModuleId() {
    return parameterModuleId;
  }

  public void setParameterModuleId(String parameterModuleId) {
    this.parameterModuleId = parameterModuleId;
  }

  public String getParameterRecordId() {
    return parameterRecordId;
  }

  public void setParameterRecordId(String parameterRecordId) {
    this.parameterRecordId = parameterRecordId;
  }

}
