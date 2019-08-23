package cn.com.platform.platform.mapper.common;

import cn.com.platform.platform.model.common.PlatformRoleUser;
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
    int deleteByPrimaryKey(@Param("roleId") String roleId, @Param("memberId") String memberId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_role_user
     *
     * @mbg.generated
     */
    int insert(PlatformRoleUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_role_user
     *
     * @mbg.generated
     */
    int insertSelective(PlatformRoleUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_role_user
     *
     * @mbg.generated
     */
    PlatformRoleUser selectByPrimaryKey(@Param("roleId") String roleId, @Param("memberId") String memberId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_role_user
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(PlatformRoleUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_role_user
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(PlatformRoleUser record);
}