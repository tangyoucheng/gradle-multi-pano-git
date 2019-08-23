package cn.com.pano.pano.common.code;

import cn.com.platform.framework.code.EnumConstants;
import cn.com.platform.framework.util.FwCodeUtil;

/**
 * 展览状态。
 * 
 * @author ouyangzidou
 *
 */
public enum ExpositionStatus implements EnumConstants {

  /** 展览状态：规划中。 */
  PLANNING("0", "规划中"),
  /** 展览状态：进行中。 */
  PROGRESS("1", "进行中"),
  /** 展览状态：已结束。 */
  OVER("2", "已结束");

  private final String code;
  private final String messageId;

  private ExpositionStatus(final String code, final String messageId) {
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
  public static ExpositionStatus getEnum(final String code) {
    return FwCodeUtil.stringToEnum(ExpositionStatus.class, code);
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
