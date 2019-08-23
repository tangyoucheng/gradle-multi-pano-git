package cn.com.platform.common.security;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.framework.common.session.UserSessionInfo;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.platform.mapper.common01.PlatformAdminRoleUser01Mapper;
import cn.com.platform.platform.mapper.common01.PlatformAdminUser01Mapper;
import cn.com.platform.platform.model.common.PlatformAdminRoleUser;
import cn.com.platform.platform.model.common.PlatformAdminRoleUserQuery;
import cn.com.platform.platform.model.common.PlatformAdminUser;
import cn.com.platform.platform.model.common.PlatformAdminUserQuery;
import cn.com.platform.platform.model.common.PlatformAdminUserQuery.Criteria;
import cn.com.platform.util.IpUtil;
import cn.com.platform.util.ServletUtils;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;

/**
 * 继承AbstractUserDetailsAuthenticationProvider，只实装retrieveUser。
 * 
 * @author 唐友成
 * @date 2018-08-23
 *
 */
@Component
public class ShAdminDetailsAuthenticationProvider
    extends AbstractUserDetailsAuthenticationProvider {

  @Autowired
  private PlatformAdminUser01Mapper platformAdminUser01Mapper;
  @Autowired
  private PlatformAdminRoleUser01Mapper platformAdminRoleUser01Mapper;

  @Override
  protected void additionalAuthenticationChecks(UserDetails userDetails,
      UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {}

  @Override
  protected UserDetails retrieveUser(String loginId,
      UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

    Boolean adminLoginPermit = UserSessionUtils.popSession(CommonConstantsIF.ADMIN_LOGIN_PERMIT);
    if (adminLoginPermit == null || !adminLoginPermit) {
      throw new UsernameNotFoundException("It can not be acquired User");
    }

    PlatformAdminUserQuery query = new PlatformAdminUserQuery();
    List<PlatformAdminUser> selectedAdmin = null;
    try {
      Criteria criteria = query.createCriteria();
      criteria.andAdminLoginIdEqualTo(loginId);
      selectedAdmin = platformAdminUser01Mapper.selectByBaseModel(query);
    } catch (Exception e) { // 取得失败的场合
      throw new UsernameNotFoundException("It can not be acquired User");
    }

    // 用户信息取不到的场合
    if (selectedAdmin == null || selectedAdmin.isEmpty()) {
      throw new UsernameNotFoundException("User not found for login id: " + loginId);
    }

    Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
    // for (Role role : user.getRoles()){
    // grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
    // }
    grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
    // AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN");

    // 参数分别是：用户名，密码，用户权限
    UserSessionInfo user =
        new UserSessionInfo(loginId, selectedAdmin.get(0).getAdminPassword(), grantedAuthorities);
    user.setLoginUserKey(selectedAdmin.get(0).getAdminId());
    user.setUserDisplayName(selectedAdmin.get(0).getAdminName());
    user.setUserCurrentLocale(Locale.SIMPLIFIED_CHINESE);

    // 用户角色
    PlatformAdminRoleUserQuery adminRoleUserQuery = new PlatformAdminRoleUserQuery();
    adminRoleUserQuery.createCriteria().andAdminIdEqualTo(user.getLoginUserKey());
    List<PlatformAdminRoleUser> adminRoleUserList =
        platformAdminRoleUser01Mapper.selectByBaseModel(adminRoleUserQuery);

    List<String> roleIds = Lists.newArrayList();
    if (adminRoleUserList != null) {
      for (PlatformAdminRoleUser ciszAdminRoleUser : adminRoleUserList) {
        roleIds.add(ciszAdminRoleUser.getRoleId());
      }
    }
    user.setRoleIds(roleIds);

    HttpServletRequest request = ServletUtils.getRequest();
    String agent = request.getHeader("User-Agent");
    // 解析agent字符串
    UserAgent userAgent = UserAgent.parseUserAgentString(agent);
    // 获取浏览器对象
    Browser browser = userAgent.getBrowser();
    user.setBrowser(browser);

    // IP地址
    String ipAddress = IpUtil.getIpAddr(request);
    user.setIpAddress(ipAddress);

    // 获取操作系统对象
    OperatingSystem operatingSystem = userAgent.getOperatingSystem();
    user.setOperatingSystem(operatingSystem);

    // 返回实装的UserDetails
    return user;
  }

}
