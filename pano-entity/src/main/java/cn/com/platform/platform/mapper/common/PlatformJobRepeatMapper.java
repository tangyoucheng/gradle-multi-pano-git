package cn.com.platform.platform.mapper.common;

import cn.com.platform.platform.model.common.PlatformJobRepeat;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlatformJobRepeatMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_job_repeat
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String jobId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_job_repeat
     *
     * @mbg.generated
     */
    int insert(PlatformJobRepeat record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_job_repeat
     *
     * @mbg.generated
     */
    int insertSelective(PlatformJobRepeat record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_job_repeat
     *
     * @mbg.generated
     */
    PlatformJobRepeat selectByPrimaryKey(String jobId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_job_repeat
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(PlatformJobRepeat record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_job_repeat
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(PlatformJobRepeat record);
}