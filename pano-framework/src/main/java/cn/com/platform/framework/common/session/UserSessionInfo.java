package cn.com.platform.framework.common.session;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import lombok.Getter;
import lombok.Setter;

/**
 * 登陆的用户信息
 * 
 * @author 唐友成
 * @date 2018-08-08
 *
 */
public class UserSessionInfo extends User {

  private static final long serialVersionUID = 1L;

  public UserSessionInfo(String username, String password,
      Collection<? extends GrantedAuthority> authorities) throws IllegalArgumentException {
    super(username, password, authorities);
  }

  public UserSessionInfo(String username, String password, boolean enabled,
      boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
      Collection<? extends GrantedAuthority> authorities) throws IllegalArgumentException {
    super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
        authorities);
  }

  /** 用户显示名。 */
  @Getter
  @Setter
  private String userDisplayName;
  /** 登录用户主键。 */
  @Getter
  @Setter
  private String loginUserKey;
  /** 用户角色。 */
  @Getter
  @Setter
  private List<String> roleIds;
  /** 登录用户所属公司。 */
  @Getter
  @Setter
  private String companyId;
  /** 登录用户所属公司名称。 */
  @Getter
  @Setter
  private String companyName;
  /** 是否是直属部门标识。 */
  @Getter
  @Setter
  private String isDirectDepartment;
  /** 登录用户所属部门 。 */
  @Getter
  @Setter
  private String departmentId;
  /** 登录用户所属部门名称 。 */
  @Getter
  @Setter
  private String departmentName;
  /** 登录用户当前区域语言。 */
  @Getter
  @Setter
  private Locale userCurrentLocale;
  /** 浏览器信息。 */
  @Getter
  @Setter
  private Browser browser;
  /** IP地址。 */
  @Getter
  @Setter
  private String ipAddress;
  /** 操作系统信息。 */
  @Getter
  @Setter
  private OperatingSystem operatingSystem;
  /** 最后一次同步到数据库的时间 。 */
  @Getter
  @Setter
  private Date lastSyncDate;


}
