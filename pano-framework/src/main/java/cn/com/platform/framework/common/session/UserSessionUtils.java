package cn.com.platform.framework.common.session;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * 用户会话信息类
 * 
 * @author 唐友成
 * @date 2018-08-09
 *
 */
public class UserSessionUtils {


  /**
   * 取得登陆用户的会话信息
   * 
   * @return 登陆用户的会话信息
   */
  public static UserSessionInfo getUserSessionInfo() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null) {
      return null;
    }
    return (UserSessionInfo) auth.getPrincipal();
  }

  /**
   * 取得用户ID
   * 
   * @return 用户ID
   */
  public static String getUserName() {
    if (getUserSessionInfo() == null) {
      return null;
    }
    return getUserSessionInfo().getUsername();
  }

  /**
   * 取得用户区域
   * 
   * @return 用户ID
   */
  public static Locale getUserCurrentLocale() {
    if (getUserSessionInfo() == null) {
      return Locale.SIMPLIFIED_CHINESE;
    }
    return getUserSessionInfo().getUserCurrentLocale();
  }

  /**
   * 取得登录用户主键
   * 
   * @return 登录用户主键
   */
  public static String getLoginUserKey() {
    if (getUserSessionInfo() == null) {
      return null;
    }
    return getUserSessionInfo().getLoginUserKey();
  }

  /**
   * 取得登录用户所属公司
   * 
   * @return 登录用户所属公司
   */
  public static String getCompanyId() {
    if (getUserSessionInfo() == null) {
      return null;
    }
    return getUserSessionInfo().getCompanyId();
  }

  /**
   * 取得登录用户所属公司名称
   * 
   * @return 登录用户所属公司
   */
  public static String getCompanyName() {
    if (getUserSessionInfo() == null) {
      return null;
    }
    return getUserSessionInfo().getCompanyName();
  }

  /**
   * 取得登录用户所属部门
   * 
   * @return 登录用户所属部门
   */
  public static String getDepartmentId() {
    if (getUserSessionInfo() == null) {
      return null;
    }
    return getUserSessionInfo().getDepartmentId();
  }

  /**
   * 取得登录用户所属部门名称
   * 
   * @return 登录用户所属部门
   */
  public static String getDepartmentName() {
    if (getUserSessionInfo() == null) {
      return null;
    }
    return getUserSessionInfo().getDepartmentName();
  }

  /**
   * 取得登录用户所属部门是公司还是社区 0：社区；1：公司
   * 
   * @return 登录用户所属部门
   */
  public static String getIsDirectDepartment() {
    if (getUserSessionInfo() == null) {
      return null;
    }
    return getUserSessionInfo().getIsDirectDepartment();
  }

  /**
   * 取得登录用户拥有的角色
   * 
   * @return 登录用户所属部门
   */
  public static List<String> getUserRoleIds() {
    if (getUserSessionInfo() == null) {
      return null;
    }
    return getUserSessionInfo().getRoleIds();
  }


  /**
   * 取得登录用户的会话ID
   * 
   * @return 登录用户的会话ID
   */
  public static String getSessionId() {
    return RequestContextHolder.currentRequestAttributes().getSessionId();
  }

  /**
   * RequestAttributesの取得
   * 
   * @return RequestAttributes
   */
  private static RequestAttributes getRequestAttributes() {
    return RequestContextHolder.currentRequestAttributes();
  }

  /**
   * 把数据保存到会话中
   * 
   * @param <T>
   * 
   * @param <T> 保存数据类型
   * @param _key 数据的键值
   * @param _value 保存値
   * @return 保存値
   */
  public static <T> void putSession(String _key, T _value) {
    getRequestAttributes().setAttribute(_key, _value, RequestAttributes.SCOPE_SESSION);
  }

  /**
   * 取得会话中的数据
   * 
   * @param <T> 保存数据类型
   * @param _key 数据的键值
   * @return 保存値
   */
  @SuppressWarnings("unchecked")
  public static <T> T getSession(String _key) {
    return (T) getRequestAttributes().getAttribute(_key, RequestAttributes.SCOPE_SESSION);
  }

  /**
   * 从会话中删除数据
   * 
   * @param _key 数据的键值
   */
  public static void removeSession(String _key) {
    getRequestAttributes().removeAttribute(_key, RequestAttributes.SCOPE_SESSION);
  }

  /**
   * 从会话中取得数据，并把该数据从会话中移除
   * 
   * @param <T> 保存数据类型
   * @param _key 数据的键值
   * @return 保存値
   */
  public static <T> T popSession(String _key) {
    T result_ = getSession(_key);
    removeSession(_key);
    return result_;
  }

}
