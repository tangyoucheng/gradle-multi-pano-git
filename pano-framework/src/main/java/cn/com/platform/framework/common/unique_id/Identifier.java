package cn.com.platform.framework.common.unique_id;

import java.util.ServiceLoader;

public final class Identifier {

  private static SystemIdProvider provider;

  static {
    for (final SystemIdProvider provider : ServiceLoader.load(SystemIdProvider.class)) {
      Identifier.provider = provider;
      break;
    }
  }

  /**
   * 构造函数。
   */
  public Identifier() {
    super();
  }

  /**
   * 返回唯一标识。
   */
  public String get() {
    // return Identifier.provider.getSystemId().concat(make());
    return make();
  }

  /**
   * 设定唯一标识。
   * 
   * @return 唯一标识符
   */
  public static String make() {
    return UniqueIdGenerator.getUniqueId();
  }
}
