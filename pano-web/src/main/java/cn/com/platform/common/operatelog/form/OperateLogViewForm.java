package cn.com.platform.common.operatelog.form;

import java.util.List;
import cn.com.platform.framework.common.CodeValueRecord;
import cn.com.platform.framework.form.AbstractForm;
import cn.com.platform.platform.model.common01.PlatformOperateLog01Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 操作日志共通查看 form 。
 * 
 * @author 代仁宗
 * @date 2019-07-09
 *
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class OperateLogViewForm extends AbstractForm {
  // 参数 模块主键
  private String parameterModuleId;
  // 参数 数据记录主键
  private String parameterRecordId;
  // 模块主键
  private String moduleId;
  // 数据记录主键
  private String recordId;
  // 日志主键
  private String operateId;
  // 操作时间
  private String operateTime;
  // 操作人员
  private String operateName;
  // 请求参数
  private String operateParam;
  // 业务类型
  public String businessType;
  // 业务类型名
  public String businessTypeName;
  // 业务类型 List
  public List<CodeValueRecord> businessTypeList;
  /** 结果集。 */
  public List<PlatformOperateLog01Model> operateLogList;
}
