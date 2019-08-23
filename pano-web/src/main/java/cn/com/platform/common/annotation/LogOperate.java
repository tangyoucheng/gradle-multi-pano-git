package cn.com.platform.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import cn.com.platform.framework.code.BusinessType;
import cn.com.platform.framework.code.OperatorType;

/**
 * 自定义操作日志记录注解。
 * 
 * @author 唐友成
 * @date 2019-07-03
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogOperate {
  /**
   * 模块主键。
   */
  public String moduleId() default "";

  /**
   * 模块标题。
   */
  public String moduleTitle() default "";

  /**
   * 数据记录主键。
   */
  public String recordId() default "";

  /**
   * 功能。
   */
  public BusinessType businessType() default BusinessType.OTHER;

  /**
   * 操作人类别。
   */
  public OperatorType operatorType() default OperatorType.MANAGE;

  /**
   * 是否保存请求的参数。
   */
  public boolean isSaveRequestData() default true;
}
