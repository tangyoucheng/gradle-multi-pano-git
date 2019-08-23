package cn.com.example.pano.pano.mapper.common;

import cn.com.pano.pano.model.common.PanoGrpRoleUserTmpl;
import cn.com.pano.pano.model.common.PanoGrpRoleUserTmplQuery;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PanoGrpRoleUserTmplMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pano_grp_role_user_tmpl
     *
     * @mbg.generated
     */
    long countByExample(PanoGrpRoleUserTmplQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pano_grp_role_user_tmpl
     *
     * @mbg.generated
     */
    int deleteByExample(PanoGrpRoleUserTmplQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pano_grp_role_user_tmpl
     *
     * @mbg.generated
     */
    List<PanoGrpRoleUserTmpl> selectByExampleWithBLOBs(PanoGrpRoleUserTmplQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pano_grp_role_user_tmpl
     *
     * @mbg.generated
     */
    List<PanoGrpRoleUserTmpl> selectByExample(PanoGrpRoleUserTmplQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pano_grp_role_user_tmpl
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") PanoGrpRoleUserTmpl record, @Param("example") PanoGrpRoleUserTmplQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pano_grp_role_user_tmpl
     *
     * @mbg.generated
     */
    int updateByExampleWithBLOBs(@Param("record") PanoGrpRoleUserTmpl record, @Param("example") PanoGrpRoleUserTmplQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pano_grp_role_user_tmpl
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") PanoGrpRoleUserTmpl record, @Param("example") PanoGrpRoleUserTmplQuery example);
}