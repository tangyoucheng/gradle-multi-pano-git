package cn.com.platform.common.aspect;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;
import cn.com.platform.common.manager.AsyncManager;
import cn.com.platform.common.manager.factory.AsyncFactory;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.framework.common.session.UserSessionInfo;
import cn.com.platform.platform.mapper.common01.PlatformOnlineUser01Mapper;
import cn.com.platform.util.SpringUtils;

/**
 * 监听会话结束的切面。
 * 
 * @author 唐友成
 * @date 2018-09-25
 *
 */
@Aspect // 定义一个切面
@Component
// @Configuration
public class AspectSessionDestroyed {

  // 定义切点Pointcut
  @Pointcut("execution(public * javax.servlet.http.HttpSessionListener.sessionDestroyed(..))")
  // 两个..代表所有子目录，最后括号里的两个..代表所有参数
  public void pointCutLogoutCtrl() {}

  /**
   * Before处理。
   * 
   * @param joinPoint JoinPoint
   * @throws Throwable 异常的场合
   */
  @Before("pointCutLogoutCtrl()")
  public void doBefore(JoinPoint joinPoint) throws Throwable {

    HttpSessionEvent se = (HttpSessionEvent) joinPoint.getArgs()[0];
    HttpSession session = se.getSession();

    // 删除当前在线用户数据
    PlatformOnlineUser01Mapper ciszOnlineUser01Mapper = SpringUtils.getBean(PlatformOnlineUser01Mapper.class);
    ciszOnlineUser01Mapper.deleteByPrimaryKey(session.getId());
    // 删除会话已经超时，未及时清除的的在线用户数据
    ciszOnlineUser01Mapper.deleteTimeOutOnlineUser();

    SecurityContextImpl securityContextImpl = (SecurityContextImpl) session
        .getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
    if (securityContextImpl != null && securityContextImpl.getAuthentication() != null) {
      UserSessionInfo userSessionInfo =
          (UserSessionInfo) securityContextImpl.getAuthentication().getPrincipal();
      // 记录用户退出日志
      AsyncManager.me().execute(
          AsyncFactory.saveLoginInfo(userSessionInfo, FlagStatus.Enable.toString(), "退出系统成功"));
    }
  }

  @AfterReturning(returning = "ret", pointcut = "pointCutLogoutCtrl()")
  // returning的值和doAfterReturning的参数名一致
  public void doAfterReturning(JoinPoint joinPoint, Object ret) throws Throwable {}

  /**
   * Around处理。
   * 
   * @param pjp ProceedingJoinPoint
   * @return 处理结果
   * @throws Throwable 异常的场合
   */
  @Around("pointCutLogoutCtrl()")
  public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
    Object returnObject = pjp.proceed();
    // returnObject 为方法的返回值
    return returnObject;
  }

}
