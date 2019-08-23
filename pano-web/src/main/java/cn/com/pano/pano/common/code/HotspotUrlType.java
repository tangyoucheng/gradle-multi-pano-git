package cn.com.pano.pano.common.code;

import cn.com.platform.framework.code.EnumConstants;
import cn.com.platform.framework.util.FwCodeUtil;

/**
 * 热点URL类别
 * 
 * @author ouyangzidou
 *
 */
public enum HotspotUrlType implements EnumConstants {

  /** 热点URL：全景图 */
  PANORAMA("1", "全景图"),
  /** 热点URL：素材图 */
  IMAGE("2", "素材图"),
  /** 热点URL：声音 */
  SOUND("3", "声音"),
  /** 热点URL：视频 */
  VIDEO("4", "视频"),
  /** 热点URL：外部链接 */
  LINK("5", "外部链接"),
  /** 热点URL：图文 */
  TEXT_IMAGE("6", "图文");

  private final String code;
  private final String messageId;

  private HotspotUrlType(final String code, final String messageId) {
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
  public static HotspotUrlType getEnum(final String code) {
    return FwCodeUtil.stringToEnum(HotspotUrlType.class, code);
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
