package cn.com.platform.framework.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cn.com.platform.framework.code.CodeModel;
import cn.com.platform.framework.code.EnumConstants;

/**
 * 关于enumo定义的代码信息的工具类。 <br>
 * 这个类有以下功能。<br>
 * <br>
 * enum定义的代码信息的对象（代码值，信息ID） <br>
 * 字符串转换成enum<br>
 * 
 * @author admin
 */
public class FwCodeUtil {

  /** 枚举型定数的缓存 */
  private static Map<Class<? extends Enum<?>>, Map<String, ? extends Enum<?>>> enumConstants =
      new HashMap<Class<? extends Enum<?>>, Map<String, ? extends Enum<?>>>();

  /**
   * 按照指定的代码值的字符串和枚举型，返还枚举型定数
   * 
   * @param <T>
   * @param clazz 枚举型
   * @param code 代码值的字符串
   * @return 枚举型定数
   */
  @SuppressWarnings("unchecked")
  public static <T extends Enum<?> & EnumConstants> T stringToEnum(final Class<T> clazz,
      final String code) {

    Map<String, T> work = null;

    synchronized (enumConstants) {

      work = (Map<String, T>) enumConstants.get(clazz);

      if (work == null) {
        work = new HashMap<String, T>();
        enumConstants.put(clazz, work);

        final T[] values = clazz.getEnumConstants();

        for (T t : values) {
          String cd = t.toString();
          if (cd == null) {
            cd = "";
          }
          work.put(cd, t);
        }
      }
      if (code == null) {
        return work.get("");
      } else {
        return work.get(code);
      }
    }
  }

  /**
   * 更具指定的枚举型，回去代码值和信息ID的对象。<br>
   * 
   * @param <T>
   * @param clazz 枚举型
   * @return CodeModel[] CodeModel数据
   */
  public static synchronized <T extends Enum<T> & EnumConstants> CodeModel[] getCodeMessageIdList(
      final Class<T> clazz) {

    final List<CodeModel> list = new ArrayList<CodeModel>();
    for (EnumConstants ect : clazz.getEnumConstants()) {
      final CodeModel model = new CodeModel();
      model.setCode(ect.toString());
      model.setMessageId(ect.getMessageId());
      list.add(model);
    }
    final CodeModel[] models = list.toArray(new CodeModel[list.size()]);

    return models;
  }
}
