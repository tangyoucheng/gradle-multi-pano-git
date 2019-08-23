package cn.com.platform.framework.code;

import cn.com.platform.framework.util.FwCodeUtil;

/**
 * 业务操作类型。
 * 
 * @author 唐友成
 * @date 2019-07-03
 */
public enum BusinessType implements EnumConstants {
  /**
   * 其它。
   */
  OTHER("0", "FW.CAP.BUSINESS.TYPE.01"),

  /**
   * 新增。
   */
  INSERT("1", "FW.CAP.BUSINESS.TYPE.02"),

  /**
   * 修改。
   */
  UPDATE("2", "FW.CAP.BUSINESS.TYPE.03"),

  /**
   * 删除。
   */
  DELETE("3", "FW.CAP.BUSINESS.TYPE.04"),

  /**
   * 授权。
   */
  GRANT("4", "FW.CAP.BUSINESS.TYPE.05"),

  /**
   * 导出。
   */
  EXPORT("5", "FW.CAP.BUSINESS.TYPE.06"),

  /**
   * 导入。
   */
  IMPORT("6", "FW.CAP.BUSINESS.TYPE.07"),

  /**
   * 强退。
   */
  FORCE("7", "FW.CAP.BUSINESS.TYPE.08"),

  /**
   * 生成代码。
   */
  GENCODE("8", "FW.CAP.BUSINESS.TYPE.09"),

  /**
   * 清空。
   */
  CLEAN("9", "清空");

  private final String code;
  private final String messageId;

  private BusinessType(final String code, final String messageId) {
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
  public static BusinessType getEnum(final String code) {
    return FwCodeUtil.stringToEnum(BusinessType.class, code);
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
