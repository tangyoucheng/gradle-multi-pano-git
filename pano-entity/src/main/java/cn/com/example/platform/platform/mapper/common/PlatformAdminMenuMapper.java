package cn.com.example.platform.platform.mapper.common;

import cn.com.platform.platform.model.common.PlatformAdminMenu;
import cn.com.platform.platform.model.common.PlatformAdminMenuQuery;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PlatformAdminMenuMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_admin_menu
     *
     * @mbg.generated
     */
    long countByExample(PlatformAdminMenuQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_admin_menu
     *
     * @mbg.generated
     */
    int deleteByExample(PlatformAdminMenuQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_admin_menu
     *
     * @mbg.generated
     */
    List<PlatformAdminMenu> selectByExample(PlatformAdminMenuQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_admin_menu
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") PlatformAdminMenu record, @Param("example") PlatformAdminMenuQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_admin_menu
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") PlatformAdminMenu record, @Param("example") PlatformAdminMenuQuery example);
}