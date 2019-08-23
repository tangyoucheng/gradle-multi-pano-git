package cn.com.platform.common.quartz.trigger.impl;

import cn.com.platform.common.quartz.trigger.Trigger;

/**
 * 繰り返しトリガクラス
 * <p>
 * このトリガクラスは、実行回数と間隔（秒）を指定してジョブネットを実行するトリガクラスです。
 * <p>
 * トリガビルダを利用したトリガの生成例 :
 * 
 * <pre>
 * // ビルダを生成する
 * TriggerBuilder builder = new TriggerBuilder();
 * 
 * // 1回だけ実行するトリガを生成する
 * builder.onceSchedule().build();
 * 
 * // 1回だけ実行するトリガを生成する
 * builder.repertSchedule(1, null).build();
 * 
 * // 60秒間隔で10回実行するトリガを生成する
 * builder.repertSchedule(10, 60).build();
 * 
 * // 60秒間隔で無限に実行するトリガを生成する
 * builder.repertSchedule(null, 60).build();
 * 
 * </pre>
 * @author INTRAMART
 * @version 8.0
 */
public class RepeatTrigger extends Trigger {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** 回数 */
    protected Integer count;

    /** 間隔（秒） */
    protected Integer interval;

    /**
     * 新しい繰り返しトリガを生成します。
     */
    public RepeatTrigger() {
        super();
    }

    /**
     * 繰り返し回数を取得します。
     * <p>
     * 繰り返し回数が無限の場合は、<tt>null</tt>値
     * @return 回数
     */
    public Integer getCount() {
        return count;
    }

    /**
     * 繰り返し回数を設定します。
     * <p>
     * 繰り返し回数が無限の場合は、<tt>null</tt>値
     * @param count 回数
     */
    public void setCount(final Integer count) {
        this.count = count;
    }

    /**
     * 間隔（秒）を取得します。
     * <p>
     * 最小値は<tt>1</tt>秒、ただし繰り返し回数が1回の場合は、<tt>null</tt>値でも可
     * @return 間隔（秒）
     */
    public Integer getInterval() {
        return interval;
    }

    /**
     * 間隔（秒）を設定します。
     * <p>
     * 最小値は<tt>1</tt>秒、ただし繰り返し回数が1回の場合は、<tt>null</tt>値でも可
     * @param interval 間隔（秒）
     */
    public void setInterval(final Integer interval) {
        this.interval = interval;
    }

    /**
     * {@inheritDoc}
     * @see java.lang.Object#toString()
     */
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("RepeatTrigger [toString()=");
        builder.append(super.toString());
        builder.append(", count=");
        builder.append(count);
        builder.append(", interval=");
        builder.append(interval);
        builder.append("]");
        return builder.toString();
    }

}
