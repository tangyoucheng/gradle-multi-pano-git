package cn.com.platform.platform.model.common;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PlatformOperateLog implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column platform_operate_log.MODULE_ID
     *
     * @mbg.generated
     */
    public String moduleId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column platform_operate_log.RECORD_ID
     *
     * @mbg.generated
     */
    public String recordId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column platform_operate_log.OPERATE_ID
     *
     * @mbg.generated
     */
    public String operateId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column platform_operate_log.MODULE_TITLE
     *
     * @mbg.generated
     */
    public String moduleTitle;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column platform_operate_log.BUSINESS_TYPE
     *
     * @mbg.generated
     */
    public Integer businessType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column platform_operate_log.METHOD
     *
     * @mbg.generated
     */
    public String method;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column platform_operate_log.OPERATOR_TYPE
     *
     * @mbg.generated
     */
    public Integer operatorType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column platform_operate_log.OPERATE_NAME
     *
     * @mbg.generated
     */
    public String operateName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column platform_operate_log.DEPARTMENT_NAME
     *
     * @mbg.generated
     */
    public String departmentName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column platform_operate_log.OPERATE_URL
     *
     * @mbg.generated
     */
    public String operateUrl;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column platform_operate_log.OPERATE_IP
     *
     * @mbg.generated
     */
    public String operateIp;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column platform_operate_log.OPERATE_LOCATION
     *
     * @mbg.generated
     */
    public String operateLocation;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column platform_operate_log.SUCCESS_STATUS
     *
     * @mbg.generated
     */
    public Integer successStatus;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column platform_operate_log.ERROR_MSG
     *
     * @mbg.generated
     */
    public String errorMsg;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column platform_operate_log.OPERATE_TIME
     *
     * @mbg.generated
     */
    public LocalDateTime operateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column platform_operate_log.OPERATE_PARAM
     *
     * @mbg.generated
     */
    public String operateParam;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table platform_operate_log
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;
}