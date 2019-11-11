package cn.com.platform.common.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import cn.com.platform.platform.model.common.PlatformOperateLog;
import cn.com.platform.common.annotation.LogLabel;
import cn.com.platform.common.annotation.LogOperate;
import cn.com.platform.common.annotation.LogTargetClass;
import cn.com.platform.common.manager.AsyncManager;
import cn.com.platform.common.manager.factory.AsyncFactory;
import cn.com.platform.framework.code.BusinessStatus;
import cn.com.platform.framework.common.session.UserSessionInfo;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.framework.util.FwStringUtils;
import cn.com.platform.util.IpUtil;
import cn.com.platform.util.ServletUtils;

/**
 * 操作日志记录处理。
 * 
 * @author 唐友成
 * @date 2019-07-03
 */
@Aspect
@Component
public class AspectLogOperate {
  private static final Logger log = LoggerFactory.getLogger(AspectLogOperate.class);

  @Autowired
  public ObjectMapper objectMapper;
  
  // 配置织入点
  @Pointcut("@annotation(cn.com.platform.common.annotation.LogOperate)")
  public void logPointCut() {}

  /**
   * 前置通知 用于拦截操作。
   *
   * @param joinPoint 切点
   */
  @AfterReturning(pointcut = "logPointCut()")
  public void doBefore(JoinPoint joinPoint) {
    handleLog(joinPoint, null);
  }

  /**
   * 拦截异常操作。
   * 
   * @param joinPoint JoinPoint
   * @param e 异常的场合
   */
  @AfterThrowing(value = "logPointCut()", throwing = "e")
  public void doAfter(JoinPoint joinPoint, Exception e) {
    handleLog(joinPoint, e);
  }

  protected void handleLog(final JoinPoint joinPoint, final Exception e) {
    try {
      // 获得注解
      LogOperate controllerLog = getAnnotationLogOperate(joinPoint, LogOperate.class);
      if (controllerLog == null) {
        return;
      }


      // *========数据库日志=========*//
      PlatformOperateLog operLog = new PlatformOperateLog();
      operLog.setOperateId(FwStringUtils.getUniqueId());
      operLog.setSuccessStatus(BusinessStatus.SUCCESS.ordinal());

      operLog.setOperateUrl(ServletUtils.getRequest().getRequestURI());
      // 获取当前的用户
      UserSessionInfo currentUser = UserSessionUtils.getUserSessionInfo();
      if (currentUser != null) {
        operLog.setOperateName(currentUser.getUsername());
        if (StringUtils.isNotEmpty(currentUser.getDepartmentId())
            && StringUtils.isNotEmpty(currentUser.getDepartmentName())) {
          operLog.setDepartmentName(currentUser.getDepartmentName());
        }
      }

      if (e != null) {
        operLog.setSuccessStatus(BusinessStatus.FAIL.ordinal());
        operLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
      }
      // 设置方法名称
      String className = joinPoint.getTarget().getClass().getName();
      String methodName = joinPoint.getSignature().getName();
      operLog.setMethod(className + "." + methodName + "()");
      // 处理设置注解上的参数
      getControllerMethodDescription(joinPoint, controllerLog, operLog);
      // 获取真实的ip地址
      operLog.setOperateIp(IpUtil.getIpAddr(ServletUtils.getRequest()));
      // 操作时间
      operLog.setOperateTime(LocalDateTime.now());
      // 保存数据库
      AsyncManager.me().execute(AsyncFactory.recordOperatr(operLog));
    } catch (Exception exp) {
      // 记录本地异常日志
      log.error("==前置通知异常==");
      log.error("异常信息:{}", exp.getMessage());
      exp.printStackTrace();
    }
  }

  /**
   * 获取注解中对方法的描述信息 用于Controller层注解。
   * 
   * @param log 日志实例
   * @param operLog 操作日志实例
   * @throws Exception 异常的场合
   */
  public void getControllerMethodDescription(JoinPoint joinPoint, LogOperate log,
      PlatformOperateLog operLog) throws Exception {
    // 设置action动作
    operLog.setBusinessType(log.businessType().ordinal());
    // 设置模块主键
    operLog.setModuleId(log.moduleId());
    // 设置模块标题
    operLog.setModuleTitle(log.moduleTitle());
    // 设置数据记录主键
    operLog.setRecordId(FwStringUtils.getUniqueId());
    // 设置操作人类别
    operLog.setOperatorType(log.operatorType().ordinal());
    // 是否需要保存request，参数和值
    if (log.isSaveRequestData()) {
      // 获取参数的信息，传入到数据库中。
      setRequestValue(joinPoint, operLog);
    }
  }

  /**
   * 获取请求的参数，放到log中。
   * 
   * @param joinPoint JoinPoint
   * @param operLog 操作日志
   * @throws Exception 异常
   */
  private void setRequestValue(JoinPoint joinPoint, PlatformOperateLog operLog)
      throws Exception {
    // Map<String, String[]> map = ServletUtils.getRequest().getParameterMap();

    Map<String, Object> paramsMap = new HashMap<String, Object>();
    for (Object arg : joinPoint.getArgs()) {

      if (arg.getClass().isAnnotationPresent(LogTargetClass.class)) {
        Class<?> targetClass = arg.getClass();


        // 设置数据记录主键
        Field logRecordKeyField = ReflectionUtils.findField(targetClass, "logRecordId");
        ReflectionUtils.makeAccessible(logRecordKeyField);
        String logRecordKey = (String) ReflectionUtils.getField(logRecordKeyField, arg);
        if (StringUtils.isNotEmpty(logRecordKey)) {
          operLog.setRecordId(logRecordKey);
        }

        // 保存的原始数据
        Field editBeforDataJsonField = ReflectionUtils.findField(targetClass, "editBeforeDataJson");
        ReflectionUtils.makeAccessible(editBeforDataJsonField);
        String editBeforeDataJson = (String) ReflectionUtils.getField(editBeforDataJsonField, arg);
        Object editBeforeClass = null;
        if (!Objects.isNull(editBeforeDataJson)) {
          editBeforeClass = objectMapper.readValue(editBeforeDataJson, targetClass);
        }

        Field[] allFields = targetClass.getDeclaredFields();
        // 得到所有需要打印日志的项目
        for (Field field : allFields) {
          LogLabel fieldLogLabel = getFieldAnnotation(field, LogLabel.class);
          if (fieldLogLabel != null) {
            ReflectionUtils.makeAccessible(field);
            Object fieldValue = ReflectionUtils.getField(field, arg);
            if (Objects.isNull(editBeforeClass)) {
              paramsMap.put(fieldLogLabel.label(), fieldValue);
            } else {
              Object fieldBeforeValue = ReflectionUtils.getField(field, editBeforeClass);
              // 变更前，变更后的数据都为空的场合
              if (ObjectUtils.isEmpty(fieldBeforeValue) && ObjectUtils.isEmpty(fieldValue)) {
                continue;
              }
              // 变更前，变更后的数据不相等的场合
              if (!ObjectUtils.nullSafeEquals(fieldBeforeValue, fieldValue)) {
                paramsMap.put(fieldLogLabel.label(), fieldBeforeValue + "⇒" + fieldValue);
              }
            }
          }
        }
      }
    }
    ObjectWriter ow = objectMapper.writer();
    String params = ow.writeValueAsString(paramsMap);
    operLog.setOperateParam(params);
  }

  /**
   * 是否存在指定的注解，如果存在就获取。
   * 
   * @param joinPoint JoinPoint
   * @return LogOperate
   * @throws Exception 异常的场合
   */
  private <T extends Annotation> T getAnnotationLogOperate(JoinPoint joinPoint,
      Class<T> customAnnotation) throws Exception {
    Signature signature = joinPoint.getSignature();
    MethodSignature methodSignature = (MethodSignature) signature;
    Method method = methodSignature.getMethod();

    if (method != null) {
      return method.getAnnotation(customAnnotation);
    }
    return null;
  }

  /**
   * 是否存在LogLabel注解，如果存在就获取。
   * 
   * @param field Field
   * @return LogLabel
   * @throws Exception 异常的场合
   */
  private <T extends Annotation> T getFieldAnnotation(Field field, Class<T> customAnnotation)
      throws Exception {

    if (field.isAnnotationPresent(customAnnotation)) {
      return field.getAnnotation(customAnnotation);
    }
    return null;
  }
}
