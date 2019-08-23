package cn.com.example.platform.platform.mapper.common;

import cn.com.platform.platform.model.common.PlatformAdminRoleMenu;
import cn.com.platform.platform.model.common.PlatformAdminRoleMenuQuery;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PlatformAdminRoleMenuMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_admin_role_menu
     *
     * @mbg.generated
     */
    long countByExample(PlatformAdminRoleMenuQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_admin_role_menu
     *
     * @mbg.generated
     */
    int deleteByExample(PlatformAdminRoleMenuQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_admin_role_menu
     *
     * @mbg.generated
     */
    List<PlatformAdminRoleMenu> selectByExample(PlatformAdminRoleMenuQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_admin_role_menu
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") PlatformAdminRoleMenu record, @Param("example") PlatformAdminRoleMenuQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_admin_role_menu
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") PlatformAdminRoleMenu record, @Param("example") PlatformAdminRoleMenuQuery example);
}