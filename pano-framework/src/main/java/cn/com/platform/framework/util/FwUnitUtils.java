/*
 * Copyright(c) 2011
 */
package cn.com.platform.framework.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 単位を取り扱うユーティリティ.
 * 
 * @author 唐友成
 * @date 2019-02-19
 */
public final class FwUnitUtils {

  /** バイト単位 */
  private static final BigDecimal UNIT_BYTE = BigDecimal.valueOf(1024);

  /** バイトスケール */
  private static final int DEFAULT_SCALE_BYTE = 4;

  /** バイト操作用コンテキスト */
  private static final RoundingMode ROUNDING_MODE_BYTE = RoundingMode.UP;

  /**
   * 非公開コンストラクタ.
   */
  private FwUnitUtils() {
    super();
  }

  /**
   * Byte を KByte に換算して返します
   * 
   * @param _byte バイト
   * @return キロバイト
   */
  public static BigDecimal byteToKByte(long _byte) {
    return byteToKByte(_byte, DEFAULT_SCALE_BYTE);
  }

  /**
   * Byte を KByte に換算して返します
   * 
   * @param _byte バイト
   * @return キロバイト
   */
  public static BigDecimal byteToKByte(BigDecimal _byte) {
    return byteToKByte(_byte, DEFAULT_SCALE_BYTE);
  }

  /**
   * Byte を MByte に換算して返します
   * 
   * @param _byte バイト
   * @return メガバイト
   */
  public static BigDecimal byteToMByte(long _byte) {
    return byteToMByte(_byte, DEFAULT_SCALE_BYTE);
  }

  /**
   * Byte を MByte に換算して返します
   * 
   * @param _byte バイト
   * @return メガバイト
   */
  public static BigDecimal byteToMByte(BigDecimal _byte) {
    return byteToMByte(_byte, DEFAULT_SCALE_BYTE);
  }

  /**
   * Byte を KByte に換算して返します
   * 
   * @param _byte バイト
   * @param _scale 小数点精度
   * @return キロバイト
   */
  public static BigDecimal byteToKByte(long _byte, int _scale) {
    return byteToKByte(BigDecimal.valueOf(_byte), _scale);
  }

  /**
   * Byte を KByte に換算して返します
   * 
   * @param _byte バイト
   * @param _scale 小数点精度
   * @return キロバイト
   */
  public static BigDecimal byteToKByte(BigDecimal _byte, int _scale) {
    return _byte.divide(UNIT_BYTE, _scale, ROUNDING_MODE_BYTE);
  }

  /**
   * Byte を MByte に換算して返します
   * 
   * @param _byte バイト
   * @param _scale 小数点精度
   * @return メガバイト
   */
  public static BigDecimal byteToMByte(long _byte, int _scale) {
    return byteToMByte(BigDecimal.valueOf(_byte), _scale);
  }

  /**
   * Byte を MByte に換算して返します
   * 
   * @param _byte バイト
   * @param _scale 小数点精度
   * @return メガバイト
   */
  public static BigDecimal byteToMByte(BigDecimal _byte, int _scale) {
    return byteToKByte(_byte, _scale).divide(UNIT_BYTE, _scale, ROUNDING_MODE_BYTE);
  }

}
