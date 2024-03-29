package cn.com.example.platform.platform.mapper.common;

import cn.com.platform.platform.model.common.PlatformJobCron;
import cn.com.platform.platform.model.common.PlatformJobCronQuery;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PlatformJobCronMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_job_cron
     *
     * @mbg.generated
     */
    long countByExample(PlatformJobCronQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_job_cron
     *
     * @mbg.generated
     */
    int deleteByExample(PlatformJobCronQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_job_cron
     *
     * @mbg.generated
     */
    List<PlatformJobCron> selectByExample(PlatformJobCronQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_job_cron
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") PlatformJobCron record, @Param("example") PlatformJobCronQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_job_cron
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") PlatformJobCron record, @Param("example") PlatformJobCronQuery example);
}