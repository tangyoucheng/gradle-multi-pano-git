package cn.com.platform.platform.service.platform01;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import cn.com.platform.platform.mapper.common01.PlatformMember01Mapper;
import cn.com.platform.platform.model.common.PlatformMember;
import cn.com.platform.platform.model.common.PlatformMemberQuery;
import cn.com.platform.common.manager.AsyncManager;
import cn.com.platform.common.manager.factory.AsyncFactory;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.framework.common.session.UserSessionInfo;
import cn.com.platform.util.EasyJson;
import cn.com.platform.util.IpUtil;
import cn.com.platform.util.ServletUtils;
import cn.com.platform.web.BaseService;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;

/**
 * 会员登录注册 服务接口
 * 
 * @author 唐友成
 * @date 2018-08-23
 *
 */
@Service
public class Platform010102Service extends BaseService {

  @Autowired
  private PlatformMember01Mapper platformMember01Mapper;

  /**
   * 会员普通登录验证
   * 
   * @return 返回列表List列表
   */
  public EasyJson<Object> doCheckGeneralLogin(PlatformMember platformzMember) {

    Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
    UserSessionInfo userSessionInfo =
        new UserSessionInfo(platformzMember.getMemberLoginId(), "dumy", grantedAuthorities);
    HttpServletRequest request = ServletUtils.getRequest();
    String agent = request.getHeader("User-Agent");
    // 解析agent字符串
    UserAgent userAgent = UserAgent.parseUserAgentString(agent);
    // 获取浏览器对象
    Browser browser = userAgent.getBrowser();
    userSessionInfo.setBrowser(browser);

    // IP地址
    String ipAddress = IpUtil.getIpAddr(request);
    userSessionInfo.setIpAddress(ipAddress);

    // 获取操作系统对象
    OperatingSystem operatingSystem = userAgent.getOperatingSystem();
    userSessionInfo.setOperatingSystem(operatingSystem);

    PlatformMemberQuery memberQuery = new PlatformMemberQuery();
    memberQuery.createCriteria().andMemberLoginIdEqualTo(platformzMember.getMemberLoginId());
    // 查询用户
    List<PlatformMember> userList = platformMember01Mapper.selectByBaseModel(memberQuery);

    EasyJson<Object> easyJson = new EasyJson<Object>();
    // 帐号信息获取失败
    if (userList == null || userList.isEmpty()) {
      easyJson.setSuccess(false);
      easyJson.setMsg("当前登录的用户不存在");
      // 记录用户登陆日志
      AsyncManager.me().execute(
          AsyncFactory.saveLoginInfo(userSessionInfo, FlagStatus.Disable.toString(), "当前登录的用户不存在"));
      return easyJson;
    }

    // 验证密码是否匹配
    PlatformMember accountInfo = userList.get(0);
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    if (!passwordEncoder.matches(platformzMember.getMemberPassword(), accountInfo.getMemberPassword())) {
      easyJson.setSuccess(false);
      easyJson.setMsg("当前密码错误");
      // 记录用户登陆日志
      AsyncManager.me().execute(
          AsyncFactory.saveLoginInfo(userSessionInfo, FlagStatus.Disable.toString(), "当前密码错误"));
      return easyJson;
    }

    easyJson.setSuccess(true);
    // 记录用户登陆日志
    AsyncManager.me().execute(
        AsyncFactory.saveLoginInfo(userSessionInfo, FlagStatus.Enable.toString(), "登陆系统成功"));

    return easyJson;
  };


  /**
   * 注册处理
   * 
   * @return 返回列表List列表
   */
  public PlatformMember doRegister(PlatformMember platformzMember) {
    platformMember01Mapper.insert(platformzMember);
    return platformzMember;
  };

}
