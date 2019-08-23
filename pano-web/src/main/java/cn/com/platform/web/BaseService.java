package cn.com.platform.web;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.framework.common.CodeValueRecord;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.framework.service.StandardService;

/**
 * 共通Service。
 * 
 * @ @author 唐友成
 * @date 2019-07-31
 *
 */
@Service
public class BaseService extends StandardService {

  @Autowired
  public ObjectMapper objectMapper;



  /**
   * 创建审查信息。
   * 
   * @param auditableClass 保存审查信息类
   */
  public void createAudit(Object auditableClass) {

    Field deleteFlagField = ReflectionUtils.findField(auditableClass.getClass(), "deleteFlag");
    if (deleteFlagField != null) {
      ReflectionUtils.makeAccessible(deleteFlagField);
      if (TypeUtils.isAssignable(deleteFlagField.getType(), String.class)) {
        // true:1 false:0 0为未删除 1为删除
        ReflectionUtils.setField(deleteFlagField, auditableClass, FlagStatus.Disable.toString());
      } else {
        // true:1 false:0 0为未删除 1为删除
        ReflectionUtils.setField(deleteFlagField, auditableClass, false);
      }
    }
    Field createUserIdField = ReflectionUtils.findField(auditableClass.getClass(), "createUserId");
    if (createUserIdField != null) {
      ReflectionUtils.makeAccessible(createUserIdField);
      ReflectionUtils.setField(createUserIdField, auditableClass, UserSessionUtils.getUserName());
    }
    Field createDateField = ReflectionUtils.findField(auditableClass.getClass(), "createDate");
    if (createDateField != null) {
      ReflectionUtils.makeAccessible(createDateField);
      ReflectionUtils.setField(createDateField, auditableClass, LocalDateTime.now());
    }
    Field lastUpdateUserIdField =
        ReflectionUtils.findField(auditableClass.getClass(), "lastUpdateUserId");
    if (lastUpdateUserIdField != null) {
      ReflectionUtils.makeAccessible(lastUpdateUserIdField);
      ReflectionUtils.setField(lastUpdateUserIdField, auditableClass,
          UserSessionUtils.getUserName());
    }
    Field lastUpdateDate = ReflectionUtils.findField(auditableClass.getClass(), "lastUpdateDate");
    if (lastUpdateDate != null) {
      ReflectionUtils.makeAccessible(lastUpdateDate);
      ReflectionUtils.setField(lastUpdateDate, auditableClass, LocalDateTime.now());
    }
  }

  /**
   * 更新审查信息。
   * 
   * @param auditableClass 保存审查信息类
   */
  public void updateAudit(Object auditableClass) {
    Field lastUpdateUserIdField =
        ReflectionUtils.findField(auditableClass.getClass(), "lastUpdateUserId");
    if (lastUpdateUserIdField != null) {
      ReflectionUtils.makeAccessible(lastUpdateUserIdField);
      ReflectionUtils.setField(lastUpdateUserIdField, auditableClass,
          UserSessionUtils.getUserName());
    }
    Field lastUpdateDate = ReflectionUtils.findField(auditableClass.getClass(), "lastUpdateDate");
    if (lastUpdateDate != null) {
      ReflectionUtils.makeAccessible(lastUpdateDate);
      ReflectionUtils.setField(lastUpdateDate, auditableClass, LocalDateTime.now());
    }
  }


}
