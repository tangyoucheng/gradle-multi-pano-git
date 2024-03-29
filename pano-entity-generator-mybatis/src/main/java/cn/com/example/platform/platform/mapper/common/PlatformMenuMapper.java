package cn.com.example.platform.platform.mapper.common;

import cn.com.platform.platform.model.common.PlatformMenu;
import cn.com.platform.platform.model.common.PlatformMenuQuery;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PlatformMenuMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_menu
     *
     * @mbg.generated
     */
    long countByExample(PlatformMenuQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_menu
     *
     * @mbg.generated
     */
    int deleteByExample(PlatformMenuQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_menu
     *
     * @mbg.generated
     */
    List<PlatformMenu> selectByExample(PlatformMenuQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_menu
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") PlatformMenu record, @Param("example") PlatformMenuQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_menu
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") PlatformMenu record, @Param("example") PlatformMenuQuery example);
}