package cn.com.example.platform.platform.mapper.common;

import cn.com.platform.platform.model.common.PlatformRole;
import cn.com.platform.platform.model.common.PlatformRoleQuery;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PlatformRoleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_role
     *
     * @mbg.generated
     */
    long countByExample(PlatformRoleQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_role
     *
     * @mbg.generated
     */
    int deleteByExample(PlatformRoleQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_role
     *
     * @mbg.generated
     */
    List<PlatformRole> selectByExample(PlatformRoleQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_role
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") PlatformRole record, @Param("example") PlatformRoleQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_role
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") PlatformRole record, @Param("example") PlatformRoleQuery example);
}