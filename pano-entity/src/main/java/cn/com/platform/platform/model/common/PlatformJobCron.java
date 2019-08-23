package cn.com.platform.platform.model.common;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PlatformJobCron implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column platform_job_cron.JOB_ID
     *
     * @mbg.generated
     */
    public String jobId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column platform_job_cron.JOB_NAME
     *
     * @mbg.generated
     */
    public String jobName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column platform_job_cron.JOB_GROUP
     *
     * @mbg.generated
     */
    public String jobGroup;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column platform_job_cron.METHOD_NAME
     *
     * @mbg.generated
     */
    public String methodName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column platform_job_cron.METHOD_PARAMS
     *
     * @mbg.generated
     */
    public String methodParams;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column platform_job_cron.CRON_EXPRESSION
     *
     * @mbg.generated
     */
    public String cronExpression;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column platform_job_cron.TRIGGER_REMARK
     *
     * @mbg.generated
     */
    public String triggerRemark;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column platform_job_cron.PRIORITY
     *
     * @mbg.generated
     */
    public String priority;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column platform_job_cron.MISFIRE_POLICY
     *
     * @mbg.generated
     */
    public String misfirePolicy;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column platform_job_cron.JOB_STATUS
     *
     * @mbg.generated
     */
    public String jobStatus;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column platform_job_cron.REMARK
     *
     * @mbg.generated
     */
    public String remark;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column platform_job_cron.DELETE_FLAG
     *
     * @mbg.generated
     */
    public Boolean deleteFlag;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column platform_job_cron.CREATE_USER_ID
     *
     * @mbg.generated
     */
    public String createUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column platform_job_cron.CREATE_DATE
     *
     * @mbg.generated
     */
    public LocalDateTime createDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column platform_job_cron.LAST_UPDATE_USER_ID
     *
     * @mbg.generated
     */
    public String lastUpdateUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column platform_job_cron.LAST_UPDATE_DATE
     *
     * @mbg.generated
     */
    public LocalDateTime lastUpdateDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table platform_job_cron
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;
}