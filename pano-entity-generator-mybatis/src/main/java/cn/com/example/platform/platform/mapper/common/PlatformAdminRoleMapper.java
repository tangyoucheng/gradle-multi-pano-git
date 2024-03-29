package cn.com.example.platform.platform.mapper.common;

import cn.com.platform.platform.model.common.PlatformAdminRole;
import cn.com.platform.platform.model.common.PlatformAdminRoleQuery;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PlatformAdminRoleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_admin_role
     *
     * @mbg.generated
     */
    long countByExample(PlatformAdminRoleQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_admin_role
     *
     * @mbg.generated
     */
    int deleteByExample(PlatformAdminRoleQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_admin_role
     *
     * @mbg.generated
     */
    List<PlatformAdminRole> selectByExample(PlatformAdminRoleQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_admin_role
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") PlatformAdminRole record, @Param("example") PlatformAdminRoleQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_admin_role
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") PlatformAdminRole record, @Param("example") PlatformAdminRoleQuery example);
}