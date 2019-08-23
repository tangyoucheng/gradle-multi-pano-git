package cn.com.platform.common.quartz.trigger.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;
import cn.com.platform.common.quartz.trigger.Trigger;

/**
 * 日時指定トリガクラス
 * <p>
 * このトリガクラスは、年月日（曜日）時分秒のセットを指定してジョブネットを実行するトリガクラスです。
 * <p>
 * 設定例：
 * 
 * <pre>
 * <table border="1">
 *   <tr>
 *     <th>description</th>
 *     <th>years</th>
 *     <th>months</th>
 *     <th>daysOfMonth</th>
 *     <th>daysOfWeek</th>
 *     <th>hours</th>
 *     <th>minutes</th>
 *   </tr>
 *   <tr>
 *     <td>2011年1月1日0時0分0秒に一度だけ実行される</td>
 *     <td>{2011}</td>
 *     <td>{0} ※月の設定値はjava.util.Calendar仕様に従います</td>
 *     <td>{1}</td>
 *     <td>null ※日に{1}を設定しているので曜日は設定しない</td>
 *     <td>{0}</td>
 *     <td>{0}</td>
 *   </tr>
 *   <tr>
 *     <td>毎年1～6月の1日0時0分0秒に実行される</td>
 *     <td>null</td>
 *     <td>{0,1,2,3,4,5}</td>
 *     <td>{1}</td>
 *     <td>null ※日に{1}を設定しているので曜日は設定しない</td>
 *     <td>{0}</td>
 *     <td>{0}</td>
 *   </tr>
 *   <tr>
 *     <td>毎週土曜日、日曜日の9時、12時、18時の0分0秒に実行される</td>
 *     <td>null</td>
 *     <td>null</td>
 *     <td>null ＊曜日に{1,7}を設定しているので日は設定しない</td>
 *     <td>{1,7}</td>
 *     <td>{9,12,18}</td>
 *     <td>{0}</td>
 *   </tr>
 * </table>
 * </pre>
 * 
 * ※日と曜日は、同時に設定出来ない条件なので必ず片方には<tt>null</tt>値を設定します。<br/>
 * どちらも<tt>null</tt>値の場合は、毎日実行されるスケジュールとなり。どちらも<tt>null</tt>値でない場合は、曜日の設定が優先されます。
 * <p>
 * トリガビルダを利用したトリガの生成例 :
 * 
 * <pre>
 * // ビルダを生成する
 * TriggerBuilder builder = new TriggerBuilder();
 * 
 * // 実行したい日時のセットでトリガを作成する。
 * final Set<Integer> years = new TreeSet<Integer>();
 * final Set<Integer> months = new TreeSet<Integer>();
 * ...
 * final Set<Integer> minutes = new TreeSet<Integer>();
 * builder.datetimeSchedule(years, months, daysOfWeek, daysOfMonth, hours, minutes).build();
 * 
 * // 指定日時に1回実行するスケジュールのトリガを生成する
 * builder.anniversarySchedule(2020, 0, 1, 23, 59).build();
 * 
 * // 毎年実行するスケジュールのトリガを生成する
 * builder.annuallySchedule(Calendar.OCTOBER, 6, 0, 0).build();
 * 
 * // 毎月実行するスケジュールのトリガを生成する
 * builder.monthlySchedule(20, 18, 30, 0).build();
 * 
 * // 毎週実行するスケジュールのトリガを生成する
 * builder.weeklySchedule(Calendar.MONDAY, 9, 30).build();
 * 
 * ...
 * 
 * // 毎分実行するスケジュールのトリガを生成する
 * builder.minutelySchedule().build();
 * </pre>
 * @author INTRAMART
 * @version 8.0
 */
public class DatetimeTrigger extends Trigger {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** タイムゾーン */
    protected TimeZone timeZone;

    /** 年 */
    protected Set<Integer> years = new TreeSet<Integer>();

    /** 月 */
    protected Set<Integer> months = new TreeSet<Integer>();

    /** 日 */
    protected Set<Integer> daysOfMonth = new TreeSet<Integer>();

    /** 曜日 */
    protected Set<Integer> daysOfWeek = new TreeSet<Integer>();

    /** 時 */
    protected Set<Integer> hours = new TreeSet<Integer>();

    /** 分 */
    protected Set<Integer> minutes = new TreeSet<Integer>();

    /** 秒 */
    protected Set<Integer> seconds = new TreeSet<Integer>();

    /**
     * 新しい日時指定トリガを生成します。
     */
    public DatetimeTrigger() {
        super();
        seconds.add(0);
    }

    /**
     * タイムゾーンを取得します。
     * @return タイムゾーン
     */
    public TimeZone getTimeZone() {
        return timeZone;
    }

    /**
     * タイムゾーンを設定します。
     * @param timeZone タイムゾーン
     */
    public void setTimeZone(final TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    /**
     * 年を取得します。
     * @return 年
     */
    public Set<Integer> getYears() {
        return years;
    }

    /**
     * 年を設定します。
     * @param years 年
     */
    public void setYears(final Set<Integer> years) {
        this.years = years;
    }

    /**
     * 月を取得します。
     * @return 月
     */
    public Set<Integer> getMonths() {
        return months;
    }

    /**
     * 月を設定します。
     * @param months 月
     */
    public void setMonths(final Set<Integer> months) {
        this.months = months;
    }

    /**
     * 日を取得します。
     * @return 日
     */
    public Set<Integer> getDaysOfMonth() {
        return daysOfMonth;
    }

    /**
     * 日を設定します。
     * @param daysOfMonth 日
     */
    public void setDaysOfMonth(final Set<Integer> daysOfMonth) {
        this.daysOfMonth = daysOfMonth;
    }

    /**
     * 曜日を取得します。
     * @return 曜日
     */
    public Set<Integer> getDaysOfWeek() {
        return daysOfWeek;
    }

    /**
     * 曜日を設定します。
     * @param daysOfWeek 曜日
     */
    public void setDaysOfWeek(final Set<Integer> daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    /**
     * 時を取得します。
     * @return 時
     */
    public Set<Integer> getHours() {
        return hours;
    }

    /**
     * 時を設定します。
     * @param hours 時
     */
    public void setHours(final Set<Integer> hours) {
        this.hours = hours;
    }

    /**
     * 分を取得します。
     * @return 分
     */
    public Set<Integer> getMinutes() {
        return minutes;
    }

    /**
     * 分を設定します。
     * @param minutes 分
     */
    public void setMinutes(final Set<Integer> minutes) {
        this.minutes = minutes;
    }

    // /**
    // * 秒を取得します。
    // * @return 秒
    // */
    // public Set<Integer> getSeconds() {
    // return seconds;
    // }
    //
    // /**
    // * 秒を設定します。
    // * @param seconds 秒
    // */
    // public void setSeconds(final Set<Integer> seconds) {
    // this.seconds = seconds;
    // }

    /**
     * {@inheritDoc}
     * @see java.lang.Object#toString()
     */
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("DatetimeTrigger [toString()=");
        builder.append(super.toString());
        builder.append(", timeZone=");
        builder.append(timeZone);
        builder.append(", years=");
        builder.append(years);
        builder.append(", months=");
        builder.append(months);
        builder.append(", daysOfMonth=");
        builder.append(daysOfMonth);
        builder.append(", daysOfWeek=");
        builder.append(daysOfWeek);
        builder.append(", hours=");
        builder.append(hours);
        builder.append(", minutes=");
        builder.append(minutes);
        // builder.append(", seconds=");
        // builder.append(seconds);
        builder.append("]");
        return builder.toString();
    }

}
