package cn.com.platform.framework.code;

/**
 * 
 * 代码定义模型类<br>
 * <br>
 * 
 * @author admin
 */
public class CodeModel {

  /** 代码值 */
  private String code;

  /** 信息ID（信息属性键） */
  private String messageId;

  /**
   * 构造函数<br>
   * 创建新的代码定义模型类。
   */
  public CodeModel() {}

  /**
   * 获取代码值。
   * 
   * @return String 代码值
   */
  public String getCode() {
    return this.code;
  }

  /**
   * 设定代码值。
   * 
   * @param code 代码值
   */
  public void setCode(final String code) {
    this.code = code;
  }

  /**
   * 获取信息ID（信息属性键） 。
   * 
   * @return String 信息ID（信息属性键） 
   */
  public String getMessageId() {
    return this.messageId;
  }

  /**
   * 设定信息ID（信息属性键） 。
   * 
   * @param messageId 信息ID（信息属性键） 
   */
  public void setMessageId(final String messageId) {
    this.messageId = messageId;
  }

}
