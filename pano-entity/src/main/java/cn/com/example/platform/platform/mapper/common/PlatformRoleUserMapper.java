package cn.com.example.platform.platform.mapper.common;

import cn.com.platform.platform.model.common.PlatformRoleUser;
import cn.com.platform.platform.model.common.PlatformRoleUserQuery;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PlatformRoleUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_role_user
     *
     * @mbg.generated
     */
    long countByExample(PlatformRoleUserQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_role_user
     *
     * @mbg.generated
     */
    int deleteByExample(PlatformRoleUserQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_role_user
     *
     * @mbg.generated
     */
    List<PlatformRoleUser> selectByExample(PlatformRoleUserQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_role_user
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") PlatformRoleUser record, @Param("example") PlatformRoleUserQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_role_user
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") PlatformRoleUser record, @Param("example") PlatformRoleUserQuery example);
}