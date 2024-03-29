package cn.com.example.pano.pano.mapper.common;

import cn.com.pano.pano.model.common.PanoExpositionMapHotspot;
import cn.com.pano.pano.model.common.PanoExpositionMapHotspotQuery;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PanoExpositionMapHotspotMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pano_exposition_map_hotspot
     *
     * @mbg.generated
     */
    long countByExample(PanoExpositionMapHotspotQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pano_exposition_map_hotspot
     *
     * @mbg.generated
     */
    int deleteByExample(PanoExpositionMapHotspotQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pano_exposition_map_hotspot
     *
     * @mbg.generated
     */
    List<PanoExpositionMapHotspot> selectByExample(PanoExpositionMapHotspotQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pano_exposition_map_hotspot
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") PanoExpositionMapHotspot record, @Param("example") PanoExpositionMapHotspotQuery example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pano_exposition_map_hotspot
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") PanoExpositionMapHotspot record, @Param("example") PanoExpositionMapHotspotQuery example);
}