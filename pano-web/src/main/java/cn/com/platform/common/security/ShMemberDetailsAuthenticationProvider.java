package cn.com.platform.common.security;

import java.util.HashMap;
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
import cn.com.platform.platform.mapper.common01.PlatformRoleUser01Mapper;
import cn.com.platform.platform.model.common.PlatformRoleUser;
import cn.com.platform.platform.model.common.PlatformRoleUserQuery;
import cn.com.platform.platform.model.common.PlatformRoleUserQuery.Criteria;
import cn.com.platform.platformz.mapper.platformz02.Platformz0201Mapper;
import cn.com.platform.platformz.model.platformz02.Platformz0201Model;
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
public class ShMemberDetailsAuthenticationProvider
    extends AbstractUserDetailsAuthenticationProvider {

  @Autowired
  private Platformz0201Mapper platformz0201Mapper;
  @Autowired
  private PlatformRoleUser01Mapper platformRoleUser01Mapper;

  @Override
  protected void additionalAuthenticationChecks(UserDetails userDetails,
      UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {}

  @Override
  protected UserDetails retrieveUser(String loginId,
      UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    Boolean memberLoginPermit = UserSessionUtils.popSession(CommonConstantsIF.MEMBER_LOGIN_PERMIT);
    if (memberLoginPermit == null || !memberLoginPermit) {
      throw new UsernameNotFoundException("It can not be acquired User");
    }
    List<Platformz0201Model> selectedMember = null;
    try {
      // SQL文的参数
      HashMap<String, Object> parameter = new HashMap<String, Object>();
      // 删除标识
      parameter.put("memberLoginId", loginId);
      selectedMember = platformz0201Mapper.selectMemberSessionInfo(parameter);
    } catch (Exception e) { // 取得失败的场合
      throw new UsernameNotFoundException("It can not be acquired User");
    }

    // 用户信息取不到的场合
    if (selectedMember == null || selectedMember.isEmpty()) {
      throw new UsernameNotFoundException("not found : " + loginId);
    }

    Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
    // for (Role role : user.getRoles()){
    // grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
    // }
    grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
    // AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_MEMBER");

    // 参数分别是：用户名，密码，用户权限
    UserSessionInfo user =
        new UserSessionInfo(loginId, selectedMember.get(0).getMemberPassword(), grantedAuthorities);
    user.setLoginUserKey(selectedMember.get(0).getMemberId());
    
    // 用户角色
    PlatformRoleUserQuery roleUserQuery = new PlatformRoleUserQuery();
    Criteria roleUserQueryCriteria = roleUserQuery.createCriteria();
    roleUserQueryCriteria.andMemberIdEqualTo(user.getLoginUserKey());
    List<PlatformRoleUser> roleUserList = platformRoleUser01Mapper.selectByBaseModel(roleUserQuery);
    
    List<String> roleIds = Lists.newArrayList();
    if (roleUserList != null) {
      for (PlatformRoleUser ciszRoleUser : roleUserList) {
        roleIds.add(ciszRoleUser.getRoleId());
      }
    }
    user.setRoleIds(roleIds);
    
    // 公司
    user.setCompanyId(selectedMember.get(0).getCompanyId());
    user.setCompanyName(selectedMember.get(0).getCompanyName());
    // 是否是公司直属部门标识
    user.setIsDirectDepartment(selectedMember.get(0).getIsDirectDepartment());
    // 部门
    user.setDepartmentId(selectedMember.get(0).getDepartmentId());
    user.setDepartmentName(selectedMember.get(0).getDepartmentName());
    user.setUserDisplayName(selectedMember.get(0).getMemberName());
    user.setUserCurrentLocale(Locale.SIMPLIFIED_CHINESE);

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
