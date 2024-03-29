package cn.com.platform.platform.mapper.common;

import cn.com.platform.platform.model.common.PlatformRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PlatformRoleMenuMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_role_menu
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(@Param("roleId") String roleId, @Param("menuId") String menuId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_role_menu
     *
     * @mbg.generated
     */
    int insert(PlatformRoleMenu record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_role_menu
     *
     * @mbg.generated
     */
    int insertSelective(PlatformRoleMenu record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_role_menu
     *
     * @mbg.generated
     */
    PlatformRoleMenu selectByPrimaryKey(@Param("roleId") String roleId, @Param("menuId") String menuId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_role_menu
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(PlatformRoleMenu record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_role_menu
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(PlatformRoleMenu record);
}