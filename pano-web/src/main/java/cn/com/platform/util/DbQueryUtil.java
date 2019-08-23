package cn.com.platform.util;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.util.ReflectionUtils;

/**
 * db拼接util
 * @author chenzhuoqi
 *
 */
public class DbQueryUtil {
  
  /**
   * 拼接排序信息。
   * 
   * 
   */
  public static String getOrderInfo(Object objClass) {
    String sortName="";
    String sortOrder="";
    Field sortNameField =
        ReflectionUtils.findField(objClass.getClass(), "sortName");
    if (sortNameField != null) {
      sortName = ReflectionUtils.getField(sortNameField, objClass).toString();
    }
    Field sortOrderField =
        ReflectionUtils.findField(objClass.getClass(), "sortOrder");
    if (sortOrderField != null) {
      sortOrder = ReflectionUtils.getField(sortOrderField, objClass).toString();
    }
    sortName = humpToLine(sortName);
    return sortName + " " + sortOrder;
  }
  
  /**
   * 驼峰转换成“_”
   * @param str
   * @return
   */
  public static String humpToLine(String str) {
    StringBuffer sbf = new StringBuffer(str);
    StringBuffer sb = addUnderline(sbf);
    return sb.toString();
  }

  /**
   * 驼峰转换成“_”
   * @param str
   * @return
   */
  public static StringBuffer addUnderline(StringBuffer str) {
    Pattern humpPattern = Pattern.compile("[A-Z]");
    Matcher matcher = humpPattern.matcher(str);
    StringBuffer sb = new StringBuffer(str);
    if (matcher.find()) {
      sb = new StringBuffer();
      matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
      matcher.appendTail(sb);
    } else {
      return sb;
    }
    return addUnderline(sb);
  }
}
