package cn.com.pano.pano.mapper.common;

import cn.com.pano.pano.model.common.PanoPanorama;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PanoPanoramaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pano_panorama
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String panoramaId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pano_panorama
     *
     * @mbg.generated
     */
    int insert(PanoPanorama record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pano_panorama
     *
     * @mbg.generated
     */
    int insertSelective(PanoPanorama record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pano_panorama
     *
     * @mbg.generated
     */
    PanoPanorama selectByPrimaryKey(String panoramaId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pano_panorama
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(PanoPanorama record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pano_panorama
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyWithBLOBs(PanoPanorama record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pano_panorama
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(PanoPanorama record);
}