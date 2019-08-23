package cn.com.example.platform.platform.mapper.common;

import cn.com.platform.platform.model.common.PlatformDepartmentUser;
import cn.com.platform.platform.model.common.PlatformDepartmentUserQuery;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PlatformDepartmentUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_department_user
     *
     * @mbg.generated
     */
    long countByExample(PlatformDepartmentUserQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_department_user
     *
     * @mbg.generated
     */
    int deleteByExample(PlatformDepartmentUserQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_department_user
     *
     * @mbg.generated
     */
    List<PlatformDepartmentUser> selectByExample(PlatformDepartmentUserQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_department_user
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") PlatformDepartmentUser record, @Param("example") PlatformDepartmentUserQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_department_user
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") PlatformDepartmentUser record, @Param("example") PlatformDepartmentUserQuery example);
}