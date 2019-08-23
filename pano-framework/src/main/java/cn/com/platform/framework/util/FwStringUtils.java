/*
 * Copyright(c) 2011
 */

package cn.com.platform.framework.util;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import org.springframework.util.StringUtils;
import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.framework.common.unique_id.Identifier;


/**
 * 文字类型相关的工具类。
 * 
 * @author 唐友成
 * @date 2019-02-19
 */
public final class FwStringUtils {

  /** 通用数字格式。 */
  private static final NumberFormat FORMATTER_GENERIC_NUMBER = NumberFormat.getNumberInstance();

  /** 空文字列。 **/
  public static final String EMPTY = "";

  /**
   * StringUtils 的构造。
   */
  private FwStringUtils() {}

  /**
   * defaultString。
   * 
   * @param value 対象数列
   * @param isEditComma カンマ编辑を行う場合 true
   * @return String 変換された値
   */
  public static String defaultString(BigDecimal value, boolean isEditComma) {
    if (value == null) {
      return "";
    }
    return isEditComma ? FORMATTER_GENERIC_NUMBER.format(value) : value.toPlainString();
  }

  /**
   * 唯一标识做成。
   * 
   * @return 唯一标识
   * @throws SystemException 异常的场合
   */
  public static String getUniqueId() {
    String uniqueid;

    Identifier identifier = new Identifier();
    uniqueid = identifier.get();
    return uniqueid;
  }

  /**
   * 是否包含字符串。
   * 
   * @param str 验证字符串
   * @param strs 字符串组
   * @return 包含返回true
   */
  public static boolean inStringIgnoreCase(String str, String... strs) {
    if (str != null && strs != null) {
      for (String s : strs) {
        if (str.equalsIgnoreCase(trim(s))) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * 去空格。
   * 
   * @param str 要处理的内容
   * @return
   */
  public static String trim(String str) {
    return (str == null ? "" : str.trim());
  }

  /**
   * ISO_8859_1 转 UTF_8。
   * 
   * @throws Exception 异常的场合
   */
  public static String toUtf8FromDefault(String content) throws Exception {
    String inContent = "";
    if (content != null) {
      inContent = content;
    }
    return new String(inContent.getBytes(StandardCharsets.ISO_8859_1.name()),
        StandardCharsets.UTF_8.name());
  }

  /**
   * <p>
   * 模糊匹配查询参数生成。
   * </p>
   * 
   * @param param 查询内容
   * @return String
   */
  public static String getMatchParameter(String param) {
    String result = null;
    if (StringUtils.isEmpty(param)) {
      result = "%%";
    } else {
      result = "%" + param + "%";
    }
    return result;
  }

  /**
   * <p>
   * 前匹配查询参数生成。
   * </p>
   * 
   * @param param 查询内容。
   * @return String
   */
  public static String getPreMatchParameter(String param) {
    String result = null;
    if (StringUtils.isEmpty(param)) {
      result = "%%";
    } else {
      result = "%" + param;
    }
    return result;
  }

  /**
   * <p>
   * 后匹配查询参数生成。
   * </p>
   * 
   * @param param 查询内容
   * @return String
   */
  public static String getPostMatchParameter(String param) {
    String result = null;
    if (StringUtils.isEmpty(param)) {
      result = "%%";
    } else {
      result = param + "%";
    }
    return result;
  }

}
