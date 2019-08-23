package cn.com.platform.common.aspect;

import java.util.Arrays;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import cn.com.platform.framework.common.StandardConstantsIF;

/**
 * Service的切面。
 * 
 * @author 唐友成
 * @date 2018-09-25
 *
 */
@Aspect // 定义一个切面
@Component
// @Configuration
public class AspectService {

  @Autowired
  public ObjectMapper objectMapper;

  // 定义切点Pointcut
  @Pointcut("execution(public * cn.com..service..*(..))")
  // 两个..代表所有子目录，最后括号里的两个..代表所有参数
  public void pointCutLogService() {}

  @Before("pointCutLogService()")
  public void doBefore(JoinPoint joinPoint) throws Throwable {
    // Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());

    // logger.info(logInfo.toString());
  }

  @AfterReturning(returning = "ret", pointcut = "pointCutLogService()")
  // returning的值和doAfterReturning的参数名一致
  public void doAfterReturning(JoinPoint joinPoint, Object ret) throws Throwable {
    // Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
    // StringBuffer logInfo = new StringBuffer();
    // // 记录下请求内容
    // logInfo.append("END");
    // // 处理完请求，返回内容(返回值太复杂时，打印的是物理存储空间的地址)
    // logInfo.append(StandardConstantsIF.LINE_SEPARATOR);
    // logInfo.append("====>【Service】返回值：" + ret);
    // logger.info(logInfo.toString());
  }

  /**
   * Around处理。
   * 
   * @param pjp ProceedingJoinPoint
   * @return 处理结果
   * @throws Throwable 异常的场合
   */
  @Around("pointCutLogService()")
  public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
    Logger logger = LoggerFactory.getLogger(pjp.getTarget().getClass());
    long startTime = System.currentTimeMillis();
    StringBuffer logInfo = new StringBuffer();
    Object returnObject = null;
    // ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
    ObjectWriter ow = objectMapper.writer();

    // 记录下请求内容
    logInfo.append(StandardConstantsIF.LINE_SEPARATOR);
    logInfo.append("┏━━━━━【" + pjp.getTarget().getClass().getSimpleName() + "】━━━━━");
    // 调用方法
    logInfo.append(StandardConstantsIF.LINE_SEPARATOR);
    logInfo.append(
        "┣ 调用方法：" + pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName());
    // 参数
    logInfo.append(StandardConstantsIF.LINE_SEPARATOR);
    String userArg = ow.writeValueAsString(pjp.getArgs());
    if (userArg.length() > 1024) {
      logInfo.append("┣ 参数：" + Arrays.deepToString(pjp.getArgs()));
    } else {
      logInfo.append("┣ 参数：" + userArg);
    }
    try {
      returnObject = pjp.proceed();

      // 处理完请求，返回内容(返回值太复杂时，打印的是物理存储空间的地址)
      logInfo.append(StandardConstantsIF.LINE_SEPARATOR);
      logInfo.append("┣ 耗时： " + (System.currentTimeMillis() - startTime) + "毫秒");
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
      // 处理完请求，返回内容(返回值太复杂时，打印的是物理存储空间的地址)
      logInfo.append(StandardConstantsIF.LINE_SEPARATOR);
      logInfo.append("┣ 耗时： " + (System.currentTimeMillis() - startTime) + "毫秒");
      logInfo.append(StandardConstantsIF.LINE_SEPARATOR);
      logInfo.append("┣返回值：" + e.getMessage());
      logInfo.append(StandardConstantsIF.LINE_SEPARATOR);
      logInfo.append("┗━━━━━【" + pjp.getTarget().getClass().getSimpleName() + "】━━━━━");

      logger.info(logInfo.toString());
      throw e;
    }
    return returnObject;
  }

}
