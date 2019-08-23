package cn.com.platform.common.aspect;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ObjectUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import cn.com.platform.framework.common.StandardConstantsIF;
import cn.com.platform.framework.form.AbstractForm;
import cn.com.platform.util.IpUtil;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;

/**
 * Controller的切面。
 * 
 * @author 唐友成
 * @date 2018-09-25
 *
 */
@Aspect // 定义一个切面
@Component
// @Configuration
public class AspectController {
  
  @Autowired
  public ObjectMapper objectMapper;

  // 定义切点Pointcut
  @Pointcut("execution(public * cn.com..controller..*(..))")
  // 两个..代表所有子目录，最后括号里的两个..代表所有参数
  public void pointCutLogCtrl() {}

  @Before("pointCutLogCtrl()")
  public void doBefore(JoinPoint joinPoint) throws Throwable {
    // Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
    // logInfo.append("┗━━━━━" + joinPoint.getTarget().getClass().getSimpleName() + "━━━");

    // logger.info(logInfo.toString());
  }

  @AfterReturning(returning = "ret", pointcut = "pointCutLogCtrl()")
  // returning的值和doAfterReturning的参数名一致
  public void doAfterReturning(JoinPoint joinPoint, Object ret) throws Throwable {
    // Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
    // StringBuffer logInfo = new StringBuffer();
    // 记录下请求内容
    // logInfo.append("END");
    // 处理完请求，返回内容(返回值太复杂时，打印的是物理存储空间的地址)
    // logInfo.append(StandardConstantsIF.LINE_SEPARATOR);
    // logInfo.append("┣返回值：" + ret);
    // logInfo.append(StandardConstantsIF.LINE_SEPARATOR);
    // logInfo.append("┗━━━━━" + joinPoint.getTarget().getClass().getSimpleName() + "━━━");
    // logger.info(logInfo.toString());
  }

  /**
   * Around处理。
   * 
   * @param pjp ProceedingJoinPoint
   * @return 处理结果
   * @throws Throwable 异常
   */
  @Around("pointCutLogCtrl()")
  public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
    Logger logger = LoggerFactory.getLogger(pjp.getTarget().getClass());
    long startTime = System.currentTimeMillis();
    StringBuffer logInfo = new StringBuffer();
    Object returnObject = null;
    // ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
    ObjectWriter ow = objectMapper.writer();
    try {
      // 接收到请求，记录请求内容
      ServletRequestAttributes attributes =
          (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
      HttpServletRequest request = attributes.getRequest();
      String agent = request.getHeader("User-Agent");
      // 解析agent字符串
      UserAgent userAgent = UserAgent.parseUserAgentString(agent);
      // 获取浏览器对象
      Browser browser = userAgent.getBrowser();
      // 获取操作系统对象
      OperatingSystem operatingSystem = userAgent.getOperatingSystem();
      // 记录下请求内容
      // logInfo.append("START");
      logInfo.append(StandardConstantsIF.LINE_SEPARATOR);
      logInfo.append("┏━━━━━【" + pjp.getTarget().getClass().getSimpleName() + "】━━━━━");
      // 操作系统
      logInfo.append(StandardConstantsIF.LINE_SEPARATOR);
      logInfo.append("┣ 操作系统：" + operatingSystem.getDeviceType() + "|" + operatingSystem.getName());
      // 浏览器
      logInfo.append(StandardConstantsIF.LINE_SEPARATOR);
      logInfo.append("┣ 浏览器：" + browser.getName() + "|" + browser.getRenderingEngine() + "|"
          + userAgent.getBrowserVersion());
      // 获取真实的ip地址
      logInfo.append(StandardConstantsIF.LINE_SEPARATOR);
      logInfo.append("┣ IP地址：" + IpUtil.getIpAddr(request));
      logInfo.append(StandardConstantsIF.LINE_SEPARATOR);
      logInfo.append("┣ 请求地址： " + request.getRequestURL().toString());
      logInfo.append(StandardConstantsIF.LINE_SEPARATOR);
      logInfo.append("┣ HTTP方法：" + request.getMethod());
      logInfo.append(StandardConstantsIF.LINE_SEPARATOR);
      logInfo.append("┣ 调用方法：" + pjp.getSignature().getDeclaringTypeName() + "."
          + pjp.getSignature().getName());
      // 参数
      logInfo.append(StandardConstantsIF.LINE_SEPARATOR);
      // 不序列化的参数
      List<Object> excludeArgs = new ArrayList<>();
      // login的场合
      excludeArgs.add("FirewalledResponse");
      excludeArgs.add("StandardSessionFacade");
      excludeArgs.add("RequestWrapper");
      excludeArgs.add("StatHttpServletResponseWrapper");
      // logout的场合
      excludeArgs.add("Servlet3SecurityContextHolderAwareRequestWrapper");
      excludeArgs.add("HeaderWriterResponse");
      // 需要序列化的参数
      List<Object> userArgs = new ArrayList<>();
      for (Object arg : pjp.getArgs()) {
        // 表单使用Form规则的场合
        if (arg instanceof AbstractForm || arg.getClass().toString().endsWith("Form")) {
          userArgs.add(arg);
          break;
        }
        if (!excludeArgs.contains(arg.getClass().getSimpleName()) && ObjectUtils.isNotEmpty(arg)) {
          // 反向代理的场合
          if (arg instanceof Proxy) {
            continue;
          }
          // org.springframework.ui.Model 过滤
          if (arg instanceof BindingAwareModelMap) {
            BindingAwareModelMap argModelMap = (BindingAwareModelMap) arg;
            Object[] attributeArray = argModelMap.values().stream()
                .filter(modelAttribute -> !(modelAttribute instanceof BeanPropertyBindingResult))
                .toArray();
            userArgs.add(attributeArray);
            continue;
          }
          userArgs.add(arg);
          continue;
        }
      }
      String userArg = ow.writeValueAsString(userArgs);
      if (userArg.length() > 1024) {
        logInfo.append("┣ 参数：" + Arrays.deepToString(pjp.getArgs()));
      } else {
        logInfo.append("┣ 参数：" + userArg);
      }

      returnObject = pjp.proceed();

      // 处理完请求，返回内容(返回值太复杂时，打印的是物理存储空间的地址)
      // 耗时
      logInfo.append(StandardConstantsIF.LINE_SEPARATOR);
      logInfo.append("┣ 耗时：" + (System.currentTimeMillis() - startTime) + "毫秒");
      // 返回值序列化后长度超过1024的场合，就不序列化
      logInfo.append(StandardConstantsIF.LINE_SEPARATOR);
      String userReturnObject = ow.writeValueAsString(returnObject);
      if (userReturnObject.length() > 1024) {
        logInfo.append("┣ 返回值：" + returnObject);
      } else {
        logInfo.append("┣ 返回值：" + userReturnObject);
      }
      // logInfo.append("┣返回值：" + returnObject);
      logInfo.append(StandardConstantsIF.LINE_SEPARATOR);
      logInfo.append("┗━━━━━【" + pjp.getTarget().getClass().getSimpleName() + "】━━━━━");

      logger.info(logInfo.toString());

    } catch (Exception e) {
      // 耗时
      logInfo.append(StandardConstantsIF.LINE_SEPARATOR);
      logInfo.append("┣ 耗时：" + (System.currentTimeMillis() - startTime) + "毫秒");
      logInfo.append(StandardConstantsIF.LINE_SEPARATOR);
      logInfo.append("┣ 返回值：" + e.getMessage());
      logInfo.append(StandardConstantsIF.LINE_SEPARATOR);
      logInfo.append("┗━━━━━【" + pjp.getTarget().getClass().getSimpleName() + "】━━━━━");


      logger.info(logInfo.toString());
      throw e;
    }

    // returnObject 为方法的返回值
    return returnObject;
  }

}
