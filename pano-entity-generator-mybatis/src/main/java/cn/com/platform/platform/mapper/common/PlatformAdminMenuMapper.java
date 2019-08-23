package cn.com.platform.platform.mapper.common;

import cn.com.platform.platform.model.common.PlatformAdminMenu;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlatformAdminMenuMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_admin_menu
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String menuId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_admin_menu
     *
     * @mbg.generated
     */
    int insert(PlatformAdminMenu record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_admin_menu
     *
     * @mbg.generated
     */
    int insertSelective(PlatformAdminMenu record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_admin_menu
     *
     * @mbg.generated
     */
    PlatformAdminMenu selectByPrimaryKey(String menuId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_admin_menu
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(PlatformAdminMenu record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_admin_menu
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(PlatformAdminMenu record);
}