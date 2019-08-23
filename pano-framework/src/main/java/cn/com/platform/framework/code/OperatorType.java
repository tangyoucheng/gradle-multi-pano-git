package cn.com.platform.framework.code;

import cn.com.platform.framework.code.EnumConstants;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.framework.util.FwCodeUtil;

/**
 * 操作人类别。
 * 
 * @author 唐友成
 * @date 2019-07-03
 */
public enum OperatorType implements EnumConstants {
  /**
   * 其它。
   */
  OTHER("0", "FW.CAP.OPERATOR.TYPE.01"),

  /**
   * 后台用户。
   */
  MANAGE("1", "FW.CAP.OPERATOR.TYPE.02"),

  /**
   * 手机端用户。
   */
  MOBILE("2", "FW.CAP.OPERATOR.TYPE.03");

  private final String code;
  private final String messageId;

  private OperatorType(final String code, final String messageId) {
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
