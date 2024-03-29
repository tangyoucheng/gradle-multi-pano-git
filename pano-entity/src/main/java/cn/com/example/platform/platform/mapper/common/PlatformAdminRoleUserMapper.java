package cn.com.example.platform.platform.mapper.common;

import cn.com.platform.platform.model.common.PlatformAdminRoleUser;
import cn.com.platform.platform.model.common.PlatformAdminRoleUserQuery;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PlatformAdminRoleUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_admin_role_user
     *
     * @mbg.generated
     */
    long countByExample(PlatformAdminRoleUserQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_admin_role_user
     *
     * @mbg.generated
     */
    int deleteByExample(PlatformAdminRoleUserQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_admin_role_user
     *
     * @mbg.generated
     */
    List<PlatformAdminRoleUser> selectByExample(PlatformAdminRoleUserQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_admin_role_user
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") PlatformAdminRoleUser record, @Param("example") PlatformAdminRoleUserQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_admin_role_user
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") PlatformAdminRoleUser record, @Param("example") PlatformAdminRoleUserQuery example);
}