package cn.com.platform.framework.code;

import cn.com.platform.framework.util.FwCodeUtil;

/**
 * 
 * 用户状态<br>
 * <br>
 * 
 * @author admin
 */
public enum UserState implements EnumConstants {

  /** 激活 */
  Active("1", "FW.CAP.0003"),
  /** 冻结 */
  Frozen("2", "FW.CAP.0004");

  private final String code;
  private final String messageId;

  private UserState(final String code, final String messageId) {
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
   * @return UserStatus 用户状态enum定数
   */
  public static UserState getEnum(final String code) {
    return FwCodeUtil.stringToEnum(UserState.class, code);
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
