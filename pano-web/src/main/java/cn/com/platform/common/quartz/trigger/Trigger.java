package cn.com.platform.common.quartz.trigger;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * トリガクラス
 * @author INTRAMART
 * @version 8.0
 * @see cn.com.platform.common.quartz.trigger.impl.RepeatTrigger
 * @see cn.com.platform.common.quartz.trigger.impl.DatetimeTrigger
 */
public abstract class Trigger implements Serializable {
    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** トリガ<tt>ID</tt> */
    protected String id;

    /** ジョブネット<tt>ID</tt> */
    protected String jobnetId;

    /** パラメータ */
    protected Map<String, String> parameters = new TreeMap<String, String>();

    /** 開始日時 */
    protected Date startDate;

    /** 終了日時 */
    protected Date endDate;

    /** 説明 */
    protected String description;

    /** 有効 */
    protected boolean enable = true;

    /**
     * 新しいトリガを生成します。
     */
    public Trigger() {
    }

    /**
     * トリガ<tt>ID</tt>を取得します。
     * @return トリガ<tt>ID</tt>
     */
    public String getId() {
        return id;
    }

    /**
     * トリガ<tt>ID</tt>を設定します。
     * @param id トリガ<tt>ID</tt>
     */
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * ジョブネット<tt>ID</tt>を取得します。
     * @return ジョブネット<tt>ID</tt>
     */
    public String getJobnetId() {
        return jobnetId;
    }

    /**
     * ジョブネット<tt>ID</tt>を設定します。
     * @param jobnetId ジョブネット<tt>ID</tt>
     */
    public void setJobnetId(final String jobnetId) {
        this.jobnetId = jobnetId;
    }

    /**
     * パラメータを取得します。
     * @return パラメータ
     */
    public Map<String, String> getParameters() {
        return parameters;
    }

    /**
     * パラメータを設定します。
     * @param parameters パラメータ
     */
    public void setParameters(final Map<String, String> parameters) {
        this.parameters = parameters;
    }

    /**
     * 開始日時を取得します。
     * <p>
     * 指定されていない場合は<tt>null</tt>
     * @return 開始日時
     */
    @Deprecated
    public Date getStartDate() {
        return startDate;
    }

    /**
     * 開始日時を設定します。
     * <p>
     * 指定しない場合は<tt>null</tt>
     * @param startDate 開始日時
     */
    @Deprecated
    public void setStartDate(final Date startDate) {
        this.startDate = startDate;
    }

    /**
     * 終了日時を取得します。
     * <p>
     * 指定されていない場合は<tt>null</tt>
     * @return 終了日時
     */
    @Deprecated
    public Date getEndDate(final Date endDate) {
      return endDate;
    }

    /**
     * 終了日時を設定します。
     * <p>
     * 指定しない場合は<tt>null</tt>
     * @param endDate 終了日時
     */
    @Deprecated
    public void setEndDate(final Date endDate) {
        this.endDate = endDate;
    }

    /**
     * 説明を取得します。
     * @return 説明
     */
    public String getDescription() {
        return description;
    }

    /**
     * 説明を設定します。
     * @param description 説明
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * 有効を取得します。
     * @return 有効な場合は<tt>true</tt>、無効の場合は<tt>false</tt>
     */
    public boolean isEnable() {
        return enable;
    }

    /**
     * 有効を設定します。
     * @param enable 有効な場合は<tt>true</tt>、無効の場合は<tt>false</tt>
     */
    public void setEnable(final boolean enable) {
        this.enable = enable;
    }

    /**
     * {@inheritDoc}
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Trigger [id=");
        builder.append(id);
        builder.append(", jobnetId=");
        builder.append(jobnetId);
        builder.append(", parameters=");
        builder.append(parameters);
        builder.append(", startDate=");
        builder.append(startDate);
        builder.append(", endDate=");
        builder.append(endDate);
        builder.append(", description=");
        builder.append(description);
        builder.append(", enable=");
        builder.append(enable);
        builder.append("]");
        return builder.toString();
    }

    protected boolean isNull(final Set<Integer> value) {
        return value == null || value.isEmpty();
    }

}
