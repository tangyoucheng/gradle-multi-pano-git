package cn.com.pano.pano.common.code;

import cn.com.platform.framework.code.EnumConstants;
import cn.com.platform.framework.util.FwCodeUtil;

/**
 * 展览类别
 * 
 * @author ouyangzidou
 *
 */
public enum ExpositionType implements EnumConstants {

  /** 展览：引进展览。 */
  IMPORT("0", "引进展览"),
  /** 展览：输出展览。 */
  OUTPUT("1", "输出展览"),
  /** 展览：原创展览。 */
  ORIGINAL("2", "原创展览");

  private final String code;
  private final String messageId;

  private ExpositionType(final String code, final String messageId) {
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
  public static ExpositionType getEnum(final String code) {
    return FwCodeUtil.stringToEnum(ExpositionType.class, code);
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
