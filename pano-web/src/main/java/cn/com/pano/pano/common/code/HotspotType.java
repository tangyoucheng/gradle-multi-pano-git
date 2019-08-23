package cn.com.pano.pano.common.code;

import cn.com.platform.framework.code.EnumConstants;
import cn.com.platform.framework.util.FwCodeUtil;

/**
 * 热点类别
 * 
 * @author ouyangzidou
 *
 */
public enum HotspotType implements EnumConstants {

  /** 热点：多边形热点。 */
  POLYGON("1", "多边形热点"),
  /** 热点：导航热点。 */
  CHANGE_SCENE("2", "导航热点"),
  /** 热点：普通热点。 */
  IMAGE("3", "普通热点"),
  /** 热点：LOGO热点。 */
  LOGO("4", "LOGO热点"),
  /** 热点：首个场景里被设为推荐路线的点。 */
  FIRST_SCENE_RECOMMEND("5", "首个场景里被设为推荐路线的点"),
  /** 热点：音频热点。 */
  MUSIC("6", "音频热点");

  private final String code;
  private final String messageId;

  private HotspotType(final String code, final String messageId) {
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
  public static HotspotType getEnum(final String code) {
    return FwCodeUtil.stringToEnum(HotspotType.class, code);
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
