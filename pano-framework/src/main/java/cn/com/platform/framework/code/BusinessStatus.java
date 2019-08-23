package cn.com.platform.framework.code;

import cn.com.platform.framework.util.FwCodeUtil;

/**
 * 操作状态。
 * 
 * @author 唐友成
 * @date 2019-07-03
 */
public enum BusinessStatus implements EnumConstants {

  /**
   * 失败。
   */
  FAIL("0", "FW.CAP.BUSINESS.STATUS.01"),

  /**
   * 成功。
   */
  SUCCESS("1", "FW.CAP.BUSINESS.STATUS.02");

  private final String code;
  private final String messageId;

  private BusinessStatus(final String code, final String messageId) {
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
   * 获取FlagStatus。
   * 
   * @param code 代码值
   * @return FlagStatus 标识状态enum定数
   */
  public static FlagStatus getEnum(final String code) {
    return FwCodeUtil.stringToEnum(FlagStatus.class, code);
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
