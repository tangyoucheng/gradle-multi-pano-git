package cn.com.platform.platform.common.code;

import cn.com.platform.framework.code.EnumConstants;
import cn.com.platform.framework.util.FwCodeUtil;

/**
 * 
 * 任务状态
 * 
 * @author admin
 */
public enum JobStatus implements EnumConstants {

  /** 暂停。*/
  Pause("0", "SH.JOB.STATUS.00"),
  /** 正常。*/
  Normal("1", "SH.JOB.STATUS.01");

  private final String code;
  private final String messageId;

  private JobStatus(final String code, final String messageId) {
    this.code = code;
    this.messageId = messageId;
  }

  /**
   * 获取声明中包含enum定数的代码值。
   * 
   * @return String 声明中包含enum定数的代码值
   */
  @Override
  public String toString() {
    return this.code;
  }

  /**
   * 获取JobStatus。
   * 
   * @param code 代码值
   * @return JobStatus 评论状态enum定数
   */
  public static JobStatus getEnum(final String code) {
    return FwCodeUtil.stringToEnum(JobStatus.class, code);
  }

  /**
   * 获取信息ID。<br>
   * 信息ID是设置在信息属性文件中的值。<br>
   * 
   * @return String 信息ID
   */
  public String getMessageId() {
    return this.messageId;
  }
}
