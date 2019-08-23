package cn.com.example.pano.pano.mapper.common;

import cn.com.pano.pano.model.common.PanoGroupRoleUser;
import cn.com.pano.pano.model.common.PanoGroupRoleUserQuery;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PanoGroupRoleUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pano_group_role_user
     *
     * @mbg.generated
     */
    long countByExample(PanoGroupRoleUserQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pano_group_role_user
     *
     * @mbg.generated
     */
    int deleteByExample(PanoGroupRoleUserQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pano_group_role_user
     *
     * @mbg.generated
     */
    List<PanoGroupRoleUser> selectByExample(PanoGroupRoleUserQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pano_group_role_user
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") PanoGroupRoleUser record, @Param("example") PanoGroupRoleUserQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pano_group_role_user
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") PanoGroupRoleUser record, @Param("example") PanoGroupRoleUserQuery example);
}