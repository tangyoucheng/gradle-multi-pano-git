package cn.com.platform.framework.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 序列化工具类。
 * 
 * @author 唐友成
 * @date 2019-07-06
 * 
 */
public abstract class FwJsonUtil {

  /**
   * <p>
   * 機能:。
   * </p>
   * <p>
   * 説明を自由に書く。
   * </p>
   * <p>
   * 備考を自由に書く。
   * </p>
   * 
   * @author MBPCD : 陳洋
   * @param object Object
   * @return 戻り値の説明
   */
  public static String objectToJson(Object object) {
    Gson gson = new Gson();
    return gson.toJson(object, Object.class);
  }

  /**
   * <p>
   * 機能: リスト→ jsonString を変更
   * </p>
   * <p>
   * 説明:
   * </p>
   * <p>
   * 備考:
   * </p>
   * 
   * @author MBPCD : 陳洋
   * @param fromList 変更リスト
   * @return listのjsonStr[{xxx:xxx,xxx:xxx},{xxx:xxx,xxx:xxx}]
   */
  public static String listToJson(List<?> fromList) {
    Gson gson = new Gson();
    String jsonStr =
        gson.toJson(fromList, new com.google.gson.reflect.TypeToken<List<?>>() {}.getType());
    return jsonStr;
  }

  /**
   * <p>
   * 機能: json String.
   * </p>
   * <p>
   * 説明:
   * </p>
   * <p>
   * 備考:
   * </p>
   * 
   * @author MBPCD : 陳洋
   * @param jsonStr 変更jsonStr
   * @param clazz List<?> ?.class
   * @return 変更後でリスト
   */
  public static List jsonToList(String jsonStr, Class clazz) {
    Gson gson = new Gson();
    Type objectType = type(List.class, clazz);
    return gson.fromJson(jsonStr, objectType);
  }

  private static ParameterizedType type(final Class raw, final Type... args) {
    return new ParameterizedType() {
      public Type getRawType() {
        return raw;
      }

      public Type[] getActualTypeArguments() {
        return args;
      }

      public Type getOwnerType() {
        return null;
      }
    };
  }

  /**
   * jsonをオブジェクトに変換する。
   * 
   * @param jsonStr 序列化字符串
   * @param clazz 对象
   * @return
   */
  public static Object objectFromJson(String jsonStr, Class clazz) {
    Gson gson = new Gson();
    return gson.fromJson(jsonStr, clazz);
  }


  /**
   * jsonをオブジェクトに変換する。
   * 
   * @param <clazz>
   * 
   * @param jsonStr 序列化字符串
   * @param clazz 对象
   * @return
   */
  public static <clazz> List listFromjson(String jsonStr, Class clazz) {
    Gson gson = new Gson();
    Type objectType = new TypeToken<List<clazz>>() {}.getType();
    return gson.fromJson(jsonStr, objectType);
  }
}
