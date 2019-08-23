/*
 * Copyright(c) 2011 
 */
package cn.com.platform.framework.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import cn.com.platform.framework.common.StandardConstantsIF;



/**
 * タイムスタンプユーティリティ.
 * 
 * @author nttdc
 */
public class FwTimestampUtils {

    /**
     * DateUtils 的构造。
     */
    private FwTimestampUtils() {
    }

    /**
     * APサーバーから系统Timestampを取得
     * 
     * @return 現在の系统日付
     */
    public static Timestamp getSysTimestampFromApServer() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 系统Timestampを取得
     * 
     * @return 現在の系统日付
     */
    public static Timestamp getSysTimestamp() {

        Timestamp result_ = null;

        // APサーバーの日付を返す
        result_ = getSysTimestampFromApServer();

        // ミリ秒桁を統一する
        return parse(format(result_, StandardConstantsIF.DATE_FORMAT_TIMESTAMP));

    }

    /**
     * 指定された文字列を日付時刻型に変換する。
     * 
     * @param _text 日付時刻文字列
     * @return 変換後の日付
     */
    public static Timestamp parse(String _text) {
        if (FwCheckUtils.isEmpty(_text)) {
            return null;
        }
        Timestamp timestamp = Timestamp.valueOf(_text);
        return timestamp;
    }

    /**
     * 日付を指定された形式の日付時刻文字列に変換する。
     * 
     * @param _timestamp 付時刻
     * @param _format 日付時刻文字列の形式
     * @return 変換後の日付時刻文字列
     */
    public static String format(Timestamp _timestamp, String _format) {
        if (_timestamp == null || FwCheckUtils.isEmpty(_format)) {
            throw new IllegalArgumentException();
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(_format);
        return dateFormat.format(_timestamp);
    }

    /**
     * 指定された日付時刻文字列に変換する。
     * 
     * @param _timestamp 付時刻
     * @return 変換後の日付時刻文字列
     */
    public static String formatUpd(Timestamp _timestamp) {
        if (_timestamp == null) {
            return null;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(StandardConstantsIF.DATE_FORMAT_TIMESTAMP);
        return dateFormat.format(_timestamp);
    }

}
