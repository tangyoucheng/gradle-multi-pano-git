/*
 * Copyright(c) 2011
 */
package cn.com.platform.framework.common;

import java.io.Serializable;

/**
 * 键值对类。.
 * 
 * @author 唐友成
 * @date 2018-09-07
 */
public class CodeValueRecord implements Serializable {

  /**
   * 串行版本号。.
   */
  private static final long serialVersionUID = 1L;

  /**
   * 键。.
   */
  private String recordCode = null;

  /**
   * 值。.
   */
  private String recordValue = null;

  /**
   * 构造函数。.
   */
  public CodeValueRecord() {
    super();
  }

  /**
   * 构造函数。.
   * 
   * @param code 键
   * @param value 值
   */
  public CodeValueRecord(String code, String value) {
    super();
    recordCode = code;
    recordValue = value;
  }

  /**
   * 取得键。.
   * 
   * @return 键
   */
  public String getRecordCode() {
    return recordCode;
  }

  /**
   * 设定键。
   * 
   * @param recordCode 键
   */
  public void setRecordCode(String recordCode) {
    this.recordCode = recordCode;
  }

  /**
   * 取得值。
   * 
   * @return 值
   */
  public String getRecordValue() {
    return recordValue;
  }

  /**
   * 设定值。
   * 
   * @param recordValue 值
   */
  public void setRecordValue(String recordValue) {
    this.recordValue = recordValue;
  }

}
