package cn.com.pano.pano.mapper.common;

import cn.com.pano.pano.model.common.PanoMaterial;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PanoMaterialMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pano_material
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String materialId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pano_material
     *
     * @mbg.generated
     */
    int insert(PanoMaterial record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pano_material
     *
     * @mbg.generated
     */
    int insertSelective(PanoMaterial record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pano_material
     *
     * @mbg.generated
     */
    PanoMaterial selectByPrimaryKey(String materialId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pano_material
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(PanoMaterial record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pano_material
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyWithBLOBs(PanoMaterial record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pano_material
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(PanoMaterial record);
}