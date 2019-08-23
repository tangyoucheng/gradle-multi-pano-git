package cn.com.pano.pano.common.code;

import cn.com.platform.framework.code.EnumConstants;
import cn.com.platform.framework.util.FwCodeUtil;

/**
 * 素材类别。
 * 
 * @author ouyangzidou
 *
 */
public enum MaterialType implements EnumConstants {

  /** 素材：平面图。 */
  IMAGE("1", "平面图"),
  /** 素材：声音。 */
  SOUND("2", "声音"),
  /** 素材：视频。 */
  VIDEO("3", "视频"),
  /** 素材：场景切换。 */
  HOTSPOT_CHANGE_SCENE("4", "场景切换"),
  /** 素材：普通热点。 */
  HOTSPOT_IMAGE("5", "普通热点"),
  /** 素材：LOGO热点。 */
  HOTSPOT_LOGO("6", "LOGO热点"),
  /** 素材：图片浮动信息层。 */
  FLOW_INFO_IMAGE("7", "图片浮动信息层"),
  /** 素材：文字浮动信息层。 */
  FLOW_INFO_TEXT("8", "文字浮动信息层"),
  /** 素材：图文信息。 */
  IMAGE_TEXT("9", "图文信息");

  private final String code;
  private final String messageId;

  private MaterialType(final String code, final String messageId) {
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
  public static MaterialType getEnum(final String code) {
    return FwCodeUtil.stringToEnum(MaterialType.class, code);
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
