package cn.com.platform.common.code;

import cn.com.platform.framework.code.EnumConstants;
import cn.com.platform.framework.util.FwCodeUtil;

/**
 * 性别类别
 * 
 * @author ouyangzidou
 *
 */
public enum SexType implements EnumConstants {

  /** 性别：男 */
  MAN("0", "FW.CAP.0012"),
  /** 性别：女 */
  WOMAN("1", "FW.CAP.0013");

  private final String code;
  private final String messageId;

  private SexType(final String code, final String messageId) {
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
  public static SexType getEnum(final String code) {
    return FwCodeUtil.stringToEnum(SexType.class, code);
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
