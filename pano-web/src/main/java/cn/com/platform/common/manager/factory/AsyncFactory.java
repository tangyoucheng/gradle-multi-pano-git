package cn.com.platform.common.manager.factory;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;
import java.util.TimerTask;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;
import cn.com.platform.framework.common.session.UserSessionInfo;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.framework.util.FwStringUtils;
import cn.com.platform.platform.mapper.common01.PlatformLoginInfo01Mapper;
import cn.com.platform.platform.mapper.common01.PlatformOnlineUser01Mapper;
import cn.com.platform.platform.mapper.common01.PlatformOperateLog01Mapper;
import cn.com.platform.platform.model.common.PlatformLoginInfo;
import cn.com.platform.platform.model.common.PlatformOnlineUser;
import cn.com.platform.platform.model.common.PlatformOperateLog;
import cn.com.platform.util.ServletUtils;
import cn.com.platform.util.SpringUtils;

/**
 * 异步工厂（产生任务用）。
 * 
 * @author 唐友成
 * @date 2018-12-19
 *
 */
public class AsyncFactory {


  /**
   * 同步session到数据库。
   * 
   * @param status 状态
   * @return 任务task
   */
  public static TimerTask syncSessionToDb(String status) {

    HttpServletRequest request = ServletUtils.getRequest();
    HttpSession httpSession = request.getSession(false);

    UserSessionInfo userSessionInfo = UserSessionUtils.getUserSessionInfo();

    return new TimerTask() {
      @Override
      public void run() {

        PlatformOnlineUser onlineUser = new PlatformOnlineUser();
        // 会话ID
        onlineUser.setSessionId(httpSession.getId());
        // 登陆ID
        onlineUser.setLoginId(userSessionInfo.getUsername());
        // 会话开始时间
        LocalDateTime startDate = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(httpSession.getCreationTime()), TimeZone.getDefault().toZoneId());
        onlineUser.setStartDate(startDate);
        // 会话最后一次访问时间
        LocalDateTime lastAccessedTime =
            LocalDateTime.ofInstant(Instant.ofEpochMilli(httpSession.getLastAccessedTime()),
                TimeZone.getDefault().toZoneId());
        onlineUser.setLastAccessDate(lastAccessedTime);

        // 会话过期时间
        BigDecimal expireTime = NumberUtils.convertNumberToTargetClass(
            httpSession.getMaxInactiveInterval() / 60, BigDecimal.class);
        onlineUser.setExpireTime(expireTime);
        // IP地址
        onlineUser.setIpAddress(userSessionInfo.getIpAddress());
        // onlineUser.setLoginLocation(AddressUtils.getRealAddressByIP(session.getHost()));

        // 浏览器
        onlineUser.setBrowser(userSessionInfo.getBrowser().getName());
        // 操作系统
        onlineUser.setOperatingSystem(userSessionInfo.getOperatingSystem().getName());
        // 状态
        onlineUser.setStatus(status);

        // 更新数据库
        PlatformOnlineUser01Mapper ciszOnlineUser01Mapper =
            SpringUtils.getBean("platformOnlineUser01Mapper");
        PlatformOnlineUser selectedOnlineUser =
            ciszOnlineUser01Mapper.selectByPrimaryKey(onlineUser.getSessionId());
        if (selectedOnlineUser != null && !StringUtils.isEmpty(selectedOnlineUser.getSessionId())) {
          ciszOnlineUser01Mapper.updateByPrimaryKey(onlineUser);
        } else {
          ciszOnlineUser01Mapper.insert(onlineUser);
        }
      }
    };
  }

  /**
   * 操作日志记录。
   * 
   * @param operLog 操作日志信息
   * @return 任务task
   */
  public static TimerTask recordOperatr(final PlatformOperateLog operLog) {
    return new TimerTask() {
      @Override
      public void run() {
        // 远程查询操作地点
        // operLog.setOperLocation(AddressUtils.getRealAddressByIP(operLog.getOperIp()));
        SpringUtils.getBean(PlatformOperateLog01Mapper.class).insert(operLog);
      }
    };
  }

  /**
   * 记录登陆信息。
   * 
   * @param userSessionInfo 登陆信息
   * @param status 状态
   * @param message 消息
   * @return 任务task
   */
  public static TimerTask saveLoginInfo(final UserSessionInfo userSessionInfo, final String status,
      final String message) {

    return new TimerTask() {
      @Override
      public void run() {
        PlatformLoginInfo loginInfo = new PlatformLoginInfo();
        loginInfo.setInfoId(FwStringUtils.getUniqueId());
        loginInfo.setLoginId(userSessionInfo.getUsername());
        loginInfo.setIpAddress(userSessionInfo.getIpAddress());
        loginInfo.setBrowser(userSessionInfo.getBrowser().getName());
        loginInfo.setOperatingSystem(userSessionInfo.getOperatingSystem().getName());
        loginInfo.setMsg(message);
        loginInfo.setLoginTime(LocalDateTime.now());
        // 登录状态（1成功 0失败）
        loginInfo.setStatus(status);
        // 插入数据
        PlatformLoginInfo01Mapper platformLoginInfo01Mapper = SpringUtils.getBean(PlatformLoginInfo01Mapper.class);
        platformLoginInfo01Mapper.insert(loginInfo);
      }
    };
  }
}
